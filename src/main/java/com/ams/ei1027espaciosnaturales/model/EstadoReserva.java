package com.ams.ei1027espaciosnaturales.model;

import org.jetbrains.annotations.NotNull;

public enum EstadoReserva {
    PENDIENTE_USO("pendiente de uso", "pendienteUso"),
    EN_USO("en uso", "enUso"),
    FIN_USO("fin de uso", "finUso"),
    CANCELADA_CIUDADANO("cancelada por ciudadano", "canceladaCiudadano"),
    CANCELADA_CONTROLADOR("cancelada por controlador", "canceladaControlador"),
    CANCELADA_GESTOR_MUNICIPAL("cancelada por gestor municipal", "canceladaGestorMunicipal");

    private final String value;
    private final String id;

    EstadoReserva(String value, String id){
        this.value = value;
        this.id = id;
    }

    public String getValue(){
        return this.value;
    }

    public String getId(){
        return this.id;
    }

    public static EstadoReserva stringToTipo(@NotNull String id){
        EstadoReserva estadoReserva;
        switch (id){
            case "pendienteUso":
                estadoReserva = PENDIENTE_USO;
                break;
            case "enUso":
                estadoReserva = EN_USO;
                break;
            case "finUso":
                estadoReserva = FIN_USO;
                break;
            case "canceladaCiudadano":
                estadoReserva = CANCELADA_CIUDADANO;
                break;
            case "canceladaControlador":
                estadoReserva = CANCELADA_CONTROLADOR;
                break;
            case "canceladaGestorMunicipal":
                estadoReserva = CANCELADA_GESTOR_MUNICIPAL;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + id);
        }

        return estadoReserva;
    }
}
