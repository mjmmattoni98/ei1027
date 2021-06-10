package com.ams.ei1027espaciosnaturales.model;

public class UserInterno {

    String username;
    String password;
    String rol;

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

    public String getUrlMainPage() {
        if(this.rol.equals("gestor"))
            return "login/gestor";
        if(this.rol.equals("controlador"))
            return "controlador/main";
        if(this.rol.equals("ciudadano"))
            return "ciudadano/main";

        return null;

    }
}
