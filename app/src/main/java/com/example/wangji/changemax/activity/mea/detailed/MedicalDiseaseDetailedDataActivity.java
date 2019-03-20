package com.example.wangji.changemax.activity.mea.detailed;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wangji.changemax.R;
import com.example.wangji.changemax.activity.mea.MedicalEncyclopediaActivity;
import com.example.wangji.changemax.model.internal.Disease;
import com.example.wangji.changemax.service.internal.DiseaseService;

import java.util.ArrayList;

/**
 * Created by WangJi.
 */
public class MedicalDiseaseDetailedDataActivity extends Activity {

    private Context myContent = this;

    private ImageButton return_me;

    private TextView tv_disease_name;
    private TextView tv_disease_intro;
    private TextView tv_disease_alias, tv_disease_incidence_site, tv_disease_contagious, tv_disease_multiple_people, tv_disease_visit_department, tv_disease_cure_rate;
    private TextView tv_disease_symptom_early, tv_disease_symptom_late;
    private TextView tv_disease_symptom_intro;
    private TextView tv_disease_complication_intro;

    private TextView tv_disease_symptom_intro_detailed, tv_disease_complication_intro_detailed;

    private LinearLayout ll_disease_symptom_related, ll_complication;

    private DiseaseService diseaseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_disease_detailed_data);

        init();

        //接收传入关键词，初始化数据
        Intent intent = getIntent();
        String disease_id = intent.getStringExtra("disease_id");
        String disease_name = intent.getStringExtra("disease_name");

        if (TextUtils.isEmpty(disease_id)) {
            if (TextUtils.isEmpty(disease_name)) {
                //结束此页面。返回上一级页面
                Toast.makeText(MedicalDiseaseDetailedDataActivity.this, "该医疗数据为空，后期继续完善", Toast.LENGTH_LONG).show();

                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                this.finish();

            } else {
                nameDataInit(disease_name);
            }
        } else {
            idDataInit(disease_id);
        }


        return_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MedicalDiseaseDetailedDataActivity.this, MedicalEncyclopediaActivity.class));
                MedicalDiseaseDetailedDataActivity.this.finish();
            }
        });
    }//end onCreate()

    private void init() {
        viewInit();
        objectInit();
    }

    /**
     * 初始化控件
     */
    private void viewInit() {
        return_me = (ImageButton) findViewById(R.id.return_me);

        tv_disease_name = (TextView) findViewById(R.id.tv_disease_name);

        tv_disease_incidence_site = (TextView) findViewById(R.id.tv_disease_incidence_site);
        tv_disease_contagious = (TextView) findViewById(R.id.tv_disease_contagious);
        tv_disease_symptom_early = (TextView) findViewById(R.id.tv_disease_symptom_early);
        tv_disease_visit_department = (TextView) findViewById(R.id.tv_disease_visit_department);
        tv_disease_cure_rate = (TextView) findViewById(R.id.tv_disease_cure_rate);

        tv_disease_alias = (TextView) findViewById(R.id.tv_disease_alias);
        tv_disease_intro = (TextView) findViewById(R.id.tv_disease_intro);
        tv_disease_multiple_people = (TextView) findViewById(R.id.tv_disease_multiple_people);
        tv_disease_symptom_late = (TextView) findViewById(R.id.tv_disease_symptom_late);
        tv_disease_symptom_intro = (TextView) findViewById(R.id.tv_disease_symptom_intro);
        tv_disease_complication_intro = (TextView) findViewById(R.id.tv_disease_complication_intro);

        tv_disease_symptom_intro_detailed = (TextView) findViewById(R.id.tv_disease_symptom_intro_detailed);
        tv_disease_complication_intro_detailed = (TextView) findViewById(R.id.tv_disease_complication_intro_detailed);

        ll_disease_symptom_related = (LinearLayout) findViewById(R.id.ll_disease_symptom_related);
        ll_complication = (LinearLayout) findViewById(R.id.ll_complication);
    }

    private void objectInit() {
        diseaseService = new DiseaseService(myContent);
    }

    /**
     * 根据传入id初始化页面数据
     *
     * @param disease_id
     */
    private void idDataInit(String disease_id) {
        int id = Integer.parseInt(disease_id);
        Disease disease = diseaseService.getDiseaseById(id);
        if (disease != null) {
            showDisease(disease);
        } else {
            //结束此页面，自动返回上一级
            Toast.makeText(MedicalDiseaseDetailedDataActivity.this, "该医疗数据为空，后期继续完善", Toast.LENGTH_LONG).show();
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.finish();
        }

    }

    /**
     * 根据传入name初始化页面数据
     *
     * @param disease_name
     */
    private void nameDataInit(String disease_name) {
        Disease disease = diseaseService.getDiseaseByName(disease_name);
        if (disease != null) {
            showDisease(disease);
        } else {
            //结束此页面，自动返回上一级
            if (disease_name.equals("不详")) {
                Toast.makeText(MedicalDiseaseDetailedDataActivity.this, "都写了【不详】，还点，真是的。", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(MedicalDiseaseDetailedDataActivity.this, "该医疗数据为空，后期继续完善", Toast.LENGTH_LONG).show();
            }
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.finish();
        }
    }

    /**
     * 显示数据
     *
     * @param disease
     */
    private void showDisease(Disease disease) {
        String disease_name = disease.getDisease_name();// 疾病名称
        String disease_alias = disease.getDisease_alias();// 疾病别名
        String disease_intro = disease.getDisease_intro();// 疾病简介
        String disease_incidence_site = disease.getDisease_incidence_site();// 疾病发病部位集 disease_incidence_site
        String disease_contagious = disease.getDisease_contagious();// 疾病的传染性
        String disease_multiple_people = disease.getDisease_multiple_people();// 疾病多发人群
        String disease_symptom_early = disease.getDisease_symptom_early();// 疾病早期症状
        String disease_symptom_late = disease.getDisease_symptom_late();// 疾病晚期症状
        String disease_symptom_related = disease.getDisease_symptom_related();// 疾病相关症状
        String disease_symptom_intro = disease.getDisease_symptom_intro();// 疾病相关症状介绍
        String disease_complication = disease.getDisease_complication();// 疾病并发症
        String disease_complication_intro = disease.getDisease_complication_intro();// 疾病并发症介绍
        String disease_visit_department = disease.getDisease_visit_department();// 疾病就诊科室
        String disease_cure_rate = disease.getDisease_cure_rate();// 疾病治愈率


        tv_disease_name.setText(isNull(disease_name) ? " " : disease_name);

        tv_disease_alias.setText(isNull(disease_alias) ? "不详" : disease_alias);
        tv_disease_intro.setText(isNull(disease_alias) ? "不详" : "\n       " + disease_intro);
        tv_disease_incidence_site.setText(isNull(disease_incidence_site) ? "不详" : disease_incidence_site);
        tv_disease_contagious.setText(isNull(disease_contagious) ? "不详" : disease_contagious);
        tv_disease_multiple_people.setText(isNull(disease_multiple_people) ? "不详" : disease_multiple_people);
        tv_disease_symptom_early.setText(isNull(disease_symptom_early) ? "不详" : "\n" + "       " + disease_symptom_early);
        tv_disease_symptom_late.setText(isNull(disease_symptom_late) ? "不详" : "\n" + "       " + disease_symptom_late);

        if (isNull(disease_symptom_related)) {//相关症状，可设指定点击事件，实现跳转相关症状
            generateView("不详", "sy");
        } else {
            generateView(disease_symptom_related, "sy");
        }

        if (isNull(disease_symptom_intro)) {
            tv_disease_symptom_intro.setText("不详");
        } else {
            tv_disease_symptom_intro.setText("\n" + "       " + disease_symptom_intro);
            if (disease_symptom_intro.length() < 150) {//如果简介字符串长度小于150，那么将隐藏【详细】
                tv_disease_symptom_intro_detailed.setVisibility(View.GONE);
            }
        }

        if (isNull(disease_complication)) {//并发症，可设指定点击事件，实现跳转相关疾病
            generateView("不详", "di");
        } else {
            generateView(disease_complication, "di");
        }

        if (isNull(disease_complication_intro)) {
            tv_disease_complication_intro.setText("不详");
        } else {
            tv_disease_complication_intro.setText("\n" + "       " + disease_complication_intro);
            if (disease_complication_intro.length() < 150) {//如果简介字符串长度小于150，那么将隐藏【详细】
                tv_disease_complication_intro_detailed.setVisibility(View.GONE);
            }
        }

        tv_disease_visit_department.setText(isNull(disease_visit_department) ? "不详" : disease_visit_department);
        tv_disease_cure_rate.setText(isNull(disease_cure_rate) ? "不详" : disease_cure_rate);

        //相关症状介绍详细点击事件
        tv_disease_symptom_intro_detailed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExitDialog("相关症状详细介绍", disease_symptom_intro);
            }
        });

        //并发症介绍详细点击事件
        tv_disease_complication_intro_detailed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExitDialog("并发症详细介绍", disease_complication_intro);
            }
        });


    }

    /**
     * 判断字符串是否为空
     *
     * @param string
     * @return
     */
    private boolean isNull(String string) {
        if (TextUtils.isEmpty(string)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 开启一个新的本页面
     *
     * @param diOrSy
     * @param name
     */
    private void newMedicalDetailedDataActivity(String diOrSy, String name) {
        Intent intent = null;
        if (diOrSy.equals("di")) {
            intent = new Intent(MedicalDiseaseDetailedDataActivity.this, MedicalDiseaseDetailedDataActivity.class);
            intent.putExtra("disease_name", name);
        } else {//跳转到为症状界面
            intent = new Intent(MedicalDiseaseDetailedDataActivity.this, MedicalSymptomDetailedDataActivity.class);
            intent.putExtra("symptom_name", name);
        }
        startActivity(intent);
        overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.finish();//监听返会按钮，返回将结束本页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void generateView(String string, String diOrSy) {
        LinearLayout linearLayout = null;
        if (diOrSy.equals("di")) {
            linearLayout = ll_complication;
        } else {
            linearLayout = ll_disease_symptom_related;
        }

        String[] strings = string.split(" ");

        int size = strings.length; // 添加Button的个数
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT); // 每行的水平LinearLayout
        layoutParams.setMargins(10, 3, 10, 3);


        ArrayList<Button> childBtns = new ArrayList<Button>();
        int totoalBtns = 0;

        for (int i = 0; i < size; i++) {
            String item1 = strings[i];
            String item = "【" + item1 + "】";
            LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            int length = item.length();


            if (length < 4) {  // 根据字数来判断按钮的空间长度, 少于4个当一个按钮
                itemParams.weight = 1;
                totoalBtns++;
            } else if (length < 8) { // <8个两个按钮空间
                itemParams.weight = 2;
                totoalBtns += 2;
            } else {
                itemParams.weight = 3;
                totoalBtns += 3;
            }

            itemParams.width = 0;
            itemParams.setMargins(1, 1, 1, 1);
            Button childBtn = (Button) LayoutInflater.from(this).inflate(R.layout.item_button, null);
            childBtn.setText(item);
            childBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    newMedicalDetailedDataActivity(diOrSy, item1);
                }
            });
            childBtn.setTag(item);
            childBtn.setLayoutParams(itemParams);
            childBtns.add(childBtn);

            if (totoalBtns >= 5) {
                LinearLayout horizLL = new LinearLayout(this);
                horizLL.setOrientation(LinearLayout.HORIZONTAL);
                horizLL.setLayoutParams(layoutParams);

                for (Button addBtn : childBtns) {
                    horizLL.addView(addBtn);
                }
                linearLayout.addView(horizLL);
                childBtns.clear();
                totoalBtns = 0;
            }
        }
        //最后一行添加一下
        if (!childBtns.isEmpty()) {
            LinearLayout horizLL = new LinearLayout(this);
            horizLL.setOrientation(LinearLayout.HORIZONTAL);
            horizLL.setLayoutParams(layoutParams);

            for (Button addBtn : childBtns) {
                horizLL.addView(addBtn);
            }
            linearLayout.addView(horizLL);
            childBtns.clear();
            totoalBtns = 0;
        }
    }


    /**
     * 简单消息提示框
     *
     * @param title
     * @param message
     */
    private void showExitDialog(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("返回", null)
                .show();
    }

}

