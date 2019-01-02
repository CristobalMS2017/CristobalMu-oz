/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Clases.Agregacion;
import Clases.Atributo;
import Clases.Diagrama;
import Clases.Elemento;
import Clases.Entidad;
import Clases.Relacion;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 * Controlador modificar diagrama
 * Los elementos se modifican directamente en el diagrama principal.
 * @author Cristobal Muñoz Salinas
 */
public class ModificarDiagramaController implements Initializable {
    
    
    FXMLDocumentController controlador1;
    ModificarDiagramaController controlador;
    Diagrama diagrama;
    @FXML private AnchorPane root;
    @FXML private Button cerrarVentana;
    @FXML private ComboBox comboBoxEntidad;
    @FXML private TextField modificarNombreEntidad;
    @FXML private TextField modificarNombreRelacion;
    @FXML private TextField modificarNombreAtributo;
    
    @FXML private ComboBox  comboBoxRelacion;
    @FXML private ComboBox comboBoxHerencia;


    @FXML private ComboBox tipoOrigenAtributo;//Contiene el tipo de objeto del origen del atributo(Entidad, relación o atributo).
    @FXML private ComboBox origenAtributo; //Contiene los elementos del tipo seleccionedo
    @FXML private ComboBox comboBoxAtributo; //Atributos contenidos en el elemento seleccionado;    
    @FXML private ListView<CheckBox> listaAtributosEntidad;
    @FXML private ListView<CheckBox> listaAtributosRelacion;
    @FXML private ListView<CheckBox> listaEntidadesRelacion;  
    @FXML private ListView<CheckBox> listaEntidadesHijasHerencia; 
    
    
    
    @FXML private RadioButton disyuncion;
    @FXML private RadioButton solapamiento;    
    
    
    private String tipoHerencia;
    private int entidadSeleccionada;
    private int relacionSeleccionada;
    private int herenciaSeleccionada;
    private boolean modificacionParticipacion=false;
    private ArrayList<String> participacionModificada;
    
    
    
    
    /**
     * Se recibe el diagrama y el controlador principal
     * Se cargan los comboBox de cada seccion en base a los datos del diagrama.
     * @param controlador controlador principal
     * @param diagrama diagrama principal.
     */
    public void recibirParametros(FXMLDocumentController controlador,Diagrama diagrama){
        this.diagrama=diagrama;
        this.controlador1=controlador;
        for(int i = 0; i<diagrama.getElementos().size();i++){
            comboBoxEntidad.getItems().add(diagrama.getElementos().get(i).getNombre());
        }
        for(int i = 0; i<diagrama.getRelaciones().size();i++){
            comboBoxRelacion.getItems().add(diagrama.getRelaciones().get(i).getNombre());
        }        
        for(int i = 0; i<diagrama.getHerencias().size();i++){
            comboBoxHerencia.getItems().add(diagrama.getHerencias().get(i).getEntidadPadre().getNombre());
        }    

    }  

    
    
    /**
     * Agrega las nombres de los elementos que contienen al atributo, en el combobox "origenAtributo".
     */
    @FXML
    private void origenAtributo(){
        origenAtributo.getItems().clear();
        if(tipoOrigenAtributo!=null){
            if(tipoOrigenAtributo.getValue().equals("Entidad")){
                for(int i = 0; i<diagrama.getElementos().size();i++){
                    if(diagrama.getElementos().get(i) instanceof Entidad){
                        origenAtributo.getItems().add(diagrama.getElementos().get(i).getNombre());
                    }
                    
                }            

            }
            if(tipoOrigenAtributo.getValue().equals("Relación")){
                for(int i = 0; i<diagrama.getRelaciones().size();i++){
                    origenAtributo.getItems().add(diagrama.getRelaciones().get(i).getNombre());
                }             
            }
            if(tipoOrigenAtributo.getValue().equals("Atributo Compuesto (Relación)")){
                for(int i = 0 ; i < diagrama.getAtributos().size();i++){
                    if(diagrama.getAtributos().get(i).getGuardadoEn().equals("Relación")){
                        if(diagrama.getAtributos().get(i).getTipoAtributo().equals("Compuesto")){
                            origenAtributo.getItems().add(diagrama.getAtributos().get(i).getNombre());
                        }
                        
                    }
                }
                
                
            }
            if(tipoOrigenAtributo.getValue().equals("Atributo Compuesto (Entidad)")){
                for(int i = 0 ; i < diagrama.getAtributos().size();i++){
                    if(diagrama.getAtributos().get(i).getGuardadoEn().equals("Entidad")){
                        if(diagrama.getAtributos().get(i).getTipoAtributo().equals("Compuesto")){
                            origenAtributo.getItems().add(diagrama.getAtributos().get(i).getNombre());
                        }
                        
                    }
                }
                
                
            }            
            

        }
    }
    
