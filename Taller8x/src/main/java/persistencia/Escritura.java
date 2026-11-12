package persistencia;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import modelo.Arbol;
import modelo.Lista;
import modelo.NodoArbol;
import modelo.NodoLista;
import modelo.Registro;
import modelo.TablaHash;

public class Escritura {
    
    //aqui se guarda el cambio en el documento directamente aunque no le de a save(primera idea)
    public void saveInsert (File archivo, Registro registro){
        
        
        //si el FileWriter recibe como segundo parametro un true, escribe al final del archivo
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true))){
            
            bw.write(registro.toCSV());
            bw.newLine();
                         
            System.out.println("Insercion completada(ha sido guardada)");
            
        }
        catch(Exception e){
            System.out.println("Error en la escritura del archivo csv: "+e.getMessage()); //getMessage() es un metodo que extrae un mensaje corto explicando la causa directa del error
            e.printStackTrace(); //muestra una lista de archivos y lineas de codigo organizados de manera inversa, que me ayudan a identificar en donde se rompio el programa, mas exactamente en la linea 1
        }
    }
    
    
    public void actualizarArchivo(File archivo, TablaHash miTabla) {
        
        // false para sobrescribir el archivo completo con los datos actualizados
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, false))) {
            
            for (int i = 0; i < miTabla.getM(); i++) {
                
                if (miTabla.isEsLista()) {
                    Lista<Registro> listaSlot = (Lista<Registro>) miTabla.getTablaHash().get(i);
                    
                    if (!listaSlot.estaVacia()) {
                        
                        NodoLista<Registro> actual = listaSlot.getHead(); 
                        while (actual != null) {
                            
                            Registro reg = (Registro) actual.getDato(); 
                            bw.write(reg.toCSV());
                            bw.newLine();
                            actual = actual.getNext();
                        }
                    }
                } else {
                    Arbol<Registro> arbolSlot = (Arbol<Registro>) miTabla.getTablaHash().get(i);
                    
                    if (arbolSlot.getRaiz() != null) {
                        escribirArbolInOrden(arbolSlot.getRaiz(), bw);
                    }
                }
            }
            
            System.out.println("Archivo CSV actualizado correctamente desde la Tabla Hash.");
            
        } catch (IOException e) {
            System.out.println("Error al actualizar el archivo csv: " + e.getMessage());
            e.printStackTrace();
        }
    }
    // Método auxiliar recursivo para recorrer el árbol de cada slot
    private void escribirArbolInOrden(NodoArbol<Registro> nodo, BufferedWriter bw) throws IOException {
        if (nodo != null) {
            escribirArbolInOrden(nodo.getIzq(), bw);
            
            
            Registro reg = (Registro) nodo.getInformacion(); 
            bw.write(reg.toCSV());
            bw.newLine();
            
            escribirArbolInOrden(nodo.getDer(), bw);
        }
    }
    
    
    
    }

