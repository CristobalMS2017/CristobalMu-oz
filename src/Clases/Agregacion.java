/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.util.ArrayList;
import javafx.scene.Node;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author Cristobal Andres
 */
public class Agregacion {
    private Relacion relacion;
    private String nombre;
    private int posX;
    private int posY;
    private ArrayList<Line> lineas = new ArrayList<>();
    private ArrayList<Integer> posicionesX = new ArrayList<>();
    private ArrayList<Integer> posicionesY = new ArrayList<>();
    private ArrayList<Line> puntosDeControl = new ArrayList<>();
    private ArrayList<Line> lineasUnionAtributos = new ArrayList<>();
    private Text textoFigura;
    
    private ArrayList<Atributo> atributos = new ArrayList<>();
    
    public Agregacion(Relacion relacion, String nombre, int posX, int posY) {
        this.relacion = relacion;
        this.nombre = nombre;
        this.posX = posX;
        this.posY = posY;
    }
    private boolean dentroDelPanel(){
        int contador = 0;
        for(int i = 0; i<this.getPosicionesX().size();i++){
            if(0<=this.getPosicionesX().get(i)-5 && this.getPosicionesX().get(i)+5<=1050 && 0<=this.getPosicionesY().get(i) && this.getPosicionesY().get(i)<= 687){
                contador++;
            }            
        }
        return contador==posicionesX.size();
        

    }    

    public ArrayList<Atributo> getAtributos() {
        return atributos;
    }
    
    public void crearAgregacion(){
        
            if(246>=posX){
                posX = posX+(246-posX);
            }
            
                lineas.clear();
                
                this.posicionesX.clear();
                this.posicionesY.clear();

                relacion.actualizarPosicion(posX, posY);
                int distancia = relacion.getEntidad().get(0).getFigura().getPosicionesX().get(1)-relacion.getEntidad().get(0).getFigura().getPosicionesX().get(0);
                relacion.getEntidad().get(0).actualizarPosEntidad(relacion.getFigura().getPosicionesX().get(0)-distancia-100, posY-20);
                relacion.getEntidad().get(1).actualizarPosEntidad(relacion.getFigura().getPosicionesX().get(1)+100, posY-20);
                relacion.crearRelacion();
                relacion.crearLineasunionAtributos();
                Entidad entidad1 = relacion.getEntidad().get(0);
                Entidad entidad2 = relacion.getEntidad().get(1);


                int posx1 = entidad1.getFigura().getPosicionesX().get(0)-20;
                int posy1 = relacion.getFigura().getPosicionesY().get(2)-30;
                int posy2 = relacion.getFigura().getPosicionesY().get(3)+20;
                int posx2 = entidad2.getFigura().getPosicionesX().get(1)+20;


                Line linea1 = new Line(posx1,posy1,posx2,posy1);
                //linea1.getStrokeDashArray().add(6D);
                Line linea2 = new Line(posx1,posy2,posx2,posy2);
                //linea2.getStrokeDashArray().add(6D);
                Line linea3 = new Line(posx1,posy1,posx1,posy2);
                //linea3.getStrokeDashArray().add(6D);
                Line linea4 = new Line(posx2,posy1,posx2,posy2);
                //linea4.getStrokeDashArray().add(6D);
                lineas.add(linea1);
                lineas.add(linea2);
                lineas.add(linea3);
                lineas.add(linea4);
                this.posicionesX.add(posx1);
                this.posicionesY.add(posy1);
                this.posicionesX.add(posx2);
                this.posicionesY.add(posy1);
                this.posicionesX.add(posx1);
                this.posicionesY.add(posy2);
                this.posicionesX.add(posx2);
                this.posicionesY.add(posy2);       
                for(int i = 0; i<4;i++){
                    Line nueva = new Line(this.posicionesX.get(i),this.posicionesY.get(i),
                            this.posicionesX.get(i),this.posicionesY.get(i));
                    nueva.setStrokeWidth(7);
                    this.puntosDeControl.add(nueva);
                }
                    textoFigura = new Text(posx1,posy1+20,nombre);
                    textoFigura.setWrappingWidth(posx2-posx1);
                    textoFigura.setTextAlignment(TextAlignment.CENTER);            
                    textoFigura.setFont(new Font(14));        

            }                  
            
          
        


    public Relacion getRelacion() {
        return relacion;
    }

    public ArrayList<Line> getPuntosDeControl() {
        return puntosDeControl;
    }

    public ArrayList<Integer> getPosicionesX() {
        return posicionesX;
    }

    public ArrayList<Integer> getPosicionesY() {
        return posicionesY;
    }

    public String getNombre() {
        return nombre;
    }
    public void crearLineasunionAtributos(){
        this.lineasUnionAtributos.clear();
        for(int i =0; i<atributos.size();i++){
            int indEntidad = puntoCercano(i);
            Line linea = new Line(this.getPosicionesX().get(indEntidad),this.getPosicionesY().get(indEntidad),atributos.get(i).puntoMedioXFigura(),atributos.get(i).getPosY());
            this.lineasUnionAtributos.add(linea);
        }
    }    
    
    
    public ArrayList<Node> dibujoAgregacion(){
        ArrayList<Node> retornar = new ArrayList<>();
        retornar.addAll(lineas);
        retornar.addAll(this.lineasUnionAtributos);
        retornar.add(textoFigura);
        return retornar;
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
}
