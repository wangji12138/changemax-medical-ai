package com.example.wangji.changemax.model.external;

/**
 * 关键字
 * Created by WangJi.
 */

public class KeyWord {
    private int id;
    private String keyWordString;//关键词内容
    private String keyWordPartOfSpeech;//关键词所属词性

    public KeyWord(){

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKeyWordString() {
        return keyWordString;
    }

    public void setKeyWordString(String keyWordString) {
        this.keyWordString = keyWordString;
    }

    public String getKeyWordPartOfSpeech() {
        return keyWordPartOfSpeech;
    }

    public void setKeyWordPartOfSpeech(String keyWordPartOfSpeech) {
        this.keyWordPartOfSpeech = keyWordPartOfSpeech;
    }

    @Override
    public String toString() {
        return id + "-" + keyWordString + "-" + keyWordPartOfSpeech;
    }
}
