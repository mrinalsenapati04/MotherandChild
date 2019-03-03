package com.sih.teamsquadra.motherandchild;

public class EmployeeBookAppointmentClass {

    public String docName;
    public String date;
    public String time;
    public String dAdhar;
    public String uAdhar;


    public EmployeeBookAppointmentClass() {

    }

    public EmployeeBookAppointmentClass(String docName, String date, String time, String dAdhar, String uAdhar) {
        this.docName = docName;
        this.date = date;
        this.time = time;
        this.dAdhar = dAdhar;
        this.uAdhar = uAdhar;
    }

    public String getDocName() {
        return docName;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getdAdhar() {
        return dAdhar;
    }

    public String getuAdhar() {
        return uAdhar;
    }
}
