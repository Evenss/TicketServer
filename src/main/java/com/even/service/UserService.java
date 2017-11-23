package com.even.service;

import com.even.model.User;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.redis.Redis;

import java.util.HashMap;
import java.util.Map;

public class UserService {
    public static Map<String, Object> register(String phone, String password) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        Record record = Db.findFirst("SELECT * FROM user WHERE phone = ?", phone);
        if (record != null) {
            return map;
        }

        if (new User().set("phone", phone).set("password", password).save()) {
            record = Db.findFirst("SELECT * FROM user WHERE phone = ?", phone);
            map.put("user_id", record.getInt("id"));
            map.put("phone", record.getStr("phone"));
        }
        return map;
    }

    public static Map<String, Object> login(String phone, String password) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        Record record = Db.findFirst("SELECT * FROM user WHERE phone = ?", phone);
        if (record == null || record.getColumnNames().length == 0) {
            return map;
        }
        if (password.equals(record.getStr("password"))) {
            map.put("user_id", record.getInt("id"));
            map.put("phone", record.getStr("phone"));
            map.put("email", record.getStr("email"));
        }
        return map;
    }

    public static boolean logout(String token) {
        return Redis.use("ticket_assistant").del(token) != 0;
    }

    public static boolean changePassword(int user_id, String oldPwd, String newPwd) throws Exception {
        User user = User.dao.findFirst("SELECT * FROM user WHERE id = ?", user_id);
        if (null == user) {
            return false;
        }
        if (oldPwd.equals(user.getPassword())) {
            return user.set("password", newPwd).update();
        }
        return false;
    }

    public static boolean setEmail(int user_id, String email) {
        User user = User.dao.findFirst("SELECT * FROM user WHERE id = ?", user_id);
        if (null == user) {
            return false;
        }
        return user.set("email", email).update();
    }
}
