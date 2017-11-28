package com.even.service;

import com.even.model.Ip;

import java.util.List;
import java.util.Map;

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
            new Ip().setIp(ipList.get(i)).setPort(portList.get(i)).setUsable(usableList.get(i)).save();
        }
    }

    // 得到第一个可用ip
    public static Ip getUsableIpFirst(){
        return Ip.dao.findFirst("SELECT * FROM IP WHERE ip.usable = ? " , true);
    }

    // 得到所有可用ip的列表
    public static List<Ip> getUsableIpList(){
        return Ip.dao.find("SELECT * FROM IP WHERE ip.usable = ? " , true);
    }
}
