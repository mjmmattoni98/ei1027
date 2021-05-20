package com.ams.ei1027espaciosnaturales.model;

public enum TipoEspacio {
    PLAYA("playa"),
    RIO("río"),
    ESTANQUE("estanque"),
    LAGO("lago"),
    BOSQUE("bosque"),
    OTROS("otros");

    private final String value;

    TipoEspacio(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }

    public static TipoEspacio stringToTipo(@org.jetbrains.annotations.NotNull String tipo){
        TipoEspacio tipoEspacio;
        switch (tipo){
            case "playa":
                tipoEspacio = PLAYA;
                break;
            case "río":
                tipoEspacio = RIO;
                break;
            case "estanque":
                tipoEspacio = ESTANQUE;
                break;
            case "lago":
                tipoEspacio = LAGO;
                break;
            case "bosque":
                tipoEspacio = BOSQUE;
                break;
            default:
                tipoEspacio = OTROS;
        }

        return tipoEspacio;
    }
}
