package com.example.wangji.changemax.medical_system.pretreatment;


import android.text.TextUtils;
import android.util.Log;

import com.example.wangji.changemax.model.external.KeyWord;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 词性标注线程池
 * Created by WangJi.
 */
public class DpFutureTask {
    private Executor executor;
    private String analysisData;
    private LtpCloudAnalysis ltpCloudAnalysis;
    private String errorMessage = "";

    public String getErrorMessage() {
        return errorMessage;
    }

    DpFutureTask(Executor executor, String analysisData) {
        this.executor = executor;
        this.analysisData = analysisData;
        ltpCloudAnalysis = new LtpCloudAnalysis();
    }

    public List<KeyWord> getFutureTask() {
        FutureTask<List<KeyWord>> future = new FutureTask<List<KeyWord>>(new Callable<List<KeyWord>>() {
            @Override
            public List<KeyWord> call() throws Exception {
                String namePosString = ltpCloudAnalysis.naturalLanguageAnalysis(analysisData);//对患者姓名词性标注
                List<KeyWord> allNameKwList = null;
                if (!TextUtils.isEmpty(namePosString)) {
                    allNameKwList = ltpCloudAnalysis.getAllKwList();
                }
                return allNameKwList;
            }
        });
        executor.execute(future);

        List<KeyWord> keyWordList = null;
        try {
            keyWordList = future.get(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            // TODO: handle exception
            Log.v("DpFutureTask", "语言云分析方法执行中断");
            // future.cancel(true);
        } catch (ExecutionException e) {
            errorMessage = "语言云分析异常";
            // TODO: handle exception
            future.cancel(true);
        } catch (TimeoutException e) {
            // TODO: handle exception

            try {
                keyWordList = future.get(1, TimeUnit.SECONDS);
            } catch (InterruptedException e1) {
                // TODO: handle exception
                errorMessage = "语言云分析方法执行中断";
                // future.cancel(true);
            } catch (ExecutionException e1) {
                errorMessage = "语言云分析Excution异常";
                // TODO: handle exception
                future.cancel(true);
            } catch (TimeoutException e1) {
                // TODO: handle exception
                errorMessage = "语言云分析方法执行时间超时";
                //future.cancel(true);
            }

            //future.cancel(true);
        }

        return keyWordList;
    }
}
