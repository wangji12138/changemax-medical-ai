package com.example.wangji.changemax.model.external;


import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.wangji.changemax.service.external.DataCollectionService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 系统性分析对象
 * 启动一次系统性分析
 * 则构建一个新对象
 * Created by WangJi.
 */
public class DataCollection {
    private Context myContext;
    private LookAndAsk lookAndAsk;
    private Patient patient;

    private DataCollectionService dcService;

    private boolean isUserConfirmSwitch = false;


    public DataCollection(Context myContext) {
        this.myContext = myContext;
        lookAndAsk = new LookAndAsk();
        patient = new Patient();
        patient.setUid(lookAndAsk.getUid());
    }

    public String getLookAndAskUid() {
        return lookAndAsk.getUid();
    }

    public String systemDataColletion(String userChatMessage) {
        Log.v("DataCollection参数", userChatMessage);
        while (true) {

            if (lookAndAsk.getI() < -1) {
                return "拒绝进入";
            }

            if (lookAndAsk.getI() == -1) {//如果回答不OK
                if (userChatMessage.contains("不")) {
                    lookAndAsk.setI(lookAndAsk.getI() - 1);
                } else {
                    isUserConfirmSwitch = true;
                }
            }
            Log.v("DataCollection", "ABC" + lookAndAsk.getI() + "，" + isUserConfirmSwitch + "，" + userChatMessage);

            if (lookAndAsk.getI() < -1) {
                return "拒绝进入";
            }

            if (lookAndAsk.getI() == lookAndAsk.getMtQuestionList().size() - 1) {
                isUserConfirmSwitch = false;
                lookAndAsk.setPatient(patient);
                dcService = new DataCollectionService(myContext);
                dcService.add(lookAndAsk);

                return "数据收集完成";
            }

            if (isUserConfirmSwitch) {//从-1开始
                if (lookAndAsk.getI() == -1) {
                    String message1 = "已经开启系统性诊疗。";
                    Log.v("DataCollection", "已经开启系统性诊疗。");
                    String message2 = lookAndAsk.getMtQuestionList().get(lookAndAsk.getI() + 1);
                    lookAndAsk.setI(lookAndAsk.getI() + 1);
                    return message1 + "---" + message2;
                }

                Log.v("DataCollection", "判断数据问题：" + lookAndAsk.getI() + "，判断数据：" + userChatMessage);
                if (isDataQualified(lookAndAsk.getI(), userChatMessage)) {//
                    lookAndAsk.setI(lookAndAsk.getI() + 1);
                }

                String message2 = lookAndAsk.getMtQuestionList().get(lookAndAsk.getI());
                return message2;
            }
        }
    }

    private boolean isDataQualified(int i, String userChatMessage) {
        switch (i) {
            case -1:
                return true;
            case 0:
                return true;
            case 1:
                if (userChatMessage.contains("男") || userChatMessage.contains("女")) {
                    patient.setPatientGender(userChatMessage);
                    return true;
                } else {
                    return false;
                }
            case 2:
                Pattern p = Pattern.compile(".*\\d+岁.*");
                Matcher m = p.matcher(userChatMessage);
                if (m.matches() || userChatMessage.contains("老") || userChatMessage.contains("中") || userChatMessage.contains("幼")) {
                    patient.setPatientAge(userChatMessage);
                    return true;
                }
                return false;

            case 3:
                //身体部位器官集
                //如果返回集为空，那么返回false
                if (userChatMessage.length() > 1) {
                    patient.setSymptomString(userChatMessage);
                    return true;
                } else {
                    return false;
                }
            case 4:
                //症状集，进行语言云分析，然后进行词库匹配
                //如果返回集为空，那么返回false
                if (userChatMessage.length() > 1) {
                    patient.setSymptomString(patient.getSymptomString() + "，" + userChatMessage);
                    return true;
                } else {
                    return false;
                }
            case 5:
                if (userChatMessage.contains("没有")) {
                    return true;
                } else {
                    if (!TextUtils.isEmpty(patient.getSymptomString())) {
                        patient.setSymptomString(patient.getSymptomString() + "，" + userChatMessage);
                    } else {
                        patient.setSymptomString(userChatMessage);
                    }
                    return false;
                }
            case 6:
                //疾病集，进行语言云分析，然后进行词库匹配
                //如果返回集为空，那么返回false
                if (userChatMessage.length() > 1) {
                    patient.setDiseaseString(patient.getDiseaseString() + "，" + userChatMessage);
                    return true;
                } else {
                    return false;
                }
            case 7:
                if (userChatMessage.contains("没有")) {
                    return true;
                } else {
                    if (!TextUtils.isEmpty(patient.getDiseaseString())) {
                        patient.setDiseaseString(patient.getDiseaseString() + "，" + userChatMessage);
                    } else {
                        patient.setDiseaseString(userChatMessage);
                    }
                    return false;
                }
            default:
                return false;
        }
    }

    private boolean isScore(String userChatMessage) {
        Pattern p = Pattern.compile(".*\\d+分.*");
        Matcher m = p.matcher(userChatMessage);
        if (m.matches()) {
            lookAndAsk.setScore(userChatMessage);
            return true;
        } else {
            return false;
        }
    }

}
