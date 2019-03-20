package com.example.wangji.changemax.util.share;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import com.example.wangji.changemax.util.permissions.PermissionBaseActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by WangJi.
 */
public class TimeBaseActivity extends PermissionBaseActivity {

    // 都是static声明的变量，避免被实例化多次；因为整个app只需要一个计时任务就可以了。
    private static Timer mTimer; // 计时器，每1秒执行一次任务
    private static MyTimerTask mTimerTask; // 计时任务，判断是否未操作时间到达5s
    private static long mLastActionTime; // 上一次操作时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // 每当用户接触了屏幕，都会执行此方法
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mLastActionTime = System.currentTimeMillis();
        Log.e("TimeBaseActivity", "user action");
        return super.dispatchTouchEvent(ev);
    }

    private static class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            Log.e("TimeBaseActivity", "check time");
            // 1分未操作
            if (System.currentTimeMillis() - mLastActionTime > (1 * 60 * 1000)) {
                promptUser();
                // 停止计时任务
                stopTimer();
            }
        }
    }

    /**
     * 进行提示操作
     */
    protected static void promptUser() {

    }

    // 运行开始，开始计时
    protected static void startTimer() {
        mTimer = new Timer();
        mTimerTask = new MyTimerTask();
        // 初始化上次操作时间为登录成功的时间
        mLastActionTime = System.currentTimeMillis();
        // 每过1s检查一次
        mTimer.schedule(mTimerTask, 0, 1000);
        Log.e("TimeBaseActivity", "start timer");
    }

    // 停止计时任务
    protected static void stopTimer() {
        mTimer.cancel();
        Log.e("TimeBaseActivity", "cancel timer");
    }
}
