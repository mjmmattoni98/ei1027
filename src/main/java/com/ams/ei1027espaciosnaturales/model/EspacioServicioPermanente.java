package com.ams.ei1027espaciosnaturales.model;

public class EspacioServicioPermanente {
    private String nombre;
    private String tipo;

    public EspacioServicioPermanente() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
                "nombre='" + nombre + '\'' +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
