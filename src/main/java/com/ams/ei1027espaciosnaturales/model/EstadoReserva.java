package com.ams.ei1027espaciosnaturales.model;

public enum EstadoReserva {
    PENDIENTE_USO("pendiente de uso", "PU"),
    EN_USO("en uso", "EU"),
    FIN_USO("fin de uso", "FU"),
    CANCELADA_CIUDADANO("cancelada por ciudadano", "CCI"),
    CANCELADA_CONTROLADOR("cancelada por controlador", "CCO"),
    CANCELADA_GESTOR_MUNICIPAL("cancelada por gestor municipal", "CGM");

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

    public static EstadoReserva stringToTipo(@org.jetbrains.annotations.NotNull String id){
        EstadoReserva estadoReserva;
        switch (id){
            case "PU":
                estadoReserva = PENDIENTE_USO;
                break;
            case "EU":
                estadoReserva = EN_USO;
                break;
            case "FU":
                estadoReserva = FIN_USO;
                break;
            case "CCI":
                estadoReserva = CANCELADA_CIUDADANO;
                break;
            case "CCO":
                estadoReserva = CANCELADA_CONTROLADOR;
                break;
            case "CGM":
                estadoReserva = CANCELADA_GESTOR_MUNICIPAL;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + id);
        }

        return estadoReserva;
    }
}
