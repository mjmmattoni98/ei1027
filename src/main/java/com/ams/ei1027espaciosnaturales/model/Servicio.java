package com.ams.ei1027espaciosnaturales.model;

public class Servicio {
    private String tipo;
    private String descripcion;
    private String supertipo = "Permanente";

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

    public String getSupertipo(){
        return this.supertipo;
    }

    public void setSupertipo(String supertipo){
        this.supertipo = supertipo;
    }

    @Override
    public String toString() {
        return "Servicio{" +
                "tipo='" + tipo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", supertipo='" + supertipo + '\'' +
                '}';
    }
}