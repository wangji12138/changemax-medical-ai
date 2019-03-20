package com.example.wangji.changemax.model.external;

/**
 * id分值集
 * Created by WangJi.
 */

public class IDScore {
    private int objectId = 0;
    private int score = 0;

    public IDScore() {

    }

    public IDScore(int objectId, int score) {
        this.objectId = objectId;
        this.score = score;
    }

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "id：" + objectId + "，score：" + score;
    }
}
