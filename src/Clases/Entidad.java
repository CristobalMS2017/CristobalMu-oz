package Clases;

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
    
    @Override
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
    }
}
