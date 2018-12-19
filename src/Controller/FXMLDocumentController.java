package Controller;
import Clases.Agregacion;
import Clases.Atributo;
import Clases.Diagrama;
import Clases.Elemento;
import Clases.Entidad;
import Clases.Herencia;
import Clases.Relacion;
import Clases.RespaldoDiagrama;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;


/**
 * Clase donde se crean los elementos del diagrama. Se accede a ventanas
 * Se dibujan los elementos en el Panel de dibujo. etc.
 * @author Cristobal Muñoz Salinas
 */
public class FXMLDocumentController implements Initializable {
      
    
    @FXML private Pane panelDibujo; // panel donde se dibujan las entidades y relaciones.
    private String textoEntidad; //guarda el nombre de la entidad ingresada por el usuario.    
    private String textoRelacion;  //guarda el nombre de la relacion ingresada por el usuario.   
    private String textoAtributo; //Guarda el nombre del atributo ingresado por el usuario.
    private String textoAgregacion;
    @FXML private CheckBox puntosDeControl; //Activa y desactiva los puntos de control.    
    @FXML private ArrayList<Elemento> elementosDisponibles;//Guarda las entidades seleccionadas para crear una relación.

    
    
    private String tipoAtributo;//Guarda el tipo de atributo

    private String comboBoxEntidadesRelaciones;//Guarda el nombre del elemento en que se guardará el atributo.
    private String destinoAtributo;//Guarda el destino del atributo(en entidad, relación o atributo).
    private Diagrama diagrama = new Diagrama(); //se crea un diagrama donde se guardan las entidades y relaciones.



    private boolean crearEntidad = false;//Permite saber si el usuario seleccionó el boton para crear una entidad
    private boolean moverFiguras = true;//Se utiliza para activar o desactivar el movimiento de las figuras(entidades, relaciones, atributos y herencias).
    private boolean crearRelacion = false;//Permite saber si el usuario seleccionó el botón para crear una relacion.
    private boolean crearAtributo = false;//Permite saber si el usuario seleccionó el botón para crear el atributo.
    private boolean crearHerencia = false;//Permite saber si el usuario seleccionó el botón para crear la herencia.
    private boolean crearAgregacion = false;
    private boolean atributoEncontradoMover = false;
    private boolean entidadEncontradaMover=false;//Permite saber si el usuario seleccionó una entidad, permitiendo ser movida.
    private boolean relacionEncontradaMover = false;//Permite saber si el usuario seleccionó una relación, permitiendo ser movida.        
    private boolean herenciaEncontradaMover = false;//Permite saber si el usuario seleccionó una herencia, permitiendo ser movida.
    private boolean agregacionEncontradaMover = false;  
    private boolean entidadDebil = false;//Permite saber si la entidad que se quiere crear es debil o fuerte.    
    private Entidad  entidadAuxiliar;// Guarda la entidad original antes de ser movida.
    private Atributo atributoAuxiliar; //Guarda el atributo original antes de ser movido.
    private Relacion relacionAuxiliar;//Guarda la relacion seleccionada para mover.
    private Herencia herenciaAuxiliar;//Guarda la herencia original antes de ser movida.
    private Relacion relacionAgregacion; //se guarda la relacion que sera parte de la agregación.
    private Agregacion agregacionAuxiliar;//Guarda la agregacion original antes de ser movida.
    FXMLDocumentController controlador;
    RespaldoDiagrama listaDiagramas = new RespaldoDiagrama();//Guarda los diagramas con el proposito de rehacer y deshacer.
    int desHacer = 0;//Guarda la posción del diagrama al que se llamó para volver a crearlo o rehacerlo.
    private boolean relacionDebil;
    ArrayList<String> participacion;
    boolean figuraEnMovimiento=false;
    
    
    
    /**
     * Guarda el diagrama una ves que se haya hecho una modificaciíon
     */
    public void guardarDiagrama(){
       /* listaDiagramas.removerPosteriores(desHacer);
        listaDiagramas.agregarDiagrama(diagrama);
        desHacer = listaDiagramas.tamano()-1;*/
    }
    
    
    /**
     * Se aumenta la variable desHacer y
     * accede a la posición en la lista de diagramas.
     */
    @FXML
    private void deshacer(){
        /*
        desHacer++;
        if(desHacer>=listaDiagramas.tamano()){
            desHacer = listaDiagramas.tamano()-1;
        }        
        diagrama =listaDiagramas.retornarDiagrama(desHacer);
        actualizarPanel();   */     
    }
    /**
     * Se decrementa la variable desHacer y
     * accede a la posición en la lista de diagramas.
     */    
    @FXML
    private void rehacer(){
        /*
        desHacer--;
        if(desHacer<0){
            desHacer=0;
        }

        diagrama =listaDiagramas.retornarDiagrama(desHacer);
        actualizarPanel();
        */
    }
    
    private void modificarCardinalidad(double posX,double posY){
        for(int i = 0; i<diagrama.getRelaciones().size();i++){
            for(int j=0; j<diagrama.getRelaciones().get(i).getCardinalidades().size();j++){
                double x=diagrama.getRelaciones().get(i).getCardinalidades().get(j).getX();
                double y=diagrama.getRelaciones().get(i).getCardinalidades().get(j).getY();
                if((x-10)<=posX &&(x+20)>=posX&&(y+10)>=posY&&(y-20)<=posY){
                    if(diagrama.getRelaciones().get(i).getCardinalidades().get(j).getText().equals("N")){
                        diagrama.getRelaciones().get(i).getStringCardinalidades().set(j,"1");
                        diagrama.getRelaciones().get(i).getCardinalidades().get(j).setText("1");
                    }
                    else{
                        diagrama.getRelaciones().get(i).getStringCardinalidades().set(j,"N");
                        diagrama.getRelaciones().get(i).getCardinalidades().get(j).setText("N");
                    }
                }
            }
        }
    }


