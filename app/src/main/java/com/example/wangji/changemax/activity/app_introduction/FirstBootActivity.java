package com.example.wangji.changemax.activity.app_introduction;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.wangji.changemax.R;
import com.example.wangji.changemax.SplashActivity;
import com.example.wangji.changemax.activity.MainActivity;
import com.example.wangji.changemax.activity.app_introduction.widget.CircleIndicator;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangJi.
 */
public class FirstBootActivity extends Activity {
    private static final String TAG = "FirstBootActivity";

    private List<View> viewList;
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private final Handler mHandler = new Handler();

    private RelativeLayout activity_first_boot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_boot);

        initData();
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(pagerAdapter);

        circleIndicator = (CircleIndicator) findViewById(R.id.indicator);
        circleIndicator.setViewPager(viewPager);

        activity_first_boot = (RelativeLayout) findViewById(R.id.activity_first_boot);
    }

    private void initData() {
        viewList = new ArrayList<View>();

        List<Integer> backgroundList = new ArrayList<Integer>();
        backgroundList.add(R.drawable.ai_one);
        backgroundList.add(R.drawable.ai_two);
        backgroundList.add(R.drawable.ai_three);
        backgroundList.add(R.drawable.ai_four2);
        backgroundList.add(R.drawable.ai_five);


        //设置图片
        for (int i : backgroundList) {

            View view = new View(this);

            view.setBackgroundResource(i);


            viewList.add(view);
        }
    }

    /**
     * 以最省内存的方式读取本地资源的图片
     *
     * @param context
     * @param resId
     * @return
     */
    public static Bitmap readBitMap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        //获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    PagerAdapter pagerAdapter = new PagerAdapter() {
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {

            return arg0 == arg1;
        }

        @Override
        public int getCount() {
            return viewList.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(viewList.get(position));
            if (position == (((viewList.size() % 2) + (viewList.size() / 2)) - 1)) {
                showExitDialog();
            }
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "title";

        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewList.get(position));
            return viewList.get(position);
        }

    };

    private void showExitDialog() {
        new AlertDialog.Builder(this)
                .setTitle("人工智能医疗助手")
                .setMessage("您将创造一个医疗机器人\r\n基于人工智能领域，卷积神经网络算法的后台，多方服务器加持\r\n开启交流吧。")
                .setPositiveButton("创造", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        viewPager.setVisibility(View.GONE);
                        circleIndicator.setVisibility(View.GONE);
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(FirstBootActivity.this, MainActivity.class));
                                FirstBootActivity.this.finish();
                                overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
                                activity_first_boot.setBackgroundResource(R.drawable.change_max_bg);
                            }
                        }, 1000);
                    }
                })
                .setNegativeButton("算了", null)
                .show();
    }

    private void requestForConsent() {

        try {
            Toast.makeText(FirstBootActivity.this, "3秒后自动创建医疗机器人", Toast.LENGTH_SHORT).show();
            Thread.sleep(3000);
            startActivity(new Intent(FirstBootActivity.this, MainActivity.class));
            overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
            FirstBootActivity.this.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}


