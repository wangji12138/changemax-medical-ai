package com.example.wangji.changemax.medical_system.analysis.data;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.wangji.changemax.model.external.IDScore;
import com.example.wangji.changemax.model.external.KeyWord;
import com.example.wangji.changemax.model.external.MatchAttribute;
import com.example.wangji.changemax.model.internal.Disease;
import com.example.wangji.changemax.service.internal.ComplicationService;
import com.example.wangji.changemax.service.internal.DiseaseOrPaService;
import com.example.wangji.changemax.service.internal.DiseaseService;
import com.example.wangji.changemax.service.internal.DiseaseSyService;
import com.example.wangji.changemax.service.internal.SymptomDiService;
import com.example.wangji.changemax.service.internal.SymptomOrPaService;
import com.example.wangji.changemax.service.internal.SymptomService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 词频分析
 * Created by WangJi.
 */

public class WordFrequencyAnalysis {
    private Context myContext;

    private SymptomService symptomService;
    private DiseaseService diseaseService;

    private SymptomOrPaService symptomOrPaService;
    //    private DiseaseOrPaService diseaseOrPaService;

    private ComplicationService complicationService;

    private SymptomDiService symptomDiService;
    //    private DiseaseSyService diseaseSyService;

    WordFrequencyAnalysis(Context myContext) {
        this.myContext = myContext;
        symptomService = new SymptomService(myContext);
        diseaseService = new DiseaseService(myContext);

        symptomOrPaService = new SymptomOrPaService(myContext);
        //        diseaseOrPaService = new DiseaseOrPaService(myContext);

        complicationService = new ComplicationService(myContext);

        symptomDiService = new SymptomDiService(myContext);
        //        diseaseSyService = new DiseaseSyService(myContext);
    }


    /**
     * 分析症状数据库，获取模糊匹配id集
     *
     * @param kwList
     * @param level
     * @return
     */
    public List<IDScore> wFSymptomAnalysis(List<KeyWord> kwList, int level) {
        Log.v("诊疗分析", "分析症状数据库，获取模糊匹配id集!!!!");
        int i1 = 5 * level;
        int i2 = 2 * level;
        int i3 = 2 * level;
        int i4 = 2 * level;

        List<IDScore> symptomIdScoresList = new ArrayList<IDScore>();
        for (KeyWord keyWord : kwList) {
            String keyWordString = keyWord.getKeyWordString();

            List<MatchAttribute> symptomNameList = symptomService.getSymptomByFuzzyName(keyWordString);
            List<MatchAttribute> symptomIntroList = symptomService.getSymptomByFuzzyIntro(keyWordString);
            List<MatchAttribute> symptomCauseList = symptomService.getSymptomByFuzzyCause(keyWordString);
            List<MatchAttribute> symptomDetailsList = symptomService.getSymptomByFuzzyDetails(keyWordString);

            //定义统计对应Symptom中不同id加分值
            idAddPoints(symptomIdScoresList, symptomNameList, i1);
            idAddPoints(symptomIdScoresList, symptomIntroList, i2);
            idAddPoints(symptomIdScoresList, symptomCauseList, i3);
            idAddPoints(symptomIdScoresList, symptomDetailsList, i4);

        }
        return symptomIdScoresList;
    }


