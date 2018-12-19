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
    public ArrayList<Node> getDibujoFigura() {
        return dibujoFigura;
    }

    public void setDibujoFigura(ArrayList<Node> dibujoFigura) {
        this.dibujoFigura = dibujoFigura;
    }
    
   
    public void crearFigura(){
        
    }
    public void actualizarPosicion(int x,int y){
        
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

}
