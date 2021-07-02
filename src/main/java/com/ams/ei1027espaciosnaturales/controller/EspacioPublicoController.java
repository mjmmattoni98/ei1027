package com.ams.ei1027espaciosnaturales.controller;

import com.ams.ei1027espaciosnaturales.dao.EspacioPublicoDAO;
import com.ams.ei1027espaciosnaturales.model.EspacioPublico;

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
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/espacioPublico")
public class EspacioPublicoController {

    private EspacioPublicoDAO espacioPublicoDAO;
    private static final EspacioPublicoValidator validator = new EspacioPublicoValidator();

    @Autowired
    public void setEspacioPublicoDAO(EspacioPublicoDAO e) {
        this.espacioPublicoDAO = e;
    }

    // Listar los espacios publicos
    @RequestMapping("/list")
    public String listEspacioPublico(Model model) {
        model.addAttribute("espaciosPublicos", espacioPublicoDAO.getEspaciosPublicos());
        return "espacioPublico/list";
    }

    // Los siguientes dos metodos gestionan la inserción de un espacio publico
    @RequestMapping(value = "/add")
    public String addEspacioPublico(HttpSession session, Model model) {
        UserInterno user = checkSession(session);
        if (user == null){
            model.addAttribute("user", new UserInterno());
            return "login";
        }

        model.addAttribute("espacioPublico", new EspacioPublico());
        return "espacioPublico/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddEspacioPublico(@ModelAttribute("espacioPublico") EspacioPublico e,
                                      BindingResult bindingResult) {
        validator.validate(e, bindingResult);
        if (bindingResult.hasErrors()) {
            return "espacioPublico/add";
        }
        try {
            espacioPublicoDAO.addEspacioPublico(e);
        }
        catch (DuplicateKeyException exception){
            throw new EspaciosNaturalesException("Ya existe un espacio público con el mismo nombre",
                    "CPDuplicada", "espacioPublico/add");
        }
        catch (DataAccessException exception){
            throw new EspaciosNaturalesException("Error accediendo a la base de datos\n" + exception.getMessage(),
                    "ErrorAccidiendoDatos", "/");
        }
        return "redirect:list";
    }

    // Los siguientes dos metodos gestionan la modificacion de un espacio publico
    @RequestMapping(value = "/update/{nombre}", method = RequestMethod.GET)
    public String updateEspacioPublico(HttpSession session, Model model, @PathVariable String nombre) {
        UserInterno user = checkSession(session);
        if (user == null){
            model.addAttribute("user", new UserInterno());
            return "login";
        }

        model.addAttribute("espacioPublico", espacioPublicoDAO.getEspacioPublico(nombre));
        List<String> espacios = Arrays.asList("playa", "río", "estanque", "lago", "bosque", "otros");
        model.addAttribute("espacioList", espacios);
        List<String> suelos = Arrays.asList("arena", "roca", "piedra");
        model.addAttribute("sueloList", suelos);
        List<String> accesos = Arrays.asList("abierto", "restringido", "cerrado");
        model.addAttribute("accesoList", accesos);
        return "espacioPublico/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("espacioPublico") EspacioPublico e,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "espacioPublico/update";
        espacioPublicoDAO.updateEspacioPublico(e);
        return "redirect:list";
    }

    @RequestMapping(value = "/delete/{nombre}")
    public String processDeleteEspacioPublico(HttpSession session, Model model, @PathVariable String nombre) {
        UserInterno user = checkSession(session);
        if (user == null){
            model.addAttribute("user", new UserInterno());
            return "login";
        }

        espacioPublicoDAO.deleteEspacioPublicoNombre(nombre);
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
