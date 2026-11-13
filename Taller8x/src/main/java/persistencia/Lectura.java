package persistencia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import modelo.Registro;

public class Lectura {
    
    // Lee el csv y devuelve la lista de registros encontrados.
    // Devuelve null si no se pudo abrir el archivo (distinto de una lista vacia,
    // que significa "se abrio pero no tenia ninguna linea valida").
    // Ya no inserta en la tabla: de eso se encarga el Controlador, que necesita
    // quedarse con la lista para poder reconstruir la tabla despues.
    public List<Registro> leer(File archivo){
        
        List<Registro> registros = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            
            String linea;
            while ((linea = br.readLine()) != null) {

                String[] datos = linea.split(";"); 
                
                if (datos.length >= 4) {
                    registros.add(new Registro(datos[0].trim(), datos[1].trim(), datos[2].trim(), datos[3].trim()));
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
            return null;
        }
        
        return registros;
    }
}
