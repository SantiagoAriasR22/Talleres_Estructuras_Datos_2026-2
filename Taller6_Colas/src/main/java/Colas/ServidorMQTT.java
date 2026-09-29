package Colas;

public class ServidorMQTT{
    MensajeMQTT primerNodo;
    MensajeMQTT ultimoNodo;
    int tamaño = 0;

    public ServidorMQTT(){
        limpiar();
    }

    private void limpiar(){
        primerNodo = null;
        ultimoNodo = null;
        tamaño = 0;
    }

    public boolean estaVacia(){
        if (tamaño == 0)
            return true;
        else
            return false;
    }

    public MensajeMQTT decolar(){

        MensajeMQTT aux=null;

        if(!estaVacia()){
            aux = primerNodo;
            primerNodo = primerNodo.sig;
            tamaño--;
        }

        else System.out.println("La Cola esta vacia");

        return aux;

    }

    public void encolar(MensajeMQTT Nodo){

        MensajeMQTT nuevoNodo = Nodo;

        if(estaVacia()){
            primerNodo = nuevoNodo;
            ultimoNodo = nuevoNodo;
        }
        else{
            ultimoNodo.sig = Nodo;
            ultimoNodo= Nodo;
        }

        tamaño++;

        System.out.println("Se agrego correctamente un nuevo mensaje");
        System.out.println("ID: "+nuevoNodo.getId());
        System.out.println("Hora en la que se añadio a la cola: "+nuevoNodo.getTimeStamp());
    }

}
