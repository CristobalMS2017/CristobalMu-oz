package Controller;
import Clases.Atributo;
import Clases.Diagrama;
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
    @FXML private String textoEntidad; //guarda el nombre de la entidad ingresada por el usuario.    
    @FXML private String textoRelacion;  //guarda el nombre de la relacion ingresada por el usuario.   
    @FXML private String textoAtributo; //Guarda el nombre del atributo ingresado por el usuario.
    @FXML private CheckBox puntosDeControl; //Activa y desactiva los puntos de control.    
    @FXML private ArrayList<Entidad> entidadesDisponibles;//Guarda las entidades seleccionadas para crear una relación.
    
    
    
    private String tipoAtributo;//Guarda el tipo de atributo

    private String comboBoxEntidadesRelaciones;//Guarda el nombre del elemento en que se guardará el atributo.
    private String destinoAtributo;//Guarda el destino del atributo(en entidad, relación o atributo).
    private Diagrama diagrama = new Diagrama(); //se crea un diagrama donde se guardan las entidades y relaciones.



    private boolean crearEntidad = false;//Permite saber si el usuario seleccionó el boton para crear una entidad
    private boolean moverFiguras = true;//Se utiliza para activar o desactivar el movimiento de las figuras(entidades, relaciones, atributos y herencias).
    private boolean crearRelacion = false;//Permite saber si el usuario seleccionó el botón para crear una relacion.
    private boolean crearAtributo = false;//Permite saber si el usuario seleccionó el botón para crear el atributo.
    private boolean crearHerencia = false;//Permite saber si el usuario seleccionó el botón para crear la herencia.
    private boolean atributoEncontradoMover = false;
    private boolean entidadEncontradaMover=false;//Permite saber si el usuario seleccionó una entidad, permitiendo ser movida.
    private boolean relacionEncontradaMover = false;//Permite saber si el usuario seleccionó una relación, permitiendo ser movida.        
    private boolean herenciaEncontradaMover = false;//Permite saber si el usuario seleccionó una herencia, permitiendo ser movida.
    private boolean atributoEnEntidadMover=false; //Permite saber si el atributo se encuentra en una entidad.
    private boolean atributoEnRelacionMover=false; //Permite saber si un atributo se encuentra en una relación.
    private boolean atributoEnAtributoMover=false; //Permite saber si un atributo se encuentra en un atributo    
    private boolean entidadDebil = false;//Permite saber si la entidad que se quiere crear es debil o fuerte.
    private String tipoHerenciaMover="";//Guarda el tipo de herencia de la entidad seleccionada o creada.
    private String nombreAtributoMover = "";//Guarda el nombre del atributo seleccionado para mover.
    private String nombreRelacionMover="";//Guarda el nombre de la relación seleccionada para mover.
    private String tipoAtributoMover="";//Guarda el nombre del atributo seleccionado para mover.  
    private String nombreEntidadMover=""; //Guarda el nombre de la entidad seleccionada para mover.

    
    private int indiceEntidadAtributo; //Guarda la posición en la que se encuentra el atributo en la entidad.
    private int indiceRelacionAtributo;//guarda la posicion en la que se encuentra el atributo en la relación.
    private int indiceRelacionAtributoAtributo;//guarda la posición en la que se encuentra el atributo del atrbuto compuesto de la relación.
    private int indiceEntidadAtributoAtributo;//guarda la posición en la que se encuentra el atributo del atrbuto compuesto de la entidad.   
    private Entidad  entidadAuxiliar;// Guarda la entidad original antes de ser movida.
    private Atributo atributoAuxiliar; //Guarda el atributo original antes de ser movido.
    private Relacion relacionAuxiliar;//Guarda la relacion seleccionada para mover.
    private Herencia herenciaAuxiliar;//Guarda la herencia original antes de ser movida.

    FXMLDocumentController controlador;
    RespaldoDiagrama listaDiagramas = new RespaldoDiagrama();//Guarda los diagramas con el proposito de rehacer y deshacer.
    int desHacer = 0;//Guarda la posción del diagrama al que se llamó para volver a crearlo o rehacerlo.
    private boolean relacionDebil;
    
    
    
    
    /**
     * Guarda el diagrama una ves que se haya hecho una modificaciíon
     */
    public void guardarDiagrama(){
        listaDiagramas.removerPosteriores(desHacer);
        listaDiagramas.agregarDiagrama(diagrama);
        desHacer = listaDiagramas.tamano()-1;
    }
    
    
    /**
     * Se aumenta la variable desHacer y
     * accede a la posición en la lista de diagramas.
     */
    @FXML
    private void deshacer(){
        desHacer++;
        if(desHacer>=listaDiagramas.tamano()){
            desHacer = listaDiagramas.tamano()-1;
        }        
        diagrama =listaDiagramas.retornarDiagrama(desHacer);
        actualizarPanel();        
    }
    /**
     * Se decrementa la variable desHacer y
     * accede a la posición en la lista de diagramas.
     */    
    @FXML
    private void rehacer(){
        desHacer--;
        if(desHacer<0){
            desHacer=0;
        }

        diagrama =listaDiagramas.retornarDiagrama(desHacer);
        actualizarPanel();
        
    }
    
    
    
    
    /**
     * Crea las entidades, relaciones, atributos y herencias una ves conocida la posicion
     * donde hizo click el usuario en el panel de dibujo. Posteriormente, se guarda
     * el elemento creado en el diagrama.
     * @param event 
     */
    @FXML
    private void crear(MouseEvent event){
        if(crearEntidad){
            Entidad nueva = new Entidad(false,(int)event.getX(),(int)event.getY(),textoEntidad);
            if(entidadDebil){
                nueva = new Entidad(true,(int)event.getX(),(int)event.getY(),textoEntidad); 
                entidadDebil=false;
            }

            if(puntosDeControl.isSelected()){
                panelDibujo.getChildren().addAll(nueva.getFigura().getPuntosControl());
            }
            diagrama.getEntidades().add(nueva);  
            guardarDiagrama(); 
            
        }
        if(crearRelacion){
            Relacion nueva = new Relacion(relacionDebil,(int)event.getX(),(int)event.getY(),textoRelacion);
            for(int i = 0; i<entidadesDisponibles.size();i++){

                    nueva.getEntidad().add(entidadesDisponibles.get(i));
            }
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
        if(crearAtributo){
            
                if(destinoAtributo.equals("Entidad")){
                    
                    Atributo nueva = new Atributo(tipoAtributo,(int)event.getX(),(int)event.getY(),textoAtributo,"Entidad");
                    for(int i=0;i<diagrama.getEntidades().size();i++){
                        if(comboBoxEntidadesRelaciones.equals(diagrama.getEntidades().get(i).getNombre())){
                            diagrama.getEntidades().get(i).agregarAtributo(nueva);
                            diagrama.getEntidades().get(i).crearLineasunionAtributos();  
                            diagrama.getAtributos().add(nueva);
                        }
                    }
                    
                    
                }
                if(destinoAtributo.equals("Relación")){

                    Atributo nueva = new Atributo(tipoAtributo,(int)event.getX(),(int)event.getY(),textoAtributo,"Relación");
                    for(int i=0;i<diagrama.getRelaciones().size();i++){
                        if(comboBoxEntidadesRelaciones.equals(diagrama.getRelaciones().get(i).getNombre())){
                            diagrama.getRelaciones().get(i).agregarAtributo(nueva);
                            diagrama.getRelaciones().get(i).crearLineasunionAtributos();  
                            diagrama.getAtributos().add(nueva);
                        }
                    }
                   

                }
                if(destinoAtributo.equals("Atributo")){
                    
                    Atributo nueva = new Atributo((String)tipoAtributo,(int)event.getX(),(int)event.getY(),textoAtributo,"Compuesto");

                    for(int i=0; i< diagrama.getEntidades().size();i++){
                        for(int j = 0; j < diagrama.getEntidades().get(i).getAtributos().size();j++){
                            if(diagrama.getEntidades().get(i).getAtributos().get(j).getNombre().equals(comboBoxEntidadesRelaciones)){
                                diagrama.getEntidades().get(i).getAtributos().get(j).getAtributos().add(nueva);
                                diagrama.getEntidades().get(i).getAtributos().get(j).crearLineasunionAtributos();

                            }
                        }
                    }
                    for(int i=0; i< diagrama.getRelaciones().size();i++){
                        for(int j = 0; j < diagrama.getRelaciones().get(i).getAtributos().size();j++){
                            if(diagrama.getRelaciones().get(i).getAtributos().get(j).getNombre().equals(comboBoxEntidadesRelaciones)){
                                diagrama.getRelaciones().get(i).getAtributos().get(j).getAtributos().add(nueva);
                                diagrama.getRelaciones().get(i).getAtributos().get(j).crearLineasunionAtributos();
                            }
                        }
                    }                    
                }
                guardarDiagrama(); 
        }
        
        actualizarPanel();
                                
        crearEntidad=false;
        moverFiguras = true;
        crearRelacion = false;
        crearAtributo = false;
        crearHerencia = false;

    }

    /**
     * Encuentra el elemento donde el usuario esta presionó en el panel de dibujo. Al encontrar el elemento,
     * este se elimina del diagrama, se guarda un auxiliar el elemento. Tambien
     * se desactivan los if que encuentran elementos. Finalmente, se genera el movimiento del elemento
     * creando el elemento y eliminandolo, actualizando el panel de dibujo de manera simultanea
     * generando el movimiento del elemento.
     * @param event 
     */
    @FXML
    private void moverEnPanel(MouseEvent event){
        if(moverFiguras){
            int posX = (int)event.getX();
            int posY = (int)event.getY();
            if (0<=posX && posX<=1050 && 0<=posY && posY<= 687){
                if(relacionEncontradaMover == entidadEncontradaMover && herenciaEncontradaMover == atributoEncontradoMover){

                    //Busqueda de entidad en el panel de dibujo
                    for (int i=0; i<diagrama.getEntidades().size();i++){
                        ArrayList<Integer> x=diagrama.getEntidades().get(i).posicionesX();
                        ArrayList<Integer> y=diagrama.getEntidades().get(i).posicionesY();
                        if (x.get(0) < posX && x.get(1) > posX && y.get(0) < posY && y.get(x.size()-1) > posY) {
                            entidadDebil = diagrama.getEntidades().get(i).isDebil();
                            nombreEntidadMover=diagrama.getEntidades().get(i).getNombre();
                            
                            entidadAuxiliar=diagrama.getEntidades().get(i);
                            diagrama.getEntidades().remove(i);
                            entidadEncontradaMover = true;
                        } 
                    }
                    //Busqueda de relaciones en el panel  de dubujo
                    for(int i=0; i<diagrama.getRelaciones().size();i++){
                        if(areaFiguras((int)event.getX(), (int)event.getY(), i)){
                            relacionDebil = diagrama.getRelaciones().get(i).isRelacionDebil();
                            nombreRelacionMover = diagrama.getRelaciones().get(i).getNombre();
                            relacionAuxiliar = diagrama.getRelaciones().get(i);
                            diagrama.getRelaciones().remove(i);
                            relacionEncontradaMover = true;
                        }
                    }
                    
                    
                    //Busquedad de atributos de entidades en el panel
                    for(int i = 0; i<diagrama.getEntidades().size(); i++){
                        for(int j =0;j<diagrama.getEntidades().get(i).getAtributos().size();j++){
                            ArrayList<Integer> x=diagrama.getEntidades().get(i).getAtributos().get(j).posicionesX();
                            ArrayList<Integer> y=diagrama.getEntidades().get(i).getAtributos().get(j).posicionesY();
                            if (x.get(0) <= posX && x.get(1) >= posX && y.get(2) <= posY && y.get(3) >= posY) {
                               
                                tipoAtributoMover = diagrama.getEntidades().get(i).getAtributos().get(j).getTipoAtributo();
                                nombreAtributoMover=diagrama.getEntidades().get(i).getAtributos().get(j).getNombre();
                                atributoAuxiliar = diagrama.getEntidades().get(i).getAtributos().get(j);
                                 removerAtributoEnDiagrama(atributoAuxiliar);

                                diagrama.getEntidades().get(i).getAtributos().remove(j);
                                atributoEncontradoMover = true;
                                indiceEntidadAtributo=i;
                                atributoEnEntidadMover = true;
                                


                            }                            
                        }
                         
                    }
                    
                    //Busqueda atributos de relación
                    for(int i = 0; i<diagrama.getRelaciones().size(); i++){
                        for(int j =0;j<diagrama.getRelaciones().get(i).getAtributos().size();j++){
                            ArrayList<Integer> x=diagrama.getRelaciones().get(i).getAtributos().get(j).posicionesX();
                            ArrayList<Integer> y=diagrama.getRelaciones().get(i).getAtributos().get(j).posicionesY();
                            if (x.get(0) <= posX && x.get(1) >= posX && y.get(2) <= posY && y.get(3) >= posY) {
                                
                                tipoAtributoMover = diagrama.getRelaciones().get(i).getAtributos().get(j).getTipoAtributo();
                                nombreAtributoMover=diagrama.getRelaciones().get(i).getAtributos().get(j).getNombre();
                                atributoAuxiliar = diagrama.getRelaciones().get(i).getAtributos().get(j);
                                removerAtributoEnDiagrama(atributoAuxiliar);

                                diagrama.getRelaciones().get(i).getAtributos().remove(j);
                                atributoEncontradoMover = true;
                                indiceRelacionAtributo=i;
                                atributoEnRelacionMover=true;
                                


                            }                            
                        }   
                    }
                    
                    //Busquedad de atributo de atrbitos de relacion
                    for(int i=0; i< diagrama.getRelaciones().size();i++){
                        for(int j = 0; j < diagrama.getRelaciones().get(i).getAtributos().size();j++){
                            if(diagrama.getRelaciones().get(i).getAtributos().get(j).getTipoAtributo().equals("Compuesto")){
                                for(int k = 0; k<diagrama.getRelaciones().get(i).getAtributos().get(j).getAtributos().size();k++){
                                ArrayList<Integer> x=diagrama.getRelaciones().get(i).getAtributos().get(j).getAtributos().get(k).getFigura().getPosicionesX();
                                ArrayList<Integer> y=diagrama.getRelaciones().get(i).getAtributos().get(j).getAtributos().get(k).getFigura().getPosicionesY();
                                if (x.get(0) <= posX && x.get(1) >= posX && y.get(2) <= posY && y.get(3) >= posY) {

                                    tipoAtributoMover = diagrama.getRelaciones().get(i).getAtributos().get(j).getAtributos().get(k).getTipoAtributo();
                                    nombreAtributoMover=diagrama.getRelaciones().get(i).getAtributos().get(j).getAtributos().get(k).getNombre();
                                    atributoAuxiliar = diagrama.getRelaciones().get(i).getAtributos().get(j).getAtributos().get(k);
                                    removerAtributoEnDiagrama(atributoAuxiliar);
                                    diagrama.getRelaciones().get(i).getAtributos().get(j).getAtributos().remove(k);
                                    atributoEncontradoMover = true;
                                    indiceRelacionAtributo=i;
                                    indiceRelacionAtributoAtributo=j;
                                    atributoEnAtributoMover=true;
                                    atributoEnRelacionMover=true;


                                }                             
                                }
                            }
                        }
                    }
                    //Busqueda de herencias en panel
                    for(int i = 0; i< diagrama.getHerencias().size();i++){
                        ArrayList<Integer> x = diagrama.getHerencias().get(i).posicionesXFigura();
                        ArrayList<Integer> y = diagrama.getHerencias().get(i).posicionesYFigura();
                        if(x.get(0)<=posX && posX<=x.get(1) && y.get(2)<=posY && posY<=y.get(3)){
                            herenciaEncontradaMover = true;
                            herenciaAuxiliar = diagrama.getHerencias().get(i);
                            tipoHerenciaMover = diagrama.getHerencias().get(i).getTipoHerencia();
                            diagrama.getHerencias().remove(i);
                            
                        }
                    }
                    
                    
                    //Atributos de atributos compuestos de entidades
                    for(int i=0; i< diagrama.getEntidades().size();i++){
                        for(int j = 0; j < diagrama.getEntidades().get(i).getAtributos().size();j++){
                            if(diagrama.getEntidades().get(i).getAtributos().get(j).getTipoAtributo().equals("Compuesto")){
                                for(int k = 0; k<diagrama.getEntidades().get(i).getAtributos().get(j).getAtributos().size();k++){
                                ArrayList<Integer> x=diagrama.getEntidades().get(i).getAtributos().get(j).getAtributos().get(k).getFigura().getPosicionesX();
                                ArrayList<Integer> y=diagrama.getEntidades().get(i).getAtributos().get(j).getAtributos().get(k).getFigura().getPosicionesY();
                                if (x.get(0) <= posX && x.get(1) >= posX && y.get(2) <= posY && y.get(3) >= posY) {

                                    tipoAtributoMover = diagrama.getEntidades().get(i).getAtributos().get(j).getAtributos().get(k).getTipoAtributo();
                                    nombreAtributoMover=diagrama.getEntidades().get(i).getAtributos().get(j).getAtributos().get(k).getNombre();
                                    atributoAuxiliar = diagrama.getEntidades().get(i).getAtributos().get(j).getAtributos().get(k);
                                    diagrama.getEntidades().get(i).getAtributos().get(j).getAtributos().remove(k);
                                    atributoEncontradoMover = true;
                                    indiceEntidadAtributo=i;
                                    indiceEntidadAtributoAtributo=j;
                                    atributoEnAtributoMover=true;
                                    atributoEnEntidadMover=true;



                                }                             
                                }
                            }
                        }
                    }
                }
                else{
                    if(entidadEncontradaMover){
                                                
                        Entidad nueva = new Entidad(entidadDebil,(int)event.getX(),(int)event.getY(),nombreEntidadMover);

                        
                        
                        for(int i = 0; i<entidadAuxiliar.getAtributos().size();i++){
                            nueva.getAtributos().add(entidadAuxiliar.getAtributos().get(i));
                        }
                        nueva.crearLineasunionAtributos();
                        if(sobreposicionEntidad(nueva)){

                            moverEntidadEnElementos(nueva);
                            diagrama.getEntidades().add(nueva);

                            actualizarPanel();
                            entidadAuxiliar=nueva;
                            diagrama.getEntidades().remove(diagrama.getEntidades().size()-1);
                            
                            }
                        }                         
                    }
                    if(relacionEncontradaMover){
                        
                        Relacion nueva = new Relacion(relacionDebil,(int)event.getX(),(int)event.getY(),nombreRelacionMover); 
                        for(int i = 0; i<relacionAuxiliar.getEntidad().size();i++){
                            nueva.getEntidad().add(relacionAuxiliar.getEntidad().get(i));
                        }
                        nueva.crearRelacion();
                         
                        for(int i = 0; i<relacionAuxiliar.getAtributos().size();i++){
                            nueva.getAtributos().add(relacionAuxiliar.getAtributos().get(i));
                        }

                        nueva.crearLineasunionAtributos(); 
                        if(sobreposicionRelacion(nueva)){    
                            diagrama.getRelaciones().add(nueva);

                            actualizarPanel();

                            relacionAuxiliar=nueva;
                            diagrama.getRelaciones().remove(diagrama.getRelaciones().size()-1);
                        }
                        
                    }
                    if(herenciaEncontradaMover){
                        ArrayList<Entidad> hijas = new ArrayList<>();                        
                        for(int i = 0; i < herenciaAuxiliar.getEntidadesHijas().size();i++){
                            hijas.add(herenciaAuxiliar.getEntidadesHijas().get(i));
                        }
                        
                        
                        Herencia nueva = new Herencia(herenciaAuxiliar.getEntidadPadre(),hijas,tipoHerenciaMover,(int)event.getX(),(int)event.getY());
                        nueva.crearHerencia();

                        if(sobreposicionHerencia(nueva)){

                            diagrama.getHerencias().add(nueva);

                            actualizarPanel();
                            herenciaAuxiliar = nueva;
                            diagrama.getHerencias().remove(diagrama.getHerencias().size()-1);
                            
                        }                        
                    }
                    if(atributoEncontradoMover){
                        if(atributoEnEntidadMover){
                            if(atributoEnAtributoMover){
                                Atributo nueva = new Atributo(tipoAtributoMover,(int)event.getX(),(int)event.getY(),nombreAtributoMover,"Compuesto");
                                    
                                if(sobreposicionAtributo(nueva)){
 
                                    diagrama.getEntidades().get(indiceEntidadAtributo).getAtributos().get(indiceEntidadAtributoAtributo).agregarAtributo(nueva);
                                     diagrama.getEntidades().get(indiceEntidadAtributo).getAtributos().get(indiceEntidadAtributoAtributo).crearLineasunionAtributos();

                                     actualizarPanel();      
                                     atributoAuxiliar = nueva;
                                     removerAtributoEnDiagrama(atributoAuxiliar);
                                  
                                     int tamano = diagrama.getEntidades().get(indiceEntidadAtributo).getAtributos().get(indiceEntidadAtributoAtributo).getAtributos().size()-1;
                                     diagrama.getEntidades().get(indiceEntidadAtributo).getAtributos().get(indiceEntidadAtributoAtributo).getAtributos().remove(tamano);                                 

                                }
 
                            }else{
                                Atributo nueva = new Atributo(tipoAtributoMover,(int)event.getX(),(int)event.getY(),nombreAtributoMover,"Entidad");
                                for(int i = 0;i<atributoAuxiliar.getAtributos().size();i++){
                                    nueva.getAtributos().add(atributoAuxiliar.getAtributos().get(i));
                                }
                                nueva.crearLineasunionAtributos();
                                if(sobreposicionAtributo(nueva)){
                                    diagrama.getEntidades().get(indiceEntidadAtributo).agregarAtributo(nueva);
                                    diagrama.getEntidades().get(indiceEntidadAtributo).crearLineasunionAtributos();
                                 
                                    diagrama.getAtributos().add(nueva);
                                                                                                          
                                    panelDibujo.getChildren().clear();
                                    actualizarPanel();      
                                    atributoAuxiliar = nueva;
                                    removerAtributoEnDiagrama(atributoAuxiliar);
                                    diagrama.getEntidades().get(indiceEntidadAtributo).getAtributos().remove(diagrama.getEntidades().get(indiceEntidadAtributo).getAtributos().size()-1);                            
 
                                }

                            }

                        }
                        if(atributoEnRelacionMover){
                            if(atributoEnAtributoMover){
                                Atributo nueva = new Atributo(tipoAtributoMover,(int)event.getX(),(int)event.getY(),nombreAtributoMover,"Compuesto");

                                if(sobreposicionAtributo(nueva)){

                                    diagrama.getRelaciones().get(indiceRelacionAtributo).getAtributos().get(indiceRelacionAtributoAtributo).agregarAtributo(nueva);
                                    diagrama.getRelaciones().get(indiceRelacionAtributo).getAtributos().get(indiceRelacionAtributoAtributo).crearLineasunionAtributos();

                                    actualizarPanel(); 
                                    atributoAuxiliar = nueva;
                                    removerAtributoEnDiagrama(atributoAuxiliar);

                                    int tamano = diagrama.getRelaciones().get(indiceRelacionAtributo).getAtributos().get(indiceRelacionAtributoAtributo).getAtributos().size()-1;
                                    diagrama.getRelaciones().get(indiceRelacionAtributo).getAtributos().get(indiceRelacionAtributoAtributo).getAtributos().remove(tamano);                                 

                                                                    

                                }

                            }
                            else{
                                Atributo nueva = new Atributo(tipoAtributoMover,(int)event.getX(),(int)event.getY(),nombreAtributoMover,"Relación");

                                for(int i = 0;i<atributoAuxiliar.getAtributos().size();i++){
                                    nueva.getAtributos().add(atributoAuxiliar.getAtributos().get(i));
                                }
                                nueva.crearLineasunionAtributos();
                                if(sobreposicionAtributo(nueva)){

                                        diagrama.getAtributos().add(nueva);
                                                                       
                                    diagrama.getRelaciones().get(indiceRelacionAtributo).agregarAtributo(nueva);
                                    diagrama.getRelaciones().get(indiceRelacionAtributo).crearLineasunionAtributos();
                                    panelDibujo.getChildren().clear();
                                    actualizarPanel();      
                                    atributoAuxiliar = nueva;
                                    removerAtributoEnDiagrama(atributoAuxiliar);
                               
                                    diagrama.getRelaciones().get(indiceRelacionAtributo).getAtributos().remove(diagrama.getRelaciones().get(indiceRelacionAtributo).getAtributos().size()-1);                                 
                                }    
                                
   
                            }
                            
                        }
                    

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
        for(int i = 0; i<diagrama.getEntidades().size();i++){
            panelDibujo.getChildren().addAll(diagrama.getEntidades().get(i).dibujoEntidad());
            if(puntosDeControl.isSelected()){
                panelDibujo.getChildren().addAll(diagrama.getEntidades().get(i).getFigura().getPuntosControl());
            }
        }
        
        for(int i = 0; i < diagrama.getRelaciones().size();i++){
            panelDibujo.getChildren().addAll(diagrama.getRelaciones().get(i).dibujoRelacion());
            if(puntosDeControl.isSelected()){
                panelDibujo.getChildren().addAll(diagrama.getRelaciones().get(i).getFigura().getPuntosControl());
            } 
        }
        for(int i = 0; i<diagrama.getHerencias().size();i++){
            panelDibujo.getChildren().addAll(diagrama.getHerencias().get(i).dibujoHerencia());
        }
        for(int i = 0; i<diagrama.getAtributos().size();i++){
            panelDibujo.getChildren().addAll(diagrama.getAtributos().get(i).dibujoAtributo());
            if(puntosDeControl.isSelected()){
                panelDibujo.getChildren().addAll(diagrama.getAtributos().get(i).getFigura().getPuntosControl());
                for(int j = 0; j<diagrama.getAtributos().get(i).getAtributos().size();j++){
                    panelDibujo.getChildren().addAll(diagrama.getAtributos().get(i).getAtributos().get(j).getFigura().getPuntosControl());
                }
                
            } 
        }

    }   
    
    
    
    /**
     * Una vez que el usuario deja de presionar el elemento en el panel, este se guarda oficialmente
     * en el diagrama en la nueva posición.
     * @param event 
     */
    @FXML
    public void mouseNoPresionado(MouseEvent event){
        if(moverFiguras){
            if(entidadEncontradaMover){

                diagrama.getEntidades().add(entidadAuxiliar);
                actualizarPanel();                                             

                nombreEntidadMover = "";   
                entidadDebil=false;
                
            }
            if(relacionEncontradaMover){
                        diagrama.getRelaciones().add(relacionAuxiliar);
                        actualizarPanel(); 
                        nombreRelacionMover = ""; 
                        
            }
            if(herenciaEncontradaMover){
                diagrama.getHerencias().add(herenciaAuxiliar);
                
                actualizarPanel();
                tipoHerenciaMover = "";
                
            }
            if(atributoEncontradoMover){

                if(atributoEnEntidadMover){   
                    if(atributoEnAtributoMover){
                        diagrama.getEntidades().get(indiceEntidadAtributo).getAtributos().get(indiceEntidadAtributoAtributo).getAtributos().add(atributoAuxiliar);
                        diagrama.getEntidades().get(indiceEntidadAtributo).getAtributos().get(indiceEntidadAtributoAtributo).crearLineasunionAtributos();
                                                                
                    }
                    else{
                        diagrama.getEntidades().get(indiceEntidadAtributo).getAtributos().add(atributoAuxiliar);
                        diagrama.getEntidades().get(indiceEntidadAtributo).crearLineasunionAtributos();                                
                    }
                }
                if(atributoEnRelacionMover){
                    if(atributoEnAtributoMover){
                        diagrama.getRelaciones().get(indiceRelacionAtributo).getAtributos().get(indiceRelacionAtributoAtributo).getAtributos().add(atributoAuxiliar);
                        diagrama.getRelaciones().get(indiceRelacionAtributo).getAtributos().get(indiceRelacionAtributoAtributo).crearLineasunionAtributos();                                    
                    }
                    else{
                        diagrama.getRelaciones().get(indiceRelacionAtributo).getAtributos().add(atributoAuxiliar);
                        diagrama.getRelaciones().get(indiceRelacionAtributo).crearLineasunionAtributos(); 
                                
                            
                            
                    }
                }
                        
                        
                if(!atributoAuxiliar.getGuardadoEn().equals("Compuesto")){

                    diagrama.getAtributos().add(atributoAuxiliar);
             
                }
                atributoEnAtributoMover=false;
                atributoEnRelacionMover=false;
                atributoEnEntidadMover=false;
                nombreAtributoMover = ""; 
                            
            }

            actualizarPanel();
            relacionEncontradaMover = false;
            entidadEncontradaMover= false;
            atributoEncontradoMover = false;
            herenciaEncontradaMover = false;
            
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
        int tamano = diagrama.getRelaciones().get(indice).getEntidad().size();
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
    
    
    private void moverEntidadEnElementos(Entidad entidad){
        for(int i =0; i<diagrama.getRelaciones().size();i++){
            for(int j = 0; j<diagrama.getRelaciones().get(i).getEntidad().size(); j++){
                if(diagrama.getRelaciones().get(i).getEntidad().get(j).getNombre().equals(entidad.getNombre())){
                    
                    diagrama.getRelaciones().get(i).getEntidad().remove(j);
                    diagrama.getRelaciones().get(i).getEntidad().add(entidad);
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
                    
                    diagrama.getHerencias().get(i).getEntidadesHijas().remove(j);
                    diagrama.getHerencias().get(i).getEntidadesHijas().add(entidad);
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
        if(diagrama.cantidadEntidad()!=0 || !diagrama.getAtributos().isEmpty()){
            
            diagrama.getEntidades().clear();
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
                if(atributo.getNombre().equals(diagrama.getAtributos().get(i).getNombre())){
                    diagrama.getAtributos().remove(i);
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
            for(int i=0;i<diagrama.getEntidades().size();i++){
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


        return true;
    }    

    private boolean sobreposicionRelacion(Relacion relacion){
            for(int i=0;i<diagrama.getEntidades().size();i++){
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
            
            return true;   
            }
      
    private boolean sobreposicionEntidad(Entidad entidad){
            for(int i=0;i<diagrama.getEntidades().size();i++){
                ArrayList<Integer> x=diagrama.getEntidades().get(i).getFigura().getPosicionesX();
                ArrayList<Integer> y=diagrama.getEntidades().get(i).getFigura().getPosicionesY();
                for(int j=0;j<entidad.getFigura().getPosicionesX().size();j++){
                    int posX = entidad.getFigura().getPosicionesX().get(j);
                    int posY = entidad.getFigura().getPosicionesY().get(j);
                    if (x.get(0)-5 <= posX && x.get(1)+5 >= posX && y.get(0)-5 <= posY && y.get(3)+5 >= posY) {
                            return false;
                        }
                }    
            } 
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
        
        
    }



    @FXML
    private void ventanaEntidad() throws IOException{
        Stage stage = new Stage();
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

    
    public void creacionRelacion(boolean debil,String nombreRelacion,Diagrama diagrama,ArrayList<Entidad> entidades){
        if(debil){
            this.entidadesDisponibles = new ArrayList<>();
            entidadesDisponibles.add(entidades.get(0));
            entidadesDisponibles.add(entidades.get(1));
        }
        else{
            this.entidadesDisponibles = entidades;
        }
        
       
        this.textoRelacion=nombreRelacion;
        this.diagrama=diagrama;
        this.relacionDebil=debil;
            crearEntidad=false;
            moverFiguras = false; 
            crearRelacion=true;    
            crearAtributo = false;
            crearHerencia = false;
        
    } 

    @FXML
    private void ventanaRelacion() throws IOException{
        
        if(!diagrama.getEntidades().isEmpty()){
            Stage stage = new Stage();
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
        
        if(!diagrama.getEntidades().isEmpty()){
            Stage stage = new Stage();
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
        
        if(diagrama.getEntidades().size()>=2){
            Stage stage = new Stage();
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

    }    

    @FXML
    private void ventanaModificarDiagrama() throws IOException{
        
            Stage stage = new Stage();
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
    public void creacionHerencia(Entidad padre,ArrayList<Entidad> hijas,String tipo){
        herenciaAuxiliar = new Herencia(padre,hijas,tipo,0,0);
            crearEntidad=false;
            moverFiguras = false; 
            crearRelacion=false;
            crearAtributo = false;
            crearHerencia = true;
    }

    public void modificarDiagrama(Diagrama diagrama){
        this.diagrama=diagrama;
        this.actualizarPanel();
        
    }    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        controlador = this;
        
        
       
    }  
    
    
}
