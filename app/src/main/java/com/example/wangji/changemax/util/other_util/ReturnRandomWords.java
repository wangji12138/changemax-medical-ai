package com.example.wangji.changemax.util.other_util;

import android.content.Context;

import com.example.wangji.changemax.R;
import com.example.wangji.changemax.activity.MainActivity;
import com.example.wangji.changemax.util.file_util.FileUtil;
import com.example.wangji.changemax.util.file_util.MKFileUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangJi.
 */
public final class ReturnRandomWords {

    public String changeMaxStrike(int i) {
        List<String> list = new ArrayList<String>();
        list.add("，额....打住！说这么多话，我就不会累吗？真不晓得工程师怎么想的？");
        list.add("，打住！我不想说了！这么多，自己看！");
        list.add("，打住！你自己看吧，我喝口水先");
        list.add("、呼，昨晚讲了一晚上，好累啊！");
        list.add("、呼，我喝口水，你自己看。");

        if (i > 0 && i <= list.size()) {
            return list.get(i - 1);
        } else {
            int random = (int) (Math.random() * (list.size()));
            return list.get(random);
        }
    }

    /**
     * 分析过程语
     *
     * @return
     */
    public List<String> getAnalysisList() {
        List<String> list = new ArrayList<String>();
        list.add("获取用户话语........");
        list.add("分析用户话语...............");
        list.add("预处理...............");
        list.add("云分析............");
        list.add("词性标注分析...............");
        list.add("命名实体分析...............");
        list.add("句法分析分析...............");
        list.add("语义角色标注分析...............");
        list.add("语义依存（树）分析...............");
        list.add("语义依存（图）分析....................");
        list.add("云分析结果...............");
        list.add("医疗减负载分析.........................");
        list.add("医疗词汇匹配分析....................");
        list.add("功能性分析...............");
        list.add("历史问题匹配分析...............");
        list.add("医疗症状匹配分析....................");
        list.add("模糊分析用户...............");
        list.add("连续性双词模糊匹配分析...............");
        list.add("患处部位减负载集分析....................");
        list.add("取分值症状集合前300替换分析...............");
        list.add("精准疾病分析....................");
        list.add("模糊疾病分析...............");
        list.add("部位器官描述分析...............");
        list.add("集合前100替换分析....................");
        list.add("结果集分析............");
        return list;
    }

    /**
     * 分析过程语（English）
     *
     * @return
     */
    public List<String> getAnalysisListEnglish() {
        List<String> list = new ArrayList<String>();
        list.add("Get User Talk........");
        list.add("Analyze user utterance...............");
        list.add("Preprocessing...............");
        list.add("Cloud Analysis............");
        list.add("partial part annotation analysis...............");
        list.add("named entity analysis...............");
        list.add("syntax analysis...............");
        list.add("Semantic Role Annotation Analysis...............");
        list.add("Semantic Dependency (Tree) Analysis............");
        list.add("Semantic Dependency (Graph) Analysis...................");
        list.add("Cloud Analysis Results...............");
        list.add("Medical load reduction analysis.........................");
        list.add("Medical vocabulary matching analysis....................");
        list.add("Functional Analysis...............");
        list.add("Historical problem matching analysis...............");
        list.add("Medical Symptom Match Analysis....................");
        list.add("fuzzy analysis user...............");
        list.add("Continuous double word fuzzy matching analysis...............");
        list.add("Analysis of the affected area minus load set..............");
        list.add("Taking the score of the symptom set before the 300 replacement analysis ...............");
        list.add("Precise disease analysis....................");
        list.add("fuzzy disease analysis...............");
        list.add("partial organ description analysis...............");
        list.add("100 pre-set replacement analysis....................");
        list.add("Result Set Analysis.........");
        return list;
    }

    public List<String> getFirstStartWelcome() {
        List<String> welcomeList = new ArrayList<String>();
        welcomeList.add("你好，很高兴认识你：\r\n" +
                "初次见面，请多多关照\r\n" +
                "我先自我介绍，我中文名字叫：大易；\r\n" +
                "英文名字叫：changeMax；\r\n" +
                "我很多愁善感，我也无所不聊，很高兴能在接下来的日子伴你左右，我们一起成长、进步。~");
        welcomeList.add("如果你不了解我，可以试试问问我“你会做什么”。~");
        return welcomeList;
    }

