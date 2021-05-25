package com.ams.ei1027espaciosnaturales.controller;

import com.ams.ei1027espaciosnaturales.dao.ServicioDAO;
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
@RequestMapping("/servicio")
public class ServicioController {

    private ServicioDAO servicioDAO;

    @Autowired
    public void setMunicipioDAO(ServicioDAO m) {
        this.servicioDAO = m;
    }

    // Listar los servicios
    @RequestMapping("/list")
    public String listServicios(Model model) {
        model.addAttribute("servicos", servicioDAO.getServicios());
        return "servicios/list";
    }

    // Los siguientes dos metodos gestionan el alta de un servicio
    @RequestMapping(value = "/add")
    public String addServicio(Model model) {
        model.addAttribute("servicio", new Servicio());
        return "servicio/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddServicio(@ModelAttribute("servicio") Servicio s,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "servicio/add";
        }
        servicioDAO.addServicio(s);
        return "redirect:list";
    }

    // Los siguientes dos metodos gestionan la modificacion de un servicio
    @RequestMapping(value = "/update/{tipo}/{supertipo}", method = RequestMethod.GET)
    public String updateServicio(Model model, @PathVariable String tipo, @PathVariable String supertipo) {
        model.addAttribute("servicio", servicioDAO.getServicio(tipo, supertipo));
        return "servicio/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("servicio") Servicio s,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "servicio/update";
        servicioDAO.updateServicio(s);
        return "redirect:list";
    }

    @RequestMapping(value = "/delete/{tipo}/{supertipo}")
    public String processDeleteServicio(@PathVariable String tipo, @PathVariable String supertipo) {
         servicioDAO.deleteServicio(tipo, supertipo);
        return "redirect:../list";
    }
}
