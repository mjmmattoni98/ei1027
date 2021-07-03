package com.ams.ei1027espaciosnaturales.controller;

import com.ams.ei1027espaciosnaturales.model.Ciudadano;
import com.ams.ei1027espaciosnaturales.model.UserInterno;
import org.jetbrains.annotations.NotNull;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class CiudadanoValidator implements Validator {

    @Override
    public boolean supports(@NotNull Class<?> aClass) {
        return UserInterno.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(@NotNull Object o, @NotNull Errors errors) {
        Ciudadano c = (Ciudadano) o;
        if (c.getDni().length() != 9)
            errors.rejectValue("dni", "longitud", "El dni debe de tener 9 carácteres");
        if (c.getEdad() < 18)
            errors.rejectValue("edad", "edad minima", "La edad mínima es de 18 años");
        if (c.getNombre().trim().equals(""))
            errors.rejectValue("nombre", "obligatorio", "Debes introducir el nombre");
        if (c.getApellidos().trim().equals(""))
            errors.rejectValue("apellidos", "obligatorio", "Debes introducir el apellido");
        if (c.getCalle().trim().equals(""))
            errors.rejectValue("calle", "obligatorio", "Debes introducir la calle");
        if (c.getNumero() < 1)
            errors.rejectValue("numero", "consistencia", "El número de tu casa no puede ser menor a 1");
        if (c.getPoblacion().trim().equals(""))
            errors.rejectValue("poblacion", "obligatorio", "Debes introducir la población");
        if (c.getTelefono().trim().equals(""))
            errors.rejectValue("telefono", "obligatorio", "Debes introducir el teléfono");
//        if (c.getTelefono().matches("^123456789\\+$"))
//            errors.rejectValue("telefono_formato", "caracteres numericos y +", "El teléfono solamente debe estar " +
//                    "compuesto por carácteres numéricos y/o +");
        if (c.getEmail().trim().equals(""))
            errors.rejectValue("email", "obligatorio", "Debes introducir el email");
//        if (c.getEmail().matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$"))
//            errors.rejectValue("email_formato", "formato", "El email no sigue el formato común");
        if (!c.getConfirmacion().equals("si"))
            errors.rejectValue("confirmacion", "obligatorio", "Debes confirmar que comprendes cuál es tu " +
                    "usuario y contraseña");

    }
}
