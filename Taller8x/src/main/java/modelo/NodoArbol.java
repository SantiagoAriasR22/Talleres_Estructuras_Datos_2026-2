package modelo;

public class NodoArbol<T>{
    public long datos;
    public NodoArbol<T> izquierdo;
    public NodoArbol<T> derecho;
    
    public NodoArbol(long dato, NodoArbol<T> izq, NodoArbol<T> der){
	this.datos=dato;
	this.izquierdo=izq;
	this.derecho=der;
    }
    
    public NodoArbol(long dato){
	this.datos=dato;
	this.izquierdo=null;
	this.derecho=null;
    }
    
    public long getDato(){
	return this.datos;
    }
}
