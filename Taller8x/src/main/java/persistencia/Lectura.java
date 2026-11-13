package persistencia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import modelo.Registro;
import modelo.TablaHash;

public class Lectura {
    
    // Lee el csv y devuelve cuantos registros se insertaron.
    // Devuelve -1 si hubo un error de lectura, para que la GUI pueda distinguir
    // "el archivo no tiene el formato" de "no se pudo abrir".
    public int lecturaRegistros(File archivo, TablaHash tablaHash){
        
        int cargados = 0;
        
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            
            String linea;
            while ((linea = br.readLine()) != null) {

                String[] datos = linea.split(";"); 
                
                if (datos.length >= 4) {
                    Registro nuevo = new Registro(datos[0].trim(), datos[1].trim(), datos[2].trim(), datos[3].trim());
                    
                    tablaHash.insertarRegistro(nuevo);
                    cargados++;
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
            return -1;
        }
        
        return cargados;
    }
}
