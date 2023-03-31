package com.webapp.controller.AdminControllers;


import com.webapp.dao.SystemDao;
import com.webapp.model.Course;
import com.webapp.model.Student;
import com.webapp.model.StudentCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class AdminServicesController {

    @Autowired
    private SystemDao dao;

    @PostMapping("/admin/services")
    public String showServices(HttpServletRequest httpServletRequest , Model model){
        if (httpServletRequest.getSession().getAttribute("isLoggedIn")==null) {
            model.addAttribute("message", "You have to login!");
            return "admin-login";
        }
        return "admin-services";
    }

    @GetMapping("/admin/services/")
    public String showService(HttpServletRequest request , Model model){
        if (request.getSession().getAttribute("isLoggedIn")==null){
            model.addAttribute("message", "You have to login!");
            return "admin-login";
        }
        if (request.getParameter("value")==null){
            return "admin-services";
        }
        switch (request.getParameter("value")){
            case "add-student":
                model.addAttribute("student" , new Student());
                break;
            case "add-course":
                model.addAttribute("course" , new Course());
                break;
            case "add-course-to-a-student":
                model.addAttribute("studentCourse" , new StudentCourse());
                break;
            case "list-students":
                return"forward:/admin/list-students";
            case "list-courses":
                return"forward:/admin/list-courses";
            default:
                model.addAttribute("message" , "service not found");
                return "admin-services";
        }
        return "/admin-services/"+request.getParameter("value");
    }

    @PostMapping("/admin/add-student")
    public String addStudent(@ModelAttribute Student student ,  HttpServletRequest request , Model model){
        if (request.getSession().getAttribute("isLoggedIn")==null) {
            model.addAttribute("message", "You have to login!");
            return "admin-login";
        }
        dao.addStudent(student);
        model.addAttribute("message" , "student added!");
        return "admin-services";
    }

    @PostMapping("/admin/add-course")
    public String addCourse(@ModelAttribute Course course ,  HttpServletRequest request , Model model){
        if (request.getSession().getAttribute("isLoggedIn")==null) {
            model.addAttribute("message", "You have to login!");
            return "admin-login";
        }
        dao.addCourse(course);
        model.addAttribute("message" , "course added!");
        return "admin-services";
    }


    @PostMapping("/admin/add-course-to-a-student")
    public String addCourseToAStudent(@ModelAttribute StudentCourse studentCourse ,  HttpServletRequest request , Model model){
        if (request.getSession().getAttribute("isLoggedIn")==null) {
            model.addAttribute("message", "You have to login!");
            return "admin-login";
        }
        try {
            Student student = dao.findStudentByID(studentCourse.getStudentID());
            Course course = dao.findCourseByID(studentCourse.getCourseID());
        }
        catch (EmptyResultDataAccessException e ){
            model.addAttribute("message" , "course was not added!\n either the course id or the student id is not valid");
            return "admin-services";
        }
        dao.addCourseToAStudent(studentCourse.getStudentID() , studentCourse.getCourseID(),studentCourse.getGrade());
        model.addAttribute("message" , "course added to the student "+studentCourse.getStudentID()+" !");
        return "admin-services";
    }

    @GetMapping("/admin/list-students")
    public String listStudents(HttpServletRequest request , Model model){
        if (request.getSession().getAttribute("isLoggedIn")==null) {
            model.addAttribute("message", "You have to login!");
            return "admin-login";
        }
        List<Student> list;
        try {
            list = dao.findAllStudents();
        }
        catch (EmptyResultDataAccessException e ){
            model.addAttribute("message" , "there are no students!");
            return "admin-services";
        }
        model.addAttribute("students" , list);
        return"/admin-services/list-students";
    }
    @GetMapping("/admin/list-courses")
    public String listCourses(HttpServletRequest request , Model model){
        if (request.getSession().getAttribute("isLoggedIn")==null) {
            model.addAttribute("message", "You have to login!");
            return "admin-login";
        }
        List<Course> list;
        try {
            list = dao.findAllCourses();
        }
        catch (EmptyResultDataAccessException e ){
            model.addAttribute("message" , "there are no courses!");
            return "admin-services";
        }
        model.addAttribute("courses" , list);
        return"/admin-services/list-courses";
    }



}
