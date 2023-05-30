package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AppUser {
    private int id;
    private String trIdNumber;
    private String password;
    private boolean isAdmin;

    public AppUser() {
    }

    public AppUser(@JsonProperty int id, @JsonProperty String trIdNumber, @JsonProperty String password, @JsonProperty boolean isAdmin) {
        this.id = id;
        this.trIdNumber = trIdNumber;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTrIdNumber() {
        return trIdNumber;
    }

    public void setTrIdNumber(String tcIdNumber) {
        this.trIdNumber = tcIdNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}