    /**
     * Agrega los atributos del elemento seleccionado(entidad, relación o atributo compuesto) al comboBox
     * "comboBoxAtributo".
     */
    @FXML 
    private void atributosEncontrados(){
        
        if(tipoOrigenAtributo.getValue()!=null){
            comboBoxAtributo.getItems().clear();
            if(((String)tipoOrigenAtributo.getValue()).equals("Entidad")&tipoOrigenAtributo.getValue()!=null){
                for(int i = 0; i<diagrama.getElementos().size();i++){
                    if(origenAtributo.getValue()!=null && ((String)origenAtributo.getValue()).equals(diagrama.getElementos().get(i).getNombre())){
                        for(int j = 0; j<((Entidad)diagrama.getElementos().get(i)).getAtributos().size();j++){
                            comboBoxAtributo.getItems().add(((Entidad)diagrama.getElementos().get(i)).getAtributos().get(j).getNombre());
                        }
                    }

                }
            }
            if(tipoOrigenAtributo.getValue().equals("Relación")){
                for(int i = 0; i<diagrama.getRelaciones().size();i++){
                    if(origenAtributo.getValue().equals(diagrama.getRelaciones().get(i).getNombre())){
                        for(int j = 0; j<diagrama.getRelaciones().get(i).getAtributos().size();j++){
                            comboBoxAtributo.getItems().add(diagrama.getRelaciones().get(i).getAtributos().get(j).getNombre());
                        }
                    }

                }            
            }
            if(tipoOrigenAtributo.getValue().equals("Atributo Compuesto (Relación)")){
                for(int i = 0; i<diagrama.getAtributos().size();i++){
                    if(diagrama.getAtributos().get(i).getGuardadoEn().equals("Relación")){
                        if(diagrama.getAtributos().get(i).getTipoAtributo().equals("Compuesto")){
                            if(diagrama.getAtributos().get(i).getNombre().equals(origenAtributo.getValue())){
                                for(int j = 0; j<diagrama.getAtributos().get(i).getAtributos().size();j++){
                                    comboBoxAtributo.getItems().add(diagrama.getAtributos().get(i).getAtributos().get(j).getNombre());
                                }                                
                            }                                                        
                        }                        
                    }
                }      
            }
            if(tipoOrigenAtributo.getValue().equals("Atributo Compuesto (Entidad)")){
                for(int i = 0; i<diagrama.getAtributos().size();i++){
                    if(diagrama.getAtributos().get(i).getGuardadoEn().equals("Entidad")){
                        if(diagrama.getAtributos().get(i).getTipoAtributo().equals("Compuesto")){
                            if(diagrama.getAtributos().get(i).getNombre().equals(origenAtributo.getValue())){
                                for(int j = 0; j<diagrama.getAtributos().get(i).getAtributos().size();j++){
                                    comboBoxAtributo.getItems().add(diagrama.getAtributos().get(i).getAtributos().get(j).getNombre());
                                }                                
                            }                                                        
                        }                        
                    }
                }      
            }
        }
    }
    
    
    /**
     * Inicializa la sección de la relación.
     */
    @FXML 
    private void seccionRelacion(){
        listaAtributosRelacion.getItems().clear();
        if(comboBoxRelacion.getValue()!=null){
            for(int i = 0; i<diagrama.getRelaciones().size();i++){
                if(((String)comboBoxRelacion.getValue()).equals(diagrama.getRelaciones().get(i).getNombre())){
                    relacionSeleccionada=i;
                    for(int j = 0; j<diagrama.getRelaciones().get(i).getAtributos().size();j++){
                        CheckBox nuevo = new CheckBox(diagrama.getRelaciones().get(i).getAtributos().get(j).getNombre());
                        nuevo.setSelected(true);
                        listaAtributosRelacion.getItems().add(nuevo);
                    }
                }
            }  
            listaEntidadesRelacion.getItems().clear();
            for(int i = 0; i<diagrama.getRelaciones().size();i++){
                if(comboBoxRelacion.getValue().equals(diagrama.getRelaciones().get(i).getNombre())){
                    for(int j = 0; j<diagrama.getRelaciones().get(i).getElementos().size();j++){
                        CheckBox nuevo = new CheckBox(diagrama.getRelaciones().get(i).getElementos().get(j).getNombre());
                        nuevo.setSelected(true);
                        listaEntidadesRelacion.getItems().add(nuevo);
                    }
                }
            }
        }
    }
    
