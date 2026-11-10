package modelo;

public class NodoArbol<T>{
    public T informacion;
    public long datos;
    public NodoArbol<T> izquierdo;
    public NodoArbol<T> derecho;
    
    public NodoArbol(T informacion, long dato, NodoArbol<T> izq, NodoArbol<T> der){
	this.datos=dato;
	this.izquierdo=izq;
	this.derecho=der;
        this.informacion=informacion;
    }
    
    public NodoArbol(T informacion, long dato){
	this.datos=dato;
        this.informacion=informacion;
	this.izquierdo=null;
	this.derecho=null;
    }
    
    public long getDato(){
	return this.datos;
    }

    public T getInformacion() {
        return informacion;
    }
    
}
