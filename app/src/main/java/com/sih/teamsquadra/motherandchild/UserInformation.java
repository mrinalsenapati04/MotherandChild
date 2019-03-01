package com.sih.teamsquadra.motherandchild;

public class UserInformation {
    public String type;
    public String name;
    public String address;
    public String age;
    public String gender;
    public String aadhar;
    public String phone;
    public String previousHistory;

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPreviousHistory(String previousHistory) {
        this.previousHistory = previousHistory;
    }

    public String getGender() {
        return gender;
    }

    public String getAadhar() {
        return aadhar;
    }

    public String getPhone() {
        return phone;
    }

    public String getPreviousHistory() {
        return previousHistory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UserInformation() {
    }

    public UserInformation(String name, String address, String age, String type, String gender,
                           String aadhar, String phone, String previousHistory ) {
        this.name = name;
        this.address = address;
        this.age = age;
        this.type = type;
        this.gender = gender;
        this.aadhar = aadhar;
        this.phone = phone ;
        this.previousHistory = previousHistory;
    }
}