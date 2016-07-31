package com.b2infosoft.paathshala.model;

/**
 * Created by rajesh on 7/31/2016.
 */
public class Marks {
    private int id;
    private String examName;
    private String subjectName;
    private String tMarks;
    private String tMarksObt;
    private String pMarks;
    private String pMarksObt;
    private String addInMark;
    private String addInRes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getTMarks() {
        return tMarks;
    }

    public void setTMarks(String tMarks) {
        this.tMarks = tMarks;
    }

    public String getTMarksObt() {
        return tMarksObt;
    }

    public void setTMarksObt(String tMarksObt) {
        this.tMarksObt = tMarksObt;
    }

    public String getPMarks() {
        return pMarks;
    }

    public void setPMarks(String pMarks) {
        this.pMarks = pMarks;
    }

    public String getPMarksObt() {
        return pMarksObt;
    }

    public void setPMarksObt(String pMarksObt) {
        this.pMarksObt = pMarksObt;
    }

    public String getAddInMark() {
        return addInMark;
    }

    public void setAddInMark(String addInMark) {
        this.addInMark = addInMark;
    }

    public String getAddInRes() {
        return addInRes;
    }

    public void setAddInRes(String addInRes) {
        this.addInRes = addInRes;
    }
}
