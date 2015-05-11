package com.trainingcenter.controller;

import com.trainingcenter.form.LoginForm;
import com.trainingcenter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Enumeration;

@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    ServletContext servletContext;
    

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String submitForm(@ModelAttribute("loginForm") @Valid LoginForm loginForm, BindingResult result, Model model,
                             HttpSession session) {
        String userLogin = loginForm.getLogin();
        String userPassword = loginForm.getPassword();
        if (result.hasErrors()) {
            model.addAttribute("isValid", false);
            return "login";
        } else if (userService.getUser(userLogin) == null || !userService.getUser(userLogin).getUserPassword().equals(userPassword)) {
            model.addAttribute("isValid", true);
            return "login";
        }
        session.setAttribute("user", userService.getUser(userLogin));
        session.setAttribute("LOGGEDIN_USER", loginForm);
        if (session.getAttribute("redirectPath") != null) {
            return "redirect:" + session.getAttribute("redirectPath");
        }
        return "redirect:courses";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLoginPage(Model model) {
        Enumeration enum1 = servletContext.getInitParameterNames();
        System.out.println("DSf"+ enum1.hashCode());
        while (enum1.hasMoreElements()) {

            System.out.println("Inside");
            String name = (String) enum1.nextElement();
            System.out.println(name + ": " + servletContext.getInitParameter(name));
        }
        model.addAttribute(new LoginForm());
        model.addAttribute("isValid", false);
        return "login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String showLogoutPage() {
        return "logout";
    }

    @RequestMapping(value = "/logoutToLogin", method = RequestMethod.GET)
    public String showLoginPageAfterLogout(HttpSession session) {
        session.invalidate();
        return "redirect:login";
    }
}
