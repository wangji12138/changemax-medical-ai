package com.example.wangji.changemax.medical_system.analysis.data;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.wangji.changemax.model.external.IDScore;
import com.example.wangji.changemax.model.external.KeyWord;
import com.example.wangji.changemax.model.external.Question;
import com.example.wangji.changemax.service.external.QuestionService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 数据分析
 * Created by WangJi.
 */

public class DataAnalysis {
    private Context myContext;
    private WordFrequencyAnalysis wfAnalysis;//词频分析对象

    private DataResutlAnalysis drAnalysis;//结果分析处理对象；


    private QuestionService questionService;

    public DataAnalysis(Context myContext) {
        this.myContext = myContext;
    }

    public String dataAnalysis(Map<String, List<KeyWord>> kwListMap) {
        /**
         * 系统性诊疗综合分析数据
         *          通过对各数据表进行对应属性模糊匹配，得到模糊匹配的id集以及对应属性，然后进行交叉分析，增加结果可信度
         *          基于评分模型对其进行筛选，该模型的目标是评价候选答案可信度
         *              前者预分析出
         *                  1.称呼减负载关键词集，2.性别减负载关键词集，3.年龄减负载关键词集，4.部位器官减负载关键词集，
         *                  5.症状医学关键词集，6.症状减负载关键词集，7.疾病医学关键词集，8.疾病减负载关键词集
         *
         *            注意：本系统分析数据为全局分析（全库分析），所以存在一个系统负载问题，但是不会对结果集造成大量删减
         *                         系统分支分析结果均未排序，结果分析末端选择取值时才排序
         *                            1.对用户的症状描述集进行精准分析用户的症状
         *                            2.对用户的症状描述集进行模糊分析用户的症状
         *                            3.对用户的疾病描述集进行精准分析用户的症状
         *                            4.对用户的疾病描述集进行模糊分析用户的症状
         *                            5.对用户的症状减负载集进行连续性双词模糊匹配分析用户的症状
         *                            6.对用户的疾病减负载集进行连续性双词模糊匹配分析用户的症状
         *                            7.对患者所描述的患处部位减负载集进行模糊分析用户的症状
         *                            8.取分值症状集合前300替换原分值症状集合
         *
         *                         注意：以下分析前提均为有症状分析出来的可能病症集下分析，不能进行新增
         *                            9.构建疾病id集
         *                            10.传递分析：症状得出疾病
         *                            11.对用户的症状描述集进行精准分析出用户的疾病集，进行加分
         *                            12.对用户的症状描述集进行模糊分析出用户的疾病集，进行加分
         *                            13.对用户的疾病描述集进行精准分析出用户的疾病集，进行加分
         *                            14.对用户的疾病描述集进行模糊分析出用户的疾病集，进行加分
         *                            15.对用户的患者性别描述分析用户的疾病集，进行加分
         *                            16.对用户的患者年龄描述分析用户的疾病集，进行加分
         *                            17.对用户的患者部位器官描述分析用户的疾病集，进行加分
         *                            18.并发症分析，对用户的患有疾病分析用户的疾病集，进行加分
         *                            19.取分值集合前100替换原分值集合
         *                            20.分析id集，处理出结果
         */
        wfAnalysis = new WordFrequencyAnalysis(myContext);//实例化词频分析对象wfAnalysis

        List<KeyWord> nameBurdenKwList = kwListMap.get("name");//称呼减负载关键词集
        List<KeyWord> genderBurdenKwList = kwListMap.get("gender");//性别减负载关键词集
        List<KeyWord> ageBurdenKwList = kwListMap.get("age");//年龄减负载关键词集
        List<KeyWord> partOrganBurdenKwList = kwListMap.get("partOrgan");//部位器官减负载关键词集

        List<KeyWord> symptomMedicineKwList = kwListMap.get("symptomM");//症状医学关键词集
        List<KeyWord> symptomBurdenKwList = kwListMap.get("symptomB");//症状减负载关键词集
        List<KeyWord> diseaseMedicineKwList = kwListMap.get("diseaseM");//疾病医学关键词集
        List<KeyWord> diseaseBurdenKwList = kwListMap.get("diseaseB");//疾病减负载关键词集


        //1.对用户的症状描述集进行精准分析用户的症状
        List<IDScore> sIdScoresList = new ArrayList<IDScore>();
        if (symptomMedicineKwList != null && symptomMedicineKwList.size() > 0) {
            Log.v("系统性诊疗分析", "sIdScoresList----symptomMedicineKwList !!!!");
            List<IDScore> sMIdScoresList = wfAnalysis.wFSymptomAnalysis(symptomMedicineKwList, 2);
            mergeIDScoreList(sIdScoresList, sMIdScoresList);

              /*
         在系统分析过程中，会出现大量id，且出现一定分值差距，需对其进行精简，减少后期对比，系统负担
             */
            sIdScoresList = wfAnalysis.topRankWFAnalysis(sIdScoresList, 500);
        }

        //2.对用户的症状描述集进行模糊分析用户的症状
        if (symptomBurdenKwList != null && symptomBurdenKwList.size() > 0) {
            Log.v("系统性诊疗分析", "sBIdScoresList----symptomBurdenKwList !!!!");
            List<IDScore> sBIdScoresList = wfAnalysis.wFSymptomAnalysis(symptomBurdenKwList, 1);
            mergeIDScoreList(sIdScoresList, sBIdScoresList);

              /*
         在系统分析过程中，会出现大量id，且出现一定分值差距，需对其进行精简，减少后期对比，系统负担
             */
            sIdScoresList = wfAnalysis.topRankWFAnalysis(sIdScoresList, 500);
        }

        //3.对用户的疾病描述集进行精准分析用户的症状
        if (diseaseMedicineKwList != null && diseaseMedicineKwList.size() > 0) {
            Log.v("系统性诊疗分析", "sMIdScoresList----diseaseMedicineKwList !!!!");
            List<IDScore> sMIdScoresList = wfAnalysis.wFSymptomAnalysis(diseaseMedicineKwList, 2);
            mergeIDScoreList(sIdScoresList, sMIdScoresList);

              /*
         在系统分析过程中，会出现大量id，且出现一定分值差距，需对其进行精简，减少后期对比，系统负担
             */
            sIdScoresList = wfAnalysis.topRankWFAnalysis(sIdScoresList, 500);
        }

        //4.对用户的疾病描述集进行模糊分析用户的症状
        if (diseaseBurdenKwList != null && diseaseBurdenKwList.size() > 0) {
            Log.v("系统性诊疗分析", "sBIdScoresList----diseaseBurdenKwList !!!!");
            List<IDScore> sBIdScoresList = wfAnalysis.wFSymptomAnalysis(diseaseBurdenKwList, 1);
            mergeIDScoreList(sIdScoresList, sBIdScoresList);

              /*
         在系统分析过程中，会出现大量id，且出现一定分值差距，需对其进行精简，减少后期对比，系统负担
             */
            sIdScoresList = wfAnalysis.topRankWFAnalysis(sIdScoresList, 500);
        }


        //5.对用户的症状减负载集进行连续性双词模糊匹配分析用户的症状
        if (symptomBurdenKwList != null && symptomBurdenKwList.size() > 1) {
            Log.v("系统性诊疗分析", "sWSNIdScoresList----symptomBurdenKwList !!!!");
            List<IDScore> sWSNIdScoresList = wfAnalysis.doubleWordWFAnalysis(null, symptomBurdenKwList, 0);
            mergeIDScoreList(sIdScoresList, sWSNIdScoresList);

              /*
         在系统分析过程中，会出现大量id，且出现一定分值差距，需对其进行精简，减少后期对比，系统负担
             */
            sIdScoresList = wfAnalysis.topRankWFAnalysis(sIdScoresList, 500);
        }

        //6.对用户的疾病减负载集进行连续性双词模糊匹配分析用户的症状
        if (diseaseBurdenKwList != null && diseaseBurdenKwList.size() > 1) {
            Log.v("系统性诊疗分析", "sWSNIdScoresList----diseaseBurdenKwList !!!!");
            List<IDScore> sWSNIdScoresList = wfAnalysis.doubleWordWFAnalysis(null, diseaseBurdenKwList, 0);
            mergeIDScoreList(sIdScoresList, sWSNIdScoresList);

              /*
         在系统分析过程中，会出现大量id，且出现一定分值差距，需对其进行精简，减少后期对比，系统负担
             */
            sIdScoresList = wfAnalysis.topRankWFAnalysis(sIdScoresList, 500);
        }

        //7.对患者所描述的患处部位减负载集进行模糊分析用户的症状
        if (partOrganBurdenKwList != null && partOrganBurdenKwList.size() > 0) {
            Log.v("系统性诊疗分析", "partOrganIdScoresList----partOrganBurdenKwList !!!!");
            List<IDScore> partOrganIdScoresList = wfAnalysis.symptomPartOrganWFAnalysis(partOrganBurdenKwList);
            mergeIDScoreList(sIdScoresList, partOrganIdScoresList);

              /*
         在系统分析过程中，会出现大量id，且出现一定分值差距，需对其进行精简，减少后期对比，系统负担
             */
            sIdScoresList = wfAnalysis.topRankWFAnalysis(sIdScoresList, 500);
        }


        //8.取分值症状集合前100替换原分值症状集合
        sIdScoresList = wfAnalysis.topRankWFAnalysis(sIdScoresList, 100);
        Log.v("系统性诊疗分析", "sIdScoresList----topRankWFAnalysis !!!!");


        //9.构建疾病id集
        List<IDScore> dIdScoresList = new ArrayList<IDScore>();
        //10.传递分析：症状得出疾病
        List<IDScore> dCrossIdScoresList = wfAnalysis.crossWFAnalysis(sIdScoresList);
        if (dCrossIdScoresList != null && dCrossIdScoresList.size() > 0) {
            Log.v("系统性诊疗分析", "dIdScoresList----dIdScoresList， dCrossIdScoresList!!!!");
            mergeIDScoreList(dIdScoresList, dCrossIdScoresList);
        }


        //12.对用户的症状描述集进行模糊分析出用户的疾病集，进行加分
        //对用户的疾病集进行加分操作，不会产生新IDScore，不需要排序，筛选，但是由于是第一次，
        if (symptomBurdenKwList != null && symptomBurdenKwList.size() > 0) {
            Log.v("系统性诊疗分析", "dIdScoresList----dIdScoresList， symptomBurdenKwList!!!!");
            dIdScoresList = wfAnalysis.wFDiseaseAnalysis(dIdScoresList, symptomBurdenKwList, 2);

                   /*
         在系统分析过程中，会出现大量id，且出现一定分值差距，需对其进行精简，减少后期对比，系统负担
             */
            sIdScoresList = wfAnalysis.topRankWFAnalysis(sIdScoresList, 500);
        }

        //11.对用户的症状描述集进行精准分析出用户的疾病集，进行加分
        //对用户的疾病集进行加分操作，不会产生新IDScore，不需要排序，筛选
        if (symptomMedicineKwList != null && symptomMedicineKwList.size() > 0) {
            Log.v("系统性诊疗分析", "dIdScoresList----dIdScoresList， symptomMedicineKwList!!!!");
            dIdScoresList = wfAnalysis.wFDiseaseAnalysis(dIdScoresList, symptomMedicineKwList, 1);
        }


        //13.对用户的疾病描述集进行精准分析出用户的疾病集，进行加分
        //对用户的疾病集进行加分操作，不会产生新IDScore，不需要排序，筛选
        if (diseaseMedicineKwList != null && diseaseMedicineKwList.size() > 0) {
            Log.v("系统性诊疗分析", "dIdScoresList----dIdScoresList， diseaseMedicineKwList!!!!");
            dIdScoresList = wfAnalysis.wFDiseaseAnalysis(dIdScoresList, diseaseMedicineKwList, 1);
        }

        //14.对用户的疾病描述集进行模糊分析出用户的疾病集，进行加分
        if (diseaseBurdenKwList != null && diseaseBurdenKwList.size() > 1) {
            Log.v("系统性诊疗分析", "dIdScoresList----dIdScoresList， diseaseBurdenKwList!!!!");
            dIdScoresList = wfAnalysis.doubleWordWFAnalysis(dIdScoresList, diseaseBurdenKwList, 1);

                   /*
         在系统分析过程中，会出现大量id，且出现一定分值差距，需对其进行精简，减少后期对比，系统负担
             */
            sIdScoresList = wfAnalysis.topRankWFAnalysis(sIdScoresList, 500);
        }

        //15.对用户的患者性别描述分析用户的疾病集，进行加分
        //对用户的疾病集进行加分操作，不会产生新IDScore，不需要排序，筛选
        if (genderBurdenKwList != null && genderBurdenKwList.size() > 0) {
            Log.v("系统性诊疗分析", "dIdScoresList----dIdScoresList， genderBurdenKwList!!!!");
            wfAnalysis.diseaseGenderAgeWFAnalysis(dIdScoresList, genderBurdenKwList);
        }

        //16.对用户的患者年龄描述分析用户的疾病集，进行加分
        //对用户的疾病集进行加分操作，不会产生新IDScore，不需要排序，筛选
        if (ageBurdenKwList != null && ageBurdenKwList.size() > 0) {
            Log.v("系统性诊疗分析", "dIdScoresList----dIdScoresList， ageBurdenKwList!!!!");
            wfAnalysis.diseaseGenderAgeWFAnalysis(dIdScoresList, ageBurdenKwList);
        }

        //17.对用户的患者部位器官描述分析用户的疾病集，进行加分
        //对用户的疾病集进行加分操作，不会产生新IDScore，不需要排序，筛选
        if (partOrganBurdenKwList != null && partOrganBurdenKwList.size() > 0) {
            Log.v("系统性诊疗分析", "dIdScoresList----dIdScoresList， partOrganBurdenKwList!!!!");
            wfAnalysis.diseasePartOrganWFAnalysis(dIdScoresList, partOrganBurdenKwList);
        }

        //18.并发症分析，对用户的患有疾病分析用户的疾病集，进行加分
        //对用户的疾病集进行加分操作，不会产生新IDScore，不需要排序，筛选
        if (diseaseMedicineKwList != null && diseaseMedicineKwList.size() > 0) {
            wfAnalysis.complicationWFAnalysis(dIdScoresList, diseaseMedicineKwList);
        }

        //19.取分值集合前100替换原分值集合
        Log.v("系统性诊疗分析", "dIdScoresList----topRankWFAnalysis!!!!");
        dIdScoresList = wfAnalysis.topRankWFAnalysis(dIdScoresList, 10);
        Log.v("系统性诊疗分析", "sIdScoresList----topRankWFAnalysis!!!!");
        sIdScoresList = wfAnalysis.topRankWFAnalysis(sIdScoresList, 10);


        //20.分析id集，处理出结果
        drAnalysis = new DataResutlAnalysis(myContext); //实例化数据结果集处理对象

        StringBuilder systemAnalysisResult = new StringBuilder("");
        if (sIdScoresList != null && sIdScoresList.size() > 0) {
            String symptomResult = drAnalysis.drSymptomAnalysis(sIdScoresList);
            systemAnalysisResult.append(symptomResult);
        }
        if (dIdScoresList != null && dIdScoresList.size() > 0) {
            String diseaseResult = drAnalysis.drDiseaseAnalysis(dIdScoresList);
            systemAnalysisResult.append(diseaseResult);
        }


        return systemAnalysisResult.toString();
    }