    /**
     * Inicializa la sección herencia.
     */
    @FXML 
    private void seccionHerencia(){
        
        
        listaEntidadesHijasHerencia.getItems().clear();
        if(comboBoxHerencia.getValue()!=null){


            for(int i = 0; i<diagrama.getHerencias().size();i++){

                if(((String)comboBoxHerencia.getValue()).equals(diagrama.getHerencias().get(i).getEntidadPadre().getNombre())){
                    herenciaSeleccionada = i;
                    for(int j = 0; j<diagrama.getHerencias().get(i).getEntidadesHijas().size();j++){
                        CheckBox nuevo = new CheckBox(diagrama.getHerencias().get(i).getEntidadesHijas().get(j).getNombre());
                        nuevo.setSelected(true);
                        listaEntidadesHijasHerencia.getItems().add(nuevo);
                    }
                }
            }
            if(herenciaSeleccionada!=-1 && !diagrama.getHerencias().isEmpty()){
                if(diagrama.getHerencias().get(herenciaSeleccionada).getTipoHerencia().equals("S")){
                    solapamiento();
                }
                else{
                    disyuncion();
                }            
            }

        }
    }
    /**
     * Funcion que permite crear una ventana de alerta
     * @param texto: mensaje que se quiere escribir en la ventana. 
     */
    public void mensaje(String texto){
        JOptionPane.showMessageDialog(null, texto);
    }    
    
    
    /**
     * Inicializa la sección entidad.
     */
    @FXML 
    private void seccionEntidad(){
        listaAtributosEntidad.getItems().clear();
        if(((String)comboBoxEntidad.getValue())!=null){
            for(int i = 0; i<diagrama.getElementos().size();i++){
                    if(((String)comboBoxEntidad.getValue()).equals(diagrama.getElementos().get(i).getNombre())){
                        entidadSeleccionada = i;
                        if(diagrama.getElementos().get(i) instanceof Entidad){
                            for(int j = 1; j<((Entidad)diagrama.getElementos().get(i)).getAtributos().size();j++){
                                CheckBox nuevo = new CheckBox(((Entidad)diagrama.getElementos().get(i)).getAtributos().get(j).getNombre());
                                nuevo.setSelected(true);
                                listaAtributosEntidad.getItems().add(nuevo);
                            }
                    }
                }
                
            }           
        }

    }    
    
    /**
     * Se cierra la ventana
     */
    @FXML
    private void Cancelar() throws CloneNotSupportedException{

        Stage stage = (Stage)cerrarVentana.getScene().getWindow();
        stage.close();        
    }  
    
    
    /**
     * Se elimina la entidad seleccionada en la sección entidad
     */
    @FXML
    private void eliminarEntidad(){
        
        if(!((String)comboBoxEntidad.getValue()).isEmpty()){
            for(int i =0; i<diagrama.getElementos().size();i++){
                if(comboBoxEntidad.getValue().equals(diagrama.getElementos().get(i).getNombre())){
                    if(diagrama.getElementos().get(i) instanceof Agregacion){
                        diagrama.eliminarAgregacionEnRelacion(((Agregacion)diagrama.getElementos().get(i)));
                    }
                   
                        diagrama.eliminarElemento(diagrama.getElementos().get(i));
                        i=0;
                    
                    
                }

            }
            listaAtributosEntidad.getItems().clear();
            comboBoxEntidad.getItems().clear();
            comboBoxEntidad.setValue("");
            modificarNombreEntidad.clear();
            for(int i = 0; i<diagrama.getElementos().size();i++){

                    comboBoxEntidad.getItems().add(diagrama.getElementos().get(i).getNombre());
                
            }        
            this.controlador1.actualizarPanel();
            this.controlador1.guardarDiagrama();

        }
        else{
            mensaje("Debe seleccionar un elemento para realizar la acción");
        }
    }
    
    /**
     * Se utiliza para la ventana de modificaciones
     * @param participacion 
     */
    public void modificacionParticipacion(ArrayList<String> participacion){
        this.modificacionParticipacion=true;
        this.participacionModificada=participacion;
        
        
    }
   
