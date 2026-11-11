package persistencia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import modelo.Registro;
import modelo.TablaHash;

public class Lectura {
    
    public void lecturaRegistros(File archivo, TablaHash tablaHash, boolean estructura){
        
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            
            String linea;
            while ((linea = br.readLine()) != null) {

                String[] datos = linea.split(";"); 
                
                if (datos.length >= 4) {
                    Registro nuevo = new Registro(datos[0].trim(), datos[1].trim(), datos[2].trim(), datos[3].trim());
                    
                    tablaHash.insertarRegistro(nuevo, estructura);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
        
    }
}
