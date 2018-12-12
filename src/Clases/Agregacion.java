/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author Cristobal Andres
 */
public class Agregacion extends Elemento {
    private Relacion relacion;
    public Agregacion(Relacion relacion, String nombre, int posX, int posY) {
        this.relacion = relacion;
        this.nombre = nombre;
        this.posX = posX;
        this.posY = posY;
        
    }    

    @Override
    public void crearFigura(){
        this.dibujoFigura.clear();
        this.posicionesX.clear();
        this.posicionesY.clear();
        this.puntosDeControl.clear();
         
                this.posicionesX.clear();
                this.posicionesY.clear();

                int tamanoxElemento1 = this.relacion.getElementos().get(0).getPosicionesX().get(1)-this.relacion.getElementos().get(0).getPosicionesX().get(0);
                int tamanoxElemento2 = this.relacion.getElementos().get(1).getPosicionesX().get(1)-this.relacion.getElementos().get(1).getPosicionesX().get(0);
                int tamanoxRelacion = this.getRelacion().getFigura().getPosicionesX().get(1)-this.getRelacion().getFigura().getPosicionesX().get(0);
                int distanciaMediaRelacion = this.relacion.getFigura().getPosicionesY().get(0)-this.relacion.getFigura().getPosicionesY().get(2);
                relacion.actualizarPosicion(posX+20+tamanoxElemento1+100, posY+distanciaMediaRelacion+30);
                int distancia = relacion.getElementos().get(0).getPosicionesX().get(1)-relacion.getElementos().get(0).getPosicionesX().get(0);
                relacion.getElementos().get(0).actualizarPosicion(posX+20, posY+distanciaMediaRelacion+10);
                relacion.getElementos().get(1).actualizarPosicion(posX+20+tamanoxElemento1+100+tamanoxRelacion+100, posY+distanciaMediaRelacion+10);
                relacion.crearRelacion();
                relacion.crearLineasunionAtributos();
                
                Elemento elemento1 = relacion.getElementos().get(0);
                Elemento elemento2 = relacion.getElementos().get(1);


                int posx1 = posX;
                int posy1 = posY;
                int posy2;
                if(elemento1.getPosicionesY().get(2)<=elemento2.getPosicionesY().get(2)){
                    if(this.relacion.getFigura().getPosicionesY().get(3)<=elemento2.getPosicionesY().get(2)){
                        posy2=elemento2.getPosicionesY().get(2)+20;
                    }
                    else{
                        posy2=this.relacion.getFigura().getPosicionesY().get(3)+20;
                    }
                }
                else{
                    if(this.relacion.getFigura().getPosicionesY().get(3)<=elemento1.getPosicionesY().get(2)){
                        posy2=elemento1.getPosicionesY().get(2)+20;
                    }
                    else{
                        posy2=this.relacion.getFigura().getPosicionesY().get(3)+20;
                    }
                }
                
                
                int posx2 = elemento2.getPosicionesX().get(1)+20;

                this.dibujoFigura.add(new Line(posx1,posy1,posx2,posy1));
                this.dibujoFigura.add(new Line(posx1,posy2,posx2,posy2));
                this.dibujoFigura.add(new Line(posx1,posy1,posx1,posy2));
                this.dibujoFigura.add(new Line(posx2,posy1,posx2,posy2));
                this.posicionesX.add(posx1);
                this.posicionesY.add(posy1);
                this.posicionesX.add(posx2);
                this.posicionesY.add(posy1);
                this.posicionesX.add(posx1);
                this.posicionesY.add(posy2);
                this.posicionesX.add(posx2);
                this.posicionesY.add(posy2);       
                this.crearPuntosDeControl();
                    Text textoFigura = new Text(posx1,posy1+20,nombre);
                    textoFigura.setWrappingWidth(posx2-posx1);
                    textoFigura.setTextAlignment(TextAlignment.CENTER);            
                    textoFigura.setFont(new Font(14));        
                    this.dibujoFigura.add(textoFigura);
            }                                              
    public Relacion getRelacion() {
        return relacion;
    }

   
}
