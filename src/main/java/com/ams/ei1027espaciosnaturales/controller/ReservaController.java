package com.ams.ei1027espaciosnaturales.controller;

import com.ams.ei1027espaciosnaturales.dao.ReservaDAO;
import com.ams.ei1027espaciosnaturales.model.Reserva;
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

@Controller
@RequestMapping("/reserva")
public class ReservaController {
    private ReservaDAO reservaDAO;

    @Autowired
    public void setReservaDAO(ReservaDAO r) {
        this.reservaDAO = r;
    }

    @RequestMapping("/list")
    public String listComentarios(Model model) {
        model.addAttribute("reservas", reservaDAO.getReservas());
        return "reserva/list";
    }

    @RequestMapping(value = "/add")
    public String addComentario(Model model) {
        model.addAttribute("reserva", new Reserva());
        return "reserva/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddComentario(@ModelAttribute("reserva") Reserva r,
                                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "reserva/add";
        }
        try {
            reservaDAO.addReserva(r);
        }
        catch (DataAccessException e){
            throw new EspaciosNaturalesException("Error accediendo a la base de datos", "ErrorAccidiendoDatos", "/");
        }
        return "redirect:list";
    }

    @RequestMapping(value = "/update/{numReserva}", method = RequestMethod.GET)
    public String updateComentario(Model model, @PathVariable int numReserva) {
        model.addAttribute("reserva", reservaDAO.getReserva(numReserva));
        return "reserva/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("reserva") Reserva r,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "reserva/update";
        reservaDAO.updateReserva(r);
        return "redirect:list";
    }

    @RequestMapping(value = "/delete/{numReserva}")
    public String processDeleteComentario(@PathVariable int numReserva) {
        reservaDAO.deleteReserva(numReserva);
        return "redirect:../list";
    }
}
