package com.example.wangji.changemax.activity.admin;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.wangji.changemax.R;
import com.example.wangji.changemax.medical_system.MSEntrance;
import com.example.wangji.changemax.model.external.Patient;
import com.example.wangji.changemax.model.external.PersonChat;
import com.example.wangji.changemax.model.external.Question;
import com.example.wangji.changemax.service.external.PatientService;
import com.example.wangji.changemax.service.external.PersonChatService;
import com.example.wangji.changemax.service.external.QuestionService;
import com.example.wangji.changemax.util.speech_util.stt.DictationListener;
import com.example.wangji.changemax.util.speech_util.stt.SpeechRecognition;
import com.example.wangji.changemax.util.sqlite_util.external.DBManager;

import java.util.List;

/**
 * Created by WangJi.
 */

public class AdminActivity extends Activity {
    private static final String TAG = "AdminActivity";


    private EditText et_depa_name, et_depa_introduce, et_depa_address;
    private TextView tv_admin_news, tv_content, tv_text;
    private Button btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8;

    private MSEntrance msEntrance;

    //数据库初始化工具类
    DBManager dbManager;

    Context myContext = this;
    PersonChatService pc = new PersonChatService(myContext);

    private View mParent;
    private Handler mHandler = new Handler();

    boolean flag = false;

    Context getMyContext = this;


    public AdminActivity adminActivity;

    public AdminActivity() {
        adminActivity = this;
    }

    public AdminActivity getAdminActivity() {
        return adminActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        init();
        mParent = (RelativeLayout) findViewById(R.id.mParent);

        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PatientService patientService = new PatientService(myContext);
                List<Patient> patientList = patientService.readAll();
                if (patientList != null && patientList.size() > 0) {
                    StringBuffer stringBuffer = new StringBuffer("");
                    for (Patient patient : patientList) {
                        stringBuffer.append(patient.toString() + "\n\n");
                    }
                    tv_admin_news.setText(stringBuffer.toString());
                } else {
                    tv_admin_news.setText("数据为空。");
                }
            }
        });

        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersonChatService personChatService = new PersonChatService(myContext);
                List<PersonChat> personChatList = personChatService.readAllPersonChat();
                if (personChatList != null && personChatList.size() > 0) {
                    StringBuffer stringBuffer = new StringBuffer("");
                    for (PersonChat personChat : personChatList) {
                        stringBuffer.append(personChat.toString() + "\n\n");
                    }
                    tv_admin_news.setText(stringBuffer.toString());
                } else {
                    tv_admin_news.setText("数据为空。");
                }
            }
        });

        btn_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QuestionService questionService = new QuestionService(myContext);
                List<Question> questionList = questionService.readAll();
                if (questionList != null && questionList.size() > 0) {
                    StringBuffer stringBuffer = new StringBuffer("");
                    for (Question question : questionList) {
                        stringBuffer.append(question.toString() + "\n\n");
                    }
                    tv_admin_news.setText(stringBuffer.toString());
                } else {
                    tv_admin_news.setText("数据为空。");
                }
            }
        });

    }


    /**
     * 初始化控件
     */
    private void init() {

        et_depa_name = (EditText) findViewById(R.id.et_depa_name);
        et_depa_introduce = (EditText) findViewById(R.id.et_depa_introduce);
        et_depa_address = (EditText) findViewById(R.id.et_depa_address);

        btn_1 = (Button) findViewById(R.id.btn_1);
        btn_2 = (Button) findViewById(R.id.btn_2);
        btn_3 = (Button) findViewById(R.id.btn_3);
        btn_4 = (Button) findViewById(R.id.btn_4);

        btn_5 = (Button) findViewById(R.id.btn_5);
        btn_6 = (Button) findViewById(R.id.btn_6);
        btn_7 = (Button) findViewById(R.id.btn_7);
        btn_8 = (Button) findViewById(R.id.btn_8);

        tv_admin_news = (TextView) findViewById(R.id.tv_admin_news);


    }

}

