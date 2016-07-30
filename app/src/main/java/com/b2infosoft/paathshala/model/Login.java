package com.b2infosoft.paathshala.model;

/**
 * Created by rajesh on 7/17/2016.
 */
public class Login {
    private String id;
    private String password;
    private String role;
    private String session;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }
}
