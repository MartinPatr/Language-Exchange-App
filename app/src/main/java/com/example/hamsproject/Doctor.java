package com.example.hamsproject;

import java.util.Map;
import java.util.HashMap;

public class Doctor extends Account {
    public String employeeNum;
    public Map<String,Boolean> specialties;
    public Map<String, Map<String, Integer>> shifts;
    public boolean acceptAllAppointments = false;

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
    public Map<String, Map<String, Integer>> getShifts(){ return shifts; }

    public void setShifts(Map<String, Map<String, Integer>> shifts){ this.shifts = shifts; }

    public void addShift(String date, Integer start, Integer end){
        if (shifts == null){
            shifts = new HashMap<>();
        }
        Map<String, Integer> shift = new HashMap<>();
        shift.put("start", start);
        shift.put("end", end);
        shifts.put(date, shift);
    }
    public void removeShift(String date){
        if (shifts == null){
            shifts = new HashMap<>();
        }
        shifts.remove(date);
    }
    public void setAcceptAllAppointments(boolean acceptAllAppointments) {
        this.acceptAllAppointments = acceptAllAppointments;
    }
    public boolean getAcceptAllAppointments() {
        return acceptAllAppointments;
    }
    @Override
    public String getType() {
        return "Doctor";
    }
}
