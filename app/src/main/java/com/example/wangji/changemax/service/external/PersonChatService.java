package com.example.wangji.changemax.service.external;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.wangji.changemax.R;
import com.example.wangji.changemax.activity.MainActivity;
import com.example.wangji.changemax.dao.external.PersonChatDao;
import com.example.wangji.changemax.model.external.PersonChat;
import com.example.wangji.changemax.util.file_util.FileUtil;
import com.example.wangji.changemax.util.other_util.KeywordEvent;
import com.example.wangji.changemax.util.other_util.ReturnRandomWords;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 构建PersonChat对象
 * Created by WangJi.
 */

public class PersonChatService {
    private FeedbackDialogueCore feedbackDialogueCore;
    private List<PersonChat> personChatList;
    private PersonChatDao personChatDao;
    private Context myContext;
    private MainActivity mainActivity;

    private Map<String, String> identifierMap = new KeywordEvent().identifierRemove();


    public PersonChatService(Context myContext) {
        this.myContext = myContext;
        feedbackDialogueCore = new FeedbackDialogueCore(myContext);
        mainActivity = MainActivity.getMainActivity();//实例化
    }

    /**
     * 随机开场白
     *
     * @return
     */
    public void getWelcomePersonChat(int i) {
        personChatList = new ArrayList<PersonChat>();
        if (i == 0) {
            List<String> firstStartWelcome = new ReturnRandomWords().getFirstStartWelcome();
            for (String chatMessage : firstStartWelcome) {
                PersonChat personChat = getPersonChat_changeMax(chatMessage);
                personChatList.add(personChat);
            }

        } else {
            PersonChat personChat = getPersonChat_changeMax(new ReturnRandomWords().getRandomWelcome(myContext));
            personChatList.add(personChat);
        }
        mainActivity.changeMaxFeedback(personChatList);
    }

    /**
     * 根据消息内容生成用户消息对象
     *
     * @param chatMessage
     * @return
     */
    public PersonChat getPersonChat_user(String chatMessage) {
        PersonChat personChat_user = new PersonChat();
        personChat_user.setMeSend(true);//设置为用户聊天消息对象
        personChat_user.setChatMessage(chatMessage);//设置用户聊天消息内容
        Log.v("user", chatMessage);
        mainActivity.userFeedback(personChat_user);
        return personChat_user;//返回当前生成的用户聊天对象
    }

    /**
     * 根据消息内容生成changeMax消息对象
     *
     * @param chatMessage
     * @return
     */
    public PersonChat getPersonChat_changeMax(String chatMessage) {
        Log.v("changeMax", chatMessage);
        PersonChat personChat_changeMax = new PersonChat();
        personChat_changeMax.setMeSend(false);//设置为大易聊天消息对象
        personChat_changeMax.setChatMessage(chatMessage);//设置大易聊天消息内容

        /*
        对语音输出进行分析
         */
        personChat_changeMax.setReadChatMessage(analysisAnswerTTS(chatMessage));//设置语音输出内容

        /*
        在返回changeMax消息对象之前，对它的点击事件进行选择处理
         */
        personChat_changeMax.setPcIntent(keywordEventProcess(chatMessage));

        return personChat_changeMax;
    }

    /**
     * 功能性页面跳转赋值
     *
     * @param changeMaxMessage
     * @return
     */
    private Intent keywordEventProcess(String changeMaxMessage) {
        Map<String, Intent> functionKwMap = new KeywordEvent().getFunctionKwMap(myContext);

        for (Map.Entry<String, Intent> entry : functionKwMap.entrySet()) {
            String kwString = entry.getKey();
            Intent intent = entry.getValue();
            if (changeMaxMessage.contains(kwString)) {

                int startInt;
                int endInt;
                String keyWordString;

                if (kwString.equals("***") || kwString.equals("###") || kwString.equals("///")) {//为不确定性关键词，那么将扩展包含
                    Log.v("ChatAdapter", "为百科关键词，那么将扩展包含");
                    startInt = changeMaxMessage.indexOf(kwString) + 3;
                    endInt = changeMaxMessage.lastIndexOf(kwString) + kwString.length() - 3;
                    keyWordString = changeMaxMessage.substring(startInt, endInt);
                    if (kwString.equals("***")) {
                        intent.putExtra("disease_name", keyWordString);
                    } else if (kwString.equals("###")) {
                        intent.putExtra("symptom_name", keyWordString);
                    } else if (kwString.equals("///")) {
                        intent.putExtra("url", keyWordString);
                    }
                } else {//只包含该关键词
                    startInt = changeMaxMessage.indexOf(kwString);
                    endInt = changeMaxMessage.indexOf(kwString) + kwString.length();
                    keyWordString = changeMaxMessage.substring(startInt, endInt);
                    intent.putExtra("keyWord", keyWordString);
                }
                return intent;

            }
        }//end for (Map.Entry<String, Intent> entry : functionKwMap.entrySet())
        return null;
    }


