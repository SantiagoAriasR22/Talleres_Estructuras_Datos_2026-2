package controlador;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import modelo.Arbol;
import modelo.Lista;
import modelo.NodoArbol;
import modelo.NodoLista;
import modelo.Registro;
import modelo.SlotVista;
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
    
    // true si hubo inserciones o borrados desde la ultima carga o guardado.
    // Sirve para preguntar antes de cerrar la aplicacion.
    private boolean hayCambios = false;
    
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
        hayCambios = false;
        
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
        
        boolean escrito = escritura.actualizarArchivo(destino, tablaHash);
        
        if(escrito) hayCambios = false;
        
        return escrito;
    }
    
    public boolean hayCambios(){
        return hayCambios;
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
        hayCambios = true;
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
            hayCambios = true;
        }
        
        return borrado;
    }
    
    public Registro buscarRegistro(String plate){
        return (tablaHash == null) ? null : tablaHash.buscar(plate);
    }
    
    public List<SlotVista> slotsConVarios(){
        
        List<SlotVista> resultado = new ArrayList<>();
        
        if(tablaHash == null) return resultado;
        
        for(int i = 0; i < tablaHash.getM(); i++){
            
            Object contenido = tablaHash.getTablaHash().get(i);
            
            if(tablaHash.isEsLista()){
                
                Lista<Registro> lista = (Lista<Registro>) contenido;
                List<String> placas = new ArrayList<>();
                
                for(NodoLista<Registro> actual = lista.getHead(); actual != null; actual = actual.getNext()){
                    placas.add(((Registro) actual.getDato()).getPlate());
                }
                
                if(placas.size() > 1){
                    resultado.add(new SlotVista(i, true, placas, null));
                }
            }
            else{
                
                Arbol<Registro> arbol = (Arbol<Registro>) contenido;
                NodoArbol<Registro> raiz = (NodoArbol<Registro>) arbol.getRaiz();
                
                if(raiz != null && contarNodos(raiz) > 1){
                    resultado.add(new SlotVista(i, false, null, raiz));
                }
            }
        }
        
        return resultado;
    }
    
    private int contarNodos(NodoArbol<Registro> nodo){
        if(nodo == null) return 0;
        return 1 + contarNodos((NodoArbol<Registro>) nodo.getIzq()) + contarNodos((NodoArbol<Registro>) nodo.getDer());
    }
    
}
