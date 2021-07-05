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

//    @RequestMapping(value = "/add")
//    public String addImagen(Model model) {
//        model.addAttribute("imagen", new Imagen());
//        return "imagen/add";
//    }
//
//    @RequestMapping(value = "/add", method = RequestMethod.POST)
//    public String processAddImagen(@ModelAttribute("imagen") Imagen i,
//                                       BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return "imagen/add";
//        }
//        try {
//            imagenDAO.addImagen(i);
//        }
//        catch (DataAccessException e){
//            throw new EspaciosNaturalesException("Error accediendo a la base de datos", "ErrorAccidiendoDatos", "/");
//        }
//        return "redirect:list";
//    }
//
//    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
//    public String updateImagen(Model model, @PathVariable int id) {
//        model.addAttribute("imagen", imagenDAO.getImagen(id));
//        return "imagen/update";
//    }
//
//    @RequestMapping(value = "/update", method = RequestMethod.POST)
//    public String processUpdateSubmit(@ModelAttribute("imagen") Imagen i,
//                                      BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) return "imagen/update";
//        imagenDAO.updateImagen(i);
//        return "redirect:list";
//    }
//
//    @RequestMapping(value = "/delete/{id}")
//    public String processDeleteImagen(@PathVariable int id) {
//        imagenDAO.deleteImagen(id);
//        return "redirect:../list";
//    }
}
