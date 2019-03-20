package com.example.wangji.changemax.medical_system.ltp_cloud;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by WangJi.
 */

public class HttpConnectLtp extends Thread {
    private String api_key;
    private String pattern;
    private String format;
    private String LTPURL;
    private String text = "";
    public String result = "";

    public HttpConnectLtp(String txt, String pat) {
        LTPURL = "https://api.ltp-cloud.com/analysis/";
        api_key = "62c0K4d4z0xI6bEBdFKUWwmHGeUbvDJwqlWpZMza";// api_key
        pattern = pat;// ws表示只分词，除此还有pos词性标注、ner命名实体识别、dp依存句法分词、srl语义角色标注、all全部
        format = "plain";// 指定结果格式类型，plain表示简洁文本格式
        text = txt;
    }

    @Override
    public void run() {
        URL url;
        try {
            url = new URL(LTPURL + "?" + "api_key=" + api_key
                    + "&" + "text=" + text + "&" + "format="
                    + format + "&" + "pattern=" + pattern);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();
            InputStreamReader isr = new InputStreamReader(conn.getInputStream(), "utf-8");
            BufferedReader innet = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = innet.readLine()) != null) {
                // 这里是对链接进行处理
                line = line.replaceAll("</?a[^>]*>", "");
                // 这里是对样式进行处理
                line = line.replaceAll("<(\\w+)[^>]*>", "<$1>");

                sb.append(line);
            }
            isr.close();
            innet.close();

            result = sb.toString().trim();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
