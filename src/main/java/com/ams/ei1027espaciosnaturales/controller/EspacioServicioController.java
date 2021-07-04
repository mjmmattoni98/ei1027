package com.ams.ei1027espaciosnaturales.controller;

import com.ams.ei1027espaciosnaturales.dao.EspacioPublicoDAO;
import com.ams.ei1027espaciosnaturales.dao.EspacioServicioEstacionalDAO;
import com.ams.ei1027espaciosnaturales.dao.EspacioServicioPermanenteDAO;
import com.ams.ei1027espaciosnaturales.model.EspacioServicioEstacional;
import com.ams.ei1027espaciosnaturales.model.EspacioServicioPermanente;
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
import java.time.LocalDate;
import java.time.LocalTime;

@Controller
@RequestMapping("/espacios_servicios")
public class EspacioServicioController extends RolController{
    private EspacioServicioEstacionalDAO estacionalDAO;
    private EspacioServicioPermanenteDAO permanenteDAO;

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

    @RequestMapping(value = "/add_permanente/{tipo}")
    public String addEspacioServicioPermanente(HttpSession session, Model model, @PathVariable String tipo) {
        UserInterno user = checkSession(session, rolGestor);
        if (user == null){
            model.addAttribute("user", new UserInterno());
            return "login";
        }

        model.addAttribute("permanente", permanenteDAO.getEspaciosSinServicio(tipo));
        return "espacios_servicios/add";
    }

    @RequestMapping(value = "/add_estacional/{tipo}")
    public String addEspacioServicioEstacional(HttpSession session, Model model, @PathVariable String tipo) {
        UserInterno user = checkSession(session, rolGestor);
        if (user == null){
            model.addAttribute("user", new UserInterno());
            return "login";
        }

        model.addAttribute("estacional", estacionalDAO.getEspaciosSinServicio(tipo));
        return "espacios_servicios/add";
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
        UserInterno user = checkSession(session, rolGestor);
        if (user == null){
            model.addAttribute("user", new UserInterno());
            return "login";
        }

        permanenteDAO.deleteEspacioServicioPermanente(nombre, tipo);
        return "redirect:../../list/" + nombre;
    }

    //TODO no va bien
    @RequestMapping(value = "/delete_estacional/{nombre}/{tipo}/{fechaIni}/{horaIni}")
    public String processDeleteEspacioServicioEstacional(HttpSession session, Model model, @PathVariable String nombre,
                                                         @PathVariable String tipo, @PathVariable String fechaIni,
                                                         @PathVariable String horaIni) {
        UserInterno user = checkSession(session, rolGestor);
        if (user == null){
            model.addAttribute("user", new UserInterno());
            return "login";
        }

        estacionalDAO.deleteEspacioServicioEstacional(nombre, tipo,  LocalDate.parse(fechaIni), LocalTime.parse(horaIni));
        return "redirect:../../../../list/" + nombre;
    }
}
