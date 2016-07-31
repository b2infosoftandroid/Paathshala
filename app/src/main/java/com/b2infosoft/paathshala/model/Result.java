package com.b2infosoft.paathshala.model;

/**
 * Created by rajesh on 7/31/2016.
 */
public class Result {
    private int id;
    private String result;
    private String division;
    private double percentage;
    private double totalMarks;
    private double totalObtain;
    private String markSheetType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResult() {
        return result;
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
}
