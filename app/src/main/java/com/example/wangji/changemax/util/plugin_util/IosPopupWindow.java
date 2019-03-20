package com.example.wangji.changemax.util.plugin_util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.example.wangji.changemax.R;
import com.example.wangji.changemax.activity.web.WebActivity;
import com.example.wangji.changemax.activity.map.MapActivity;

/**
 * Created by WangJi.
 * 界面下方弹窗插件
 */
public class IosPopupWindow extends PopupWindow implements View.OnClickListener {

    private Context mContext;
    private PoiDetailResult pdr;

    private ImageView iv_building_img;
    private Button btn_building_name, btn_building_go;
    private TextView tv_building_intro;
    private Button btn_cancel;


    public IosPopupWindow(Activity activity, PoiDetailResult poiDetailResult) {
        super(activity);
        mContext = activity;
        this.pdr = poiDetailResult;

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View contentView = inflater.inflate(R.layout.dialog_share, null);
        setContentView(contentView);

        int screenWidth = activity.getWindowManager().getDefaultDisplay().getWidth();
        //获取popupwindow的高度与宽度
        this.setWidth((int) (screenWidth - 2 * dp2px(mContext, 12f)));
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));// 设置背景透明度
        this.setAnimationStyle(R.style.IosDialog);  // 设置动画
        this.setFocusable(true); // 设置弹出窗体可点击
        this.setOutsideTouchable(true);  // 点击外部可取消

        initView(contentView);
    }

    private void initView(View contentView) {
        btn_cancel = (Button) contentView.findViewById(R.id.btn_cancel);

        btn_building_name = (Button) contentView.findViewById(R.id.btn_building_name);
        tv_building_intro = (TextView) contentView.findViewById(R.id.tv_building_intro);
        btn_building_go = (Button) contentView.findViewById(R.id.btn_building_go);

        iv_building_img = (ImageView) contentView.findViewById(R.id.iv_building_img);


        btn_cancel.setOnClickListener(this);

        btn_building_name.setOnClickListener(this);
        tv_building_intro.setOnClickListener(this);
        btn_building_go.setOnClickListener(this);

        iv_building_img.setOnClickListener(this);

        updateObject();
    }

    private void updateObject() {
        btn_building_name.setText(pdr.getName());

        String telePhone = pdr.getTelephone();
        if (TextUtils.isEmpty(telePhone)) {
            tv_building_intro.setText(pdr.getAddress() + "\r\n");
        } else {
            tv_building_intro.setText("联系电话：" + telePhone + "\r\n" + pdr.getAddress() + "\r\n");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel:
                dismiss();
                break;
            case R.id.btn_building_name:

                break;
            case R.id.tv_building_intro:
                Toast.makeText(mContext, "【" + pdr.getName() + "】详细信息", Toast.LENGTH_SHORT).show();
                //                调用自定义浏览器进行访问该网页
                Intent intent = new Intent(mContext, WebActivity.class);
                intent.putExtra("url", pdr.getDetailUrl());
                MapActivity.getMapActivity().jumpPage(intent);


                //调用内置浏览器进行访问该网页
                //                Uri uri = Uri.parse(pdr.getDetailUrl());
                //                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                //                MapActivity.getMapActivity().jumpPage(intent);

                //                MapActivity.getMapActivity().selectWalkingLine(pdr.getName());
                break;
            case R.id.btn_building_go:
                Toast.makeText(mContext, "正在分析【" + pdr.getName() + "】的最优路线信息", Toast.LENGTH_SHORT).show();
                MapActivity.getMapActivity().selectWalkingLine(pdr.getName());
                break;
            case R.id.iv_building_img:


                break;
        }
    }

    /**
     * 显示在屏幕的下方
     */
    public void showAtScreenBottom(View parent) {
        this.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        popOutShadow();
    }

    /**
     * 让popupwindow以外区域阴影显示
     */
    private void popOutShadow() {
        final Window window = ((Activity) mContext).getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 0.3f;//设置阴影透明度


        window.setAttributes(lp);
        setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.alpha = 1f;
                window.setAttributes(lp);
            }
        });
    }

    /**
     * dp转 px.
     *
     * @param value the value
     * @return the int
     */
    public static int dp2px(Context context, float value) {
        final float scale = context.getResources().getDisplayMetrics().densityDpi;
        return (int) (value * (scale / 160) + 0.5f);
    }
}
