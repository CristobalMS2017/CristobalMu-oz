/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Clases.Agregacion;
import Clases.Diagrama;
import Clases.Elemento;
import Clases.Entidad;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 * Controlador Ventana Relaciones
 * @author Cristobal Muñoz Salinas
 */
public class RelacionesController implements Initializable {
    Diagrama diagrama;
    FXMLDocumentController controlador1;
    @FXML private AnchorPane root;
    @FXML private TextField textoRelacion;
   
    @FXML private Button cerrarVentana;
    @FXML ListView<CheckBox> entidadesDisponibles = new ListView<>();
    @FXML ListView<CheckBox> agregacionesDisponibles = new ListView<>();
    @FXML ComboBox TipoEntidades;
    @FXML private Pane relacionDebil;
    @FXML private Pane relacionFuerte;
    @FXML private ComboBox entidadFuerte;
    @FXML private ComboBox entidadDebil;
    @FXML private ComboBox tipoRelacion;
    private boolean tipoDebil=false;
    private ArrayList<String> participacion = new ArrayList<>();
    RelacionesController controladorRelacion;
    
    
    
    
    public void recibirParametrosEntidadDebil(FXMLDocumentController controlador,Diagrama diagrama,String entidadDebil){
        this.diagrama=diagrama;
        this.controlador1=controlador;
        tipoRelacion.setValue("Débil");
        tipoRelacion.setDisable(true);
        this.tipoRelacion();
        this.cerrarVentana.setDisable(true);
        this.entidadDebil.setValue(entidadDebil);
        this.entidadDebil.setDisable(true);
        for(int i = 0; i<diagrama.getElementos().size();i++){
            if(diagrama.getElementos().get(i) instanceof Entidad){
                if(!((Entidad)diagrama.getElementos().get(i)).isDebil()){
                    this.entidadFuerte.getItems().add(diagrama.getElementos().get(i).getNombre());
                }
            }
        }        
        
    }
    /**
     * Se recibe el controlador y diagrama principal.
     * @param controlador
     * @param diagrama 
     */
    public void recibirParametros(FXMLDocumentController controlador,Diagrama diagrama){
        this.diagrama=diagrama;
        this.controlador1=controlador;   
        for(int i = 0; i<diagrama.getElementos().size();i++){
            if(diagrama.getElementos().get(i) instanceof Entidad){
                if(!((Entidad)diagrama.getElementos().get(i)).isDebil()){
                    entidadesDisponibles.getItems().add(new CheckBox(diagrama.getElementos().get(i).getNombre()));
                }                
            }
            else{
                entidadesDisponibles.getItems().add(new CheckBox(diagrama.getElementos().get(i).getNombre()));
            }


        }  
        for(int i = 0; i<diagrama.getElementos().size();i++){
            if(diagrama.getElementos().get(i) instanceof Entidad){
                if(((Entidad)diagrama.getElementos().get(i)).isDebil()){
                    entidadDebil.getItems().add(diagrama.getElementos().get(i).getNombre());
                }
                else{
                    entidadFuerte.getItems().add(diagrama.getElementos().get(i).getNombre());
                }
        }
        
       
        }
         tipoRelacion.getItems().addAll("Fuerte","Débil");
         this.participacion.add("Parcial");
         this.participacion.add("Parcial");
    }
    /**
     * Se revisa que tipo de relación se quiere hacer, si relación débil o fuerte,
     * habilitando la creación de relación seleccionada.
     */
    @FXML
    private void tipoRelacion(){
        if(tipoRelacion.getValue().equals("Fuerte")){
            tipoDebil = false;
            relacionDebil.setVisible(false);
            relacionDebil.setDisable(true);;
            relacionFuerte.setVisible(true);
            relacionFuerte.setDisable(false);;            
        }
        if(tipoRelacion.getValue().equals("Débil")){
            tipoDebil = true;
            relacionDebil.setVisible(true);
            relacionDebil.setDisable(false);;
            relacionFuerte.setVisible(false);
            relacionFuerte.setDisable(true);;            
        }
    }
    
    /**
     * Se valida que no se repita el nombre de la relación.
     * @return 
     */
    private boolean validarNombreParaRelacion(){
        
        for(int i=0;i<diagrama.getRelaciones().size();i++){
            if(diagrama.getRelaciones().get(i).getNombre().equals(textoRelacion.getText())){
                return false;
            }
        }
        return true;
    }     
    public void obtenerParticipacion(ArrayList<String> participacion){
        this.participacion=participacion;
    }
    /**
     * Se crea un nombre por defecto si es que el usuario no ingresó uno para la relación.
     * @return 
     */
    private String nombreRelacionVacia(){
        boolean disponible=true;
        
        for(int i = 0; i<diagrama.getRelaciones().size();i++){
            String nombre = "Relación "+(i+1);
            for(int j=0;j<diagrama.getRelaciones().size();j++){
                if(diagrama.getRelaciones().get(j).getNombre().equals(nombre)){
                    j=diagrama.getRelaciones().size();
                    disponible=false;
                }
            }
            if(disponible){
                return nombre;
            }
            disponible = true;
        }
        return "Relación 0";
    }

