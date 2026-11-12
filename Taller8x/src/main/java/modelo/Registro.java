package modelo;

public class Registro {
    
    private String plate;
    private String firstName;
    private String lastName;
    private String genero;
    
    public Registro(String plate, String firstName, String lastName, String genero){
        this.plate=plate;
        this.firstName=firstName;
        this.lastName=lastName;
        this.genero=genero;
    }

    //getters
    public String getFirtsName() {
        return firstName;
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
    //methods
    public String toCSV(){
        //se concatenan toda la informacion del registro en un solo string que va a ser retornando, siendo que cada valor esta separado por un ;
        String linea = plate+";"+firstName+";"+lastName+";"+genero;
        return linea;
    }
}