    /**
     * Crea las entidades, relaciones, atributos y herencias una ves conocida la posicion
     * donde hizo click el usuario en el panel de dibujo. Posteriormente, se guarda
     * el elemento creado en el diagrama.
     * @param event 
     */
    @FXML
    private void crear(MouseEvent event) throws IOException{  
        
        if(event.getButton().equals(MouseButton.PRIMARY)){
            if(event.getClickCount()==2){
                modificarCardinalidad(event.getX(),event.getY());
            }
        }
        
        if(crearEntidad){
            Entidad nueva = new Entidad(false,(int)event.getX(),(int)event.getY(),textoEntidad);
            
            if(entidadDebil){
                nueva = new Entidad(true,(int)event.getX(),(int)event.getY(),textoEntidad); 
                
            }
            
            nueva.crearFigura();
            if(puntosDeControl.isSelected()){
                panelDibujo.getChildren().addAll(nueva.getPuntosDeControl());
            }
            diagrama.getElementos().add(nueva);  
            ventanaAtributosEntidad(textoEntidad,entidadDebil); 

            guardarDiagrama(); 
            
        }
        if(crearRelacion){
            Relacion nueva = new Relacion(relacionDebil,(int)event.getX(),(int)event.getY(),textoRelacion);
            for(int i = 0; i<elementosDisponibles.size();i++){
                    nueva.getElementos().add(elementosDisponibles.get(i));
            }
            
            nueva.setParticipacion(participacion);
            nueva.crearRelacion();
            diagrama.getRelaciones().add(nueva);
            guardarDiagrama(); 
        }
        if(crearHerencia){
            Herencia nueva = new Herencia(herenciaAuxiliar.getEntidadPadre(),herenciaAuxiliar.getEntidadesHijas(),
                    herenciaAuxiliar.getTipoHerencia(),(int)event.getX(),(int)event.getY());
            nueva.crearHerencia();
            diagrama.getHerencias().add(nueva);
            guardarDiagrama(); 
            
        }
        if(crearAgregacion){
            Agregacion nueva = new Agregacion(relacionAgregacion,textoAgregacion,(int)event.getX(),(int)event.getY());
            nueva.crearFigura();
            diagrama.getElementos().add(nueva);
            guardarDiagrama();
        }
        if(crearAtributo){
            
                if(destinoAtributo.equals("Entidad")){
                    
                    Atributo nueva = new Atributo(tipoAtributo,(int)event.getX(),(int)event.getY(),textoAtributo,"Entidad",comboBoxEntidadesRelaciones);
                    for(int i=0;i<diagrama.getElementos().size();i++){
                        if(comboBoxEntidadesRelaciones.equals(diagrama.getElementos().get(i).getNombre())){
                            ((Entidad)diagrama.getElementos().get(i)).agregarAtributo(nueva);
                            diagrama.getElementos().get(i).crearFigura();
                            atributoAnadidoEntidadAgregacion(((Entidad)diagrama.getElementos().get(i)),nueva);
                            diagrama.getAtributos().add(nueva);
                            if(entidadDebil){
                                relacionEntidadDebil(comboBoxEntidadesRelaciones);
                                entidadDebil=false;
                            }
                            
                        }
                    }
                    
                    
                }
                if(destinoAtributo.equals("Relación")){

                    Atributo nueva = new Atributo(tipoAtributo,(int)event.getX(),(int)event.getY(),textoAtributo,"Relación",comboBoxEntidadesRelaciones);
                    for(int i=0;i<diagrama.getRelaciones().size();i++){
                        if(comboBoxEntidadesRelaciones.equals(diagrama.getRelaciones().get(i).getNombre())){
                            
                            diagrama.getRelaciones().get(i).agregarAtributo(nueva);
                            diagrama.getRelaciones().get(i).crearLineasunionAtributos(); 
                            atributoAnadidoRelacionAgregacion(diagrama.getRelaciones().get(i),nueva);
                            diagrama.getAtributos().add(nueva);
                        }
                    }
                   

                }
                if(destinoAtributo.equals("Atributo")){
                    
                    Atributo nueva = new Atributo((String)tipoAtributo,(int)event.getX(),(int)event.getY(),textoAtributo,"Compuesto",comboBoxEntidadesRelaciones);

                    for(int i=0; i< diagrama.getElementos().size();i++){
                        if(diagrama.getElementos().get(i) instanceof Entidad){
                            for(int j = 0; j < ((Entidad)diagrama.getElementos().get(i)).getAtributos().size();j++){
                                if(((Entidad)diagrama.getElementos().get(i)).getAtributos().get(j).getNombre().equals(comboBoxEntidadesRelaciones)){
                                    ((Entidad)diagrama.getElementos().get(i)).getAtributos().get(j).getAtributos().add(nueva);
                                    ((Entidad)diagrama.getElementos().get(i)).getAtributos().get(j).crearLineasunionAtributos();
                                    atributoAnadidoEntidadAgregacion(((Entidad)diagrama.getElementos().get(i)),nueva);

                                }
                            }
                        }
                    }
                    for(int i=0; i< diagrama.getRelaciones().size();i++){
                        for(int j = 0; j < diagrama.getRelaciones().get(i).getAtributos().size();j++){
                            if(diagrama.getRelaciones().get(i).getAtributos().get(j).getNombre().equals(comboBoxEntidadesRelaciones)){
                                diagrama.getRelaciones().get(i).getAtributos().get(j).getAtributos().add(nueva);
                                diagrama.getRelaciones().get(i).getAtributos().get(j).crearLineasunionAtributos();
                                atributoAnadidoRelacionAgregacion(diagrama.getRelaciones().get(i),nueva);
                            }
                        }
                    }
                    diagrama.getAtributos().add(nueva);
                }
                guardarDiagrama(); 
        }
        
        actualizarPanel();
                                
        crearEntidad=false;
        moverFiguras = true;
        crearRelacion = false;
        crearAtributo = false;
        crearHerencia = false;
        crearAgregacion = false;
    }

    private void atributoAnadidoRelacionAgregacion(Relacion relacion,Atributo atributo){
            for(int i = 0; i<diagrama.getElementos().size();i++){
                if(diagrama.getElementos().get(i) instanceof Agregacion){
                    if(((Agregacion)diagrama.getElementos().get(i)).getRelacion().equals(relacion)){
                        if(atributo.getPosX()<((Agregacion)diagrama.getElementos().get(i)).getPosX()){
                            int sumar=((Agregacion)diagrama.getElementos().get(i)).getPosX()-atributo.getPosX();
                            atributo.actualizarPosicion(atributo.getPosX()+sumar, atributo.getPosY());
                        }
                        if(atributo.getPosY()<((Agregacion)diagrama.getElementos().get(i)).getPosY()){
                            int sumar=((Agregacion)diagrama.getElementos().get(i)).getPosY()-atributo.getPosY();
                            atributo.actualizarPosicion(atributo.getPosX(), atributo.getPosY()+sumar+30);
                        }                        
                    }
                }
            }         
    }
    private void atributoAnadidoEntidadAgregacion(Entidad entidad,Atributo atributo){
            for(int i = 0; i<diagrama.getElementos().size();i++){
                if(diagrama.getElementos().get(i) instanceof Agregacion){
                    for(int j = 0; j<((Agregacion)diagrama.getElementos().get(i)).getRelacion().getElementos().size();j++){
                        if(((Agregacion)diagrama.getElementos().get(i)).getRelacion().getElementos().get(j) instanceof Entidad){
                            if(((Entidad)((Agregacion)diagrama.getElementos().get(i)).getRelacion().getElementos().get(j)).equals(entidad)){
                                if(atributo.getPosX()<((Agregacion)diagrama.getElementos().get(i)).getPosX()){
                                    int sumar=((Agregacion)diagrama.getElementos().get(i)).getPosX()-atributo.getPosX();
                                    atributo.actualizarPosicion(atributo.getPosX()+sumar, atributo.getPosY());
                                }
                                if(atributo.getPosY()<((Agregacion)diagrama.getElementos().get(i)).getPosY()){
                                    int sumar=((Agregacion)diagrama.getElementos().get(i)).getPosY()-atributo.getPosY();
                                    atributo.actualizarPosicion(atributo.getPosX(), atributo.getPosY()+sumar+30);
                                }                        
                            
                            }
                        }
                    }
                }
            }
        
    }
    
    
    
    
    
