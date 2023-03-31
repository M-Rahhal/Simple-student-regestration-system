package com.webservlet;

import com.Dao.StudentDao;
import com.model.Course;
import com.model.StudentCourse;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


@WebServlet(name = "showCoursesServlet", value = "/show_result")

public class ShowCoursesServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!request.getSession().getAttribute("isLoggedIn").equals("true")){
            request.setAttribute("message" , "you have to LOGIN!");
            RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
            rd.forward(request,response);
        }

        if (request.getParameter("value")==null){
            RequestDispatcher rd = request.getRequestDispatcher("showCourses.jsp");
            rd.forward(request,response);
        }

        String courseName =request.getParameter("value");
        String studentID =(String) request.getSession().getAttribute("studentID");
        StudentDao dao = new StudentDao();
        StudentCourse studentCourse;
        try {
            studentCourse = dao.getStudentCourse(courseName ,studentID );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        request.setAttribute("studentCourse" , studentCourse);
        try {
            request.setAttribute("min" , dao.getMinScore(studentCourse.getCourseID()));
            request.setAttribute("max" , dao.getMaxScore(studentCourse.getCourseID()));
            request.setAttribute("avg" , dao.getAvgScore(studentCourse.getCourseID()));
        }
        catch (SQLException sqlException){
            throw new RuntimeException(sqlException);
        }
        RequestDispatcher rd = request.getRequestDispatcher("showResult.jsp");
        rd.forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (!request.getSession().getAttribute("isLoggedIn").equals("true")){
            request.setAttribute("message" , "you have to LOGIN!");
            RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
            rd.forward(request,response);
        }
        StudentDao dao = new StudentDao();

        String name = (String)request.getSession().getAttribute("studentName");
        String id = (String)request.getSession().getAttribute("studentID");
        List<Course> courses;
        try {
            courses = dao.getCourses(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        request.setAttribute("courses" , courses);

        RequestDispatcher rd = request.getRequestDispatcher("showCourses.jsp");
        rd.forward(request,response);

    }
}
