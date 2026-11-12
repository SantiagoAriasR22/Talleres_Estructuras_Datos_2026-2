package modelo;

public class NodoLista<T>{
    
    T dato;
    NodoLista<T> sig;
    
    public NodoLista(T dato){
        this.dato=dato;
	this.sig=null;
    }
	
    public NodoLista(T dato, NodoLista<T> sig){
	this.dato=dato;
	this.sig=sig;
    }
	
    public T getDato(){
	return this.dato;
    }
    
    public NodoLista getNext(){
        return sig;
    }
}
