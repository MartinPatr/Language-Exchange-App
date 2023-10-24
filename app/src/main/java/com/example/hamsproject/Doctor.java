package com.example.hamsproject;

import java.util.Map;

public class Doctor extends Account {
    public String employeeNum;
    public Map<String,Boolean> specialties;

    public Doctor() {
        super();
    }

    public String getEmployeeNum() {
        return employeeNum;
    }
    public void setEmployeeNum(String employeeNum) {
        this.employeeNum = employeeNum;
    }
    public Map<String,Boolean> getSpecialties() {
        return specialties;
    }
    public void setSpecialties(Map<String,Boolean> specialties) {
        this.specialties = specialties;
    }
    @Override
    public String getType() {
        return "Doctor";
    }
}
