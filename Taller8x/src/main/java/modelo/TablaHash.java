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
