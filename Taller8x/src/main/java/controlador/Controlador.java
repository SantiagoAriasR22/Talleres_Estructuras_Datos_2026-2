package controlador;

import java.io.File;
import modelo.Registro;
import modelo.TablaHash;
import persistencia.Lectura;

public class Controlador {
    
    private TablaHash tablaHash;
    private Lectura lectura = new Lectura();
    
    public Controlador(){}
    
    public void crearTabla(boolean esLista, boolean esDivision){
        tablaHash = new TablaHash(esLista, esDivision);
    }
    
    public int cargarDatos(File archivo){ 
        return (tablaHash == null) ? 0 : lectura.lecturaRegistros(archivo, tablaHash);
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
