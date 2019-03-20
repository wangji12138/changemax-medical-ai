package com.example.wangji.changemax.util.speech_util.stt;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangJi.
 */
/*
 * 封装的GSON解析工具类，提供泛型参数方法
 *
 */
public class GsonUtil {
    // 将单条Json数据解析成相应的映射对象
    public static <T> T parseJsonWithGson(String jsonData, Class<T> type) {
        Gson gson = new Gson();
        T result = gson.fromJson(jsonData, type);
        return result;
    }

    // 将Json数组解析成相应的映射对象列表
    public static <T> List<T> parseJsonArrayWithGson(String jsonData,
                                                     Class<T> type) {
        Gson gson = new Gson();
        List<T> result = new ArrayList<T>();

        // 下面这句因为泛型在编译期类型会被擦除，从而导致如下错误：
        // java.lang.ClassCastException: com.google.gson.internal.LinkedTreeMap
        // cannot be cast to DictationResult
        // List<T> result = gson.fromJson(jsonData, new TypeToken<List<T>>() {
        // }.getType());

        // 正确写法
        JsonArray array = new JsonParser().parse(jsonData).getAsJsonArray();
        for (final JsonElement elem : array) {
            result.add(new Gson().fromJson(elem, type));
        }

        return result;
    }
}