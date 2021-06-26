package com.ams.ei1027espaciosnaturales.model;

public class ZonaReserva {
    private int zona;
    private int reserva;

    public int getZona() {
        return zona;
    }

    public void setZona(int zona) {
        this.zona = zona;
    }

    public int getReserva() {
        return reserva;
    }

    public void setReserva(int reserva) {
        this.reserva = reserva;
    }

    @Override
    public String toString() {
        return "ZonaReserva{" +
                "zona=" + zona +
                ", reserva=" + reserva +
                '}';
    }
}
