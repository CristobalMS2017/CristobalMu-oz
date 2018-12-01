package Clases;

import java.util.ArrayList;
import javafx.scene.Node;
import javafx.scene.shape.Line;

/**
 * Clase Relación.
 * @author Cristobal Muñoz Salinas
 */
public class Relacion {
    private int posX;
    private int posY;
    private String nombre;
    
    private ArrayList<Entidad> entidad;
    private ArrayList<Agregacion> agregacion;
    private Figura figura;
    private ArrayList<Line> lineasRelacion = new ArrayList<>();
    
    private ArrayList<Atributo> atributos = new ArrayList<>();
    private ArrayList<Line> lineasUnionAtributos = new ArrayList<>();

    private boolean relacionDebil;
    public Relacion(boolean debil,int posX, int posY,String nombre) {
        this.posX = posX;
        this.posY = posY;
        this.nombre=nombre;

        this.relacionDebil=debil;
        this.entidad = new ArrayList<>();
        this.agregacion=new ArrayList<>();
        this.figura = new Figura();
    }
    public void AnadirEntidad(Entidad nueva){
        this.entidad.add(nueva);

    }

    public ArrayList<Agregacion> getAgregacion() {
        return agregacion;
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

    public boolean isRelacionDebil() {
        return relacionDebil;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
        this.crearRelacion();
    }

    public ArrayList<Entidad> getEntidad() {
        return entidad;
    }

    public void setEntidad(ArrayList<Entidad> entidad) {
        this.entidad = entidad;
    }

    public Figura getFigura() {
        return figura;
    }

    public void setFigura(Figura figura) {
        this.figura = figura;
    }
    /**
     * Según la cantidad de entidades agregadas a la relacion
     * se creara la figura junto con las lineas de union de la figura con las entidades.
     */
    public void crearRelacion(){
        figura = new Figura();
        this.lineasRelacion.clear();
        if(this.relacionDebil){
            if(!entidad.get(0).isDebil()){
                Entidad auxiliar = entidad.get(0);
                entidad.remove(0);
                entidad.add(auxiliar);
            }
            if(entidad.get(1).isDebil()){
                Entidad auxiliar = entidad.get(1);
                entidad.remove(1);
                entidad.add(auxiliar);                
            }
            figura.crearFigura(true,entidad.size(), this.nombre, this.getPosX(), this.getPosY());
            PuntoCercano pc = new PuntoCercano(this.figura,this.entidad);  
            
            Line linea = new Line(entidad.get(0).getFigura().getPosicionesX().get(pc.pcEntidad(0)),entidad.get(0).getFigura().getPosicionesY().get(pc.pcEntidad(0)),
            figura.getPosicionesX().get(pc.pcFigura(0)),figura.getPosicionesY().get(pc.pcFigura(0)));
              
            Line linea2 = new Line(entidad.get(0).getFigura().getPosicionesX().get(pc.pcEntidad(0))+3,entidad.get(0).getFigura().getPosicionesY().get(pc.pcEntidad(0)),
            figura.getPosicionesX().get(pc.pcFigura(0))+3,figura.getPosicionesY().get(pc.pcFigura(0)));
            
            Line linea3 = new Line(entidad.get(1).getFigura().getPosicionesX().get(pc.pcEntidad(1)),entidad.get(1).getFigura().getPosicionesY().get(pc.pcEntidad(1)),
            figura.getPosicionesX().get(pc.pcFigura(1)),figura.getPosicionesY().get(pc.pcFigura(1)));            
            lineasRelacion.add(linea3);
            lineasRelacion.add(linea2);
            lineasRelacion.add(linea);       
            
            
            
        }        
        else{
            figura.crearFigura(false,entidad.size()+agregacion.size(), this.nombre, this.getPosX(), this.getPosY());                     
            if(agregacion.size()==2){

                ArrayList<Entidad> copia = new ArrayList<>();
                for(int i = 0; i<this.agregacion.size();i++){
                    Entidad a = new Entidad(true,0,0,"");
                    a.getFigura().setPosicionesX(agregacion.get(i).getPosicionesX());
                    a.getFigura().setPosicionesY(agregacion.get(i).getPosicionesY());
                    copia.add(a);
                }                
                
                PuntoCercano pc = new PuntoCercano(this.figura,copia); 
                Line linea = new Line(agregacion.get(0).getPosicionesX().get(pc.getOrdenIndicesEntidades().get(0)),agregacion.get(0).getPosicionesY().get(pc.getOrdenIndicesEntidades().get(0)),
                figura.getPosicionesX().get(pc.getOrdenIndicesFigura().get(0)),figura.getPosicionesY().get(pc.getOrdenIndicesFigura().get(0)));
                lineasRelacion.add(linea); 
                Line linea2 = new Line(agregacion.get(1).getPosicionesX().get(pc.getOrdenIndicesEntidades().get(1)),agregacion.get(1).getPosicionesY().get(pc.getOrdenIndicesEntidades().get(1)),
                figura.getPosicionesX().get(pc.getOrdenIndicesFigura().get(1)),figura.getPosicionesY().get(pc.getOrdenIndicesFigura().get(1)));
                lineasRelacion.add(linea2);                      
            }
            if(entidad.size()==2){
                PuntoCercano pc = new PuntoCercano(this.figura,this.entidad); 
                 for(int i = 0; i<entidad.size();i++){
                    Line linea = new Line(entidad.get(i).getFigura().getPosicionesX().get(pc.pcEntidad(i)),entidad.get(i).getFigura().getPosicionesY().get(pc.pcEntidad(i)),
                    figura.getPosicionesX().get(pc.pcFigura(i)),figura.getPosicionesY().get(pc.pcFigura(i)));
                    lineasRelacion.add(linea);                
                }               
            }
            if(entidad.size()==1&&agregacion.isEmpty()){
                PuntoCercano pc = new PuntoCercano(this.figura,this.entidad); 
                for(int i = 0; i<2;i++){
                    Line linea = new Line(entidad.get(0).getFigura().getPosicionesX().get(pc.pcEntidad(i)),entidad.get(0).getFigura().getPosicionesY().get(pc.pcEntidad(i)),
                    figura.getPosicionesX().get(pc.pcFigura(i)),figura.getPosicionesY().get(pc.pcFigura(i)));
                    lineasRelacion.add(linea);  

                } 
            }
            if((entidad.size()==1)&&(agregacion.size()==1)){
                ArrayList<Entidad> copia = new ArrayList<>();
                copia.add(this.entidad.get(0));
                Entidad a = new Entidad(true,0,0,"");
                a.getFigura().setPosicionesX(this.agregacion.get(0).getPosicionesX());
                a.getFigura().setPosicionesY(this.agregacion.get(0).getPosicionesY());
                copia.add(a);
                        
                    PuntoCercano pc = new PuntoCercano(this.figura,copia); 
                    Line linea2 = new Line(entidad.get(0).getFigura().getPosicionesX().get(pc.getOrdenIndicesEntidades().get(0)),entidad.get(0).getFigura().getPosicionesY().get(pc.getOrdenIndicesEntidades().get(0)),
                    figura.getPosicionesX().get(pc.getOrdenIndicesFigura().get(0)),figura.getPosicionesY().get(pc.getOrdenIndicesFigura().get(0)));
                    lineasRelacion.add(linea2);    

                    Line linea = new Line(agregacion.get(0).getPosicionesX().get(pc.getOrdenIndicesEntidades().get(1)),agregacion.get(0).getPosicionesY().get(pc.getOrdenIndicesEntidades().get(1)),
                    figura.getPosicionesX().get(pc.getOrdenIndicesFigura().get(1)),figura.getPosicionesY().get(pc.getOrdenIndicesFigura().get(1)));
                    lineasRelacion.add(linea);                    
            }
            
        }           

    }

    public ArrayList<Line> getLineasRelacion() {
        return lineasRelacion;
    }

    public ArrayList<Atributo> getAtributos() {
        return atributos;
    }
    
    public void agregarAtributo(Atributo nuevo){
        atributos.add(nuevo);
        
    }
    public ArrayList<Line> getLineasUnionAtributos() {
        return lineasUnionAtributos;
    }
    public void crearLineasunionAtributos(){
        this.lineasUnionAtributos.clear();
        for(int i =0; i<atributos.size();i++){
            int indRelacion = puntoCercanoAtributo(i);
            Line linea = new Line(this.figura.getPosicionesX().get(indRelacion),this.figura.getPosicionesY().get(indRelacion),atributos.get(i).puntoMedioXFigura(),atributos.get(i).getPosY());
            this.lineasUnionAtributos.add(linea);
        }     
    }    
    private int puntoCercanoAtributo(int indiceAtributo){
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

    public ArrayList<Node> dibujoRelacion(){
        ArrayList<Node> retornar = new ArrayList<>();
        retornar.addAll(this.lineasUnionAtributos);


        retornar.addAll(this.figura.getLineas());
        retornar.addAll(this.lineasRelacion);
        retornar.add(this.figura.getTextoFigura());
        return retornar;
    }    
    public ArrayList<Atributo> retornarAtributosDeAtributos(int i){
        return this.atributos.get(i).getAtributos();
    }
    
    public void eliminarEntidad(int i){
        this.entidad.remove(i);
        this.crearRelacion();
        
    }
    
    
    public void actualizarPosicion(int x,int y){
        this.posX=x;
        this.posY=y;
        this.crearRelacion();
        this.crearLineasunionAtributos();
    }
}
