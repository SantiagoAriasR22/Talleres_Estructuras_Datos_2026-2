package controlador;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import modelo.Registro;
import modelo.TablaHash;
import persistencia.Escritura;
import persistencia.Lectura;

public class Controlador {
    
    private TablaHash tablaHash;
    private final Lectura lectura = new Lectura();
    private final Escritura escritura = new Escritura();
    
    // Copia de los registros que hay en la tabla. Es la fuente de verdad para
    // reconstruir la tabla cuando se cambia de estructura o de metodo hash,
    // sin tener que leer ni escribir el archivo.
    private final List<Registro> registros = new ArrayList<>();
    
    // Configuracion vigente de la tabla.
    private boolean esLista;
    private boolean esDivision;
    
    private File archivoActual;
    
    public Controlador(){}
    
    public void crearTabla(boolean esLista, boolean esDivision){
        this.esLista = esLista;
        this.esDivision = esDivision;
        this.tablaHash = new TablaHash(esLista, esDivision);
    }
    
    // Devuelve cuantos registros se cargaron, o -1 si no se pudo abrir el archivo.
    public int cargarDatos(File archivo){ 
        
        List<Registro> leidos = lectura.leer(archivo);
        
        if(leidos == null){
            // No se pudo leer: se deja la tabla como estaba, coherente con la lista
            // de registros que ya se tenia. Asi no se pierde lo que habia cargado.
            reinsertar();
            return -1;
        }
        
        registros.clear();
        registros.addAll(leidos);
        archivoActual = archivo;
        
        reinsertar();
        
        return registros.size();
    }
    
    // Reconstruye la tabla desde cero con la configuracion vigente.
    // Todo en memoria: no toca el archivo.
    private void reinsertar(){
        tablaHash = new TablaHash(esLista, esDivision);
        for(Registro r : registros) tablaHash.insertarRegistro(r);
    }
    
    // Cambia la estructura y/o el metodo hash y reconstruye la tabla.
    // Los registros no se pierden: viven en la lista, no en el archivo.
    public void cambiarEstructura(boolean esLista, boolean esDivision){
        this.esLista = esLista;
        this.esDivision = esDivision;
        reinsertar();
    }
    
    // Escribe el estado actual de la tabla en el archivo indicado.
    // No reconstruye nada: la tabla ya esta en el orden de su estructura.
    public boolean guardarDatos(File destino){
        if(tablaHash == null || destino == null) return false;
        return escritura.actualizarArchivo(destino, tablaHash);
    }
    
    public File getArchivoActual(){
        return archivoActual;
    }
    
    public void cargarRegistroGUI(){
        //aca va la logica para cuando se vayan a pasar los registros a la GUI por medio de lectura (seria lo ideal)
    }
    
    public int cargarColisiones(){
        return (tablaHash==null) ? 0 : tablaHash.getColisiones();
    }
    
    public boolean hayTabla(){
        return tablaHash != null;
    }
    
    // La lista y la tabla se mantienen sincronizadas: si se desincronizan,
    // una reconstruccion devolveria datos viejos.
    public void insertarRegistro(Registro registro){
        if(tablaHash == null) return;
        registros.add(registro);
        tablaHash.insertarRegistro(registro);
    }
    
    public boolean eliminarRegistro(String plate){
        if(tablaHash == null) return false;
        
        boolean borrado = tablaHash.eliminar(plate);
        
        if(borrado){
            for(int i = 0; i < registros.size(); i++){
                if(registros.get(i).getPlate().equals(plate)){
                    registros.remove(i);
                    break;
                }
            }
        }
        
        return borrado;
    }
    
    public Registro buscarRegistro(String plate){
        return (tablaHash == null) ? null : tablaHash.buscar(plate);
    }
    
}
