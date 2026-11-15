package modelo;
import java.util.ArrayList;

public class TablaHash{
    
    private final ArrayList<Object> tablaHash;
    private int colisiones=0;
    private final Integer m=256;
    private final boolean esLista;
    private final boolean esDivision;
    private final double A = (Math.sqrt(5)-1)/2;
    private boolean nodoBorrado;
    
    public TablaHash(boolean esLista, boolean esDivision){
        
        this.esLista=esLista;
        this.esDivision=esDivision;
        this.tablaHash=new ArrayList<>(m);
        
        //crea un arbol o una lista por cada slot, dependiendo de si esLista es true o false
        for(int i=0; i<m; i++){ 
            tablaHash.add(esLista ? new Lista<Registro>() : new Arbol());
        }
        
    }
    
    public void insertarRegistro(Registro registro){
        
        int k = getK(registro.getPlate());
        
        int indice = (esDivision) ? hashDivision(k) : hashMultiplicacion(k);
        
        if(esLista){
            
            Lista<Registro> listaSlot = (Lista<Registro>) this.tablaHash.get(indice);

            if(!listaSlot.estaVacia()){
                
                if(listaSlot.tamaño==1){
                    colisiones+=2;
                }
                else{
                    colisiones++;
                }
            }

            listaSlot.adicionarFinal(new NodoLista(registro));
            
        }
        else{
            
            Arbol<Registro> arbolSlot = (Arbol<Registro>) this.tablaHash.get(indice);
            
            if(arbolSlot.getRaiz()!=null){
                if(arbolSlot.getNumeroNodos()==1){
                    colisiones+=2;
                }
                else{
                    colisiones++;
                }
            }

            arbolSlot.adicionar(registro, k, arbolSlot.getRaiz(), 0);
            
        }
        
    }
    
    public boolean eliminar(String plate){
        
        int k = getK(plate);
        
        int indice = (esDivision) ? hashDivision(k) : hashMultiplicacion(k);
        
        if(esLista){
            
            Lista<Registro> listaSlot = (Lista<Registro>) this.tablaHash.get(indice);
            
            //si el slot vacio no hay nada que buscar
            if(listaSlot.estaVacia()) return false;
            
            NodoLista<Registro> anterior = null;
            NodoLista<Registro> actual = listaSlot.getHead();
            
            while(actual != null){
                
                Registro reg = (Registro) actual.getDato();
                
                if(getK(reg.getPlate()) == k && reg.getPlate().equals(plate)){
                    
                    if(anterior == null){
                        //si era el primero de la cadena, el head va a ser el que sigue
                        listaSlot.primerNodo = actual.getNext();
                    }
                    else{
                        //si esta en medio se salta el nodo
                        anterior.sig = actual.getNext();
                    }
                    
                    listaSlot.tamaño--;

                    if(listaSlot.tamaño == 1) colisiones -= 2;
                    else if(listaSlot.tamaño > 1) colisiones--;
                    
                    return true;
                }
                
                anterior = actual;
                actual = actual.getNext();
            }
            
            return false;
        }
        else{
            
            Arbol<Registro> arbolSlot = (Arbol<Registro>) this.tablaHash.get(indice);
            
            nodoBorrado = false;
            
            arbolSlot.raiz = eliminarEnArbol(arbolSlot.getRaiz(), k, plate);
            
            if(nodoBorrado){
                
                arbolSlot.nodos--;
                
                if(arbolSlot.nodos == 1) colisiones -= 2;
                else if(arbolSlot.nodos > 1) colisiones--;
            }
            
            return nodoBorrado;
        }
        
    }
    
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
    
    //busca en el subarbol el nodo cuya llave sea k y cuyo plate coincida, si la llave coincide pero la placa no es la buscada
    //todavia puede haber otro nodo con esa misma k mas a la derecha.
    private Registro buscarEnArbol(NodoArbol<Registro> nodo, int k, String plate){
        
        if(nodo == null) return null;
        
        long llave = nodo.getDato();
        
        if(k < llave) return buscarEnArbol(nodo.izquierdo, k, plate);
        
        if(k > llave) return buscarEnArbol(nodo.derecho, k, plate);
        
        Registro reg = (Registro) nodo.getInformacion();
        
        if(reg.getPlate().equals(plate)) return reg;
        
        return buscarEnArbol(nodo.derecho, k, plate);
    }
    
    private NodoArbol<Registro> eliminarEnArbol(NodoArbol<Registro> nodo, int k, String plate){
        
        if(nodo == null) return null;
        
        long llave = nodo.getDato();
        
        if(k < llave){
            nodo.izquierdo = eliminarEnArbol(nodo.izquierdo, k, plate);
        }
        else if(k > llave){
            nodo.derecho = eliminarEnArbol(nodo.derecho, k, plate);
        }
        else{
            
            Registro reg = (Registro) nodo.getInformacion();
            
            if(!nodoBorrado && reg.getPlate().equals(plate)){
                nodoBorrado = true;
                return desconectarNodo(nodo);
            }
            
            nodo.derecho = eliminarEnArbol(nodo.derecho, k, plate);
        }
        
        return nodo;
    }
    
    //saca el nodo del arbol segun tres casos
    private NodoArbol<Registro> desconectarNodo(NodoArbol<Registro> nodo){
        
        //caso 1 no tiene hijo izquierdo
        if(nodo.izquierdo == null) return nodo.derecho;
        //caso 2 no tiene hijo derecho
        if(nodo.derecho == null) return nodo.izquierdo;
        
        //en cualquiera de los casos anteriores el hijo que quede sube al lugar del nodo
        
        //caso 3, si tiene dos hijos se busca el sucesor in-orden, el menor del subarbol derecho,
        //se copia su contenido en este nodo y se saca el sucesor de donde estaba
        NodoArbol<Registro> padreSucesor = nodo;
        NodoArbol<Registro> sucesor = nodo.derecho;
        
        while(sucesor.izquierdo != null){
            padreSucesor = sucesor;
            sucesor = sucesor.izquierdo;
        }
        
        nodo.informacion = sucesor.getInformacion();
        nodo.datos = sucesor.getDato();
        
        if(padreSucesor == nodo){
            padreSucesor.derecho = sucesor.derecho;
        } else {
            padreSucesor.izquierdo = sucesor.derecho;
        }
        
        return nodo;
    }
    
    public int getK(String plate){
        
        int k=0;
        
        for(int i=plate.length()-1, j=0; i>=0; i--, j++){
            
            char c = plate.charAt(j);
            int ascii = (int) c;
            
            k+=(ascii*(1 << i));
                 
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
        return Math.floorMod(k, m);
    }
    
    public int hashMultiplicacion(int k){
        
        return (int) Math.floor(m*((k*A)%1));
    }
    
    public int getColisiones(){
        return colisiones;
    }
    
}
