package com.ams.ei1027espaciosnaturales.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reserva {
    private int numReserva;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime horaAcceso;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime horaSalida;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaAcceso;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaCreacion;
    private int numPersonas;
    private EstadoReserva estado;
    private String dni;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime inicioFranjaHoraria;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime finFranjaHoraria;
    private String franjaHoraria;
    private String espacioPublico;
    private int zona;

    public int getNumReserva() {
        return numReserva;
    }

    public void setFranjaHoraria(String franjaHoraria){
        this.franjaHoraria = franjaHoraria;
        String[] franja = franjaHoraria.split("-");
        this.inicioFranjaHoraria = LocalTime.parse(franja[0]);
        this.finFranjaHoraria = LocalTime.parse(franja[1]);
    }

    public String getFranjaHoraria(){
        return franjaHoraria;
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

    public String getEstado() {
        return estado.getId();
    }

    public void setEstado(String estado) {
        this.estado = EstadoReserva.stringToTipo(estado);
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
