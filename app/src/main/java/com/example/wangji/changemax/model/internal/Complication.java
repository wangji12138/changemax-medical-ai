package com.example.wangji.changemax.model.internal;

/**
 * 并发症对象
 */
public class Complication {
    private int complication_id;
    private int t_disease_id;
    private String complication_name;
    private int complication_association_disease_id;
    private String complication_association_disease_name;

    public int getComplication_id() {
        return complication_id;
    }

    public void setComplication_id(int complication_id) {
        this.complication_id = complication_id;
    }

    public int getT_disease_id() {
        return t_disease_id;
    }

    public void setT_disease_id(int t_disease_id) {
        this.t_disease_id = t_disease_id;
    }

    public String getComplication_name() {
        return complication_name;
    }

    public void setComplication_name(String complication_name) {
        this.complication_name = complication_name;
    }

    public int getComplication_association_disease_id() {
        return complication_association_disease_id;
    }

    public void setComplication_association_disease_id(int complication_association_disease_id) {
        this.complication_association_disease_id = complication_association_disease_id;
    }

    public String getComplication_association_disease_name() {
        return complication_association_disease_name;
    }

    public void setComplication_association_disease_name(String complication_association_disease_name) {
        this.complication_association_disease_name = complication_association_disease_name;
    }

}
