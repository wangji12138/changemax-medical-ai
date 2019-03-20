package com.example.wangji.changemax.medical_system.analysis.data;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.wangji.changemax.model.external.IDScore;
import com.example.wangji.changemax.model.internal.Disease;
import com.example.wangji.changemax.model.internal.Symptom;
import com.example.wangji.changemax.service.internal.DiseaseService;
import com.example.wangji.changemax.service.internal.SymptomService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangJi.
 */
public class DataResutlAnalysis {
    private Context myContext;
    private SymptomService symptomService;
    private DiseaseService diseaseService;

    DataResutlAnalysis(Context myContext) {
        this.myContext = myContext;
        symptomService = new SymptomService(myContext);
        diseaseService = new DiseaseService(myContext);
    }

    /**
     * 处理症状d集，生成反馈结果
     *
     * @param idScoreList
     * @return
     */
    public String drSymptomAnalysis(List<IDScore> idScoreList) {
        StringBuilder proneToSymptoms = new StringBuilder("经系统分析，你是否有以下症状？：~\r\n");
        int symptomNumber = 0;

        for (int i = 0; i < idScoreList.size(); i++) {
            int symptom_id = idScoreList.get(i).getObjectId();
            String symptom_name = symptomService.getSymptomNameById(symptom_id);
            if (!TextUtils.isEmpty(symptom_name)) {
                symptomNumber++;

                if (i != (idScoreList.size() - 1)) {
                    proneToSymptoms.append(symptomNumber + ". " + symptom_name + "\r\n");
                } else {
                    proneToSymptoms.append(symptomNumber + ". " + symptom_name);
                }
            }
        }

        StringBuilder stringBuilderSymptomResult = new StringBuilder("");

        stringBuilderSymptomResult.append(proneToSymptoms.toString());

        Symptom theFirstSymptom = symptomService.getSymptomById(idScoreList.get(0).getObjectId());

        if (theFirstSymptom != null) {
            String theFirstSymptomName = theFirstSymptom.getSymptom_name();
            String theFirstSymptomIntro = streamlinedText(theFirstSymptom.getSymptom_intro());
            String theFirstSymptomCause = streamlinedText(theFirstSymptom.getSymptom_cause());
            String theFirstSymptomDepartment = theFirstSymptom.getSuggested_treatment_department();

            stringBuilderSymptomResult.append("---");
            if (!TextUtils.isEmpty(theFirstSymptomName) && !TextUtils.isEmpty(theFirstSymptomIntro)) {
                stringBuilderSymptomResult.append("您可以简单了解一下“" + theFirstSymptomName + "“：~\r\n" + theFirstSymptomIntro);
                stringBuilderSymptomResult.append("---");
            }
            if (!TextUtils.isEmpty(theFirstSymptomName) && !TextUtils.isEmpty(theFirstSymptomCause)) {
                stringBuilderSymptomResult.append("“" + theFirstSymptomName + "“的病因：~\r\n" + theFirstSymptomCause);
                stringBuilderSymptomResult.append("---");
            }

            if (!TextUtils.isEmpty(theFirstSymptomName)) {
                stringBuilderSymptomResult.append("需要详细了解###" + theFirstSymptomName + "###信息，点击我哦~");
                stringBuilderSymptomResult.append("---");
            }

            if (!TextUtils.isEmpty(theFirstSymptomName) && !TextUtils.isEmpty(theFirstSymptomDepartment)) {
                stringBuilderSymptomResult.append("如果“" + theFirstSymptomName + "“明显，咱还是建议您去医疗机构看看哦，建议你去以下科室问诊：~\r\n" + theFirstSymptomDepartment);
                stringBuilderSymptomResult.append("---");
            }
        }//end  if (theFirstSymptom != null)


        return stringBuilderSymptomResult.toString();
    }


