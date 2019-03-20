package com.example.wangji.changemax.util.runnable_util;

import android.util.Log;

import com.example.wangji.changemax.activity.MainActivity;
import com.example.wangji.changemax.medical_system.MSEntrance;

/**
 * 数据分析开启线程
 * Created by WangJi.
 */
public class DataAnalysisOpenRunnable implements Runnable {
    private String uidOrUserMessage;
    private String analysisType;
    private MSEntrance msEntrance;
    private MainActivity mainActivity;
    private String resultString;

    public String getResultString() {
        return resultString;
    }

    public DataAnalysisOpenRunnable(String uidOrUserMessage, String analysisType) {
        this.uidOrUserMessage = uidOrUserMessage;
        this.analysisType = analysisType;
        mainActivity = MainActivity.getMainActivity();
        msEntrance = new MSEntrance(mainActivity);
    }

    @Override
    public void run() {
        Log.v("分析", "开启loading");
        if (analysisType.equals("SYSTEM")) {
            resultString = msEntrance.getAnswerStringBySystem(uidOrUserMessage);
        } else if (analysisType.equals("COLLETION")) {
            resultString = msEntrance.getAnswerStringByCommon(uidOrUserMessage);
        } else {

        }
    }
}