package app;


import Colas.MensajeMQTT;
import Colas.ServidorMQTT;

import java.time.LocalTime;

public class SimuladorMQTT {

    public static void main(String args[]){

        LocalTime horaActual = LocalTime.now();

        ServidorMQTT server = new ServidorMQTT();

        server.encolar(new MensajeMQTT(1, "S01", "iot/sensor01/temperatura", "23.5°C", horaActual, null));

    }
}
