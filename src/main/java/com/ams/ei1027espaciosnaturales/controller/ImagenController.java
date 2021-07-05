package com.ams.ei1027espaciosnaturales.controller;

import com.ams.ei1027espaciosnaturales.dao.ImagenDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/imagen")
public class ImagenController extends RolController{
    private ImagenDAO imagenDAO;

    @Autowired
    public void setImagenDAO(ImagenDAO imagenDAO) {
        this.imagenDAO = imagenDAO;
    }

    @RequestMapping("/list/{nombre}")
    public String listImagenes(Model model, @PathVariable String nombre) {
        model.addAttribute("imagenes", imagenDAO.getImagenes(nombre));
        model.addAttribute("espacio_publico", nombre);
        return "imagen/list";
    }
}
