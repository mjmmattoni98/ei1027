package com.ams.ei1027espaciosnaturales.controller;

import com.ams.ei1027espaciosnaturales.dao.ComentarioDAO;
import com.ams.ei1027espaciosnaturales.model.Comentario;
import com.ams.ei1027espaciosnaturales.model.UserInterno;
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

@Controller
@RequestMapping("/comentario")
public class ComentarioController extends RolController{
    private ComentarioDAO comentarioDAO;

    @Autowired
    public void setComentarioDAO(ComentarioDAO c) {
        this.comentarioDAO = c;
    }

    @RequestMapping("/list/{nombre}")
    public String listComentarios(Model model, @PathVariable String nombre) {
        model.addAttribute("comentarios", comentarioDAO.getComentarios());
        model.addAttribute("espacio_publico", nombre);
        return "comentario/list";
    }

    @RequestMapping(value = "/add/{nombre}")
    public String addComentario(HttpSession session, Model model, @PathVariable String nombre) {
        if(session.getAttribute("user") == null){
            model.addAttribute("user", new UserInterno());
            return "login";
        }

        Comentario comentario = new Comentario();
        comentario.setEspacioPublico(nombre);
        model.addAttribute("comentario", comentario);
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
        return "redirect:list/" + c.getEspacioPublico();
    }

    @RequestMapping(value = "/delete/{id}/{nombre}")
    public String processDeleteComentario(HttpSession session, Model model, @PathVariable int id, @PathVariable String nombre) {
        UserInterno user = checkSession(session, ROL_GESTOR);
        if (user == null){
            model.addAttribute("user", new UserInterno());
            return "login";
        }

        comentarioDAO.deleteComentario(id);
        return "redirect:../../list/" + nombre;
    }
}
