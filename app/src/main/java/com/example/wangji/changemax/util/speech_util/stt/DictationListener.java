package com.example.wangji.changemax.util.speech_util.stt;

/**
 * 科大讯飞语音解析结果返回监听接口
 * Created by WangJi.
 */

public interface DictationListener {
    public abstract void onDictationListener(String dictationResultStr);
}