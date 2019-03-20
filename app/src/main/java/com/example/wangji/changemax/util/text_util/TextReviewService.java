package com.example.wangji.changemax.util.text_util;

import java.net.URLEncoder;

/**
 * Created by WangJi.
 */
public class TextReviewService {

    TextReviewUtil textReviewUtil = new TextReviewUtil();


    public void textReview() {

        String access_token = "24.240624d5ded080e6cde4844bea2c63dd.2592000.1529037598.282335-11243441";
        try {
            String result = get_text("上天了", "https://aip.baidubce.com/rest/2.0/antispam/v2/spam", access_token);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public String get_text(String content, String url, String accessToken) {
        String param;
        String data;
        try {
            //设置请求的编码
            param = "content=" + URLEncoder.encode(content, "UTF-8");
            //发送并取得结果
            data = textReviewUtil.post(url, accessToken, param);
            System.out.println(data);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

}