    /**
     * 分析疾病数据集，从症状引发疾病集分析，匹配对应id的疾病数据库，生成疾病id集
     *
     * @param dIDScoreList
     * @param attributesKWList
     * @param level
     * @return
     */
    public List<IDScore> wFDiseaseAnalysis(List<IDScore> dIDScoreList, List<KeyWord> attributesKWList, int level) {
        Log.v("诊疗分析", "分析症状数据库，分析疾病数据集，从症状引发疾病集分析，匹配对应id的疾病数据库，生成疾病id集!!!!");
        int i1 = 5 * level;
        int i2 = 5 * level;
        int i3 = 2 * level;
        int i4 = 2 * level;
        int i5 = 2 * level;

        int i6 = 2 * level;
        int i7 = 2 * level;
        int i8 = 2 * level;

        int i9 = 2 * level;
        int i10 = 2 * level;


        for (KeyWord keyWord : attributesKWList) {
            String kwString = keyWord.getKeyWordString();

            for (IDScore dIdScore : dIDScoreList) {
                int disease_id = dIdScore.getObjectId();
                Disease disease = diseaseService.getDiseaseById(disease_id);
                if (disease != null) {
                    String diseaseName = disease.getDisease_name();
                    String diseaseAilas = disease.getDisease_alias();
                    String diseaseIntro = disease.getDisease_intro();
                    String diseaseIncidenceSite = disease.getDisease_incidence_site();
                    String diseaseMultiplePeople = disease.getDisease_multiple_people();

                    String diseaseSymptomEarly = disease.getDisease_symptom_early();
                    String diseaseSymptomLate = disease.getDisease_symptom_late();
                    String diseaseSymptomRelated = disease.getDisease_symptom_related();

                    String diseaseComplication = disease.getDisease_complication();
                    String diseaseComplicationIntro = disease.getDisease_complication_intro();

                    isContainsEqual(dIdScore, diseaseName, kwString, i1);
                    isContainsEqual(dIdScore, diseaseAilas, kwString, i2);
                    isContainsEqual(dIdScore, diseaseIntro, kwString, i3);
                    isContainsEqual(dIdScore, diseaseIncidenceSite, kwString, i4);
                    isContainsEqual(dIdScore, diseaseMultiplePeople, kwString, i5);
                    isContainsEqual(dIdScore, diseaseSymptomEarly, kwString, i6);
                    isContainsEqual(dIdScore, diseaseSymptomLate, kwString, i7);
                    isContainsEqual(dIdScore, diseaseSymptomRelated, kwString, i8);
                    isContainsEqual(dIdScore, diseaseComplication, kwString, i9);
                    isContainsEqual(dIdScore, diseaseComplicationIntro, kwString, i10);

                }
            }//end  for (KeyWord keyWord : attributesKWList)
        }//end  for (IDScore dIdScore : dIDScoreList)

        return dIDScoreList;
    }

    /**
     * 对生成的疾病id集进行关键词匹配
     *
     * @param dIdScore
     * @param diseaseContentString
     * @param kwString
     * @param score
     */
    private void isContainsEqual(IDScore dIdScore, String diseaseContentString, String kwString, int score) {
        if (diseaseContentString.contains(kwString)) {
            if (diseaseContentString.equals(kwString)) {
                dIdScore.setScore(dIdScore.getScore() + (score * 2));//包含且相等，加分翻倍
            } else {
                dIdScore.setScore(dIdScore.getScore() + score);
            }
        }
    }


    /**
     * 连续性双词模糊匹配加分
     *
     * @param kwList
     * @param table  0为Symptom，1为Disease
     * @return
     */
    public List<IDScore> doubleWordWFAnalysis(List<IDScore> dIdScoreList, List<KeyWord> kwList, int table) {
        Log.v("诊疗分析", "连续性双词模糊匹配加分!!!!");
        List<KeyWord> doubleWordKwList = new ArrayList<KeyWord>();
        for (int i = 0; i < kwList.size(); i++) {//生成连续性双词集
            if (i != kwList.size() - 1) {
                KeyWord newKeyWord = new KeyWord();
                KeyWord keyWord = kwList.get(i);//获取当前关键词对象
                KeyWord nextKeyWord = kwList.get(i + 1);//获取下一个关键词对象
                newKeyWord.setKeyWordString(keyWord.getKeyWordString() + nextKeyWord.getKeyWordString());//将上方二者关键词内容拼接，并传入新对象中
                doubleWordKwList.add(newKeyWord);
            }
        }
        if (table == 0) {
            return wFSymptomAnalysis(doubleWordKwList, 1);
        } else {
            return wFDiseaseAnalysis(dIdScoreList, doubleWordKwList, 1);

        }
    }

