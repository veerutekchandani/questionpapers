package com.veeru.Model;

public class ChangeSemester {
    private String oldSemester;
    private String changeSubject;
    private String newSemester;

    public void setOldSemester(String oldSemester) {
        this.oldSemester = oldSemester;
    }

    public void setChangeSubject(String changeSubject) {
        this.changeSubject = changeSubject;
    }

    public void setNewSemester(String newSemester) {
        this.newSemester = newSemester;
    }

    public String getOldSemester() {
        return oldSemester;
    }

    public String getChangeSubject() {
        return changeSubject;
    }

    public String getNewSemester() {
        return newSemester;
    }
}
