package modelo;
import java.util.ArrayList;

public class TablaHash{
    
    private final ArrayList<Object> tablaHash;
    private int colisiones=0;
    private final Integer m=256;
    private final boolean flag; //si flag es true es lista, de lo contrario es arbol
    
    public TablaHash(boolean flag){
        
        this.flag=flag;
        this.tablaHash=new ArrayList<>(m);
        
        for(int i=0; i<m; i++){ 
            
            if(flag){
                this.tablaHash.add(new Lista<Registro>());
            }
            else{
                this.tablaHash.add(new Arbol<Registro>());
            }
        }
        
    }
    
    public void insertarRegistro(Registro registro, boolean metodoHash){ //si metodoHash es true es division, de lo contrario es mutliplicacion
        
        int k = getK(registro.getPlate());
        
        int indice = (metodoHash) ? hashDivision(k) : hashMultiplicacion(k);
        
        if(flag){
            
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
