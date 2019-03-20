package com.example.wangji.changemax.model.external;

import com.example.wangji.changemax.util.other_util.Tool;
import android.content.Intent;

/**
 * 消息条例对象
 * Created by WangJi.
 */

public class PersonChat {
    //id
    private int id;
    //uid   32位消息条例，后期对数据库的存取提供方便
    private String uid;
    //消息内容
    private String chatMessage;
    //是否为用户发送
    private boolean isMeSend;
    //消息时间
    private String date;
    //电脑专属
    private String readChatMessage;

    //点击跳转
    private Intent pcIntent = null;//初始为null


    public PersonChat() {
        Tool tool = new Tool();
        date = tool.getNowTime();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getChatMessage() {
        return chatMessage;
    }

    public void setChatMessage(String chatMessage) {
        this.chatMessage = chatMessage;
    }

    public boolean isMeSend() {
        return isMeSend;
    }

    public void setMeSend(boolean isMeSend) {
        this.isMeSend = isMeSend;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReadChatMessage() {
        return readChatMessage;
    }

    public void setReadChatMessage(String readChatMessage) {
        this.readChatMessage = readChatMessage;
    }

	public Intent getPcIntent() {
		return pcIntent;
	}

	public void setPcIntent(Intent pcIntent) {
		this.pcIntent = pcIntent;
	}

    @Override
    public String toString() {
        return "[id=" + id + ",\r\n uid=" + uid + ",\n chatMessage=" + chatMessage + ",\n isMeSend=" + isMeSend
                + ",\n date=" + date + ",\n readChatMessage=" + readChatMessage + "]";
    }
}
