package com.webapp.controller.AdminControllers;

import com.webapp.model.Admin;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AdminMainController {

    @GetMapping("/admin/login")
    public String showLogin(Model model){
        model.addAttribute("admin" , new Admin());
        return"admin-login";
    }

    @PostMapping("/admin/login")
    public String formSubmitResult(@ModelAttribute Admin admin , Model model){
        model.addAttribute("admin" , admin);
        return"forward:/validate/admin";
    }
    @PostMapping("/admin/logged-in")
    public String loggingIn(HttpServletRequest request , Model model){
        if (request.getSession().getAttribute("isLoggedIn").equals("true"))
            return"forward:/admin/services";
        model.addAttribute("message" , "You have to login!");
        return"admin-login";
    }
}
