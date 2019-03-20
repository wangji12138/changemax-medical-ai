package com.example.wangji.changemax.util.speech_util.stt;

import android.content.Context;

import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * 实时语音转写
 * Created by WangJi.
 */
public class SpeechRecognition {
    //    private static final String DICTATION_APPID = GlobalConfig.IFLY_VOICE_SDK_APP_ID;
    private static SpeechRecognizer mIat;
    private static RecognizerDialog iatDialog;
    private static String dictationResultStr;
    private static String finalResult;
    private static String messageString;

    private static String string;

    public String getString() {
        return string;
    }

    public static void showDictationDialog(final Context context,
                                           final DictationListener listener) {
        // 初始化语音配置
        initConfig(context);

        // 开始听写
        iatDialog.setListener(new RecognizerDialogListener() {

            @Override
            public void onResult(RecognizerResult results, boolean isLast) {
                //                if (!isLast) {
                //                    dictationResultStr += results.getResultString() + ",";
                //                } else {
                //                    dictationResultStr += results.getResultString() + "]";
                //                    finalResult = DictationJsonParseUtil.parseJsonData(dictationResultStr);
                //                    listener.onDictationListener(finalResult);
                //                }
                printResult(results);


            }

            @Override
            public void onError(SpeechError error) {
                error.getPlainDescription(true);
            }
        });

        // 开始听写
        iatDialog.show();
    }

    private static void initConfig(Context context) {
        dictationResultStr = "[";
        finalResult = "";

        // 语音配置对象初始化
        //        SpeechUtility.createUtility(context, SpeechConstant.APPID + "=" + DICTATION_APPID);

        // 1.创建SpeechRecognizer对象，第2个参数：本地听写时传InitListener
        mIat = SpeechRecognizer.createRecognizer(context, null);
        // 交互动画
        iatDialog = new RecognizerDialog(context, null);

        // 2.设置听写参数，详见《科大讯飞MSC API手册(Android)》SpeechConstant类
        mIat.setParameter(SpeechConstant.DOMAIN, "iat"); // domain:域名
        mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mIat.setParameter(SpeechConstant.ACCENT, "mandarin"); // mandarin:普通话
    }

    /**
     * TODO 回调说话的结果
     *
     * @param results
     */
    public static void printResult(RecognizerResult results) {
        String Speaks = parseIatResult(results.getResultString());
        if (Speaks.trim().length() <= 1) {
            return;
        }

        StringBuffer stringBuffer = new StringBuffer();
        // 自动将speaker的话Speaks赋值给xuanz和sWrods
        stringBuffer.append(Speaks);
        string = stringBuffer.toString();

    }//end printResult

    /**
     * 解析语音识别的数据
     *
     * @param json
     * @return
     */
    private static String parseIatResult(String json) {
        StringBuffer ret = new StringBuffer();
        try {
            JSONTokener tokener = new JSONTokener(json);
            JSONObject joResult = new JSONObject(tokener);
            JSONArray words = joResult.getJSONArray("ws");
            for (int i = 0; i < words.length(); i++) {
                // 转写结果词，默认使用第一个结果
                JSONArray items = words.getJSONObject(i).getJSONArray("cw");
                JSONObject obj = items.getJSONObject(0);
                ret.append(obj.getString("w"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret.toString();
    }//end parseIatResult()

}