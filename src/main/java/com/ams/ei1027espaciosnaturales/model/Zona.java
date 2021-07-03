package com.ams.ei1027espaciosnaturales.model;

public class Zona {
    private int id;
    private int capacidad;
    private int ocupacion;
    private String nombreEspacioPublico;
    private int porcentajeOcupado;

    public int getPorcentajeOcupado() {
        return porcentajeOcupado;
    }

    public void setPorcentajeOcupado(int porcentajeOcupado) {
        this.porcentajeOcupado = porcentajeOcupado;
    }

    public void calcularPorcentajeOcupacion(){
        this.setPorcentajeOcupado(ocupacion/capacidad*100);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public int getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(int ocupacion) {
        this.ocupacion = ocupacion;
    }

    public String getNombreEspacioPublico() {
        return nombreEspacioPublico;
    }

    public void setNombreEspacioPublico(String nombreEspacioPublico) {
        this.nombreEspacioPublico = nombreEspacioPublico;
    }

    @Override
    public String toString() {
        return "Zona{" +
                "id=" + id +
                ", capacidad=" + capacidad +
                ", ocupacion=" + ocupacion +
                ", nombreEspacioPublico=" + nombreEspacioPublico +
                '}';
    }
}
