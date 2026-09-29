package Colas;

import java.time.LocalTime;

public class MensajeMQTT{

    int Id;
    String dispositivoId;
    String topic;
    String payload;
    LocalTime timeStamp;
    MensajeMQTT sig;

    public MensajeMQTT(int Id, String dispositivoId, String topic, String payload, LocalTime timeStamp, MensajeMQTT sig){

        this.Id=Id;
        this.dispositivoId=dispositivoId;
        this.topic=topic;
        this.payload=payload;
        this.timeStamp=timeStamp;
        this.sig=sig;
    }

    //getters
    public int getId(){ return Id; }
    public String getDispositivoId(){ return dispositivoId; }
    public String getTopic(){ return topic; }
    public String getPayload(){ return payload; }
    public LocalTime getTimeStamp(){ return timeStamp; }

}