    /**
     * 处理疾病id集，生成反馈结果
     *
     * @param idScoreList
     * @return
     */
    public String drDiseaseAnalysis(List<IDScore> idScoreList) {

        StringBuilder proneToDiseases = new StringBuilder("经系统分析，你容易发生以下疾病：~\r\n");
        int diseaseNumber = 0;
        for (int i = 0; i < idScoreList.size(); i++) {
            int disease_id = idScoreList.get(i).getObjectId();
            String disease_name = diseaseService.getDiseaseNameById(disease_id);
            if (!TextUtils.isEmpty(disease_name)) {
                diseaseNumber++;
                if (i != (idScoreList.size() - 1)) {
                    proneToDiseases.append(diseaseNumber + ". " + disease_name + "\r\n");
                } else {
                    proneToDiseases.append(diseaseNumber + ". " + disease_name);
                }
            }
        }


        StringBuilder stringBuilderDiseaseResult = new StringBuilder("");

        stringBuilderDiseaseResult.append(proneToDiseases.toString());

        Disease theFirstDisease = diseaseService.getDiseaseById(idScoreList.get(0).getObjectId());

        if (theFirstDisease != null) {
            String theFirstDiseaseName = theFirstDisease.getDisease_name();
            String theFirstDiseaseIntro = streamlinedText(theFirstDisease.getDisease_intro());
            String theFirstDiseaseSymptomIntro = streamlinedText(theFirstDisease.getDisease_symptom_intro());
            String theFirstDiseaseComplicationIntro = streamlinedText(theFirstDisease.getDisease_complication_intro());
            String theFirstDiseaseDepartment = theFirstDisease.getDisease_visit_department();

            stringBuilderDiseaseResult.append("---");

            if (!TextUtils.isEmpty(theFirstDiseaseName) && !TextUtils.isEmpty(theFirstDiseaseIntro)) {
                stringBuilderDiseaseResult.append("您可以简单了解一下“" + theFirstDiseaseName + "”：~\r\n" + theFirstDiseaseIntro);
                stringBuilderDiseaseResult.append("---");
            }


            if (!TextUtils.isEmpty(theFirstDiseaseName) && !TextUtils.isEmpty(theFirstDiseaseSymptomIntro)) {
                stringBuilderDiseaseResult.append("“" + theFirstDiseaseName + "”主要症状：~\r\n" + theFirstDiseaseSymptomIntro);
                stringBuilderDiseaseResult.append("---");
            }

            if (!TextUtils.isEmpty(theFirstDiseaseName) && !TextUtils.isEmpty(theFirstDiseaseComplicationIntro)) {
                stringBuilderDiseaseResult.append("“" + theFirstDiseaseName + "”有一些并发症：~\r\n" + theFirstDiseaseComplicationIntro);
                stringBuilderDiseaseResult.append("---");
            }


            if (!TextUtils.isEmpty(theFirstDiseaseName)) {
                stringBuilderDiseaseResult.append("需要详细了解***" + theFirstDiseaseName + "***信息，点击我哦~");
                stringBuilderDiseaseResult.append("---");
            }
            if (!TextUtils.isEmpty(theFirstDiseaseName) && !TextUtils.isEmpty(theFirstDiseaseDepartment)) {
                stringBuilderDiseaseResult.append("“" + theFirstDiseaseName + "”疾病咱还是建议您去医疗机构看看哦，建议你去以下科室问诊：~\r\n" + theFirstDiseaseDepartment);
                stringBuilderDiseaseResult.append("---");
                stringBuilderDiseaseResult.append("点击我可以帮你找周围的医院YY哦~");
                stringBuilderDiseaseResult.append("---");
            }

        }//end  if (theFirstDisease != null)


        return stringBuilderDiseaseResult.toString();
    }


    private String streamlinedText(String text) {
        int maxLength = 100;
        if (!TextUtils.isEmpty(text)) {
            if (text.length() > maxLength) {
                return text.substring(0, maxLength - 3) + "......";
            } else {
                return text;
            }
        } else {
            return "";
        }
    }

