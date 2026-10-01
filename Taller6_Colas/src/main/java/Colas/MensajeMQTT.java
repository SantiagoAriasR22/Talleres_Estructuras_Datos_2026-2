package Colas;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class MensajeMQTT{

    int Id;
    String dispositivoId;
    String topic;
    String payload;
    LocalTime timeStamp;
    MensajeMQTT sig;

    private DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm:ss");

    public MensajeMQTT(int Id, String dispositivoId, String topic, String payload, LocalTime timeStamp, MensajeMQTT sig){

        this.Id=Id;
        this.dispositivoId=dispositivoId;
        this.topic=topic;
        this.payload=payload;
        this.timeStamp=timeStamp;
        this.sig=sig;
    }

    public String toResumen(){
        return "ID mensaje: "+this.Id+" | "+
                "ID dispositivo: "+this.dispositivoId+" | "+
                "Topic: "+this.topic+" | "+
                "Payload: "+this.payload+" | "+
                "TimeStamp: "+getTimeStamp();
    }

    //getters
    public int getId(){ return Id; }
    public String getDispositivoId(){ return dispositivoId; }
    public String getTopic(){ return topic; }
    public String getPayload(){ return payload; }
    public String getTimeStamp(){ return timeStamp.format(formato); }

}
