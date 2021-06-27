package com.ams.ei1027espaciosnaturales.controller;

import com.ams.ei1027espaciosnaturales.dao.*;
import com.ams.ei1027espaciosnaturales.model.UserInterno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/responsable")
public class UserInternoController {

    private UserDAO userDAO;
    private ServicioDAO servicioDAO;
    private GestorMunicipalDAO gestorDao;
    private EspacioPublicoDAO espacioDao;
    private MunicipioDAO municipioDAO;

    @Autowired
    public void setMunicipioDAO(ServicioDAO m) {
        this.servicioDAO = m;
    }

    @Autowired
    public void setUserInternoDAO(UserDAO userDao) {
        this.userDAO = userDao;
    }

    @Autowired
    public void setGestorDAO(GestorMunicipalDAO gestorDao) {
        this.gestorDao = gestorDao;
    }

    @Autowired
    public void setEspacioDAO(EspacioPublicoDAO espacioDao) {
        this.espacioDao = espacioDao;
    }

    @Autowired
    public void setMunicipioDAO(MunicipioDAO municipioDao) {
        this.municipioDAO = municipioDao;
    }

    @RequestMapping("/anadirMunicipios")
    public String inicioGestorAnyadirMunicipios(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            model.addAttribute("user", new UserInterno());

            session.setAttribute("nextUrl", "/responsable/anadirMunicipios");

            return "login";
        }
        model.addAttribute("users", municipioDAO.getMunicipios());
        return "/responsable/anadirMunicipios";
    }

    @RequestMapping("/anadirServicios")
    public String inicioGestorAnyadirServicios(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            model.addAttribute("user", new UserInterno());

            session.setAttribute("nextUrl", "/responsable/anadirServicios");

            return "login";
        }
        model.addAttribute("servicios", servicioDAO.getServicios());
        return "/responsable/anadirServicios";
    }

    @RequestMapping("/datosGestores")
    public String inicioGestorDatosGestores(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            model.addAttribute("user", new UserInterno());

            session.setAttribute("gestoresMunicipales", gestorDao.getGestoresMunicipales());

            return "login";
        }
        model.addAttribute("users", userDAO.listAllUsers());
        return "/responsable/datosGestores";
    }

    @RequestMapping("/listaEspacios")
    public String inicioGestorListaEspacios(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            model.addAttribute("user", new UserInterno());

            session.setAttribute("nextUrl", "/responsable/listaEspacios");

            return "login";
        }
        model.addAttribute("users", espacioDao.getEspaciosPublicos());
        return "/responsable/listaEspacios";
    }

}