    /**
     * Se mueve la figura seleccionada.
     * Primero se revisa si en la posicion en la que se encuentra el mouse(cuando se presiona el boton izquierdo), hay
     * un elemento. si es así, se guarda el elemento en una variable auxiliar y se le asigna true a la variable boleana correspondiente al tipo de figura a mover.
     * Finalmente, se actualiza la posición de la figura hasta que el usuario deje de presionar el boton izquierdo del mouse.
     * @param event 
     */
    @FXML
    private void moverEnPanel(MouseEvent event){
        if(moverFiguras){
            int posX = (int)event.getX();
            int posY = (int)event.getY();
            if (0<=posX && posX<=1050 && 0<=posY && posY<= 687){
                if((relacionEncontradaMover == entidadEncontradaMover)&&herenciaEncontradaMover == atributoEncontradoMover&&atributoEncontradoMover==agregacionEncontradaMover){
                    for (int i=0; i<diagrama.getElementos().size();i++){
                        if(diagrama.getElementos().get(i) instanceof Entidad){
                            if(entidadEnAgregacion((Entidad)diagrama.getElementos().get(i),posX,posY,0)){
                                ArrayList<Integer> x=diagrama.getElementos().get(i).getPosicionesX();
                                ArrayList<Integer> y=diagrama.getElementos().get(i).getPosicionesY();
                                if (x.get(0) < posX && x.get(1) > posX && y.get(0) < posY && y.get(x.size()-1) > posY) {
                                    entidadAuxiliar=(Entidad)diagrama.getElementos().get(i);
                                    entidadEncontradaMover = true;
                                    figuraEnMovimiento=true;
                                }
                            }
                        }
                    }
                    for(int i=0; i<diagrama.getRelaciones().size();i++){
                        if(areaFiguras((int)event.getX(), (int)event.getY(), i)& relacionEnAgregacion(diagrama.getRelaciones().get(i),posX,posY,0)){                           
                            relacionAuxiliar = diagrama.getRelaciones().get(i);
                            relacionEncontradaMover = true;
                            figuraEnMovimiento=true;
                        }
                    } 
                    for(int i = 0; i<diagrama.getAtributos().size();i++){
                        if(atributoEnAgregacion(diagrama.getAtributos().get(i),posX,posY,0)){
                            
                            ArrayList<Integer> x=diagrama.getAtributos().get(i).posicionesX();
                            ArrayList<Integer> y=diagrama.getAtributos().get(i).posicionesY();
                            if (x.get(0) <= posX && x.get(1) >= posX && y.get(2) <= posY && y.get(3) >= posY) {                                
                                atributoAuxiliar = diagrama.getAtributos().get(i);
                                atributoEncontradoMover = true;
                                figuraEnMovimiento=true;
                            } 
                        }
                    }                    
                    for(int i = 0; i< diagrama.getHerencias().size();i++){
                        ArrayList<Integer> x = diagrama.getHerencias().get(i).posicionesXFigura();
                        ArrayList<Integer> y = diagrama.getHerencias().get(i).posicionesYFigura();
                        if(x.get(0)<=posX && posX<=x.get(1) && y.get(2)<=posY && posY<=y.get(3)){
                            herenciaEncontradaMover = true;
                            herenciaAuxiliar = diagrama.getHerencias().get(i);  
                            figuraEnMovimiento=true;
                        }
                    }  
                    for (int i=0; i<diagrama.getElementos().size();i++){
                        if(diagrama.getElementos().get(i) instanceof Agregacion){
                            if(agregacionEnAgregacion((Agregacion)diagrama.getElementos().get(i))){
                                ArrayList<Integer> x=diagrama.getElementos().get(i).getPosicionesX();
                                ArrayList<Integer> y=diagrama.getElementos().get(i).getPosicionesY();
                                if (x.get(0) < posX && x.get(1) > posX && y.get(0) < posY && y.get(x.size()-1) > posY) {
                                    if(figuraEnMovimiento==false){
                                        System.out.println("Entre");
                                        agregacionAuxiliar=(Agregacion)diagrama.getElementos().get(i);
                                        agregacionEncontradaMover = true;
                                    }

                                }
                            }
                    
                        }                    
                    } 


                }
                else{                    
                    if(entidadEncontradaMover){
                        if(entidadEnAgregacion(entidadAuxiliar,posX,posY,1)){
                            entidadAuxiliar.actualizarPosicion(posX, posY);   
                        }
                    }
                    if(relacionEncontradaMover){
                        if(relacionEnAgregacion(relacionAuxiliar,posX,posY,1)){
                            relacionAuxiliar.actualizarPosicion(posX, posY); 
                        }
                    }
                    if(herenciaEncontradaMover){
                        herenciaAuxiliar.actualizarPosicion(posX, posY);                  
                    }
                    if(agregacionEncontradaMover){                                       
                        agregacionAuxiliar.actualizarPosicion(posX, posY);                                              
                    }                    
                    if(atributoEncontradoMover){
                        if(atributoEnAgregacion(atributoAuxiliar,posX,posY,1)){
                            atributoAuxiliar.actualizarPosicion(posX, posY);  
                        }
                    }
                    actualizarPanel();
                }
            }
        }
    }
    
    /**
     * Limpia el panel de dibujo
     * Luego agrega todas las figuras una vez hecha una modificación.
     */
    public void actualizarPanel(){
        panelDibujo.getChildren().clear();
        for(int i = 0; i<diagrama.getElementos().size();i++){
            diagrama.getElementos().get(i).crearFigura();
            panelDibujo.getChildren().addAll(diagrama.getElementos().get(i).getDibujoFigura());
            
            if(puntosDeControl.isSelected()){
                panelDibujo.getChildren().addAll(diagrama.getElementos().get(i).getPuntosDeControl());
            }
        }
        
        for(int i = 0; i < diagrama.getRelaciones().size();i++){
                diagrama.getRelaciones().get(i).crearRelacion();
            
            panelDibujo.getChildren().addAll(diagrama.getRelaciones().get(i).dibujoRelacion());
            if(puntosDeControl.isSelected()){
                panelDibujo.getChildren().addAll(diagrama.getRelaciones().get(i).getFigura().getPuntosControl());
            } 
        }
        for(int i = 0; i<diagrama.getHerencias().size();i++){
            diagrama.getHerencias().get(i).crearHerencia();
            panelDibujo.getChildren().addAll(diagrama.getHerencias().get(i).dibujoHerencia());
        }
        for(int i = 0; i<diagrama.getAtributos().size();i++){
            diagrama.getAtributos().get(i).crearAtributo();
            panelDibujo.getChildren().addAll(diagrama.getAtributos().get(i).dibujoAtributo());
            if(puntosDeControl.isSelected()){
                panelDibujo.getChildren().addAll(diagrama.getAtributos().get(i).getFigura().getPuntosControl());
                for(int j = 0; j<diagrama.getAtributos().get(i).getAtributos().size();j++){
                    diagrama.getAtributos().get(i).getAtributos().get(j).crearAtributo();
                    panelDibujo.getChildren().addAll(diagrama.getAtributos().get(i).getAtributos().get(j).getFigura().getPuntosControl());
                }
                
            } 
        }

    }   
    
