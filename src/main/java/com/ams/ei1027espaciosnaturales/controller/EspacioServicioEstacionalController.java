package com.ams.ei1027espaciosnaturales.controller;

import com.ams.ei1027espaciosnaturales.dao.EspacioServicioEstacionalDAO;
import com.ams.ei1027espaciosnaturales.model.EspacioServicioEstacional;
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

import java.time.LocalDate;
import java.time.LocalTime;

@Controller
@RequestMapping("/espacio_servicio_estacional")
public class EspacioServicioEstacionalController {

    private EspacioServicioEstacionalDAO espacioServicioEstacionalDAO;

    @Autowired
    public void setEspacioServicioEstacionalDAO(EspacioServicioEstacionalDAO ese) {
        this.espacioServicioEstacionalDAO = ese;
    }

    // Listar los servicios estacionales asignados
    @RequestMapping("/list")
    public String listEspacioServiciosEstacionales(Model model) {
        model.addAttribute("espacios_servicios_estacionales", espacioServicioEstacionalDAO.getEspaciosServiciosEstacionales());
        return "espacio_servicio_estacional/list";
    }

    // Los siguientes dos metodos gestionan la asignación de un servicio a un espacio
    @RequestMapping(value = "/add")
    public String addEspacioServicioEstacional(Model model) {
        model.addAttribute("espacio_servicio_estacional", new EspacioServicioEstacional());
        return "espacio_servicio_estacional/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddEspacioServicioEstacional(@ModelAttribute("espacio_servicio_estacional") EspacioServicioEstacional ese,
                                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "espacio_servicio_estacional/add";
        }
        try {
            espacioServicioEstacionalDAO.addEspacioServicioEstacional(ese);
        }
        catch (DuplicateKeyException e){
            throw new EspaciosNaturalesException("Ya existe el servicio estacional en el esapcio público",
                    "CPDuplicada", "espacio_servicio_estacional/add");
        }
        catch (DataAccessException e){
            throw new EspaciosNaturalesException("Error accediendo a la base de datos", "ErrorAccidiendoDatos", "/");
        }
        return "redirect:list";
    }

    // Los siguientes dos metodos gestionan la modificacion de un servicio estacional asignado a un espacio
    @RequestMapping(value = "/update/{nombre}/{tipo}/{fechaIni}/{horaIni}", method = RequestMethod.GET)
    public String updateEspacioServicioEstacional(Model model, @PathVariable String nombre, @PathVariable String tipo,
                                                  @PathVariable LocalDate fechaIni, @PathVariable LocalTime horaIni) {
        model.addAttribute("espacio_servicio_estacional",
                espacioServicioEstacionalDAO.getEspacioServicioEstacional(nombre, tipo, fechaIni, horaIni));
        return "espacio_servicio_estacional/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("espacio_servicio_estacional") EspacioServicioEstacional ese,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "espacio_servicio_estacional/update";
        espacioServicioEstacionalDAO.updateEspacioServicioEstacional(ese);
        return "redirect:list";
    }

    @RequestMapping(value = "/delete/{nombre}/{tipo}/{fechaIni}/{horaIni}")
    public String processDeleteEspacioServicioEstacional(@PathVariable String nombre, @PathVariable String tipo,
                                                         @PathVariable LocalDate fechaIni, @PathVariable LocalTime horaIni) {
        espacioServicioEstacionalDAO.deleteEspacioServicioEstacional(nombre, tipo, fechaIni, horaIni);
        return "redirect:../list";
    }
}
