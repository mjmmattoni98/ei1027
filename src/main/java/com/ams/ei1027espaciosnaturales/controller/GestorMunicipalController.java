package com.ams.ei1027espaciosnaturales.controller;

import com.ams.ei1027espaciosnaturales.dao.GestorMunicipalDAO;
import com.ams.ei1027espaciosnaturales.model.GestorMunicipal;
import com.ams.ei1027espaciosnaturales.model.UserInterno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/gestorMunicipal")
public class GestorMunicipalController {
    private GestorMunicipalDAO gestorMunicipalDAO;

    @Autowired
    public void setGestorMunicipalDAO(GestorMunicipalDAO gm) {
        this.gestorMunicipalDAO = gm;
    }

    @RequestMapping("/perfil")
    public String perfilCiudadano(HttpSession session, Model model){
        UserInterno user = checkSession(session);
        if (user == null){
            model.addAttribute("user", new UserInterno());
            return "login";
        }

        model.addAttribute("gestor", gestorMunicipalDAO.getGestorMunicipal(user.getDni()));
        return "gestorMunicipal/perfil";
    }

    @RequestMapping("/list")
    public String listGestoresMunicipales(Model model) {
        model.addAttribute("gestoresMunicipales", gestorMunicipalDAO.getGestoresMunicipales());
        return "gestorMunicipal/list";
    }

    @RequestMapping(value = "/add")
    public String addGestorMunicipal(Model model) {
        model.addAttribute("gestorMunicipal", new GestorMunicipal());
        return "gestorMunicipal/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddGestorMunicipal(@ModelAttribute("gestorMunicipal") GestorMunicipal gm,
                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "gestorMunicipal/add";
        }
        try {
            gestorMunicipalDAO.addGestorMunicipal(gm);
        }
        catch (DuplicateKeyException e){
            throw new EspaciosNaturalesException("Ya existe un gestor municipal con el mismo DNI",
                    "CPDuplicada", "gestorMunicipal/add");
        }
        catch (DataAccessException e){
            throw new EspaciosNaturalesException("Error accediendo a la base de datos", "ErrorAccidiendoDatos", "/");
        }
        return "redirect:list";
    }

    @RequestMapping(value = "/update/{dni}", method = RequestMethod.GET)
    public String updateGestorMunicipal(Model model, @PathVariable String dni) {
         model.addAttribute("gestorMunicipal", gestorMunicipalDAO.getGestorMunicipal(dni));
        return "gestorMunicipal/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("gestorMunicipal") GestorMunicipal gm,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "gestorMunicipal/update";
        gestorMunicipalDAO.updateGestorMunicipal(gm);
        return "redirect:list";
    }

    @RequestMapping(value = "/delete/{dni}")
    public String processDeleteGestorMunicipal(@PathVariable String dni) {
         gestorMunicipalDAO.deleteGestorMunicipal(dni);
        return "redirect:../list";
    }

    private UserInterno checkSession(HttpSession session){
        if(session.getAttribute("user") == null) return null;

        UserInterno user = (UserInterno) session.getAttribute("user");

        if (!user.getRol().equals("gestor")) {
            System.out.println("El usuario no puede acceder a esta pagina con este rol");
            throw new EspaciosNaturalesException("No tienes permisos para acceder a esta página porque no eres un gestor",
                    "AccesDenied", "../" + user.getUrlMainPage());
        }

        return user;
    }
}
