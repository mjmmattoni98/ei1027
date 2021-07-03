package com.ams.ei1027espaciosnaturales.controller;

import com.ams.ei1027espaciosnaturales.dao.ImagenDAO;
import com.ams.ei1027espaciosnaturales.model.Imagen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/imagen")
public class ImagenController {
    private ImagenDAO imagenDAO;

    @Autowired
    public void setImagenDAO(ImagenDAO imagenDAO) {
        this.imagenDAO = imagenDAO;
    }

    @RequestMapping("/list")
    public String listImagenes(Model model) {
        model.addAttribute("imagenes", imagenDAO.getImagenes());
        return "imagen/list";
    }

    @RequestMapping(value = "/add")
    public String addImagen(Model model) {
        model.addAttribute("imagen", new Imagen());
        return "imagen/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddImagen(@ModelAttribute("imagen") Imagen i,
                                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "imagen/add";
        }
        try {
            imagenDAO.addImagen(i);
        }
        catch (DataAccessException e){
            throw new EspaciosNaturalesException("Error accediendo a la base de datos", "ErrorAccidiendoDatos", "/");
        }
        return "redirect:list";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String updateImagen(Model model, @PathVariable int id) {
        model.addAttribute("imagen", imagenDAO.getImagen(id));
        return "imagen/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("imagen") Imagen i,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "imagen/update";
        imagenDAO.updateImagen(i);
        return "redirect:list";
    }

    @RequestMapping(value = "/delete/{id}")
    public String processDeleteImagen(@PathVariable int id) {
        imagenDAO.deleteImagen(id);
        return "redirect:../list";
    }
}
