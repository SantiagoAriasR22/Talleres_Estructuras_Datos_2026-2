package Colas;

public class Cola<T>{

    Nodo<T> primerNodo;
    Nodo<T> ultimoNodo;
    int tamaño = 0;

    public Cola(){
        limpiar();
    }

    private void limpiar(){
        primerNodo = null;
        ultimoNodo = null;
        tamaño = 0;
    }

    public boolean estaVacia(){
        if (tamaño == 0) return true;
        else return false;
    }

    public T decolar(){

        T aux = null;

        if(!estaVacia()){
            aux = primerNodo.getDato();
            primerNodo = primerNodo.sig;
            tamaño--;
        }

        else{
            System.out.println("La Cola esta vacia, no se pueden retirar mensajes");
        }

        return aux;

    }

    public void encolar(Nodo<T> nodo){

        Nodo<T> nuevoNodo = nodo;

        if(estaVacia()){
            primerNodo = nuevoNodo;
            ultimoNodo = nuevoNodo;
        }
        else{
            ultimoNodo.sig = nodo;
            ultimoNodo= nodo;
        }

        tamaño++;

    }

}
