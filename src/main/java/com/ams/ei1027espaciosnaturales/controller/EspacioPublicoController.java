package com.ams.ei1027espaciosnaturales.controller;

import com.ams.ei1027espaciosnaturales.dao.*;
import com.ams.ei1027espaciosnaturales.model.EspacioPublico;

import com.ams.ei1027espaciosnaturales.model.FranjaHoraria;
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
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/espacioPublico")
public class EspacioPublicoController extends RolController{
    private EspacioPublicoDAO espacioPublicoDAO;
    private GestorMunicipalDAO gestorMunicipalDAO;
    private FranjaHorariaDAO franjaHorariaDAO;
    private ImagenDAO imagenDAO;
    private ComentarioDAO comentarioDAO;

    private static final EspacioPublicoValidator validator = new EspacioPublicoValidator();



    @Autowired
    public void setEspacioPublicoDAO(EspacioPublicoDAO e) {
        this.espacioPublicoDAO = e;
    }

    @Autowired
    public void setComentarioDAO(ComentarioDAO e) {
        this.comentarioDAO = e;
    }

    @Autowired
    public void setFranjaHorariaDAO(FranjaHorariaDAO franjaHorariaDAO) {
        this.franjaHorariaDAO = franjaHorariaDAO;
    }

    @Autowired
    public void setImagenDAO(ImagenDAO imagenDAO) {
        this.imagenDAO = imagenDAO;
    }

    @Autowired
    public void setGestorMunicipalDAO(GestorMunicipalDAO gestorMunicipalDAO) {
        this.gestorMunicipalDAO = gestorMunicipalDAO;
    }

    @RequestMapping("/list")
    public String listEspacioPublico(Model model) {
        model.addAttribute("espaciosPublicos", espacioPublicoDAO.getEspaciosPublicos());
        return "espacioPublico/list";
    }

    @RequestMapping(value = "/add")
    public String addEspacioPublico(HttpSession session, Model model) {
        UserInterno user = checkSession(session, ROL_GESTOR);
        if (user == null){
            model.addAttribute("user", new UserInterno());
            return "login";
        }

        int municipio = gestorMunicipalDAO.getGestorMunicipal(user.getDni()).getIdMunicipio();
        EspacioPublico espacioPublico = new EspacioPublico();
        espacioPublico.setIdMunicipio(municipio);
        model.addAttribute("espacioPublico", espacioPublico);
        return "espacioPublico/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddEspacioPublico(@ModelAttribute("espacioPublico") EspacioPublico e,
                                      BindingResult bindingResult) {
        validator.validate(e, bindingResult);
        if (bindingResult.hasErrors()) {
            return "espacioPublico/add";
        }
        try {
            FranjaHoraria franjaHoraria = new FranjaHoraria();
            franjaHoraria.setEspacioPublico(e.getNombre());
            espacioPublicoDAO.addEspacioPublico(e);
            if(e.getTAcceso().equals("restringido")) {
                String tiempo;
                String tiempo2;
                for (int i = 8; i < 23; i++) {
                    if (i < 10) {
                        tiempo = "0" + Integer.toString(i) + ":00";
                        if (i < 9) {
                            tiempo2 = "0" + Integer.toString(i + 1) + ":00";
                        } else {
                            tiempo2 = Integer.toString(i + 1) + ":00";
                        }
                    } else {
                        tiempo = Integer.toString(i) + ":00";
                        tiempo2 = Integer.toString(i + 1) + ":00";
                    }
                    franjaHoraria.setInicio(LocalTime.parse(tiempo));
                    franjaHoraria.setFin(LocalTime.parse(tiempo2));
                    franjaHorariaDAO.addFranjaHoraria(franjaHoraria);
                }
            }

        }
        catch (DuplicateKeyException exception){
            throw new EspaciosNaturalesException("Ya existe un espacio público con el mismo nombre",
                    "CPDuplicada", "espacioPublico/add");
        }
        catch (DataAccessException exception){
            throw new EspaciosNaturalesException("Error accediendo a la base de datos\n" + exception.getMessage(),
                    "ErrorAccidiendoDatos", "/");
        }
        return "redirect:list";
    }

    // Los siguientes dos metodos gestionan la modificacion de un espacio publico
    @RequestMapping(value = "/update/{nombre}", method = RequestMethod.GET)
    public String updateEspacioPublico(HttpSession session, Model model, @PathVariable String nombre) {
        UserInterno user = checkSession(session, ROL_GESTOR);
        if (user == null){
            model.addAttribute("user", new UserInterno());
            return "login";
        }

        model.addAttribute("espacioPublico", espacioPublicoDAO.getEspacioPublico(nombre));
        List<String> espacios = Arrays.asList("playa", "río", "estanque", "lago", "bosque", "otros");
        model.addAttribute("espacioList", espacios);
        List<String> suelos = Arrays.asList("arena", "roca", "piedra");
        model.addAttribute("sueloList", suelos);
        List<String> accesos = Arrays.asList("abierto", "restringido", "cerrado");
        model.addAttribute("accesoList", accesos);
        return "espacioPublico/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("espacioPublico") EspacioPublico e,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "espacioPublico/update";
        espacioPublicoDAO.updateEspacioPublico(e);
        return "redirect:list";
    }

    @RequestMapping(value = "/delete/{nombre}")
    public String processDeleteEspacioPublico(HttpSession session, Model model, @PathVariable String nombre) {
        UserInterno user = checkSession(session, ROL_GESTOR);
        if (user == null){
            model.addAttribute("user", new UserInterno());
            return "login";
        }

        imagenDAO.deleteImagen(nombre);
        comentarioDAO.deleteComentario(nombre);
        franjaHorariaDAO.deleteFranjaHoraria(nombre);
        espacioPublicoDAO.deleteEspacioPublicoNombre(nombre);
        return "redirect:../list";
    }
}
