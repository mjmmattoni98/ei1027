package com.ams.ei1027espaciosnaturales.model;

public class Imagen {
    private int id;
    private String archivo;
    private String espacioPublico;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public String getEspacioPublico() {
        return espacioPublico;
    }

    public void setEspacioPublico(String espacioPublico) {
        this.espacioPublico = espacioPublico;
    }

    @Override
    public String toString() {
        return "Imagen{" +
                "id=" + id +
                ", archivo='" + archivo + '\'' +
                ", espacioPublico='" + espacioPublico + '\'' +
                '}';
    }
}
