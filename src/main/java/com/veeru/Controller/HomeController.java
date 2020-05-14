package com.veeru.Controller;

import com.veeru.Model.Admin;
import com.veeru.Model.QuestionPaper;
import com.veeru.Model.Subject;
import com.veeru.Service.DBConnection;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    @RequestMapping(value = "/" , method = RequestMethod.GET)
    public String home(Model model) {
        model.addAttribute("questionpaper",new QuestionPaper());
        return "home";
    }

    @RequestMapping(value = "/adminLogin" , method = RequestMethod.GET)
    public String showAdmin(Model model) {
        model.addAttribute("admin",new Admin());
        return "admin";
    }


    @ModelAttribute("semesterList")
    public Map<String, String> getSemesterList() {
        Map<String, String> semesterList = new HashMap<String, String>();
        semesterList.put("1", "FIRST");
        semesterList.put("2", "SECOND");
        semesterList.put("3", "THIRD");
        semesterList.put("4", "FOURTH");
        semesterList.put("5", "FIFTH");
        return semesterList;
    }


    @RequestMapping(value="/populateSubjects",method = RequestMethod.POST)
    public @ResponseBody List<Subject> populateSubjects(@RequestParam(value="semester") String semester) {
        Connection con;
        List<Subject> subjectList = new ArrayList<Subject>();
        try {
            con = DBConnection.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select * from subjects where semester = '" + semester + "'");
            while(rs.next()) {
                Subject subject = new Subject();
                subject.setSubjectCode(rs.getString("subjectCode"));
                subject.setSubjectName(rs.getString("subjectName"));
                subjectList.add(subject);
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }

        return subjectList;
    }
}
