package com.even.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReturnUtil {
    public final static Map<String, Object> ERROR = new HashMap<String, Object>();
    public final static Map<String, Object> PARAM_ERROR = new HashMap<String, Object>();
    public final static Map<String, Object> SUCCESS = new HashMap<String, Object>();

    static {
        ERROR.put("code", 1);
        ERROR.put("error", "操作失败。");

        PARAM_ERROR.put("code", 1);
        PARAM_ERROR.put("error", "参数错误。");

        SUCCESS.put("code", 0);
        SUCCESS.put("success", "操作成功。");
    }

    public static Map<String, Object> ERROR(String err)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", 1);
        map.put("error", err);
        return map;
    }

    public static Map<String, Object> DATA(Object o)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", 0);
        if(o == null){
            map.put("data",new ArrayList<Object>());
            return map;
        }
        map.put("data", o);
        return map;
    }
}
