package com.webapp.controller.StudentControllers;

import com.webapp.dao.SystemDao;
import com.webapp.model.Course;
import com.webapp.model.Student;
import com.webapp.model.StudentCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class StudentController {
    @Autowired
    private SystemDao dao;

    @GetMapping("/student/login")
    public String showLogin(Model model){
        model.addAttribute("student" , new Student());
        return "student-login";
    }

    @PostMapping("/student/login")
    public String validation(@ModelAttribute Student student ,Model model, HttpServletRequest request){
        model.addAttribute("student" , student);
        return "forward:/validate/student";
    }

    @PostMapping("/student/loggedIn")
    public String showCourses(HttpServletRequest request ,Model model){
        if (request.getSession().getAttribute("isValid")==null){
            model.addAttribute("message" , "you have to login!");
            return "student-login";
        }
        List<Course> courses = dao.findAllCoursesForAStudent((String)request.getSession().getAttribute("studentID"));
        model.addAttribute("courses" , courses);
        return "student-list-courses";
    }
    @GetMapping("/show_course_details")
    public String showCourseDetails(HttpServletRequest request , Model model){
        if (request.getSession().getAttribute("isValid")==null){
            model.addAttribute("message" , "you have to login!");
            return "student-login";
        }
        String courseID = request.getParameter("value");
        String studentID =(String) request.getSession().getAttribute("studentID");
        int max = dao.getMaxGrade(courseID);
        int min = dao.getMinGrade(courseID);
        double avg = dao.getAvgGrade(courseID);
        int grade = dao.getStudentGrade(courseID , studentID);
        model.addAttribute("max",max);
        model.addAttribute("min",min);
        model.addAttribute("avg",avg);
        model.addAttribute("grade",grade);
        return "show-result";
    }

}
