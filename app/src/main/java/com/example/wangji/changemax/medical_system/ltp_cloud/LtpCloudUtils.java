package com.example.wangji.changemax.medical_system.ltp_cloud;

/**
 * Created by WangJi.
 */

public class LtpCloudUtils {

    /**
     * ws表示只分词
     * @param text
     * @return
     */
    public String WS(String text) {
        HttpConnectLtp htp = new HttpConnectLtp(text, "ws");
        htp.start();
        try {
            htp.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return htp.result;
    }

    /**
     * ner表示命名实体识别
     * @param text
     * @return
     */
    public String NER(String text) {
        HttpConnectLtp htp = new HttpConnectLtp(text, "ner");
        htp.start();
        try {
            htp.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return htp.result;
    }

    /**
     * dp表示依存句法分词
     * @param text
     * @return
     */
    public String DP(String text) {

        HttpConnectLtp htp = new HttpConnectLtp(text, "dp");
        htp.start();
        try {
            htp.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return htp.result;

    }

    /**
     * pos表示词性标注
     * @param text
     * @return
     */
    public String POS(String text) {
        HttpConnectLtp htp = new HttpConnectLtp(text, "pos");
        htp.start();
        try {
            htp.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return htp.result;

    }

    /**
     * srl表示语义角色标注
     * * @param text
     * @return
     */
    public String SRL(String text) {

        HttpConnectLtp htp = new HttpConnectLtp(text, "srl");
        htp.start();
        try {
            htp.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return htp.result;

    }

}
