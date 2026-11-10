package modelo;

public class Registro {
    
    private String plate;
    private String firtsName;
    private String lastName;
    private String genero;
    
    public Registro(String plate, String firstName, String lastName, String genero){
        this.plate=plate;
        this.firtsName=firstName;
        this.lastName=lastName;
        this.genero=genero;
    }

    //getters
    
    public String getFirtsName() {
        return firtsName;
    }

    public String getPlate() {
        return plate;
    }

    public String getGenero() {
        return genero;
    }

    public String getLastName() {
        return lastName;
    }
    
}
