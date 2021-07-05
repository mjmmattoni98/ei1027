package com.ams.ei1027espaciosnaturales.controller;

import com.ams.ei1027espaciosnaturales.dao.CiudadanoDAO;
import com.ams.ei1027espaciosnaturales.dao.EmailDAO;
import com.ams.ei1027espaciosnaturales.model.Ciudadano;
import com.ams.ei1027espaciosnaturales.model.UserInterno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/email")
public class EmailController extends RolController{
    private EmailDAO emailDAO;
    private CiudadanoDAO ciudadanoDAO;

    @Autowired
    public void setCiudadanoDAO(CiudadanoDAO ciudadanoDAO) {
        this.ciudadanoDAO = ciudadanoDAO;
    }

    @Autowired
    public void setEmailDAO(EmailDAO e) {
        this.emailDAO = e;
    }

    @RequestMapping("/list/{destinatario}")
    public String listEmails(HttpSession session, Model model, @PathVariable String destinatario) {
        UserInterno user = checkSession(session, ROL_CIUDADANO);
        if (user == null){
            model.addAttribute("user", new UserInterno());
            return "login";
        }

        Ciudadano ciudadano = ciudadanoDAO.getCiudadano(user.getDni());

        if (!ciudadano.getEmail().equals(destinatario)) {
            System.out.println("El usuario no puede acceder a esta página");
            throw new EspaciosNaturalesException("No tienes permisos para acceder a esta página porque no son tus emails.",
                    "AccesDenied", "../" + user.getUrlMainPage());
        }

        model.addAttribute("emails", emailDAO.getEmails(destinatario));
        return "email/list";
    }
}
