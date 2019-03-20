package com.example.wangji.changemax.model.internal;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class Part {
    private int part_id;
    private String part_name;
    private String part_contained_organs;

    public int getPart_id() {
        return part_id;
    }

    public void setPart_id(int part_id) {
        this.part_id = part_id;
    }

    public String getPart_name() {
        return part_name;
    }

    public void setPart_name(String part_name) {
        this.part_name = part_name;
    }

    public String getPart_contained_organs() {
        return part_contained_organs;
    }

    public void setPart_contained_organs(String part_contained_organs) {
        this.part_contained_organs = part_contained_organs;
    }

    public List<String> allPartOrgan() {
        List<String> allPartOrganList = new ArrayList<String>();

        allPartOrganList.add(part_name);
        if (!TextUtils.isEmpty(part_contained_organs)) {
            String[] organArray = part_contained_organs.split("，");
            for (String organ : organArray) {
                allPartOrganList.add(organ);
            }
        }
        return allPartOrganList;
    }

}