    /**
     * 返回随机开场语
     *
     * @return
     */
    public String getRandomWelcome(Context myContext) {
        //        List<String> welcomeList = new ArrayList<String>();
        //        welcomeList.add("你可以问我,你会做什么？");
        //
        //        welcomeList.add("我是一个专业的医疗助手，你可别想找我闲聊！我是不会理你的。");
        //
        //        welcomeList.add("我有两种对话模式，一种是打字模式，一种是语音模式，连续双击顶部空白屏幕就可以切换了。");
        //
        //        welcomeList.add("如果需要了解一些疾病或者症状信息，可以对我说出来就可以了。");
        //        welcomeList.add("有些话我知道你说不出来，连续双击顶部空白屏幕就可以切换成打字模式了。");
        //        welcomeList.add("你了解过慢性疾病吗？");


        MKFileUtil mkFileUtil = new MKFileUtil(myContext);

        int rawFile = R.raw.medical_knowledge;

        int totalRow = mkFileUtil.readTotalFromRaw(rawFile);

        int randomRow = (int) (Math.random() * (totalRow));
        if (randomRow % 2 == 0) {
            randomRow += 1;
        }

        return mkFileUtil.readDesignationFromRaw(rawFile, randomRow) + "~";

    }


    public String getRandomCan(int i) {
        List<String> list = new ArrayList<String>();
        list.add("你说我会做什么！我平时都潜移默化的告诉你了，你还问我！");
        list.add("你说我会做什么！我平时都潜移默化的告诉你了，怎么那么笨！");
        list.add("我会做这些事：~\r\n" + "吃喝嫖赌，样样精通！");
        list.add("我会做这些事：~\r\n" +
                "1.吃\n" +
                "2.吃\n" +
                "3.吃\n" +
                "4.吃\n" +
                "5.吃\n" +
                ".....");

        list.add("我会做这些事：~\r\n" +
                "1.生活百科，没有我不知道的医学小常识\n" +
                "2.问答百科，我了解很多事物的底细，不管是人还是物\n" +
                "3.算术啊，很简单的东西\n" +
                "4.我可以教你学单词，苹果的英文是apple，我发音还是很标准\n" +
                "5.讲笑话，将故事，歇后语，绕口令，顺口溜，都会，叫我讲就是了\n" +
                "6.会点小游戏，成语接龙，脑筋急转弯。\n" +
                "7.新闻时事，娱乐，政治新闻我都了解。\n" +
                "8.一个地址，我就能告诉你天气，不管是明天，今天，后天\n" +
                "9.查菜名、快递、节假日、邮编都是一些小功能\n" +
                "10.关键我会系统性看病\n" +
                "10.我会好多医疗小常识，“每日一句”哦\n" +
                ".....");

        list.add("你可以这样对我说：~\r\n" +
                "1.“小麦是什么？”\n" +
                "2.“王际是谁？奥巴马是谁？”\n" +
                "3.“一加一等于几？”..............算术天才\n" +
                "4.“香蕉用英语怎么说？”\n" +
                "5.“讲个笑话呗”、“讲个故事呗”、“说个歇后语呗”、“说个绕口令呗”、“说个顺口溜呗”, 撒娇都告诉你\n" +
                "6.“来玩成语接龙把”、“脑筋急转弯”\n" +
                "7.“新闻”、“娱乐新闻”、“体育新闻”\n" +
                "8.“九江庐山今天天气怎么样？”\n" +
                "9.“满汉全席怎么做？”、“红烧茄子怎么做？”\n" +
                "10.“查快递”、“九江邮编多少？”\n" +
                "11.“我要看病”      \n" +
                "12.“每日一句”      \n" +
                ".....");

        if (i > 0 && i <= list.size()) {
            return list.get(i - 1) + "~";
        } else {
            int random = (int) (Math.random() * (list.size()));
            return list.get(random) + "~";
        }
    }

    /**
     * 返回提示语
     *
     * @param promptFrequency
     * @return
     */
    public String returnPromptString(int promptFrequency) {
        List<String> promptStringList = new ArrayList<String>();
        promptStringList.add("你就这样把我晾在一边，不理我，真的好吗？~");
        promptStringList.add("你真的不想理我吗？~");
        promptStringList.add("再给你一次机会考虑一下！？~");
        promptStringList.add("主人，你是不是有事忙去了？~");
        promptStringList.add("哼，你不理我！下次别来找我了！");
        return promptStringList.get(promptFrequency - 1);
    }


    public String randomShare() {
        List<String> list = new ArrayList<String>();

        list.add("如果觉得我还不错，可以推荐一下我，点击即可分享FX~");
        for (int i = 0; i < 60; i++) {
            list.add("");
        }

        return list.get((int) (Math.random() * (list.size())));

    }

}