    public String dataAnalysis(String uid) {
        /*
          常规性诊疗综合分析数据
                    通过对各数据表进行对应属性模糊匹配，得到模糊匹配的id集以及对应属性，然后进行交叉分析，增加结果可信度
                    基于评分模型对其进行筛选，该模型的目标是评价候选答案可信度
                        前者预分析出
                            1.所有词词集；2.减负载关键词词集；3.医疗关键词词集


                    注意：本系统分析数据为全局分析（全库分析），所以存在一个系统负载问题，但是不会对结果集造成大量删减
                                   系统分支分析结果均未排序，结果分析末端选择取值时才排序
                                     //获取各集对象
                                     //1.对用户的症状描述集进行精准模糊分析用户的症状(精简)
                                     //2.对用户的症状描述集进行模糊分析用户的症状(精简)
                                     //3.对用户的症状减负载集进行连续性双词模糊匹配分析用户的症状(精简)
                                     //4.对患者所描述的患处部位减负载集进行模糊分析用户的症状

                                     //5.取分值症状集合前5替换原分值症状集合，为最终结果

                                     //6.构建疾病id集
                                     //7.传递分析：症状得出疾病
                                     //8.对用户的症状描述集进行精准分析出用户的疾病集，进行加分(精简)

                                     //9.对用户的症状描述集进行模糊分析用户的疾病集，进行用户疾病加分
                                     //对用户的疾病集进行加分操作，不会产生新IDScore，不需要排序，筛选

                                     //10.对用户的疾病减负载集进行连续性双词模糊匹配分析用户的症状(精简)
                                     （连续性双词：指连续两个关键词合成一个关键词）

                                     //11.对用户的患者部位器官描述分析用户的疾病集，进行加分
                                     //对用户的疾病集进行加分操作，不会产生新IDScore，不需要排序，筛选

                                     //12.取分值集合前10替换原分值集合

                                     //14.分析id集，处理出结果

                                     //15.反馈数据分析结果

                                     (以上精简：在系统分析过程中，会出现大量id，且出现一定分值差距，需对其进行精简，减少后期对比，系统负担)
            */
        questionService = new QuestionService(myContext);
        Question newQuestion = questionService.read(uid);
        Log.v("常规性诊疗AnalysisQuestion", newQuestion.toString());

        wfAnalysis = new WordFrequencyAnalysis(myContext);//实例化词频分析对象wfAnalysis

        //获取各集对象
        List<KeyWord> allWList = newQuestion.getAllWList(); //所有词词集（未删减）
        List<KeyWord> burdenKwList = newQuestion.getBurdenKwList();//减负载关键词词集
        List<KeyWord> medicineKwList = newQuestion.getMedicineKwList(); //医学关键词集


        //1.对用户的症状描述集进行精准模糊分析用户的症状
        List<IDScore> sIdScoresList = new ArrayList<IDScore>();
        if (medicineKwList != null && medicineKwList.size() > 0) {
            Log.v("常规性诊疗分析", "medicineKwList!!!!");
            List<IDScore> sMIdScoresList = wfAnalysis.wFSymptomAnalysis(medicineKwList, 2);
            mergeIDScoreList(sIdScoresList, sMIdScoresList);

                  /*
         在系统分析过程中，会出现大量id，且出现一定分值差距，需对其进行精简，减少后期对比，系统负担
             */
            sIdScoresList = wfAnalysis.topRankWFAnalysis(sIdScoresList, 500);
        }


        //2.对用户的症状描述集进行模糊分析用户的症状
        if (burdenKwList != null && burdenKwList.size() > 0) {
            Log.v("常规性诊疗分析", "wFSymptomAnalysis!!!!");
            List<IDScore> sBIdScoresList = wfAnalysis.wFSymptomAnalysis(burdenKwList, 1);
            mergeIDScoreList(sIdScoresList, sBIdScoresList);

            /*
            在系统分析过程中，会出现大量id，且出现一定分值差距，需对其进行精简，减少后期对比，系统负担
             */
            sIdScoresList = wfAnalysis.topRankWFAnalysis(sIdScoresList, 500);
        }

        //3.对用户的症状减负载集进行连续性双词模糊匹配分析用户的症状
        if (burdenKwList != null && burdenKwList.size() > 1) {
            Log.v("常规性诊疗分析", "doubleWordWFAnalysis!!!!");
            List<IDScore> sWSNIdScoresList = wfAnalysis.doubleWordWFAnalysis(sIdScoresList, burdenKwList, 0);
            mergeIDScoreList(sIdScoresList, sWSNIdScoresList);

                 /*
         在系统分析过程中，会出现大量id，且出现一定分值差距，需对其进行精简，减少后期对比，系统负担
             */
            sIdScoresList = wfAnalysis.topRankWFAnalysis(sIdScoresList, 500);
        }

        //4.对患者所描述的患处部位减负载集进行模糊分析用户的症状
        if (burdenKwList != null && burdenKwList.size() > 0) {
            Log.v("常规性诊疗分析", "symptomPartOrganWFAnalysis!!!!");
            List<IDScore> partOrganIdScoresList = wfAnalysis.symptomPartOrganWFAnalysis(burdenKwList);
            mergeIDScoreList(sIdScoresList, partOrganIdScoresList);
        }


        //5.取分值症状集合前100替换原分值症状集合，为最终结果
        Log.v("常规性诊疗分析", "topRankWFAnalysis!!!!");
        sIdScoresList = wfAnalysis.topRankWFAnalysis(sIdScoresList, 100);


        //6.构建疾病id集
        List<IDScore> dIdScoresList = null;
        //7.传递分析：症状得出疾病
        List<IDScore> dCrossIdScoresList = wfAnalysis.crossWFAnalysis(sIdScoresList);
        if (dCrossIdScoresList != null && dCrossIdScoresList.size() > 0) {
            Log.v("常规性诊疗分析", "mergeIDScoreList!!!!");
            dIdScoresList = dCrossIdScoresList;

        }


        //8.对用户的症状描述集进行精准分析出用户的疾病集，进行加分
        if (medicineKwList != null && medicineKwList.size() > 0) {
            Log.v("常规性诊疗分析", "wFDiseaseAnalysis!!!!");
            dIdScoresList = wfAnalysis.wFDiseaseAnalysis(dIdScoresList, medicineKwList, 2);

            /*
            在系统分析过程中，会出现大量id，且出现一定分值差距，需对其进行精简，减少后期对比，系统负担
             */
            dIdScoresList = wfAnalysis.topRankWFAnalysis(dIdScoresList, 500);
        }


        //9.对用户的症状描述集进行模糊分析用户的疾病集，进行用户疾病加分
        //加分筛选没有产生新IdScore
        if (burdenKwList != null && burdenKwList.size() > 0) {
            Log.v("常规性诊疗分析", "wFDiseaseAnalysis!!!!");
            dIdScoresList = wfAnalysis.wFDiseaseAnalysis(dIdScoresList, burdenKwList, 1);
        }


        //10.对用户的疾病减负载集进行连续性双词模糊匹配分析用户的症状
        if (burdenKwList != null && burdenKwList.size() > 1) {
            Log.v("常规性诊疗分析", "doubleWordWFAnalysis!!!!");
            List<IDScore> dWSNIdScoresList = wfAnalysis.doubleWordWFAnalysis(dIdScoresList, burdenKwList, 1);
            mergeIDScoreList(dIdScoresList, dWSNIdScoresList);

                 /*
         在系统分析过程中，会出现大量id，且出现一定分值差距，需对其进行精简，减少后期对比，
             */
            dIdScoresList = wfAnalysis.topRankWFAnalysis(dIdScoresList, 500);
        }


        //11.对用户的患者部位器官描述分析用户的疾病集，进行加分
        //对用户的疾病集进行加分操作，不会产生新IDScore，不需要排序，筛选
        if (burdenKwList != null && burdenKwList.size() > 0) {
            Log.v("常规性诊疗分析", "diseasePartOrganWFAnalysis!!!!");
            wfAnalysis.diseasePartOrganWFAnalysis(dIdScoresList, burdenKwList);
        }


        //13.取分值集合前10替换原分值集合
        Log.v("常规性诊疗分析", "topRankWFAnalysis!!!!");
        dIdScoresList = wfAnalysis.topRankWFAnalysis(dIdScoresList, 10);
        sIdScoresList = wfAnalysis.topRankWFAnalysis(sIdScoresList, 10);


        //14.分析id集，处理出结果
        drAnalysis = new DataResutlAnalysis(myContext); //实例化数据结果集处理对象

        StringBuilder systemAnalysisResult = new StringBuilder("");
        if (sIdScoresList != null && sIdScoresList.size() > 0) {
            String symptomResult = drAnalysis.drSymptomAnalysis(sIdScoresList);
            systemAnalysisResult.append(symptomResult);
        }
        if (dIdScoresList != null && dIdScoresList.size() > 0) {
            String diseaseResult = drAnalysis.drDiseaseAnalysis(dIdScoresList);
            systemAnalysisResult.append(diseaseResult);
        }


        return systemAnalysisResult.toString();

    }

    /**
     * 合并IDScore集，未排序
     *
     * @param bigList
     * @param smallList
     * @return
     */
    private void mergeIDScoreList(List<IDScore> bigList, List<IDScore> smallList) {
        Log.v("诊疗分析", "合并IDScore集，未排序!!!!");
        if (smallList != null && smallList.size() > 0) {
            for (IDScore smallIDScore : smallList) {
                int smallId = smallIDScore.getObjectId();
                int smallScore = smallIDScore.getScore();
                boolean isExistId = false;//默认在该id集中不包含该id对象
                for (IDScore bigIDScore : bigList) {
                    int bigId = bigIDScore.getObjectId();
                    int bigScore = bigIDScore.getScore();
                    if (bigId == smallId) {//id相同，为同一内容
                        isExistId = true;
                        bigIDScore.setScore(bigScore + smallScore);
                        break;
                    }
                }//end for()
                if (!isExistId) {//如果该集合中不存在，那么将创建新对象，添加
                    bigList.add(smallIDScore);
                }
            }//end for (IDScore smallIDScore : smallList)
        }//end if (smallList != null && smallList.size() > 0)
    }


}
