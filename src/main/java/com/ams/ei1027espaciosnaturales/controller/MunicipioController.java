package com.ams.ei1027espaciosnaturales.controller;


import com.ams.ei1027espaciosnaturales.dao.MunicipioDAO;
import com.ams.ei1027espaciosnaturales.model.Municipio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/municipio")
public class MunicipioController {

    private MunicipioDAO municipioDAO;

    @Autowired
    public void setMunicipioDAO(MunicipioDAO m) {
        this.municipioDAO = m;
    }

    // Listar los municipios
    @RequestMapping("/list")
    public String listMunicipio(Model model) {
        model.addAttribute("municipio", municipioDAO.getMunicipio());
        return "municipio/list";
    }

    // Los siguientes dos metodos gestionan la inserción de un municipio	
    @RequestMapping(value = "/add")
    public String addMunicipio(Model model) {
        model.addAttribute("municipio", new Municipio());
        return "municipio/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddMunicipio(@ModelAttribute("municipio") Municipio m,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "municipio/add";
        }
        municipioDAO.addMunicipio(m);
        return "redirect:list";
    }

    // Los siguientes dos metodos gestionan la modificacion de un municipio
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String updateMunicipio(Model model, @PathVariable int id) {
        model.addAttribute("municipio", municipioDAO.getMunicipio(id));
        return "municipio/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("municipio") Municipio m,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "municipio/update";
        municipioDAO.updateMunicipio(m);
        return "redirect:list";
    }

    @RequestMapping(value = "/delete/{id}")
    public String processDeleteMunicipio(@PathVariable int id) {
       municipioDAO.deleteMunicipio(id);
        return "redirect:../list";
    }
}
