/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.util.ArrayList;

/**
 * Clae RespaldoDiagrama
 * Se utiliza para la función "undo" y "redo".
 * En esta se crean copias de los diagramas que son recibidos como parametros
 * 
 * @author Cristobal Muñoz Salinas
 */
public class RespaldoDiagrama {
    ArrayList<Diagrama> diagramas = new ArrayList<>();

    
    /**
     * Se agrega un diagrama al arrayList de diagramas
     * @param diagrama 
     */
    public void agregarDiagrama(Diagrama diagrama){
        
        if(diagramas.size()==10){
            diagramas.remove(0);
        }
        Diagrama a = crearCopiaDiagrama(diagrama);
        diagramas.add(a);
        
    }
    
    
    /**
     * Se crea la copia del diagrama recibido como parametro
     * @param diagrama
     * @return Copia del diagrama.
     */
    public Diagrama crearCopiaDiagrama(Diagrama diagrama){
        ArrayList<Atributo> copiaAtributos = new ArrayList<>();
        ArrayList<Entidad> copiaEntidades = new ArrayList<>();
        ArrayList<Relacion> copiaRelaciones = new ArrayList<>();
        ArrayList<Herencia> copiaHerencias = new ArrayList<>();
        
        for(int i =0; i< diagrama.getAtributos().size();i++){
            copiaAtributos.add(copiaAtributo(diagrama.getAtributos().get(i)));
        }
        for(int i = 0;i<diagrama.getEntidades().size();i++){
            copiaEntidades.add(copiarEntidad(diagrama.getEntidades().get(i),copiaAtributos));
        }
        for(int i = 0; i<diagrama.getRelaciones().size();i++){
            copiaRelaciones.add(copiaRelacion(diagrama.getRelaciones().get(i),copiaAtributos,copiaEntidades));
        }
        for(int i = 0; i< diagrama.getHerencias().size();i++){
            copiaHerencias.add(copiaHerencia(diagrama.getHerencias().get(i),copiaEntidades));
        }
        
        Diagrama agregar = new Diagrama();
        agregar.setAtributos(copiaAtributos);
        agregar.setEntidades(copiaEntidades);
        agregar.setHerencias(copiaHerencias);
        agregar.setRelaciones(copiaRelaciones);
        
        
        
        if(diagramas.size()==10){
            diagramas.remove(0);
        }
        return agregar;
    }
    private Herencia copiaHerencia(Herencia herencia,ArrayList<Entidad> entidadesDiagrama){
        Entidad padre = null;
        ArrayList<Entidad> hijas = new ArrayList<>();
        String tipo = herencia.getTipoHerencia();
        int x = herencia.getPosX();
        int y = herencia.getPosY();
        for(int i = 0; i<entidadesDiagrama.size();i++){
            if(entidadesDiagrama.get(i).getNombre().equals(herencia.getEntidadPadre().getNombre())){
                padre = entidadesDiagrama.get(i);
            }
        }
        
        for(int i = 0; i<herencia.getEntidadesHijas().size();i++){
            for(int j = 0; j<entidadesDiagrama.size();j++){
                if(herencia.getEntidadesHijas().get(i).getNombre().equals(entidadesDiagrama.get(j).getNombre())){
                    hijas.add(entidadesDiagrama.get(j));
                }
            }
        }
        Herencia copia = new Herencia(padre,hijas,tipo,x,y);
        copia.crearHerencia();
        return copia;
            
        
        
        
        
        
    }
    private Relacion copiaRelacion(Relacion relacion, ArrayList<Atributo> atributosDiagrama,ArrayList<Entidad> entidadesDiagrama){
        boolean debil = relacion.isRelacionDebil();
        int x = relacion.getPosX();
        int y = relacion.getPosY();
        String nombre = relacion.getNombre();

        Relacion copia = new Relacion(debil,x,y,nombre);
        
        for(int i = 0; i<relacion.getEntidad().size();i++){
            for(int j = 0; j<entidadesDiagrama.size();j++){
                if(relacion.getEntidad().get(i).getNombre().equals(entidadesDiagrama.get(j).getNombre())){
                    copia.getEntidad().add(entidadesDiagrama.get(j));
                }
            }
        }
        for(int i = 0; i<relacion.getAtributos().size();i++){
            for(int j = 0; j<atributosDiagrama.size();j++){
                if(relacion.getAtributos().get(i).getNombre().equals(atributosDiagrama.get(j).getNombre())){
                    copia.getAtributos().add(atributosDiagrama.get(j));
                }
            }
        }        
        copia.crearRelacion();
        copia.crearLineasunionAtributos();
        return copia;        
    }
    private Atributo copiaAtributo(Atributo atributo){        
        String tipo = atributo.getTipoAtributo();
        int x = atributo.getPosX();
        int y = atributo.getPosY();
        String nombre = atributo.getNombre();
        String origen = atributo.getGuardadoEn();

        Atributo copia = new Atributo(tipo,x,y,nombre,origen);
        for(int i = 0; i<atributo.getAtributos().size();i++){
            copia.getAtributos().add(copiaAtributo(atributo.getAtributos().get(i)));
        }
        copia.crearLineasunionAtributos();
        return copia;   
    }
    
    private Entidad copiarEntidad(Entidad entidad,ArrayList<Atributo> atributosDiagrama){
        boolean debil = entidad.isDebil();
        int x = entidad.getPosX();
        int y = entidad.getPosY();
        String nombre = entidad.getNombre();

        Entidad copia = new Entidad(debil,x,y,nombre);
        
        
        for(int i = 0; i<entidad.getAtributos().size();i++){
            for(int j = 0; j<atributosDiagrama.size();j++){
                if(atributosDiagrama.get(j).getNombre().equals(entidad.getAtributos().get(i).getNombre())){
                    copia.getAtributos().add(atributosDiagrama.get(j));
                }
            }
        }
        copia.crearLineasunionAtributos();
        return copia;
        
        
        
    }

    public int tamano(){
        return diagramas.size();
    }
    
    /**
     * Retorna el diagrama contenido en el indice "i".
     * @param i
     * @return copia del diagrama contenido en la posición "i" del arrayList diagramas
     */
    public Diagrama retornarDiagrama(int i){

        return crearCopiaDiagrama(diagramas.get(i));
    }
    
    /**
     * Remueve todos los diagramas que esten despues de la posición "indice" en el arreglo.
     * @param indice 
     */
    public void removerPosteriores(int indice){
        for(int i=diagramas.size()-1;indice<i;i--){
            diagramas.remove(i);
        }
        
        
    }
    
}
