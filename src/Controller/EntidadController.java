/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Clases.Diagrama;
import Clases.Entidad;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Cristobal Muñoz Salinas
 */
public class EntidadController implements Initializable {
    
    Diagrama diagrama;
    FXMLDocumentController controlador1;

    @FXML private TextField textoEntidad;
    @FXML private CheckBox entidadDebil;
    @FXML private Button cerrarVentana;
    
    
    /**
     * Se reciben el diagrama y controlador principal.
     * @param stage
     * @param diagrama 
     */
    public void recibirParametros(FXMLDocumentController stage, Diagrama diagrama){
        this.diagrama=diagrama;
        controlador1=stage;
  
    }
    
    /**
     * Valida que no se repita un nombre para la entidad.
     * @return 
     */
    private boolean validarNombreParaEntidad(){
        
        for(int i=0;i<diagrama.getElementos().size();i++){
            if(diagrama.getElementos().get(i).getNombre().equals(textoEntidad.getText())){
                return false;
            }
        }
        return true;
    } 

    /**
     * Se crea un nombre por defecto para la entidad
     * si es que el usuario no ingresó un nombre.
     * @return retorna el nombre por defecto.
     */
    private String nombreEntidadVacia(){
        boolean disponible=true;
        
        for(int i = 0; i<diagrama.getElementos().size();i++){
            String nombre = "Entidad "+(i+1);
            for(int j=0;j<diagrama.getElementos().size();j++){
                if(diagrama.getElementos().get(j).getNombre().equals(nombre)){
                    j=diagrama.getElementos().size();
                    disponible=false;
                }
            }
            if(disponible){
                return nombre;
            }
            disponible = true;
        }
        return "Entidad 0";
    }
    private boolean validarEntidadDebil(){
        if(this.entidadDebil.isSelected()){
            for(int i = 0; i<diagrama.getElementos().size();i++){
                if(diagrama.getElementos().get(i) instanceof Entidad){
                    if(!((Entidad)diagrama.getElementos().get(i)).isDebil()){
                        return true;
                    }
                }
            }
            return false;
        }
        return true;
    }
    /**
     * Se cierra la ventana y se envian los datos para crear la entdad.
     */
    public void Aceptar(){
        if(validarNombreParaEntidad()){
            if(validarEntidadDebil()){
                if(textoEntidad.getText().isEmpty()){
                    controlador1.obtenerEntidad(nombreEntidadVacia(), entidadDebil.isSelected(),this.diagrama);
                } 
                else{
                    controlador1.obtenerEntidad(textoEntidad.getText(), entidadDebil.isSelected(),this.diagrama);
                }
                Stage stage = (Stage)cerrarVentana.getScene().getWindow();
                stage.close();
            }
            else{
                mensaje("Debe crear una entidad fuerte para poder crear"
                        + "\n        una entidad débil");
                Stage stage = (Stage)cerrarVentana.getScene().getWindow();
                stage.close();                
            }
        }
        else{
            mensaje("Nombre ingresado anteriormente. Por favor,\n"
                    + "     ingrese un nuevo nombre.");
            textoEntidad.clear();
        }
    }
    /**
     * Solamente se cierra la ventana entidad.
     */
    @FXML
    public void Cancelar(){
        Stage stage = (Stage)cerrarVentana.getScene().getWindow();
        stage.close();        
    }
    
    /**
     * Funcion que permite crear una ventana de alerta
     * @param texto: mensaje que se quiere escribir en la ventana. 
     */
    public void mensaje(String texto){
        JOptionPane.showMessageDialog(null, texto);
    }    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
}
