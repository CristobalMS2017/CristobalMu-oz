/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Clases.Diagrama;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 * Controlador ventana atributo
 * @author Cristobal Muñoz Salinas
 */
public class AtributosController implements Initializable {
    FXMLDocumentController controlador1;
    Diagrama diagrama;
    
    @FXML private AnchorPane root;
    @FXML private ComboBox destinoAtributo;
    @FXML private ComboBox entidadesAtributosRelaciones;
    @FXML private ComboBox tiposAtributos;
    @FXML private TextField textoAtributo;
    @FXML private Button cerrarVentana;
    
    
    /**
     * Recibe el diagrama y controlador principal.
     * @param controlador
     * @param diagrama 
     */
    public void recibirParametros(FXMLDocumentController controlador,Diagrama diagrama){
        this.diagrama=diagrama;
        this.controlador1=controlador;

        
        
    }    
    
    
    /**
     * Se utiliza para saber si se quiere crear un atributo en una entidad, relación o atributo compuesto.
     */
    @FXML
    private void crearAtributoEn(){
        String destino = (String)destinoAtributo.getValue();
        if(destino.equals("Entidad")){
            tiposAtributos.getItems().clear();
            tiposAtributos.getItems().addAll("Generico","Clave","Clave Parcial","Multivaluado","Derivado","Compuesto");
            entidadesAtributosRelaciones.getItems().clear();
            entidadesAtributosRelaciones.setValue("Seleccione Entidad");
            for(int i=0; i<diagrama.getEntidades().size();i++){
                entidadesAtributosRelaciones.getItems().add(diagrama.getEntidades().get(i).getNombre());
            }
        }
        if(destino.equals("Relación")){
            tiposAtributos.getItems().clear();
            tiposAtributos.getItems().addAll("Generico","Clave","Clave Parcial","Multivaluado","Derivado","Compuesto");
            entidadesAtributosRelaciones.getItems().clear();
            entidadesAtributosRelaciones.setValue("Seleccione Relación");
            for(int i=0; i<diagrama.getRelaciones().size();i++){
                entidadesAtributosRelaciones.getItems().add(diagrama.getRelaciones().get(i).getNombre());
            }
        }
        if(destino.equals("Atributo")){
            tiposAtributos.getItems().clear();
            tiposAtributos.setValue("Tipo");
            tiposAtributos.getItems().addAll("Generico");
            entidadesAtributosRelaciones.getItems().clear();
            entidadesAtributosRelaciones.setValue("Seleccione Atributo");
            for(int i=0;i<diagrama.getRelaciones().size();i++){
                for(int j=0; j<diagrama.getRelaciones().get(i).getAtributos().size();j++){
                    if("Compuesto".equals(diagrama.getRelaciones().get(i).getAtributos().get(j).getTipoAtributo())){
                        entidadesAtributosRelaciones.getItems().add(diagrama.getRelaciones().get(i).getAtributos().get(j).getNombre());
                    }
                }
            }
            for(int i=0;i<diagrama.getEntidades().size();i++){
                for(int j=0; j<diagrama.getEntidades().get(i).getAtributos().size();j++){
                    if("Compuesto".equals(diagrama.getEntidades().get(i).getAtributos().get(j).getTipoAtributo())){
                        entidadesAtributosRelaciones.getItems().add(diagrama.getEntidades().get(i).getAtributos().get(j).getNombre());
                    }
                }
            }
            
        }
    }
    private boolean validarNombreParaAtributo(){
        
        for(int i=0;i<diagrama.getAtributos().size();i++){
            if(diagrama.getAtributos().get(i).getNombre().equals(textoAtributo.getText())){
                return false;
            }
            for(int j=0; j<diagrama.getAtributos().get(i).getAtributos().size();j++){
                if(diagrama.getAtributos().get(i).getAtributos().get(j).getNombre().equals(textoAtributo.getText())){
                    return false;
                }
            }
            
        }
        return true;
    }     
    /**
     * Se crea un nombre por defecto si es que el usuario no ingresó un nombre para el atributo.
     * @return retorna el nombre por defecto.
     */
    private String nombreAtributoVacio(){
        boolean disponible=true;
        int numeroAtributos=0;
        for(int i = 0; i< diagrama.getAtributos().size();i++){
            numeroAtributos +=diagrama.getAtributos().get(i).getAtributos().size()+1;
        }
        
        for(int i = 0; i<numeroAtributos;i++){
            String nombre = "Atributo "+(i+1);
            for(int j=0;j<diagrama.getAtributos().size();j++){
                if(diagrama.getAtributos().get(j).nombrePorDefectoRepetido(nombre)){
                    j=diagrama.getAtributos().size();
                    disponible=false;
                }
            }
            if(disponible){
                return nombre;
            }
            disponible = true;
        }
        return "Atributo 0";
    }

    /**
     * Se cierra la ventana y se envian los datos necesarios para la creación del atributo.
     */
    public void Aceptar(){
        if(validarNombreParaAtributo()){
            if(textoAtributo.getText().isEmpty()){
                controlador1.creacionAtributo((String)tiposAtributos.getValue(),(String)destinoAtributo.getValue(),(String)entidadesAtributosRelaciones.getValue(),this.diagrama,nombreAtributoVacio());
                
            }
            else{
                controlador1.creacionAtributo((String)tiposAtributos.getValue(),(String)destinoAtributo.getValue(),(String)entidadesAtributosRelaciones.getValue(),this.diagrama,textoAtributo.getText());
            }

            Stage stage = (Stage)cerrarVentana.getScene().getWindow();
            stage.close();            
        }    
        else{
            mensaje("Nombre registrado anteriormente. Intente con otro.");
        }

        

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
       tiposAtributos.getItems().addAll("Generico","Clave","Clave Parcial","Multivaluado","Derivado","Compuesto");
       tiposAtributos.setValue("Tipo");
       destinoAtributo.getItems().addAll("Entidad","Relación","Atributo");
       destinoAtributo.setValue("Destino");
       textoAtributo.clear();
    }    
    
}
