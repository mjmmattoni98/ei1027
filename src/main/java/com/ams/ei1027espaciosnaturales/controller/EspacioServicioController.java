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
        model.addAttribute("servicio", new Servicio());
        model.addAttribute("espacioPublico", espacioPublico);
        model.addAttribute("tipo", "Estacional");
        model.addAttribute("servicios", servicioDAO.getServiciosNoEspacios(espacioPublico, "Estacional"));
        return "espacios_servicios/add";
    }

    @RequestMapping(value = "/add/{espacioPublico}", method = RequestMethod.POST)
    public String processAddServicio(@ModelAttribute("servicio") Servicio s,
                                    BindingResult bindingResult,  @PathVariable String espacioPublico) {
        if (bindingResult.hasErrors()) {
            return "espacios_servicios/add/"+espacioPublico;
        }
        try {
            if(s.getSupertipo().equals("Permanente")) {
                EspacioServicioPermanente esp = new EspacioServicioPermanente();
                esp.setEspacioPublico(espacioPublico);
                esp.setTipo(s.getTipo());
                esp.setDescripcion(servicioDAO.getServicio(s.getTipo(),"Permanente").getDescripcion());
                permanenteDAO.addEspacioServicioPermanente(esp);
            }
            else{

            }

        }catch (DataAccessException e){
            throw new EspaciosNaturalesException("Error accediendo a la base de datos", "ErrorAccidiendoDatos", "/");
        }
        return "redirect:../list/"+espacioPublico;
    }


//    @RequestMapping(value = "/add", method = RequestMethod.POST)
//    public String processAddEspacioServicioPermanente(@ModelAttribute("espacios_servicios") EspacioServicioEstacional ese,
//                                                      BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return "espacios_servicios/add";
//        }
//        try {
//            espacioServicioEstacionalDAO.addEspacioServicioEstacional(ese);
//        }
//        catch (DuplicateKeyException e){
//            throw new EspaciosNaturalesException("Ya existe el servicio estacional en el esapcio público",
//                    "CPDuplicada", "espacio_servicio_estacional/add");
//        }
//        catch (DataAccessException e){
//            throw new EspaciosNaturalesException("Error accediendo a la base de datos", "ErrorAccidiendoDatos", "/");
//        }
//        return "redirect:list";
//    }
//
//    @RequestMapping(value = "/update/{nombre}/{tipo}/{fechaIni}/{horaIni}", method = RequestMethod.GET)
//    public String updateEspacioServicio(Model model, @PathVariable String nombre, @PathVariable String tipo,
//                                                  @PathVariable LocalDate fechaIni, @PathVariable LocalTime horaIni) {
//        model.addAttribute("espacio_servicio_estacional",
//                espacioServicioEstacionalDAO.getEspacioServicioEstacional(nombre, tipo, fechaIni, horaIni));
//        return "espacios_servicios/update";
//    }
//
//    @RequestMapping(value = "/update", method = RequestMethod.POST)
//    public String processUpdateSubmit(@ModelAttribute("espacios_servicios") EspacioServicioEstacional ese,
//                                      BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) return "espacios_servicios/update";
//        espacioServicioEstacionalDAO.updateEspacioServicioEstacional(ese);
//        return "redirect:list";
//    }

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
