package modelo;

public class main {
    
    public static void main(String[] args) {
        
        TablaHash tabla = new TablaHash(true);
        
        int radix=tabla.hashMultiplicacion(50770);
        System.out.println(radix);
        
    }
}
