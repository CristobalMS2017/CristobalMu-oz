/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.util.ArrayList;

/**
 * Clase diagrama
 * @author Cristobal Muñoz Salinas
 */
public class Diagrama {
    private ArrayList<Entidad> entidades;
    private ArrayList<Relacion> relaciones;
    private ArrayList<Atributo> atributos;
    private ArrayList<Agregacion> agregaciones;
    private ArrayList<Herencia> herencias; 
    public Diagrama() {
        
        entidades = new ArrayList<>();
        relaciones = new ArrayList<>();
        atributos = new ArrayList<>();
        herencias = new ArrayList<>();
        agregaciones = new ArrayList<>();
        
    }   

    public void setEntidades(ArrayList<Entidad> entidades) {
        this.entidades = entidades;
    }

    public void setRelaciones(ArrayList<Relacion> relaciones) {
        this.relaciones = relaciones;
    }

    public void setAtributos(ArrayList<Atributo> atributos) {
        this.atributos = atributos;
    }

    public void setHerencias(ArrayList<Herencia> herencias) {
        this.herencias = herencias;
    }

    public ArrayList<Agregacion> getAgregaciones() {
        return agregaciones;
    }

    public void setAgregaciones(ArrayList<Agregacion> agregaciones) {
        this.agregaciones = agregaciones;
    }
    
    
    
    public void agregarEntidades(ArrayList<Entidad> a){
        entidades =a;
    }
    public void agregarHerencias(ArrayList<Herencia> a){
        herencias =a;
    }   
    public void agregarRelaciones(ArrayList<Relacion> a){
        relaciones =a;
    }  
    public void agregarAtributos(ArrayList<Atributo> a){
        atributos =a;
    }          
    

    public ArrayList<Herencia> getHerencias() {
        return herencias;
    }


    
    public  ArrayList<Entidad> getEntidades() {
        return entidades;
    }

    public  ArrayList<Relacion> getRelaciones() {
        return relaciones;
    }
    public void agregarEntidad(Entidad nuevo){
        this.getEntidades().add(nuevo);
    }
    
    public Entidad obtenerEntidad(int i){
        return (Entidad)entidades.get(i);
    }
    public Relacion obtenerRelacion(int i){
        return relaciones.get(i);
    }
    public int cantidadEntidad(){
        return this.entidades.size();
    }
    public int cantidadRelacion(){
        return this.getRelaciones().size();
    }
    public void agregarRelacion(Relacion nuevo){
        this.getRelaciones().add(nuevo);
    }
    
    
    public void eliminarRelacion(int i){
        this.relaciones.remove(i);
    }

    public ArrayList<Atributo> getAtributos() {
        return atributos;
    }
    public void eliminarAtributo(String origen,Atributo atributo){   
    }
    
    
    public void eliminarEntidad(Entidad entidad){
        eliminarArregloDeAtributos(entidad.getAtributos());
        for(int i = 0; i<this.getEntidades().size();i++){
            if(entidad.equals(this.entidades.get(i))){
                this.entidades.remove(i);
            }            
        }
        for(int i = 0; i<this.relaciones.size();i++){
            if(this.relaciones.get(i).getEntidad().size()==1 && this.relaciones.get(i).getEntidad().get(0).equals(entidad)){
                this.relaciones.remove(i);
            }
            else{
                for(int j = 0; j<this.getRelaciones().get(i).getEntidad().size();j++){
                    if(this.relaciones.get(i).getEntidad().get(j).equals(entidad)){
                        this.relaciones.get(i).eliminarEntidad(j);
                    }
                }
            }
        }
        
        for(int i = 0; i<this.herencias.size();i++){
            if(this.herencias.get(i).getEntidadPadre().equals(entidad)){
                this.herencias.remove(i);
                i=0;
            }            
        }


        for(int i = 0; i<this.herencias.size();i++){
            if(this.herencias.get(i).getEntidadesHijas().size()==1 &this.herencias.get(i).getEntidadesHijas().get(0).equals(entidad)){
                this.herencias.remove(i);
                i=0;
            }  
        }    
        for(int i = 0; i<this.herencias.size();i++){
            for(int j =0; j<this.herencias.get(i).getEntidadesHijas().size();j++){
                if(this.herencias.get(i).getEntidadesHijas().get(j).equals(entidad)){
                    this.herencias.get(i).eliminarEntidadHija(i);
                    this.herencias.get(i).crearHerencia();
                }
            }            
        }    
    }
    
    public void modificarNombreEntidad(){
        
    }
    
    
    public void eliminarRelacion(Relacion relacion){
        for(int i = 0; i< this.relaciones.size();i++){
            if(this.relaciones.get(i).equals(relacion)){
                this.relaciones.remove(i);
            }
        } 
    }
    public void eliminarHerencia(Herencia herencia){
        for(int i = 0; i< this.herencias.size();i++){
            if(this.herencias.get(i).equals(herencia)){
                this.herencias.remove(i);
            }
        }         
    }
    
    
    private void eliminarArregloDeAtributos(ArrayList<Atributo> atributos){
        for(int i =0; i<atributos.size();i++){
            for(int j = 0; j<atributos.get(i).getAtributos().size();j++){
                eliminarAtributo(atributos.get(i).getAtributos().get(j));
            }
        }
        for(int i = 0; i<atributos.size();i++){
            eliminarAtributo(atributos.get(i));
        }
    }
    
    public void actualizarUnionesFiguras(){
        for(int i = 0; i< this.entidades.size();i++){
            this.entidades.get(i).crearLineasunionAtributos();
        }
        for(int i = 0; i<this.relaciones.size();i++){
            this.relaciones.get(i).crearRelacion();
            this.relaciones.get(i).crearLineasunionAtributos();
            
        }
        for(int i = 0; i<this.herencias.size();i++){
            this.herencias.get(i).crearHerencia();
        }
        for(int i = 0; i<this.atributos.size();i++){
            this.atributos.get(i).crearLineasunionAtributos();
        }
    }
    
    public void eliminarAtributo(Atributo atributo){
        for(int i =0; i< this.getAtributos().size();i++){
            if(this.getAtributos().get(i).equals(atributo)){
                this.atributos.remove(i);
            }
        }
        
        
        
    }
    
    
    
}
