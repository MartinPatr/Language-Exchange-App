package com.example.hamsproject;
import java.io.Serializable;

public class Account implements Serializable{
    public String firstName;
    public String lastName;
    public String username;
    public String password;
    public String phone;
    public String address;
    public String registrationStatus = "Pending";
    public String key;

    public Account() {
    }
    
    public String getType() {
        return "Account";
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getRegistrationStatus() {
        return registrationStatus;
    }
    public void setKey(String key){
        this.key = key;
    }
    public String getKey(){
        return key;
    }
    public void setRegistrationStatus(String registrationStatus) {
        if (!(registrationStatus.equals("Pending") || registrationStatus.equals("Approved") || registrationStatus.equals("Denied"))){
            throw new IllegalArgumentException("registrationStatus must be Pending, Approved, or Denied");
        }
        this.registrationStatus = registrationStatus;
    }
}
