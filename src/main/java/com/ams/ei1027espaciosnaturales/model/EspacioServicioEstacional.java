package com.ams.ei1027espaciosnaturales.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public class EspacioServicioEstacional {
    private String nombre;
    private String tipo;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate fechaIni;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate fechaFin;
    @DateTimeFormat(pattern = "HH:mm:ss.SSS")
    private LocalTime horaIni;
    @DateTimeFormat(pattern = "HH:mm:ss.SSS")
    private LocalTime horaFin;
    private String lugarContratacion;

    public EspacioServicioEstacional(){}

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

    public LocalDate getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(LocalDate fechaIni) {
        this.fechaIni = fechaIni;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public LocalTime getHoraIni() {
        return horaIni;
    }

    public void setHoraIni(LocalTime horaIni) {
        this.horaIni = horaIni;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public String getLugarContratacion() {
        return lugarContratacion;
    }

    public void setLugarContratacion(String lugarContratacion) {
        this.lugarContratacion = lugarContratacion;
    }

    @Override
    public String toString() {
        return "EspacioServicioEstacional{" +
                "nombre='" + nombre + '\'' +
                ", tipo='" + tipo + '\'' +
                ", fechaIni=" + fechaIni +
                ", fechaFin=" + fechaFin +
                ", horaIni=" + horaIni +
                ", horaFin=" + horaFin +
                ", lugarContratacion='" + lugarContratacion + '\'' +
                '}';
    }
}
