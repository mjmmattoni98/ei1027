package com.ams.ei1027espaciosnaturales.controller;

import com.ams.ei1027espaciosnaturales.dao.FranjaHorariaDAO;
import com.ams.ei1027espaciosnaturales.model.FranjaHoraria;
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

import java.time.LocalTime;

@Controller
@RequestMapping("/franja_horaria")
public class FranjaHorariaController {
    private FranjaHorariaDAO franjaHorariaDAO;

    @Autowired
    public void setFranjaHorariaDAO(FranjaHorariaDAO f) {
        this.franjaHorariaDAO = f;
    }

    @RequestMapping("/list")
    public String listComentarios(Model model) {
        model.addAttribute("franjas_horarias", franjaHorariaDAO.getFranjasHorarias());
        return "franja_horaria/list";
    }

    @RequestMapping(value = "/add")
    public String addComentario(Model model) {
        model.addAttribute("franja_horaria", new FranjaHoraria());
        return "franja_horaria/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddComentario(@ModelAttribute("franja_horaria") FranjaHoraria f,
                                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "franja_horaria/add";
        }
        try {
            franjaHorariaDAO.addFranjaHoraria(f);
        }
        catch (DuplicateKeyException e){
            throw new EspaciosNaturalesException("Ya existe la franja horaria en el espacio público",
                    "CPDuplicada", "franja_horaria/add");
        }
        catch (DataAccessException e){
            throw new EspaciosNaturalesException("Error accediendo a la base de datos", "ErrorAccidiendoDatos", "/");
        }
        return "redirect:list";
    }

//    @RequestMapping(value = "/update/{horaInicio}/{horaFin}/{nombre}", method = RequestMethod.GET)
//    public String updateComentario(Model model, @PathVariable LocalTime horaInicio, @PathVariable LocalTime horaFin,
//                                   @PathVariable String nombre) {
//        model.addAttribute("franja_horaria", franjaHorariaDAO.getFranjaHoraria(horaInicio, horaFin, nombre));
//        return "franja_horaria/update";
//    }
//
//    @RequestMapping(value = "/update", method = RequestMethod.POST)
//    public String processUpdateSubmit(@ModelAttribute("franja_horaria") FranjaHoraria f,
//                                      BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) return "franja_horaria/update";
//        franjaHorariaDAO.;
//        return "redirect:list";
//    }

    @RequestMapping(value = "/delete/{horaInicio}/{horaFin}/{nombre}")
    public String processDeleteComentario(@PathVariable LocalTime horaInicio, @PathVariable LocalTime horaFin,
                                          @PathVariable String nombre) {
        franjaHorariaDAO.deleteFranjaHoraria(horaInicio, horaFin, nombre);
        return "redirect:../list";
    }
}
