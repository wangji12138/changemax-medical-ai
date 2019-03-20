package com.example.wangji.changemax.model.external;

/**
 * 匹配的属性内容
 * Created by WangJi.
 */

public class MatchAttribute {
    private int objectId;
    private String attributeContent;

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public String getAttributeContent() {
        return attributeContent;
    }

    public void setAttributeContent(String attributeContent) {
        this.attributeContent = attributeContent;
    }

    @Override
    public String toString() {
        return objectId + ",  " + attributeContent;
    }
}
