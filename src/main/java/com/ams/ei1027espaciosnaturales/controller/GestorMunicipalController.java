package com.ams.ei1027espaciosnaturales.controller;

import com.ams.ei1027espaciosnaturales.dao.GestorMunicipalDAO;
import com.ams.ei1027espaciosnaturales.model.UserInterno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/gestorMunicipal")
public class GestorMunicipalController extends RolController{
    private GestorMunicipalDAO gestorMunicipalDAO;

    @Autowired
    public void setGestorMunicipalDAO(GestorMunicipalDAO gm) {
        this.gestorMunicipalDAO = gm;
    }

    @RequestMapping("/perfil")
    public String perfilGestorMunicipal(HttpSession session, Model model){
        UserInterno user = checkSession(session, ROL_GESTOR);
        if (user == null){
            model.addAttribute("user", new UserInterno());
            return "login";
        }

        model.addAttribute("gestor", gestorMunicipalDAO.getGestorMunicipal(user.getDni()));
        return "gestorMunicipal/perfil";
    }
}
