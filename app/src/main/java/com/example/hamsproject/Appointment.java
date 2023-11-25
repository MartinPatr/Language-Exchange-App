package com.example.hamsproject;

public class Appointment {
    String appointmentKey;
    String patientName;
    String patientKey;
    String doctorKey;
    String doctorName;
    String date;
    Integer startHour;
    Integer startMinute;
    Integer endHour;
    Integer endMinute;
    String appointmentStatus;

    public Appointment() {
        appointmentStatus = "Requested";
    }

    public Appointment(String appointmentKey, String patientName, String patientKey, String doctorKey, String date, Integer startHour, Integer startMinute, Integer endHour, Integer endMinute, String doctorName) {
        this.appointmentKey = appointmentKey;
        this.patientName = patientName;
        this.patientKey = patientKey;
        this.doctorKey = doctorKey;
        this.date = date;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;
        this.doctorName = doctorName;
        appointmentStatus = "Requested";
    }
    public String getAppointmentKey() {
        return appointmentKey;
    }
    public Integer getStartHour(){
        return startHour;
    }
    public String getDoctorName(){ return doctorName;}
    public Integer getStartMinute(){
        return startMinute;
    }public Integer getEndHour(){
        return endHour;
    }public Integer getEndMinute(){
        return endMinute;
    }
    public String getPatientName() {
        return patientName;
    }
    public String getPatientKey() {
        return patientKey;
    }
    public String getDoctorKey() {
        return doctorKey;
    }
    public String getDate() {
        return date;
    }
    public String getAppointmentStatus() {
        return appointmentStatus;
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

    public void setAppointmentKey(String appointmentKey) {
        this.appointmentKey = appointmentKey;
    }

    public void setDoctorKey(String doctorKey) {
        this.doctorKey = doctorKey;
    }
    public void setDoctorName(String doctorName){ this.doctorName = doctorName;}

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public void setPatientKey(String patientKey) {
        this.patientKey = patientKey;
    }

    public void setStartHour(Integer startHour) {
        this.startHour = startHour;
    }

    public void setEndMinute(Integer endMinute) {
        this.endMinute = endMinute;
    }

    public void setEndHour(Integer endHour) {
        this.endHour = endHour;
    }

    public void setStartMinute(Integer startMinute) {
        this.startMinute = startMinute;
    }
}
