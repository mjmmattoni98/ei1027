package com.ams.ei1027espaciosnaturales.controller;

import com.ams.ei1027espaciosnaturales.dao.ImagenDAO;
import com.ams.ei1027espaciosnaturales.model.Imagen;
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
@RequestMapping("/imagen")
public class ImagenController {
    private ImagenDAO imagenDAO;

    @Autowired
    public void setImagenDAO(ImagenDAO imagenDAO) {
        this.imagenDAO = imagenDAO;
    }

    @RequestMapping("/list")
    public String listComentarios(Model model) {
        model.addAttribute("imagenes", imagenDAO.getImagenes());
        return "imagen/list";
    }

    @RequestMapping(value = "/add")
    public String addComentario(Model model) {
        model.addAttribute("imagen", new Imagen());
        return "imagen/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddComentario(@ModelAttribute("imagen") Imagen i,
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
    public String updateComentario(Model model, @PathVariable int id) {
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
    public String processDeleteComentario(@PathVariable int id) {
        imagenDAO.deleteImagen(id);
        return "redirect:../list";
    }
}
