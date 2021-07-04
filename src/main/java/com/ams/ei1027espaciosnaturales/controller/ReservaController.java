package com.ams.ei1027espaciosnaturales.controller;

import com.ams.ei1027espaciosnaturales.dao.ReservaDAO;
import com.ams.ei1027espaciosnaturales.model.EstadoReserva;
import com.ams.ei1027espaciosnaturales.model.Reserva;
import com.ams.ei1027espaciosnaturales.model.UserInterno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;

@Controller
@RequestMapping("/reserva")
public class ReservaController extends RolController{
    private ReservaDAO reservaDAO;

    @Autowired
    public void setReservaDAO(ReservaDAO r) {
        this.reservaDAO = r;
    }

    @RequestMapping("/list/{zona}/{nombre}")
    public String listReservas(HttpSession session, Model model, @PathVariable int zona, @PathVariable String nombre) {
        if (session.getAttribute("user") == null){
            model.addAttribute("user", new UserInterno());
            return "login";
        }
        UserInterno user = (UserInterno) session.getAttribute("user");

        if (user.getRol().equals(ROL_GESTOR)) {
            model.addAttribute("reservas", reservaDAO.getReservas());
        }
        else if(user.getRol().equals(ROL_CIUDADANO)){
            model.addAttribute("reservas", reservaDAO.getReservasCiudadano(user.getDni()));
        }
        model.addAttribute("zona", zona);
        model.addAttribute("espacio_publico", nombre);

        return "reserva/list";
    }

    @RequestMapping(value = "/add/{zona}/{espacio}")
    public String addReserva(HttpSession session, Model model, @PathVariable int zona,
                             @PathVariable String espacio) {
        UserInterno user = checkSession(session, ROL_CIUDADANO);
        if (user == null){
            model.addAttribute("user", new UserInterno());
            return "login";
        }
        Reserva reserva = new Reserva();
        reserva.setZona(zona);
        reserva.setEspacioPublico(espacio);
        model.addAttribute("reserva", reserva);
        return "reserva/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddReserva(HttpSession session, @ModelAttribute("reserva") Reserva r,
                                       BindingResult bindingResult) {
        UserInterno user = (UserInterno) session.getAttribute("user");
        if (bindingResult.hasErrors()) {
            return "reserva/add";
        }
        try {
            r.setDni(user.getDni());
            r.setEstado(EstadoReserva.PENDIENTE_USO.getId());
            r.setFechaCreacion(LocalDate.now());
            reservaDAO.addReserva(r);
        }
        catch (DataAccessException e){
            throw new EspaciosNaturalesException("Error accediendo a la base de datos", "ErrorAccidiendoDatos", "/");
        }
        return "redirect:list/" + r.getZona() + "/" + r.getEspacioPublico();
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
        if(r.getHoraAcceso() != null){
            if(r.getHoraSalida() == null){
                r.setEstado(EstadoReserva.EN_USO.getId());
            }
            else {
                r.setEstado(EstadoReserva.FIN_USO.getId());
            }
        }
        else{
            r.setEstado(EstadoReserva.PENDIENTE_USO.getId());
            r.setHoraSalida(null);
        }
        reservaDAO.updateReserva(r);
        return "redirect:list/" + r.getZona() + "/" + r.getEspacioPublico();
    }

    @RequestMapping(value = "/delete/{numReserva}/{zona}/{nombre}")
    public String processDeleteReserva(HttpSession session, Model model, @PathVariable int numReserva,
                                       @PathVariable int zona, @PathVariable String nombre) {
        if (session.getAttribute("user") == null){
            model.addAttribute("user", new UserInterno());
            return "login";
        }
        UserInterno user = (UserInterno) session.getAttribute("user");
        if (user.getRol().equals("gestor")) {
            reservaDAO.updateReservaEstado(numReserva, EstadoReserva.CANCELADA_GESTOR_MUNICIPAL);
        }
        else if(user.getRol().equals("ciudadano")){
            reservaDAO.updateReservaEstado(numReserva, EstadoReserva.CANCELADA_CIUDADANO);
        }
        return "redirect:../../../list/" + zona + "/" + nombre;
    }
}