    private ArrayList<Elemento> elementosSeleccionados(){
            ArrayList<Elemento> elementosSeleccionados = new ArrayList<>();
            if(tipoDebil){
                
                for(int i = 0; i<diagrama.getElementos().size();i++){
                    if(entidadFuerte.getValue().equals(diagrama.getElementos().get(i).getNombre())){
                        elementosSeleccionados.add((Entidad)diagrama.getElementos().get(i));
                    }
                        

                        
                }

                for(int i = 0; i<diagrama.getElementos().size();i++){
                        if(entidadDebil.getValue().equals(diagrama.getElementos().get(i).getNombre())){
                            elementosSeleccionados.add(diagrama.getElementos().get(i));                            
                        }       
                    }  
                
            }
            else{
                for(int i=0;i<this.entidadesDisponibles.getItems().size();i++){
                    if(this.entidadesDisponibles.getItems().get(i).isSelected() ){
                        for(int j = 0; j<diagrama.getElementos().size();j++){
                            if(entidadesDisponibles.getItems().get(i).getText().equals(diagrama.getElementos().get(j).getNombre())){
                                elementosSeleccionados.add(diagrama.getElementos().get(j));
                            }
                        }
                        
                    }
                }                    
            }  
        return elementosSeleccionados;    
    }

    
    /**
     * Se cierra la ventana y se envian los datos para la creación de la relación.
     */
    @FXML
    private void Aceptar(){
        

        if(validarNombreParaRelacion()){
            ArrayList<Elemento> elementosSeleccionados = elementosSeleccionados();

            if((elementosSeleccionados.size())<=2){
                if(textoRelacion.getText().isEmpty()){
                    controlador1.creacionRelacion(tipoDebil,nombreRelacionVacia(),this.participacion,this.diagrama,elementosSeleccionados);
                } 
                else{
                    controlador1.creacionRelacion(tipoDebil,textoRelacion.getText(),this.participacion,this.diagrama,elementosSeleccionados);
                }


                Stage stage = (Stage)cerrarVentana.getScene().getWindow();
                stage.close();
            }
            else{
                mensaje("Solo puede seleccionar un maximo de 2 elementos");
            }
        }
        else{
            mensaje("Nombre ingresado anteriormente. Por favor,\n"
                    + "     ingrese un nuevo nombre.");
            textoRelacion.clear();
        }
    }
    @FXML
    private void ventanaParticipacion() throws IOException{
        Stage stage = new Stage();
        stage.setTitle("Participación");
        stage.setResizable(false);
        FXMLLoader loader = new FXMLLoader();
        AnchorPane root1 = (AnchorPane)loader.load(getClass().getResource("ParticipacionRelacion.fxml").openStream());
        ParticipacionRelacionController instanciaControlador = (ParticipacionRelacionController)loader.getController();
        instanciaControlador.recibirParametros(this.controladorRelacion,elementosSeleccionados(),this.textoRelacion.getText());
        Scene scene = new Scene(root1);
        stage.setScene(scene);
        stage.alwaysOnTopProperty();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        
    }    
    /**
     * Solamente se cierra la ventana.
     */
    @FXML
    private void Cancelar(){
        Stage stage = (Stage)cerrarVentana.getScene().getWindow();
        stage.close();        
    }
    
    /**
     * Funcion que permite crear una ventana de alerta
     * @param texto: mensaje que se quiere escribir en la ventana. 
     */
    private void mensaje(String texto){
        JOptionPane.showMessageDialog(null, texto);
    }     
    @FXML
    private void elementosSeleccionadosParaRelacion(){
        int contador=0;
        for(int i = 0; i<this.entidadesDisponibles.getItems().size();i++){
            if(this.entidadesDisponibles.getItems().get(i).isSelected()){
                contador++;
            }
        }
        for(int i = 0; i<this.agregacionesDisponibles.getItems().size();i++){
            if(this.agregacionesDisponibles.getItems().get(i).isSelected()){
                contador++;
            }
        }  
        if(contador>2){
            mensaje("Solo puede seleccionar 2 elementos");
        }
    }
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        controladorRelacion = this;

        
    }    
   
}
