package com.example.wangji.changemax.util.plugin_util;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wangji.changemax.R;
import com.example.wangji.changemax.util.other_util.ReturnRandomWords;

import java.util.List;

/**
 * Created by WangJi.
 * Loading插件
 */
public class LoadingDialog extends Dialog {

    private static final String TAG = "LoadingDialog";

    private String mMessage;
    private int mImageId;
    private boolean mCancelable;
    private RotateAnimation mRotateAnimation;

    private TextView tv_loading;
    private ImageView iv_loading;

    private int i = 0;
    private boolean startSwitch = true;

    private List<String> analysisList = new ReturnRandomWords().getAnalysisListEnglish();

    public void setStartSwitch(boolean startSwitch) {
        this.startSwitch = startSwitch;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (startSwitch) {
                        if (i == analysisList.size() - 1) {
                            i = 0;
                        }
                        tv_loading.setText(analysisList.get(i));
                        i++;
                        handler.sendEmptyMessageDelayed(1, 250);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    public LoadingDialog(@NonNull Context context, String message, int imageId) {
        this(context, R.style.LoadingDialog, message, imageId, false);
        i = 0;
    }

    public LoadingDialog(@NonNull Context context, int themeResId, String message, int imageId, boolean cancelable) {
        super(context, themeResId);
        mMessage = message;
        mImageId = imageId;
        mCancelable = cancelable;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        initView();
        handler.sendEmptyMessage(1);

    }

    private void initView() {
        setContentView(R.layout.dialog_loading);
        // 设置窗口大小
        WindowManager windowManager = getWindow().getWindowManager();
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.alpha = 0.2f;
        attributes.width = screenWidth / 2;
        attributes.height = attributes.width;
        getWindow().setAttributes(attributes);
        setCancelable(mCancelable);

        tv_loading = findViewById(R.id.tv_loading);
        iv_loading = findViewById(R.id.iv_loading);

        tv_loading.setText(mMessage);
        iv_loading.setImageResource(mImageId);
        iv_loading.measure(0, 0);
        mRotateAnimation = new RotateAnimation(0, 360, iv_loading.getMeasuredWidth() / 2, iv_loading.getMeasuredHeight() / 2);
        mRotateAnimation.setInterpolator(new LinearInterpolator());
        mRotateAnimation.setDuration(1000);
        mRotateAnimation.setRepeatCount(-1);
        iv_loading.startAnimation(mRotateAnimation);
    }

    @Override
    public void dismiss() {
        mRotateAnimation.cancel();
        super.dismiss();
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 屏蔽返回键
            return mCancelable;
        }
        return super.onKeyDown(keyCode, event);
    }
}
