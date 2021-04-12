package com.ams.ei1027espaciosnaturales.controller;


import com.ams.ei1027espaciosnaturales.dao.CiudadanoDAO;
import com.ams.ei1027espaciosnaturales.model.Ciudadano;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/ciudadano")
public class CiudadanoController {

    private CiudadanoDAO ciudadanoDAO;

    @Autowired
    public void setCiudadanoDAO(CiudadanoDAO c) {
        this.ciudadanoDAO = c;
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
    public String processAddCiudadano(@ModelAttribute("ciudadano") Ciudadano c,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "ciudadano/add";
        }
        ciudadanoDAO.addCiudadano(c);
        return "redirect:list";
    }

    // Los siguientes dos metodos gestionan la modificacion de un ciudadano
    @RequestMapping(value = "/update/{dni}", method = RequestMethod.GET)
    public String updateCiudadano(Model model, @PathVariable String dni) {
        model.addAttribute("ciudadano", ciudadanoDAO.getCiudadano(dni));
        return "ciudadano/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("nadador") Ciudadano c,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "ciudadano/update";
        ciudadanoDAO.updateCiudadano(c);
        return "redirect:list";
    }

    @RequestMapping(value = "/delete/{dni}")
    public String processDeleteCiudadano(@PathVariable String dni) {
        ciudadanoDAO.deleteCiudadanoDNI(dni);
        return "redirect:../list";
    }
}
