package com.ams.ei1027espaciosnaturales.controller;

import com.ams.ei1027espaciosnaturales.dao.ReservaDAO;
import com.ams.ei1027espaciosnaturales.model.Reserva;
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

@Controller
@RequestMapping("/reserva")
public class ReservaController {
    private ReservaDAO reservaDAO;

    @Autowired
    public void setReservaDAO(ReservaDAO r) {
        this.reservaDAO = r;
    }

    @RequestMapping("/list")
    public String listReservas(HttpSession session, Model model) {

        UserInterno user = (UserInterno) session.getAttribute("user");

        if (user.getRol().equals("gestor")) {
            model.addAttribute("reservas", reservaDAO.getReservas());
        }
        else if(user.getRol().equals("ciudadano")){
            model.addAttribute("reservas", reservaDAO.getReservasCiudadano(user.getDni()));
        }

        return "reserva/list";
    }

    @RequestMapping(value = "/add")
    public String addReserva(Model model) {
        System.out.println("llega aqui 3?");
        model.addAttribute("reserva", new Reserva());
        return "reserva/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddReserva(@ModelAttribute("reserva") Reserva r,
                                       BindingResult bindingResult) {
        System.out.println("llega aqui 2?");
        if (bindingResult.hasErrors()) {
            System.out.println("hay errores verdad?");
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
    public String updateReserva(Model model, @PathVariable int numReserva) {
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
    public String processDeleteReserva(@PathVariable int numReserva) {
        reservaDAO.deleteReserva(numReserva);
        return "redirect:../list";
    }
}
