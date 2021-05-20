package com.ams.ei1027espaciosnaturales.model;

public enum TipoSuelo {
    ARENA("arena"),
    ROCA("roca"),
    PIEDRA("piedra");

    private final String value;

    TipoSuelo(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }

    public static TipoSuelo stringToTipo(@org.jetbrains.annotations.NotNull String tipo){
        TipoSuelo tipoSuelo;
        switch (tipo){
            case "arena":
                tipoSuelo = ARENA;
                break;
            case "roca":
                tipoSuelo = ROCA;
                break;
            case "piedra":
                tipoSuelo = PIEDRA;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + tipo);
        }

        return tipoSuelo;
    }
}
