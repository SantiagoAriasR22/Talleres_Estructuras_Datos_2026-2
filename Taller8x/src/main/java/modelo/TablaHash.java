package modelo;
import java.util.ArrayList;

public class TablaHash{
    
    private final ArrayList<Object> tablaHash;
    private int colisiones=0;
    private final Integer m=256;
    private final boolean esLista;
    private final boolean esDivision;
    
    public TablaHash(boolean esLista, boolean esDivision){
        
        this.esLista=esLista;
        this.esDivision=esDivision;
        this.tablaHash=new ArrayList<>(m);
        
        for(int i=0; i<m; i++){ 
            tablaHash.add(esLista ? new Lista<Registro>() : new Arbol());
        }
        
    }
    
    public void insertarRegistro(Registro registro){
        
        int k = getK(registro.getPlate());
        
        int indice = (esDivision) ? hashDivision(k) : hashMultiplicacion(k);
        
        if(esLista){
            
            Lista<Registro> listaSlot = (Lista<Registro>) this.tablaHash.get(indice);
            
            if(!listaSlot.estaVacia()) colisiones++;
            
            listaSlot.adicionarFinal(new NodoLista(registro));
            
        }
        else{
            
            Arbol<Registro> arbolSlot = (Arbol<Registro>) this.tablaHash.get(indice);
            
            if(arbolSlot.getRaiz()!=null) colisiones++;

            arbolSlot.adicionar(registro, k, arbolSlot.getRaiz(), 0);
            
        }
        
    }
    
    public boolean eliminar(String plate){
        
        int k = getK(plate);
        
        int indice = (esDivision) ? hashDivision(k) : hashMultiplicacion(k);
        
        if(esLista){
            
            Lista<Registro> listaSlot = (Lista<Registro>) this.tablaHash.get(indice);
            
            // Slot vacio: no hay nada que buscar, el plate no puede estar.
            if(listaSlot.estaVacia()) return false;
            
            NodoLista<Registro> anterior = null;
            NodoLista<Registro> actual = listaSlot.getHead();
            
            // Si el slot tiene un solo nodo, el while hace una sola comparacion y sale:
            // no hay cadena que recorrer. Con mas nodos, avanza con 'anterior' para
            // poder reenlazar cuando encuentre el que hay que sacar.
            while(actual != null){
                
                Registro reg = (Registro) actual.getDato();
                
                if(reg.getPlate().equals(plate)){
                    
                    if(anterior == null){
                        // Era el primero de la cadena: la cabeza pasa al siguiente.
                        listaSlot.primerNodo = actual.getNext();
                    }
                    else{
                        // Estaba en medio o al final: se salta el nodo.
                        anterior.sig = actual.getNext();
                    }
                    
                    // Se mantiene sincronizado con estaVacia(), que mira tamaño.
                    listaSlot.tamaño--;
                    
                    // Colisiones = registros - slots ocupados. Al sacar un registro:
                    //   - si el slot queda con otros, los ocupados no cambian y se
                    //     pierde una colision, entonces baja
                    //   - si el slot queda vacio, bajan los registros y los ocupados
                    //     a la vez, entonces el numero de colisiones no cambia
                    if(!listaSlot.estaVacia()) colisiones--;
                    
                    return true;
                }
                
                anterior = actual;
                actual = actual.getNext();
            }
            
            // Se recorrio el slot completo y el plate no estaba.
            return false;
        }
        else{
            
            Arbol<Registro> arbolSlot = (Arbol<Registro>) this.tablaHash.get(indice);
            
            boolean[] borrado = new boolean[1];
            
            arbolSlot.raiz = eliminarEnArbol(arbolSlot.getRaiz(), k, plate, borrado);
            
            if(borrado[0]){
                
                // nodos lo lleva el Arbol para su getNumeroNodos(); se mantiene al dia.
                arbolSlot.nodos--;
                
                // Misma regla que en la lista: si el slot sigue teniendo registros
                // se pierde una colision; si quedo vacio, el numero no cambia.
                if(arbolSlot.getRaiz() != null) colisiones--;
            }
            
            return borrado[0];
        }
        
    }
    
    // Busca un registro por su plate. Devuelve el Registro, o null si no esta.
    // Igual que en eliminar: se recalcula el indice a partir del plate y solo se
    // mira el slot que le corresponde.
    public Registro buscar(String plate){
        
        int k = getK(plate);
        
        int indice = (esDivision) ? hashDivision(k) : hashMultiplicacion(k);
        
        if(esLista){
            
            Lista<Registro> listaSlot = (Lista<Registro>) this.tablaHash.get(indice);
            
            for(NodoLista<Registro> actual = listaSlot.getHead(); actual != null; actual = actual.getNext()){
                
                Registro reg = (Registro) actual.getDato();
                
                if(reg.getPlate().equals(plate)) return reg;
            }
            
            return null;
        }
        else{
            
            Arbol<Registro> arbolSlot = (Arbol<Registro>) this.tablaHash.get(indice);
            
            return buscarEnArbol(arbolSlot.getRaiz(), k, plate);
        }
        
    }
    