    /**
     * Revisa si una entidad esta agregada en una agregacion.
     * @param entidad Entidad que se busca en las agregaciones.
     * @return true si la encuentra y false si no la encuentra.
     */
    private boolean entidadEnAgregacion(Entidad entidad,int posX,int posY,int limitacion){
        if(limitacion==0){ 
            boolean encontrado=true;
            for(int i = 0; i<diagrama.getElementos().size();i++){
                    if(diagrama.getElementos().get(i) instanceof Agregacion){
                    Agregacion agregacion = (Agregacion)diagrama.getElementos().get(i);
                    if((agregacion.getRelacion().getElementos().get(0).equals(entidad))||(agregacion.getRelacion().getElementos().get(1).equals(entidad))){
                        ArrayList<Integer> n=agregacion.getPosicionesX();
                        ArrayList<Integer> n2=agregacion.getPosicionesY();
                        encontrado=false;
                        if(n.get(0)-5 <= posX && n.get(1)+5 >= posX && n2.get(0)-5 <= posY && n2.get(3)+5 >= posY){
                            return true;
                        }
                    }  
                }
            }
            return encontrado==true;
        }
        else{
            boolean encontrado=true;
            for(int i = 0; i<diagrama.getElementos().size();i++){
                    if(diagrama.getElementos().get(i) instanceof Agregacion){
                    Agregacion agregacion = (Agregacion)diagrama.getElementos().get(i);
                    if((agregacion.getRelacion().getElementos().get(0).equals(entidad))||(agregacion.getRelacion().getElementos().get(1).equals(entidad))){
                        ArrayList<Integer> n=agregacion.getPosicionesX();
                        ArrayList<Integer> n2=agregacion.getPosicionesY();
                        encontrado=false;
                        if(n.get(0)+5 <= posX  && n2.get(0)+40 <= posY ){
                            return true;
                        }
                    }  
                }
            }
            return encontrado==true;
        }    
    }
    private boolean atributoEnAgregacion(Atributo atributo,int posX,int posY,int limitacion){
        boolean encontrado=true;

        
        for(int i = 0; i<diagrama.getElementos().size();i++){
                    double dist=Math.sqrt(Math.pow(atributo.getFigura().getPosicionesY().get(0)-atributo.getFigura().getPosicionesY().get(2), 2));
            if(diagrama.getElementos().get(i) instanceof Agregacion){
                Agregacion agregacion =(Agregacion)diagrama.getElementos().get(i);
                for(int j = 0; j < agregacion.getRelacion().getAtributos().size();j++){
                    Atributo atributo1 = agregacion.getRelacion().getAtributos().get(j);
                    if(atributo1.equals(atributo)){
                        ArrayList<Integer> n=agregacion.getPosicionesX();
                        ArrayList<Integer> n2=agregacion.getPosicionesY();
                        encontrado=false;
                        if(limitacion==0){
                            if(n.get(0)-5 <= posX && n.get(1)+5 >= posX && n2.get(0)-5 <= posY-dist && n2.get(3)+5 >= posY){
                                return true;
                            }   
                        }
                        if(limitacion==1){
                            if(n.get(0)+5 <= posX  && n2.get(0)+40 <= posY-dist ){
                                return true;
                            }
                        }
                        
                    }
                    for(int k=0;k<atributo1.getAtributos().size();k++){
                        atributo1=atributo1.getAtributos().get(k);
                        if(atributo1.equals(atributo)){
                            ArrayList<Integer> n=agregacion.getPosicionesX();
                            ArrayList<Integer> n2=agregacion.getPosicionesY();
                            encontrado=false;
                            if(limitacion==0){
                                if(n.get(0)-5 <= posX && n.get(1)+5 >= posX && n2.get(0)-5 <= posY-dist && n2.get(3)+5 >= posY){
                                    return true;
                                }   
                            }
                            if(limitacion==1){
                                if(n.get(0)+5 <= posX  && n2.get(0)+40 <= posY -dist){
                                    return true;
                                }
                            }                          

                        }
                    }
                    
                }
                for(int j = 0;j<agregacion.getRelacion().getElementos().size();j++){
                    if(agregacion.getRelacion().getElementos().get(j) instanceof Entidad){
                        for(int k = 0; k < ((Entidad)agregacion.getRelacion().getElementos().get(j)).getAtributos().size();k++){
                            Atributo atributo1 = ((Entidad)agregacion.getRelacion().getElementos().get(j)).getAtributos().get(k);
                            if(atributo1.equals(atributo)){
                                ArrayList<Integer> n=agregacion.getPosicionesX();
                                ArrayList<Integer> n2=agregacion.getPosicionesY();
                                encontrado=false;
                                if(limitacion==0){
                                    if(n.get(0)-5 <= posX && n.get(1)+5 >= posX && n2.get(0)-5 <= posY-dist && n2.get(3)+5 >= posY){
                                        return true;
                                    }   
                                }
                                if(limitacion==1){
                                    if(n.get(0)+5 <= posX  && n2.get(0)+40 <= posY-dist ){
                                        return true;
                                    }
                                }
                            }
                            for(int l=0;l<atributo1.getAtributos().size();l++){
                                atributo1=atributo1.getAtributos().get(l);
                                if(atributo1.equals(atributo)){
                                    ArrayList<Integer> n=agregacion.getPosicionesX();
                                    ArrayList<Integer> n2=agregacion.getPosicionesY();
                                    encontrado=false;
                                    if(limitacion==0){
                                        if(n.get(0)-5 <= posX && n.get(1)+5 >= posX && n2.get(0)-5 <= posY-dist && n2.get(3)+5 >= posY){
                                            return true;
                                        }   
                                    }
                                    if(limitacion==1){
                                        if(n.get(0)+5 <= posX  && n2.get(0)+40 <= posY-dist ){
                                            return true;
                                        }
                                    }
                                }
                            }

                        }                        
                    }
                }
                
                
                
                
                
                
                
                
                
            }
        }
        
        return encontrado==true;
        
        
        
    }
    
    
    
    
    /**
     * Revisa si una relacion esta agregada en una agregacion.
     * @param relacion Relacion que se busca en las agregaciones.
     * @return true si la encuentra y false si no la encuentra.
     */    
    private boolean relacionEnAgregacion(Relacion relacion,int posX,int posY,int limitacion){
         if(limitacion==0){
            boolean encontrado=true;

            for(int i = 0; i<diagrama.getElementos().size();i++){
                if(diagrama.getElementos().get(i) instanceof Agregacion){
                    Agregacion agregacion = (Agregacion)diagrama.getElementos().get(i);
                    if(agregacion.getRelacion().equals(relacion)){
                        
                        encontrado=false;
                        ArrayList<Integer> n=agregacion.getPosicionesX();
                        ArrayList<Integer> n2=agregacion.getPosicionesY();
                        if(n.get(0)-5 <= posX && n.get(1)+5 >= posX && n2.get(0)-5 <= posY && n2.get(3)+5 >= posY){
                            return true;
                        }
                    }  
                }
            }
            return encontrado==true;
         }
         else{
            boolean encontrado=true;
            for(int i = 0; i<diagrama.getElementos().size();i++){
                if(diagrama.getElementos().get(i) instanceof Agregacion){
                    
                    Agregacion agregacion = (Agregacion)diagrama.getElementos().get(i);
                    
                    if(agregacion.getRelacion().equals(relacion)){
                        double dist = Math.sqrt(Math.pow(agregacion.getRelacion().getPosY()-agregacion.getRelacion().getFigura().getPosicionesY().get(2), 2));
                        encontrado=false;
                        ArrayList<Integer> n=agregacion.getPosicionesX();
                        ArrayList<Integer> n2=agregacion.getPosicionesY();
                        if(n.get(0)+5 <= posX && n2.get(0)+40 <= posY-dist){
                            return true;
                        }
                    }  
                }
            }
            return encontrado==true;             
         }
    }    
    
