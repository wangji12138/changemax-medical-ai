package com.example.wangji.changemax.service.external;

import android.content.Context;

import com.example.wangji.changemax.model.external.LookAndAsk;

/**
 * 系统性诊疗事务操作类
 * Created by WangJi.
 */
public class DataCollectionService {
    private Context myContext;
    private LookAndAskService lookAndAskService;


    public DataCollectionService(Context myContext) {
        this.myContext = myContext;
    }

    public void add(LookAndAsk lookAndAsk) {
        lookAndAskService = new LookAndAskService(myContext);

        lookAndAskService.add(lookAndAsk);

    }
}