    // Busca en el subarbol el nodo cuya llave sea k y cuyo plate coincida.
    // Misma regla que en el borrado: si la llave coincide pero la placa no es la
    // buscada, todavia puede haber otro nodo con esa misma k mas a la derecha.
    private Registro buscarEnArbol(NodoArbol<Registro> nodo, int k, String plate){
        
        if(nodo == null) return null;
        
        long llave = nodo.getDato();
        
        if(k < llave) return buscarEnArbol(nodo.izquierdo, k, plate);
        
        if(k > llave) return buscarEnArbol(nodo.derecho, k, plate);
        
        Registro reg = (Registro) nodo.getInformacion();
        
        if(reg.getPlate().equals(plate)) return reg;
        
        return buscarEnArbol(nodo.derecho, k, plate);
    }
    
    // Elimina del subarbol el nodo cuya llave sea k Y cuyo plate coincida.
    // Devuelve la raiz del subarbol ya corregida (puede cambiar si se borra la raiz).
    //
    // Ojo con las llaves repetidas: adicionar() manda las iguales a la derecha, asi que
    // cuando la llave coincide pero la placa NO es la buscada, todavia puede haber otro
    // nodo con esa misma k mas a la derecha. Por eso en ese caso se sigue por la derecha
    // en vez de cortar la busqueda.
    private NodoArbol<Registro> eliminarEnArbol(NodoArbol<Registro> nodo, int k, String plate, boolean[] borrado){
        
        if(nodo == null) return null;
        
        long llave = nodo.getDato();
        
        if(k < llave){
            nodo.izquierdo = eliminarEnArbol(nodo.izquierdo, k, plate, borrado);
        }
        else if(k > llave){
            nodo.derecho = eliminarEnArbol(nodo.derecho, k, plate, borrado);
        }
        else{
            
            Registro reg = (Registro) nodo.getInformacion();
            
            if(!borrado[0] && reg.getPlate().equals(plate)){
                borrado[0] = true;
                return desconectarNodo(nodo);
            }
            
            nodo.derecho = eliminarEnArbol(nodo.derecho, k, plate, borrado);
        }
        
        return nodo;
    }
    
    // Saca el nodo del arbol con los tres casos clasicos del BST.
    private NodoArbol<Registro> desconectarNodo(NodoArbol<Registro> nodo){
        
        // Caso 1 y 2: no tiene hijo izquierdo, o no tiene hijo derecho.
        // En los dos, el hijo que quede sube al lugar del nodo.
        if(nodo.izquierdo == null) return nodo.derecho;
        if(nodo.derecho == null) return nodo.izquierdo;
        
        // Caso 3: dos hijos. Se busca el sucesor in-orden (el menor del subarbol derecho),
        // se copia su contenido en este nodo y se saca el sucesor de donde estaba.
        NodoArbol<Registro> sucesor = nodo.derecho;
        
        while(sucesor.izquierdo != null) sucesor = sucesor.izquierdo;
        
        nodo.informacion = sucesor.getInformacion();
        nodo.datos = sucesor.getDato();
        nodo.derecho = quitarMenor(nodo.derecho);
        
        return nodo;
    }
    
    private NodoArbol<Registro> quitarMenor(NodoArbol<Registro> nodo){
        
        if(nodo.izquierdo == null) return nodo.derecho;
        
        nodo.izquierdo = quitarMenor(nodo.izquierdo);
        
        return nodo;
    }
    
    public int getK(String plate){
        
        int k=0;
        
        for(int i=plate.length()-1, j=0; i>=0; i--, j++){
            
            char c = plate.charAt(j);
            int ascii = (int) c;
            
            k+=(ascii*(Math.pow(2, i)));
                 
        }
        
        return k;
        
    }
    public ArrayList<Object> getTablaHash() {
        return tablaHash;
    }

    public boolean isEsLista() {
        return esLista;
    }

    public int getM() {
        return m;
    }
    
    public int hashDivision(int k){
        return k%m;
    }
    
    public int hashMultiplicacion(int k){
        
        double A = (Math.sqrt(5)-1)/2;
        return (int) Math.floor(m*((k*A)%1));
    }
    
    public int getColisiones(){
        return colisiones;
    }
    
}
