package com.example.wangji.changemax.medical_system.pretreatment;

import android.content.Context;

import com.example.wangji.changemax.model.external.KeyWord;
import com.example.wangji.changemax.model.external.MatchAttribute;
import com.example.wangji.changemax.service.internal.MedicalNounService;

import java.util.ArrayList;
import java.util.List;

/**
 * 关键词分析
 * Created by WangJi.
 */

public class KeyWordInitialAnalysis {
    private Context myContext;
    private MedicalNounService mns;
    private PartOfSpeechAnalysis psa;

    public KeyWordInitialAnalysis(Context myContext) {
        this.myContext = myContext;
    }


    /**
     * 医疗关键词匹配分析
     *
     * @param allKwList
     * @return
     */
    public List<KeyWord> mkwiAnalysis(List<KeyWord> allKwList) {
        mns = new MedicalNounService(myContext);
        List<KeyWord> kwList = new ArrayList<KeyWord>();
        for (KeyWord keyWord : allKwList) {
            String keyWordString = keyWord.getKeyWordString();
            List<MatchAttribute> maList = mns.getMedicalNounByFuzzyName(keyWordString);
            if (maList != null && maList.size() > 0) {// 存在匹配的医学关键字
                kwList.add(keyWord);
            }
        }
        return kwList;
    }

    /**
     * 各关键词减负载分析
     *
     * @param allKwList
     * @param attributes
     * @return
     */
    public List<KeyWord> bkwiAnalysis(List<KeyWord> allKwList, String attributes) {
        List<KeyWord> kwList = new ArrayList<KeyWord>();
        psa = new PartOfSpeechAnalysis();

        if (attributes.contains("name")) {
            for (KeyWord keyWord : allKwList) {
                String keyWordPartOfSpeech = keyWord.getKeyWordPartOfSpeech();
                //查询词性，去除标点符号，你我他等。
                if (psa.partOfSpeechCallAnalysis(keyWordPartOfSpeech)) {
                    kwList.add(keyWord);
                }
            }
            return kwList;
        } else if (attributes.contains("gender") || attributes.contains("age")) {
            for (KeyWord keyWord : allKwList) {
                String keyWordPartOfSpeech = keyWord.getKeyWordPartOfSpeech();
                //查询词性，去除...
                if (psa.partOfSpeechGenderAgeAnalysis(keyWordPartOfSpeech)) {
                    kwList.add(keyWord);
                }
            }
            return kwList;
        } else if (attributes.contains("partorgan")) {
            for (KeyWord keyWord : allKwList) {
                String keyWordPartOfSpeech = keyWord.getKeyWordPartOfSpeech();
                //查询词性，去除...
                if (psa.partOfSpeechPartOrganAnalysis(keyWordPartOfSpeech)) {
                    kwList.add(keyWord);
                }
            }
            return kwList;
        } else if (attributes.contains("disease") || attributes.contains("symptom")) {
            for (KeyWord keyWord : allKwList) {
                String keyWordPartOfSpeech = keyWord.getKeyWordPartOfSpeech();
                //查询词性，去除...
                if (psa.partOfSpeechMedicineAnalysis(keyWordPartOfSpeech)) {
                    kwList.add(keyWord);
                }
            }
            return kwList;
        } else {

            return kwList;
        }


    }

}
