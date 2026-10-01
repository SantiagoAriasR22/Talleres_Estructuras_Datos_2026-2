package app;

import Colas.MensajeMQTT;
import Colas.Nodo;
import Colas.Cola;
import Colas.ServidorMQTT;

import java.time.LocalTime;

public class SimuladorMQTT {

    public static void main(String args[]){

        ServidorMQTT servidor = new ServidorMQTT();

        servidor.publicarMensaje(new MensajeMQTT(1, "S01", "iot/sensor01/temperatura", "28.5 °C", LocalTime.now(), null));

        servidor.publicarMensaje(new MensajeMQTT(2, "S02", "iot/sensor02/humedad", "76 %", LocalTime.now(), null));

        servidor.publicarMensaje(new MensajeMQTT(3, "S03", "iot/sensor03/nivel", "45 cm", LocalTime.now(), null));

        servidor.procesarMensaje();

        servidor.publicarMensaje(new MensajeMQTT(4, "S01", "iot/sensor01/temperatura", "29.1 °C", LocalTime.now(), null));

        servidor.procesarMensaje();

        servidor.procesarMensaje();

        servidor.procesarMensaje();

        servidor.procesarMensaje();

    }
}
