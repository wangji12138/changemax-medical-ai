package com.example.wangji.changemax.model.external;

import com.example.wangji.changemax.util.other_util.Tool;

import java.util.List;

/**
 * 用户所提出的问题
 * Created by WangJi.
 */

public class Question {
    private int id;
    private String uid;
    private String questionString;
    private String answerString;
    private String date;
    private List<KeyWord> allWList; //所有词词集（未删减）
    private List<KeyWord> burdenKwList;//减负载关键词词集
    private List<KeyWord> medicineKwList; //医学关键词集

    public Question() {
        Tool tool = new Tool();
        uid = tool.getUUID();
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

    public String getQuestionString() {
        return questionString;
    }

    public void setQuestionString(String questionString) {
        this.questionString = questionString;
    }

    public String getAnswerString() {
        return answerString;
    }

    public void setAnswerString(String answerString) {
        this.answerString = answerString;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<KeyWord> getAllWList() {
        return allWList;
    }

    public void setAllWList(List<KeyWord> allWList) {
        this.allWList = allWList;
    }

    public List<KeyWord> getBurdenKwList() {
        return burdenKwList;
    }

    public void setBurdenKwList(List<KeyWord> burdenKwList) {
        this.burdenKwList = burdenKwList;
    }

    public List<KeyWord> getMedicineKwList() {
        return medicineKwList;
    }

    public void setMedicineKwList(List<KeyWord> medicineKwList) {
        this.medicineKwList = medicineKwList;
    }


    @Override
    public String toString() {
        StringBuilder sb1 = new StringBuilder("");
        StringBuilder sb2 = new StringBuilder("");
        StringBuilder sb3 = new StringBuilder("");

        if (allWList != null && allWList.size() > 0) {
            for (int i = 0; i < allWList.size(); i++) {
                if (i == (allWList.size() - 1)) {
                    sb1.append("【" + allWList.get(i).toString() + "】");
                } else {
                    sb1.append("【" + allWList.get(i).toString() + "】" + "-");
                }
            }
        }
        if (burdenKwList != null && burdenKwList.size() > 0) {
            for (int i = 0; i < burdenKwList.size(); i++) {
                if (i == (burdenKwList.size() - 1)) {
                    sb2.append("【" + burdenKwList.get(i).toString() + "】");
                } else {
                    sb2.append("【" + burdenKwList.get(i).toString() + "】" + "-");
                }
            }
        }
        if (medicineKwList != null && medicineKwList.size() > 0) {
            for (int i = 0; i < medicineKwList.size(); i++) {
                if (i == (medicineKwList.size() - 1)) {
                    sb3.append("【" + medicineKwList.get(i).toString() + "】");
                } else {
                    sb3.append("【" + medicineKwList.get(i).toString() + "】" + "-");
                }
            }
        }
        return "MySum [id=" + id + ", uid=" + uid + ", questionString=" + questionString + ", answerString="
                + answerString + ", date=" + date + ", allWList=" + sb1.toString() + ", burdenKwList=" + sb2.toString()
                + ", medicineKwList=" + sb3.toString() + "]";
    }

}
