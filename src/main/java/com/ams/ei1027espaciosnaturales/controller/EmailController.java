package com.ams.ei1027espaciosnaturales.controller;

import com.ams.ei1027espaciosnaturales.dao.EmailDAO;
import com.ams.ei1027espaciosnaturales.dao.MunicipioDAO;
import com.ams.ei1027espaciosnaturales.model.Email;
import com.ams.ei1027espaciosnaturales.model.Municipio;
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
@RequestMapping("/email")
public class EmailController {
    private EmailDAO emailDAO;

    @Autowired
    public void setEmailDAO(EmailDAO e) {
        this.emailDAO = e;
    }

    //TODO comprobar como hacer para pasar el email del destinatario para listar los emails
    @RequestMapping("/list/{destinatario}")
    public String listEmails(Model model, @PathVariable String destinatario) {
        model.addAttribute("emails", emailDAO.getEmails(destinatario));
        return "email/list";
    }

    @RequestMapping(value = "/add")
    public String addEmail(Model model) {
        model.addAttribute("email", new Email());
        return "email/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddEmail(@ModelAttribute("email") Email e,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "email/add";
        }
        try {
            emailDAO.addEmail(e);
        }
        catch (DataAccessException exception){
            throw new EspaciosNaturalesException("Error accediendo a la base de datos", "ErrorAccidiendoDatos", "/");
        }
        return "redirect:list";
    }

    // Los siguientes dos metodos gestionan la modificacion de un municipio
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String updateEmail(Model model, @PathVariable int id) {
        model.addAttribute("email", emailDAO.getEmail(id));
        return "email/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("email") Email e,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "email/update";
        emailDAO.updateEmail(e);
        return "redirect:list";
    }

    @RequestMapping(value = "/delete/{id}")
    public String processDeleteEmail(@PathVariable int id) {
        emailDAO.deleteEmail(id);
        return "redirect:../list";
    }
}
