package com.ams.ei1027espaciosnaturales.model;

public class Servicio {
    private String tipo;
    private String descripcion;
    private boolean isEstacional;

    public Servicio(){
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean getIsEstacional(){
        return this.isEstacional;
    }

    public void setEstacional(boolean isEstacional){
        this.isEstacional = isEstacional;
    }

    @Override
    public String toString() {
        return "Servicio{" +
                "tipo='" + tipo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}