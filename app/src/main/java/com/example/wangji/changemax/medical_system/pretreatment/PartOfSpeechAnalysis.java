package com.example.wangji.changemax.medical_system.pretreatment;

/**
 * Created by WangJi.
 */
public class PartOfSpeechAnalysis {
    // List<String> partOfSpeechList;

    public PartOfSpeechAnalysis() {
        /*
        partOfSpeechList = new ArrayList<String>();
        partOfSpeechList.add("a--形容词--取英语形容词adjective的第1个字母。");
        partOfSpeechList.add("ad--副形词--直接作状语的形容词。形容词代码a和副词代码d并在一起。");
        partOfSpeechList.add("an--名形词--具有名词功能的形容词。形容词代码a和名词代码n并在一起。");
        partOfSpeechList.add("b--区别词--取汉字“别”的声母。");
        partOfSpeechList.add("c--连词--取英语连词conjunction的第1个字母。");
        partOfSpeechList.add("dg--副语素--副词性语素。副词代码为d，语素代码ｇ前面置以D。");
        partOfSpeechList.add("d--副词--取adverb的第2个字母，因其第1个字母已用于形容词。");
        partOfSpeechList.add("e--叹词--取英语叹词exclamation的第1个字母。");
        partOfSpeechList.add("f--方位词--取汉字“方”。");
        partOfSpeechList.add("g--语素--绝大多数语素都能作为合成词的“词根”，取汉字“根”的声母。");
        partOfSpeechList.add("h--前接成分--取英语head的第1个字母。");
        partOfSpeechList.add("i--成语--取英语成语idiom的第1个字母。");
        partOfSpeechList.add("j--简称略语--取汉字“简”的声母。");
        partOfSpeechList.add("k--后接成分--");
        partOfSpeechList.add("l--习用语--习用语尚未成为成语，有点“临时性”，取“临”的声母。");
        partOfSpeechList.add("m--数词--取英语numeral的第3个字母，n，u已有他用。");
        partOfSpeechList.add("Ng--名语素--名词性语素。名词代码为n，语素代码ｇ前面置以N。");
        partOfSpeechList.add("n--名词--取英语名词noun的第1个字母。");
        partOfSpeechList.add("nr--人名--名词代码n和“人(ren)”的声母并在一起。");
        partOfSpeechList.add("ns--地名--名词代码n和处所词代码s并在一起。");
        partOfSpeechList.add("nt--机构团体--“团”的声母为t，名词代码n和t并在一起。");
        partOfSpeechList.add("nz--其他专名--“专”的声母的第1个字母为z，名词代码n和z并在一起。");
        partOfSpeechList.add("o--拟声词--取英语拟声词onomatopoeia的第1个字母。");
        partOfSpeechList.add("p--介词--取英语介词prepositional的第1个字母。");
        partOfSpeechList.add("q--量词--取英语quantity的第1个字母。");
        partOfSpeechList.add("r--代词--取英语代词pronoun的第2个字母因p已用于介词。");
        partOfSpeechList.add("s--处所词--取英语space的第1个字母。");
        partOfSpeechList.add("tg--时语素--时间词性语素。时间词代码为t在语素的代码g前面置以T。");
        partOfSpeechList.add("t--时间词--取英语time的第1个字母。");
        partOfSpeechList.add("u--助词--取英语助词auxiliary。");
        partOfSpeechList.add("vg--动语素--动词性语素。动词代码为v。在语素的代码g前面置以V。");
        partOfSpeechList.add("v--动词--取英语动词verb的第一个字母。");
        partOfSpeechList.add("vd--副动词--直接作状语的动词。动词和副词的代码并在一起。");
        partOfSpeechList.add("vn--名动词--指具有名词功能的动词。动词和名词的代码并在一起。");
        partOfSpeechList.add("w--标点符号--");
        partOfSpeechList.add("x--非语素字--非语素字只是一个符号，字母x通常用于代表未知数、符号。");
        partOfSpeechList.add("y--语气词--取汉字“语”的声母。");
        partOfSpeechList.add("z--状态词--取汉字“状”的声母的前一个字母。");
        partOfSpeechList.add("un--未知词--不可识别词及用户自定义词组。取英文Unkonwn首两个字母。(非北大标准，CSW分词中定义)");
        partOfSpeechList.add("t--时间词--取英语time的第1个字母。");
        */
    }

    public boolean partOfSpeechCallAnalysis(String kwps) {
        if (kwps.equals("nr") || kwps.equals("nh")) {
            return true;
        }
        return false;
    }

    public boolean partOfSpeechGenderAgeAnalysis(String kwps) {
        if (kwps.equals("v") || kwps.equals("n") || kwps.equals("q") || kwps.equals("m") || kwps.equals("wp")) {
            return true;
        }
        return false;
    }


    public boolean partOfSpeechPartOrganAnalysis(String kwps) {
        if (kwps.equals("a") || kwps.equals("r") || kwps.equals("v") || kwps.equals("nd") || kwps.equals("i") || kwps.equals("n")) {
            return true;
        }
        return false;
    }





    public boolean partOfSpeechMedicineAnalysis(String kwps) {
        if (kwps.equals("r") || kwps.equals("u") || kwps.equals("wp") || kwps.equals("v") || kwps.equals("e") || kwps.equals("n") ||
                kwps.equals("nr") || kwps.equals("y")) {
            return false;
        }
        return true;
    }


}


