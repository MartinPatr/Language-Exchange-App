package com.example.hamsproject;

public class Appointment {
    String appointmentKey;
    String patientName;
    String patientKey;
    String doctorKey;
    String date;
    Integer time;
    String appointmentStatus = "Pending";

    public Appointment() {}

    public Appointment(String appointmentKey, String patientName, String patientKey, String doctorKey, String date, Integer time) {
        this.appointmentKey = appointmentKey;
        this.patientName = patientName;
        this.patientKey = patientKey;
        this.doctorKey = doctorKey;
        this.date = date;
        this.time = time;
    }
    public String getAppointmentKey() {
        return appointmentKey;
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
    public Integer getTime() {
        return time;
    }
    public String getAppointmentStatus() {
        return appointmentStatus;
    }
    public void setAppointmentStatus(String appointmentStatus) {
        if (appointmentStatus.equals("Pending") || appointmentStatus.equals("Approved") || appointmentStatus.equals("Declined")){
            this.appointmentStatus = appointmentStatus;
        }else{
            throw new IllegalArgumentException("Appointment status must be either Pending, Approved, or Declined");
        }
    }


}
