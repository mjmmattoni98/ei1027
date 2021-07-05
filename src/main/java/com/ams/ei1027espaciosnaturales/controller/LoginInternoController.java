package com.ams.ei1027espaciosnaturales.controller;

import com.ams.ei1027espaciosnaturales.dao.UserDAO;
import com.ams.ei1027espaciosnaturales.model.UserInterno;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

class UserInternoValidator implements Validator {
    @Override
    public boolean supports(@NotNull Class<?> cls) {
        return UserInterno.class.isAssignableFrom(cls);
    }

    @Override
    public void validate(@NotNull Object o, @NotNull Errors errors) {

        UserInterno user = (UserInterno) o;
        if (user.getUsername().length() == 0) {
            errors.rejectValue("username", "campo usuario vacio", "Introduce un usuario");
        }
        if (user.getPassword().length() == 0) {
            errors.rejectValue("password", "campo contraseña vacio", "Introduce una contraseña");
        }
    }
}

@Controller
public class LoginInternoController {

    @Autowired
    private UserDAO userDao;

    @RequestMapping("/login")
    public String login(Model model, HttpSession session) {
        if (session.getAttribute("user") == null){
            model.addAttribute("user", new UserInterno());
            return "login";
        }
        UserInterno user = (UserInterno) session.getAttribute("user");
        throw new EspaciosNaturalesException("Ya estás logueado", "AlreadyLogIn", "../" + user.getUrlMainPage());
    }

    @RequestMapping(value="/login", method= RequestMethod.POST)
    public String checkLogin(@ModelAttribute("user") UserInterno user,
                             BindingResult bindingResult, HttpSession session) {
        UserInternoValidator userValidator = new UserInternoValidator();
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "login";
        }
        // Comprobar que el login es el correcto intentando cargar el usuario
        user = userDao.loadUserByUsername(user.getUsername(), user.getPassword());
        if (user == null) {
            bindingResult.rejectValue("password", "badpw", "Contrasenya incorrecta");
            return "login";
        }
        // Autenticado correctamente. Guardamos los datos en la sesión
        session.setAttribute("user", user);

        String nextUrl = user.getUrlMainPage();

        return "redirect:/" + nextUrl;
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