    /**
     * Se elimina la relación seleccionada en la sección relación.
     */
    @FXML
    private void eliminarRelacion(){
        if(!((String)comboBoxRelacion.getValue()).isEmpty()){
            
            for(int i =0; i<diagrama.getRelaciones().size();i++){
                if(comboBoxRelacion.getValue().equals(diagrama.getRelaciones().get(i).getNombre())){
                    
                    diagrama.eliminarAgregaciones(diagrama.getRelaciones().get(i));
                    diagrama.eliminarRelacion(diagrama.getRelaciones().get(i));
                    
                }
            }
            modificarNombreRelacion.clear();
            listaEntidadesRelacion.getItems().clear();
            listaAtributosRelacion.getItems().clear();
            comboBoxRelacion.setValue("");
            comboBoxRelacion.getItems().clear();
            for(int i = 0; i<diagrama.getRelaciones().size();i++){
                comboBoxRelacion.getItems().add(diagrama.getRelaciones().get(i).getNombre());
            }
            this.controlador1.actualizarPanel();
            this.controlador1.guardarDiagrama();
        }
        else{
            mensaje("Debe seleccionar una relación para realizar la acción");
        }
    }    
    
    /**
     * Se elimina la herencia seleccionada en la sección herencia.
     */
    @FXML
    private void eliminarHerencia(){
        
        if(!((String)comboBoxHerencia.getValue()).isEmpty()){
            for(int i =0; i<diagrama.getHerencias().size();i++){
                if(comboBoxHerencia.getValue().equals(diagrama.getHerencias().get(i).getEntidadPadre().getNombre())){
                    diagrama.eliminarHerencia(diagrama.getHerencias().get(i));
                }
            }
            comboBoxHerencia.setValue("");
            comboBoxHerencia.getItems().clear();
            for(int i = 0; i<diagrama.getHerencias().size();i++){
                comboBoxHerencia.getItems().add(diagrama.getHerencias().get(i).getEntidadPadre().getNombre());
            }             
            this.controlador1.actualizarPanel();
            this.controlador1.guardarDiagrama();

        }
        else{
            mensaje("Debe seleccionar una herencia para realizar la acción");
        }
        
    }    
    private void eliminarAtributoEnDiagrama(Atributo atributo){
        for(int i = 0; i<diagrama.getAtributos().size();i++){
            if(diagrama.getAtributos().get(i).equals(atributo)){
                diagrama.getAtributos().remove(i);
                i=i-1;
            }
        }
        
    }
    
