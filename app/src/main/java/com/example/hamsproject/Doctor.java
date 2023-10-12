package com.example.hamsproject;

import java.util.ArrayList;

public class Doctor extends Account{
    public String employeeNum;
    public ArrayList<String> specialties;

    public String getEmployeeNum() {
        return employeeNum;
    }
    public void setEmployeeNum(String employeeNum) {
        this.employeeNum = employeeNum;
    }
    public ArrayList<String> getSpecialties() {
        return specialties;
    }
    public void setSpecialties(ArrayList<String> specialties) {
        // fix this
        this.specialties = specialties;
    }
}