    public String drAnalysis2(List<IDScore> idScoreList, int table) {
        StringBuffer stringBuffer = new StringBuffer("");
        for (IDScore idScore : idScoreList) {
            stringBuffer.append(idScore.toString() + "---");
        }
        Log.v("DataResutlAnalysis", stringBuffer.toString());

        StringBuilder sb = new StringBuilder("");
        int i = 0;
        if (table == 0) {
            sb.append("根据系统分析：你是否存在以下现象：~\r\n ");
            for (IDScore idScore : idScoreList) {
                i++;
                int id = idScore.getObjectId();
                if (id != 0) {
                    Symptom symptom = symptomService.getSymptomById(id);
                    if (symptom != null) {
                        String name = symptom.getSymptom_name();
                        String s = symptom.getSymptomatic_details_content();
                        sb.append(i + "：" + name + "   ");
                        if (i % 2 == 0) {
                            sb.append("\r\n");
                        }
                    }
                }
            }


            //分析分数排名第一id
            int numberOneId = idScoreList.get(0).getObjectId();

            Symptom numberOneSymptom = symptomService.getSymptomById(numberOneId);
            if (numberOneSymptom != null) {
                String symptom_cause = numberOneSymptom.getSymptom_cause();
                if (!TextUtils.isEmpty(symptom_cause)) {
                    sb.append("---" + symptom_cause);
                }
                String symptomatic_details_content = numberOneSymptom.getSymptomatic_details_content();
                if (!TextUtils.isEmpty(symptomatic_details_content)) {
                    sb.append("---" + symptomatic_details_content);
                }
            }

            sb.append("---请注意身体哦。健康才是一切的本钱。");
        } else {
            sb.append("根据系统分析：你易发生以下疾病：\r\n ");
            for (IDScore idScore : idScoreList) {
                i++;
                int id = idScore.getObjectId();
                if (id != 0) {
                    Disease disease = diseaseService.getDiseaseById(id);
                    if (disease != null) {
                        String name = disease.getDisease_name();
                        String intro = disease.getDisease_intro();
                        sb.append(i + "：" + name + "   ");
                        if (i % 2 == 0) {
                            sb.append("\r\n");
                        }
                    }
                }
            }//end  for (IDScore idScore : idScoreList)

            sb.append("---亲，我说的仅供参考的，最好的方法还是去医院找专业的医师看看哦。");

            //分析分数排名第一id
            int numberOneId = idScoreList.get(0).getObjectId();

            Disease numberOneDisease = diseaseService.getDiseaseById(numberOneId);
            if (numberOneDisease != null) {
                if (numberOneDisease != null) {
                    String disease_name = numberOneDisease.getDisease_name();//疾病名称
                    String disease_intro = numberOneDisease.getDisease_intro();//疾病简介
                    if (!TextUtils.isEmpty(disease_name) && !TextUtils.isEmpty(disease_intro)) {
                        sb.append("---" + "【" + disease_name + "】" + "：" + disease_intro);
                    }

                    String disease_multiple_people = numberOneDisease.getDisease_multiple_people();//疾病易发人群
                    String disease_contagious = numberOneDisease.getDisease_contagious();//疾病传染性
                    if (!TextUtils.isEmpty(disease_name) && !TextUtils.isEmpty(disease_multiple_people) && !TextUtils.isEmpty(disease_contagious)) {
                        sb.append("---" + "疾病易发人群：" + disease_multiple_people + "\r\n"
                                + "疾病传染性：" + "：" + disease_contagious);
                    }

                    String disease_visit_department = numberOneDisease.getDisease_visit_department();//疾病建议科室
                    if (!TextUtils.isEmpty(disease_name) && !TextUtils.isEmpty(disease_visit_department)) {
                        sb.append("---" + "疾病建议您去医院以下科室看一看：" + "：" + disease_visit_department);
                    }

                }
            }

        }
        return sb.toString();
    }

}
