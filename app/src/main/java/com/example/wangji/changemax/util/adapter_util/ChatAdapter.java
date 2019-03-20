package com.example.wangji.changemax.util.adapter_util;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wangji.changemax.R;
import com.example.wangji.changemax.activity.MainActivity;
import com.example.wangji.changemax.model.external.ChildMessage;
import com.example.wangji.changemax.model.external.PersonChat;
import com.example.wangji.changemax.util.other_util.KeywordEvent;
import com.example.wangji.changemax.util.ui_util.AutoLinefeedLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 消息适配器
 * Created by WangJi.
 */
public class ChatAdapter extends BaseAdapter {
    private static final String TAG = "ChatAdapter";

    private Context myContext;
    private List<PersonChat> lists;
    private Map<String, String> identifierMap = new KeywordEvent().identifierRemove();


    public ChatAdapter(Context context, List<PersonChat> lists) {
        super();
        this.myContext = context;
        this.lists = lists;
    }

    /**
     * 是否是用户发送的消息
     *
     * @author cyf
     */
    public static interface IMsgViewType {
        int IMVT_COM_MSG = 0;// xiaod发送的消息
        int IMVT_TO_MSG = 1;// 用户发送的消息
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return lists.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub


        return lists.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    /**
     * 得到Item的类型，判断是xiaod发送的消息，还是用户发送出去的
     */
    public int getItemViewType(int position) {
        PersonChat entity = lists.get(position);

        if (entity.isMeSend()) {// xiaod发送的消息
            return IMsgViewType.IMVT_COM_MSG;
        } else {                // 用户发送的消息
            return IMsgViewType.IMVT_TO_MSG;
        }
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        // TODO Auto-generated method stub
        HolderView holderView = null;
        PersonChat entity = lists.get(arg0);
        boolean isMeSend = entity.isMeSend();
        String message = entity.getChatMessage();
        Intent pcIntent = entity.getPcIntent();

        if (holderView == null) {
            message = keywordShielding(message);//敏感词屏蔽

            holderView = new HolderView();
            if (isMeSend) {//为用户发送出去的消息
                arg1 = View.inflate(myContext, R.layout.chat_dialog_user_item, null);
                holderView.tv_chat_message = (TextView) arg1.findViewById(R.id.tv_chat_me_message);
                holderView.tv_chat_message.setText(message);

            } else {//为changeMax发送出去的消息
                arg1 = View.inflate(myContext, R.layout.chat_dialog_changemax_item, null);

                holderView.tv_chat_message = (TextView) arg1.findViewById(R.id.tv_chat_changemax_message);

                //对消息进行生成
                if (pcIntent != null) {
                    holderView.tv_chat_message.setTextColor(Color.BLUE);

                    for (Map.Entry<String, String> entry : identifierMap.entrySet()) {
                        if (message.contains(entry.getKey())) {
                            message = message.replace(entry.getKey(), entry.getValue());
                            break;
                        }
                    }
                }
                holderView.tv_chat_message.setText(message);

            }

            arg1.setTag(holderView);

        } else {
            holderView = (HolderView) arg1.getTag();
        }


        //对聊天页面中的消息点击事件进行监听
        holderView.tv_chat_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isMeSend) {
                    if (pcIntent != null) {
                        MainActivity.getMainActivity().jumpPage(pcIntent);
                    } else {
                        // Toast.makeText(myContext, "没有点击效果...", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Toast.makeText(myContext, "你自己的话你点了有什么用？...", Toast.LENGTH_SHORT).show();
                }

            }
        });


        return arg1;
    }


    class HolderView {
        TextView tv_chat_message;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }


    /**
     * 对changeMax消息内容进行关键词处理
     *
     * @param oldMessage
     * @return
     */
    //    private String keywordLabeling(String oldMessage) {
    //        Map<String, Intent> functionKwMap = keywordEvent.getFunctionKwMap();
    //
    //        for (Map.Entry<String, Intent> entry : functionKwMap.entrySet()) {
    //            if (oldMessage.contains(entry.getKey())) {
    //
    //                String keyword = entry.getKey();
    //                String stringBefore = oldMessage.substring(0, oldMessage.lastIndexOf(keyword));
    //                String stringRear = oldMessage.substring(oldMessage.lastIndexOf(keyword) + keyword.length(), oldMessage.length());
    //                String newMessage = stringBefore + keyword + "【可点击】" + stringRear;
    //
    //                return newMessage;
    //            }
    //        }
    //        return null;
    //    }


    /**
     * 对所有消息进行关键词屏蔽
     *
     * @param message
     * @return
     */
    private String keywordShielding(String message) {
        String[] sensitiveWord = {"admin"};
        for (String string : sensitiveWord) {
            if (message.contains(string)) {
                StringBuilder sb = new StringBuilder("");
                for (int i = 0; i < string.length(); i++) {
                    sb.append("*");
                }
                message = message.replace(string, sb.toString());
            }
        }

        return message;
    }


    /**
     * 动态拆分LinearLayout中的TextView：孵化模式
     * 原理如下：
     * 先假设不存在关键词，那么在LinearLayout中只存在一个TextView，包含完整消息内容
     * 取textView1中消息内容，进行功能性关键词匹配，
     * 如果出现匹配关键词，那么将对该关键词进行提取，将其一个TextView拆分为三个TextView，
     *
     * @param ll_chat_message
     * @param message
     */
    //    private void dynamicallyDisplayingMessage(PersonChat changeMaxPersonChat, TextView tv_chat_changemax_message, String message) {
    //
    //        Log.v("ChatAdapter", "进入dynamicallyDisplayingMessage分析模式");
    //
    //        Map<String, Intent> functionKwMap = keywordEvent.getFunctionKwMap();
    //
    //        for (Map.Entry<String, Intent> entry : functionKwMap.entrySet()) {
    //            String kwString = entry.getKey();
    //            for (int i = 0; i < childMessageList.size(); i++) {
    //                ChildMessage childMessage = childMessageList.get(i);
    //                String cmString = childMessage.getCmString();
    //                boolean isKw = childMessage.isKw();
    //                if (!isKw) {//不为功能性关键词对象
    //                    Log.v("ChatAdapter", "正在分析：" + cmString + "，非功能性关键词对象");
    //                    if (cmString.contains(kwString)) {
    //
    //
    //                        Log.v("ChatAdapter", "第" + i + "次分析，分割前集长度为：" + childMessageList.size() + "，内容：" + fun(childMessageList));
    //
    //
    //                        Log.v("ChatAdapter", "正在分析：" + cmString + "，" + kwString + "，匹配成功");
    //                        int startInt;
    //                        int endInt;
    //                        if (kwString.equals("***") || kwString.equals("###")) {//为百科关键词，那么将扩展包含
    //                            Log.v("ChatAdapter", "为百科关键词，那么将扩展包含");
    //                            startInt = cmString.indexOf(kwString);
    //                            endInt = cmString.lastIndexOf(kwString) + kwString.length();
    //                        } else {//只包含该关键词
    //                            startInt = cmString.indexOf(kwString);
    //                            endInt = cmString.indexOf(kwString) + kwString.length();
    //                        }
    //                        String firstString = cmString.substring(0, startInt);
    //                        String middleString = cmString.substring(startInt, endInt);
    //                        String tailString = cmString.substring(endInt, cmString.length());
    //
    //                        Log.v("ChatAdapter", "分割结果：" + firstString + "===" + middleString + "===" + tailString + "，匹配成功。匹配位置：" + i + "，总：" + childMessageList.size());
    //
    //
    //                        /*
    //                        删除原来位置的字符，顺序添加分割后的字符集，实现替换
    //                         */
    //                        childMessageList.remove(childMessage);
    //                        if (!TextUtils.isEmpty(tailString)) {
    //                            childMessageList.add(i, new ChildMessage(tailString, false));
    //                        }
    //                        if (!TextUtils.isEmpty(middleString)) {
    //                            childMessageList.add(i, new ChildMessage(middleString, true));
    //                        }
    //                        if (!TextUtils.isEmpty(firstString)) {
    //                            childMessageList.add(i, new ChildMessage(firstString, false));
    //                        }
    //                        Log.v("ChatAdapter", "第" + i + "次分析，分割后集长度为：" + childMessageList.size() + "，内容：" + fun(childMessageList));
    //                        //                        if (i == childMessageList.size() - 1) {
    //                        //                            break;
    //                        //                        }
    //                    }
    //                }//end    if (!isKw)
    //
    //            }//end  for (int i = 0; i < childMessageList.size(); i++)
    //        }//end for (Map.Entry<String, Intent> entry : functionKwMap.entrySet())
    //
    //        int row = 15;
    //
    //
    //        Log.v("ChatAdapter", "分割后长度：" + childMessageList.size());
    //        if (childMessageList.size() > 1) {//消息内容中存在功能性关键词，不止包含默认消息内容对象
    //            Log.v("ChatAdapter", "消息内容中存在功能性关键词，不止包含默认消息内容对象");
    //            if (message.length() < row) {//消息的语句长度在20内，直接进行动态生成
    //                Log.v("ChatAdapter", "消息的语句长度在20内，直接进行动态生成");
    //                for (int i = 0; i < childMessageList.size(); i++) {
    //                    ChildMessage childMessage = childMessageList.get(i);
    //                    String cmString = childMessage.getCmString();
    //
    //                    TextView textView = new TextView(myContext);
    //                    textView.setText(cmString);
    //                    Log.v("ChatAdapter", "组合分割词：" + cmString);
    //
    //                    if (childMessage.isKw()) {
    //                        textView.setTextColor(Color.BLUE);
    //                        textView.setOnClickListener(new View.OnClickListener() {//添加点击事件
    //                            @Override
    //                            public void onClick(View v) {
    //                                //                                Intent intent = keywordEventProcess(cmString);
    //                                //                                if (intent != null) {
    //                                //                                    MainActivity.getMainActivity().jumpPage(intent);
    //                                //                                }
    //                                Toast.makeText(myContext, cmString, Toast.LENGTH_SHORT).show();
    //                            }
    //                        });
    //                    }
    //                    ll_chat_message.addView(textView);
    //
    //                }
    //
    //                return;
    //            } else {//消息的语句长度大于15，那么字符到达15换行生成
    //
    //
    //                int row1 = 0;
    //                for (int i = 0; i < childMessageList.size(); i++) {
    //
    //
    //                    ChildMessage childMessage = childMessageList.get(i);
    //                    String cmString = childMessage.getCmString();
    //                    boolean isKw = childMessage.isKw();
    //
    //                    Log.v("ChatAdapter", "当前行：" + row1 + "，当前对象：" + cmString + "，对象位置：" + i);
    //                    TextView textView = new TextView(myContext);
    //
    //
    //                    if (isKw) {
    //                        textView.setText(cmString);
    //                        row1 += cmString.length();
    //                        textView.setTextColor(Color.BLUE);
    //                    } else {
    //                        if ((cmString.length()) > row - row1) {
    //                            Log.v("ChatAdapter", "消息的语句长度累加大于15，使用换行操作");
    //                            String oneString = cmString.substring(0, row - row1);
    //                            textView.setText(oneString);
    //                            String twoString = cmString.substring(row - row1, cmString.length());
    //
    //                            childMessageList.add(i + 1, new ChildMessage(twoString, false));
    //
    //                        } else {
    //                            textView.setText(cmString);
    //                            row1 += cmString.length();
    //                        }
    //                    }
    //
    //                    Log.v("ChatAdapter", "TextView：" + textView.getText().toString());
    //
    //                    if (row1 >= row) {
    //                        Log.v("ChatAdapter", "消息的语句长度大于15，使用换行操作");
    //                        row1 = 0;
    //                        i = i - 1;
    //                    }
    //
    //
    //                    ll_chat_message.addView(textView);
    //
    //                }
    //
    //
    //                return;
    //            }
    //        } else {
    //            Log.v("ChatAdapter", "消息内容中不存在功能性关键词");
    //            TextView textView = new TextView(myContext);
    //            textView.setText(message);
    //            ll_chat_message.addView(textView);
    //
    //        }
    //    }

    //    /**
    //     * 根据changeMax消息内容进行点击事件处理
    //     *
    //     * @param cmString
    //     * @return
    //     */
    //    private Intent keywordEventProcess(String cmString) {
    //        Map<String, Intent> functionKwMap = keywordEvent.getFunctionKwMap(myContext);
    //
    //        for (Map.Entry<String, Intent> entry : functionKwMap.entrySet()) {
    //            String keyword = entry.getKey();
    //            if (cmString.contains(keyword)) {
    //                Intent intent = entry.getValue();
    //                /*
    //                医疗百科关键词
    //                 */
    //                switch (keyword) {
    //                    case "***":
    //                        String diseaseKw = extractKeyword(cmString, "***");
    //                        intent.putExtra("disease_name", diseaseKw);
    //                        Log.v("keywordEventProcess", "出现疾病关键词：" + diseaseKw);
    //                        return intent;
    //                    case "###":
    //                        String symptomKw = extractKeyword(cmString, "***");
    //                        intent.putExtra("symptom_name", symptomKw);
    //                        Log.v("keywordEventProcess", "出现症状关键词：" + symptomKw);
    //                        return intent;
    //                    default://默认返回无关键词的Intent对象
    //                        return intent;
    //                }
    //            }
    //        }
    //        return null;
    //    }//end keywordEventProcess()

    //    /**
    //     * 提取消息内容中的关键词
    //     *
    //     * @param chatMessage
    //     * @return
    //     */
    //    private String extractKeyword(String chatMessage, String kwIdentification) {
    //        int startInt = chatMessage.indexOf(kwIdentification) + kwIdentification.length();//由标志字符首次出现位置 + 标志字符长度
    //        int endInt = chatMessage.lastIndexOf(kwIdentification);//由标志字符最后出现位置
    //        return chatMessage.substring(startInt, endInt);
    //    }
    //
    //
    //    private String fun(List<ChildMessage> childMessageList) {
    //        StringBuilder sb = new StringBuilder("");
    //
    //        for (ChildMessage childMessage : childMessageList) {
    //            sb.append(childMessage.toString());
    //        }
    //
    //        return sb.toString();
    //    }
    //
    //    private List<ChildMessage> dichotomizeString(List<ChildMessage> childChildMessageList, String cmString, int row) {
    //
    //        if (cmString.length() > row) {
    //
    //            String oneString = cmString.substring(0, row);
    //            String twoString = cmString.substring(row, cmString.length());
    //
    //            childChildMessageList.addAll(dichotomizeString(childChildMessageList, twoString, row));
    //
    //            childChildMessageList.add(new ChildMessage(oneString, false));
    //        }
    //        return childChildMessageList;
    //
    //    }

}