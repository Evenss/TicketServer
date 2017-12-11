package com.even.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class PLog {

    private static Map<String,Logger> loggerMap = new HashMap<String,Logger>();

    public static void d(Object message){
        String className = getClassName();
        Logger log = getLogger(className);
        if(log.isDebugEnabled()){
            log.debug(message);
        }
    }

    public static void d(String tag, Object message){
        String className = getClassName();
        Logger log = getLogger(className);
        if(log.isDebugEnabled()){
            log.debug(new StringBuffer().append("【").append(tag).append("】").append(message).toString());
        }
    }

    public static void i(Object message){
        String className = getClassName();
        Logger log = getLogger(className);
        if(log.isInfoEnabled()){
            log.info(message);
        }
    }

    public static void i(String tag, Object message){
        String className = getClassName();
        Logger log = getLogger(className);
        if(log.isInfoEnabled()){
            log.info(new StringBuffer().append("【").append(tag).append("】").append(message).toString());
        }
    }

    public static void w(Object message){
        String className = getClassName();
        Logger log = getLogger(className);
        log.warn(message);
    }

    public static void w(String tag, Object message){
        String className = getClassName();
        Logger log = getLogger(className);
        log.warn(new StringBuffer().append("【").append(tag).append("】").append(message).toString());
    }

    public static void e(Object message){
        String className = getClassName();
        Logger log = getLogger(className);
        log.error(message);
    }

    public static void e(String tag, Object message){
        String className = getClassName();
        Logger log = getLogger(className);
        log.error(new StringBuffer().append("【").append(tag).append("】").append(message).toString());
    }

    /**
     * 获取最开始的调用者所在类
     * @return
     */
    private static String getClassName(){
        Throwable th = new Throwable();
        StackTraceElement[] stes = th.getStackTrace();
        StackTraceElement ste = stes[2];
        return ste.getClassName();
    }
    /**
     * 根据类名获得logger对象
     * @param className
     * @return
     */
    private static Logger getLogger(String className){
        Logger log = null;
        if(loggerMap.containsKey(className)){
            log = loggerMap.get(className);
        }else{
            try {
                log = Logger.getLogger(Class.forName(className));
                loggerMap.put(className, log);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return log;
    }
}
