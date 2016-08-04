package com.b2infosoft.paathshala.model;

/**
 * Created by Microsoft on 8/4/2016.
 */
public class ComplaintInfo {
    private String id;
    private String subject;
    private String detail;
    private String cdate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getCdate() {
        return cdate;
    }

    public void setCdate(String cdate) {
        this.cdate = cdate;
    }
}
