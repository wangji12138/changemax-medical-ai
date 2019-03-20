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
import com.example.wangji.changemax.model.external.MatchAttribute;
import com.example.wangji.changemax.model.internal.Disease;
import com.example.wangji.changemax.model.internal.DiseaseSy;
import com.example.wangji.changemax.model.internal.Symptom;
import com.example.wangji.changemax.service.internal.DiseaseService;
import com.example.wangji.changemax.service.internal.DiseaseSyService;
import com.example.wangji.changemax.service.internal.SymptomService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangJi.
 */
public class MedicalSymptomDetailedDataActivity extends Activity {
    private Context myContent = this;

    private ImageButton return_me;


    private TextView tv_symptom_name, tv_symptom_intro, tv_symptom_cause,
            tv_symptomatic_details_content, tv_suggested_treatment_department;

    private TextView tv_symptom_cause_detailed, tv_symptomatic_details_content_detailed;

    private LinearLayout ll_symptom_association_disease;

    private SymptomService symptomService;
    private DiseaseSyService diseaseSyService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_symptom_detailed_data);

        init();

        //接收传入关键词，初始化数据
        Intent intent = getIntent();
        String symptom_id = intent.getStringExtra("symptom_id");
        String symptom_name = intent.getStringExtra("symptom_name");

        if (TextUtils.isEmpty(symptom_id)) {
            if (TextUtils.isEmpty(symptom_name)) {
                //结束此页面。返回上一级页面
                Toast.makeText(MedicalSymptomDetailedDataActivity.this, "该医疗数据为空，后期继续完善", Toast.LENGTH_LONG).show();
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                this.finish();
            } else {
                nameDataInit(symptom_name);
            }
        } else {
            idDataInit(symptom_id);
        }

        return_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MedicalSymptomDetailedDataActivity.this, MedicalEncyclopediaActivity.class));
                MedicalSymptomDetailedDataActivity.this.finish();
            }
        });
    }//end onCreate()

    /**
     * 初始化
     */
    private void init() {
        viewInit();
        objectInit();
    }

    /**
     * 初始化控件
     */
    private void viewInit() {
        return_me = (ImageButton) findViewById(R.id.return_me);

        tv_symptom_name = (TextView) findViewById(R.id.tv_symptom_name);

        tv_symptom_intro = (TextView) findViewById(R.id.tv_symptom_intro);
        tv_symptom_cause = (TextView) findViewById(R.id.tv_symptom_cause);
        tv_symptomatic_details_content = (TextView) findViewById(R.id.tv_symptomatic_details_content);
        tv_suggested_treatment_department = (TextView) findViewById(R.id.tv_suggested_treatment_department);

        tv_symptom_cause_detailed = (TextView) findViewById(R.id.tv_symptom_cause_detailed);
        tv_symptomatic_details_content_detailed = (TextView) findViewById(R.id.tv_symptomatic_details_content_detailed);

        ll_symptom_association_disease = (LinearLayout) findViewById(R.id.ll_symptom_association_disease);
    }//end viewInit()

    /**
     * 初始化对象
     */
    private void objectInit() {
        symptomService = new SymptomService(myContent);
        diseaseSyService = new DiseaseSyService(myContent);
    }


    /**
     * 根据传入id初始化页面数据
     *
     * @param symptom_id
     */
    private void idDataInit(String symptom_id) {
        int id = Integer.parseInt(symptom_id);
        Symptom symptom = symptomService.getSymptomById(id);
        if (symptom != null) {
            showSymptom(symptom);
        } else {
            //结束此页面，自动返回上一级
            Toast.makeText(MedicalSymptomDetailedDataActivity.this, "该医疗数据为空，后期继续完善", Toast.LENGTH_LONG).show();
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
     * @param symptom_name
     */
    private void nameDataInit(String symptom_name) {
        Symptom symptom = symptomService.getSymptomByName(symptom_name);
        if (symptom != null) {
            showSymptom(symptom);
        } else {
            //结束此页面，自动返回上一级
            if (symptom_name.equals("不详")) {
                Toast.makeText(MedicalSymptomDetailedDataActivity.this, "都写了【不详】，还点，真是的。", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(MedicalSymptomDetailedDataActivity.this, "该医疗数据为空，后期继续完善", Toast.LENGTH_LONG).show();
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
     * @param symptom
     */
    private void showSymptom(Symptom symptom) {

        int symptom_id = symptom.getSymptom_id();
        String symptom_name = symptom.getSymptom_name();// 症状名称
        String symptom_intro = symptom.getSymptom_intro();// 症状简介
        String symptom_cause = symptom.getSymptom_cause();// 症状起因
        List<MatchAttribute> diseaseIdNameList = diseaseSyService.getDiBySyId(symptom_id);//易引起疾病
        String symptomatic_details_content = symptom.getSymptomatic_details_content();// 诊断详述内容
        String suggested_treatment_department = symptom.getSuggested_treatment_department(); // 建议就诊科室


        tv_symptom_name.setText(isNull(symptom_name) ? " " : symptom_name);

        tv_symptom_intro.setText(isNull(symptom_intro) ? "不详" : symptom_intro);

        if (isNull(symptom_cause)) {
            tv_symptom_cause.setText("不详");
        } else {
            tv_symptom_cause.setText("\n" + "       " + symptom_cause);
            if (symptom_cause.length() < 150) {//如果简介字符串长度小于150，那么将隐藏【详细】
                tv_symptom_cause_detailed.setVisibility(View.GONE);
            }
        }

        if (isNull(symptomatic_details_content)) {
            tv_symptomatic_details_content.setText("不详");
        } else {
            tv_symptomatic_details_content.setText("\n" + "       " + symptomatic_details_content);
            if (symptomatic_details_content.length() < 150) {//如果简介字符串长度小于150，那么将隐藏【详细】
                tv_symptomatic_details_content_detailed.setVisibility(View.GONE);
            }
        }

        tv_suggested_treatment_department.setText(isNull(suggested_treatment_department) ? "不详" : suggested_treatment_department);

        if (diseaseIdNameList != null && diseaseIdNameList.size() > 0) {
            String[] strings = new String[diseaseIdNameList.size()];
            int i = 0;
            for (MatchAttribute matchAttribute : diseaseIdNameList) {
                strings[i] = matchAttribute.getAttributeContent();
                i++;
            }
            generateView(strings);
        } else {
            generateView(null);
        }

        //起因详细点击事件
        tv_symptom_cause_detailed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExitDialog("起因详细介绍", symptom_cause);
            }
        });

        //诊断详细点击事件
        tv_symptomatic_details_content_detailed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExitDialog("诊断详细介绍", symptomatic_details_content);
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
     * @param diseaseName
     */
    private void newMedicalDiseaseDetailedDataActivity(String diseaseName) {
        Intent intent = new Intent(MedicalSymptomDetailedDataActivity.this, MedicalDiseaseDetailedDataActivity.class);
        intent.putExtra("disease_name", diseaseName);
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

    private void generateView(String[] strings) {
        LinearLayout linearLayout = ll_symptom_association_disease;

        if (strings != null && strings.length > 0) {
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
                        newMedicalDiseaseDetailedDataActivity(item1);
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
        }//end if()

    }//end generateView()

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
