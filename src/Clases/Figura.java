package Clases;

import java.util.ArrayList;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * Creación de figuras y puntos de control de las relaciones y entidades
 * 
 * @author Cristobal Muñoz Salinas
 */
public class Figura {

    private ArrayList<Line> lineas = new ArrayList<>(); // guarda las lineas creadas.
    
    private ArrayList<Integer> posicionesX = new ArrayList<>(); //posiciones x de la figura.
    private ArrayList<Integer> posicionesY = new ArrayList<>(); //posiciones y de la figura.
    private ArrayList<Line> puntosControl = new ArrayList<>(); //puntos de control de la figura.

    private int puntoMedioX,puntoMedioY;
    private Text textoFigura;
    
    private ArrayList<Integer> puntosOcupados= new ArrayList<>();
    public void crearFigura(boolean debil,int tipo,String nombre, int posX,int posY){

        if (tipo == -1){//Rectangulo
            int agrandar=nombre.length()*5;
            posicionesX.add(posX);
            posicionesY.add(posY);
            posicionesX.add(posX+80+agrandar);      
            posicionesY.add(posY);
            posicionesX.add(posX);
            posicionesY.add(posY+40);
            posicionesX.add(posX+80+agrandar);
            posicionesY.add(posY+40);
            
            puntoMedioX = posX+(80+agrandar)/2;
            puntoMedioY = posY+20;

            

           
            lineas.add(new Line(posX, posY, posX+80+agrandar, posY));
            lineas.add(new Line(posX+80+agrandar, posY, posX+80+agrandar, posY+40));
            lineas.add(new Line(posX+80+agrandar, posY+40, posX, posY+40));
            lineas.add(new Line(posX, posY+40, posX, posY)); 
            
            if(debil){
                lineas.add(new Line(posX+3, posY+3, posX+80+agrandar-3, posY+3));
                lineas.add(new Line(posX+80+agrandar-3, posY+3, posX+80+agrandar-3, posY+40-3));
                lineas.add(new Line(posX+80+agrandar-3, posY+40+-3, posX+3, posY+40-3));
                lineas.add(new Line(posX+3, posY+40-3, posX+3, posY+3)); 
                            
            }

            crearPuntosDeControl();
            
            textoFigura = new Text(posX,puntoMedioY,nombre);
            textoFigura.setWrappingWidth(80+agrandar);
            textoFigura.setTextAlignment(TextAlignment.CENTER);            
            textoFigura.setFont(new Font(14));
            
            
            
            
            

        }
        if(tipo == 2 || tipo == 1){//Rombo
            
            int agrandar=nombre.length()*4;
            puntoMedioX = posX+(50+agrandar)/2;
            puntoMedioY = posY;             
            int sumarY = (50+agrandar)/2;
           
            posicionesX.add(posX);
            posicionesY.add(posY);
            posicionesX.add(posX+50+agrandar);      
            posicionesY.add(posY);
            posicionesX.add(puntoMedioX);
            posicionesY.add(posY-sumarY);
            posicionesX.add(puntoMedioX);
            posicionesY.add(posY+sumarY);


            lineas.add(new Line(posX, posY, puntoMedioX, posY-sumarY));
            lineas.add(new Line(puntoMedioX, posY-sumarY, posX+50+agrandar, posY));
            lineas.add(new Line(posX+50+agrandar, posY, puntoMedioX, posY+sumarY));
            lineas.add(new Line(puntoMedioX, posY+sumarY, posX, posY));    
            
            
            if(debil==true){
                lineas.add(new Line(posX+3, posY, puntoMedioX, posY-sumarY+3));
                lineas.add(new Line(puntoMedioX, posY-sumarY+3, posX+50+agrandar-3, posY));
                lineas.add(new Line(posX+50+agrandar-3, posY, puntoMedioX, posY+sumarY-3));
                lineas.add(new Line(puntoMedioX, posY+sumarY-3, posX+3, posY));  
            }
            
            crearPuntosDeControl();
            textoFigura = new Text(posX,puntoMedioY,nombre);
            textoFigura.setWrappingWidth(50+agrandar);
            textoFigura.setTextAlignment(TextAlignment.CENTER);  
            textoFigura.setFont(new Font(14)); 
           
        }         
    }
    private void crearPuntosDeControl(){
        for(int i=0; i<posicionesX.size();i++){
            Line linea = new Line(posicionesX.get(i), posicionesY.get(i), posicionesX.get(i), posicionesY.get(i));
            linea.setStrokeWidth(7);
            puntosControl.add(linea);
        }
        
    }
    public ArrayList<Line> getLineas() {
        return lineas;
    }

    public void setLineas(ArrayList<Line> lineas) {
        this.lineas = lineas;
    }

    public ArrayList<Integer> getPosicionesX() {
        return posicionesX;
    }

    public void setPosicionesX(ArrayList<Integer> posicionesX) {
        this.posicionesX = posicionesX;
    }

    public ArrayList<Integer> getPosicionesY() {
        return posicionesY;
    }

    public void setPosicionesY(ArrayList<Integer> posicionesY) {
        this.posicionesY = posicionesY;
    }

    public int getPuntoMedioX() {
        return puntoMedioX;
    }

    public void setPuntoMedioX(int puntoMedioX) {
        this.puntoMedioX = puntoMedioX;
    }

    public int getPuntoMedioY() {
        return puntoMedioY;
    }

    public void setPuntoMedioY(int puntoMedioY) {
        this.puntoMedioY = puntoMedioY;
    }

    public ArrayList<Line> getPuntosControl() {
        return puntosControl;
    }

    public void setPuntosControl(ArrayList<Line> puntosControl) {
        this.puntosControl = puntosControl;
    }

    public Text getTextoFigura() {
        return textoFigura;
    }

    public void setTextoFigura(Text textoFigura) {
        this.textoFigura = textoFigura;
    }
    public void anadirPuntosOcupados(int i){
        this.puntosOcupados.add(i);
    }

    public ArrayList<Integer> getPuntosOcupados() {
        return puntosOcupados;
    }
    
}
