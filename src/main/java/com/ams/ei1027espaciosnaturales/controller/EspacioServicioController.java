package com.ams.ei1027espaciosnaturales.controller;

import com.ams.ei1027espaciosnaturales.dao.EspacioServicioEstacionalDAO;
import com.ams.ei1027espaciosnaturales.dao.EspacioServicioPermanenteDAO;
import com.ams.ei1027espaciosnaturales.dao.ServicioDAO;
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
import java.time.LocalTime;

@Controller
@RequestMapping("/espacios_servicios")
public class EspacioServicioController extends RolController{
    private EspacioServicioEstacionalDAO estacionalDAO;
    private EspacioServicioPermanenteDAO permanenteDAO;
    private ServicioDAO servicioDAO;
    private static final EspacioServcicioEstacionalValidator validator = new EspacioServcicioEstacionalValidator();

    @Autowired
    public void setServicioDAO(ServicioDAO ese) {
        this.servicioDAO = ese;
    }

    @Autowired
    public void setEstacionalDAO(EspacioServicioEstacionalDAO ese) {
        this.estacionalDAO = ese;
    }

    @Autowired
    public void setPermanenteDAO(EspacioServicioPermanenteDAO esp) {
        this.permanenteDAO = esp;
    }

    @RequestMapping("/list/{nombre}")
    public String listEspacioServicios(Model model, @PathVariable String nombre) {
        model.addAttribute("espacios_servicios_estacionales", estacionalDAO.getEspaciosServiciosEstacionales(nombre));
        model.addAttribute("espacios_servicios_permanentes", permanenteDAO.getEspaciosServiciosPermanentes(nombre));
        model.addAttribute("espacio_publico", nombre);
        return "espacios_servicios/list";
    }

    @RequestMapping(value = "/add_permanente/{espacioPublico}")
    public String addEspacioServicioPermanente(HttpSession session, Model model, @PathVariable String espacioPublico) {
        UserInterno user = checkSession(session, ROL_GESTOR);
        if (user == null){
            model.addAttribute("user", new UserInterno());
            return "login";
        }
        model.addAttribute("servicio", new Servicio());
        model.addAttribute("espacioPublico", espacioPublico);
        model.addAttribute("tipo", "Permanente");
        model.addAttribute("servicios", servicioDAO.getServiciosNoEspacios(espacioPublico, "Permanente"));
        return "espacios_servicios/add";
    }

    @RequestMapping(value = "/add_estacional/{espacioPublico}")
    public String addEspacioServicioEstacional(HttpSession session, Model model, @PathVariable String espacioPublico) {
        UserInterno user = checkSession(session, ROL_GESTOR);
        if (user == null){
            model.addAttribute("user", new UserInterno());
            return "login";
        }
        model.addAttribute("servicio", new EspacioServicioEstacional());
        model.addAttribute("espacioPublico", espacioPublico);
        model.addAttribute("tipo", "Estacional");
        model.addAttribute("servicios", servicioDAO.getServiciosNoEspacios(espacioPublico, "Estacional"));
        return "espacios_servicios/add2";
    }

    @RequestMapping(value = "/add/permanente/{espacioPublico}", method = RequestMethod.POST)
    public String processAddServicio(@ModelAttribute("servicio") Servicio s,
                                    BindingResult bindingResult,  @PathVariable String espacioPublico) {
        if (bindingResult.hasErrors()) {
            return "espacios_servicios/add/permanente/"+espacioPublico;
        }
        try {
                EspacioServicioPermanente esp = new EspacioServicioPermanente();
                esp.setEspacioPublico(espacioPublico);
                esp.setTipo(s.getTipo());
                esp.setDescripcion(servicioDAO.getServicio(s.getTipo(),"Permanente").getDescripcion());
                permanenteDAO.addEspacioServicioPermanente(esp);

        }catch (DataAccessException e){
            throw new EspaciosNaturalesException("Error accediendo a la base de datos", "ErrorAccidiendoDatos", "/");
        }
        return "redirect:../../list/"+espacioPublico;
    }

    @RequestMapping(value = "/add/estacional/{espacioPublico}", method = RequestMethod.POST)
    public String processAddServicioe(@ModelAttribute("servicio") EspacioServicioEstacional s,
                                     BindingResult bindingResult,  @PathVariable String espacioPublico) {
        validator.validate(s, bindingResult);
        if (bindingResult.hasErrors()) {
            System.out.println("error");

            for(ObjectError error : bindingResult.getAllErrors()){
                System.out.println(error);
            }
            return "espacios_servicios/add/estacional/"+espacioPublico;
        }
        try {
            estacionalDAO.addEspacioServicioEstacional(s);

        }catch (DataAccessException e){
            throw new EspaciosNaturalesException("Error accediendo a la base de datos", "ErrorAccidiendoDatos", "/");
        }
        return "redirect:../../list/"+espacioPublico;
    }

    @RequestMapping(value = "/delete_permanente/{nombre}/{tipo}")
    public String processDeleteEspacioServicioPermanente(HttpSession session, Model model, @PathVariable String nombre,
                                                         @PathVariable String tipo) {
        UserInterno user = checkSession(session, ROL_GESTOR);
        if (user == null){
            model.addAttribute("user", new UserInterno());
            return "login";
        }

        permanenteDAO.deleteEspacioServicioPermanente(nombre, tipo);
        return "redirect:../../list/" + nombre;
    }

    @RequestMapping(value = "/delete_estacional/{nombre}/{tipo}/{fechaIni}/{horaIni}")
    public String processDeleteEspacioServicioEstacional(HttpSession session, Model model, @PathVariable String nombre,
                                                         @PathVariable String tipo, @PathVariable String fechaIni,
                                                         @PathVariable String horaIni) {
        UserInterno user = checkSession(session, ROL_GESTOR);
        if (user == null){
            model.addAttribute("user", new UserInterno());
            return "login";
        }

        estacionalDAO.deleteEspacioServicioEstacional(nombre, tipo,  LocalDate.parse(fechaIni), LocalTime.parse(horaIni));
        return "redirect:../../../../list/" + nombre;
    }
}
