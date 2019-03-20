package com.example.wangji.changemax.medical_system.pretreatment;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.wangji.changemax.model.external.KeyWord;
import com.example.wangji.changemax.model.external.Patient;
import com.example.wangji.changemax.model.external.Question;
import com.example.wangji.changemax.service.external.PatientService;
import com.example.wangji.changemax.service.external.QuestionService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 描述预处理分析
 * Created by WangJi.
 */
public class DescriptionPretreatmentAnalysis {
    private Context myContext;
    private LtpCloudAnalysis lca;
    private PatientService patientService;
    private QuestionService questionService;

    public DescriptionPretreatmentAnalysis(Context myContext) {
        this.myContext = myContext;
        lca = new LtpCloudAnalysis();
    }

    /**
     * 对系统性问诊对象进行预处理分析线程池分析
     *
     * @param lookAndAskUID
     * @return
     */
    public Map<String, List<KeyWord>> dpBySystemAnalysisExecutors(String lookAndAskUID) {
        patientService = new PatientService(myContext);
        Patient patient = patientService.read(lookAndAskUID);
        if (patient == null) {
            return null;
        }
        String patientName = patient.getPatientName();//获取患者姓名
        String patientGender = patient.getPatientGender();//获取患者性别
        String patientAge = patient.getPatientAge();//获取患者年龄
        String patientPartOrgan = patient.getPatientPartOrgan();//获取患者疼痛部位
        String symptomString = patient.getSymptomString();//获取患者症状描述
        String diseaseString = patient.getDiseaseString();//获取患者疾病描述
        Log.v("系统性诊疗分析", patient.toString());


        //1.自然语言分析云分析
        Log.v("系统性诊疗分析", "自然语言分析云分析!!!!");
        Executor executor = Executors.newSingleThreadExecutor();//开启线程池Executor框架

        Log.v("系统性诊疗分析", "姓名词性标注!!!!");
        DpFutureTask nameDpFutureTask = new DpFutureTask(executor, patientName);
        List<KeyWord> allNameKwList = nameDpFutureTask.getFutureTask();
        if (allNameKwList == null) {//存在错误信息
            Log.v("系统性诊疗分析", nameDpFutureTask.getErrorMessage());
        }

        Log.v("系统性诊疗分析", "性别词性标注!!!!");
        DpFutureTask genderDpFutureTask = new DpFutureTask(executor, patientGender);
        List<KeyWord> allGenderKwList = genderDpFutureTask.getFutureTask();
        if (allGenderKwList == null) {//存在错误信息
            Log.v("系统性诊疗分析", genderDpFutureTask.getErrorMessage());
        }

        Log.v("系统性诊疗分析", "年龄词性标注!!!!");
        DpFutureTask ageDpFutureTask = new DpFutureTask(executor, patientAge);
        List<KeyWord> allAgeKwList = ageDpFutureTask.getFutureTask();
        if (allAgeKwList == null) {//存在错误信息
            Log.v("系统性诊疗分析", ageDpFutureTask.getErrorMessage());
        }

        Log.v("系统性诊疗分析", "部位器官词性标注!!!!");
        DpFutureTask partOrganDpFutureTask = new DpFutureTask(executor, patientPartOrgan);
        List<KeyWord> allPartOrganKwList = partOrganDpFutureTask.getFutureTask();
        if (allPartOrganKwList == null) {//存在错误信息
            Log.v("系统性诊疗分析", partOrganDpFutureTask.getErrorMessage());
        }

        Log.v("系统性诊疗分析", "患者症状描述词性标注!!!!");
        DpFutureTask symptomDpFutureTask = new DpFutureTask(executor, symptomString);
        List<KeyWord> allSymptomKwList = symptomDpFutureTask.getFutureTask();
        if (allSymptomKwList == null) {//存在错误信息
            Log.v("系统性诊疗分析", symptomDpFutureTask.getErrorMessage());
        }

        Log.v("系统性诊疗分析", "患者疾病描述词性标注!!!!");
        DpFutureTask diseaseDpFutureTask = new DpFutureTask(executor, diseaseString);
        List<KeyWord> allDiseaseKwList = diseaseDpFutureTask.getFutureTask();
        if (allDiseaseKwList == null) {//存在错误信息
            Log.v("系统性诊疗分析", diseaseDpFutureTask.getErrorMessage());
        }


        KeyWordInitialAnalysis kwia = new KeyWordInitialAnalysis(myContext);//实例化医学关键词预分析对象

        Map<String, List<KeyWord>> kwListMap = new HashMap<String, List<KeyWord>>();
        /**
         * 对症状描述和疾病描述进行医疗关键词预分析
         *      但是对于个别对诊断有帮助的非医疗词汇也会被舍弃：例如：早上，睡前，天冷.....
         *      所以为避免对诊断减低帮助，所以对自然语言词性标注集也应保留，但是需要去除广义上无法对诊断进行帮助的词汇，反而会对数据匹配产生误解、负重的词汇。
         */
        if (allNameKwList != null) {
            Log.v("系统性诊疗分析", "称呼减负载!!!!");
            List<KeyWord> nameBurdenKwList = kwia.bkwiAnalysis(allNameKwList, "name");//称呼减负载关键词集
            if (nameBurdenKwList != null) {
                kwListMap.put("name", nameBurdenKwList);
            }
        }
        if (allGenderKwList != null) {
            Log.v("系统性诊疗分析", "性别减负载!!!!");
            List<KeyWord> genderBurdenKwList = kwia.bkwiAnalysis(allGenderKwList, "gender");//性别减负载关键词集
            if (genderBurdenKwList != null) {
                kwListMap.put("gender", genderBurdenKwList);
            }
        }
        if (allAgeKwList != null) {
            Log.v("系统性诊疗分析", "年龄减负载!!!!");
            List<KeyWord> ageBurdenKwList = kwia.bkwiAnalysis(allAgeKwList, "age");//年龄减负载关键词集
            if (ageBurdenKwList != null) {
                kwListMap.put("age", ageBurdenKwList);
            }
        }
        if (allPartOrganKwList != null) {
            Log.v("系统性诊疗分析", "部位器官减负载!!!!");
            List<KeyWord> partOrganBurdenKwList = kwia.bkwiAnalysis(allPartOrganKwList, "name");//部位器官减负载关键词集
            if (partOrganBurdenKwList != null) {
                kwListMap.put("partOrgan", partOrganBurdenKwList);
            }
        }


        if (allSymptomKwList != null && allDiseaseKwList != null) {
            Log.v("系统性诊疗分析", "症状医学关键词集!!!!");
            List<KeyWord> symptomMedicineKwList = kwia.mkwiAnalysis(allSymptomKwList);//症状医学关键词集
            if (symptomMedicineKwList != null) {
                kwListMap.put("symptomM", symptomMedicineKwList);
            }
            Log.v("系统性诊疗分析", "症状减负载关键词集!!!!");
            List<KeyWord> symptomBurdenKwList = kwia.bkwiAnalysis(allSymptomKwList, "symptom");//症状减负载关键词集
            if (symptomBurdenKwList != null) {
                kwListMap.put("symptomB", symptomBurdenKwList);
            }
            Log.v("系统性诊疗分析", "疾病医学关键词集!!!!");
            List<KeyWord> diseaseMedicineKwList = kwia.mkwiAnalysis(allDiseaseKwList);//疾病医学关键词集
            if (diseaseMedicineKwList != null) {
                kwListMap.put("diseaseM", diseaseMedicineKwList);
            }
            Log.v("系统性诊疗分析", "疾病减负载关键词集!!!!");
            List<KeyWord> diseaseBurdenKwList = kwia.bkwiAnalysis(allDiseaseKwList, "disease");//疾病减负载关键词集
            if (diseaseBurdenKwList != null) {
                kwListMap.put("diseaseB", diseaseBurdenKwList);
            }

        }//end if

        return kwListMap;
    }//end dpAnalysis(LookAndAsk newLookAndAsk)


