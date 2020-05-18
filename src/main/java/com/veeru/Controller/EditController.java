package com.veeru.Controller;

import com.veeru.Model.AddSubject;
import com.veeru.Model.ChangeSemester;
import com.veeru.Model.DeleteSubject;
import com.veeru.Model.UploadPaper;
import com.veeru.Service.DBConnection;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Connection;
import java.sql.Statement;

@Controller
public class EditController {

    @RequestMapping(value = "changeSemester")
    public String changeSemester(@ModelAttribute("changeSemester")ChangeSemester changeSemester, Model model) {
        String oldSemester = changeSemester.getOldSemester();
        String newSemester = changeSemester.getNewSemester();
        String subject = changeSemester.getChangeSubject();
        Connection connection;
        try {
            connection = DBConnection.getConnection();
            Statement statement = connection.createStatement();
            int query = statement.executeUpdate("update subjects set semester = '"+ newSemester +"' where subjectCode = '"+ subject +"'");
            if(query > 0) {
                model.addAttribute("success","Changed successfully!");
            }
            else {
                model.addAttribute("error","Error Occurred!");
            }
        }
        catch (Exception e) {

        }
        model.addAttribute("uploadPaper",new UploadPaper());
        model.addAttribute("addSubject",new AddSubject());
        model.addAttribute("deleteSubject",new DeleteSubject());
        return "edit";
    }

    @RequestMapping(value = "addSubject")
    public String addSubject(@ModelAttribute("addSubject")AddSubject addSubject,Model model) {
        String subjectCode = addSubject.getAddSubjectCode();
        String semester = addSubject.getAddSemester();
        String subjectName = addSubject.getAddSubjectName();
        subjectName = subjectName.toUpperCase();
        Connection connection;
        try {
            connection= DBConnection.getConnection();
            Statement statement = connection.createStatement();
            int query = statement.executeUpdate("insert into subjects values('"+ semester +"','"+subjectCode+"','"+subjectName+"')");
            if(query > 0) {
                model.addAttribute("success","Added Successfully !!");
            }
            else {
                model.addAttribute("error","Error Occurred!!");
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }

        model.addAttribute("uploadPaper",new UploadPaper());
        model.addAttribute("changeSemester",new ChangeSemester());
        model.addAttribute("deleteSubject",new DeleteSubject());
        return "edit";
    }

    @RequestMapping(value = "deleteSubject")
    public String DeleteSubject(@ModelAttribute("deleteSubject")DeleteSubject deleteSubject,Model model) {
        String subject = deleteSubject.getDeleteSubject();
        String semester = deleteSubject.getDeleteSemester();

        Connection connection;
        try {
            connection = DBConnection.getConnection();
            Statement statement = connection.createStatement();
            int query = statement.executeUpdate("delete from subjects where subjectCode = '"+subject+"'");
            if(query > 0) {
                model.addAttribute("success","Deleted Successfully!!");
            }
            else {
                model.addAttribute("error","Error Occurred!!");
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
        model.addAttribute("uploadPaper",new UploadPaper());
        model.addAttribute("changeSemester",new ChangeSemester());
        model.addAttribute("addSubject",new AddSubject());
        return "edit";
    }

}
