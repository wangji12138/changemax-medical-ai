package com.example.wangji.changemax.util.speech_util.tts;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.example.wangji.changemax.util.speech_util.stt.GlobalConfig;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;

/**
 * Created by WangJi.
 * <p>
 * 此类为封装科大讯飞在线语音合成类
 */

public class KqwSpeechCompound {
    private static String TAG = "KqwSpeechCompound";

    private Context mContext;

    private String mEngineType = SpeechConstant.TYPE_CLOUD;

    private SpeechSynthesizer mTts;
    /**
     * 一个appID一天可以免费使用500次
     */
    private static final String DICTATION_APPID = GlobalConfig.IFLY_VOICE_SDK_APP_ID;

    private String speed = "50";
    private String pitch = "30";
    private String volume = "100";
    private String stream_preference = "3";

    private VoiceCallback callback;

    public KqwSpeechCompound(Context context) {
        this.mContext = context;

        StringBuffer param = new StringBuffer();
        param.append("appid" + "=" + DICTATION_APPID);
        param.append(",");
        param.append(SpeechConstant.ENGINE_MODE + "=" + SpeechConstant.MODE_MSC);

        //初始化报空指针异常
        //  SpeechUtility.createUtility(mContext, param.toString());
    }

    private InitListener mTtsInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            Log.d(TAG, "InitListener init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                Log.e(TAG, "Init Failed, Error Code : " + code);
            }
        }
    };

    private SynthesizerListener mTtsListener = new SynthesizerListener() {
        @Override
        public void onSpeakBegin() {
            callback.speakStart();
            Log.e(TAG, "开始speak");
        }

        @Override
        public void onSpeakPaused() {
            Log.e(TAG, "speak pause");
        }

        @Override
        public void onSpeakResumed() {
            Log.e(TAG, "speak resume");
        }

        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos, String info) {
            Log.e(TAG, "onbuffer process==" + percent + "===" + beginPos + "===" + endPos + "==" + info);
        }

        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
            Log.e(TAG, "speak process==" + percent + "===" + beginPos + "===" + endPos);
        }

        @Override
        public void onCompleted(SpeechError error) {
            callback.speakFinish();
            Log.e(TAG, "speak complete");
        }

        @Override
        public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
            // TODO Auto-generated method stub
        }
    };


    public void speak(String text) {
        // create the synthesizer.
        mTts = SpeechSynthesizer.createSynthesizer(mContext, mTtsInitListener);
        setParam();
        int code = mTts.startSpeaking(text, mTtsListener);
        Log.e("==========", "=========语音播放code" + code);
    }

    public void stop() {
        mTts.stopSpeaking();
        mTts.destroy();
    }

    private void setParam() {
        mTts.setParameter(SpeechConstant.PARAMS, null);
        if (mEngineType.equals(SpeechConstant.TYPE_CLOUD)) {
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
            mTts.setParameter(SpeechConstant.VOICE_NAME, "xuxiaobao");
        }
        mTts.setParameter(SpeechConstant.SPEED, speed);
        mTts.setParameter(SpeechConstant.PITCH, pitch);
        mTts.setParameter(SpeechConstant.VOLUME, volume);
        mTts.setParameter(SpeechConstant.STREAM_TYPE, stream_preference);
    }

    public void setCallback(VoiceCallback callback) {
        this.callback = callback;
    }

    public interface VoiceCallback {
        public void speakFinish();

        public void speakStart();
    }
}
