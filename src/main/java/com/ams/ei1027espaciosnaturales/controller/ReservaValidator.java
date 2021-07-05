package com.ams.ei1027espaciosnaturales.controller;

import com.ams.ei1027espaciosnaturales.model.Reserva;
import org.jetbrains.annotations.NotNull;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservaValidator implements Validator {
    private String path;

    public String getPath() {
        return path;
    }

    @Override
    public boolean supports(@NotNull Class<?> cls) {
        return Reserva.class.equals(cls);
        // o, si volguérem tractar també les subclasses:
        // return Nadador.class.isAssignableFrom(cls);
    }

    @Override
    public void validate(@NotNull Object obj, @NotNull Errors errors) {
        Reserva reserva = (Reserva) obj;
        LocalTime acceso = reserva.getHoraAcceso();
        LocalTime salida = reserva.getHoraSalida();
        if (acceso != null && salida != null && acceso.compareTo(salida) <= 0) {
            errors.rejectValue("horaSalida", "consistencia",
                    "No puedes salir antes de haber entrado");
            this.path = "reserva/addesp";
        }
        LocalDate fechaAcceso = reserva.getFechaAcceso();
        LocalDate fechaCreacion = reserva.getFechaCreacion();
        if (fechaAcceso.compareTo(fechaCreacion) <= 0) {
            errors.rejectValue("fechaAcceso", "consistencia",
                    "No puedes acceder antes de haber hecho la reserva");
            this.path = "reserva/add";
        }
        LocalTime franjaInicio = reserva.getInicioFranjaHoraria();
        LocalDate rangeFecha = fechaAcceso.minusDays(2);
        if (fechaCreacion.compareTo(rangeFecha) < 0) {
            errors.rejectValue("fechaAcceso", "limitacion",
                    "No puedes reservar con más de 2 días de antelación");
            this.path = "reserva/add";
        }
        if (fechaAcceso.compareTo(LocalDate.now()) < 0){
            errors.rejectValue("fechaAcceso", "consistencia",
                    "No puedes acceder en un día que ya ha pasado");
            this.path = "reserva/addesp";
        }
        if (fechaAcceso.compareTo(LocalDate.now()) == 0 && franjaInicio.compareTo(LocalTime.now()) < 0){
            errors.rejectValue("inicioFranjaHoraria", "consistencia",
                    "No puedes reservar habiendo ya empezado la franja horaria");
            this.path = "reserva/addesp";
        }
    }
}