    /**
     * Valida que no se repita un nombre para la entidad.
     * @return 
     */
    private boolean validarNombreParaElemento(String nombre,Elemento elemento){
        
        for(int i=0;i<diagrama.getElementos().size();i++){
            if(elemento instanceof Entidad){
                if(diagrama.getElementos().get(i).getNombre().equals(nombre)){
                    return false;
                }                
            }
            if(elemento instanceof Agregacion){
                if(diagrama.getElementos().get(i).getNombre().equals(nombre)){
                    return false;
                }                
            }
        }
        return true;
    }    
    /**
     * Modifica la entidad seleccionada en la sección entidad. En esta se modifica el nombre
     * si es que el usuario lo pisió y tambien se eliminan atributos de esta si es que el usuario desseleccionó
     * alguno de ellos.
     */
    @FXML
    private void modificarEntidad(){
        boolean modificada=false;
        diagrama.getElementos().get(entidadSeleccionada).crearFigura();
        String nombre = (String)modificarNombreEntidad.getText();
        if(!"".equals(nombre)){ 
            if(this.validarNombreParaElemento(nombre,diagrama.getElementos().get(entidadSeleccionada))){
                diagrama.getElementos().get(entidadSeleccionada).setNombre(nombre);   
                diagrama.actualizarUnionesFiguras();
                modificada=true;                
            }
            else{
                if(diagrama.getElementos().get(entidadSeleccionada) instanceof Entidad){
                 mensaje("Nombre registrado en otro elemento.\n"
                       + "        Ingrese otro nombre        ");                   
                }
                else{
                    mensaje("Nombre registrado en otra Agregación.\n"
                          + "        Ingrese otro nombre");                    
                }

            }

        }
        listaAtributosEntidad.getItems().clear();
        comboBoxEntidad.getItems().clear();
        comboBoxEntidad.setValue("");
        modificarNombreEntidad.clear();
        for(int i = 0; i<diagrama.getElementos().size();i++){

                comboBoxEntidad.getItems().add(diagrama.getElementos().get(i).getNombre());
            
            
        }        
        this.controlador1.actualizarPanel();
        if(modificada){
            this.controlador1.guardarDiagrama();
        }

        
        
    }
    /**
     * Se valida que no se repita el nombre de la relación.
     * @return 
     */
    private boolean validarNombreParaRelacion(String nombre){
        
        for(int i=0;i<diagrama.getRelaciones().size();i++){
            if(diagrama.getRelaciones().get(i).getNombre().equals(nombre)){
                return false;
            }
        }
        return true;
    }     
    /**
     * Modifica la relación seleccionada en la sección relación. En esta se modifica el nombre
     * si es que el usuario lo pidió y tambien se eliminan atributos o entidades de esta si es que el usuario desseleccionó
     * alguno de ellos.
     */    
    @FXML
    private void modificarRelacion(){
        boolean modificar=false;
        for(int i = 0; i<diagrama.getRelaciones().get(relacionSeleccionada).getAtributos().size();i++){
            if(listaAtributosRelacion.getItems().get(i).isSelected()==false){
                diagrama.eliminarAtributo(diagrama.getRelaciones().get(relacionSeleccionada).getAtributos().get(i));
                diagrama.getRelaciones().get(relacionSeleccionada).getAtributos().remove(i);
                modificar=true;
            }
            
        } 
        for(int i = 0; i<diagrama.getRelaciones().get(relacionSeleccionada).getElementos().size();i++){
            if(listaEntidadesRelacion.getItems().get(i).isSelected()==false){
                diagrama.getRelaciones().get(relacionSeleccionada).getElementos().remove(i);
                modificar=true;
            } 
        }
        
        String nombre = (String)modificarNombreRelacion.getText();
        if(!"".equals(nombre)){
            if(validarNombreParaRelacion(nombre)){
                diagrama.getRelaciones().get(relacionSeleccionada).setNombre(nombre);
                modificar=true;                
            }
            else{
                mensaje("Nombre registrado en otra relación.\n"
                      + "       Ingrese otro nombre");
            }

        }
        if(this.modificacionParticipacion){
            diagrama.getRelaciones().get(relacionSeleccionada).setParticipacion(participacionModificada);
            modificar=true;
        }
        diagrama.getRelaciones().get(relacionSeleccionada).crearRelacion();
        diagrama.getRelaciones().get(relacionSeleccionada).crearLineasunionAtributos();
        if(diagrama.getRelaciones().get(relacionSeleccionada).getElementos().isEmpty()){
            diagrama.getRelaciones().remove(relacionSeleccionada);
        }

        
        //reiniciar la sección de relaciones con los datos actualizados
        modificarNombreRelacion.clear();
        listaEntidadesRelacion.getItems().clear();
        listaAtributosRelacion.getItems().clear();
        comboBoxRelacion.setValue("");
        comboBoxRelacion.getItems().clear();
        for(int i = 0; i<diagrama.getRelaciones().size();i++){
            comboBoxRelacion.getItems().add(diagrama.getRelaciones().get(i).getNombre());
        }
        this.controlador1.actualizarPanel();
        if(modificar){
            this.controlador1.guardarDiagrama();
        }
        
    }
    
