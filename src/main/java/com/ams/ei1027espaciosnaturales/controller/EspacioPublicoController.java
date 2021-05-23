package com.ams.ei1027espaciosnaturales.controller;


import com.ams.ei1027espaciosnaturales.dao.EspacioPublicoDAO;
import com.ams.ei1027espaciosnaturales.model.EspacioPublico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/espacioPublico")
public class EspacioPublicoController {

    private EspacioPublicoDAO espacioPublicoDAO;

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
    public String addEspacioPublico(Model model) {
        model.addAttribute("espacioPublico", new EspacioPublico());
        return "espacioPublico/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddEspacioPublico(@ModelAttribute("espacioPublico") EspacioPublico e,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "espacioPublico/add";
        }
        espacioPublicoDAO.addEspacioPublico(e);
        return "redirect:list";
    }

    // Los siguientes dos metodos gestionan la modificacion de un espacio publico
    @RequestMapping(value = "/update/{nombre}", method = RequestMethod.GET)
    public String updateEspacioPublico(Model model, @PathVariable String nombre) {
        model.addAttribute("espacioPublico", espacioPublicoDAO.getEspacioPublico(nombre));
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
    public String processDeleteEspacioPublico(@PathVariable String nombre) {
        espacioPublicoDAO.deleteEspacioPublicoNombre(nombre);
        return "redirect:../list";
    }
}