    /**
     * Una vez que el usuario deja de presionar el elemento en el panel, este se guarda oficialmente
     * en el diagrama en la nueva posición.
     * @param event 
     */
    @FXML
    public void mouseNoPresionado(MouseEvent event){
        if(moverFiguras==true){
            actualizarPanel();
            relacionEncontradaMover = false;
            entidadEncontradaMover= false;
            atributoEncontradoMover = false;
            herenciaEncontradaMover = false;
            agregacionEncontradaMover = false;
            figuraEnMovimiento=false;
            guardarDiagrama();   
        }    
    }
    
    /**
     * Revisa si el punto (x,y) se encuentra dentro del area de
     * la relación seleccionada.
     * @param x 
     * @param y
     * @param indice indice de la relación en el diagrama, con este se revisa si el punto se encuentra dentro 
     *                  de la relación.
     *               
     * @return true si lo esta, false si no lo esta.
     */
    private boolean areaFiguras(int x, int y, int indice){
        int tamano = diagrama.getRelaciones().get(indice).getElementos().size();
        ArrayList<Integer> posX=diagrama.getRelaciones().get(indice).getFigura().getPosicionesX();
        ArrayList<Integer> posY=diagrama.getRelaciones().get(indice).getFigura().getPosicionesY();

        if(tamano==3){
            if(posX.get(0)<=x && x<=posX.get(1) && posY.get(2)<=y && y<=posY.get(0)){
                return true;
            }             
        }
        else{
            if(posX.get(0)<=x && x<=posX.get(1) && posY.get(2)<=y && y<=posY.get(3)){
                return true;
            }  
        }
        return false;
    }    
    private boolean dentroDelPanel(ArrayList<Integer> x,ArrayList<Integer> y){
        int contador = 0;
        for(int i = 0; i<x.size();i++){
            if(0<=x.get(i) && x.get(i)<=1050 && 0<=y.get(i) && y.get(i)<= 687){
                contador++;
            }            
        }
        return contador==x.size();
        

    }
    private boolean agregacionEnAgregacion(Agregacion agregacion){
        
        for(int i = 0; i<diagrama.getElementos().size();i++){
            if(diagrama.getElementos().get(i) instanceof Agregacion){
                if(((Agregacion)diagrama.getElementos().get(i)).getRelacion().getElementos().get(0).equals(agregacion)
                        || ((Agregacion)diagrama.getElementos().get(i)).getRelacion().getElementos().get(0).equals(agregacion)){
                    return true;
                }
            }
        }
        return true;
        
        
    }
    private void moverAgregacionEnElementos(Agregacion agregacion){
        for(int i =0; i<diagrama.getRelaciones().size();i++){
            for(int j = 0; j<diagrama.getRelaciones().get(i).getElementos().size(); j++){
                if(diagrama.getRelaciones().get(i).getElementos().get(j).getNombre().equals(agregacion.getNombre())){
                    
                    diagrama.getRelaciones().get(i).getElementos().set(j, agregacion);
                    diagrama.getRelaciones().get(i).crearRelacion();
                }
            }
        }        
    }
    private void moverEntidadEnElementos(Entidad entidad){
        for(int i =0; i<diagrama.getRelaciones().size();i++){
            for(int j = 0; j<diagrama.getRelaciones().get(i).getElementos().size(); j++){
                if(diagrama.getRelaciones().get(i).getElementos().get(j).getNombre().equals(entidad.getNombre())){
                    
                    diagrama.getRelaciones().get(i).getElementos().set(j, entidad);
                    diagrama.getRelaciones().get(i).crearRelacion();
                }
            }
        }
        for(int i =0; i<diagrama.getHerencias().size();i++){
            if(entidad.getNombre().equals(diagrama.getHerencias().get(i).getEntidadPadre().getNombre())){
                diagrama.getHerencias().get(i).setEntidadPadre(entidad);
                diagrama.getHerencias().get(i).crearHerencia();
            }
            
            for(int j = 0; j<diagrama.getHerencias().get(i).getEntidadesHijas().size(); j++){
                if(diagrama.getHerencias().get(i).getEntidadesHijas().get(j).getNombre().equals(entidad.getNombre())){
                    
                    diagrama.getHerencias().get(i).getEntidadesHijas().set(j, entidad);
                    diagrama.getHerencias().get(i).crearHerencia();
                }
            }
        }
        
    }
    

    /**
     * Limpia el panel de dibujo por completo
     * tambien se limpia el diagrama
     */
    @FXML
    private void limpiarPagina(){
        panelDibujo.getChildren().clear();
        if(diagrama.cantidadElementosDiagrama()>=1){
            
            diagrama.getElementos().clear();
            diagrama.getRelaciones().clear();
            diagrama.getAtributos().clear();  
            diagrama.getHerencias().clear(); 

        }
        else{
            mensaje("Ya esta todo limpio...");
        }

    }     

    private void removerAtributoEnDiagrama(Atributo atributo){

            for(int i  = 0; i<diagrama.getAtributos().size(); i++){
                if(atributo.getGuardadoEn().equals(diagrama.getAtributos().get(i).getGuardadoEn())){
                    if(atributo.getNombreOrigenAtributo().equals(diagrama.getAtributos().get(i).getNombreOrigenAtributo())){
                        if(atributo.getNombre().equals(diagrama.getAtributos().get(i).getNombre())){
                            diagrama.getAtributos().remove(i);
                        }
                    }
                }
            }            
        
        

    }
     
