package com.example.hamsproject;

public class Appointment {
    String appointmentKey;
    String userName;
    String userKey;
    String teacherKey;
    String teacherName;
    String date;
    Integer startHour;
    Integer startMinute;
    Integer endHour;
    Integer endMinute;
    String appointmentStatus;
    Boolean isRated = false;

    public Appointment() {
        appointmentStatus = "Requested";
    }

    public Appointment(String appointmentKey, String userName, String userKey, String teacherKey, String date, Integer startHour, Integer startMinute, Integer endHour, Integer endMinute, String teacherName) {
        this.appointmentKey = appointmentKey;
        this.userName = userName;
        this.userKey = userKey;
        this.teacherKey = teacherKey;
        this.date = date;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;
        this.teacherName = teacherName;
        appointmentStatus = "Requested";
    }
    public String getAppointmentKey() {
        return appointmentKey;
    }
    public String getAppointmentStatus() {
        return appointmentStatus;
    }
    public String getDate() {
        return date;
    }
    public Integer getStartHour(){
        return startHour;
    }
    public Integer getStartMinute(){
        return startMinute;
    }public Integer getEndHour(){
        return endHour;
    }public Integer getEndMinute(){
        return endMinute;
    }
    public String getUserName() {
        return userName;
    }
    public String getUserKey() {
        return userKey;
    }
    public String getTeacherName(){ return teacherName;}
    public String getTeacherKey() {
        return teacherKey;
    }


    public void setAppointmentKey(String appointmentKey) {
        this.appointmentKey = appointmentKey;
    }

    public void setAppointmentStatus(String appointmentStatus) {
        if (appointmentStatus.equals("Requested") || appointmentStatus.equals("Accepted") || appointmentStatus.equals("Past")){
            this.appointmentStatus = appointmentStatus;
        }else{
            throw new IllegalArgumentException("Appointment status must be either Requested, Accepted, or Past");
        }
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStartHour(Integer startHour) {
        this.startHour = startHour;
    }
    public void setStartMinute(Integer startMinute) {
        this.startMinute = startMinute;
    }

    public void setEndHour(Integer endHour) {
        this.endHour = endHour;
    }

    public void setEndMinute(Integer endMinute) {
        this.endMinute = endMinute;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public void setTeacherName(String teacherName){ this.teacherName = teacherName;}

    public void setTeacherKey(String teacherKey) {
        this.teacherKey = teacherKey;
    }
    public Boolean getIsRated() {
        return isRated;
    }
    public void setIsRated(Boolean isRated) {
        this.isRated = isRated;
    }
}
