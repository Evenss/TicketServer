package com.even.spider.monitor;

import com.even.bean.query.TicketData;
import com.even.model.Ip;
import com.even.model.UserMonitorTicket;
import com.even.service.SpiderService;
import com.even.service.TicketService;
import com.even.spider.Observable.TicketObserver;
import com.even.spider.Observable.TicketSubject;
import com.even.spider.query.NetworkConnector;
import com.even.util.StringUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckTicket {
    private List<UserMonitorTicket> ticketList = new ArrayList<UserMonitorTicket>();
    private List<Ip> ipList = new ArrayList<Ip>();

    private static final long PERIOD_DAY = 24 * 60 * 60 * 1000;//订单最多爬取时间
    private static final int ACCESS_ERROR = -4;                 //访问异常
    private static final int NET_WORK_ERROR = -3;               //网络错误
    private static final int ORDER_OUT_DATE_ERROR = -2;         //订单超时
    private static final int IP_ERROR = -1;                     //IP被封
    private static final int SUCCESS = 0;                       //成功

    // 开启线程查询
    public void startThread() {
        ticketList = TicketService.queryMonitorOrder();
        ipList = SpiderService.getUsableIpList();
        // todo 线程管理

    }

    public void startTask(int userId, String trainNum, String seats, String url) {
        // 观察者注册
        TicketSubject subject = new TicketSubject();
        subject.addObserver(new TicketObserver());

        long startTime = System.currentTimeMillis();
        String ip = ipList.get(0).getIp();
        int port = ipList.get(0).getPort();
        accessNet(startTime, userId, trainNum, seats, url, subject, ip, port);
    }

    // 连接网络请求数据
    private int accessNet(long startTime, int userId, String trainNum, String seats, String url, TicketSubject subject) {
        return accessNet(startTime, userId, trainNum, seats, url, subject, "", -1);
    }

    private int accessNet(long startTime, int userId, String trainNum, String seats, String url, TicketSubject subject, String ip, int port) {
        long nowTime = System.currentTimeMillis();
        if ((nowTime - startTime) > PERIOD_DAY) {
            System.out.println("Error：查询订单超过1天！");
            return ORDER_OUT_DATE_ERROR;
        }

        TicketData.TicketInfo ticketInfo;
        if (StringUtils.isBlank(ip) && port == -1) {
            ticketInfo = NetworkConnector.getInstance().query(url);
        } else {
            ticketInfo = NetworkConnector.getInstance().query(url, ip, port);
        }

        if (null == ticketInfo) {
            System.out.println("Error：查询订单失败！");
            accessNet(startTime, userId, trainNum, seats, url, subject, ip, port);
            return NET_WORK_ERROR;
        }

        // 代理IP被封
        if (null == ticketInfo.ticketLists) {
            System.out.println("Error：查询订单失败！");
            Ip proxyIp = updateIpList();
            if(null != proxyIp){
                accessNet(startTime, userId, trainNum, seats, url, subject, proxyIp.getIp(), proxyIp.getPort());//重新分配代理IP
            }else{
                accessNet(startTime, userId, trainNum, seats, url, subject);//使用本机IP
            }
            return IP_ERROR;
        }
        if (getTicketCount(ticketInfo.ticketLists, trainNum, seats) > 0) {
            Map<String, Double> map = new HashMap<String, Double>();
            map.put("userId", (double) userId);
            map.put("ticketCount", (double) getTicketCount(ticketInfo.ticketLists, trainNum, seats));
            map.put("price", getPrice(ticketInfo.ticketLists, trainNum, seats));
            subject.notifyObserver(map);
            return SUCCESS;
        }
        return ACCESS_ERROR;
    }

    // 更新代理IP表
    private Ip updateIpList() {
        if(ipList.size() == 1){
            ipList.remove(0);
            return ipList.get(0);
        }else{
            return null;
        }
    }

    // 取出票余量
    private int getTicketCount(List<TicketData.TicketList> ticketLists, String trainNum, String seats) {
        List<String> seatList = StringUtil.listUtil(seats);
        int size = seatList.size();
        for (TicketData.TicketList ticket : ticketLists) {
            if (trainNum.contains(ticket.trainNo)) {
                for (int i = 0; i < size; i++) {
                    int count = matchSeat(ticket.seats, seatList.get(i)).count;
                    if (count > 0) {
                        return count;
                    }
                }
            }
        }
        return 0;
    }

    // 取出价格
    private double getPrice(List<TicketData.TicketList> ticketLists, String trainNum, String seats) {
        List<String> seatList = StringUtil.listUtil(seats);
        int size = seatList.size();
        for (TicketData.TicketList ticket : ticketLists) {
            if (trainNum.contains(ticket.trainNo)) {
                for (int i = 0; i < size; i++) {
                    double price = matchSeat(ticket.seats, seatList.get(i)).price;
                    return price;
                }
            }
        }
        return 0;
    }

    // 找出匹配的座位类型
    private TicketData.Seat matchSeat(TicketData.Seats seats, String name) {
        if ("zero".equals(name)) {
            return seats.zero;
        } else if ("one".equals(name)) {
            return seats.one;
        } else if ("two".equals(name)) {
            return seats.two;
        } else if ("business".equals(name)) {
            return seats.business;
        } else if ("soft".equals(name)) {
            return seats.soft;
        } else if ("hard_sleep".equals(name)) {
            return seats.hard_sleep;
        } else {
            return seats.hard_seat;
        }
    }

}