    /**
     * 对系统性问诊对象进行预处理分析线程池分析
     *
     * @param lookAndAskUID
     * @return
     */
    public Map<String, List<KeyWord>> dpBySystemAnalysis(String lookAndAskUID) {
        patientService = new PatientService(myContext);
        Patient patient = patientService.read(lookAndAskUID);
        if (patient == null) {
            return null;
        }
        String patientName = patient.getPatientName();//获取患者姓名
        String patientGender = patient.getPatientGender();//获取患者性别
        String patientAge = patient.getPatientAge();//获取患者年龄
        String patientPartOrgan = patient.getPatientPartOrgan();//获取患者疼痛部位
        String symptomString = patient.getSymptomString();//获取患者症状描述
        String diseaseString = patient.getDiseaseString();//获取患者疾病描述
        Log.v("系统性诊疗分析", patient.toString());


        //1.自然语言分析云分析
        Log.v("系统性诊疗分析", "自然语言分析云分析!!!!");
        Executor executor = Executors.newSingleThreadExecutor();//开启线程池Executor框架

        Log.v("系统性诊疗分析", "姓名词性标注!!!!");
        DpFutureTask nameDpFutureTask = new DpFutureTask(executor, patientName);
        List<KeyWord> allNameKwList = nameDpFutureTask.getFutureTask();
        if (allNameKwList == null) {//存在错误信息
            Log.v("系统性诊疗分析", nameDpFutureTask.getErrorMessage());
        }

        Log.v("系统性诊疗分析", "性别词性标注!!!!");
        DpFutureTask genderDpFutureTask = new DpFutureTask(executor, patientGender);
        List<KeyWord> allGenderKwList = genderDpFutureTask.getFutureTask();
        if (allGenderKwList == null) {//存在错误信息
            Log.v("系统性诊疗分析", genderDpFutureTask.getErrorMessage());
        }

        Log.v("系统性诊疗分析", "年龄词性标注!!!!");
        DpFutureTask ageDpFutureTask = new DpFutureTask(executor, patientAge);
        List<KeyWord> allAgeKwList = ageDpFutureTask.getFutureTask();
        if (allAgeKwList == null) {//存在错误信息
            Log.v("系统性诊疗分析", ageDpFutureTask.getErrorMessage());
        }

        Log.v("系统性诊疗分析", "部位器官词性标注!!!!");
        DpFutureTask partOrganDpFutureTask = new DpFutureTask(executor, patientPartOrgan);
        List<KeyWord> allPartOrganKwList = partOrganDpFutureTask.getFutureTask();
        if (allPartOrganKwList == null) {//存在错误信息
            Log.v("系统性诊疗分析", partOrganDpFutureTask.getErrorMessage());
        }

        Log.v("系统性诊疗分析", "患者症状描述词性标注!!!!");
        DpFutureTask symptomDpFutureTask = new DpFutureTask(executor, symptomString);
        List<KeyWord> allSymptomKwList = symptomDpFutureTask.getFutureTask();
        if (allSymptomKwList == null) {//存在错误信息
            Log.v("系统性诊疗分析", symptomDpFutureTask.getErrorMessage());
        }

        Log.v("系统性诊疗分析", "患者疾病描述词性标注!!!!");
        DpFutureTask diseaseDpFutureTask = new DpFutureTask(executor, diseaseString);
        List<KeyWord> allDiseaseKwList = diseaseDpFutureTask.getFutureTask();
        if (allDiseaseKwList == null) {//存在错误信息
            Log.v("系统性诊疗分析", diseaseDpFutureTask.getErrorMessage());
        }


        KeyWordInitialAnalysis kwia = new KeyWordInitialAnalysis(myContext);//实例化医学关键词预分析对象

        Map<String, List<KeyWord>> kwListMap = new HashMap<String, List<KeyWord>>();
        /**
         * 对症状描述和疾病描述进行医疗关键词预分析
         *      但是对于个别对诊断有帮助的非医疗词汇也会被舍弃：例如：早上，睡前，天冷.....
         *      所以为避免对诊断减低帮助，所以对自然语言词性标注集也应保留，但是需要去除广义上无法对诊断进行帮助的词汇，反而会对数据匹配产生误解、负重的词汇。
         */
        if (allNameKwList != null) {
            Log.v("系统性诊疗分析", "称呼减负载!!!!");
            List<KeyWord> nameBurdenKwList = kwia.bkwiAnalysis(allNameKwList, "name");//称呼减负载关键词集
            if (nameBurdenKwList != null) {
                kwListMap.put("name", nameBurdenKwList);
            }
        }
        if (allGenderKwList != null) {
            Log.v("系统性诊疗分析", "性别减负载!!!!");
            List<KeyWord> genderBurdenKwList = kwia.bkwiAnalysis(allGenderKwList, "gender");//性别减负载关键词集
            if (genderBurdenKwList != null) {
                kwListMap.put("gender", genderBurdenKwList);
            }
        }
        if (allAgeKwList != null) {
            Log.v("系统性诊疗分析", "年龄减负载!!!!");
            List<KeyWord> ageBurdenKwList = kwia.bkwiAnalysis(allAgeKwList, "age");//年龄减负载关键词集
            if (ageBurdenKwList != null) {
                kwListMap.put("age", ageBurdenKwList);
            }
        }
        if (allPartOrganKwList != null) {
            Log.v("系统性诊疗分析", "部位器官减负载!!!!");
            List<KeyWord> partOrganBurdenKwList = kwia.bkwiAnalysis(allPartOrganKwList, "name");//部位器官减负载关键词集
            if (partOrganBurdenKwList != null) {
                kwListMap.put("partOrgan", partOrganBurdenKwList);
            }
        }


        if (allSymptomKwList != null && allDiseaseKwList != null) {
            Log.v("系统性诊疗分析", "症状医学关键词集!!!!");
            List<KeyWord> symptomMedicineKwList = kwia.mkwiAnalysis(allSymptomKwList);//症状医学关键词集
            if (symptomMedicineKwList != null) {
                kwListMap.put("symptomM", symptomMedicineKwList);
            }
            Log.v("系统性诊疗分析", "症状减负载关键词集!!!!");
            List<KeyWord> symptomBurdenKwList = kwia.bkwiAnalysis(allSymptomKwList, "symptom");//症状减负载关键词集
            if (symptomBurdenKwList != null) {
                kwListMap.put("symptomB", symptomBurdenKwList);
            }
            Log.v("系统性诊疗分析", "疾病医学关键词集!!!!");
            List<KeyWord> diseaseMedicineKwList = kwia.mkwiAnalysis(allDiseaseKwList);//疾病医学关键词集
            if (diseaseMedicineKwList != null) {
                kwListMap.put("diseaseM", diseaseMedicineKwList);
            }
            Log.v("系统性诊疗分析", "疾病减负载关键词集!!!!");
            List<KeyWord> diseaseBurdenKwList = kwia.bkwiAnalysis(allDiseaseKwList, "disease");//疾病减负载关键词集
            if (diseaseBurdenKwList != null) {
                kwListMap.put("diseaseB", diseaseBurdenKwList);
            }

        }//end if

        return kwListMap;
    }//end dpAnalysis(LookAndAsk newLookAndAsk)


