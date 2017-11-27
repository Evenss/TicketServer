package com.even.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.even.bean.monitor.TicketMonitor;
import com.even.bean.query.MobileTicket;
import com.even.bean.query.MobileTicketInfo;
import com.even.bean.query.TicketData;
import com.even.service.TicketService;
import com.even.spider.query.NetCallBack;
import com.even.util.ReturnUtil;
import com.even.util.TimeUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jfinal.core.Controller;
import com.jfinal.json.FastJson;
import com.jfinal.json.JFinalJson;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicketController extends Controller {

    // 票查询
    public void query() {
        JSONObject json = getAttr("data");
        if (json == null) throw new RuntimeException("数据异常");
        String departure = json.getString("departure");
        String destination = json.getString("destination");
        long date = json.getLong("date");
        boolean isGD = json.getBoolean("isGD");
        int pageSize = json.getInteger("page_size");
        int pageNum = json.getInteger("page_number");

        TicketData.TicketInfo ticket = TicketService.query(departure, destination, date, isGD, pageSize, pageNum);
        MobileTicket mobileTicket = new MobileTicket();
        if (null != ticket) {
            if (ticket.ticketLists.size() == 0) {
                mobileTicket.data.lastPage = true;
            } else {
                mobileTicket.data.lastPage = false;
            }
            mobileTicket.data.dptStation = ticket.dptStation;
            mobileTicket.data.arrStation = ticket.arrStation;
            List<MobileTicketInfo> mobileTicketList = new ArrayList<MobileTicketInfo>();
            for (TicketData.TicketList ticketBean : ticket.ticketLists) {
                MobileTicketInfo info = new MobileTicketInfo();
                info.dptStationName = ticketBean.dptStationName;
                info.arrStationName = ticketBean.arrStationName;
                info.trainNo = ticketBean.trainNo;
                info.startDate = TimeUtil.dateToStamp(ticketBean.startDate, "yyyyMMdd");
                info.dptTime = ticketBean.dptTime;
                info.arrTime = ticketBean.arrTime;
                info.desc = ticketBean.desc.desc;
                info.interval = ticketBean.extraTicketInfo.interval;
                info.seats = ticketBean.seats;

                mobileTicketList.add(info);
            }
            mobileTicket.data.list = mobileTicketList;
            String returnData = JSON.toJSONString(mobileTicket);
            renderJson(returnData);
        } else {
            renderJson(ReturnUtil.ERROR("数据请求失败，请稍后重试"));
        }
    }

    // 设置票余量监控
    public void set_monitor() {
        JSONObject json = getAttr("data");
        if (json == null) throw new RuntimeException("数据异常");
        String token = json.getString("token");
        if (StringUtils.isBlank(token)) throw new RuntimeException("token异常");
        int userId = json.getInteger("user_id");
        String dptStation = json.getString("dptStation");
        String arrStation = json.getString("arrStation");
        long startDate = json.getLong("startDate");
        List<String> trainNo = json.getJSONArray("trainNo").toJavaList(String.class);
        List<String> seats = json.getJSONArray("seats").toJavaList(String.class);

        try {
            if (TicketService.setMonitor(userId,dptStation,arrStation,startDate,trainNo,seats)) {
                renderJson(ReturnUtil.DATA("设置成功。"));
            } else {
                renderJson(ReturnUtil.ERROR("操作失败。"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            renderJson(ReturnUtil.ERROR("设置票余量监控异常"));
        }
    }

    // 用户标记车次查询
    public void my_order() {
        JSONObject json = getAttr("data");
        if (json == null) throw new RuntimeException("数据异常");
        String token = json.getString("token");
        if (StringUtils.isBlank(token)) throw new RuntimeException("token异常");
        int userId = json.getInteger("user_id");
        int pageSize = json.getInteger("page_size");
        int pageNum = json.getInteger("page_number");

        try {
            TicketMonitor ticketMonitor = TicketService.queryMyOrder(userId,pageSize,pageNum);
            if(ticketMonitor != null){
                String returnData = JSON.toJSONString(ticketMonitor);
                renderJson(returnData);
            }else{
                renderJson(ReturnUtil.ERROR("操作失败。"));
            }
        }catch (Exception e) {
            e.printStackTrace();
            renderJson(ReturnUtil.ERROR("查询异常"));
        }
    }
}
