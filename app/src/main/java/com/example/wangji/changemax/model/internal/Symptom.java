package com.example.wangji.changemax.model.internal;

import java.util.List;

public class Symptom {

    private int symptom_id;
    private String symptom_name;// 症状名称
    private String symptom_trans;// 症状音译
    private String symptom_intro;// 症状简介
    private String symptom_cause;// 症状起因
    private List<DiseaseSy> dList;// 可能伴随疾病集 数据库中另一个表
    private String symptomatic_details_content;// 诊断详述内容
    private String suggested_treatment_department; // 建议就诊科室

    public int getSymptom_id() {
        return symptom_id;
    }

    public void setSymptom_id(int symptom_id) {
        this.symptom_id = symptom_id;
    }

    public String getSymptom_name() {
        return symptom_name;
    }

    public void setSymptom_name(String symptom_name) {
        this.symptom_name = symptom_name;
    }

    public String getSymptom_trans() {
        return symptom_trans;
    }

    public void setSymptom_trans(String symptom_trans) {
        this.symptom_trans = symptom_trans;
    }

    public String getSymptom_intro() {
        return symptom_intro;
    }

    public void setSymptom_intro(String symptom_intro) {
        this.symptom_intro = symptom_intro;
    }

    public String getSymptom_cause() {
        return symptom_cause;
    }

    public void setSymptom_cause(String symptom_cause) {
        this.symptom_cause = symptom_cause;
    }

    public List<DiseaseSy> getdList() {
        return dList;
    }

    public void setdList(List<DiseaseSy> dList) {
        this.dList = dList;
    }

    public String getSymptomatic_details_content() {
        return symptomatic_details_content;
    }

    public void setSymptomatic_details_content(String symptomatic_details_content) {
        this.symptomatic_details_content = symptomatic_details_content;
    }

    public String getSuggested_treatment_department() {
        return suggested_treatment_department;
    }

    public void setSuggested_treatment_department(String suggested_treatment_department) {
        this.suggested_treatment_department = suggested_treatment_department;
    }

    @Override
    public String toString() {
        return "Test5 [symptom_id=" + symptom_id + ",\r\n symptom_name=" + symptom_name + ",\n symptom_trans=" + symptom_trans
                + ",\n symptom_intro=" + symptom_intro + ",\n symptom_cause=" + symptom_cause
                + ",\n symptomatic_details_content=" + symptomatic_details_content + ",\n suggested_treatment_department="
                + suggested_treatment_department + "]";
    }

}
