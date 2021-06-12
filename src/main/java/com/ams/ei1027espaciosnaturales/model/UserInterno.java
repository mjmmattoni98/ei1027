package com.ams.ei1027espaciosnaturales.model;

import java.io.Serializable;

public class UserInterno implements Serializable {
    private String username;
    private String password;
    private String rol;
    private String dni;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol(){ return rol;}

    public void setRol(String rol){ this.rol = rol;}

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getUrlMainPage() {
        if(this.rol.equals("gestor"))
            return "espacioPublico/list";
        if(this.rol.equals("ciudadano"))
            return "ciudadano/perfil";
        return "/";
    }
}
