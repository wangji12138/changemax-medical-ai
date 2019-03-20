package com.example.wangji.changemax.model.external;

import com.example.wangji.changemax.util.other_util.Tool;

import java.util.List;

/**
 * Created by WangJi.
 */
public class LtpCloudResult {
    private int id;
    private String uid;
    private String date;
    private List<KeyWord> nameBurdenKwList;// 称呼减负载关键词集
    private List<KeyWord> genderBurdenKwList;// 性别减负载关键词集
    private List<KeyWord> ageBurdenKwList;// 年龄减负载关键词集
    private List<KeyWord> partOrganBurdenKwList;// 部位器官减负载关键词集
    private List<KeyWord> symptomMedicineKwList;// 症状医学关键词集
    private List<KeyWord> symptomBurdenKwList;// 症状减负载关键词集
    private List<KeyWord> diseaseMedicineKwList;// 疾病医学关键词集
    private List<KeyWord> diseaseBurdenKwList;// 疾病减负载关键词集

    public LtpCloudResult() {
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

    public List<KeyWord> getNameBurdenKwList() {
        return nameBurdenKwList;
    }

    public void setNameBurdenKwList(List<KeyWord> nameBurdenKwList) {
        this.nameBurdenKwList = nameBurdenKwList;
    }

    public List<KeyWord> getGenderBurdenKwList() {
        return genderBurdenKwList;
    }

    public void setGenderBurdenKwList(List<KeyWord> genderBurdenKwList) {
        this.genderBurdenKwList = genderBurdenKwList;
    }

    public List<KeyWord> getAgeBurdenKwList() {
        return ageBurdenKwList;
    }

    public void setAgeBurdenKwList(List<KeyWord> ageBurdenKwList) {
        this.ageBurdenKwList = ageBurdenKwList;
    }

    public List<KeyWord> getPartOrganBurdenKwList() {
        return partOrganBurdenKwList;
    }

    public void setPartOrganBurdenKwList(List<KeyWord> partOrganBurdenKwList) {
        this.partOrganBurdenKwList = partOrganBurdenKwList;
    }

    public List<KeyWord> getSymptomMedicineKwList() {
        return symptomMedicineKwList;
    }

    public void setSymptomMedicineKwList(List<KeyWord> symptomMedicineKwList) {
        this.symptomMedicineKwList = symptomMedicineKwList;
    }

    public List<KeyWord> getSymptomBurdenKwList() {
        return symptomBurdenKwList;
    }

    public void setSymptomBurdenKwList(List<KeyWord> symptomBurdenKwList) {
        this.symptomBurdenKwList = symptomBurdenKwList;
    }

    public List<KeyWord> getDiseaseMedicineKwList() {
        return diseaseMedicineKwList;
    }

    public void setDiseaseMedicineKwList(List<KeyWord> diseaseMedicineKwList) {
        this.diseaseMedicineKwList = diseaseMedicineKwList;
    }

    public List<KeyWord> getDiseaseBurdenKwList() {
        return diseaseBurdenKwList;
    }

    public void setDiseaseBurdenKwList(List<KeyWord> diseaseBurdenKwList) {
        this.diseaseBurdenKwList = diseaseBurdenKwList;
    }


}
