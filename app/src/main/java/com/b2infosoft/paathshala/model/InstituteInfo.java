package com.b2infosoft.paathshala.model;

/**
 * Created by Microsoft on 7/31/2016.
 */
public class InstituteInfo {

    private int id;
    private int cityId;
    private String name;
    private String address;
    private String active;
    private String cityName;
    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }




    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
