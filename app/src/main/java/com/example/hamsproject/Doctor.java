package com.example.hamsproject;

public class Doctor extends Account{
    public String employeeNum;
    public String[] specialties;

    public String getEmployeeNum() {
        return employeeNum;
    }
    public void setEmployeeNum(String employeeNum) {
        this.employeeNum = employeeNum;
    }
    public String[] getSpecialties() {
        return specialties;
    }
    public void setSpecialties(String[] specialties) {
        // fix this
        this.specialties = specialties;
    }
}