    /**
     * 通过患者描述年龄，性别，易发人群增加结果可信度·
     *
     * @param genderAgeKwList
     * @return
     */
    public void diseaseGenderAgeWFAnalysis(List<IDScore> dIDScoreList, List<KeyWord> genderAgeKwList) {
        Log.v("诊疗分析", "通过患者描述年龄，性别，易发人群增加结果可信度·!!!!");
        for (IDScore dIdScore : dIDScoreList) {
            int disease_id = dIdScore.getObjectId();
            Disease disease = diseaseService.getDiseaseById(disease_id);
            if (disease != null) {
                String diseaseMultiplePeople = disease.getDisease_multiple_people();
                for (KeyWord keyWord : genderAgeKwList) {
                    String kwString = keyWord.getKeyWordString();
                    if (diseaseMultiplePeople.contains(kwString)) {
                        dIdScore.setScore(dIdScore.getScore() + 1);
                    }
                    if (diseaseMultiplePeople.contains("所有人群")) {//如果包含所有人群，该项额外加分
                        dIdScore.setScore(dIdScore.getScore() + 1);
                    }
                }//end   for (KeyWord keyWord : genderAgeKwList)
            }
        }//end for (IDScore dIdScore : dIDScoreList)
    }


    /**
     * 对部位器官进行2单位症状加分
     *
     * @param partOrganKwList
     * @return
     */
    public List<IDScore> symptomPartOrganWFAnalysis(List<KeyWord> partOrganKwList) {
        Log.v("诊疗分析", "对部位器官进行2单位症状加分·!!!!");
        List<IDScore> partOrganIdScoresList = new ArrayList<IDScore>();

        for (KeyWord keyWord : partOrganKwList) {
            String kwString = keyWord.getKeyWordString();
            List<MatchAttribute> symptomOrPaNamePeopleList = symptomOrPaService.getSymptomOrPaByFuzzyName(kwString);
            idAddPoints(partOrganIdScoresList, symptomOrPaNamePeopleList, 1);
        }
        return partOrganIdScoresList;
    }

    /**
     * 对部位器官进行2单位疾病加分
     *
     * @param partOrganKwList
     * @return
     */
    public void diseasePartOrganWFAnalysis(List<IDScore> dIDScoreList, List<KeyWord> partOrganKwList) {
        Log.v("诊疗分析", "对部位器官进行2单位疾病加分·!!!!");
        for (IDScore dIdScore : dIDScoreList) {
            int disease_id = dIdScore.getObjectId();
            Disease disease = diseaseService.getDiseaseById(disease_id);
            if (disease != null) {
                String diseaseIncidenceSite = disease.getDisease_incidence_site();
                if (!TextUtils.isEmpty(diseaseIncidenceSite)) {
                    for (KeyWord keyWord : partOrganKwList) {
                        String kwString = keyWord.getKeyWordString();
                        if (diseaseIncidenceSite.contains(kwString)) {
                            dIdScore.setScore(dIdScore.getScore() + 1);
                        }//end if (diseaseIncidenceSite.contains(kwString))
                    }//end for (KeyWord keyWord : partOrganKwList)
                }
            }
        }//end  for (IDScore dIdScore : dIDScoreList)
    }


    /**
     * 存在并发症加分1（），不存在不加分
     *
     * @param dIdScoresList
     * @return
     */
    public void complicationWFAnalysis(List<IDScore> dIdScoresList, List<KeyWord> diseaseMedicineKwList) {
        Log.v("诊疗分析", "存在并发症加分1（），不存在不加分·!!!!");
        for (IDScore dIdScore : dIdScoresList) {
            int id = dIdScore.getObjectId();
            int score = dIdScore.getScore();
            String complicationString = diseaseService.getComplicationById(id);//查询该疾病中的并发症
            if (!TextUtils.isEmpty(complicationString)) {
                for (KeyWord keyWord : diseaseMedicineKwList) {
                    if (complicationString.contains(keyWord.getKeyWordString())) {
                        dIdScore.setScore(score + 5);
                    }
                }
            }//end if (!TextUtils.isEmpty(complicationString))
        }//end for(IDScore idScore : dIdScoresList)

    }

