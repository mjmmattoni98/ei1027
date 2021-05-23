package com.ams.ei1027espaciosnaturales.model;

import org.jetbrains.annotations.NotNull;

public enum TipoAcceso {
    ABIERTO("abierto"),
    RESTRINGIDO("restringido"),
    CERRADO("cerrado");

    private final String value;

    TipoAcceso(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }

    public static TipoAcceso stringToTipo(@NotNull String tipo){
        TipoAcceso tipoAcceso;
        switch (tipo){
            case "abierto":
                tipoAcceso = ABIERTO;
                break;
            case "restringido":
                tipoAcceso = RESTRINGIDO;
                break;
            case "cerrado":
                tipoAcceso = CERRADO;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + tipo);
        }

        return tipoAcceso;
    }
}
