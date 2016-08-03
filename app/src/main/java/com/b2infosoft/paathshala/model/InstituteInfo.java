package com.b2infosoft.paathshala.model;

/**
 * Created by Microsoft on 7/31/2016.
 */
public class InstituteInfo {

    private String id;
    private String city_id;
    private String schl_name;
    private String schl_add;
    private String sch_active;

    public String getSch_active() {
        return sch_active;
    }

    public void setSch_active(String sch_active) {
        this.sch_active = sch_active;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }




    public String getSchl_add() {
        return schl_add;
    }

    public void setSchl_add(String schl_add) {
        this.schl_add = schl_add;
    }

    public String getSchl_name() {
        return schl_name;
    }

    public void setSchl_name(String schl_name) {
        this.schl_name = schl_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
