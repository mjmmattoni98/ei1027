package com.ams.ei1027espaciosnaturales.controller;

import com.ams.ei1027espaciosnaturales.dao.ComentarioDAO;
import com.ams.ei1027espaciosnaturales.dao.ZonaDAO;
import com.ams.ei1027espaciosnaturales.model.Comentario;
import com.ams.ei1027espaciosnaturales.model.Zona;
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

@Controller
@RequestMapping("/zona")
public class ZonaController {
    private ZonaDAO zonaDAO;

    @Autowired
    public void setZonaDAO(ZonaDAO z) {
        this.zonaDAO = z;
    }

    @RequestMapping("/list")
    public String listComentarios(Model model) {
        model.addAttribute("zonas", zonaDAO.getZonas());
        return "zona/list";
    }

    @RequestMapping(value = "/add")
    public String addComentario(Model model) {
        model.addAttribute("zona", new Zona());
        return "zona/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddComentario(@ModelAttribute("zona") Zona z,
                                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "zona/add";
        }
        try {
            zonaDAO.addZona(z);
        }
        catch (DataAccessException e){
            throw new EspaciosNaturalesException("Error accediendo a la base de datos", "ErrorAccidiendoDatos", "/");
        }
        return "redirect:list";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String updateComentario(Model model, @PathVariable int id) {
        model.addAttribute("zona", zonaDAO.getZona(id));
        return "zona/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("zona") Zona z,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "zona/update";
        zonaDAO.updateZona(z);
        return "redirect:list";
    }

    @RequestMapping(value = "/delete/{id}")
    public String processDeleteComentario(@PathVariable int id) {
        zonaDAO.deleteZona(id);
        return "redirect:../list";
    }
}
