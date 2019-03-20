package com.example.wangji.changemax.medical_system.analysis.history_question_match;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.wangji.changemax.dao.external.PersonChatDao;
import com.example.wangji.changemax.model.external.PersonChat;
import com.example.wangji.changemax.model.external.Question;
import com.example.wangji.changemax.service.external.PersonChatService;
import com.example.wangji.changemax.service.external.QuestionService;

import java.util.List;

/**
 * 历史问题匹配分析
 * Created by WangJi.
 */

public class HistoryQuestionMatchAnalysis {

    private Context myContext;
    private PersonChatService personChatService;

    public HistoryQuestionMatchAnalysis(Context myContext) {
        this.myContext = myContext;
        personChatService = new PersonChatService(myContext);
    }

    public String historyQuestionMatchAnalysis(String userMessage) {
        Log.v("HistoryQuestionAnalysis", "进入历史问题匹配分析");

        String changeMaxMessage = "";
        List<PersonChat> userPersonChatList = personChatService.readUserPersonChar(userMessage);
        if (userPersonChatList != null && userPersonChatList.size() > 0) {
            for (PersonChat userPersonChat : userPersonChatList) {
                if (userPersonChat.isMeSend()) {
                    String uid = userPersonChat.getUid();
                    List<PersonChat> changeMaxPersonChatList = personChatService.readChangeMaxPersonChar(uid);
                    if (changeMaxPersonChatList != null && changeMaxPersonChatList.size() > 0) {
                        for (PersonChat changeMaxPersonChat : changeMaxPersonChatList) {
                            if (!changeMaxPersonChat.isMeSend()) {
                                changeMaxMessage = changeMaxPersonChat.getChatMessage();
                            }//end if
                        }
                    }//end if (changeMaxPersonChatList != null && changeMaxPersonChatList.size() > 0)
                }
            }
        }//end if (userPersonChatList != null && userPersonChatList.size() > 0)
        return changeMaxMessage;

    }//end historyQuestionMatchAnalysis(String userMessage)

}
