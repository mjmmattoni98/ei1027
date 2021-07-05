package com.ams.ei1027espaciosnaturales.controller;

import com.ams.ei1027espaciosnaturales.dao.ZonaDAO;
import com.ams.ei1027espaciosnaturales.model.UserInterno;
import com.ams.ei1027espaciosnaturales.model.Zona;
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
import java.util.List;

@Controller
@RequestMapping("/zona")
public class ZonaController extends RolController{
    private ZonaDAO zonaDAO;
    private static final ZonaValidator validator = new ZonaValidator();

    @Autowired
    public void setZonaDAO(ZonaDAO z) {
        this.zonaDAO = z;
    }

    @RequestMapping("/list/{nombre}")
    public String listZonas(Model model, @PathVariable String nombre) {
        List<Zona> zonas = zonaDAO.getZonas(nombre);


        model.addAttribute("zonas", zonas);
        model.addAttribute("espacio_publico", nombre);
        return "zona/list";
    }

//    @RequestMapping("/main/{id}")
//    public String infoZona(Model model, @PathVariable int id) {
//        Zona zona = zonaDAO.getZona(id);
//        zona.calcularPorcentajeOcupacion();
//        model.addAttribute("zona", zona);
//        return "zona/main";
//    }

    @RequestMapping(value = "/add/{nombre}")
    public String addZona(HttpSession session, Model model, @PathVariable String nombre) {
        UserInterno user = checkSession(session, ROL_GESTOR);
        if (user == null){
            model.addAttribute("user", new UserInterno());
            return "login";
        }

        Zona zona = new Zona();
        zona.setNombreEspacioPublico(nombre);
        model.addAttribute("zona", zona);
        return "zona/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddZona(@ModelAttribute("zona") Zona z,
                                       BindingResult bindingResult) {
        validator.validate(z, bindingResult);
        if (bindingResult.hasErrors()) {
            return "zona/add";
        }
        try {
            zonaDAO.addZona(z);
        }
        catch (DataAccessException e){
            throw new EspaciosNaturalesException("Error accediendo a la base de datos\n" + e.getMessage(),
                    "ErrorAccidiendoDatos", "/");
        }
        return "redirect:list/" + z.getNombreEspacioPublico();
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String updateZona(HttpSession session, Model model, @PathVariable int id) {
        UserInterno user = checkSession(session, ROL_GESTOR);
        if (user == null){
            model.addAttribute("user", new UserInterno());
            return "login";
        }

        Zona zona = zonaDAO.getZona(id);
        model.addAttribute("zona", zona);
        return "zona/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("zona") Zona z,
                                      BindingResult bindingResult) {
        validator.validate(z, bindingResult);
        if (bindingResult.hasErrors()) return "zona/update";
        zonaDAO.updateZona(z);
        return "redirect:list/" + z.getNombreEspacioPublico();
    }

    @RequestMapping(value = "/delete/{id}/{nombre}")
    public String processDeleteZona(HttpSession session, Model model, @PathVariable int id, @PathVariable String nombre) {
        UserInterno user = checkSession(session, ROL_GESTOR);
        if (user == null){
            model.addAttribute("user", new UserInterno());
            return "login";
        }

        zonaDAO.deleteZona(id);
        return "redirect:../../list/" + nombre;
    }
}
