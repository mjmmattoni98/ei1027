package com.ams.ei1027espaciosnaturales.controller;

import com.ams.ei1027espaciosnaturales.dao.UserDAO;
import com.ams.ei1027espaciosnaturales.model.UserInterno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class UserInternoController {

    private UserDAO userDAO;

    @Autowired
    public void setUserInternoDAO(UserDAO userDao) {
        this.userDAO = userDao;
    }

    @RequestMapping("/list")
    public String listUsuariosInternos(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            model.addAttribute("user", new UserInterno());

            session.setAttribute("nextUrl", "/empleados/list");

            return "login/empleados";
        }
        model.addAttribute("users", userDAO.listAllUsers());
        return "login/list";
    }

}
