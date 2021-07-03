package com.ams.ei1027espaciosnaturales.controller;

import com.ams.ei1027espaciosnaturales.model.EspacioPublico;
import com.ams.ei1027espaciosnaturales.model.Zona;
import org.jetbrains.annotations.NotNull;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ZonaValidator implements Validator {
    @Override
    public boolean supports(@NotNull Class<?> cls) {
        return EspacioPublico.class.equals(cls);
        // o, si volguérem tractar també les subclasses:
        // return Nadador.class.isAssignableFrom(cls);
    }

    @Override
    public void validate(@NotNull Object obj, @NotNull Errors errors) {
        Zona zona = (Zona) obj;
        if (zona.getCapacidad() < zona.getOcupacion())
            errors.rejectValue("capacidad", "consistencia",
                    "No puedes actualizar a una capacidad inferior a la ocupación actual");
        if (zona.getCapacidad() <= 0)
            errors.rejectValue("capacidad", "capacidad positiva",
                    "La capacidad ha de ser mayor de 0");
    }
}
