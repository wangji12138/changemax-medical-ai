package com.example.wangji.changemax.service.external;


import android.content.Context;

import com.example.wangji.changemax.dao.external.LookAndAskDao;
import com.example.wangji.changemax.model.external.LookAndAsk;
import com.example.wangji.changemax.model.external.PersonChat;

import java.util.List;

/**
 * Created by WangJi.
 */
public class LookAndAskService {
    private Context myContext;
    private LookAndAskDao lookAndAskDao;
    private PatientService patientService;

    public LookAndAskService(Context myContext) {
        this.myContext = myContext;
        lookAndAskDao = new LookAndAskDao(myContext);

    }

    public void add(LookAndAsk lookAndAsk) {
        patientService = new PatientService(myContext);

        patientService.add(lookAndAsk.getPatient());
        lookAndAskDao.createLaa(lookAndAsk);


    }

    public void addPcList(List<PersonChat> laaPcList ) {
        PersonChatService pcService = new PersonChatService(myContext);
        pcService.addPersonChatList(laaPcList);
    }

    public void updateAnalysisResult(String uid, String analysisResult){
        lookAndAskDao.updateAnalysisResult(uid,analysisResult);
    }

    public  void updateAnalysisResultScore(String uid, String score){
        lookAndAskDao.updateAnalysisResultScore(uid,score);
    }

    public LookAndAsk read(String uid) {
        return lookAndAskDao.read(uid);
    }

    public   List<LookAndAsk>  readAll( ) {
        return lookAndAskDao.readAll();
    }

}
