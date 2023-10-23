package com.example.hamsproject;

public class PendingRequestModel {
    String name;
    String userType;

    public PendingRequestModel(String name, String userType) {
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
