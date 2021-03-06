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
public class Diagrama implements Cloneable {
    private ArrayList<Elemento> elementos;

    private ArrayList<Relacion> relaciones;
    private ArrayList<Atributo> atributos;

    private ArrayList<Herencia> herencias; 
    public Diagrama() {
        
        relaciones = new ArrayList<>();
        atributos = new ArrayList<>();
        herencias = new ArrayList<>();
        elementos = new ArrayList<>();
        
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
    

    public ArrayList<Herencia> getHerencias() {
        return herencias;
    }
    public  ArrayList<Relacion> getRelaciones() {
        return relaciones;
    }
    public Relacion obtenerRelacion(int i){
        return relaciones.get(i);
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
    public void eliminarElemento(Elemento elemento){
            for(int i = 0; i<this.relaciones.size();i++){
                if(this.relaciones.get(i).getElementos().size()==1 && this.relaciones.get(i).getElementos().get(0).equals(elemento)){
                    this.eliminarAgregaciones(relaciones.get(i));
                    this.relaciones.remove(i);
                    i=0;

                    
                }
                else{
                    if(this.relaciones.get(i).isRelacionDebil()){
                        if(this.relaciones.get(i).getElementos().get(1).equals((Entidad)elemento)){
                            Entidad entidad = (Entidad)this.relaciones.get(i).getElementos().get(0);
                            this.eliminarAgregaciones(relaciones.get(i));
                            this.relaciones.remove(i);
                            eliminarElemento(entidad);
                            
                        }
                        else{
                            this.eliminarAgregaciones(relaciones.get(i));
                            this.relaciones.remove(i);
                        }
                        
                        i=0;
                    }
                    else{
                        boolean eliminar=false;
                        for(int j = 0; j<this.getRelaciones().get(i).getElementos().size();j++){
                            if(this.relaciones.get(i).getElementos().get(j).equals(elemento)){
                                eliminar=true;
                            }
                        }
                        if(eliminar){
                            this.eliminarAgregaciones(relaciones.get(i));
                            this.relaciones.remove(i);
                        }
                    }                    

                }
            }        
        
        
        if(elemento instanceof Entidad){
            Entidad entidad=(Entidad)elemento;
            eliminarArregloDeAtributos(entidad.getAtributos());
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
            for(int i = 0; i<this.elementos.size();i++){
                if(elemento.equals(this.elementos.get(i))){
                    this.elementos.remove(i);
                    i=0;
                }            
            }        

        
        
    }
    public void eliminarRelacion(Relacion relacion){
        for(int i = 0; i< this.relaciones.size();i++){
            if(this.relaciones.get(i).equals(relacion)){
                for(int j = 0 ; j<this.relaciones.get(i).getAtributos().size();j++){
                    this.eliminarAtributo(this.relaciones.get(i).getAtributos().get(j));
                }
                this.relaciones.remove(i);
                i=i-1;
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
    public void eliminarAgregaciones(Relacion relacion){
        for(int i = 0; i< this.elementos.size();i++){
            if(elementos.get(i) instanceof Agregacion){
                if(((Agregacion)elementos.get(i)).getRelacion().equals(relacion)){
                    eliminarAgregacionEnRelacion(((Agregacion)elementos.get(i)));
                    elementos.remove(i);
                    i=i-1;
                }
            }
        }   
    }     
    public void eliminarAgregacionEnRelacion(Agregacion agregacion){
        for(int i = 0; i<relaciones.size();i++){
            boolean encontrado=false;
            for(int j = 0; j<relaciones.get(i).getElementos().size();j++){                
                if(relaciones.get(i).getElementos().get(j).equals(agregacion)){
                    encontrado=true;                     
                }
            }
            if(encontrado){
                eliminarAgregaciones(relaciones.get(i));
                relaciones.remove(i);
                i=i-1;
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
        for(int i = 0; i< this.elementos.size();i++){
            this.elementos.get(i).crearFigura();
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
                
                for(int j = 0 ; j<this.getAtributos().get(i).getAtributos().size();j++){
                    this.eliminarAtributo(this.getAtributos().get(i).getAtributos().get(j));
                }
                this.atributos.remove(i);
                i=i-1;
            }
        } 
    }

    public void setElementos(ArrayList<Elemento> elementos) {
        this.elementos = elementos;
    }
    
    public ArrayList<Elemento> getElementos() {
        return elementos;
    }
    
    public int cantidadElementosDiagrama(){
        return (this.getAtributos().size()+this.getElementos().size()+this.getHerencias().size()+this.getRelaciones().size());
    }
}
