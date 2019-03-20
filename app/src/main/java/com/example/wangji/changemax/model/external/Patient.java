package com.example.wangji.changemax.model.external;

import com.example.wangji.changemax.util.other_util.Tool;

/**
 * 患者信息
 * Created by WangJi.
 */

public class Patient {
    private int id;
    private String uid;
    private String date = "";
    private String patientName = "";
    private String patientGender = "";
    private String patientAge = "";
    private String patientPartOrgan = "";
    private String symptomString = "";
    private String diseaseString = "";
    //    private List<PersonChat> pcList;

    public Patient() {
        Tool tool = new Tool();
        date = tool.getNowTime();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientGender() {
        return patientGender;
    }

    public void setPatientGender(String patientGender) {
        this.patientGender = patientGender;
    }

    public String getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(String patientAge) {
        this.patientAge = patientAge;
    }

    public String getPatientPartOrgan() {
        return patientPartOrgan;
    }

    public void setPatientPartOrgan(String patientPartOrgan) {
        this.patientPartOrgan = patientPartOrgan;
    }

    public String getSymptomString() {
        return symptomString;
    }

    public void setSymptomString(String symptomString) {
        this.symptomString = symptomString;
    }

    public String getDiseaseString() {
        return diseaseString;
    }

    public void setDiseaseString(String diseaseString) {
        this.diseaseString = diseaseString;
    }

    @Override
    public String toString() {
        return "Test6 [id=" + id + ", uid=" + uid + ", date=" + date + ", patientName=" + patientName
                + ", patientGender=" + patientGender + ", patientAge=" + patientAge + ", patientPartOrgan="
                + patientPartOrgan + ", symptomString=" + symptomString + ", diseaseString=" + diseaseString + "]";
    }
}
