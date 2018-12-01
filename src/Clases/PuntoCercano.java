
package Clases;

import java.util.ArrayList;
/**
 * Clase PuntoCercano
 * Busca el punto cercano entre relaciones y entidades.
 * @author Cristobal Muñoz Salinas
 */
public class PuntoCercano {
    private ArrayList<Entidad> entidad ;
    private Figura figura;
    private ArrayList<Integer> ordenIndicesEntidades = new ArrayList<>();
    private ArrayList<Integer> ordenIndicesFigura = new ArrayList<>();


    public PuntoCercano(Figura figura, ArrayList<Entidad> entidades) {
        this.figura = figura;
        this.entidad=entidades;
        ordenarIndices();
    }

    public int pcEntidad(int indice){

        return ordenIndicesEntidades.get(indice);
    }
    public int pcFigura(int indice){
        return ordenIndicesFigura.get(indice);
    }    
    
    private Figura getFigura() {
        return figura;
    }

    public ArrayList<Integer> getOrdenIndicesEntidades() {
        return ordenIndicesEntidades;
    }

    public ArrayList<Integer> getOrdenIndicesFigura() {
        return ordenIndicesFigura;
    }
    
    private void ordenarIndices(){
        if (entidad.size()==1){
            int i1=puntoCercanoFigura(entidad.get(0).posicionesX(),entidad.get(0).posicionesY());
            puntoCercanoEntidad(i1,0);  
            i1=puntoCercanoFigura(entidad.get(0).posicionesX(),entidad.get(0).posicionesY());
            puntoCercanoEntidad(i1,0);   
        }
        else{
            for (int i = 0; i<entidad.size(); i++){
                int i1=puntoCercanoFigura(entidad.get(i).posicionesX(),entidad.get(i).posicionesY());
                puntoCercanoEntidad(i1,i);
            }         
        }

    }
    private boolean distinto(int indice){
            for(int i=0;i<ordenIndicesFigura.size();i++){
                if(ordenIndicesFigura.get(i)==indice){
                    return false;
                }
            }
            return true;            
    }
    
    private int puntoCercanoFigura(ArrayList<Integer> x,ArrayList<Integer> y){
        
        int x1 = x.get(0);
        int y1 = y.get(0);
        int x2,y2;
        ArrayList<Integer> distancias = new ArrayList<>();
        ArrayList<Integer> orden = new ArrayList<>();
        for(int i=0; i< this.getFigura().getPosicionesX().size();i++){
            orden.add(i);
                x2 = this.figura.getPosicionesX().get(i);
                y2 = this.figura.getPosicionesY().get(i); 
                distancias.add((int)Math.sqrt(((Math.pow(x1-x2, 2)+Math.pow(y2-y1, 2)))));
        }
        for(int w = 0; w<distancias.size(); w++){
            for(int i = 0; i<distancias.size()-1-w;i++){
                if (distancias.get(i+1) < distancias.get(i)){
                    int auxIndice = orden.get(i);
                    orden.set(i, orden.get(i+1));
                    orden.set(i+1, auxIndice);
                    
                    int aux= distancias.get(i);
                    distancias.set(i, distancias.get(i+1));
                    distancias.set(i+1, aux);
                    
                }


            }
        }
            for(int i = 0;i< orden.size();i++){
                if (distinto(orden.get(i))){
                    
                    this.ordenIndicesFigura.add(orden.get(i));
                    return orden.get(i);
                }
            }

            return orden.get(0);            


        
    }
    private void puntoCercanoEntidad(int indiceFigura, int numeroEntidad){
        int x1 = this.figura.getPosicionesX().get(indiceFigura);
        int y1 = this.figura.getPosicionesY().get(indiceFigura);
        int x2,y2;
        ArrayList<Integer> distancias = new ArrayList<>();
        ArrayList<Integer> orden = new ArrayList<>();
        for(int i=0; i< this.entidad.get(numeroEntidad).getFigura().getPosicionesX().size();i++){
            orden.add(i);
            x2 = this.entidad.get(numeroEntidad).getFigura().getPosicionesX().get(i);
            y2 = this.entidad.get(numeroEntidad).getFigura().getPosicionesY().get(i);
                distancias.add((int)Math.sqrt(((Math.pow(x1-x2, 2)+Math.pow(y2-y1, 2)))));
        }
                
        for(int x = 0; x<distancias.size(); x++){
            for(int i = 0; i<distancias.size()-1-x;i++){
                if (distancias.get(i+1) < distancias.get(i)){
                    int auxIndice = orden.get(i);
                    orden.set(i, orden.get(i+1));
                    orden.set(i+1, auxIndice);
                    
                    int aux= distancias.get(i);
                    distancias.set(i, distancias.get(i+1));
                    distancias.set(i+1, aux);
                    
                }


            }
        }        
        this.ordenIndicesEntidades.add(orden.get(0));               
    }     
    
    
    
}
