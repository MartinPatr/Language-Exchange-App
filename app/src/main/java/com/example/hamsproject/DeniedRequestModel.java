package com.example.hamsproject;

public class DeniedRequestModel {
    String name;
    String userType;

    public DeniedRequestModel(String name, String userType) {
        this.name = name;
        this.userType = userType;
    }

    public String getName() {
        return name;
    }

    public String getUserType() {
        return userType;
    }
}
