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
 * Clase Atributo
 * @author Cristobal Muñoz Salinas
 */
public class Atributo {
    private int posX;
    private int posY;
    private String nombre;
    private FiguraAtributos figura;
    private String tipoAtributo;
    private ArrayList<Atributo> atributos = new ArrayList<>();
    private ArrayList<Line> lineasUnionAtributos = new ArrayList<>();
    
    private String guardadoEn;
    private String nombreOrigenAtributo;
    
    
    
    public Atributo(String tipoAtributo,int posX, int posY, String nombre,String guardadoEn,String nombreOrigenAtributo) {
        this.posX = posX;
        this.posY = posY;
        this.nombre = nombre;
        this.tipoAtributo=tipoAtributo;
        this.guardadoEn=guardadoEn;
        this.nombreOrigenAtributo=nombreOrigenAtributo;
        this.figura = new FiguraAtributos(this.tipoAtributo,this.nombre,this.posX,this.posY);
    }

    public String getNombreOrigenAtributo() {
        return nombreOrigenAtributo;
    }
    
    public String getNombre() {
        return nombre;
    }

    public String getGuardadoEn() {
        return guardadoEn;
    }
    
    public FiguraAtributos getFigura() {
        return figura;
    }
    public ArrayList<Integer> posicionesX(){
        return figura.getPosicionesX();
    }
    public ArrayList<Integer> posicionesY(){
        return figura.getPosicionesY();
    }       

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public String getTipoAtributo() {
        return tipoAtributo;
    }
    public int puntoMedioXFigura(){
        return this.figura.puntoMedioXfigura();
    }
    
    public ArrayList<Atributo> getAtributos() {
        return atributos;
    }

    private int puntoCercano(int indiceAtributo){
        int x1=atributos.get(indiceAtributo).puntoMedioXFigura();
        int y1 = atributos.get(indiceAtributo).getPosY();
        ArrayList<Integer> distancias = new ArrayList<>();
        ArrayList<Integer> orden = new ArrayList<>();
        int x2,y2;
        for(int i=0; i< this.figura.getPosicionesX().size();i++){
            orden.add(i);
            x2 = this.getFigura().getPosicionesX().get(i);
            y2 = this.getFigura().getPosicionesY().get(i);
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
    public void crearLineasunionAtributos(){
        this.lineasUnionAtributos.clear();
        for(int i =0; i<atributos.size();i++){

            Line linea = new Line(this.figura.puntoMedioXfigura(),this.posY,atributos.get(i).puntoMedioXFigura(),atributos.get(i).getPosY());
            this.lineasUnionAtributos.add(linea);
        }
    }

    public ArrayList<Line> getLineasUnionAtributos() {
        return lineasUnionAtributos;
    } 
    public void agregarAtributo(Atributo nuevo){
        atributos.add(nuevo);
        
    }
    public ArrayList<Node> dibujoAtributo(){
        ArrayList<Node> retornar = new ArrayList<>();
        retornar.addAll(this.lineasUnionAtributos);
        for(int i = 0 ; i<this.atributos.size();i++){
            retornar.addAll(this.atributos.get(i).dibujoAtributo());
        }
       
        retornar.addAll(this.figura.getLineas());
        retornar.add(this.figura.getTextoFigura());
        return retornar;
        
    }
    public void cambiarNombre(String nuevoNombre){
        this.nombre=nuevoNombre;  
    }
    public void crearAtributo(){
        this.figura=new FiguraAtributos(this.tipoAtributo,this.nombre,this.posX,this.posY);
        crearLineasunionAtributos();        
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
        crearAtributo();
    }
    
    
    public boolean nombrePorDefectoRepetido(String nombre){
        if(this.nombre.equals(nombre)){
            return true;
        }
        for(int i = 0; i<this.atributos.size();i++){
            if(this.atributos.get(i).getNombre().equals(nombre)){
                return true;
            }
        }
        
        
        
        
        return false;
    }
    public void actualizarPosicion(int x, int y){
        this.posX=x;
        this.posY=y;
        this.crearAtributo();
        
    }       
}
