package com.example.wangji.changemax.service.external;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.wangji.changemax.util.file_util.MKFileUtil;
import com.example.wangji.changemax.util.runnable_util.DataAnalysisOpenRunnable;
import com.example.wangji.changemax.activity.MainActivity;
import com.example.wangji.changemax.medical_system.MSEntrance;
import com.example.wangji.changemax.model.external.DataCollection;
import com.example.wangji.changemax.model.external.PersonChat;
import com.example.wangji.changemax.util.other_util.ReturnRandomWords;
import com.example.wangji.changemax.util.other_util.Tool;

import java.util.ArrayList;
import java.util.List;

/**
 * 反馈对话中心
 * <p>
 * Created by WangJi.
 */
public class FeedbackDialogueCore {
    private Context myContext;
    private MSEntrance msEntrance;

    private MainActivity mainActivity;

    private DataCollection dataCollection;
    private String lookAndAskUid;

    private PersonChatService psc;
    private LookAndAskService lookAndAskService;

    private boolean mtSwitch = false;//系统性诊疗总开关状态，用于接收医疗系统中的判断情况

    private boolean ctSwitch = false;//诊疗数据收集完成开关状态，用于判断数据收集完成状态
    private boolean dtCSwitch = false;//诊疗完成开关状态，用于判断诊疗完成状态，接收打分操作
    private int medicalSkipCount = 0;//数据分析跳过计数，用于在系统请求用户进行系统性的诊疗过程，用户拒绝了，将进行跳过诊疗计数

    private List<PersonChat> pcList = new ArrayList<PersonChat>();

    FeedbackDialogueCore(Context myContext) {
        this.myContext = myContext;
        msEntrance = new MSEntrance(myContext);
        mainActivity = MainActivity.getMainActivity();
    }


