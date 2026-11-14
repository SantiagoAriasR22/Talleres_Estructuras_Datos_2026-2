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
    
    // Devuelve true si el archivo quedo escrito, false si no se pudo.
    // Quien llama esta obligado a revisar el resultado: si se ignora, se puede
    // reportar "guardado" cuando en realidad no se escribio nada.
    public boolean actualizarArchivo(File archivo, TablaHash miTabla) {
        
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
                        escribirArbolPreOrden(arbolSlot.getRaiz(), bw);
                    }
                }
            }
            
            System.out.println("CSV file updated from the hash table.");
            return true;
            
        } catch (IOException e) {
            System.err.println("Error writing the CSV file: " + e.getMessage());
            return false;
        }
    }
    
    // Método auxiliar recursivo para recorrer el árbol de cada slot.
    // Se recorre en PRE-ORDEN (raíz, izquierdo, derecho) y no en in-orden, a propósito:
    // al recargar el CSV los registros se reinsertan en el orden del archivo, y la
    // secuencia pre-orden de un BST es la única que reproduce exactamente el mismo
    // árbol. Con in-orden la secuencia salía ascendente y cada guardado+recarga
    // degeneraba cada árbol en una cadena hacia la derecha.
    private void escribirArbolPreOrden(NodoArbol<Registro> nodo, BufferedWriter bw) throws IOException {
        if (nodo != null) {
            Registro reg = (Registro) nodo.getInformacion();
            bw.write(reg.toCSV());
            bw.newLine();

            escribirArbolPreOrden(nodo.getIzq(), bw);
            escribirArbolPreOrden(nodo.getDer(), bw);
        }
    }
    
}

