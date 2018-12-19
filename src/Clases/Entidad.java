package Clases;

import java.util.ArrayList;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * Clase entidad
 * @author Cristobal Muñoz Salinas
 */
public class Entidad extends Elemento {
    private boolean debil;    
    private ArrayList<Atributo> atributos = new ArrayList<>();
    public Entidad(boolean debil,int posX,int posY, String nombre) {
        this.nombre=nombre;
        this.posX=posX;
        this.posY=posY;
        this.debil=debil;        
    }

    public void setDebil(boolean debil) {
        this.debil = debil;
    }

    public boolean isDebil() {
        return debil;
    }
   

    @Override
    public void setNombre(String nombre) {
        this.nombre = nombre;

        this.crearLineasunionAtributos();
    }    
    
    public void crearFigura(){
        this.posicionesX.clear();
        this.posicionesY.clear();
        this.dibujoFigura.clear();
        this.puntosDeControl.clear();
        
            int agrandar=nombre.length()*5;
            this.posicionesX.add(posX);
            this.posicionesY.add(posY);
            this.posicionesX.add(posX+80+agrandar);      
            this.posicionesY.add(posY);
            this.posicionesX.add(posX);
            this.posicionesY.add(posY+40);
            this.posicionesX.add(posX+80+agrandar);
            this.posicionesY.add(posY+40);
          
            
            this.dibujoFigura.add(new Line(posX, posY, posX+80+agrandar, posY));
            this.dibujoFigura.add(new Line(posX+80+agrandar, posY, posX+80+agrandar, posY+40));
            this.dibujoFigura.add(new Line(posX+80+agrandar, posY+40, posX, posY+40));
            this.dibujoFigura.add(new Line(posX, posY+40, posX, posY)); 
            
            if(debil){
                this.dibujoFigura.add(new Line(posX+3, posY+3, posX+80+agrandar-3, posY+3));
                this.dibujoFigura.add(new Line(posX+80+agrandar-3, posY+3, posX+80+agrandar-3, posY+40-3));
                this.dibujoFigura.add(new Line(posX+80+agrandar-3, posY+40+-3, posX+3, posY+40-3));
                this.dibujoFigura.add(new Line(posX+3, posY+40-3, posX+3, posY+3)); 
                            
            }
            
            Text textoFigura = new Text(posX,posY+20,nombre);
            textoFigura.setWrappingWidth(80+agrandar);
            textoFigura.setTextAlignment(TextAlignment.CENTER);            
            textoFigura.setFont(new Font(14));
            this.dibujoFigura.add(textoFigura);
            this.crearPuntosDeControl();
            this.crearLineasunionAtributos();
    }
    public void crearLineasunionAtributos(){        
        for(int i =0; i<atributos.size();i++){
            int ind = puntoCercano(i);
            Line linea = new Line(this.getPosicionesX().get(ind),this.getPosicionesY().get(ind),atributos.get(i).puntoMedioXFigura(),atributos.get(i).getPosY());
            this.dibujoFigura.add(linea);
        }
    } 
    private int puntoCercano(int indiceAtributo){
        int x1=atributos.get(indiceAtributo).puntoMedioXFigura();
        int y1 = atributos.get(indiceAtributo).getPosY();
        ArrayList<Integer> distancias = new ArrayList<>();
        ArrayList<Integer> orden = new ArrayList<>();
        int x2,y2;
        for(int i=0; i< this.getPosicionesX().size();i++){
            orden.add(i);
            x2 = this.getPosicionesX().get(i);
            y2 = this.getPosicionesY().get(i);
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
        return orden.get(0);
    }    
    public void agregarAtributo(Atributo nuevo){
        atributos.add(nuevo);
        
    }

    public ArrayList<Atributo> getAtributos() {
        return atributos;
    }
    @Override
    public void actualizarPosicion(int x, int y){
        this.posX=x;
        this.posY=y;
        this.crearFigura();
    }
}
