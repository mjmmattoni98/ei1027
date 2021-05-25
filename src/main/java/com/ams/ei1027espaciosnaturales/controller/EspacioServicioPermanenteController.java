package com.ams.ei1027espaciosnaturales.controller;

import com.ams.ei1027espaciosnaturales.dao.EspacioServicioPermanenteDAO;
import com.ams.ei1027espaciosnaturales.model.EspacioServicioPermanente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/espacio_servicio_permanente")
public class EspacioServicioPermanenteController {

    private EspacioServicioPermanenteDAO espacioServicioPermanenteDAO;

    @Autowired
    public void setEspacioServicioPermanenteDAO(EspacioServicioPermanenteDAO esp) {
        this.espacioServicioPermanenteDAO = esp;
    }

    // Listar los servicios permanentes asignados
    @RequestMapping("/list")
    public String listEspacioServiciosPermanentes(Model model) {
        model.addAttribute("espacios_servicios_permanentes", espacioServicioPermanenteDAO.getEspaciosServiciosPermanentes());
        return "espacio_servicio_permanente/list";
    }

    // Los siguientes dos metodos gestionan la asignación de un servicio a un espacio
    @RequestMapping(value = "/add")
    public String addEspacioServicioPermanente(Model model) {
        model.addAttribute("espacio_servicio_permanente", new EspacioServicioPermanente());
        return "espacio_servicio_permanente/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddEspacioServicioPermanente(@ModelAttribute("espacio_servicio_permanente") EspacioServicioPermanente esp,
                                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "espacio_servicio_permanente/add";
        }
        espacioServicioPermanenteDAO.addEspacioServicioPermanente(esp);
        return "redirect:list";
    }

    // Los siguientes dos metodos gestionan la modificacion de un servicio permanente asignado a un espacio
    @RequestMapping(value = "/update/{nombre}/{tipo}", method = RequestMethod.GET)
    public String updateEspacioServicioPermanente(Model model, @PathVariable String nombre, @PathVariable String tipo) {
        model.addAttribute("espacio_servicio_permanente", espacioServicioPermanenteDAO.getEspacioServicioPermanente(nombre, tipo));
        return "espacio_servicio_permanente/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("espacio_servicio_permanente") EspacioServicioPermanente esp,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "espacio_servicio_permanente/update";
        espacioServicioPermanenteDAO.updateEspacioServicioPermanente(esp);
        return "redirect:list";
    }

    @RequestMapping(value = "/delete/{nombre}/{tipo}")
    public String processDeleteEspacioServicioPermanente(@PathVariable String nombre, @PathVariable String tipo) {
        espacioServicioPermanenteDAO.deleteEspacioServicioPermanente(nombre, tipo);
        return "redirect:../list";
    }
}