    /**
     * 常规性问诊对象预处理分析线程池分析
     *
     * @param questionString
     * @return
     */
    public String dpCommonAnalysisExecutors(String questionString) {
        //构建问题对象
        Question newQuestion = new Question();
        newQuestion.setQuestionString(questionString);
        //1.自然语言分析云分析
        //进行词性标注，得到全词集
        Log.v("常规性诊疗分析", "自然语言分析云分析：“" + questionString + "”!!!!");

        Executor executor = Executors.newSingleThreadExecutor();//开启线程池Executor框架

        Log.v("常规性诊疗分析", "问题词性标注!!!!");
        DpFutureTask dpFutureTask = new DpFutureTask(executor, questionString);
        List<KeyWord> allWList = dpFutureTask.getFutureTask();
        if (allWList == null) {//存在错误信息
            String errorMessage = dpFutureTask.getErrorMessage();
             /*
            此为云分析错误信息
             */
            Log.v("常规性诊疗分析errorMessage", errorMessage);
            return errorMessage;
        } else {
            KeyWordInitialAnalysis kwia = new KeyWordInitialAnalysis(myContext);//实例化关键词预分析对象

            //获取所有词集
            Log.v("常规性诊疗分析", "获取所有词集!!!!");
            newQuestion.setAllWList(allWList);//所有词集对象条件新问题对象中

            //获取医学关键词集
            Log.v("常规性诊疗分析", "获取医学关键词集!!!!");
            List<KeyWord> medicineKwList = kwia.mkwiAnalysis(allWList);//医学关键词集
            if (medicineKwList != null) {
                newQuestion.setMedicineKwList(medicineKwList);
            }

            //获取减负载关键词集
            Log.v("常规性诊疗分析", "获取减负载关键词集!!!!");
            List<KeyWord> burdenKwList = kwia.bkwiAnalysis(allWList, "disease");
            if (burdenKwList != null) {
                newQuestion.setBurdenKwList(burdenKwList);
            }


        }

        questionService = new QuestionService(myContext);
        questionService.add(newQuestion);//数据序列化

        return newQuestion.getUid();
    }

