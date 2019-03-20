package com.example.wangji.changemax.dao.external;

import android.content.Context;

import com.example.wangji.changemax.model.external.PersonChat;
import com.example.wangji.changemax.util.sqlite_util.external.PersonChatDatabaseUtil;

import java.util.List;

/**
 * Created by WangJi.
 */
public class PersonChatDao {
    private Context myContext;
    private PersonChatDatabaseUtil personChatDatabaseUtil;

    public PersonChatDao(Context myContext) {
        this.myContext = myContext;
        personChatDatabaseUtil = new PersonChatDatabaseUtil(myContext);
    }

    public void add(PersonChat personChat) {
        personChatDatabaseUtil.openDataBase();
        personChatDatabaseUtil.insertData(personChat);
        personChatDatabaseUtil.closeDataBase();
    }

    public List<PersonChat> read(String uid) {
        uid = "'" + uid + "'";
        personChatDatabaseUtil.openDataBase();
        List<PersonChat> pcList = personChatDatabaseUtil.queryData(uid);
        personChatDatabaseUtil.closeDataBase();
        return pcList;
    }

    public List<PersonChat> readAll() {
        personChatDatabaseUtil.openDataBase();
        List<PersonChat> pcList = personChatDatabaseUtil.queryDataList();
        personChatDatabaseUtil.closeDataBase();
        return pcList;
    }

    public List<PersonChat> read(int id) {
        personChatDatabaseUtil.openDataBase();
        List<PersonChat> pcList = personChatDatabaseUtil.queryData(id);
        personChatDatabaseUtil.closeDataBase();
        return pcList;
    }

    public List<PersonChat> readUserPersonChar(String userMessage) {
        personChatDatabaseUtil.openDataBase();
        userMessage = "'" + userMessage + "'";
        List<PersonChat> pcList = personChatDatabaseUtil.queryUserData(userMessage);
        personChatDatabaseUtil.closeDataBase();
        return pcList;
    }

    public List<PersonChat> readChangeMaxPersonChar(String uid) {
        uid = "'" + uid + "'";
        personChatDatabaseUtil.openDataBase();
        List<PersonChat> pcList = personChatDatabaseUtil.queryData(uid);
        personChatDatabaseUtil.closeDataBase();
        return pcList;
    }
}
