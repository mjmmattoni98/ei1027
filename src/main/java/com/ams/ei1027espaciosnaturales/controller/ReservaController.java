package com.ams.ei1027espaciosnaturales.controller;

import com.ams.ei1027espaciosnaturales.dao.EspacioPublicoDAO;
import com.ams.ei1027espaciosnaturales.dao.FranjaHorariaDAO;
import com.ams.ei1027espaciosnaturales.dao.ReservaDAO;
import com.ams.ei1027espaciosnaturales.dao.ZonaDAO;
import com.ams.ei1027espaciosnaturales.model.EspacioServicioEstacional;
import com.ams.ei1027espaciosnaturales.model.EstadoReserva;
import com.ams.ei1027espaciosnaturales.model.Reserva;
import com.ams.ei1027espaciosnaturales.model.UserInterno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/reserva")
public class ReservaController {
    private ReservaDAO reservaDAO;
    private EspacioPublicoDAO espacioPublicoDAO;
    private FranjaHorariaDAO franjaHorariaDAO;
    private ZonaDAO zonaDAO;


    @Autowired
    public void setReservaDAO(ReservaDAO r) {
        this.reservaDAO = r;
    }

    @Autowired
    public void setFranjaHorariaDAO(FranjaHorariaDAO r) {
        this.franjaHorariaDAO = r;
    }

    @Autowired
    public void setEspacioPublicoDAO(EspacioPublicoDAO r) {
        this.espacioPublicoDAO = r;
    }

    @Autowired
    public void setZonaDAO(ZonaDAO r) {
        this.zonaDAO = r;
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
        model.addAttribute("reserva", new Reserva());
        model.addAttribute("espaciosPublicos", espacioPublicoDAO.getEspaciosPublicos());
        System.out.println("entra aqui 2");
        return "reserva/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddReserva(HttpSession session, @ModelAttribute("reserva") Reserva r,
                                       BindingResult bindingResult) {
        UserInterno user = (UserInterno) session.getAttribute("user");
        System.out.println("entra aqui 1");
        if (bindingResult.hasErrors()) {
            return "reserva/add";
        }
        try {
            r.setDni(user.getDni());
            r.setEstado(EstadoReserva.PENDIENTE_USO);
            r.setFechaCreacion(LocalDate.now());
            System.out.println(r);
            reservaDAO.addReserva(r);
        }catch (DataAccessException e){
            throw new EspaciosNaturalesException("Error accediendo a la base de datos", "ErrorAccidiendoDatos", "/");
        }
        return "redirect:../list";
    }

    @RequestMapping(value = "/addesp", method = RequestMethod.POST)
    public String processDeleteReserva(Model model , Reserva r) {

        System.out.println("entra aqui 3");

        model.addAttribute("franjasHorarias", franjaHorariaDAO.getFranjaHoraria(r.getEspacioPublico()));
        model.addAttribute("zonas", zonaDAO.getZonas(r.getEspacioPublico()));
        model.addAttribute("reserva", r);

        return "reserva/addesp";
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
        if(r.getHoraAcceso()!=null){
            if(r.getHoraSalida()==null){
                r.setEstado(EstadoReserva.EN_USO);
            }
            else {
                r.setEstado(EstadoReserva.FIN_USO);
            }
        }
        else{
            r.setEstado(EstadoReserva.PENDIENTE_USO);
            r.setHoraSalida(null);
        }
        reservaDAO.updateReserva(r);
        return "redirect:list";
    }

    @RequestMapping(value = "/delete/{numReserva}")
    public String processDeleteReserva(HttpSession session, @PathVariable int numReserva) {

        UserInterno user = (UserInterno) session.getAttribute("user");
        if (user.getRol().equals("gestor")) {
            reservaDAO.updateReservaEstado(numReserva, EstadoReserva.CANCELADA_GESTOR_MUNICIPAL);
        }
        else if(user.getRol().equals("ciudadano")){
            reservaDAO.updateReservaEstado(numReserva, EstadoReserva.CANCELADA_CIUDADANO);
        }
        return "redirect:../list";
    }
}
