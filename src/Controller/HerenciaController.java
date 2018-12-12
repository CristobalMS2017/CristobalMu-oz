/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

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
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 * Controlador de la ventana herencia
 * @author Cristobal Muñoz Salinas
 */
public class HerenciaController implements Initializable {
    FXMLDocumentController controlador1;
    Diagrama diagrama;
    @FXML private AnchorPane root;
    @FXML private Button cerrarVentana;
    @FXML private RadioButton disyuncion;
    @FXML private RadioButton solapamiento;
    @FXML private ComboBox entidadPadre;
    @FXML ListView<CheckBox> entidadesHijas = new ListView<>();
    private String tipoHerencia;
    private int indicePadre;

    
    
    /**
     * Recibe el controlador y diagrama principal.
     * @param controlador
     * @param diagrama 
     */
    public void recibirParametros(FXMLDocumentController controlador,Diagrama diagrama){
        this.diagrama=diagrama;
        this.controlador1=controlador;
        this.solapamiento();
        
        for(int i = 0; i<diagrama.getElementos().size();i++){
            if(diagrama.getElementos().get(i) instanceof Entidad){
                entidadPadre.getItems().add(diagrama.getElementos().get(i).getNombre()); 
            }
                    
        }       
    }    
    
    /**
     * Se elimina la entidad seleccionada como padre, del listview de las
     * entidades hijas.
     */
    @FXML
    private void eliminarPadreDeHijas(){
        entidadesHijas.getItems().clear();
        for(int i = 0; i<diagrama.getElementos().size();i++){
            if(!diagrama.getElementos().get(i).getNombre().equals((String)entidadPadre.getValue())){
                entidadesHijas.getItems().add(new CheckBox(diagrama.getElementos().get(i).getNombre()));                 
            }
            else{
                indicePadre = i;
            }
           
        }
    }
    
    
    /**
     * Tipo de herencia seleccionado: solapamiento.
     */
    @FXML
    private void solapamiento(){
        disyuncion.setSelected(false);
        solapamiento.setSelected(true);
        tipoHerencia = "S";
    }
    /**
     * Tipo de herencia seleccionado: disyunción.
     */
    @FXML
    private void disyuncion(){
        solapamiento.setSelected(false);
        disyuncion.setSelected(true);
        tipoHerencia = "D";
    }
    /**
     * Se crea un arrayList con las entidades seleccionadas para ser partes
     * como hijas.
     * @return ArrayList con entidades hijas.
     */
    private ArrayList<Entidad> entidadesHijas(){

        ArrayList<Entidad> entidades = new ArrayList<>();
        for(int i = 0; i<entidadesHijas.getItems().size();i++){
            if(entidadesHijas.getItems().get(i).isSelected() ){
                for(int j=0; j<diagrama.getElementos().size();j++){
                    if(diagrama.getElementos().get(i) instanceof Entidad){
                        if(entidadesHijas.getItems().get(i).getText().equals(diagrama.getElementos().get(j).getNombre())){
                            entidades.add((Entidad)diagrama.getElementos().get(j));
                        }
                    }

                }
            }    
        }
        
        return entidades;
    }
        
    /**
     * Funcion que permite crear una ventana de alerta
     * @param texto: mensaje que se quiere escribir en la ventana. 
     */
    public void mensaje(String texto){
        JOptionPane.showMessageDialog(null, texto);
    }
    
    /**
     * Se cierra la ventana y se envian los datos para crear la herencia.
     */
    public void Aceptar(){
        
        if(!entidadesHijas().isEmpty()){
            
        
            controlador1.creacionHerencia((Entidad)diagrama.getElementos().get(indicePadre), entidadesHijas(), tipoHerencia);


            Stage stage = (Stage)cerrarVentana.getScene().getWindow();
            stage.close();
        }
        else{
            mensaje("Debe completar todos los datos");
        }
        

    }
    /**
     * Solamente se cierra la ventana
     */
    @FXML
    public void Cancelar(){
        Stage stage = (Stage)cerrarVentana.getScene().getWindow();
        stage.close();        
    }    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
}
