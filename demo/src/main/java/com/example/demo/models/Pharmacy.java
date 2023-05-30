package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Pharmacy {
    private int id;
    private String name;
    private String address;  // maybe city, street etc.
    
    public Pharmacy(){
    }
    
    public Pharmacy(@JsonProperty int id, @JsonProperty String name, @JsonProperty String address){
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAdress(String adress) {
        this.address = adress;
    }
}
