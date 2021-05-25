package com.ams.ei1027espaciosnaturales.controller;

import com.ams.ei1027espaciosnaturales.dao.GestorMunicipalDAO;
import com.ams.ei1027espaciosnaturales.dao.ServicioDAO;
import com.ams.ei1027espaciosnaturales.model.GestorMunicipal;
import com.ams.ei1027espaciosnaturales.model.Servicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/gestorMunicipal")
public class GestorMunicipalController {
    private GestorMunicipalDAO gestorMunicipalDAO;

    @Autowired
    public void setGestorMunicipalDAO(GestorMunicipalDAO gm) {
        this.gestorMunicipalDAO = gm;
    }

    // Listar los servicios
    @RequestMapping("/list")
    public String listGestoresMunicipales(Model model) {
        model.addAttribute("gestoresMunicipales", gestorMunicipalDAO.getGestoresMunicipales());
        return "gestorMunicipal/list";
    }

    // Los siguientes dos metodos gestionan el alta de un servicio
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
        gestorMunicipalDAO.addGestorMunicipal(gm);
        return "redirect:list";
    }

    // Los siguientes dos metodos gestionan la modificacion de un servicio
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
}
