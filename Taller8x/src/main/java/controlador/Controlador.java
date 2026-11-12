package controlador;

import java.io.File;
import modelo.TablaHash;
import persistencia.Lectura;

public class Controlador {
    
    private TablaHash tablaHash;
    private Lectura lectura = new Lectura();
    
    public Controlador(){}
    
    public void crearTabla(boolean esLista, boolean esDivision){
        tablaHash = new TablaHash(esLista, esDivision);
    }
    
    public void cargarDatos(File archivo){ 
        lectura.lecturaRegistros(archivo, tablaHash);
    }
    
    public void cargarRegistroGUI(){
        //aca va la logica para cuando se vayan a pasar los registros a la GUI por medio de lectura (seria lo ideal)
    }
    
    public int cargarColisiones(){
        return (tablaHash==null) ? 0 : tablaHash.getColisiones();
    }
    
}
