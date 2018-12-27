/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.util.ArrayList;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;

/**
 * Clase Herencia
 * Dentro de esta se crea tambien la figura de la herencia.
 * @author Cristobal Muñoz Salinas
 */
public class Herencia {
    private Entidad entidadPadre;
    private ArrayList<Entidad> EntidadesHijas = new ArrayList<>();
    private ArrayList<Line> unionesEntidades = new ArrayList<>();
    private ArrayList<CubicCurve> figuraHerencia = new ArrayList<>();
    private String tipoHerencia;
    private Text textTipoHerencia;
    private int posX;
    private int posY;
    private ArrayList<CubicCurve> semicircunferencias = new ArrayList<>();
    
    public Herencia(Entidad entidadPadre, ArrayList<Entidad> hijas,String tipoHerencia,int posX,int posY) {
        this.posX=posX;
        this.posY=posY;
        this.entidadPadre=entidadPadre;
        this.EntidadesHijas=hijas;
        this.tipoHerencia=tipoHerencia;
    }

    public void setTipoHerencia(String tipoHerencia) {
        this.tipoHerencia = tipoHerencia;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }
    
    private void crearFigura(){
        figuraHerencia.clear();
        CubicCurve relleno1 = new CubicCurve(posX, posY, posX,posY+20,posX+30,posY+20,posX+30,posY);
        relleno1.setFill(Color.WHITE);

        CubicCurve relleno2 = new CubicCurve(posX, posY, posX,posY-20,posX+30,posY-20,posX+30,posY);
        relleno2.setFill(Color.WHITE);

        CubicCurve curva = new CubicCurve(posX, posY, posX,posY+20,posX+30,posY+20,posX+30,posY);
        curva.setStroke(Color.BLACK);
        curva.setStrokeWidth(2);
        curva.setFill(null);   
        
        
        
        CubicCurve curva2 = new CubicCurve(posX, posY, posX,posY-20,posX+30,posY-20,posX+30,posY);
        curva2.setStroke(Color.BLACK);
        curva2.setStrokeWidth(2);
        curva2.setFill(null);
        figuraHerencia.add(relleno1);
        figuraHerencia.add(relleno2);
        figuraHerencia.add(curva);
        figuraHerencia.add(curva2);
        
         
        textTipoHerencia=new Text(posX,posY+5,tipoHerencia);
        textTipoHerencia.setWrappingWidth(30);
        textTipoHerencia.setStrokeWidth(2);
        textTipoHerencia.setTextAlignment(TextAlignment.CENTER);          

        
    }
    
    
    /**
     * Metodo que retorna el dibujo completo de la herencia.
     * @return arraylist de los dibujos creados de la herencia
     */
    public ArrayList<Node> dibujoHerencia(){
        ArrayList<Node> retornar = new ArrayList<>();
        retornar.addAll(this.unionesEntidades);
        retornar.addAll(this.figuraHerencia);
        retornar.add(textTipoHerencia);
        retornar.addAll(semicircunferencias);
        return retornar;

        
    }
    public ArrayList<Integer> posicionesXFigura(){
        ArrayList<Integer> x = new ArrayList<>();
        x.add(posX);
        x.add(posX+30);
        x.add(posX+15);
        x.add(posX+15);
        return x;
    }
    public ArrayList<Integer> posicionesYFigura(){
        ArrayList<Integer> y = new ArrayList<>();
        y.add(posY);
        y.add(posY);
        y.add(posY-20);
        y.add(posY+20);
        return y;
    }
    public Entidad getEntidadPadre() {
        return entidadPadre;
    }

    public ArrayList<Entidad> getEntidadesHijas() {
        return EntidadesHijas;
    }
    public String getTipoHerencia() {
        return tipoHerencia;
    }

