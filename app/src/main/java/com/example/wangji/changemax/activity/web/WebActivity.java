package com.example.wangji.changemax.activity.web;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.wangji.changemax.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by WangJi.
 */

public class WebActivity extends Activity {

    //    private Map<String, String> urlMap;
    private String url;

    private WebView wv_web_page;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        initView();
        //        initUrl();

        //接收传入关键词
        Intent intent = getIntent();
        String getUrl = intent.getStringExtra("url");

        if (!TextUtils.isEmpty(getUrl)) {
            url = getUrl;
        } else {
            url = "https://www.baidu.com/";
        }

        WebSettings ws_web_page = wv_web_page.getSettings();
        ws_web_page.setDisplayZoomControls(true);
        ws_web_page.setSupportZoom(true);
        ws_web_page.setJavaScriptEnabled(true);
        wv_web_page.setWebViewClient(new WebViewClient());
        wv_web_page.setWebChromeClient(new WebChromeClient());
        wv_web_page.loadUrl(url);
    }

    public void initView() {
        wv_web_page = (WebView) findViewById(R.id.wv_web_page);
    }
}
