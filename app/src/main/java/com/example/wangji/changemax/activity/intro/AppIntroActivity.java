package com.example.wangji.changemax.activity.intro;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wangji.changemax.R;

/**
 * Created by WangJi.
 */
public class AppIntroActivity extends Activity {
    private ImageButton ib_return_me, ib_share;
    private TextView changemax_wangji;
    private TextView tv_app_intro, tv_app_use;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_intro);

        ib_return_me = (ImageButton) findViewById(R.id.ib_return_me);
        ib_share = (ImageButton) findViewById(R.id.ib_share);

        changemax_wangji = (TextView) findViewById(R.id.changemax_wangji);

        tv_app_intro = (TextView) findViewById(R.id.tv_app_intro);
        tv_app_use = (TextView) findViewById(R.id.tv_app_use);
        tv_app_intro.setText("changeMax医疗健康助手是一个Java后端，" +
                "搭建在android开发平台的对话式人工智能，能够通过中文自然语言对话交互方式来实现对用户的生理、" +
                "心理辅导并给出有趣的、亲和的反馈内容。【内测阶段】");

        tv_app_use.setText("用户可以通过双击屏幕实现语音交互和文字交互两种方式，在本人工智能中存在一个强大的后台医疗库，可满足日常医疗知识的普及宣传。" +
                "且含有多个彩蛋，总结一句话：\r\n\r\n界面简单单一，易上手，但功能强大到你不可想象，等你去发现。");


        changemax_wangji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    String url = "mqqwpa://im/chat?chat_type=wpa&uin=475652900&version=1";
                    Intent intentKeFu = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intentKeFu);
                } catch (Exception e) {
                    Toast.makeText(AppIntroActivity.this, "没有检测到QQ，启动失败....", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ib_return_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    public void run() {
                        try {
                            Instrumentation inst = new Instrumentation();
                            inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
                        } catch (Exception e) {
                            Log.e("sendKeyDownUpSync", e.toString());
                        }
                    }
                }.start();

            }
        });

        ib_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    Intent intentShare = new Intent(Intent.ACTION_SEND);
                    //        intentShare.setType("image/jpeg");
                    intentShare.setType("text/plain");
                    //        intentShare.putExtra(Intent.EXTRA_STREAM, Uri.parse(path));
                    intentShare.putExtra(Intent.EXTRA_TEXT, "changeMax医疗健康助手" +
                            "\r\n生物密码的解读" +
                            "\r\n一场基于AI的未来医疗革命" +
                            "\r\n一个对话式人工智能应用" +
                            "\r\n【点击下载】https://pan.baidu.com/s/1RwHk57NBdb2DQ45tys_IMQ");
                    Intent.createChooser(intentShare, "分享");

                    startActivity(Intent.createChooser(intentShare, "分享"));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
