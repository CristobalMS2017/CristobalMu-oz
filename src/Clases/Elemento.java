/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.util.ArrayList;
import javafx.scene.Node;
import javafx.scene.shape.Line;

/**
 *
 * @author Cristobal Andres
 */
public class Elemento {
    protected String nombre;
    protected int posX;
    protected int posY;
    protected ArrayList<Integer> posicionesX = new ArrayList<>();
    protected ArrayList<Integer> posicionesY = new ArrayList<>();
    protected ArrayList<Atributo> atributos = new ArrayList<>();
    protected ArrayList<Node> dibujoFigura = new ArrayList<>();
    protected ArrayList<Line> puntosDeControl = new ArrayList<>();

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
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

    public ArrayList<Atributo> getAtributos() {
        return atributos;
    }

    public void setAtributos(ArrayList<Atributo> atributos) {
        this.atributos = atributos;
    }

    public ArrayList<Node> getDibujoFigura() {
        return dibujoFigura;
    }

    public void setDibujoFigura(ArrayList<Node> dibujoFigura) {
        this.dibujoFigura = dibujoFigura;
    }
    
    public void crearLineasunionAtributos(){
        this.dibujoFigura.clear();
        
        this.crearFigura();
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
    public void crearFigura(){
        
    }
    
    public void actualizarPosicion(int x,int y){
        this.posX = x;
        this.posY = y;
        this.crearFigura();
 
    }
    protected void crearPuntosDeControl(){
        puntosDeControl.clear();
        for(int i=0; i<posicionesX.size();i++){
            Line linea = new Line(posicionesX.get(i), posicionesY.get(i), posicionesX.get(i), posicionesY.get(i));
            linea.setStrokeWidth(7);
            puntosDeControl.add(linea);
        }  
    }    

    public ArrayList<Line> getPuntosDeControl() {
        return puntosDeControl;
    }
    public void agregarAtributo(Atributo nuevo){
        atributos.add(nuevo);
        
    }
}
