package com.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/")
public class MainController {


    @GetMapping("/")
    public String showHomepage(){
        return "home";
    }

    @GetMapping("/admin")
    public String redirectToAdminController(){
        return "redirect:/admin/login";
    }


    @GetMapping("/student")
    public String redirectToStudentController(){
        return "redirect:/student/login";
    }
}
