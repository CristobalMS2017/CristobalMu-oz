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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * Clase que crea las figuras de los distintos tipos de atributos.
 * @author Cristobal Muñoz Salinas
 */
public class FiguraAtributos {
    private ArrayList<Node> lineas = new ArrayList<>(); // guarda las lineas creadas.
    
    private ArrayList<Integer> posicionesX = new ArrayList<>(); //posiciones x de la figura.
    private ArrayList<Integer> posicionesY = new ArrayList<>(); //posiciones y de la figura.
    private ArrayList<CubicCurve> puntosControl = new ArrayList<>(); //puntos de control de la figura.
    private int puntoMedioX;

    private Text textoFigura;  
    private int posX;
    private int posY;
    private String tipoAtributo;
    public FiguraAtributos(String tipoAtributo,String nombre, int posX, int posY) {

        this.posX = posX;
        this.posY = posY;
        this.tipoAtributo=tipoAtributo;
        crearAtributo(nombre);
    }



    
    private void crearAtributo(String nombre){
        int agrandar = nombre.length()*5;
            
            int sumaH=(100+agrandar)/20;
            int sumaV = (100+agrandar/5)/4;
            
        posicionesX.add(posX);
        posicionesY.add(posY);
                
        posicionesX.add(posX+100+agrandar);
        posicionesY.add(posY);

        posicionesX.add(posX+(100+agrandar)/2);
        posicionesY.add(posY-sumaV);
        
        posicionesX.add(posX+(100+agrandar)/2);
        posicionesY.add(posY+sumaV);  
        
                    
                        
            CubicCurve relleno1 = new CubicCurve(posX, posY, posX,posY-sumaV,posX+100+agrandar,posY-sumaV,posX+agrandar+100,posY);
            relleno1.setFill(Color.WHITE);

            CubicCurve relleno2 = new CubicCurve(posX, posY, posX,posY+sumaV,posX+agrandar+100,posY+sumaV,posX+agrandar+100,posY);
            relleno2.setFill(Color.WHITE);


            CubicCurve curva = new CubicCurve(posX, posY, posX,posY-sumaV,posX+agrandar+100,posY-sumaV,posX+agrandar+100,posY);
            curva.setStroke(Color.BLACK);
            curva.setStrokeWidth(2);
            curva.setFill(null);


            CubicCurve curva2 = new CubicCurve(posX, posY, posX,posY+sumaV,posX+agrandar+100,posY+sumaV,posX+agrandar+100,posY);
            curva2.setStroke(Color.BLACK);
            curva2.setStrokeWidth(2);
            curva2.setFill(null);



            textoFigura = new Text(posX,posY,nombre);
            textoFigura.setWrappingWidth(100+agrandar);
            textoFigura.setTextAlignment(TextAlignment.CENTER);        
            textoFigura.setFont(new Font(14));        
            puntoMedioX=posX+(100+agrandar)/2;
        
        if(tipoAtributo.equals("Generico")||tipoAtributo.equals("Compuesto")){ //Atributo generico
            lineas.add(relleno1);            
            lineas.add(relleno2);
            lineas.add(curva); 
            lineas.add(curva2);                        
        }


        if(tipoAtributo.equals("Clave")){//calve
            lineas.add(relleno1);
            lineas.add(relleno2);
            lineas.add(curva);      
            lineas.add(curva2);
            textoFigura.underlineProperty().set(true);
            
        }
        if(tipoAtributo.equals("Clave Parcial")){//calve parcial: subrayado punteado
            lineas.add(relleno1);
            lineas.add(relleno2);
            lineas.add(curva);      
            lineas.add(curva2);            
            Line linea = new Line(posX+15,posY+3,(posX+100+agrandar)-15,posY+3);
            linea.getStrokeDashArray().add(6D);
            lineas.add(linea);
            
            
        }
        if(tipoAtributo.equals("Multivaluado")){//Multivaluado


            lineas.add(relleno1);

            lineas.add(relleno2);            
            lineas.add(curva);
            lineas.add(curva2);        

            CubicCurve doble1 = new CubicCurve(posX-sumaH, posY, posX-sumaH,posY-(sumaV+sumaV/5),posX+agrandar+100+sumaH,posY-(sumaV+sumaV/5),posX+agrandar+100+sumaH,posY);
            doble1.setStroke(Color.BLACK);
            doble1.setStrokeWidth(2);
            doble1.setFill(null);
            lineas.add(doble1);
            CubicCurve doble2 = new CubicCurve(posX-sumaH, posY, posX-sumaH,posY+(sumaV+sumaV/5),posX+agrandar+100+sumaH,posY+(sumaV+sumaV/5),posX+agrandar+100+sumaH,posY);
            doble2.setStroke(Color.BLACK);
            doble2.setStrokeWidth(2);
            doble2.setFill(null);
            lineas.add(doble2);            
           
                        
        }

        if(tipoAtributo.equals("Derivado")){//derivado
       
            lineas.add(relleno1);
            lineas.add(relleno2);
            

          
            curva.getStrokeDashArray().addAll(6d);
            lineas.add(curva);
            curva2.getStrokeDashArray().addAll(6d);
            lineas.add(curva2);
        }        
            CubicCurve curvaControl = new CubicCurve(posX, posY, posX,posY-sumaV,posX+agrandar+100,posY-sumaV,posX+agrandar+100,posY);
            curvaControl.setStroke(Color.BLACK);
            curvaControl.setStrokeWidth(5);
            curvaControl.getStrokeDashArray().addAll(10D);
            curvaControl.setFill(null);
            puntosControl.add(curvaControl);

            CubicCurve curvaControl2 = new CubicCurve(posX, posY, posX,posY+sumaV,posX+agrandar+100,posY+sumaV,posX+agrandar+100,posY);
            curvaControl2.setStroke(Color.BLACK);
            curvaControl2.setStrokeWidth(5);
            curvaControl2.getStrokeDashArray().addAll(10D);
            curvaControl2.setFill(null);
            puntosControl.add(curvaControl2);
                
    }
 

    public ArrayList<Node> getLineas() {
        return lineas;
    }

    public ArrayList<Integer> getPosicionesX() {
        return posicionesX;
    }

    public ArrayList<Integer> getPosicionesY() {
        return posicionesY;
    }

    public ArrayList<CubicCurve> getPuntosControl() {
        return puntosControl;
    }

    public Text getTextoFigura() {
        return textoFigura;
    }
    public int puntoMedioXfigura(){
        
        return puntoMedioX;
    }
    
    
}
