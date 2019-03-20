package com.example.wangji.changemax.medical_system.analysis.function;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.wangji.changemax.R;
import com.example.wangji.changemax.activity.MainActivity;
import com.example.wangji.changemax.activity.admin.AdminActivity;
import com.example.wangji.changemax.activity.map.MapActivity;
import com.example.wangji.changemax.activity.mea.MedicalEncyclopediaActivity;
import com.example.wangji.changemax.model.internal.Disease;
import com.example.wangji.changemax.model.internal.Symptom;
import com.example.wangji.changemax.service.internal.DiseaseService;
import com.example.wangji.changemax.service.internal.SymptomService;
import com.example.wangji.changemax.util.file_util.FileUtil;
import com.example.wangji.changemax.util.http_util.TuLingHttpUtils;
import com.example.wangji.changemax.util.other_util.ReturnRandomWords;

import java.util.List;

/**
 * 功能性分析
 * 判断用户是否触发功能性语句，反馈功能问题
 * <p>
 * 调用第三方接口api，实现第三方服务器分析，第三方智能问答系统机器人，进行功能性反馈
 * <p>
 * Created by WangJi.
 */

public class FunctionAnalysis {
    private Context myContext;

    private DiseaseService diseaseService;
    private SymptomService symptomService;

    private FileUtil fileUtil;

    public FunctionAnalysis(Context myContext) {
        this.myContext = myContext;

        //        diseaseService = new DiseaseService(myContext);
        fileUtil = new FileUtil(myContext);
    }

