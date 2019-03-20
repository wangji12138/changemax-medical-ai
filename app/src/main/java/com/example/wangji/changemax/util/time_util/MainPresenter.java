package com.example.wangji.changemax.util.time_util;

import android.os.Handler;
import android.os.Message;

/**
 * Created by WangJi.
 */
public class MainPresenter {
    public final static int MSG_SHOW_TIPS = 0x01;

    private IMainView mMainView;

    private MainHandler mMainHandler;

    private boolean tipsIsShowed = true;

    private Runnable tipsShowRunable = new Runnable() {
        @Override
        public void run() {
            mMainHandler.obtainMessage(MSG_SHOW_TIPS).sendToTarget();
        }
    };

    public MainPresenter(IMainView view) {
        mMainView = view;
        mMainHandler = new MainHandler();
    }

    /**
     * <无操作时开始计时>
     * <功能详细描述>
     *
     * @see [类、类#方法、类#成员]
     */
    public void startTipsTimer(long delayMillis) {
        mMainHandler.postDelayed(tipsShowRunable, delayMillis);
    }

    /**
     * <结束当前计时,重置计时>
     * <功能详细描述>
     *
     * @see [类、类#方法、类#成员]
     */
    public void endTipsTimer() {
        mMainHandler.removeCallbacks(tipsShowRunable);
    }

    /**
     * 重置提示小时
     *
     * @param delayMillis
     */
    public void resetTipsTimer(long delayMillis) {
        tipsIsShowed = false;
        mMainHandler.removeCallbacks(tipsShowRunable);
        mMainHandler.postDelayed(tipsShowRunable, delayMillis);
    }

    public class MainHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SHOW_TIPS:
                    mMainView.showTipsView();
                    tipsIsShowed = true;
                    // 屏保显示,两秒内连续按下键盘Enter键可关闭屏保
                    break;
            }

        }

    }
}
