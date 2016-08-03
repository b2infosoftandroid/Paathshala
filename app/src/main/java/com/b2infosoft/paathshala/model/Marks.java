package com.b2infosoft.paathshala.model;

/**
 * Created by rajesh on 7/31/2016.
 */
public class Marks {
    private int id;
    private int sId;
    private String examName;
    private String subjectName;
    private double TMarks;
    private double TMarksObt;
    private double PMarks;
    private double PMarksObt;
    private String addInMark;
    private String addInRes;
    private int sessionId;
    private int schoolId;
    private String searchType;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getsId() {
        return sId;
    }

    public void setsId(int sId) {
        this.sId = sId;
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

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }
}
