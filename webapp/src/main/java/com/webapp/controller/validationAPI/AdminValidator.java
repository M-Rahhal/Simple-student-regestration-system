package com.webapp.controller.validationAPI;


import com.webapp.model.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;

@Controller
public class AdminValidator {


    @Autowired
    JdbcTemplate jdbcTemplate;

    @PostMapping("/validate/admin")
    public String validate(@ModelAttribute Admin admin , Model model , HttpServletRequest request){
        try {
            Admin admin1 = getAdminPassword(admin.getUserName());
            request.getSession().setAttribute("isLoggedIn" , "true");
            request.getSession().setAttribute("username" , admin.getUserName());
            return "forward:/admin/logged-in";
        }
        catch (EmptyResultDataAccessException e) {
            model.addAttribute("message", "Wrong input");
            return "admin-login";
        }
    }

    private Admin getAdminPassword(String userName){

        Admin admin = jdbcTemplate.queryForObject("SELECT * FROM admin WHERE username=?", new RowMapper<Admin>() {
            @Override
            public Admin mapRow(ResultSet rs, int rowNum) throws SQLException {
                Admin admin = new Admin();
                admin.setUserName(rs.getString("username"));
                admin.setPassword(rs.getString("password"));
                return admin;
            }
        } , userName);
        return admin;
    }
}
