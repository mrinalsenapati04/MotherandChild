package com.sih.teamsquadra.motherandchild;

public class EmployeeInformation {

    public String type;
    public String name;
    public String employeeID;
    public String age;
    public String gender;
    public String aadhar;
    public String phone;

    public EmployeeInformation(){
    }

    public EmployeeInformation(String type, String name, String employeeID, String age, String gender, String aadhar, String phone) {
        this.type = type;
        this.name = name;
        this.employeeID = employeeID;
        this.age = age;
        this.gender = gender;
        this.aadhar = aadhar;
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAadhar() {
        return aadhar;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
