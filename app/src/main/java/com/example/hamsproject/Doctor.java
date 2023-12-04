package com.example.hamsproject;

import java.util.Map;
import java.util.HashMap;

public class Doctor extends Account {
    public String employeeNum;
    public Map<String,Boolean> specialties;
    public Map<String, Map<String, Object>> shifts;
    public boolean acceptAllAppointments = false;
    public Integer numRatings = 0;
    public Double totalRating = 0.0;

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
    public Map<String, Map<String, Object>> getShifts(){ return shifts; }

    public void setShifts(Map<String, Map<String, Object>> shifts){ this.shifts = shifts; }

    public void addShift(String date, int startHour, int endHour, int startMinute, int endMinute){
        if (shifts == null){
            shifts = new HashMap<>();
        }
        Map<String, Object> shift = new HashMap<>();
        shift.put("startHour", startHour);
        shift.put("endHour", endHour);
        shift.put("startMinute", startMinute);
        shift.put("endMinute", endMinute);
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
    public void setNumRatings(Integer numRatings) {
        this.numRatings = numRatings;
    }
    public Integer getNumRatings() {
        return numRatings;
    }
    public void setTotalRating(Double totalRating) {
        this.totalRating = totalRating;
    }
    public Double getTotalRating() {
        return totalRating;
    }
    @Override
    public String getType() {
        return "Doctor";
    }
}

