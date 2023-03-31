package com.webservlet;

import com.Dao.StudentDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "loginServlet", value = "/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
        rd.forward(request,response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        StudentDao dao = new StudentDao();


        String name = request.getParameter("studentName");
        String id = request.getParameter("studentID");
         if (!dao.isValidUser(name , id)){
             request.setAttribute("message" , "invalid");
             RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
             rd.forward(request,response);
         }

         request.removeAttribute("message");
         request.getSession().setAttribute("isLoggedIn" , "true");
        request.getSession().setAttribute("studentName" , name);
        request.getSession().setAttribute("studentID" , id);
        RequestDispatcher rd = request.getRequestDispatcher("show_result");
        rd.forward(request,response);

    }


}

