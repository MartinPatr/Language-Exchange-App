package com.example.hamsproject;

public class Patient extends Account{
    public String healthCardNum;

    public String getHealthCardNum() {
        return healthCardNum;
    }
    public void setHealthCardNum(String healthCardNum) {
        this.healthCardNum = healthCardNum;
    }
}
