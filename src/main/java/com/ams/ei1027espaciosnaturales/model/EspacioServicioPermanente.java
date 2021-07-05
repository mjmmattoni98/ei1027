package com.ams.ei1027espaciosnaturales.model;

public class EspacioServicioPermanente{
    private String espacioPublico;
    private String tipo;
    private String descripcion;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEspacioPublico() {
        return espacioPublico;
    }

    public void setEspacioPublico(String espacioPublico) {
        this.espacioPublico = espacioPublico;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "EspacioServicioPermanente{" +
                "espacioPublico='" + espacioPublico + '\'' +
                ", tipo='" + tipo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
