package com.ams.ei1027espaciosnaturales.controller;

import com.ams.ei1027espaciosnaturales.model.EspacioPublico;
import org.jetbrains.annotations.NotNull;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Arrays;
import java.util.List;

public class EspacioPublicoValidator implements Validator {
    @Override
    public boolean supports(@NotNull Class<?> cls) {
        return EspacioPublico.class.equals(cls);
        // o, si volguérem tractar també les subclasses:
        // return Nadador.class.isAssignableFrom(cls);
    }

    @Override
    public void validate(@NotNull Object obj, @NotNull Errors errors) {
        EspacioPublico espacioPublico = (EspacioPublico) obj;
        List<String> espacios = Arrays.asList("playa", "río", "estanque", "lago", "bosque", "otros");
        if (!espacios.contains(espacioPublico.getTEspacio()))
            errors.rejectValue("tEspacio", "valor incorrecto",
                    "Debe ser: playa, río, estanque, lago, bosque, otros");
        List<String> suelos = Arrays.asList("arena", "roca", "piedra");
        if (!suelos.contains(espacioPublico.getTSuelo()))
            errors.rejectValue("tSuelo", "valor incorrecto",
                    "Debe ser: arena, roca, piedra");
        List<String> accesos = Arrays.asList("abierto", "restringido", "cerrado");
        if (!accesos.contains(espacioPublico.getTAcceso()))
            errors.rejectValue("tAcceso", "valor incorrecto",
                    "Debe ser: abierto, restringido, cerrado");
    }
}