    public String functionAnalysis(String userMessage) {
        String changeMaxFeedback = "";

        //其他视图跳转入口
        if (TextUtils.isEmpty(changeMaxFeedback)) {
            Log.v("FunctionAnalysis", "进入其他视图跳转入口");
            if (userMessage.contains("医院")) {
                return "点击就可以帮你定位周围的医院YY了哦。~";
            } else if (userMessage.contains("疾病百科") || userMessage.contains("医学百科") || userMessage.contains("医疗百科")) {
                return "点击就可以打开医疗百科YLBK了哦。~";
            } else if (userMessage.contains("厕所")) {
                return "点击就可以帮你定位周围的厕所WC了哦。~";
            } else if (userMessage.contains("客服")) {
                return "点击就可以帮你跳转至QQ客服KF窗口哦。~";
            } else if (userMessage.contains("admin")) {
                MainActivity.getMainActivity().jumpPage(new Intent(myContext, AdminActivity.class));
                return "正在开启管理员权限，请稍等。~";
            }


        }//end 其他视图跳转入口

        //关键词匹配入口
        if (TextUtils.isEmpty(changeMaxFeedback)) {
            Log.v("FunctionAnalysis", "进入关键词匹配入口");
            if (userMessage.contains("系统性诊疗") || userMessage.contains("生病了") || userMessage.contains("看病")) { //触发系统性诊疗
                return "系统性诊疗开启中";
            } else if (userMessage.contains("爸爸")) {  //爸爸模式
                return "哼。我爸爸只有一个，那就是我的缔造者：Mr.Wang~";
            } else if (userMessage.contains("图灵")) {//调用图灵api
                return "你知道changeMax之父是谁吗？这是一个秘密。";
            } else if (userMessage.contains("管理员")) {//反馈管理员触发提示
                return "请通过键盘模式输入管理员登入密码。";
            } else if (userMessage.contains("你会做什么")) {//反馈提示功能
                return new ReturnRandomWords().getRandomCan(-1);
            } else if (userMessage.contains("每日一句")) {
                return new ReturnRandomWords().getRandomWelcome(myContext);
            }
        }//end 关键词匹配入口




        /*
         * 此处分析，属于大数据分析，但是从系统架构角度来讲，此处大数据分析不应该存在
         * 因为，在该分析阶段，属于功能性分析，不应该对数据库进行操作。
         * 但是由于用户需求需进行医疗百科，所以必须在第三方人工智能api调用之前进行
         * 数据库疾病症状名称精确匹配，反馈其精确匹配结果的简介。
         * 那么在接下来的数据分析阶段，将进行模糊精确多维度分析。。。
         */
        //疾病症状数据库入口
        //        if (TextUtils.isEmpty(changeMaxFeedback)) {
        //            Log.v("FunctionAnalysis", "进入疾病症状数据库入口");
        //            //医疗数据匹配
        //            Disease disease = diseaseService.getDiseaseByName(userMessage);
        //            if (disease != null) {//对疾病库进行精确名称匹配
        //                return disease.getDisease_intro() + "~" + "---" + "如果需要进一步了解***"+userMessage+"***疾病信息，可以点击我哦。";
        //            } else {//疾病库中未匹配数据，将对症状库进行精确匹配
        //                Symptom symptom = symptomService.getSymptomByName(userMessage);
        //                if (symptom != null) {
        //                    return symptom.getSymptom_intro() + "~" + "---" + "如果需要进一步了解###"+userMessage+"###症状信息，可以点击我哦。";
        //                }
        //            }
        //        }//end 疾病症状数据库入口
        /*
        进一步优化代码，减少系统负担
         */
        if (TextUtils.isEmpty(changeMaxFeedback)) {
            Log.v("FunctionAnalysis", "进入疾病名称匹配入口");
            List<String> symptomNameList = fileUtil.readFromRaw(R.raw.symptom_name);

            String nameString = "";
            int nameLength = 0;//此处使用name长度功能，为更精确匹配名称
            for (String symptomString : symptomNameList) {
                if (userMessage.contains(symptomString)) {//用户消息包含该症状名称
                    if (userMessage.equals(symptomString)) {//用户消息和症状名称一样，说明用户就是需要查询该疾病
                        symptomService = new SymptomService(myContext);
                        String symptom_intro = symptomService.getDataIntroByName(symptomString);
                        Log.v("FunctionAnalysisSymptom", symptom_intro);
                        if (!TextUtils.isEmpty(symptom_intro)) {
                            changeMaxFeedback = "“###" + symptomString + "###”简介：\r\n" + streamlinedText(symptom_intro) + "【点击了解更多】";
                            return changeMaxFeedback + "~";
                        }
                    }
                    if (symptomString.length() > nameLength) {//如果碰到更精确的，则覆盖
                        nameString = symptomString;
                        nameLength = symptomString.length();
                    }
                }
            }
            if (!TextUtils.isEmpty(nameString)) {
                String symptomFeedback = "估计您可能需要了解###" + nameString + "###，点击可查看哦。";
                String tuLingFeedback = thirdPartyQASystem(userMessage);
                if (!TextUtils.isEmpty(tuLingFeedback)) {
                    changeMaxFeedback = tuLingFeedback + "---" + symptomFeedback;
                } else {
                    changeMaxFeedback = symptomFeedback;
                }
                return changeMaxFeedback + "~";
            } else {
                List<String> diseaseNameList = fileUtil.readFromRaw(R.raw.disease_name);
                for (String diseaseString : diseaseNameList) {
                    if (userMessage.contains(diseaseString)) {//用户消息包含该疾病名称
                        if (userMessage.equals(diseaseString)) {//用户消息和疾病名称一样，说明用户就是需要查询该疾病
                            diseaseService = new DiseaseService(myContext);
                            String disease_intro = diseaseService.getDataIntroByName(diseaseString);
                            Log.v("FunctionAnalysisDisease", disease_intro);
                            if (!TextUtils.isEmpty(disease_intro)) {
                                changeMaxFeedback = "“***" + diseaseString + "***”简介：\r\n" + streamlinedText(disease_intro) + "【点击了解更多】";
                                return changeMaxFeedback + "~";
                            }
                        }
                        if (diseaseString.length() > nameLength) {//如果碰到更精确的，则覆盖
                            nameString = diseaseString;
                            nameLength = diseaseString.length();
                        }
                    }
                }
                if (!TextUtils.isEmpty(nameString)) {
                    String diseaseFeedback = "估计您可能需要了解***" + nameString + "***，点击可查看哦。";
                    String tuLingFeedback = thirdPartyQASystem(userMessage);
                    if (!TextUtils.isEmpty(tuLingFeedback)) {
                        changeMaxFeedback = tuLingFeedback + "---" + diseaseFeedback;
                    } else {
                        changeMaxFeedback = diseaseFeedback;
                    }
                    return changeMaxFeedback + "~";
                } else {//症状和疾病名称都进行匹配了，但是无匹配结果
                    /*
                    判断第三方api反馈结果是否具有意义
                     */
                    return thirdPartyQASystem(userMessage);     //暂时默认都为有意义
                }//end else
            }//end else
        } else {//end   if (!TextUtils.isEmpty(changeMaxFeedback))
            /*
            前面分析过程中，已经分析出结果，但是此处毫无意义，在前面分析出结果已经通过return反馈
             */
            return changeMaxFeedback;
        }
    }//end functionAnalysis(String userMessage)


    private String thirdPartyQASystem(String userMessage) {

        //第三方人工问答入口
        String tuLingFeedback = "";//第三方人工问答api结果反馈
        Log.v("FunctionAnalysis", "进入第三方人工问答api入口");
        try {
            tuLingFeedback = TuLingHttpUtils.sendMsg(userMessage);
        } catch (RuntimeException e) {
            return "服务器连接错误！请检查网络！";
        }

        //如果图灵反馈结果和用户内容一样，说明进入复读模式，则跳过
        if (tuLingFeedback.equalsIgnoreCase(userMessage)) {
            return "";
        }

        if (tuLingFeedback.contains("图灵")) {
            tuLingFeedback = tuLingFeedback.replace("图灵", "changeMax");
        }
        Log.v("FunctionAnalysis", "第三方人工问答api结果：" + tuLingFeedback);
        tuLingFeedback = tuLingFeedback + "~";
        return tuLingFeedback;

    }


    private String streamlinedText(String text) {
        int maxLength = 100;
        if (!TextUtils.isEmpty(text)) {
            if (text.length() > maxLength) {
                return text.substring(0, maxLength - 3) + "......";
            } else {
                return text;
            }
        } else {
            return "";
        }
    }


}
