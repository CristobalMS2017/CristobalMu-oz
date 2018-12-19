/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.util.ArrayList;
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
        ordenarElementos();
        
    }    
    private void ordenarElementos(){
        ArrayList<Integer> sumar = buscarElementosBordes(true);
        
        relacion.actualizarPosicion((int)(relacion.getPosX()+sumar.get(0)), (int)(relacion.getPosY()+sumar.get(1)));
        for(int i =0 ; i<relacion.getElementos().size();i++){
            relacion.getElementos().get(i).actualizarPosicion(
                    (int)(relacion.getElementos().get(i).getPosX()+sumar.get(0)),
                    (int)(relacion.getElementos().get(i).getPosY()+sumar.get(1)));
                    
            if(relacion.getElementos().get(i) instanceof Entidad){
                for(int j = 0; j<((Entidad)relacion.getElementos().get(i)).getAtributos().size();j++){
                    double x=((Entidad)relacion.getElementos().get(i)).getAtributos().get(j).getPosX()+sumar.get(0);
                    double y =((Entidad)relacion.getElementos().get(i)).getAtributos().get(j).getPosY()+sumar.get(1);
                    ((Entidad)relacion.getElementos().get(i)).getAtributos().get(j).actualizarPosicion((int)x,(int)y);
                    
                    for(int k=0;k<((Entidad)relacion.getElementos().get(i)).getAtributos().get(j).getAtributos().size();k++){
                        x=((Entidad)relacion.getElementos().get(i)).getAtributos().get(j).getAtributos().get(k).getPosX()+sumar.get(0);
                        y=((int)((Entidad)relacion.getElementos().get(i)).getAtributos().get(j).getAtributos().get(k).getPosY()+sumar.get(1));
                       ((Entidad)relacion.getElementos().get(i)).getAtributos().get(j).getAtributos().get(k).actualizarPosicion((int)x,(int)y); 
                    }
                
                
                }
            }
            relacion.crearRelacion();
            
        }        
    }
    @Override
    public void crearFigura(){
        this.dibujoFigura.clear();
        this.posicionesX.clear();
        this.posicionesY.clear();
        this.puntosDeControl.clear();
       
        ArrayList<Integer> puntosFinales = this.buscarElementosBordes(false);
        Line linea=new Line(posX,posY,puntosFinales.get(0),posY);
        linea.getStrokeDashArray().add(6D);
        this.dibujoFigura.add(linea);
        linea=new Line(posX,posY,posX,puntosFinales.get(1));
        linea.getStrokeDashArray().add(6D);
        this.dibujoFigura.add(linea); 
        linea=new Line(puntosFinales.get(0),posY,puntosFinales.get(0),puntosFinales.get(1));
        linea.getStrokeDashArray().add(6D);
        this.dibujoFigura.add(linea);
        linea=new Line(posX,puntosFinales.get(1),puntosFinales.get(0),puntosFinales.get(1));
        linea.getStrokeDashArray().add(6D);

        this.dibujoFigura.add(linea);         
 
                this.posicionesX.add(posX);
                this.posicionesY.add(posY);
                this.posicionesX.add(puntosFinales.get(0));
                this.posicionesY.add(posY);
                this.posicionesX.add(posX);
                this.posicionesY.add(puntosFinales.get(1));
                this.posicionesX.add(puntosFinales.get(0));
                this.posicionesY.add(puntosFinales.get(1));       
                this.crearPuntosDeControl();
                    Text textoFigura = new Text(posX,posY+20,nombre);
                    textoFigura.setWrappingWidth(puntosFinales.get(0)-posX);
                    textoFigura.setTextAlignment(TextAlignment.CENTER);            
                    textoFigura.setFont(new Font(14));        
                    this.dibujoFigura.add(textoFigura);
            }                                              
    public Relacion getRelacion() {
        return relacion;
    }    
    @Override
    public void actualizarPosicion(int x,int y){
        int trX=x-posX;
        int trY=y-posY;
        this.posX=x;
        this.posY=y;
        this.relacion.actualizarPosicion(this.relacion.getPosX()+trX, this.relacion.getPosY()+trY);
        for(int i=0;i<this.relacion.getAtributos().size();i++){
            this.relacion.getAtributos().get(i).actualizarPosicion(this.relacion.getAtributos().get(i).getPosX()+trX, this.relacion.getAtributos().get(i).getPosY()+trY);
            for(int j=0;j<this.relacion.getAtributos().get(i).getAtributos().size();j++){
                int x1=this.relacion.getAtributos().get(i).getAtributos().get(j).getPosX()+trX;
                int y1=this.relacion.getAtributos().get(i).getAtributos().get(j).getPosY()+trY;
                this.relacion.getAtributos().get(i).getAtributos().get(j).actualizarPosicion(x1, y1);
            }
        }
        for(int i = 0; i<this.relacion.getElementos().size();i++){
            int x1 = this.relacion.getElementos().get(i).getPosX()+trX;
            int y1 = this.relacion.getElementos().get(i).getPosY()+trY;
            this.relacion.getElementos().get(i).actualizarPosicion(x1,y1);
            if(this.relacion.getElementos().get(i) instanceof Entidad){
                for(int j = 0; j<((Entidad)this.relacion.getElementos().get(i)).getAtributos().size();j++){
                    x1 = ((Entidad)this.relacion.getElementos().get(i)).getAtributos().get(j).getPosX()+trX;
                    y1 = ((Entidad)this.relacion.getElementos().get(i)).getAtributos().get(j).getPosY()+trY;
                    ((Entidad)this.relacion.getElementos().get(i)).getAtributos().get(j).actualizarPosicion(x1, y1);
                    for(int k = 0; k<((Entidad)this.relacion.getElementos().get(i)).getAtributos().get(j).getAtributos().size();k++){
                        x1 = ((Entidad)this.relacion.getElementos().get(i)).getAtributos().get(j).getAtributos().get(k).getPosX()+trX;
                        y1 = ((Entidad)this.relacion.getElementos().get(i)).getAtributos().get(j).getAtributos().get(k).getPosY()+trY;                        
                        ((Entidad)this.relacion.getElementos().get(i)).getAtributos().get(j).getAtributos().get(k).actualizarPosicion(x1, y1);
                    }
                }
            }
            
        }

        
        
        
    }
    private double distancia(double n, double n1){   
        return Math.sqrt(Math.pow(n1-n, 2));
    }
    private ArrayList<Integer> buscarElementosBordes(boolean menor) {
        ArrayList<Integer> distanciasX = new ArrayList<>();
        ArrayList<Integer> distanciasY = new ArrayList<>();
        for(int i = 0; i<relacion.getFigura().getPosicionesX().size();i++){
            distanciasX.add((int)distancia(relacion.getFigura().getPosicionesX().get(i),0.0));
            distanciasY.add((int)distancia(relacion.getFigura().getPosicionesY().get(i),0.0));            
        }

        for(int i = 0; i<relacion.getAtributos().size();i++){
            for(int j=0;j<relacion.getAtributos().get(i).getFigura().getPosicionesX().size();j++){
                distanciasX.add((int)distancia(relacion.getAtributos().get(i).getFigura().getPosicionesX().get(i),0.0));
                distanciasY.add((int)distancia(relacion.getAtributos().get(i).getFigura().getPosicionesY().get(i),0.0));                
            }
 
            for(int j=0;j<relacion.getAtributos().get(i).getAtributos().size();j++){
                for(int k=0; k<relacion.getAtributos().get(i).getAtributos().get(j).getFigura().getPosicionesX().size();k++){
                distanciasX.add((int)distancia(relacion.getAtributos().get(i).getAtributos().get(j).getFigura().getPosicionesX().get(k),0.0));
                distanciasY.add((int)distancia(relacion.getAtributos().get(i).getAtributos().get(j).getFigura().getPosicionesY().get(k),0.0));                
            
                }
            }
        }
       for(int k=0;k<relacion.getElementos().size();k++){
            for(int i=0; i<relacion.getElementos().get(k).getPosicionesX().size();i++){
                distanciasX.add((int)distancia(relacion.getElementos().get(k).getPosicionesX().get(i),0.0));
                distanciasY.add((int)distancia(relacion.getElementos().get(k).getPosicionesY().get(i),0.0));                
            }
            if(relacion.getElementos().get(k) instanceof Entidad){
                for(int i = 0; i<((Entidad)relacion.getElementos().get(k)).getAtributos().size();i++){
                    for(int j=0; j<((Entidad)relacion.getElementos().get(k)).getAtributos().get(i).getFigura().getPosicionesX().size();j++){
                        distanciasX.add((int)distancia(((Entidad)relacion.getElementos().get(k)).getAtributos().get(i).getFigura().getPosicionesX().get(j),0.0));
                        distanciasY.add((int)distancia(((Entidad)relacion.getElementos().get(k)).getAtributos().get(i).getFigura().getPosicionesY().get(j),0.0));                        
                    }

                    for(int j=0;j<((Entidad)relacion.getElementos().get(k)).getAtributos().get(i).getAtributos().size();j++){
                        for(int l=0;l<((Entidad)relacion.getElementos().get(k)).getAtributos().get(i).getAtributos().get(j).getFigura().getPosicionesX().size();l++){
                            distanciasX.add((int)distancia(((Entidad)relacion.getElementos().get(k)).getAtributos().get(i).getAtributos().get(j).getFigura().getPosicionesX().get(l),0.0));
                            distanciasY.add((int)distancia(((Entidad)relacion.getElementos().get(k)).getAtributos().get(i).getAtributos().get(j).getFigura().getPosicionesY().get(l),0.0));                            
                        }
                
                    }
                }     
            }
       }
       ArrayList<Integer> retornar = new ArrayList<>();
       int dx=puntoCercano(distanciasX,menor);
       int dy=puntoCercano(distanciasY,menor);
       if(menor==true){
            if(dx<posX){
                retornar.add((int)distancia(dx,posX)+5);
            }
            else{
                retornar.add(5);
            }
            if(dy<posY){
                retornar.add((int)distancia(dy,posY)+5);
            }
            else{
                retornar.add(5);
            }       
            return retornar;
       }
       else{
           retornar.add(dx+5);
           retornar.add(dy+5);
           return retornar;
       }
       
    }

    private int puntoCercano(ArrayList<Integer> distancias,boolean menor){
        for(int x = 0; x<distancias.size(); x++){
            for(int i = 0; i<distancias.size()-1-x;i++){
                if(menor==true){
                    if (distancias.get(i+1) < distancias.get(i)){
                        int aux= distancias.get(i);
                        distancias.set(i, distancias.get(i+1));
                        distancias.set(i+1, aux);

                    }                    
                }
                else{
                    if (distancias.get(i+1) > distancias.get(i)){
                        int aux= distancias.get(i);
                        distancias.set(i, distancias.get(i+1));
                        distancias.set(i+1, aux);

                    }                
                }



            }
        }
        return distancias.get(0);

    }
    public void atributoAnadido(){
        this.ordenarElementos();
        this.crearFigura();
    }
    
}
