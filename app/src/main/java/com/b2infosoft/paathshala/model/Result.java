package com.b2infosoft.paathshala.model;

/**
 * Created by rajesh on 7/31/2016.
 */
public class Result {
    private int id;
    private int sId;
    private String result;
    private String division;
    private double percentage;
    private double totalMarks;
    private double totalObtain;
    private String markSheetType;
    private int sessionId;
    private int schoolId;
    private String searchType;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResult() {
        return result;
    }

    public int getsId() {
        return sId;
    }

    public void setsId(int sId) {
        this.sId = sId;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public double getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(double totalMarks) {
        this.totalMarks = totalMarks;
    }

    public double getTotalObtain() {
        return totalObtain;
    }

    public void setTotalObtain(double totalObtain) {
        this.totalObtain = totalObtain;
    }

    public String getMarkSheetType() {
        return markSheetType;
    }

    public void setMarkSheetType(String markSheetType) {
        this.markSheetType = markSheetType;
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
