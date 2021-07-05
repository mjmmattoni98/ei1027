package com.ams.ei1027espaciosnaturales.controller;

import com.ams.ei1027espaciosnaturales.model.EspacioServicioEstacional;
import org.jetbrains.annotations.NotNull;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class EspacioServcicioEstacionalValidator implements Validator {
    @Override
    public boolean supports(@NotNull Class<?> aClass) {
        return EspacioServicioEstacional.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(@NotNull Object o, @NotNull Errors errors) {
        EspacioServicioEstacional estacional = (EspacioServicioEstacional) o;
        if (estacional.getFechaFin().compareTo(estacional.getFechaIni()) <= 0)
            errors.rejectValue("fechaFin", "consistencia",
                    "La fecha fin no puede ser antes de la fecha de inicio");
        if (estacional.getHoraFin().compareTo(estacional.getHoraIni()) <= 0)
            errors.rejectValue("horaFin", "consistencia",
                    "La hora fin no puede ser antes de la hora de inicio");
    }
}
