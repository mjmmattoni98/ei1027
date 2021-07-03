package com.ams.ei1027espaciosnaturales.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reserva {
    private int numReserva;
    @DateTimeFormat(pattern = "HH:mm:ss.SSS")
    private LocalTime horaAcceso;
    @DateTimeFormat(pattern = "HH:mm:ss.SSS")
    private LocalTime horaSalida;
    //    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDate fechaAcceso;
    //    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDate fechaCreacion;
    private int numPersonas;
    private EstadoReserva estado;
    private String dni;
    @DateTimeFormat(pattern = "HH:mm:ss.SSS")
    private LocalTime inicioFranjaHoraria;
    @DateTimeFormat(pattern = "HH:mm:ss.SSS")
    private LocalTime finFranjaHoraria;
    private String espacioPublico;
    private int zona;

    public int getNumReserva() {
        return numReserva;
    }

    public void setNumReserva(int numReserva) {
        this.numReserva = numReserva;
    }

    public LocalTime getHoraAcceso() {
        return horaAcceso;
    }

    public void setHoraAcceso(LocalTime horaAcceso) {
        this.horaAcceso = horaAcceso;
    }

    public LocalTime getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(LocalTime horaSalida) {
        this.horaSalida = horaSalida;
    }

    public LocalDate getFechaAcceso() {
        return fechaAcceso;
    }

    public void setFechaAcceso(LocalDate fechaAcceso) {
        this.fechaAcceso = fechaAcceso;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public int getNumPersonas() {
        return numPersonas;
    }

    public void setNumPersonas(int numPersonas) {
        this.numPersonas = numPersonas;
    }

    public EstadoReserva getEstado() {
        return estado;
    }

    public void setEstado(EstadoReserva estado) {
        System.out.println(estado);
        this.estado = estado;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public LocalTime getInicioFranjaHoraria() {
        return inicioFranjaHoraria;
    }

    public void setInicioFranjaHoraria(LocalTime inicioFranjaHoraria) {
        this.inicioFranjaHoraria = inicioFranjaHoraria;
    }

    public LocalTime getFinFranjaHoraria() {
        return finFranjaHoraria;
    }

    public void setFinFranjaHoraria(LocalTime finFranjaHoraria) {
        this.finFranjaHoraria = finFranjaHoraria;
    }

    public String getEspacioPublico() {
        return espacioPublico;
    }

    public void setEspacioPublico(String espacioPublico) {
        this.espacioPublico = espacioPublico;
    }

    public int getZona() {
        return zona;
    }

    public void setZona(int zona) {
        this.zona = zona;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "numReserva=" + numReserva +
                ", horaAcceso=" + horaAcceso +
                ", horaSalida=" + horaSalida +
                ", fechaAcceso=" + fechaAcceso +
                ", fechaCreacion=" + fechaCreacion +
                ", numPersonas=" + numPersonas +
                ", estado=" + estado +
                ", dni='" + dni + '\'' +
                ", inicioFranjaHoraria=" + inicioFranjaHoraria +
                ", finFranjaHoraria=" + finFranjaHoraria +
                ", espacioPublico='" + espacioPublico + '\'' +
                ", zona=" + zona +
                '}';
    }
}
