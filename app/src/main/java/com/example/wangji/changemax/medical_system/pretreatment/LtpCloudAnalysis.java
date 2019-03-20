package com.example.wangji.changemax.medical_system.pretreatment;


import android.text.TextUtils;
import android.util.Log;

import com.example.wangji.changemax.medical_system.ltp_cloud.LtpCloudUtils;
import com.example.wangji.changemax.model.external.KeyWord;

import java.util.ArrayList;
import java.util.List;

/**
 * 云分析
 * Created by WangJi.
 */

public class LtpCloudAnalysis {
    private LtpCloudUtils ltpCloudUtils = null;
    private String nls;//NaturalLanguageString    用户所传入的自然语言字符串
    private String kwPosString;//KewWord Part of speech String    分析结果
    private List<KeyWord> allKwList;//All KeyWord List     分析词对象集

    LtpCloudAnalysis() {
    }

    /**
     * 自然语言分析
     * 返回云分析结果字符串方式
     *
     * @param nls
     * @return
     */
    public String naturalLanguageAnalysis(String nls) {
        //反馈空分析
        if (nls == null || nls.equals("")) {
            return "";
        }

        ltpCloudUtils = new LtpCloudUtils();//实例化云分析方式对象
        kwPosString = ltpCloudUtils.POS(nls);//云分析返回结果
        if (!TextUtils.isEmpty(kwPosString)) {
            String[] kwPosArray = kwPosString.split(" ");//此处返回为词性分析出来的字符数组，带有词性
            if (kwPosArray != null) {
                //将云分析出来的词数组转换为词对象集
                allKwList = new ArrayList<KeyWord>();
                for (String kwPosChar : kwPosArray) {
                    KeyWord newKeyWord = new KeyWord();
                    int i = kwPosChar.trim().indexOf("_");
                    String kwChar = kwPosChar.substring(0, i);
                    String kwPos = kwPosChar.substring(i, kwPosChar.length());

                    newKeyWord.setKeyWordString(kwChar);
                    newKeyWord.setKeyWordPartOfSpeech(kwPos);

                    allKwList.add(newKeyWord);
                }
            } else {
                return "";
            }
            return kwPosString;//此处返回为词性分析出来的字符串
        }
        return "";

        //示例：我/r  脸上/nl  的/u  毛孔/n  粗大/a  ，/wp  长/v  了/u  很多/m  痘痘/n  怎么办/r  ？/wp
    }

    /**
     * 获取词对象集
     *
     * @return
     */
    public List<KeyWord> getAllKwList() {
        return allKwList;
    }

}
