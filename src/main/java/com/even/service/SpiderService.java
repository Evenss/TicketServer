package com.even.service;

import com.even.model.Ip;
import com.even.util.PLog;

import java.util.List;

public class SpiderService {

    public static List<Ip> getAll() {
        return Ip.dao.find("SELECT * FROM ip");
    }

    public static List<Ip> getIPByPage(int pageNum, int pageSize) {
        return Ip.dao.paginate(pageNum, pageSize, "SELECT *", "FROM ip").getList();
    }

    public static void addIpList(List<String> ipList, List<Integer> portList, List<Boolean> usableList) {
        int size = ipList.size();
        for (int i = 0; i < size; i++) {
            try {
                new Ip().setIp(ipList.get(i)).setPort(portList.get(i)).setUsable(usableList.get(i)).save();
            } catch (Exception e) {
                PLog.i("第" + i + "个代理测试-->" + "IP:" + ipList.get(i) + "Port:" + portList.get(i) + " 已存在");
                continue;
            }
        }
    }

    // 得到第一个可用ip
    public static Ip getUsableIpFirst() {
        return Ip.dao.findFirst("SELECT * FROM ip WHERE ip.usable = ? ", 1);
    }

    // 得到前N中第一个可用ip
    public static Ip getUsableIpBeforeNum(int num) {
        return Ip.dao.findFirst("SELECT * FROM ip WHERE ip.usable = ? AND id < ?", 1, num);
    }

    // 得到所有可用ip的列表
    public static List<Ip> getUsableIpList() {
        return Ip.dao.find("SELECT * FROM ip WHERE ip.usable = ? ", 1);
    }

    // 更新IP状态
    public static void updateIPListState(List<Integer> idList, List<Boolean> usableList) {
        for (int i = 0; i < idList.size(); i++) {
            int id = idList.get(i);
            boolean usable = usableList.get(i);
            Ip.dao.findById(id).setUsable(usable).update();
        }
    }

    // 清空所有数据
    public static boolean clearAll() {
        PLog.i("ip table clear all ");
        List<Ip> ipList = Ip.dao.find("SELECT * FROM ip");
        boolean isClearAll = true;
        for(Ip ip: ipList){
            if(!new Ip().deleteById(ip.getId())){
                isClearAll = false;
            }
        }
        return isClearAll;
    }
}
