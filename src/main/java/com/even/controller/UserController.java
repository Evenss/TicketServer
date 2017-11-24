package com.even.controller;

import com.alibaba.fastjson.JSONObject;
import com.even.service.UserService;
import com.even.util.ReturnUtil;
import com.jfinal.core.Controller;
import com.jfinal.plugin.redis.Redis;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.UUID;

public class UserController extends Controller {
    //在这里设置失效周期和token

    private static final int SESSION_TIME_OUT = 600;// 超时10分钟

    public void login() {
        JSONObject json = getAttr("data");
        if (json == null) throw new RuntimeException("用户名或密码异常");
        String phone = json.getString("phone");
        String password = json.getString("password");

        if (StringUtils.isBlank(phone) || StringUtils.isBlank(password)) throw new RuntimeException("用户名或密码为空");
        try {
            Map<String, Object> map = UserService.login(phone, password);
            if (map.isEmpty()) {
                renderJson(ReturnUtil.ERROR("用户不存在或者密码错误"));
            } else {
                //允许多账号同时在线
                setToken(map);
                renderJson(ReturnUtil.DATA(map));
            }
        } catch (Exception e) {
            e.printStackTrace();
            renderJson(ReturnUtil.ERROR("登录异常"));
        }
    }

    public void logout() {
        JSONObject json = getAttr("data");
        if (json == null) throw new RuntimeException("token异常");
        String token = json.getString("token");
        if (StringUtils.isBlank(token)) throw new RuntimeException("token异常");
        if (UserService.logout(token)) {
            renderJson(ReturnUtil.DATA("登出成功。"));
        } else {
            renderJson(ReturnUtil.ERROR("操作失败。"));
        }
    }

    public void register() {
        JSONObject json = getAttr("data");
        if (json == null) throw new RuntimeException("注册失败，请稍后重试");
        String phone = json.getString("phone");
        String password = json.getString("password");
        try {
            Map<String, Object> map = UserService.register(phone, password);
            if (map.isEmpty()) {
                renderJson(ReturnUtil.ERROR("账号已被注册"));
            } else {
                setToken(map);
                renderJson(ReturnUtil.DATA(map));
            }
        } catch (Exception e) {
            e.printStackTrace();
            renderJson(ReturnUtil.ERROR("注册异常"));
        }
    }

    private void setToken(Map<String, Object> map){
        String key = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        JSONObject token = new JSONObject();
        token.put("user_id", map.get("user_id"));
        token.put("login_time", System.currentTimeMillis());
        Redis.use("ticket_assistant").setex(key, SESSION_TIME_OUT, token.toJSONString());
        map.put("token", key);
        map.put("invalid_time", SESSION_TIME_OUT);
    }

    public void change_pwd() {
        JSONObject json = getAttr("data");
        if (json == null) throw new RuntimeException("token异常");
        String token = json.getString("token");
        if (StringUtils.isBlank(token)) throw new RuntimeException("token异常");
        int userId = json.getInteger("user_id");
        String passwordOld = json.getString("password_old");
        String passwordNew = json.getString("password_new");
        try {
            if (UserService.changePassword(userId, passwordOld, passwordNew)) {
                renderJson(ReturnUtil.DATA("修改密码成功。"));
            } else {
                renderJson(ReturnUtil.ERROR("操作失败。"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            renderJson(ReturnUtil.ERROR("修改密码异常"));
        }
    }

    public void set_email() {
        JSONObject json = getAttr("data");
        if (json == null) throw new RuntimeException("token异常");
        String token = json.getString("token");
        if (StringUtils.isBlank(token)) throw new RuntimeException("token异常");
        int userId = json.getInteger("user_id");
        String email = json.getString("email");
        try {
            if (UserService.setEmail(userId, email)) {
                renderJson(ReturnUtil.DATA("设置邮箱成功。"));
            } else {
                renderJson(ReturnUtil.ERROR("操作失败。"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            renderJson(ReturnUtil.ERROR("设置邮箱异常"));
        }

    }

}
