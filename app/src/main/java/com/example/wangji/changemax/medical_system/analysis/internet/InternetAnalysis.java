package com.example.wangji.changemax.medical_system.analysis.internet;

import android.content.Context;

/**
 * Created by WangJi.
 */
public class InternetAnalysis {
    private Context myContext;

    public InternetAnalysis(Context myContext) {
        this.myContext = myContext;
    }

    public String internetAnalysis(String userMessage) {
        String baiduSearchUrl = "https://www.baidu.com/s?wd=";

        String url = baiduSearchUrl + userMessage;

        return "请原谅我没有分析出您的意思，我将对网络请求帮助~///" + url + "///";
    }
}
