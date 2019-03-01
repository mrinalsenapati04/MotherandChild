package com.sih.teamsquadra.motherandchild;

public class DoctorInformation {

    String type,name,gender,age,speciality,license_no,phone,experience,skypeID;

    public DoctorInformation() {
    }

    public DoctorInformation(String type, String name, String gender, String age, String speciality, String license_no, String phone, String experience, String skypeID) {
        this.type = type;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.speciality = speciality;
        this.license_no = license_no;
        this.phone = phone;
        this.experience = experience;
        this.skypeID = skypeID;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getAge() {
        return age;
    }

    public String getLicense_no() {
        return license_no;
    }

    public String getPhone() {
        return phone;
    }

    public String getExperience() {
        return experience;
    }

    public String getSkypeID() {
        return skypeID;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setLicense_no(String license_no) {
        this.license_no = license_no;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public void setSkypeID(String skypeID) {
        this.skypeID = skypeID;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }
}