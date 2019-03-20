package com.example.wangji.changemax.model.external;

/**
 * Created by WangJi.
 */

public class PartOfSpeech {

    /*
    Part of speech coding : posCoding （词性编码）
    Part of speech name : posName （词性名称）
    Part of speech annotation : posAnnotation （词性注解）
     */
    private int id;
    private String posCoding;
    private String posName;
    private String posAnnotation;


    public String getPosCoding() {
        return posCoding;
    }

    public void setPosCoding(String posCoding) {
        this.posCoding = posCoding;
    }

    public String getPosName() {
        return posName;
    }

    public void setPosName(String posName) {
        this.posName = posName;
    }

    public String getPosAnnotation() {
        return posAnnotation;
    }

    public void setPosAnnotation(String posAnnotation) {
        this.posAnnotation = posAnnotation;
    }

    public PartOfSpeech() {
        super();
    }
}
