package com.example.hamsproject;

public class Shift {
    private String date;

    private String id;
    private int startHour;
    private int startMinute;
    private int endHour;
    private int endMinute;
    private String doctorName;
    private String doctorKey;

    //Database uses this to make a new shift
    public Shift() {}

    public Shift(String date, int startHour, int startMinute, int endHour, int endMinute, String doctorKey, String doctorName) {
        this.date = date;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;
        this.doctorKey = doctorKey;
        this.doctorName = doctorName;
        id = "hi";
    }

    public String getDate(){
        return date;
    }

    public String getDoctorKey() {
        return doctorKey;
    }

    public String getDoctorName(){
        return doctorName;
    }
    public int getStartHour(){
        return startHour;
    }
    public int getStartMinute(){
        return startMinute;
    }
    public int getEndHour(){
        return endHour;
    }
    public int getEndMinute(){
        return endMinute;
    }
    public String getID(){
        return id;
    }



    public void setDoctorName(String doctorName){
        this.doctorName = doctorName;
    }

    public void setDoctorKey(String doctorKey) {
        this.doctorKey = doctorKey;
    }
    public void setDate(String date){
        this.date = date;
    }
    public void setStartHour(int startHour){
        this.startHour = startHour;
    }
    public void setStartMinute(int startMinute){
        this.startMinute = startMinute;
    }
    public void setEndHour(int endHour){
        this.endHour = endHour;
    }
    public void setEndMinute(int endMinute){
        this.endMinute = endMinute;
    }
    public void setID(String id){
        this.id = id;
    }
}
