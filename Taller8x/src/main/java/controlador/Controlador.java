package controlador;

import java.io.File;
import modelo.Registro;
import modelo.TablaHash;
import persistencia.Escritura;
import persistencia.Lectura;

public class Controlador {
    
    private TablaHash tablaHash;
    private Lectura lectura = new Lectura();
    private final Escritura escritura = new Escritura();
    private File archivoActual;
    public Controlador(){}
    
    public void crearTabla(boolean esLista, boolean esDivision){
        tablaHash = new TablaHash(esLista, esDivision);
    }
    
    public int cargarDatos(File archivo){ 
        if (tablaHash == null) return 0;
        
        int cargados = lectura.lecturaRegistros(archivo, tablaHash);
        if (cargados > 0) {
            this.archivoActual = archivo; // Guardamos la referencia del archivo abierto
        }
        return cargados;
    }
    
    public boolean guardarDatos(boolean nuevaEsLista, boolean nuevaEsDivision){
        if (tablaHash == null || archivoActual == null) {
            return false;
        }
        
        // 1. Guardamos en el CSV los cambios (inserciones/borrados) que tenga la tabla actual
        escritura.actualizarArchivo(archivoActual, tablaHash);
        
        // 2. Recreamos la tabla con la estructura y metodo hash seleccionados en la GUI
        tablaHash = new TablaHash(nuevaEsLista, nuevaEsDivision);
        
        // 3. Recargamos los registros para recalcular indices, nodos y colisiones
        int cargados = lectura.lecturaRegistros(archivoActual, tablaHash);
        
        // 4. Actualizamos el CSV para que quede con el orden de la nueva estructura (lista o pre-orden)
        escritura.actualizarArchivo(archivoActual, tablaHash);
        
        return cargados >= 0;
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
    
    // Las tres operaciones de abajo devuelven un valor neutro cuando todavia no hay
    // tabla, para que la GUI nunca reciba un NullPointerException.
    public void insertarRegistro(Registro registro){
        if(tablaHash != null) tablaHash.insertarRegistro(registro);
    }
    
    public boolean eliminarRegistro(String plate){
        return (tablaHash != null) && tablaHash.eliminar(plate);
    }
    
    public Registro buscarRegistro(String plate){
        return (tablaHash == null) ? null : tablaHash.buscar(plate);
    }
    
}
