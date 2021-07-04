package com.ams.ei1027espaciosnaturales.controller;

import com.ams.ei1027espaciosnaturales.model.UserInterno;

import javax.servlet.http.HttpSession;

public abstract class RolController {
    protected static final String ROL_GESTOR = "gestor";
    protected static final String ROL_CIUDADANO = "ciudadano";

    protected UserInterno checkSession(HttpSession session, String rol){
        if(session.getAttribute("user") == null) return null;

        UserInterno user = (UserInterno) session.getAttribute("user");

        if (!user.getRol().equals(rol)) {
            System.out.println("El usuario no puede acceder a esta página con este rol");
            throw new EspaciosNaturalesException("No tienes permisos para acceder a esta página porque no eres un " + rol,
                    "AccesDenied", "../" + user.getUrlMainPage());
        }

        return user;
    }
}
