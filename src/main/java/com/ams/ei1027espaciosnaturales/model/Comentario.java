package com.ams.ei1027espaciosnaturales.model;

public class Comentario {
    private int id;
    private String descripcion;
    private String espacioPublico;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    @Override
    public String toString() {
        return "Comentario{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", espacioPublico='" + espacioPublico + '\'' +
                '}';
    }
}
