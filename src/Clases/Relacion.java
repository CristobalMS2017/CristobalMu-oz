package Clases;

import java.util.ArrayList;
import javafx.scene.Node;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Clase Relación.
 * @author Cristobal Muñoz Salinas
 */
public class Relacion {
    private int posX;
    private int posY;
    private String nombre;
    private ArrayList<Elemento> elementos;
    private Figura figura;
    private ArrayList<Line> lineasRelacion = new ArrayList<>();
    private ArrayList<String> participacion = new ArrayList<>();
    private ArrayList<Atributo> atributos = new ArrayList<>();
    private ArrayList<Line> lineasUnionAtributos = new ArrayList<>();
    private ArrayList<Text> cardinalidades  = new ArrayList<>();
    private ArrayList<String> stringCardinalidades  = new ArrayList<>();    
    private boolean relacionDebil;
    private String tipoCardinalidad;
    public Relacion(boolean debil,int posX, int posY,String nombre) {
        this.posX = posX;
        this.posY = posY;
        this.nombre=nombre;
        this.relacionDebil=debil;
        this.elementos=new ArrayList<>();
        this.figura = new Figura();
        this.elementos=new ArrayList<>();
        stringCardinalidades.add("1");
        stringCardinalidades.add("1");
        
        
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

    public ArrayList<Elemento> getElementos() {
        return elementos;
    }

    public ArrayList<String> getParticipacion() {
        return participacion;
    }
    
    public void setParticipacion(ArrayList<String> participacion) {
        this.participacion = participacion;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
        this.crearRelacion();
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
        this.cardinalidades.clear();
        this.lineasUnionAtributos.clear();
        if(this.relacionDebil){
            if(!((Entidad)elementos.get(0)).isDebil()){
                Entidad auxiliar = ((Entidad)elementos.get(0));
                elementos.remove(0);
                elementos.add(auxiliar);
            }
            if(((Entidad)elementos.get(1)).isDebil()){
                Entidad auxiliar = ((Entidad)elementos.get(1));
                elementos.remove(1);
                elementos.add(auxiliar);                
            }
            figura.crearFigura(true, this.nombre, this.getPosX(), this.getPosY());
            PuntoCercano pc = new PuntoCercano(this.figura,this.elementos);  
            
            Line linea = new Line(elementos.get(0).getPosicionesX().get(pc.pcEntidad(0)),elementos.get(0).getPosicionesY().get(pc.pcEntidad(0)),
            figura.getPosicionesX().get(pc.pcFigura(0)),figura.getPosicionesY().get(pc.pcFigura(0)));
                     int puntoCuartoX = (elementos.get(0).getPosicionesX().get(pc.pcEntidad(0))-figura.getPosicionesX().get(pc.pcFigura(0)))/8;
                    int puntoCuartoY = (elementos.get(0).getPosicionesY().get(pc.pcEntidad(0))-figura.getPosicionesY().get(pc.pcFigura(0)))/8;
                    cardinalidades.add(new Text(figura.getPosicionesX().get(pc.pcFigura(0))+puntoCuartoX+7,figura.getPosicionesY().get(pc.pcFigura(0))+puntoCuartoY,stringCardinalidades.get(0)));
                    ;                
            Line linea2 = new Line(elementos.get(0).getPosicionesX().get(pc.pcEntidad(0))+3,elementos.get(0).getPosicionesY().get(pc.pcEntidad(0)),
            figura.getPosicionesX().get(pc.pcFigura(0))+3,figura.getPosicionesY().get(pc.pcFigura(0)));
       
            Line linea3 = new Line(elementos.get(1).getPosicionesX().get(pc.pcEntidad(1)),elementos.get(1).getPosicionesY().get(pc.pcEntidad(1)),
            figura.getPosicionesX().get(pc.pcFigura(1)),figura.getPosicionesY().get(pc.pcFigura(1)));            
                    puntoCuartoY = (elementos.get(1).getPosicionesY().get(pc.pcEntidad(1))-figura.getPosicionesY().get(pc.pcFigura(1)))/8;
                    cardinalidades.add(new Text(figura.getPosicionesX().get(pc.pcFigura(1)),figura.getPosicionesY().get(pc.pcFigura(1))+puntoCuartoY,stringCardinalidades.get(1)));
                                    
            
            
            lineasRelacion.add(linea3);
            lineasRelacion.add(linea2);
            lineasRelacion.add(linea);       
         
            
            
        }        
        else{
            figura.crearFigura(false, this.nombre, this.getPosX(), this.getPosY());                     
            if(elementos.size()==2){
                
                PuntoCercano pc = new PuntoCercano(this.figura,this.elementos); 
                 for(int i = 0; i<elementos.size();i++){
                    Line linea = new Line(elementos.get(i).getPosicionesX().get(pc.pcEntidad(i)),elementos.get(i).getPosicionesY().get(pc.pcEntidad(i)),
                                            figura.getPosicionesX().get(pc.pcFigura(i)),figura.getPosicionesY().get(pc.pcFigura(i)));
                    if(this.participacion.get(i).equals("Total")){
                        int sumarY;
                        if(figura.getPosicionesY().get(pc.pcFigura(i))<elementos.get(i).getPosicionesY().get(pc.pcEntidad(i))){
                            sumarY=3;
                        }
                        else{
                            sumarY=-3;
                        }
                        if(figura.getPosicionesX().get(pc.pcFigura(i))<elementos.get(i).getPosicionesX().get(pc.pcEntidad(i))){
                            
                                Line linea2 = new Line(elementos.get(i).getPosicionesX().get(pc.pcEntidad(i))-3,elementos.get(i).getPosicionesY().get(pc.pcEntidad(i))+sumarY,
                                                   figura.getPosicionesX().get(pc.pcFigura(i))-3,figura.getPosicionesY().get(pc.pcFigura(i))+sumarY);                        
                               this.lineasRelacion.add(linea2);                                

                            
                           
                        }
                        else{
                            Line linea2 = new Line(elementos.get(i).getPosicionesX().get(pc.pcEntidad(i))+3,elementos.get(i).getPosicionesY().get(pc.pcEntidad(i))+sumarY,
                                                figura.getPosicionesX().get(pc.pcFigura(i))+3,figura.getPosicionesY().get(pc.pcFigura(i))+sumarY);                        
                            this.lineasRelacion.add(linea2);                            
                        }

                    
                    }
                    
                    
                    int puntoCuartoX = (elementos.get(i).getPosicionesX().get(pc.pcEntidad(i))-figura.getPosicionesX().get(pc.pcFigura(i)))/8;
                    int puntoCuartoY = (elementos.get(i).getPosicionesY().get(pc.pcEntidad(i))-figura.getPosicionesY().get(pc.pcFigura(i)))/8;
                    cardinalidades.add(new Text(figura.getPosicionesX().get(pc.pcFigura(i))+puntoCuartoX+7,figura.getPosicionesY().get(pc.pcFigura(i))+puntoCuartoY,stringCardinalidades.get(i)));
                    this.lineasRelacion.add(linea);
                                   
                }               
            }
            if(elementos.size()==1){
                PuntoCercano pc = new PuntoCercano(this.figura,this.elementos); 
                for(int i = 0; i<2;i++){
                    Line linea = new Line(elementos.get(0).getPosicionesX().get(pc.pcEntidad(i)),elementos.get(0).getPosicionesY().get(pc.pcEntidad(i)),
                    figura.getPosicionesX().get(pc.pcFigura(i)),figura.getPosicionesY().get(pc.pcFigura(i)));
                    int puntoCuartoX = (elementos.get(0).getPosicionesX().get(pc.pcEntidad(i))-figura.getPosicionesX().get(pc.pcFigura(i)))/8;
                    int puntoCuartoY = (elementos.get(0).getPosicionesY().get(pc.pcEntidad(i))-figura.getPosicionesY().get(pc.pcFigura(i)))/8;
                    cardinalidades.add(new Text(figura.getPosicionesX().get(pc.pcFigura(i))+puntoCuartoX+7,figura.getPosicionesY().get(pc.pcFigura(i))+puntoCuartoY,stringCardinalidades.get(i)));                    
                    lineasRelacion.add(linea);
                    
                    if(this.participacion.get(i).equals("Total")){
                        if(figura.getPosicionesX().get(pc.pcFigura(i))<elementos.get(i).getPosicionesX().get(pc.pcEntidad(i))){
                            
                                Line linea2 = new Line(elementos.get(i).getPosicionesX().get(pc.pcEntidad(i))-3,elementos.get(i).getPosicionesY().get(pc.pcEntidad(i))+3,
                                                   figura.getPosicionesX().get(pc.pcFigura(i))-3,figura.getPosicionesY().get(pc.pcFigura(i))+3);                        
                               this.lineasRelacion.add(linea2);                                

                            
                           
                        }
                        else{
                            Line linea2 = new Line(elementos.get(i).getPosicionesX().get(pc.pcEntidad(i))+3,elementos.get(i).getPosicionesY().get(pc.pcEntidad(i))+3,
                                                figura.getPosicionesX().get(pc.pcFigura(i))+3,figura.getPosicionesY().get(pc.pcFigura(i))+3);                        
                            this.lineasRelacion.add(linea2);                            
                        }

                    
                    }             
                    

                } 
            }            
        }
        this.crearLineasunionAtributos();
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
        retornar.addAll(cardinalidades);
        return retornar;
    }    
    public ArrayList<Atributo> retornarAtributosDeAtributos(int i){
        return this.atributos.get(i).getAtributos();
    }
    
    public ArrayList<Text> getCardinalidades() {
        return cardinalidades;
    }
    
    public String getTipoCardinalidad() {
        return tipoCardinalidad;
    }

    public ArrayList<String> getStringCardinalidades() {
        return stringCardinalidades;
    }

    public void setStringCardinalidades(ArrayList<String> stringCardinalidades) {
        this.stringCardinalidades = stringCardinalidades;
    }

    
    
    
    public void actualizarPosicion(int x,int y){
        this.posX=x;
        this.posY=y;
        this.crearRelacion();
    }
    @Override
    public Object clone() throws CloneNotSupportedException{
        Object obj=null;
        try{
            obj=super.clone();
        }
        catch(CloneNotSupportedException ex){
            System.out.println("no duplicado");
        }
        return obj;
    }    
}