    /**
     * Se crean las semicircunferencias que se encuentran entre las entidades hijas y 
     * el circulo de herencia
     * @param finalX el punto que une con la entidad
     * @param finalY el punto que une con la entidad
     */
    private void semicircunferencia(int finalX,int finalY){
        int px = posX+15;
        int pmediox=(-px+finalX)/2;
        int pmedioy =(-posY+finalY)/2;


        CubicCurve curva = new CubicCurve(0,10,20,10,20,-10,0,-10);
        curva.setStroke(Color.BLACK);
        curva.setStrokeWidth(2);
        curva.setFill(null);
        curva.setTranslateX(px+pmediox);
        curva.setTranslateY(posY+pmedioy);

        Rotate r = new Rotate();
        r.setPivotX(0);
        r.setPivotY(0);
        r.setAngle(plano(pmediox, pmedioy));
        curva.getTransforms().add(r);
        semicircunferencias.add(curva);

                
    }
    
    
    
    /**
     * Se crea las lineas y curvas de la herencia 
     */
    public void crearHerencia(){
        unionesEntidades.clear();
        crearFigura();
        EntidadesHijas.add(entidadPadre); 
        semicircunferencias.clear();        
        
        for(int i=0;i<EntidadesHijas.size();i++){            
            int indice= puntoCercano(posY,posX,EntidadesHijas.get(i));            
            if(i!=EntidadesHijas.size()-1){
                semicircunferencia(EntidadesHijas.get(i).getPosicionesX().get(indice),EntidadesHijas.get(i).getPosicionesY().get(indice));            
            }        
            Line nueva = new Line(posX+15,posY,EntidadesHijas.get(i).getPosicionesX().get(indice),
                                  EntidadesHijas.get(i).getPosicionesY().get(indice));
            
            unionesEntidades.add(nueva);
            
        }
        EntidadesHijas.remove(EntidadesHijas.size()-1);

    } 

    public void setEntidadPadre(Entidad entidadPadre) {
        this.entidadPadre = entidadPadre;
    }
    
    
    
    /**
     * Encuentra el punto cercano entre las entidades y la figura de la herencia.
     * @param posY: posición en la que se encuentra la figura herencia
     * @param posX: posición en la que se encuentra la figura herencia
     * @param entidad: Entidad a la que se le busca el punto mas cercano
     * @return 
     */
    private int puntoCercano(int posY,int posX,Entidad entidad){
        int x1=posX+15;
        int y1 = posY;
        ArrayList<Integer> distancias = new ArrayList<>();
        ArrayList<Integer> orden = new ArrayList<>();
        int x2,y2;
        for(int i=0; i< entidad.getPosicionesX().size();i++){
            orden.add(i);
            x2 = entidad.getPosicionesX().get(i);
            y2 = entidad.getPosicionesY().get(i);
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
  
    
    /**
     * Busca el lugar en que se encuentra la entidad y retorna el angulo de rotacíon adecuado
     * para que la semicircunferencia quede en dirección a la figura de la herencia.
     * @param x posición de la entidad
     * @param y posición de la entidad
     * @return retorna el angulo en que se encuentra el punto.
     */
    private int plano(double x, double y){
        if((x<-50 && y <-50)||(x<-25 & x>-50 & y<-25 &y>-50)){
            return 225;
        }
        if((x>=50 && y>=50)||(x>25 & x<50 & y>25 &y<50)){
            return 45;
        }
        if((x<=-50 && y>=50)||(x<-25 & x>-50 & y>25 &y<50)){
            return 135;
        }
        if((x>=50 &&y<=-50)||(x>25 & x<50 & y<-25 &y>-50)){
            return 315;
        }
        if((x>=-50 & x<=50 & y<=-50)||((x>-25 & x<25 & y<=0))){
            return 270;
        }
        if((x>=-50 & x<=50 & y>=50)||((x>-25 & x<25 & y>=0))){
            return 90;
        }        
        if((y>=-50 & y<=50 & x>=50)||((y>-25 & y<25 & x>=0))){
            return 0;
        }  
        if((y>=-50 & y<=50 & x<=-50)||((y>-25 & y<25 & x<=0))){
            return 180;
        }             
        return 0;
    }



    /**
     * elimina una entidad hija
     * @param i: indice de la entidad a remover; 
     */
    public void eliminarEntidadHija(int i){
        this.EntidadesHijas.remove(i);
        this.crearHerencia();
        
    }
    
    public void actualizarPosicion(int x, int y){
        this.posX=x;
        this.posY=y;
        this.crearHerencia();
        
    }    
}
