package com.ams.ei1027espaciosnaturales.controller;

import com.ams.ei1027espaciosnaturales.dao.*;
import com.ams.ei1027espaciosnaturales.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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

@Controller
@RequestMapping("/reserva")
public class ReservaController extends RolController{
    private ReservaDAO reservaDAO;
    private EspacioPublicoDAO espacioPublicoDAO;
    private FranjaHorariaDAO franjaHorariaDAO;
    private ZonaDAO zonaDAO;
    private EmailDAO emailDAO;
    private CiudadanoDAO ciudadanoDAO;
    private static final ReservaValidator validator = new ReservaValidator();

    @Autowired
    public void setCiudadanoDAO(CiudadanoDAO c) {
        this.ciudadanoDAO = c;
    }

    @Autowired
    public void setEmailDAO(EmailDAO emailDAO) {
        this.emailDAO = emailDAO;
    }

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

        return "reserva/list";
    }

    @RequestMapping("/list/{zona}/{nombre}")
    public String listReservas(HttpSession session, Model model, @PathVariable int zona, @PathVariable String nombre) {
        if (session.getAttribute("user") == null){
            model.addAttribute("user", new UserInterno());
            return "login";
        }
        UserInterno user = (UserInterno) session.getAttribute("user");

        if (user.getRol().equals(ROL_GESTOR)) {
            model.addAttribute("reservas", reservaDAO.getReservasZona(nombre, zona));
        }
        else if(user.getRol().equals(ROL_CIUDADANO)){
            model.addAttribute("reservas", reservaDAO.getReservasCiudadanoZona(user.getDni(), nombre, zona));
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
        model.addAttribute("espaciosPublicos", espacioPublicoDAO.getEspaciosPublicosRestringidos());
        return "reserva/add";
    }

    @RequestMapping(value = "/add")
    public String addReserva(HttpSession session, Model model) {
        UserInterno user = checkSession(session, ROL_CIUDADANO);
        if (user == null){
            model.addAttribute("user", new UserInterno());
            return "login";
        }

        Reserva reserva = new Reserva();
        reserva.setZona(0);
        model.addAttribute("reserva", reserva);
        model.addAttribute("espaciosPublicos", espacioPublicoDAO.getEspaciosPublicosRestringidos());
        return "reserva/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddReserva(HttpSession session, @ModelAttribute("reserva") Reserva r,
                                       BindingResult bindingResult) {
        UserInterno user = (UserInterno) session.getAttribute("user");
        try {
            r.setDni(user.getDni());
            r.setEstado(EstadoReserva.PENDIENTE_USO.getId());
            r.setFechaCreacion(LocalDate.now());

            validator.validate(r, bindingResult);
            if (bindingResult.hasErrors()) {
                return validator.getPath();
            }

            Zona zona = zonaDAO.getZona(r.getZona());
            if(zona.getOcupacion() + r.getNumPersonas() > zona.getCapacidad())
                throw new EspaciosNaturalesException("Número de plazas disponibles insuficientes", "ErrorAccidiendoDatos", "/");
            zonaDAO.updateOcupacionZonas(r.getZona(), zona.getOcupacion() + r.getNumPersonas());

            Ciudadano c = ciudadanoDAO.getCiudadano(user.getDni());

            Email email = new Email();
            email.setRemitente("espacios.naturales@cv.com");
            email.setDestinatario(c.getEmail());
            email.setFecha(LocalDate.now());
            email.setAsunto("Reserva realizada");
            email.setCuerpo("Se ha realizado la reserva en " + r.getEspacioPublico() + " (" + r.getZona() + ") para el día "
                    + r.getFechaAcceso() + " de " + r.getInicioFranjaHoraria() + " a " + r.getFinFranjaHoraria() + ".");

            emailDAO.addEmail(email);
            reservaDAO.addReserva(r);
        }catch (DataAccessException e){
           throw new EspaciosNaturalesException("Error accediendo a la base de datos", "ErrorAccidiendoDatos", "/");
        }
        return "redirect:../list/";
    }

    @RequestMapping(value = "/addesp", method = RequestMethod.POST)
    public String processAddEspacioReserva(Model model , Reserva r) {
        model.addAttribute("franjasHorarias", franjaHorariaDAO.getFranjaHoraria(r.getEspacioPublico()));
        model.addAttribute("zonas", zonaDAO.getZonas(r.getEspacioPublico()));
        model.addAttribute("reserva", r);

        return "reserva/addesp";
    }

    @RequestMapping(value = "/update/{numReserva}", method = RequestMethod.GET)
    public String updateReserva(HttpSession session, Model model, @PathVariable int numReserva) {
        UserInterno user = checkSession(session, ROL_CIUDADANO);
        if (user == null){
            model.addAttribute("user", new UserInterno());
            return "login";
        }

        Reserva reserva = reservaDAO.getReserva(numReserva);
        if (!user.getDni().equals(reserva.getDni()))
            throw new EspaciosNaturalesException("No puedes modificar esta reserva porque no es tuya",
                    "ErrorPertenencia", "/");

        model.addAttribute("reserva", reserva);
        return "reserva/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("reserva") Reserva r,
                                      BindingResult bindingResult) {
        validator.validate(r, bindingResult);
        if (bindingResult.hasErrors()) return "reserva/update";
        if(r.getHoraAcceso()!=null){
            if(r.getHoraSalida()==null){
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
        Reserva reserva = reservaDAO.getReserva(r.getNumReserva());
        Zona zona = zonaDAO.getZona(reserva.getZona());

        if(zona.getOcupacion() + r.getNumPersonas() - reserva.getNumPersonas() > zona.getCapacidad())
            throw new EspaciosNaturalesException("Número de plazas disponibles insuficientes", "ErrorAccidiendoDatos", "/");
        zonaDAO.updateOcupacionZonas(reserva.getZona(), zona.getOcupacion()+r.getNumPersonas()-reserva.getNumPersonas());

        Ciudadano c = ciudadanoDAO.getCiudadano(reserva.getDni());

        Email email = new Email();
        email.setRemitente("espacios.naturales@cv.com");
        email.setDestinatario(c.getEmail());
        email.setFecha(LocalDate.now());
        email.setAsunto("Modificación reserva");
        email.setCuerpo("Se ha modificado la reserva en " + reserva.getEspacioPublico() + " (" + reserva.getZona() + ") para el día " + reserva.getFechaAcceso() + " de " + reserva.getInicioFranjaHoraria() + " a " + reserva.getFinFranjaHoraria() + ".");

        emailDAO.addEmail(email);

        reservaDAO.updateReserva(r);

        return "redirect:list/" + r.getZona() + "/" + r.getEspacioPublico();
    }

    @RequestMapping(value = "/delete/{numReserva}")
    public String processDeleteReserva(HttpSession session, Model model, @PathVariable int numReserva) {
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
        Reserva reserva = reservaDAO.getReserva(numReserva);
        Zona zona = zonaDAO.getZona(reserva.getZona());

        Ciudadano c = ciudadanoDAO.getCiudadano(reserva.getDni());

        Email email = new Email();
        email.setRemitente("espacios.naturales@cv.com");
        email.setDestinatario(c.getEmail());
        email.setFecha(LocalDate.now());
        email.setAsunto("Cancelación reserva");
        email.setCuerpo("Se ha cancelado la reserva en " + reserva.getEspacioPublico() + " (" + reserva.getZona() + ") para el día " + reserva.getFechaAcceso() + " de " + reserva.getInicioFranjaHoraria() + " a " + reserva.getFinFranjaHoraria() + ".");

        emailDAO.addEmail(email);

        zonaDAO.updateOcupacionZonas(reserva.getZona(), zona.getOcupacion() - reserva.getNumPersonas());
        return "redirect:../list/";
    }
}
