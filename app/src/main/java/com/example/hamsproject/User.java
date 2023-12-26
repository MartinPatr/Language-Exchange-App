package com.example.hamsproject;

public class User extends Account {
    public String healthCardNum;

    public User() {
        super();
    }

    public String getHealthCardNum() {
        return healthCardNum;
    }
    public void setHealthCardNum(String healthCardNum) {
        this.healthCardNum = healthCardNum;
    }
    @Override
    public String getType() {
        return "User";
    }
}
