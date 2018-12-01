/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Clases.Agregacion;
import Clases.Diagrama;
import Clases.Entidad;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
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
    
    /**
     * Se recibe el controlador y diagrama principal.
     * @param controlador
     * @param diagrama 
     */
    public void recibirParametros(FXMLDocumentController controlador,Diagrama diagrama){
        this.diagrama=diagrama;
        this.controlador1=controlador;   
        for(int i = 0; i<diagrama.getEntidades().size();i++){
            if(!diagrama.getEntidades().get(i).isDebil()){
                entidadesDisponibles.getItems().add(new CheckBox(diagrama.getEntidades().get(i).getNombre()));
            }

        }  
        for(int i = 0; i<diagrama.getAgregaciones().size();i++){
            agregacionesDisponibles.getItems().add(new CheckBox(diagrama.getAgregaciones().get(i).getNombre()));
        }
        for(int i = 0; i<diagrama.getEntidades().size();i++){
            if(diagrama.getEntidades().get(i).isDebil()){
                entidadDebil.getItems().add(diagrama.getEntidades().get(i).getNombre());
            }
            else{
                entidadFuerte.getItems().add(diagrama.getEntidades().get(i).getNombre());
            }
        }
        
        tipoRelacion.getItems().addAll("Fuerte","Débil");
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

    

    
    /**
     * Se cierra la ventana y se envian los datos para la creación de la relación.
     */
    @FXML
    private void Aceptar(){
        

        if(validarNombreParaRelacion()){
            ArrayList<Entidad> entidadesSeleccionadas = new ArrayList<>();
            ArrayList<Agregacion> agregacionesSeleccionadas = new ArrayList<>();
            if(tipoDebil){
                
                for(int i = 0; i<diagrama.getEntidades().size();i++){
                    if(entidadFuerte.getValue().equals(diagrama.getEntidades().get(i).getNombre())){
                        entidadesSeleccionadas.add(diagrama.getEntidades().get(i));
                    }
                        

                        
                }

                for(int i = 0; i<diagrama.getEntidades().size();i++){
                        if(entidadDebil.getValue().equals(diagrama.getEntidades().get(i).getNombre())){
                            entidadesSeleccionadas.add(diagrama.getEntidades().get(i));                            
                        }       
                    }  
                
            }
            else{
                for(int i=0;i<this.entidadesDisponibles.getItems().size();i++){
                    if(this.entidadesDisponibles.getItems().get(i).isSelected() ){
                        for(int j = 0; j<diagrama.getEntidades().size();j++){
                            if(entidadesDisponibles.getItems().get(i).getText().equals(diagrama.getEntidades().get(j).getNombre())){
                                entidadesSeleccionadas.add(diagrama.getEntidades().get(j));
                            }
                        }
                        
                    }
                }    
                for(int i=0;i<this.agregacionesDisponibles.getItems().size();i++){
                    if(this.agregacionesDisponibles.getItems().get(i).isSelected() ){
                        for(int j = 0; j<diagrama.getAgregaciones().size();j++){
                            if(agregacionesDisponibles.getItems().get(i).getText().equals(diagrama.getAgregaciones().get(j).getNombre())){
                                agregacionesSeleccionadas.add(diagrama.getAgregaciones().get(j));
                            }
                        }
                        
                    }
                }                 
            }
            if((agregacionesSeleccionadas.size()+entidadesSeleccionadas.size())<=2){
                if(textoRelacion.getText().isEmpty()){
                    controlador1.creacionRelacion(tipoDebil,nombreRelacionVacia(),this.diagrama,entidadesSeleccionadas,agregacionesSeleccionadas);
                } 
                else{
                    controlador1.creacionRelacion(tipoDebil,textoRelacion.getText(),this.diagrama,entidadesSeleccionadas,agregacionesSeleccionadas);
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
    
    
    
    
    
    
    
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        
    }    
    
}