    /**
     * 常规性问诊对象预处理分析
     *
     * @param questionString
     * @return
     */
    public String dpCommonAnalysis(String questionString) {
        //构建问题对象
        Question newQuestion = new Question();
        newQuestion.setQuestionString(questionString);
        //1.自然语言分析云分析
        //进行词性标注，得到全词集
        Log.v("常规性诊疗分析", "自然语言分析云分析：“" + questionString + "”!!!!");


        Log.v("常规性诊疗分析", "问题词性标注!!!!");
        //        DpFutureTask dpFutureTask = new DpFutureTask(executor, questionString);
        List<KeyWord> allWList = ltpCloudAnalysisGetList(questionString);
        if (allWList == null) {//存在错误信息
            String errorMessage = "语言云分析错误";
             /*
            此为云分析错误信息
             */
            Log.v("常规性诊疗分析errorMessage", errorMessage);
            return errorMessage;
        } else {
            KeyWordInitialAnalysis kwia = new KeyWordInitialAnalysis(myContext);//实例化关键词预分析对象

            //获取所有词集
            Log.v("常规性诊疗分析", "获取所有词集!!!!");
            newQuestion.setAllWList(allWList);//所有词集对象条件新问题对象中

            //获取医学关键词集
            Log.v("常规性诊疗分析", "获取医学关键词集!!!!");
            List<KeyWord> medicineKwList = kwia.mkwiAnalysis(allWList);//医学关键词集
            if (medicineKwList != null) {
                newQuestion.setMedicineKwList(medicineKwList);
            }

            //获取减负载关键词集
            Log.v("常规性诊疗分析", "获取减负载关键词集!!!!");
            List<KeyWord> burdenKwList = kwia.bkwiAnalysis(allWList, "disease");
            if (burdenKwList != null) {
                newQuestion.setBurdenKwList(burdenKwList);
            }


        }

        questionService = new QuestionService(myContext);
        questionService.add(newQuestion);//数据序列化

        return newQuestion.getUid();
    }


    private List<KeyWord> ltpCloudAnalysisGetList(String analysisData) {
        String namePosString = lca.naturalLanguageAnalysis(analysisData);//对患者姓名词性标注
        List<KeyWord> allNameKwList = null;
        if (!TextUtils.isEmpty(namePosString)) {
            allNameKwList = lca.getAllKwList();
        }
        return allNameKwList;
    }
}