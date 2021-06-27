package com.ams.ei1027espaciosnaturales.controller;

import com.ams.ei1027espaciosnaturales.dao.CiudadanoDAO;
import com.ams.ei1027espaciosnaturales.model.Ciudadano;
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
@RequestMapping("/ciudadano")
public class CiudadanoController {

    private CiudadanoDAO ciudadanoDAO;
    private static final CiudadanoValidator validator = new CiudadanoValidator();

    @Autowired
    public void setCiudadanoDAO(CiudadanoDAO c) {
        this.ciudadanoDAO = c;
    }

    @RequestMapping("/perfil")
    public String perfilCiudadano(HttpSession session, Model model){

        UserInterno user = checkSession(session);
        if (user == null){
            model.addAttribute("user", new UserInterno());
            return "login";
        }

        model.addAttribute("ciudadano", ciudadanoDAO.getCiudadano(user.getDni()));
        return "ciudadano/perfil";
    }

    // Listar los ciudadanos
    @RequestMapping("/list")
    public String listCiudadanos(Model model) {
        model.addAttribute("ciudadanos", ciudadanoDAO.getCiudadanos());
        return "ciudadano/list";
    }

    // Los siguientes dos metodos gestionan la inserción de un ciudadano
    @RequestMapping(value = "/add")
    public String addCiudadano(Model model) {
        model.addAttribute("ciudadano", new Ciudadano());
        return "ciudadano/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddCiudadano(@ModelAttribute("ciudadano") Ciudadano c, BindingResult bindingResult) {

        validator.validate(c, bindingResult);
        if (bindingResult.hasErrors()) return "ciudadano/add";

        c.createCodigo();
        c.generateRandomPin();
        try{
            ciudadanoDAO.addCiudadano(c);
        }
        catch (DuplicateKeyException e){
            throw new EspaciosNaturalesException("Ya existe un ciudadano con el mismo DNI", "CPDuplicada", "ciudadano/add");
        }
        catch (DataAccessException e){
            throw new EspaciosNaturalesException("Error accediendo a la base de datos", "ErrorAccidiendoDatos", "/");
        }
        return "redirect:/ciudadano/perfil";
    }

    // Los siguientes dos metodos gestionan la modificacion de un ciudadano
    @RequestMapping(value = "/update/{dni}", method = RequestMethod.GET)
    public String updateCiudadano(Model model, @PathVariable String dni) {
        model.addAttribute("ciudadano", ciudadanoDAO.getCiudadano(dni));
        return "ciudadano/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("ciudadano") Ciudadano c,
                                      BindingResult bindingResult) {
        validator.validate(c, bindingResult);
        if (bindingResult.hasErrors()) return "ciudadano/update";
        ciudadanoDAO.updateCiudadano(c);
        return "redirect:list";
    }

    @RequestMapping(value = "/delete/{dni}")
    public String processDeleteCiudadano(@PathVariable String dni) {
        ciudadanoDAO.deleteCiudadanoDNI(dni);
        return "redirect:../list";
    }

    private UserInterno checkSession(HttpSession session){
        if(session.getAttribute("user") == null) return null;

        UserInterno user = (UserInterno) session.getAttribute("user");

        if (!user.getRol().equals("ciudadano")) {
            System.out.println("El usuario no puede acceder a esta pagina con este rol");
            throw new EspaciosNaturalesException("No tienes permisos para acceder a esta página porque no eres un ciudadano",
                    "AccesDenied", "../" + user.getUrlMainPage());
        }

        return user;
    }
}
