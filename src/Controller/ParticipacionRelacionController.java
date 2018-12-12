/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Clases.Diagrama;
import Clases.Elemento;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Cristobal Andres
 */
public class ParticipacionRelacionController implements Initializable {
    @FXML private TextField elemento1;
    @FXML private TextField elemento2;
    @FXML private TextField relacion1;
    @FXML private TextField relacion2;
    
    @FXML private ComboBox participacion1;
    @FXML private ComboBox participacion2;
    @FXML private Button cerrarVentana;

    private RelacionesController controlador1;


    /**
     * Se recibe el controlador y diagrama principal.
     * @param controlador 
     * @param elementos 
     * @param relacion 
     */
    public void recibirParametros(RelacionesController controlador,ArrayList<Elemento> elementos,String relacion){

        this.controlador1=controlador;  
        if(elementos.size()==1){
            this.elemento1.setText(elementos.get(0).getNombre());
            this.elemento2.setText(elementos.get(0).getNombre());            
        }
        else{
            this.elemento1.setText(elementos.get(0).getNombre());
            this.elemento2.setText(elementos.get(1).getNombre());            
        }

        this.relacion1.setText(relacion);
        this.relacion2.setText(relacion);
        this.participacion1.getItems().addAll("Total","Parcial");
        this.participacion2.getItems().addAll("Total","Parcial");

    }
    @FXML
    private void aceptar(){
        if((this.participacion1.getValue()!=null) && (this.participacion2.getValue()!=null)){
            ArrayList<String> participacion = new ArrayList<>();
            participacion.add((String)this.participacion1.getValue());
            participacion.add((String)this.participacion2.getValue());
            this.controlador1.obtenerParticipacion(participacion);
            Stage stage = (Stage)cerrarVentana.getScene().getWindow();
            stage.close();   
        
        }
        else{
            mensaje("Complete los datos solicitados.");
        }
    }
    /**
     * Funcion que permite crear una ventana de alerta
     * @param texto: mensaje que se quiere escribir en la ventana. 
     */
    private void mensaje(String texto){
        JOptionPane.showMessageDialog(null, texto);
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
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