    /**
     * Se modifica el atributo seleccionado en la sección atributo. En esta se cambia el nombre del 
     * atributo si es que el usuario lo pide.
     */
    @FXML 
    private void modificarAtributo(){
        boolean modificado=false;
        if(!modificarNombreAtributo.getText().equals("")){
            if(tipoOrigenAtributo.getValue().equals("Entidad")){
                for(int i = 0; i<diagrama.getAtributos().size();i++){
                    if(origenAtributo.getValue().equals(diagrama.getAtributos().get(i).getNombreOrigenAtributo())){
                        if(diagrama.getAtributos().get(i).getNombre().equals(comboBoxAtributo.getValue())){
                            if(validarNombreParaAtributo("Entidad")){
                                diagrama.getAtributos().get(i).setNombre(modificarNombreAtributo.getText());
                                modificado=true;
                            }

                        }
                        
                }
            }
            }
            if(tipoOrigenAtributo.getValue().equals("Relación")){
                for(int i = 0; i<diagrama.getAtributos().size();i++){
                    if(origenAtributo.getValue().equals(diagrama.getAtributos().get(i).getNombreOrigenAtributo())){
                        if(diagrama.getAtributos().get(i).getNombre().equals(comboBoxAtributo.getValue())){
                            if(validarNombreParaAtributo("Relación")){
                                diagrama.getAtributos().get(i).setNombre(modificarNombreAtributo.getText());  
                                modificado=true;
                            }
                        }
                    }
                }
            } 

            if(tipoOrigenAtributo.getValue().equals("Atributo Compuesto (Relación)")){
                for(int i = 0; i<diagrama.getAtributos().size();i++){
                    if(diagrama.getAtributos().get(i).getGuardadoEn().equals("Relación")){
                        if(diagrama.getAtributos().get(i).getTipoAtributo().equals("Compuesto")){
                            if(diagrama.getAtributos().get(i).getNombre().equals(origenAtributo.getValue())){
                                for(int j = 0; j<diagrama.getAtributos().get(i).getAtributos().size();j++){
                                    if(diagrama.getAtributos().get(i).getAtributos().get(j).getNombre().equals(comboBoxAtributo.getValue())){
                                        if(validarNombreParaAtributo("Atributo")){
                                            diagrama.getAtributos().get(i).getAtributos().get(j).setNombre(modificarNombreAtributo.getText());
                                            modificado=true;
                                        }
                                    }
                                }                                
                            }                                                        
                        }                        
                    }
                }      
            }
            if(tipoOrigenAtributo.getValue().equals("Atributo Compuesto (Entidad)")){
                for(int i = 0; i<diagrama.getAtributos().size();i++){
                    if(diagrama.getAtributos().get(i).getGuardadoEn().equals("Entidad")){
                        if(diagrama.getAtributos().get(i).getTipoAtributo().equals("Compuesto")){
                            if(diagrama.getAtributos().get(i).getNombre().equals(origenAtributo.getValue())){
                                for(int j = 0; j<diagrama.getAtributos().get(i).getAtributos().size();j++){
                                    if(diagrama.getAtributos().get(i).getAtributos().get(j).getNombre().equals(comboBoxAtributo.getValue())){
                                        if(validarNombreParaAtributo("Atributo")){
                                            diagrama.getAtributos().get(i).getAtributos().get(j).setNombre(modificarNombreAtributo.getText());
                                            modificado=true;                                            
                                        }

                                    }
                                }                                
                            }                                                        
                        }                        
                    }
                }      
            }        
        
        
        this.controlador1.verificarAtributosHerencia();
        this.controlador1.actualizarPanel();
        if(modificado){
            diagrama.actualizarUnionesFiguras();
            this.controlador1.guardarDiagrama();
        }
        tipoOrigenAtributo.setValue("");
        comboBoxAtributo.getItems().clear();
        comboBoxAtributo.setValue("");
        modificarNombreAtributo.clear();
        
    }
    
    }
    private boolean validarNombreParaAtributo(String destino){
        if(destino.equals("Entidad")){
            for(int i = 0; i<diagrama.getElementos().size();i++){
                if(diagrama.getElementos().get(i).getNombre().equals(origenAtributo.getValue())){
                    for(int j=0;j<((Entidad)diagrama.getElementos().get(i)).getAtributos().size();j++){
                        if(((Entidad)diagrama.getElementos().get(i)).getAtributos().get(j).getNombre().equals(modificarNombreAtributo.getText())){
                            mensaje("Nombre registrado anteriormente en atributos del elemento seleccionado"
                                    + "\nPor favor, intente nuevamente.");
                            return false;
                        }
                    }
                }
            }
            boolean enHerencia=false;
            for(int i = 0; i<diagrama.getHerencias().size();i++){
                for(int j = 0 ; j<diagrama.getHerencias().get(i).getEntidadesHijas().size();j++){
                    if(diagrama.getHerencias().get(i).getEntidadesHijas().get(j).getNombre().equals(origenAtributo.getValue())){
                        enHerencia=true;
                    }
                    
                }
                if(enHerencia==true){
                    for(int j = 0; j<diagrama.getHerencias().get(i).getEntidadPadre().getAtributos().size();j++){
                        if(diagrama.getHerencias().get(i).getEntidadPadre().getAtributos().get(j).getNombre().equals(modificarNombreAtributo.getText())){
                            mensaje("Hay un atributo con el nombre registrado en \nla herencia de la entidad "+
                                    diagrama.getHerencias().get(i).getEntidadPadre().getNombre());
                            return false;
                        }
                        
                    }
                }
            }
            this.controlador1.verificarAtributosHerencia();
        }
        if(destino.equals("Relación")){
            for(int i = 0; i<diagrama.getRelaciones().size();i++){
                if(diagrama.getRelaciones().get(i).getNombre().equals(comboBoxAtributo.getValue())){
                    for(int j=0;j<(diagrama.getRelaciones().get(i)).getAtributos().size();j++){
                        if(diagrama.getRelaciones().get(i).getAtributos().get(j).getNombre().equals(modificarNombreAtributo.getText())){
                            mensaje("Nombre registrado anteriormente en atributos del elemento seleccionado"
                                    + "\nPor favor, intente nuevamente.");
                            return false;
                        }
                    }
                }
            }            
        }
        if(destino.equals("Atributo")){
            for(int i = 0; i<diagrama.getAtributos().size();i++){
                if(diagrama.getAtributos().get(i).getNombre().equals(this.origenAtributo.getValue())){
                    if(!diagrama.getAtributos().get(i).getNombre().equals(modificarNombreAtributo.getText())){
                        for(int j=0;j<(diagrama.getAtributos().get(i)).getAtributos().size();j++){
                            if((diagrama.getAtributos().get(i)).getAtributos().get(j).getNombre().equals(this.modificarNombreAtributo.getText())){
                                mensaje("Nombre registrado anteriormente en atributos del elemento seleccionado"
                                        + "\nPor favor, intente nuevamente.");
                                return false;
                            }
                        }
                    }
                    else{
                        mensaje("Nombre registrado en atributo padre. "
                                + "\nPor favor, intente con otro nombre.");
                        return false;
                    }
                }
            }            
        }
        
        return true;
    }    
    public void obtenerDatosParticipacion(ArrayList<String> participacion){
        modificacionParticipacion=true;
    }
    /**
     * Se modifica la herencia seleccionada en la sección herencia. En esta se modifica el
     * tipo de herencia (si es que el usuario lo pide) y se eliminan entidades hijas(si es que
     * el usuario lo pide).
     */
    @FXML
    private void modificarHerencia(){
        boolean modificado=false;
        for(int i = 0; i<diagrama.getHerencias().get(herenciaSeleccionada).getEntidadesHijas().size();i++){
            if(listaEntidadesHijasHerencia.getItems().get(i).isSelected()==false){
                diagrama.getHerencias().get(herenciaSeleccionada).getEntidadesHijas().remove(i);
                modificado=true;
            } 
            
        }

        if(diagrama.getHerencias().get(herenciaSeleccionada).getEntidadesHijas().isEmpty()){
            diagrama.getHerencias().remove(herenciaSeleccionada);
            modificado=true;
        }
        else{
            if(!tipoHerencia.equals(diagrama.getHerencias().get(herenciaSeleccionada).getTipoHerencia())){
                diagrama.getHerencias().get(herenciaSeleccionada).setTipoHerencia(tipoHerencia);
                diagrama.getHerencias().get(herenciaSeleccionada).crearHerencia(); 
                modificado=true;
            }

        }

        
        
        
            comboBoxHerencia.setValue("");
            comboBoxHerencia.getItems().clear();
            for(int i = 0; i<diagrama.getHerencias().size();i++){
                comboBoxHerencia.getItems().add(diagrama.getHerencias().get(i).getEntidadPadre().getNombre());
            }  
        
        this.controlador1.actualizarPanel(); 
        if(modificado){
            this.controlador1.guardarDiagrama();
        }
    }
    /**
     * se guarda el tipo de herencia seleccionada por el usuario.
     */
    @FXML
    private void solapamiento(){
        disyuncion.setSelected(false);
        solapamiento.setSelected(true);
        tipoHerencia = "S";
    }
    @FXML
    private void disyuncion(){
        solapamiento.setSelected(false);
        disyuncion.setSelected(true);
        tipoHerencia = "D";
    }    
    
    
    /**
     * Elimina el atributo seleccionado
     */
    @FXML
    private void eliminarAtributo(){ 
        boolean modificado=false;
        if(tipoOrigenAtributo.getValue().equals("Entidad")){
            for(int i = 0; i< diagrama.getElementos().size();i++){
                if(diagrama.getElementos().get(i) instanceof Entidad){
                    if(diagrama.getElementos().get(i).getNombre().equals(origenAtributo.getValue())){
                        for(int j = 0; j<((Entidad)diagrama.getElementos().get(i)).getAtributos().size();j++){
                            if(((Entidad)diagrama.getElementos().get(i)).getAtributos().get(j).getNombre().equals(comboBoxAtributo.getValue())){
                                if(j!=0){
                                    this.eliminarAtributoEnDiagrama(((Entidad)diagrama.getElementos().get(i)).getAtributos().get(j));
                                    ((Entidad)diagrama.getElementos().get(i)).getAtributos().remove(j);
                                    diagrama.getElementos().get(i).crearFigura();
                                    modificado=true;
                                }
                                else{
                                    mensaje("Atributo seleccionado no puede ser eliminado");
                                }
                            }
                        }
                    }
                }
            }          
        }
        if(tipoOrigenAtributo.getValue().equals("Relación")){
            for(int i = 0; i< diagrama.getRelaciones().size();i++){
                if(diagrama.getRelaciones().get(i).getNombre().equals(origenAtributo.getValue())){
                    for(int j = 0; j<diagrama.getRelaciones().get(i).getAtributos().size();j++){
                        if(diagrama.getRelaciones().get(i).getAtributos().get(j).getNombre().equals(comboBoxAtributo.getValue())){
                            this.eliminarAtributoEnDiagrama(diagrama.getRelaciones().get(i).getAtributos().get(j));
                            diagrama.getRelaciones().get(i).getAtributos().remove(j);
                            diagrama.getRelaciones().get(i).crearLineasunionAtributos();
                            modificado=true;
                        }
                    }
                }
            }                
        }
        if(tipoOrigenAtributo.getValue().equals("Atributo Compuesto (Relación)")){
            for(int i = 0; i<diagrama.getAtributos().size();i++){
                if(diagrama.getAtributos().get(i).getGuardadoEn().equals("Relación")){
                    if(diagrama.getAtributos().get(i).getTipoAtributo().equals("Compuesto")){
                        if(diagrama.getAtributos().get(i).getNombre().equals(origenAtributo.getValue())){
                            for(int j = 0; j<diagrama.getAtributos().get(i).getAtributos().size();j++){
                                if(diagrama.getAtributos().get(i).getAtributos().get(j).getNombre().equals(comboBoxAtributo.getValue())){
                                    this.eliminarAtributoEnDiagrama(diagrama.getAtributos().get(i).getAtributos().get(j));
                                    diagrama.getAtributos().get(i).getAtributos().remove(j);
                                    diagrama.getAtributos().get(i).crearLineasunionAtributos();
                                    modificado=true;
                                }
                            }                                
                        }                                                        
                    }                        
                }
            }      
        }
        if(tipoOrigenAtributo.getValue().equals("Atributo Compuesto (Entidad)")){
            for(int i = 0; i<diagrama.getAtributos().size();i++){
                if(diagrama.getAtributos().get(i).getGuardadoEn().equals("Entidad")){
                    if(diagrama.getAtributos().get(i).getTipoAtributo().equals("Compuesto")){
                        if(diagrama.getAtributos().get(i).getNombre().equals(origenAtributo.getValue())){
                            for(int j = 0; j<diagrama.getAtributos().get(i).getAtributos().size();j++){
                                if(diagrama.getAtributos().get(i).getAtributos().get(j).getNombre().equals(comboBoxAtributo.getValue())){
                                    this.eliminarAtributoEnDiagrama(diagrama.getAtributos().get(i).getAtributos().get(j));
                                    diagrama.getAtributos().get(i).getAtributos().remove(j);
                                    diagrama.getAtributos().get(i).crearLineasunionAtributos();
                                    modificado=true;
                                }
                            }                                
                        }                                                        
                    }                        
                }
            }      
        }             
        
        this.controlador1.actualizarPanel();
        tipoOrigenAtributo.setValue("");
        comboBoxAtributo.getItems().clear();
        comboBoxAtributo.setValue("");
        modificarNombreAtributo.clear(); 
        if(modificado){
            diagrama.actualizarUnionesFiguras();
            this.controlador1.guardarDiagrama();
        }
    }
    @FXML
    private void ventanaParticipacion() throws IOException{
        if(comboBoxRelacion.getValue()!=null){
            Stage stage = new Stage();
            stage.setTitle("Participación");
            stage.setResizable(false);
            FXMLLoader loader = new FXMLLoader();
            AnchorPane root1 = (AnchorPane)loader.load(getClass().getResource("ParticipacionRelacion.fxml").openStream());
            ParticipacionRelacionController instanciaControlador = (ParticipacionRelacionController)loader.getController();
            instanciaControlador.recibirParametrosModificacion(controlador,(String)comboBoxRelacion.getValue(), diagrama);
            Scene scene = new Scene(root1);
            stage.setScene(scene);
            stage.alwaysOnTopProperty();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        }
        else{
            mensaje("Debe seleccionar una relación.");
        }
    }     
    
    
    
    
    
    
    
    
        
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tipoOrigenAtributo.getItems().addAll("Entidad","Relación","Atributo Compuesto (Entidad)","Atributo Compuesto (Relación)");
        controlador=this;
    }    
    
}
