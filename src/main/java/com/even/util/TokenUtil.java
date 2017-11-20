package com.even.util;

import com.jfinal.plugin.redis.Redis;
import org.apache.commons.lang3.StringUtils;

public class TokenUtil {
    public static boolean checkToken(String token){
        if(StringUtils.isBlank(token)) return false;
        return Redis.use("ticket_assistant").exists(token);
    }
}
