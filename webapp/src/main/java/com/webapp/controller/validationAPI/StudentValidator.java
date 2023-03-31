package com.webapp.controller.validationAPI;


import com.webapp.dao.SystemDao;
import com.webapp.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class StudentValidator {

    @Autowired
    private SystemDao dao;


    @PostMapping("/validate/student")
    public String validate(@ModelAttribute Student student, HttpServletRequest request , Model model){
        Student student1;
        try {
            student1 = dao.findStudentByID(student.getId());
        }
        catch (EmptyResultDataAccessException e){
            model.addAttribute("message" , "student not found!");
            return "student-login";
        }
        if (!student.getName().equals(student1.getName())){
            model.addAttribute("message" , "student name is incorrect!!");
            return "student-login";
        }
        request.getSession().setAttribute("isValid" , "true");
        request.getSession().setAttribute("studentID" , student.getId());
        return "forward:/student/loggedIn";
    }




}
