package Colas;

public class ServidorMQTT {

    Cola cola = new Cola<>();

    public void publicarMensaje(MensajeMQTT mensaje){

        cola.encolar(new Nodo(mensaje));

        System.out.println("El mensaje se agrego a la cola con exito");
        System.out.println("Informacion del mensaje: ");
        System.out.print("ID del mensaje: "+mensaje.getId()+" | ");
        System.out.print("ID del sensor: "+mensaje.getDispositivoId()+" | ");
        System.out.println("Hora de publicacion: "+mensaje.getTimeStamp());
        System.out.println("Mensajes actuales en cola: "+cola.tamaño);
    }

    public void procesarMensaje(){

        MensajeMQTT mensaje = (MensajeMQTT) cola.decolar();

        if(mensaje!=null){
            System.out.println("El mensaje salio de la cola con exito");
            System.out.println("Informacion del mensaje: ");
            System.out.println(mensaje.toResumen());
            System.out.println("Mensajes restantes en cola: "+(cola.estaVacia() ? "No hay elementos en cola" : cola.tamaño));
        }

    }
}
