package com.nyaxs.shypicture.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;

public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    //序列化
    //将对象转化为 String
    public static <T> String obj2String(T obj){

        if(obj == null){
            return null;
        }
        try {
            return obj instanceof String ? (String)obj :  objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            //log.warn("Parse Object to String error",e);
            return null;
        }
    }

    //将对象转为json，并格式化的输出
    public static <T> String obj2StringPretty(T obj){
        if(obj == null){
            return null;
        }
        try {
            return obj instanceof String ? (String)obj :  objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (Exception e) {
            //log.warn("Parse Object to String error",e);
            return null;
        }
    }

    //反序列化
    public static <T> T string2Obj(String str, Class<T> clazz){
        if(StringUtils.isEmpty(str) || clazz == null){
            return null;
        }

        try {
            return clazz.equals(String.class)? (T)str : objectMapper.readValue(str, clazz);
        } catch (Exception e) {
           // log.warn("Parse String to Object error",e);
            return null;
        }
    }


}
