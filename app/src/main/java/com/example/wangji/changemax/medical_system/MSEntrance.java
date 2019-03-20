package com.example.wangji.changemax.medical_system;


/**
 * 对问题对象进行分析：
 * 至于为什么在结果分析模块中分析匹配出来的问题关键词集，因为，对于一个答案来说，问题中所问的内容方向将对答案的方向取决定性的作用，
 * 举一个栗子：
 * 用户发问：“我脸上的毛孔粗大，长了很多痘痘怎么办？”
 * <p>
 * 那么我将对此用进行该系统的模拟分析：
 * 1.对问题进行预处理：
 * （1）调用语言云进行分词处理：
 * 结果：我  脸上  的  毛孔  粗大  ，  长  了  很多  痘痘  怎么办  ？
 * （2）调用语言云进行词性标注处理：
 * 结果：我/r  脸上/nl  的/u  毛孔/n  粗大/a  ，/wp  长/v  了/u  很多/m  痘痘/n  怎么办/r  ？/wp
 * <p>
 * （3）获得词集：“我”“脸上”“的”“毛孔”“粗大”“，”“长”“了”“很多”“痘痘”“怎么办”“？”
 * “r” “nl”  “u”  “n”   “a” “wp” “v” “u” “m”   “n”    “r”   “wp”
 * 1）对于每个词，都对应着各自的词性，那么其中一些词会加重系统分析的负载，那么将对其进行分析性的去除。
 * 其中，对于r（代词，“你”，“我”，“他”等词的词性为代词，所以本系统选择性去除）
 * 2）对于每一句自然语言中，都会一串词性串，那么对于这些词性串，本系统将对其进行保留
 * 对于相同词性结构的自然语言所提出的问题的方向将会是类似的，将对后期结果分析提供帮助
 * 举例：1.小米公司是什么时候创立的？
 * 语言云分析结果：小米_n 公司_n 是_v 什么_r 时候_n 创立_v 的_u ?_wp
 * 2.智能科技是什么模式运营的?
 * 语言云分析结果：智能_n 科技_n 是_v 什么_r 模式_n 运营_v 的_u ?_wp
 * 所以：可以分析出两个类似问题词性结构的，那么可以对其进行匹配，
 * 根据问题库中内容，得出题库中答案的类别，分析出问题方向，对答案的精确度进一步提高
 */

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.wangji.changemax.medical_system.analysis.internet.InternetAnalysis;
import com.example.wangji.changemax.medical_system.pretreatment.DescriptionPretreatmentAnalysis;
import com.example.wangji.changemax.medical_system.analysis.function.FunctionAnalysis;
import com.example.wangji.changemax.medical_system.analysis.history_question_match.HistoryQuestionMatchAnalysis;
import com.example.wangji.changemax.medical_system.analysis.data.DataAnalysis;
import com.example.wangji.changemax.model.external.KeyWord;
import com.example.wangji.changemax.model.external.Question;
import com.example.wangji.changemax.util.other_util.ReturnRandomWords;

import java.util.List;
import java.util.Map;

/**
 * 医疗系统分析
 * Created by WangJi.
 */

public class MSEntrance {
    private DescriptionPretreatmentAnalysis dpAnalysis; //患者词预分析对象

    private FunctionAnalysis fa;//功能性分析对象
    private HistoryQuestionMatchAnalysis hqma;//历史问题分析对象
    private DataAnalysis da;//*************************数据分析对象
    private InternetAnalysis ia;//互联网分析对象

    private boolean mtSwitch = true;//触发系统性医疗开关状态
    private Context myContext;

    public boolean isMtSwitch() {
        return mtSwitch;
    }

    public MSEntrance(Context myContext) {
        this.myContext = myContext;
    }

    public String getAnswerStringBySystem(String lookAndAskUID) {
        if (lookAndAskUID != null && !lookAndAskUID.equals("")) {
            dpAnalysis = new DescriptionPretreatmentAnalysis(myContext);//实例化患者词预分析对象
            Map<String, List<KeyWord>> kwListMap = dpAnalysis.dpBySystemAnalysis(lookAndAskUID);//对患者描述，信息进行字预处理,得到预处理集

            if (kwListMap != null) {
                da = new DataAnalysis(myContext);//实例化数据分析对象
                Log.v("系统性诊疗分析", "MSEntranceing !!!!");
                String dataResult = da.dataAnalysis(kwListMap);//大数据分析
                if (!TextUtils.isEmpty(dataResult.trim())) {//存在数据，返回功能性结果
                    //获取诊断结果，存储系统性诊断案例
                    Log.v("系统性诊疗分析结果", dataResult);
                    return dataResult;
                }
            }

            return "抱歉，未能分析出你的医疗问题，changeMax无法给您提供建议！";
        } else {
            return "抱歉，未能成功创建诊疗信息对象！";
        }

    }

    public String getAnswerStringByCommon(String userMessage) {
        /**
         *      1. 问题进行预处理
         *      2.进行功能性分析
         *      3.进行历史问题匹配分析
         *      4.进行数据分析
         */
        if (TextUtils.isEmpty(userMessage)) {
            return "抱歉，你不说话，changeMax无法给您提供建议！";
        }

        //        hqma = new HistoryQuestionMatchAnalysis(myContext);//实例化历史问题匹配分析对象
        //        String oldQuestionResult = hqma.historyQuestionMatchAnalysis(userMessage);//历史问题匹配分析
        //        if (!TextUtils.isEmpty(oldQuestionResult.trim())) {//存在数据，返回功能性结果
        //            mtSwitch = false;//诊疗开关关闭
        //            return oldQuestionResult;
        //        }

        fa = new FunctionAnalysis(myContext);//实例化功能性分析对象
        String functionResult = fa.functionAnalysis(userMessage);//功能性分析
        if (!TextUtils.isEmpty(functionResult)) {//存在数据，返回功能性结果
            mtSwitch = false;//诊疗开关关闭
            return functionResult + "---" + new ReturnRandomWords().randomShare();
        }

        dpAnalysis = new DescriptionPretreatmentAnalysis(myContext);//实例化患者词预分析对象
        //1.问题进行预处理
        Log.v("常规性诊疗分析", "问题进行预处理!!!!");
        String uid = dpAnalysis.dpCommonAnalysis(userMessage);//进行序列化封装
        if (TextUtils.isEmpty(uid)) {
            return "未能成功生成问题对象。";
        } else if (uid.length() < 32) {
            return uid;
        }
        Log.v("常规性诊疗Analysis", uid);

        da = new DataAnalysis(myContext);//实例化数据分析对象
        String dataResult = da.dataAnalysis(uid);//大数据分析，反序列化分析
        if (!TextUtils.isEmpty(dataResult)) {//存在数据，返回功能性结果
            mtSwitch = true;//诊疗开关开启
            return dataResult;
        }

        ia = new InternetAnalysis(myContext);
        String internetResult = ia.internetAnalysis(userMessage);
        if (!TextUtils.isEmpty(internetResult)) {
            mtSwitch = false;//诊疗开关关闭
            return internetResult + "---" + new ReturnRandomWords().randomShare();
        }


        return "抱歉，未能分析出你的医疗问题，changeMax无法给您提供建议！";

    }


}
