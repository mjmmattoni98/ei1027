package com.ams.ei1027espaciosnaturales.controller;

import com.ams.ei1027espaciosnaturales.dao.ComentarioDAO;
import com.ams.ei1027espaciosnaturales.model.Comentario;
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
@RequestMapping("/comentario")
public class ComentarioController {
    private ComentarioDAO comentarioDAO;

    @Autowired
    public void setComentarioDAO(ComentarioDAO c) {
        this.comentarioDAO = c;
    }

    @RequestMapping("/list")
    public String listComentarios(Model model) {
        model.addAttribute("comentarios", comentarioDAO.getComentarios());
        return "comentario/list";
    }

    @RequestMapping(value = "/add")
    public String addComentario(Model model) {
        model.addAttribute("comentario", new Comentario());
        return "comentario/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddComentario(@ModelAttribute("comentario") Comentario c,
                                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "comentario/add";
        }
        try {
            comentarioDAO.addComentario(c);
        }
        catch (DataAccessException e){
            throw new EspaciosNaturalesException("Error accediendo a la base de datos", "ErrorAccidiendoDatos", "/");
        }
        return "redirect:list";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String updateComentario(Model model, @PathVariable int id) {
        model.addAttribute("comentario", comentarioDAO.getComentario(id));
        return "comentario/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("comentario") Comentario c,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "comentario/update";
        comentarioDAO.updateComentario(c);
        return "redirect:list";
    }

    @RequestMapping(value = "/delete/{id}")
    public String processDeleteComentario(@PathVariable int id) {
        comentarioDAO.deleteComentario(id);
        return "redirect:../list";
    }
}