    /**
     * Exporta el panel en un formato determinado
     */
    @FXML
    private void exportarPanel(){
        FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF", "*.pdf"),
                new FileChooser.ExtensionFilter("PNG", "*.png"));                                   
             File enviar = fileChooser.showSaveDialog(panelDibujo.getScene().getWindow());
             if(enviar!= null){
                 String nombre = enviar.getName();
                 String extencion = nombre.substring(nombre.lastIndexOf(".")+1,enviar.getName().length());
                 System.out.println(extencion);
                if("png".equals(extencion)){
                    WritableImage wimi = new WritableImage(1274,683);
                    panelDibujo.snapshot(null, wimi);
                    try{
                        ImageIO.write(SwingFXUtils.fromFXImage(wimi, null),"png",enviar);

                    }catch (IOException s){
                        mensaje("Error: "+s);
                    }                 
                } 
                if("pdf".equals(extencion)){
                    File file = new File("CanvasImage.png");
                     WritableImage wim = new WritableImage(626,681);
                     panelDibujo.snapshot(null, wim);
                     try{
                         ImageIO.write(SwingFXUtils.fromFXImage(wim, null),"png",file);

                     }catch (IOException s){
                         mensaje("Error: "+s);
                     }                    
                    
                    
                    PDDocument doc    = new PDDocument();
                    PDPage page = new PDPage();
                    PDImageXObject pdimage;
                    PDPageContentStream content;
                    try {
                        pdimage = PDImageXObject.createFromFile("CanvasImage.png",doc);
                        content = new PDPageContentStream(doc, page);
                        content.drawImage(pdimage, -15, 100);
                        content.close();
                        doc.addPage(page);
                        doc.save(enviar.getPath());
                        doc.close();
                        file.delete();
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                        
                       
                    }
             }
             

             

             }
             
        

  
    }  
    private boolean sobreposicionHerencia(Herencia herencia){
        
        
        return true;
    }    
    private boolean sobreposicionAtributo(Atributo atributo){
    /*        for(int i=0;i<diagrama.getEntidades().size();i++){
                ArrayList<Integer> x=diagrama.getEntidades().get(i).getFigura().getPosicionesX();
                ArrayList<Integer> y=diagrama.getEntidades().get(i).getFigura().getPosicionesY();
                for(int j=0;j<atributo.getFigura().getPosicionesX().size();j++){
                    int posX = atributo.getFigura().getPosicionesX().get(j);
                    int posY = atributo.getFigura().getPosicionesY().get(j);
                    if (x.get(0)-5 <= posX && x.get(1)+5 >= posX && y.get(0)-5 <= posY && y.get(3)+5 >= posY) {
                            return false;
                        }
                }    
            } 
            for(int j=0;j<atributo.getFigura().getPosicionesX().size();j++){
                int posX=atributo.getFigura().getPosicionesX().get(j);
                int posY = atributo.getFigura().getPosicionesY().get(j);
                for (int i=0; i<diagrama.getRelaciones().size();i++){
                    ArrayList<Integer> x=diagrama.getRelaciones().get(i).getFigura().getPosicionesX();
                    ArrayList<Integer> y=diagrama.getRelaciones().get(i).getFigura().getPosicionesY();
                    if (diagrama.getRelaciones().get(i).getEntidad().size()==3){
                        if (x.get(0) < posX && x.get(1) > posX && y.get(2) < posY && y.get(0) > posY) {
                            return false;
                        }                         
                    }
                    else{
                        if (x.get(0) < posX && x.get(1) > posX && y.get(2) < posY && y.get(3) > posY) {
                            return false;
                        }                         
                    }

                } 
            }  

            ArrayList<Integer> x=atributo.getFigura().getPosicionesX();
            ArrayList<Integer> y=atributo.getFigura().getPosicionesY();
            for(int i=0;i<diagrama.getEntidades().size();i++){
                ArrayList<Integer> Ex=diagrama.getEntidades().get(i).getFigura().getPosicionesX();
                ArrayList<Integer> Ey=diagrama.getEntidades().get(i).getFigura().getPosicionesY();
                for(int j=0;j<Ex.size();j++){
                    int posX = Ex.get(j);
                    int posY = Ey.get(j);
                    if (x.get(0) <= posX && x.get(1) >= posX && y.get(0) <= posY && y.get(3) >= posY) {
                            return false;
                        }
                }    
            } 

            for(int i=0;i<diagrama.getAtributos().size();i++){
                ArrayList<Integer> Ex=diagrama.getAtributos().get(i).getFigura().getPosicionesX();
                ArrayList<Integer> Ey=diagrama.getAtributos().get(i).getFigura().getPosicionesY();
                for(int j=0;j<Ex.size();j++){
                    int posX = Ex.get(j);
                    int posY = Ey.get(j);
                    if (x.get(0) <= posX && x.get(1)>= posX && y.get(2) <= posY && y.get(3) >= posY) {
                            return false;
                        }
                }    
            }             

*/
        return true;
    }    

    private boolean sobreposicionRelacion(Relacion relacion){
        /*    for(int i=0;i<diagrama.getEntidades().size();i++){
                ArrayList<Integer> x=diagrama.getEntidades().get(i).getFigura().getPosicionesX();
                ArrayList<Integer> y=diagrama.getEntidades().get(i).getFigura().getPosicionesY();
                for(int j=0;j<relacion.getFigura().getPosicionesX().size();j++){
                    int posX = relacion.getFigura().getPosicionesX().get(j);
                    int posY = relacion.getFigura().getPosicionesY().get(j);
                    if (x.get(0)-5 <= posX && x.get(1)+5 >= posX && y.get(0)-5 <= posY && y.get(3)+5 >= posY) {
                            return false;
                        }
                }    
            }
            for(int j=0;j<relacion.getFigura().getPosicionesX().size();j++){
                int posX=relacion.getFigura().getPosicionesX().get(j);
                int posY = relacion.getFigura().getPosicionesY().get(j);
                for (int i=0; i<diagrama.getRelaciones().size();i++){
                    ArrayList<Integer> x=diagrama.getRelaciones().get(i).getFigura().getPosicionesX();
                    ArrayList<Integer> y=diagrama.getRelaciones().get(i).getFigura().getPosicionesY();
                    if (diagrama.getRelaciones().get(i).getEntidad().size()==3){
                        if (x.get(0) < posX && x.get(1) > posX && y.get(2) < posY && y.get(0) > posY) {
                            return false;
                        }                         
                    }
                    else{
                        if (x.get(0) < posX && x.get(1) > posX && y.get(2) < posY && y.get(3) > posY) {
                            return false;
                        }                         
                    }

                } 
            }
            ArrayList<Integer> x=relacion.getFigura().getPosicionesX();
            ArrayList<Integer> y=relacion.getFigura().getPosicionesY();
            for(int j=0;j<diagrama.getRelaciones().size();j++){
                ArrayList<Integer> rX=diagrama.getRelaciones().get(j).getFigura().getPosicionesX();
                ArrayList<Integer> rY=diagrama.getRelaciones().get(j).getFigura().getPosicionesY();
                for (int i=0; i<rX.size();i++){
                    int posX = rX.get(i);
                    int posY = rY.get(i);
                    if (relacion.getEntidad().size()==3){
                        if (x.get(0) < posX && x.get(1) > posX && y.get(2) < posY && y.get(0) > posY) {
                            return false;
                        }                         
                    }
                    else{
                        if (x.get(0) < posX && x.get(1) > posX && y.get(2) < posY && y.get(3) > posY) {
                            return false;
                        }                         
                    }

                } 
            }
            for(int j=0;j<diagrama.getEntidades().size();j++){
                ArrayList<Integer> rX=diagrama.getEntidades().get(j).getFigura().getPosicionesX();
                ArrayList<Integer> rY=diagrama.getEntidades().get(j).getFigura().getPosicionesY();
                for (int i=0; i<rX.size();i++){
                    int posX = rX.get(i);
                    int posY = rY.get(i);
                    if (relacion.getEntidad().size()==3){
                        if (x.get(0) < posX && x.get(1) > posX && y.get(2) < posY && y.get(0) > posY) {
                            return false;
                        }                         
                    }
                    else{
                        if (x.get(0) < posX && x.get(1) > posX && y.get(2) < posY && y.get(3) > posY) {
                            return false;
                        }                         
                    }

                } 
            }
            for(int j=0;j<diagrama.getAtributos().size();j++){
                ArrayList<Integer> rX=diagrama.getAtributos().get(j).getFigura().getPosicionesX();
                ArrayList<Integer> rY=diagrama.getAtributos().get(j).getFigura().getPosicionesY();
                for (int i=0; i<rX.size();i++){
                    int posX = rX.get(i);
                    int posY = rY.get(i);
                    if (relacion.getEntidad().size()==3){
                        if (x.get(0) < posX && x.get(1) > posX && y.get(2) < posY && y.get(0) > posY) {
                            return false;
                        }                         
                    }
                    else{
                        if (x.get(0) < posX && x.get(1) > posX && y.get(2) < posY && y.get(3) > posY) {
                            return false;
                        }                         
                    }

                } 
               
            }             
            */
            return true;   
            }
      
    private boolean sobreposicionEntidad(Elemento entidad){
            for(int i=0;i<diagrama.getElementos().size();i++){
                ArrayList<Integer> x=diagrama.getElementos().get(i).getPosicionesX();
                ArrayList<Integer> y=diagrama.getElementos().get(i).getPosicionesY();
                for(int j=0;j<entidad.getPosicionesX().size();j++){
                    int posX = entidad.getPosicionesX().get(j);
                    int posY = entidad.getPosicionesY().get(j);
                    if (x.get(0)-5 <= posX && x.get(1)+5 >= posX && y.get(0)-5 <= posY && y.get(3)+5 >= posY) {
                            return false;
                        }
                }    
            } 
            /*
            for(int j=0;j<entidad.getFigura().getPosicionesX().size();j++){
                int posX=entidad.getFigura().getPosicionesX().get(j);
                int posY = entidad.getFigura().getPosicionesY().get(j);
                for (int i=0; i<diagrama.getRelaciones().size();i++){
                    ArrayList<Integer> x=diagrama.getRelaciones().get(i).getFigura().getPosicionesX();
                    ArrayList<Integer> y=diagrama.getRelaciones().get(i).getFigura().getPosicionesY();
                    if (diagrama.getRelaciones().get(i).getEntidad().size()==3){
                        if (x.get(0) < posX && x.get(1) > posX && y.get(2) < posY && y.get(0) > posY) {
                            return false;
                        }                         
                    }
                    else{
                        if (x.get(0) < posX && x.get(1) > posX && y.get(2) < posY && y.get(3) > posY) {
                            return false;
                        }                         
                    }

                } 
            }  
            ArrayList<Integer> x=entidad.getFigura().getPosicionesX();
            ArrayList<Integer> y=entidad.getFigura().getPosicionesY();
            for(int i=0;i<diagrama.getEntidades().size();i++){
                ArrayList<Integer> Ex=diagrama.getEntidades().get(i).getFigura().getPosicionesX();
                ArrayList<Integer> Ey=diagrama.getEntidades().get(i).getFigura().getPosicionesY();
                for(int j=0;j<entidad.getFigura().getPosicionesX().size();j++){
                    int posX = Ex.get(j);
                    int posY = Ey.get(j);
                    if (x.get(0)-5 <= posX && x.get(1)+5 >= posX && y.get(0)-5 <= posY && y.get(3)+5 >= posY) {
                            return false;
                        }
                }    
            }
            for(int i=0;i<diagrama.getRelaciones().size();i++){
                ArrayList<Integer> Ex=diagrama.getRelaciones().get(i).getFigura().getPosicionesX();
                ArrayList<Integer> Ey=diagrama.getRelaciones().get(i).getFigura().getPosicionesY();
                for(int j=0;j<Ex.size();j++){
                    int posX = Ex.get(j);
                    int posY = Ey.get(j);
                    if (x.get(0)-5 <= posX && x.get(1)+5 >= posX && y.get(0)-5 <= posY && y.get(3)+5 >= posY) {
                            return false;
                        }
                }    
            } 
            for(int i=0;i<diagrama.getAtributos().size();i++){
                ArrayList<Integer> Ex=diagrama.getAtributos().get(i).getFigura().getPosicionesX();
                ArrayList<Integer> Ey=diagrama.getAtributos().get(i).getFigura().getPosicionesY();
                for(int j=0;j<Ex.size();j++){
                    int posX = Ex.get(j);
                    int posY = Ey.get(j);
                    if (x.get(0)-5 <= posX && x.get(1)+5 >= posX && y.get(0)-5 <= posY && y.get(3)+5 >= posY) {
                            return false;
                        }
                }    
            }             
            */
        return true;    
            
    }

 
 
    
    /**
     * Activa los puntos de control de las entidades y relaciones.
     */
    @FXML
    private void puntosDeControlActivar(){
        actualizarPanel();
    }
    
    
    /**
     * Funcion que permite crear una ventana de alerta
     * @param texto: mensaje que se quiere escribir en la ventana. 
     */
    public void mensaje(String texto){
        JOptionPane.showMessageDialog(null, texto);
    }

    public void obtenerEntidad(String nombreEntidad,boolean debil,Diagrama diagrama){
        this.diagrama=diagrama;
        textoEntidad=nombreEntidad;
        entidadDebil = debil;
        crearEntidad=true;
        moverFiguras = false; 
        crearRelacion=false; 
        crearAtributo = false;
        crearAgregacion = false;
        
    }



    @FXML
    private void ventanaEntidad() throws IOException{
        Stage stage = new Stage();
        stage.setTitle("Entidad");
        stage.setResizable(false);
        FXMLLoader loader = new FXMLLoader();
        AnchorPane root1 = (AnchorPane)loader.load(getClass().getResource("Entidad.fxml").openStream());
        EntidadController instanciaControlador = (EntidadController)loader.getController();
        instanciaControlador.recibirParametros(controlador,diagrama);
        Scene scene = new Scene(root1);
        stage.setScene(scene);
        stage.alwaysOnTopProperty();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        
    }

    
    public void creacionRelacion(boolean debil,String nombreRelacion,ArrayList<String> participacion,Diagrama diagrama,ArrayList<Elemento> elementos){
        if(debil){
            this.elementosDisponibles = new ArrayList<>();
            elementosDisponibles.add(elementos.get(0));
            elementosDisponibles.add(elementos.get(1));
        }
        else{
            this.participacion=participacion;
            this.elementosDisponibles = elementos;
        }
        

        this.textoRelacion=nombreRelacion;
        this.diagrama=diagrama;
        this.relacionDebil=debil;
            crearEntidad=false;
            moverFiguras = false; 
            crearRelacion=true;    
            crearAtributo = false;
            crearHerencia = false;
            crearAgregacion = false;
    } 

    
    private void relacionEntidadDebil(String nombreEntidad) throws IOException{
            Stage stage = new Stage();
            stage.setTitle("Relación");
            stage.setResizable(false);
            FXMLLoader loader = new FXMLLoader();
            AnchorPane root1 = (AnchorPane)loader.load(getClass().getResource("Relaciones.fxml").openStream());
            RelacionesController instanciaControlador = (RelacionesController)loader.getController();
            instanciaControlador.recibirParametrosEntidadDebil(controlador, diagrama, nombreEntidad);
            Scene scene = new Scene(root1);
            stage.setScene(scene);
            stage.alwaysOnTopProperty();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();         
    }
    
    @FXML
    private void ventanaRelacion() throws IOException{
        
        if(!diagrama.getElementos().isEmpty()){
            Stage stage = new Stage();
            stage.setTitle("Relación");
            stage.setResizable(false);
            FXMLLoader loader = new FXMLLoader();
            AnchorPane root1 = (AnchorPane)loader.load(getClass().getResource("Relaciones.fxml").openStream());
            RelacionesController instanciaControlador = (RelacionesController)loader.getController();
            instanciaControlador.recibirParametros(controlador,diagrama);
            Scene scene = new Scene(root1);
            stage.setScene(scene);
            stage.alwaysOnTopProperty();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();            
        }
        else{
            mensaje("Debe crear una entidad como mínimo para poder\n"
                    + "             crear una relación");
        }

        
    }
    @FXML
    private void ventanaAtributo() throws IOException{
        
        if(!diagrama.getElementos().isEmpty()){
            Stage stage = new Stage();
            stage.setTitle("Atributo");
            stage.setResizable(false);
            FXMLLoader loader = new FXMLLoader();
            AnchorPane root1 = (AnchorPane)loader.load(getClass().getResource("Atributos.fxml").openStream());
            AtributosController instanciaControlador = (AtributosController)loader.getController();
            instanciaControlador.recibirParametros(controlador,diagrama);
            Scene scene = new Scene(root1);
            stage.setScene(scene);
            stage.alwaysOnTopProperty();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();            
        }
        else{
            mensaje("Debe crear una entidad como mínimo para poder\n"
                    + "             crear una relación");
        }

        
    }    
    @FXML
    private void ventanaHerencia() throws IOException{
        
        if(diagrama.getElementos().size()>=2){
            Stage stage = new Stage();
            stage.setTitle("Herencia");
            stage.setResizable(false);
            FXMLLoader loader = new FXMLLoader();
            AnchorPane root1 = (AnchorPane)loader.load(getClass().getResource("Herencia.fxml").openStream());
            HerenciaController instanciaControlador = (HerenciaController)loader.getController();
            instanciaControlador.recibirParametros(controlador,diagrama);
            Scene scene = new Scene(root1);
            stage.setScene(scene);
            stage.alwaysOnTopProperty();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();            
        }
        else{
            mensaje("Debe crear 2 entidades como mínimo para poder crear una herencia.");
        }

        
    }     
    
    
    @FXML
    public void creacionAtributo(String tipo, String destino, String comboBox,Diagrama diagrama,String textoAtributo){
        this.tipoAtributo=tipo;
        this.comboBoxEntidadesRelaciones=comboBox;
        this.diagrama=diagrama;
        this.textoAtributo=textoAtributo;
        this.destinoAtributo=destino;
            crearEntidad=false;
            moverFiguras = false; 
            crearRelacion=false;
            crearAtributo = true;
            crearHerencia = false;
            crearAgregacion = false;
    }    

    @FXML
    private void ventanaModificarDiagrama() throws IOException{
        
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("Modificar Diagrama");
            FXMLLoader loader = new FXMLLoader();
            AnchorPane root1 = (AnchorPane)loader.load(getClass().getResource("ModificarDiagrama.fxml").openStream());
            ModificarDiagramaController instanciaControlador = (ModificarDiagramaController)loader.getController();
            instanciaControlador.recibirParametros(controlador,diagrama);
            Scene scene = new Scene(root1);
            stage.setScene(scene);
            stage.alwaysOnTopProperty();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();              
    }
    @FXML
    private void ventanaAgregacion() throws IOException{
        
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("Agregación");
            FXMLLoader loader = new FXMLLoader();
            AnchorPane root1 = (AnchorPane)loader.load(getClass().getResource("Agregacion.fxml").openStream());
            AgregacionController instanciaControlador = (AgregacionController)loader.getController();
            instanciaControlador.recibirParametros(controlador,diagrama);
            Scene scene = new Scene(root1);
            stage.setScene(scene);
            stage.alwaysOnTopProperty();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();              
    }    
    
    
    public void creacionHerencia(Entidad padre,ArrayList<Entidad> hijas,String tipo){
        herenciaAuxiliar = new Herencia(padre,hijas,tipo,0,0);
            crearEntidad=false;
            moverFiguras = false; 
            crearRelacion=false;
            crearAtributo = false;
            crearHerencia = true;
            crearAgregacion = false;
    }
    public void crearAgregacion(Relacion relacion,String nombre){
        this.relacionAgregacion=relacion;
        this.textoAgregacion=nombre;
            crearEntidad=false;
            moverFiguras = false; 
            crearRelacion=false;
            crearAtributo = false;
            crearHerencia = false;
            crearAgregacion = true;
        
    }
    public void modificarDiagrama(Diagrama diagrama){
        this.diagrama=diagrama;
        this.actualizarPanel();
        
    }    
    
    
    private void ventanaAtributosEntidad(String nombre, boolean debil) throws IOException{
            Stage stage = new Stage();
            stage.setTitle("Atributo");
            stage.setResizable(false);
            FXMLLoader loader = new FXMLLoader();
            AnchorPane root1 = (AnchorPane)loader.load(getClass().getResource("Atributos.fxml").openStream());
            AtributosController instanciaControlador = (AtributosController)loader.getController();
            instanciaControlador.EntidadRecibirParametros(controlador,diagrama,nombre,debil);
            Scene scene = new Scene(root1);
            stage.setScene(scene);
            stage.alwaysOnTopProperty();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();         
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        controlador = this;
        
        
       
    }  
    
    
}
