package com.even.controller;

import com.alibaba.fastjson.JSONObject;
import com.even.service.TicketService;
import com.even.spider.query.NetCallBack;
import com.jfinal.core.Controller;

import java.util.Map;

public class TicketController extends Controller{
    private Map<String, Object> map;
    // 票查询
    public void query(){
        map.clear();

        JSONObject json = getAttr("data");
        if (json == null) throw new RuntimeException("数据异常");
        String departure = json.getString("departure");
        String destination = json.getString("destination");
        long date = json.getLong("date");
        boolean isGD = json.getBoolean("isGD");
        int pageSize = json.getInteger("pageSize");
        int pageNum = json.getInteger("pageNum");

        TicketService.query(departure, destination, date, isGD, pageSize, pageNum, new NetCallBack() {
            public void onNetworkError() {

            }

            public void onFailed(String error) {

            }

            public void onSuccess(Object object) {

            }
        });
    }

    // 设置票余量监控
    public void set_monitor(){

    }

    // 用户标记车次查询
    public void my_order(){

    }
}
