package com.b2infosoft.paathshala.model;

/**
 * Created by rajesh on 7/31/2016.
 */
public class Marks {
    private int id;
    private String examName;
    private String subjectName;
    private double TMarks;
    private double TMarksObt;
    private double PMarks;
    private double PMarksObt;
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

    public double getTMarks() {
        return TMarks;
    }

    public void setTMarks(double TMarks) {
        this.TMarks = TMarks;
    }

    public double getTMarksObt() {
        return TMarksObt;
    }

    public void setTMarksObt(double TMarksObt) {
        this.TMarksObt = TMarksObt;
    }

    public double getPMarks() {
        return PMarks;
    }

    public void setPMarks(double PMarks) {
        this.PMarks = PMarks;
    }

    public double getPMarksObt() {
        return PMarksObt;
    }

    public void setPMarksObt(double PMarksObt) {
        this.PMarksObt = PMarksObt;
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
