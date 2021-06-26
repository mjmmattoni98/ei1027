package com.ams.ei1027espaciosnaturales.model;

import java.time.LocalTime;

public class FranjaHoraria {
    private LocalTime inicio;
    private LocalTime fin;
    private String espacioPublico;

    public LocalTime getInicio() {
        return inicio;
    }

    public void setInicio(LocalTime inicio) {
        this.inicio = inicio;
    }

    public LocalTime getFin() {
        return fin;
    }

    public void setFin(LocalTime fin) {
        this.fin = fin;
    }

    public String getEspacioPublico() {
        return espacioPublico;
    }

    public void setEspacioPublico(String espacioPublico) {
        this.espacioPublico = espacioPublico;
    }

    @Override
    public String toString() {
        return "FranjaHoraria{" +
                "inicio=" + inicio +
                ", fin=" + fin +
                ", espacioPublico='" + espacioPublico + '\'' +
                '}';
    }
}
