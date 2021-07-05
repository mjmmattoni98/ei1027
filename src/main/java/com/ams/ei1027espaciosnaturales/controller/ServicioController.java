package com.ams.ei1027espaciosnaturales.controller;

import com.ams.ei1027espaciosnaturales.dao.ServicioDAO;
import com.ams.ei1027espaciosnaturales.model.Servicio;
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
@RequestMapping("/servicios")
public class ServicioController extends RolController{
    private ServicioDAO servicioDAO;

    @Autowired
    public void setMunicipioDAO(ServicioDAO m) {
        this.servicioDAO = m;
    }

    @RequestMapping("/list")
    public String listServicios(HttpSession session, Model model) {
        model.addAttribute("servicios", servicioDAO.getServicios());
        return "servicios/list";
    }

//    @RequestMapping(value = "/add")
//    public String addServicio(Model model) {
//        model.addAttribute("servicio", new Servicio());
//        return "servicios/add";
//    }
//
//    @RequestMapping(value = "/add", method = RequestMethod.POST)
//    public String processAddServicio(@ModelAttribute("servicio") Servicio s,
//                                      BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return "servicios/add";
//        }
//        try {
//            servicioDAO.addServicio(s);
//        }
//        catch (DuplicateKeyException e){
//            throw new EspaciosNaturalesException("Ya existe el servicio", "CPDuplicada", "servicios/add");
//        }
//        catch (DataAccessException e){
//            throw new EspaciosNaturalesException("Error accediendo a la base de datos", "ErrorAccidiendoDatos", "/");
//        }
//        return "redirect:list";
//    }

    @RequestMapping(value = "/update/{tipo}/{supertipo}", method = RequestMethod.GET)
    public String updateServicio(HttpSession session, Model model, @PathVariable String tipo, @PathVariable String supertipo) {
        UserInterno user = checkSession(session, ROL_GESTOR);
        if (user == null){
            model.addAttribute("user", new UserInterno());
            return "login";
        }
        Servicio s = servicioDAO.getServicio(tipo, supertipo);
        s.setSupertipo(supertipo);
        model.addAttribute("servicio", s);
        return "servicios/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("servicio") Servicio s,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "servicios/update";
        servicioDAO.updateServicio(s);
        return "redirect:list";
    }

//    @RequestMapping(value = "/delete/{tipo}/{supertipo}")
//    public String processDeleteServicio(@PathVariable String tipo, @PathVariable String supertipo, HttpSession session) {
//        servicioDAO.deleteServicio(tipo, supertipo);
//        UserInterno user = (UserInterno) session.getAttribute("user");
//        if(user.getRol().equals("gestor")){
//            return "redirect:../../../responsable/anadirServicios";
//        }
//        return "redirect:../../list";
//    }
}
