package com.veeru.Controller;
import com.veeru.Model.*;

import com.veeru.Service.DBConnection;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.*;

@Controller
public class AdminController {

    @RequestMapping(value="/adminEdit" , method = RequestMethod.POST)
    public String showEdit(@ModelAttribute("admin") Admin admin, Model model) {
        String username = admin.getUsername();
        String password = admin.getPassword();
        Connection con = null;
        try {
            con =  DBConnection.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from admin where username = '" + username + "' and password = '" + password + "'");
            if(rs.next()) {
                con.close();
                return "redirect:/driveLogin";
            }
            con.close();
        }
        catch (Exception e) {
            System.out.println(e);
        }
        model.addAttribute("error","Username OR Password is incorrect");
        return "admin";

    }
}
