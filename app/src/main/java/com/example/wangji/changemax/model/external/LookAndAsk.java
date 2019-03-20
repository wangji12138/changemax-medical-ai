package com.example.wangji.changemax.model.external;

import com.example.wangji.changemax.util.other_util.Tool;

import java.util.ArrayList;
import java.util.List;

/**
 * 中医之望闻问切
 * 一次诊疗集对象
 * Created by WangJi.
 */

public class LookAndAsk {
    private int id;
    private String uid;
    private String date;
    private Patient patient;
    private String score;
    private String diagnosticResult;
    private List<String> mtQuestionList;
    private int i = -1;

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public LookAndAsk() {
        Tool tool = new Tool();
        uid = tool.getUUID();
        date = tool.getNowTime();

        mtQuestionList = new ArrayList<String>();
        /**
         * 后期将优化各种询问方式，提高changeMax的拟人化
         */
        mtQuestionList.add("请问患者怎么称呼？~");
        mtQuestionList.add("请问患者性别？~");
        mtQuestionList.add("请问患者多大呢？（具体多少岁或者老中幼）。~");
        mtQuestionList.add("请问患者哪里不舒服呢？比如：头，脚。~");
        mtQuestionList.add("请问患者有哪些症状？比如：头疼，脚麻。~");
        mtQuestionList.add("患者症状还需要补充吗？如果没了，可以对我说“没有了。”~");
        mtQuestionList.add("请问患者之前有哪些长期疾病吗？比如高血压，胃病。~");
        mtQuestionList.add("患者之前的疾病还需要补充吗？如果没了，可以对我说“没有了。~”");
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

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getDiagnosticResult() {
        return diagnosticResult;
    }

    public void setDiagnosticResult(String diagnosticResult) {
        this.diagnosticResult = diagnosticResult;
    }

    public List<String> getMtQuestionList() {
        return mtQuestionList;
    }
}
