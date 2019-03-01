package com.sih.teamsquadra.motherandchild;

public class PatientImage {

    public String aadharId,desc,url;

    public PatientImage(String aadharId, String desc, String url) {
        this.aadharId = aadharId;
        this.desc = desc;
        this.url = url;
    }

    public PatientImage() {

    }

    public String getAadharId() {
        return aadharId;
    }

    public String getDesc() {
        return desc;
    }

    public String getUrl() {
        return url;
    }
}
