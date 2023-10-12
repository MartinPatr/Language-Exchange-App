package com.example.hamsproject;

public class PatientInfo {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNum;
    private String address;
    private String healthCardNum;

    public PatientInfo(){

    }

    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getAddress(){
        return address;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public String getPhoneNum(){
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum){
        this.phoneNum = phoneNum;
    }

    public String getHealthCardNum(){
        return healthCardNum;
    }

    public void setHealthCardNum(String healthCardNum){
        this.healthCardNum = healthCardNum;
    }






}
