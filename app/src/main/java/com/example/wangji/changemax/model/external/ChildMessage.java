package com.example.wangji.changemax.model.external;

/**
 * Created by WangJi.
 */
public class ChildMessage {
    private String cmString;
    private boolean isKw = false;//是否为关键词集

    public String getCmString() {
        return cmString;
    }

    public void setKw(boolean kw) {
        isKw = kw;
    }

    public boolean isKw() {
        return isKw;
    }

    public void setCmString(String cmString) {
        this.cmString = cmString;
    }

    public ChildMessage(String cmString, boolean isKw) {
        this.cmString = cmString;
        this.isKw = isKw;
    }

    public ChildMessage() {

    }

    @Override
    public String toString() {
        return cmString + ", " + isKw + "。";
    }
}
