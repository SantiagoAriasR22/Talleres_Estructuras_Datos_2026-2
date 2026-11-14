package persistencia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import modelo.Registro;

public class Lectura {
    
    //lee el csv y devuelve la lista de registros encontrados
    //devuelve null si no se pudo abrir el archivo
    public ArrayList<Registro> leer(File archivo){
        
        ArrayList<Registro> registros = new ArrayList<>();
        
        try(BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            
            String linea;
            while((linea = br.readLine()) != null){

                String[] datos = linea.split(";"); 
                
                if (datos.length >= 4) {
                    registros.add(new Registro(datos[0].trim(), datos[1].trim(), datos[2].trim(), datos[3].trim()));
                }
            }
        }catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            return null;
        }
        
        return registros;
    }
}
