package com.example.wangji.changemax.util.http_util;

import android.util.Log;

import com.example.wangji.changemax.model.external.Result;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * 消息接受和解析类
 * Created by WangJi.
 */
public class TuLingHttpUtils {
    private static String API_KEY = "40fcd6a3ef6841ebbd0fab204775dd78";
    private static String URL = "http://www.tuling123.com/openapi/api";

    /**
     * 发送一个消息，并得到返回的消息
     *
     * @param msg
     * @return
     */
    public static String sendMsg(String msg) {
        String answerMessage = "";
        String url = setParams(msg);
        String res = doGet(url);
        Gson gson = new Gson();
        Result result = gson.fromJson(res, Result.class);

        if (result.getCode() > 400000 || result.getText() == null  || result.getText().trim().equals("")) {
            Log.v("TuLingHttpUtils", "该功能等待开发...");
            answerMessage = ("");
        } else {
            answerMessage = result.getText();
        }


        return answerMessage;
    }

    /**
     * 拼接Url
     *
     * @param msg
     * @return
     */
    private static String setParams(String msg) {
        /** 利用Java中URLEncoder对其进行编码，如果不能实现，抛出异常 */
        try {
            msg = URLEncoder.encode(msg, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return URL + "?key=" + API_KEY + "&info=" + msg;
    }

    /**
     * Get请求，获得返回数据
     *
     * @param urlStr
     * @return
     */
    private static String doGet(String urlStr) {
        java.net.URL url = null;
        HttpURLConnection conn = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try {
            url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(5 * 1000);
            conn.setConnectTimeout(5 * 1000);
            conn.setRequestMethod("GET");
            conn.connect();
            if (conn.getResponseCode() == 200) { //判断服务器是否成功处理了请求
                is = conn.getInputStream();
                baos = new ByteArrayOutputStream();
                int len = -1;
                byte[] buf = new byte[128];

                while ((len = is.read(buf)) != -1) { //从输入流中读取一定数量的字节，如果流位于文件末尾而没有可用的字节将会返回-1；
                    baos.write(buf, 0, len);
                }
                baos.flush(); //刷新，将缓冲区内容全部输出
                return baos.toString();
            } else {
                throw new RuntimeException("服务器连接错误！");
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("服务器连接错误！");
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (baos != null)
                    baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            conn.disconnect();
        }

    }

}