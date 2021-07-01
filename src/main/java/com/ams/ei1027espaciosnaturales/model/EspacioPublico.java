package com.ams.ei1027espaciosnaturales.model;

public class EspacioPublico {
    private String nombre;
    private String localizacionGeografica;
    private TipoEspacio tEspacio;
    private TipoSuelo tSuelo;
    private TipoAcceso tAcceso;
    private String descripcion;
    private int longitud;
    private int anchura;
    private String orientacion;
    private int idMunicipio;

    public String getNombre() {
    	return this.nombre;
    }
    
    public String getLocalizacionGeografica() {
    	return this.localizacionGeografica;
    }
    
    public String getTEspacio() {
        if (this.tEspacio == null)
            return "";
    	return this.tEspacio.getValue();
    }

    public String getTSuelo() {
        if (this.tSuelo == null)
            return "";
    	return this.tSuelo.getValue();
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
        if (this.tAcceso == null)
            return "";
    	return this.tAcceso.getValue();
    }
    
    public void setNombre(String nombre) {
    	this.nombre = nombre;
    }
    
    public void setLocalicacionGeografica(String localizacionGeografica) {
    	this.localizacionGeografica = localizacionGeografica;
    }
    
    public void setTEspacio(String tEspacio) {
    	this.tEspacio = TipoEspacio.stringToTipo(tEspacio);
    }

    public void setTSuelo(String tSuelo) {
    	this.tSuelo = TipoSuelo.stringToTipo(tSuelo);;
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
    	this.tAcceso = TipoAcceso.stringToTipo(tAcceso);
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
