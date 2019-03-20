package com.example.wangji.changemax.activity;


import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.wangji.changemax.R;
import com.example.wangji.changemax.activity.intro.AppIntroActivity;
import com.example.wangji.changemax.model.external.PersonChat;
import com.example.wangji.changemax.service.external.PersonChatService;
import com.example.wangji.changemax.util.other_util.KeyBoardUtils;
import com.example.wangji.changemax.util.adapter_util.ChatAdapter;
import com.example.wangji.changemax.util.other_util.ReturnRandomWords;
import com.example.wangji.changemax.util.permissions.PermissionBaseActivity;
import com.example.wangji.changemax.util.permissions.RequestPermissionCallBack;
import com.example.wangji.changemax.util.plugin_util.LoadingDialog;
import com.example.wangji.changemax.util.speech_util.tts.KqwSpeechCompound;
import com.example.wangji.changemax.util.sqlite_util.external.DBManager;
import com.example.wangji.changemax.util.time_util.IMainView;
import com.example.wangji.changemax.util.time_util.MainPresenter;
import com.example.wangji.changemax.util.ui_util.AnimationUtil;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

/**
 * 主函数
 * Created by WangJi.
 */
public class MainActivity extends PermissionBaseActivity implements IMainView {
    private static final String TAG = "MainActivity";

    private static MainActivity mainActivity;
    private static MainPresenter mainPresenter;

    private Context myContext = MainActivity.this;

    private String[] myPermissions = {
            //定位需要的动态权限
            Manifest.permission.ACCESS_COARSE_LOCATION,

            //录音权限
            Manifest.permission.RECORD_AUDIO
    };

    private long delayMillis = 5 * 60 * 1000;
    private int promptFrequency = 0;

    private boolean isExit = false;  //定义一个变量，来标识是否退出

    public static final String IFLY_VOICE_SDK_APP_ID = "59f85d08";

    private LoadingDialog loadingDialog;//loading弹窗对象
    private KqwSpeechCompound ksc;//创建语音合成对象

    private LinearLayout main_text, main_voice;
    //private Switch switch_text_voice;
    private boolean isChecked = true;
    private long[] mHits = new long[2];

    private Button btn_chat_message_send; //发送消息按钮对象
    private Button btn_chat_voice;
    private EditText et_chat_message;  //消息框对象
    private LinearLayout ll_total_view;//总控件
    private ImageButton ib_app_intro;//app介绍

    private ChatAdapter chatAdapter;//创建适配器对象
    private PersonChatService pcs;

    private ListView lv_chat_dialog;//声明ListView

    private List<PersonChat> personChatList = new ArrayList<PersonChat>();//存储在系统的生命周期内所有的聊天对象