    /**
     * 症状得出疾病
     *
     * @param sIdScoreList
     * @return
     */
    public List<IDScore> crossWFAnalysis(List<IDScore> sIdScoreList) {
        Log.v("诊疗分析", "症状得出新疾病集·!!!!");
        List<IDScore> dIdScoreList = new ArrayList<IDScore>();
        //由症状集对疾病集进行分析
        for (IDScore sIDScore : sIdScoreList) {
            int symptom_id = sIDScore.getObjectId();
            List<MatchAttribute> diseaseMaList = symptomDiService.getDiBySyId(symptom_id);

            if (diseaseMaList != null && diseaseMaList.size() > 0) {
                for (MatchAttribute ma : diseaseMaList) {
                    dIdScoreList.add(new IDScore(ma.getObjectId(), 1));
                }
            }
        }
        return dIdScoreList;
    }

    /**
     * 对集合进行排序，然后取集合前300个数据，如果数据不满足300，则返回
     *
     * @param oldIDScoreList
     * @return
     */
    public List<IDScore> topRankWFAnalysis(List<IDScore> oldIDScoreList, int top) {
        Log.v("诊疗分析", "对集合进行排序，然后取集合前" + top + "个数据，如果数据不满足" + top + "，则返回!!!!");


        Collections.sort(oldIDScoreList, new Comparator<IDScore>() {
            @Override
            public int compare(IDScore idScore1, IDScore idScore2) {
                int i = idScore2.getScore() - idScore1.getScore();
                if (i == 0) {
                    return idScore2.getObjectId() - idScore1.getObjectId();
                }
                return i;
            }
        });

        List<IDScore> newIDScoreList = new ArrayList<IDScore>();
        if (oldIDScoreList.size() <= top) {
            return oldIDScoreList;
        } else {
            for (int i = 0; i < top; i++) {
                newIDScoreList.add(oldIDScoreList.get(i));
            }
            return newIDScoreList;
        }

    }

    /**
     * 汇总attributeList中元素匹配数量的对应的id，及对其进行加分，
     * 存在一个抉择问题，一个词，在表中字段中可能存在多个包含，那么将会对数据可信度提升，但是同时也会增加数据的干扰。
     * 所以在此处不考虑关键词在表字段中包含的数量问题，只要包含，那么将对其进行一次对应属性加分
     *
     * @param attributeList
     * @param weight
     */
    private void idAddPoints
    (List<IDScore> symptomIdScoresList, List<MatchAttribute> attributeList, int weight) {
        if (attributeList != null) {
            for (MatchAttribute ma : attributeList) {
                int id = ma.getObjectId();
                boolean isExistId = false;//默认不存在
                for (IDScore idScore : symptomIdScoresList) {
                    int oldId = idScore.getObjectId();
                    int oldScore = idScore.getScore();
                    if (oldId == id) {//存在
                        isExistId = true;
                        idScore.setScore(oldScore + weight);
                    }
                }

                if (!isExistId) {// 该集合中不存在， 创建新对象，添加分值
                    symptomIdScoresList.add(new IDScore(id, weight));
                }
            }//end for (MatchAttribute ma : attributeList)
        }//end if (attributeList != null)
    }


    /**
     * 计算字符串中包含关键词数量
     *
     * @param traverseString
     * @param kwString
     * @return
     */
    private int includingQuantity(String traverseString, String kwString) {
        Log.v("诊疗分析", "计算字符串中包含关键词数量!!!!");
        int total = 0;
        for (String tmp = traverseString; tmp != null && tmp.length() >= kwString.length(); ) {
            if (tmp.indexOf(kwString) == 0) {
                total++;
            }
            tmp = tmp.substring(1);
        }
        return total;
    }
}