    /**
     * 反馈对话中心主要分析方法：生成反馈
     *
     * @param personChat_user
     * @return
     */
    public List<String> prejudge(PersonChat personChat_user) {
        String userChatMessage = personChat_user.getChatMessage();
        while (true) {
            if (mtSwitch) {
                if (dataCollection == null) {
                    dataCollection = new DataCollection(myContext);
                    lookAndAskUid = dataCollection.getLookAndAskUid();
                }
                if (dtCSwitch) {
                    dtCSwitch = false;//关闭诊疗完成开关，完成打分操作
                    mtSwitch = false;//关闭系统性分析开关
                    lookAndAskService.updateAnalysisResultScore(lookAndAskUid, userChatMessage);

                    //关闭触发系统性诊疗
                    return stringToList("感谢您的评价，我希望能更好的为您服务！~" + "---" + getWelcomeString());
                }

                String maxChatMessage = dataCollection.systemDataColletion(userChatMessage);

                personChat_user.setUid(lookAndAskUid);//更新用户消息uid
                pcList.add(personChat_user);//添加用户消息对象

                if (!TextUtils.isEmpty(maxChatMessage)) {
                    if (maxChatMessage.equals("拒绝进入")) {
                        maxChatMessage = "您拒绝了系统性诊疗，那么将为您跳过二十次触发系统性诊疗分析。~";
                        maxChatMessage = maxChatMessage + "---" + getWelcomeString();
                        mtSwitch = false;
                    }
                    if (maxChatMessage.equals("数据收集完成")) {
                        ctSwitch = true;
                        lookAndAskService = new LookAndAskService(myContext);//实例化LookAndAsk对象
                        lookAndAskService.addPcList(pcList);//存储用户集
                    }
                    if (ctSwitch) {
                        maxChatMessage = maxChatMessage + "---" + "正在进行系统性分析！请稍等。";
                        ctSwitch = false;//关闭数据收集完成开关
                        Log.v("SYSTEM分析", "开启系统等待！");
                        DataAnalysisOpenRunnable systemDaor = new DataAnalysisOpenRunnable(lookAndAskUid, "SYSTEM");
                        Thread systemDAOR = new Thread(systemDaor);
                        mainActivity.setShowLoading(true);//开启等待loading界面
                        systemDAOR.start();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                //                                try {
                                //                                Log.v("SYSTEM分析", "开启分析计时操作，系统性分析超过4.5s，为超时。");
                                //                                //                                    Thread.sleep(6000);
                                //                                if (systemDAOR.isAlive()) {
                                //                                    Log.v("SYSTEM分析", "SYSTEM分析超时。");
                                //                                    displayAnalysisResults("不好意思，分析超时了，可能系统故障，也可能网络不好。要不您稍等一会？");
                                //                                    Log.v("SYSTEM分析", "停止loading");
                                //                                } else { // 当shutdown()或者shutdownNow()执行了之后才会执行，并返回true。
                                //                                    String analysisResult = systemDaor.getResultString();
                                //                                    Log.v("SYSTEM分析", analysisResult);
                                //                                    displayAnalysisResults(analysisResult);
                                //
                                //                                    displayAnalysisResults("我只是初步分析，建议您还是需要去正规医院进行专业的检查和治疗哦。您可以对我说“周围的医院”，我就可以帮你寻找您周围的医院了");
                                //                                    Log.v("SYSTEM分析", "停止loading");
                                //                                    displayAnalysisResults("本次系统诊疗分析完成：请对本次诊疗进行打分（1-10），10分为非常满意！~");
                                //                                    dtCSwitch = true;//开启预打分开关
                                //                                    lookAndAskService.updateAnalysisResult(lookAndAskUid, analysisResult);//更新处理结果
                                //                                }

                                while (true) {
                                    if (!systemDAOR.isAlive()) {
                                        String analysisResult = systemDaor.getResultString();
                                        Log.v("SYSTEM分析", analysisResult);
                                        displayAnalysisResults(analysisResult);

                                        displayAnalysisResults("我只是初步分析，建议您还是需要去正规医院进行专业的检查和治疗哦。您可以对我说“周围的医院”，我就可以帮你寻找您周围的医院了");
                                        Log.v("SYSTEM分析", "停止loading");
                                        displayAnalysisResults("本次系统诊疗分析完成：请对本次诊疗进行打分（1-10），10分为非常满意！~");
                                        dtCSwitch = true;//开启预打分开关
                                        lookAndAskService.updateAnalysisResult(lookAndAskUid, analysisResult);//更新处理结果
                                        return;
                                    }
                                }
                                //                                } catch (InterruptedException e) {
                                //                                    e.printStackTrace();
                                //                                }
                            }
                        }).start();
                    }//end   if (ctSwitch
                    return stringToList(maxChatMessage);
                }//end if (!TextUtils.isEmpty(maxChatMessage))
            } else {
                if (msEntrance.isMtSwitch()) {
                    medicalSkipCount++;
                    if (medicalSkipCount == 20) {//到达跳过计数峰值
                        //初始化用户选择
                        mtSwitch = true;//开启系统性诊疗判断
                        medicalSkipCount = 0;//初始化跳过计数值
                        dataCollection = null;//将系统性数据收集置为null
                        pcList = new ArrayList<PersonChat>();//实例化新的对话集
                        return stringToList("请问您需要系统性诊疗吗？");//返回对用户提供选择问题
                    }
                }//end  if (msEntrance.isMtSwitch())
            }//end !if (mtSwitch) {

            DataAnalysisOpenRunnable colletionDaor = new DataAnalysisOpenRunnable(userChatMessage, "COLLETION");
            Thread colletionDAOR = new Thread(colletionDaor);
            mainActivity.setShowLoading(true);//开启等待loading界面
            colletionDAOR.start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //                    try {
                    Log.v("COLLETION分析", "开启分析计时操作，常规性分析超过2.5s，为超时。");
                    //                        Thread.sleep(20000);
                    //                        if (colletionDAOR.isAlive()) {
                    //
                    //                            Log.v("COLLETION分析", "COLLETION分析超时。");
                    //                            displayAnalysisResults("不好意思，分析超时了，可能系统故障，也可能网络不好。要不您稍等一会？~");
                    //                            Log.v("COLLETION分析", "停止loading");
                    //                        } else {// 当shutdown()或者shutdownNow()执行了之后才会执行，并返回true。
                    //                            String analysisResult = colletionDaor.getResultString();
                    //                            Log.v("进行COLLETION分析结果", analysisResult);
                    //                            displayAnalysisResults(analysisResult);
                    //                            Log.v("COLLETION分析", "停止loading");
                    //
                    //                            //对常规性对话进行存储
                    //                            addColletionDialogue(personChat_user, analysisResult);
                    //
                    //                            //对系统性诊疗触发进行监听
                    //                            if (analysisResult.contains("系统性诊疗开启中")) {
                    //                                openMtSwitch();
                    //                            }
                    //                        }

                    while (true) {
                        if (!colletionDAOR.isAlive()) {
                            String analysisResult = colletionDaor.getResultString();
                            Log.v("进行COLLETION分析结果", analysisResult);
                            displayAnalysisResults(analysisResult);
                            Log.v("COLLETION分析", "停止loading");

                            //对常规性对话进行存储
                            addColletionDialogue(personChat_user, analysisResult);

                            //对系统性诊疗触发进行监听
                            if (analysisResult.contains("系统性诊疗开启中")) {
                                openMtSwitch();
                            }
                            return;
                        }
                    }

                    //                    } catch (InterruptedException e) {
                    //                        e.printStackTrace();
                    //                    }
                }
            }).

                    start();
            return

                    stringToList("");
        }// end while(true)
    }//end public List<String> prejudge(PersonChat personChat_user)

    /**
     * 手动开启系统性诊疗
     */
    public void openMtSwitch() {
        if (mtSwitch) {
            Toast.makeText(mainActivity, "抱歉，已经在系统性诊疗中。", Toast.LENGTH_SHORT).show();
        } else {
            //初始化用户选择
            mtSwitch = true;//开启系统性诊疗判断
            medicalSkipCount = 0;//初始化跳过计数值
            dataCollection = null;//将系统性数据收集置为null
            pcList = new ArrayList<PersonChat>();//实例化新的对话集
            displayAnalysisResults("请问您需要系统性诊疗吗？");//异步请求返回对用户提供选择问题
        }
    }

    /**
     * 返回随机开场白
     *
     * @param
     * @return
     */
    private String getWelcomeString() {


        return new ReturnRandomWords().getRandomWelcome(myContext);
    }

    /**
     * 将String转换成一条或多条String
     *
     * @param string
     * @return
     */
    private List<String> stringToList(String string) {
        if (TextUtils.isEmpty(string)) {
            return null;
        } else {
            List<String> messageList = new ArrayList<String>();
            String[] strings = string.split("---");
            for (String message : strings) {
                if (!TextUtils.isEmpty(message)) {
                    messageList.add(message);
                }
            }
            return messageList;
        }
    }

    /**
     * 异步请求生成反馈对话
     *
     * @param resultsString
     */
    private void displayAnalysisResults(String resultsString) {
        psc = new PersonChatService(myContext);//实例化对话事务

        List<String> stringList = stringToList(resultsString);
        List<PersonChat> personChatList = new ArrayList<PersonChat>();

        if (stringList != null) {
            for (String string : stringList) {
                PersonChat personChat_changeMax = psc.getPersonChat_changeMax(string);
                personChatList.add(personChat_changeMax);
            }
            mainActivity.changeMaxFeedback(personChatList);
            mainActivity.setShowLoading(false);//关闭loading界面
        } else {
            PersonChat personChat_changeMax = psc.getPersonChat_changeMax("系统分析超时。");
            personChatList.add(personChat_changeMax);
            mainActivity.changeMaxFeedback(personChatList);
            mainActivity.setShowLoading(false);//关闭loading界面
        }

    }

    /**
     * 存储常规性对话
     *
     * @param personChat_user
     * @param analysisResult
     */
    private void addColletionDialogue(PersonChat personChat_user, String analysisResult) {
        List<PersonChat> personChatList = new ArrayList<PersonChat>();
        PersonChat personChat_changeMax = psc.getPersonChat_changeMax(analysisResult);
        Tool tool = new Tool();
        personChat_user.setUid(tool.getUUID());
        personChat_changeMax.setUid(personChat_user.getUid());

        personChatList.add(personChat_user);
        personChatList.add(personChat_changeMax);

        psc.addPersonChatList(personChatList);

    }


}
