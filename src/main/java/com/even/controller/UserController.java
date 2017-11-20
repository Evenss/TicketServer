package com.even.controller;

import com.alibaba.fastjson.JSONObject;
import com.even.bean.LoginBean;
import com.even.service.UserService;
import com.even.util.ReturnUtil;
import com.jfinal.core.Controller;
import com.jfinal.json.FastJson;
import com.jfinal.kit.HttpKit;
import com.jfinal.plugin.redis.Redis;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.UUID;

public class UserController extends Controller {

    public static final int SESSION_TIME_OUT = 600;// 超时10分钟

    public void login(){
        String jsonString = HttpKit.readData(getRequest());
        System.out.println("login jsonString = "+ jsonString);
        LoginBean bean = FastJson.getJson().parse(jsonString,LoginBean.class);
        System.out.println(bean.getPassword());

        JSONObject json = getAttr("data");
        System.out.println(json);
        if(json == null) throw new RuntimeException("用户名或密码异常");
//        String phone = bean.getPhone();
//        String password = bean.getPassword();
        String phone = "15957159502";
        String password = "e10adc3949ba59abbe56e057f20f883e";

        if(StringUtils.isBlank(phone) || StringUtils.isBlank(password))throw new RuntimeException("用户名或密码为空");
        try {
            Map<String,Object> map = UserService.login(phone,password);
            if(map.isEmpty()){
                renderJson(ReturnUtil.ERROR("用户不存在或者密码错误"));
            }else {
                //允许多账号同时在线
                String key = UUID.randomUUID().toString().replace("-","").toUpperCase();
                JSONObject token = new JSONObject();
                token.put("user_id",map.get("user_id"));
                token.put("login_time",System.currentTimeMillis());
//                Redis.use("ticket_assistant").setex(key, SESSION_TIME_OUT,token.toJSONString());
                map.put("token",key);
                map.put("invalid_time",SESSION_TIME_OUT);
                renderJson(ReturnUtil.DATA(map));
            }
        }catch (Exception e){
            e.printStackTrace();
            renderJson(ReturnUtil.ERROR("登录异常"));
        }
    }

    public void logout(){
        JSONObject json = getAttr("data");
        if(json == null) throw new RuntimeException("token异常");
        String token =json.getString("token");
        if(StringUtils.isBlank(token)) throw new RuntimeException("token异常");
        if(UserService.logout(token)){
            renderJson(ReturnUtil.DATA("登出成功。"));
        }else {
            renderJson(ReturnUtil.ERROR("操作失败。"));
        }
    }

    public void register(){

    }

    public void change_pwd(){

    }

    public void set_email(){

    }

}
