package modelo;

import java.util.List;

/**
 * Lo que la vista necesita para dibujar un slot de la tabla hash.
 *
 * Si esLista es true, el contenido esta en "placas" (en el orden de la cadena).
 * Si es false, el contenido es el arbol binario, y se entrega la raiz para que
 * la vista la recorra y la dibuje.
 *
 * @author W11
 */
public class SlotVista {
    
    public final int indice;
    public final boolean esLista;
    public final List<String> placas;
    public final NodoArbol<Registro> raiz;
    
    public SlotVista(int indice, boolean esLista, List<String> placas, NodoArbol<Registro> raiz) {
        this.indice = indice;
        this.esLista = esLista;
        this.placas = placas;
        this.raiz = raiz;
    }
}
