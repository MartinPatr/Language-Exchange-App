package com.example.hamsproject;

public class Admin extends Account {

    public Admin() {
        super();
    }

    @Override
    public String getType() {
        return "Admin";
    }
}
