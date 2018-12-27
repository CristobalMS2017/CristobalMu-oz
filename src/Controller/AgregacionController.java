/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Clases.Agregacion;
import Clases.Diagrama;
import Clases.Relacion;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Cristobal Andres
 */
public class AgregacionController implements Initializable {
    FXMLDocumentController controlador1;
    Diagrama diagrama;
    @FXML private ComboBox comboBoxRelaciones;
    @FXML private Button cerrarVentana;
    @FXML private TextField nombreAgregacion;
    
    /**
     * Recibe el diagrama y controlador principal.
     * @param controlador
     * @param diagrama 
     */
    public void recibirParametros(FXMLDocumentController controlador,Diagrama diagrama){
        this.diagrama=diagrama;
        this.controlador1=controlador;
        
        for(int i = 0; i<diagrama.getRelaciones().size();i++){
            boolean verdadero=true;
            for(int j = 0; j<diagrama.getElementos().size();j++){
                if(diagrama.getElementos().get(j) instanceof Agregacion){
                    if(((Agregacion)diagrama.getElementos().get(j)).getRelacion().equals(diagrama.getRelaciones().get(i))){
                        verdadero=false;
                    }
                }

            }
            if(verdadero){
               comboBoxRelaciones.getItems().add(diagrama.getRelaciones().get(i).getNombre());
            }

        }
        
        
    }    
    
    private Relacion relacionSeleccionada(){
        for(int i = 0; i<diagrama.getRelaciones().size();i++){
            if(diagrama.getRelaciones().get(i).getNombre().equals(comboBoxRelaciones.getValue())){
                return diagrama.getRelaciones().get(i);
            }
        }
        
        return null;
    }
    /**
     * Se crea un nombre por defecto para la agregación
     * si es que el usuario no ingresó un nombre.
     * @return retorna el nombre por defecto.
     */
    private String nombreAgregacionVacia(){
        boolean disponible=true;
        
        for(int i = 0; i<diagrama.getElementos().size();i++){
            String nombre = "Agregación "+(i+1);
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
        return "Agregación 0";
    }    
    
    /**
     * Se cierra la ventana y se envian los datos para la agregacion.
     */
    @FXML
    public void Aceptar(){
        if(nombreAgregacion.getText().isEmpty()){
            this.controlador1.crearAgregacion(relacionSeleccionada(), nombreAgregacionVacia());
        } 
        else{
           this.controlador1.crearAgregacion(relacionSeleccionada(), nombreAgregacion.getText());
        }
        Stage stage = (Stage)cerrarVentana.getScene().getWindow();
        stage.close();        
               
    }   
    
    
    
    
    /**
     * Solamente se cierra la ventana.
     */
    @FXML
    public void Cancelar(){
        Stage stage = (Stage)cerrarVentana.getScene().getWindow();
        stage.close();        
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
