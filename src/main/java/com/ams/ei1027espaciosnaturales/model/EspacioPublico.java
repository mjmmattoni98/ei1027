package com.ams.ei1027espaciosnaturales.model;

public class EspacioPublico {
    private String nombre;
    private String localizacionGeografica;
    private String tEspacio;
    private String tSuelo;
    private String tAcceso;
    private String descripcion;
    private int longitud;
    private int anchura;
    private String orientacion;
    private int idMunicipio;

    public EspacioPublico(){
    	
    }
    
    public String getNombre() {
    	return this.nombre;
    }
    
    public String getLocalicacionGeografica() {
    	return this.localizacionGeografica;
    }
    
    public String getTEspacio() {
    	return this.tEspacio;
    }

    public String getTSuelo() {
    	return this.tSuelo;
    }
    
    public String getDescripcion() {
    	return this.descripcion;
    }

    public int getLongitud() {
    	return this.longitud;
    }
    
    public int getAnchura() {
    	return this.anchura;
    }
    
    public String getOrientacion() {
    	return this.orientacion;
    }

    public int getIdMunicipio() {
    	return this.idMunicipio;
    }

    public String getTAcceso() {
    	return this.tAcceso;
    }
    
    public void setNombre(String nombre) {
    	this.nombre = nombre;
    }
    
    public void setLocalicacionGeografica(String localizacionGeografica) {
    	this.localizacionGeografica = localizacionGeografica;
    }
    
    public void setTEspacio(String tEspacio) {
    	this.tEspacio = tEspacio;
    }

    public void setTSuelo(String tSuelo) {
    	this.tSuelo = tSuelo;;
    }
    
    public void setDescripcion(String descripcion) {
    	this.descripcion = descripcion;
    }

    public void setLongitud(int longitud) {
    	this.longitud = longitud;
    }
    
    public void setAnchura(int anchura) {
    	this.anchura = anchura;
    }
    
    public void setOrientacion(String orientacion) {
    	this.orientacion = orientacion;
    }

    public void setIdMunicipio(int idMunicipio) {
    	this.idMunicipio = idMunicipio;
    }

    public void setTAcceso(String tAcceso) {
    	this.tAcceso = tAcceso;
    }

    @Override
    public String toString() {
        return "EspacioPublico{" +
                "nombre='" + nombre + '\'' +
                ", localizacionGeografica='" + localizacionGeografica + '\'' +
                ", tEspacio='" + tEspacio + '\'' +
                ", tSuelo='" + tSuelo + '\'' +
                ", tAcceso='" + tAcceso + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", longitud=" + longitud +
                ", anchura=" + anchura +
                ", orientacion='" + orientacion + '\'' +
                ", idMunicipio=" + idMunicipio +
                '}';
    }
}