    /**
     * 诊疗判断分析系统
     *
     * @param personChat_user
     * @return
     */
    public void getPersonChat_changeMax(PersonChat personChat_user) {
        /**
         * 在诊疗分析系统中存在一个诊疗对话内容，所以对其处理存在集关系，但是对于一段对话上下联系关系分析，
         * 至少存在三个话语交流，才构成一个自然语言交流事件。
         */
        //        String topUserChatMessage = personChat_previous != null ? personChat_previous.getChatMessage() : "我需要帮助！";
        List<String> answerStringList = feedbackDialogueCore.prejudge(personChat_user);
        if (answerStringList != null && answerStringList.size() > 0) {//如果反馈不为空，则对主界面ui进行更新
            personChatList = new ArrayList<PersonChat>();
            for (String answerString : answerStringList) {
                PersonChat personChat = getPersonChat_changeMax(answerString);
                personChatList.add(personChat);
            }
            mainActivity.changeMaxFeedback(personChatList);
        }
    }

    /**
     * 对答案串进行语音输出内容分析提取
     * 算法：
     * 1.判断answerString长度是否小于等于15，或者后缀为~
     * 是：直接返回为语音输出内容
     * 否：进行进一步分析
     * 1.将其转换为字符数组
     * 2.将字符数组遍历，如果出现：: ，。等标志性标点，那么将截取之前字符串，作为输出内容
     * 3.如果遍历字符数组15个字符以上，还未出现标志性标点，那么将截取字符串前15个字符为语音输出内容
     *
     * @param changeMaxMessage
     * @return
     */
    private String analysisAnswerTTS(String changeMaxMessage) {

        for (Map.Entry<String, String> entry : identifierMap.entrySet()) {
            if (changeMaxMessage.contains(entry.getKey())) {
                changeMaxMessage = changeMaxMessage.replace(entry.getKey(), entry.getValue());
                break;
            }
        }


        int maxDefaultLength = 45;
        int maxRegulationLength = 60;

        int length = changeMaxMessage.trim().length();
        if (length <= maxDefaultLength) {
            return changeMaxMessage;
        } else {
            if (length > 150 || !changeMaxMessage.contains("~")) {
                return new ReturnRandomWords().changeMaxStrike(5);
            }
            char[] messageChar = changeMaxMessage.toCharArray();
            StringBuilder sb = new StringBuilder("");
            for (int i = 0; i < messageChar.length; i++) {
                sb.append(messageChar[i]);
                if (messageChar[i] == '~') {
                    return sb.toString();
                }
                if (i >= maxRegulationLength) {
                    sb.append(new ReturnRandomWords().changeMaxStrike(-1));
                    return sb.toString();
                }
            }
            return sb.toString();
        }
    }


    /**
     * 添加消息集对象
     *
     * @param personChatList
     */
    public void addPersonChatList(List<PersonChat> personChatList) {
        personChatDao = new PersonChatDao(myContext);
        for (PersonChat personChat : personChatList) {
            personChatDao.add(personChat);
        }
    }

    /**
     * 根据消息唯一uid读取消息集对象
     *
     * @param uid
     * @return
     */
    public List<PersonChat> readPersonChat(String uid) {
        personChatDao = new PersonChatDao(myContext);
        List<PersonChat> pcList = personChatDao.read(uid);
        if (pcList != null && pcList.size() > 0) {
            return pcList;
        } else {
            return null;
        }
    }

    /**
     * 根据用户消息内容读取用户消息集对象
     *
     * @param userMessage
     * @return
     */
    public List<PersonChat> readUserPersonChar(String userMessage) {
        personChatDao = new PersonChatDao(myContext);
        List<PersonChat> userPersonChatList = personChatDao.readUserPersonChar(userMessage);
        if (userPersonChatList != null && userPersonChatList.size() > 0) {
            return userPersonChatList;
        } else {
            return null;
        }
    }

    /**
     * 根据消息唯一uid读取changeMax消息对象
     *
     * @param uid
     * @return
     */
    public List<PersonChat> readChangeMaxPersonChar(String uid) {
        personChatDao = new PersonChatDao(myContext);
        List<PersonChat> changeMaxPersonChatList = personChatDao.readChangeMaxPersonChar(uid);
        if (changeMaxPersonChatList != null && changeMaxPersonChatList.size() > 0) {
            return changeMaxPersonChatList;
        } else {
            return null;
        }
    }


    /**
     * 读取所有的消息对象
     *
     * @return
     */
    public List<PersonChat> readAllPersonChat() {
        personChatDao = new PersonChatDao(myContext);
        List<PersonChat> pcList = personChatDao.readAll();
        if (pcList != null && pcList.size() > 0) {
            return pcList;
        } else {
            return null;
        }
    }

    /**
     * 根据id读取消息对象
     *
     * @param id
     * @return
     */
    public List<PersonChat> readPersonChatById(int id) {
        personChatDao = new PersonChatDao(myContext);
        List<PersonChat> pcList = personChatDao.read(id);
        if (pcList != null && pcList.size() > 0) {
            return pcList;
        } else {
            return null;
        }
    }

}
