package me.dack.wenda.utils;

import com.alibaba.fastjson.JSONObject;

public class FastJsonUtil {
    public static String bean2Json(Object obj) {
        return JSONObject.toJSONString(obj);
    }

    public static <T> T json2Bean(String jsonStr, Class<T> objClass) {
        return JSONObject.parseObject(jsonStr, objClass);
    }
}