package com.veeru.Model;

public class QuestionPaper {

    private String semester;
    private String term;
    private String subject;

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public void setTerm(String term) {
        this.term = term;
    }


    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSemester() {
        return semester;
    }

    public String getSubject() {
        return subject;
    }

    public String getTerm() {
        return term;
    }
}