    private final Handler mainHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            isExit = false;
            int what = msg.what;
            switch (what) {
                case 1:
                    //ListView条目控制在最后一行
                    chatAdapter.notifyDataSetChanged();
                    lv_chat_dialog.setSelection(personChatList.size());
                    break;
                case 0:

                    break;
                default:
                    break;
            }
        }
    };

    public MainActivity() {
        mainActivity = this;
    }

    public static MainActivity getMainActivity() {
        return mainActivity;
    }

    public void setShowLoading(boolean isShowLoading) {
        try {
            if (loadingDialog != null) {
                if (isShowLoading) {
                    loadingDialog = new LoadingDialog(this, "analysising...", R.mipmap.ic_dialog_loading);//实例化新等待对象
                    mainHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadingDialog.show();
                        }
                    }, 0);
                } else {
                    mainHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadingDialog.dismiss();
                        }
                    }, 0);
                    loadingDialog.setStartSwitch(false);
                }
            } else {//end  if (loadingDialog != null)
                loadingDialog = new LoadingDialog(this, "analysising...", R.mipmap.ic_dialog_loading);//实例化新等待对象
            }
        } catch (Exception e) {

        }
    }//end setShowLoading

    public LinearLayout getLl_total_view() {
        return ll_total_view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //去掉标题栏（ActionBar实际上是设置在标题栏上的）
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //去掉状态栏(顶部显示时间、电量的部分)，设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        init();//初始化
        welcome();//开场白
        clickEventSet();//点击按钮事件


    }

    private void welcome() {
        isFirstStart();
    }

    /**
     * 控件点击事件集
     */
    private void clickEventSet() {

        ll_total_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //按下
                KeyBoardUtils.closeKeyBoard(et_chat_message, MainActivity.this);//关闭软键盘

                System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
                mHits[mHits.length - 1] = SystemClock.uptimeMillis();
                if (mHits[0] >= (SystemClock.uptimeMillis() - 500)) {
                    if (isChecked) {
                        main_text.setVisibility(View.VISIBLE);
                        main_voice.setVisibility(View.GONE);
                        isChecked = false;
                        Toast.makeText(MainActivity.this, "切换为键盘输入模式", Toast.LENGTH_SHORT).show();

                        //上下切换动画
                        main_voice.setAnimation(AnimationUtil.moveToViewBottom());
                        main_text.setAnimation(AnimationUtil.moveToViewLocation());
                    } else {
                        main_voice.setVisibility(View.VISIBLE);
                        main_text.setVisibility(View.GONE);
                        isChecked = true;
                        Toast.makeText(MainActivity.this, "切换为语音对话模式", Toast.LENGTH_SHORT).show();

                        //上下切换动画
                        main_text.setAnimation(AnimationUtil.moveToViewBottom());
                        main_voice.setAnimation(AnimationUtil.moveToViewLocation());
                    }
                }

            }
        });

        /**
         * 发送按钮的点击事件
         */
        btn_chat_message_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取输入框中内容
                final String msg = et_chat_message.getText().toString();
                if (TextUtils.isEmpty(msg)) {
                    Toast.makeText(MainActivity.this, "你是不是忘了什么o...", Toast.LENGTH_SHORT).show();
                    KeyBoardUtils.openKeyBorad(et_chat_message, MainActivity.this);//关闭软键盘
                    return;
                }
                et_chat_message.setText("");//清空输入框
                KeyBoardUtils.closeKeyBoard(et_chat_message, MainActivity.this);//关闭软键盘

                PersonChat personChat_user = pcs.getPersonChat_user(msg);//创建一个用户消息对象
                pcs.getPersonChat_changeMax(personChat_user); //分析
            }
        });

        KeyBoardUtils.closeKeyBoard(et_chat_message, MainActivity.this);//关闭软键盘

        btn_chat_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openVoiceInteraction();
            }
        });

        ib_app_intro.setOnClickListener(new View.OnClickListener() {//对app的简介点击事件
            @Override
            public void onClick(View v) {
                jumpPage(new Intent(MainActivity.this, AppIntroActivity.class));

            }
        });

    }


    //对象初始化
    private void objectInIt() {
        mainPresenter = new MainPresenter(mainActivity);
        SpeechUtility.createUtility(myContext, SpeechConstant.APPID + "=" + IFLY_VOICE_SDK_APP_ID);

        ksc = new KqwSpeechCompound(myContext);//实例化语音合成类
        pcs = new PersonChatService(myContext);//实例化消息对象工具类

        loadingDialog = new LoadingDialog(this, "loading...", R.mipmap.ic_dialog_loading);//实例化新等待对象
    }

    //控件初始化
    private void viewInit() {
        ll_total_view = (LinearLayout) findViewById(R.id.ll_total_view);

        //        switch_text_voice = (Switch) findViewById(R.id.switch_text_voice);
        main_text = (LinearLayout) findViewById(R.id.main_text);
        main_voice = (LinearLayout) findViewById(R.id.main_voice);

        lv_chat_dialog = (ListView) findViewById(R.id.lv_chat_dialog);

        btn_chat_message_send = (Button) findViewById(R.id.btn_chat_message_send);
        et_chat_message = (EditText) findViewById(R.id.et_chat_message);

        btn_chat_voice = (Button) findViewById(R.id.btn_chat_voice);
        //        ll_total_view.getBackground().setAlpha(0);

        ib_app_intro = (ImageButton) findViewById(R.id.ib_app_intro);
    }

    //初始化
    private void init() {
        getPermission();//进行获取权限
        objectInIt();//对象初始化
        viewInit();//初始化控件
        initChatAdapter();//设置适配器
        initSqlite();//初始化数据库


    }

    //初始化数据库
    private void initSqlite() {
        String sqliteName = "medical_library.db";
        new DBManager(myContext).CopySqliteFileFromRawToDatabases(sqliteName);//传入数据库到本地
    }

    //设置适配器
    private void initChatAdapter() {
        chatAdapter = new ChatAdapter(this, personChatList);
        lv_chat_dialog.setAdapter(chatAdapter);
    }

    /**
     * 对changMax消息进行ui显示
     *
     * @param changeMaxPersonChatList
     */
    public void changeMaxFeedback(List<PersonChat> changeMaxPersonChatList) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (PersonChat personChat : changeMaxPersonChatList) {
                    personChatList.add(personChat);
                    mainHandler.sendEmptyMessage(1);
                    String readChatMessage = personChat.getReadChatMessage();
                    ksc.speak(readChatMessage);
                    try {
                        Thread.sleep(handlerSpeakTime(readChatMessage));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }//end changeMaxFeedback()

    /**
     * 通过文字分析说话时长
     *
     * @param readChatMessage
     * @return
     */
    private long handlerSpeakTime(String readChatMessage) {
        int length = readChatMessage.length();
        double multiple = 3.5;
        double time = (long) (length * 1000) / multiple;
        return new Double(time).longValue();
    }

    /**
     * 对用户消息进行ui显示
     *
     * @param personChat_user
     */
    public void userFeedback(PersonChat personChat_user) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                personChatList.add(personChat_user);
                mainHandler.sendEmptyMessage(1);
            }
        }).start();
    }

    /**
     * 跳转页面
     *
     * @param intent
     */
    public void jumpPage(Intent intent) {
        if (intent != null) {
            try {
                startActivity(intent);
                overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
                ksc.stop();//停止当前语音
            } catch (Exception e) {
                Toast.makeText(myContext, "启动失败....", Toast.LENGTH_SHORT).show();
            }
        }
    }


    /**
     * 开启语音交互功能
     */
    private void openVoiceInteraction() {
        RecognizerDialog rDialog = new RecognizerDialog(this, null);
        rDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        rDialog.setParameter(SpeechConstant.ACCENT, "mandarin");
        rDialog.setListener(new RecognizerDialogListener() {
            @Override
            public void onResult(RecognizerResult recognizerResult, boolean b) {
                printResult(recognizerResult);
            }

            @Override
            public void onError(SpeechError speechError) {
            }
        });

        rDialog.show();//显示人机交互的dialog，并且开启语音转文字
    }//end btnVoce()

    /**
     * 回调说话的结果
     *
     * @param results
     */
    private void printResult(RecognizerResult results) {
        String speakWords = parseIatResult(results.getResultString());
        if (speakWords.trim().length() <= 1) {
            return;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(speakWords);

        String userSpeakString = sb.toString();
        if (!TextUtils.isEmpty(userSpeakString)) {
            PersonChat personChat_user = pcs.getPersonChat_user(userSpeakString);//创建一个用户消息对象
            pcs.getPersonChat_changeMax(personChat_user); //分析
        }
    }//end printResult

    /**
     * 解析语音识别的数据
     *
     * @param json
     * @return
     */
    private static String parseIatResult(String json) {
        StringBuffer ret = new StringBuffer();
        try {
            JSONTokener tokener = new JSONTokener(json);
            JSONObject joResult = new JSONObject(tokener);
            JSONArray words = joResult.getJSONArray("ws");
            for (int i = 0; i < words.length(); i++) {
                // 转写结果词，默认使用第一个结果
                JSONArray items = words.getJSONObject(i).getJSONArray("cw");
                JSONObject obj = items.getJSONObject(0);
                ret.append(obj.getString("w"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret.toString();
    }//end parseIatResult()


    /**
     * 拦截返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }


        return super.onKeyDown(keyCode, event);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        /*
        用户响应changeMax，重置计时段，计次
        */
        delayMillis = 5 * 60 * 1000;
        promptFrequency = 0;
        mainPresenter.resetTipsTimer(delayMillis);
        //        Toast.makeText(getApplicationContext(), "初始化响应事件：" + delayMillis + "==" + promptFrequency, Toast.LENGTH_SHORT).show();
        return super.dispatchTouchEvent(event);
    }


    /**
     * 退出程序
     */
    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "changeMax健康助手：你是要离我而去了吗？", Toast.LENGTH_SHORT).show();
            //利用handler延迟发送更改状态信息
            mainHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            System.exit(0);
        }
    }

    /**
     * 判断是否为第一次启动
     */
    private void isFirstStart() {
        //        SharedPreferences sharedPreferences = getSharedPreferences("FirstRun",0);
        SharedPreferences sharedPreferences = getSharedPreferences("FirstRun", MODE_PRIVATE);
        boolean isfer = sharedPreferences.getBoolean("First", true);
        if (isfer) {
            //为第一次启动
            sharedPreferences.edit().putBoolean("First", false).commit();
            //进行首次启动功能导航
            pcs.getWelcomePersonChat(0);
        } else {
            pcs.getWelcomePersonChat(1);
        }

    }//end isFirstStart

    /**
     * 权限获取
     */
    private void getPermission() {
        requestPermissions(MainActivity.this, myPermissions, new RequestPermissionCallBack() {
            @Override
            public void granted() {//权限获取成功
            }

            @Override
            public void denied() {
                Toast.makeText(MainActivity.this, "部分权限获取失败，正常功能受到影响,2秒钟之后自动退出", Toast.LENGTH_LONG).show();
                //2秒钟之后自动退出
                mainHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        MainActivity.this.finish();
                        System.exit(0);
                    }
                }, 2000);
            }
        });
    }//end getPermission

    @Override
    protected void onDestroy() {
        //销毁时停止定时
        mainPresenter.endTipsTimer();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        //启动默认开始计时
        mainPresenter.startTipsTimer(delayMillis);
        super.onResume();
    }

    @Override
    protected void onPause() {
        //当activity不在前台，有其他操作时结束计时
        mainPresenter.endTipsTimer();
        super.onPause();
    }

    @Override
    public void showTipsView() {
        promptFrequency++; //响应一次， promptFrequency计数一次
        delayMillis = 30 * 1000;//由于用户长时间未进行操作，时间段缩短为30 * 1000
        List<PersonChat> promptPersonChatList = new ArrayList<PersonChat>();
        if (promptFrequency >= 5) {//当响应次数达到一定上限之后，系统将做出释放内存操作，退出应用
            try {
                promptPersonChatList.add(pcs.getPersonChat_changeMax(new ReturnRandomWords().returnPromptString(promptFrequency)));
                promptPersonChatList.add(pcs.getPersonChat_changeMax("3秒后，健康医疗系统将自动退出。"));
                changeMaxFeedback(promptPersonChatList);

                Thread.sleep(10000);
                MainActivity.this.finish();
                System.exit(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        promptPersonChatList.add(pcs.getPersonChat_changeMax(new ReturnRandomWords().returnPromptString(promptFrequency)));
        //进行提示操作
        changeMaxFeedback(promptPersonChatList);

        mainPresenter.startTipsTimer(delayMillis);//继续开始提示
        // pcs.getWelcomePersonChat(1);
    }

}