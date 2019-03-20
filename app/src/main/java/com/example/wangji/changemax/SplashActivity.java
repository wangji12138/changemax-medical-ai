package com.example.wangji.changemax;


import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.wangji.changemax.activity.MainActivity;
import com.example.wangji.changemax.activity.app_introduction.FirstBootActivity;
import com.example.wangji.changemax.util.permissions.PermissionBaseActivity;
import com.example.wangji.changemax.util.permissions.RequestPermissionCallBack;
import com.example.wangji.changemax.util.share.TimeBaseActivity;

/**
 * Created by WangJi.
 */
public class SplashActivity extends PermissionBaseActivity {
    private String[] myPermissions = {
            //定位需要的动态权限
            Manifest.permission.ACCESS_COARSE_LOCATION,

            //录音权限
            Manifest.permission.RECORD_AUDIO
    };
    private final Handler splashHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup_diagram);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏

        //        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        getPermission();//进行获取权限
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
            // sharedPreferences.edit().putBoolean("First", false).commit();

            //进行首次启动功能导航
            splashHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, FirstBootActivity.class));
                    SplashActivity.this.finish();
                }
            }, 500);
        } else {
            splashHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
                    SplashActivity.this.finish();
                }
            }, 2000);
        }

    }//end isFirstStart


    private void getPermission() {
        requestPermissions(this, myPermissions, new RequestPermissionCallBack() {
            @Override
            public void granted() {
                //Toast.makeText(SplashActivity.this, "权限获取成功", Toast.LENGTH_SHORT).show();
                isFirstStart();
            }

            @Override
            public void denied() {
                Toast.makeText(SplashActivity.this, "部分权限获取失败，正常功能受到影响,2秒钟之后自动退出", Toast.LENGTH_LONG).show();
                //2秒钟之后自动退出
                splashHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SplashActivity.this.finish();
                    }
                }, 2000);
            }
        });
    }//end getPermission


}
