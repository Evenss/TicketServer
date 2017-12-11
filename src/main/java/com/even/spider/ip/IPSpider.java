package com.even.spider.ip;

import com.even.model.Ip;
import com.even.service.SpiderService;
import com.even.util.ConnectUtil;
import com.even.util.JsoupUtil;
import com.even.util.PLog;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;

/**
 * 代理IP的爬虫
 */

public class IPSpider {

    public static void start() {
        PLog.i("IPSpider start");
        // todo 暂时屏蔽一开始就更新ip表
//        new Thread(new Runnable() {
//            public void run() {
//                updateIpList();
//                System.out.println("IP table update done");
//                for (int i = 1; i <= 5; i++) {
//                    getNetProxy(i);
//                }
//            }
//        }).start();
        // 设置定时任务
        SpiderTimerTask.setTimeTask();
    }

    public static void getNetProxy(int pageNum) {
        List<String> ipList = new ArrayList<String>();
        List<Integer> portList = new ArrayList<Integer>();
        List<Boolean> usableList = new ArrayList<Boolean>();

        getNetProxy(pageNum, ipList, portList, usableList);
    }

    public static void getNetProxy(int pageNum, List<String> ipList, List<Integer> portList, List<Boolean> usableList) {
        Document doc;
        Ip ip = SpiderService.getUsableIpFirst();
        // 第一次爬取数据，或者数据库里面的数据都失效
        if ( true) {//todo 得到第一个可用ip，这里不能用
            PLog.i("use my ip");
            doc = JsoupUtil.getSource("http://www.xicidaili.com/nn/" + pageNum);
        } else {// 使用匿名IP爬取IP地址
            PLog.i("use proxy ip");
            PLog.i("ip：" + ip.getIp() + " port：" + ip.getPort());
            doc = JsoupUtil.getSource("http://www.xicidaili.com/nn/" + pageNum, ip.getIp(), ip.getPort());
        }

        try {
            Element table = doc.getElementById("ip_list");
            Elements odds = table.getElementsByClass("odd");
            //todo 这里class为空，怎么拿出来？
            Elements blank = table.getElementsByClass(" ");

            for (int i = 0; i < odds.size(); i++) {
                Element odd = odds.get(i);
                Elements td = odd.getElementsByTag("td");

                ipList.add(i, td.get(1).text());
                portList.add(i, Integer.valueOf(td.get(2).text()));
            }

            usableList = setUsableList(ipList, portList);
            addToDb(ipList, portList, usableList);
        } catch (Exception e) {
            PLog.e("Error：网页解析出错");
        }

    }

    // 更新数据库已有的Ip表状态(前100)
    public static void updateIpList() {
        PLog.i("update ip list (count = 100)");
        //前100个中没有一个可用的
        if (null == SpiderService.getUsableIpBeforeNum(100)) {
            PLog.i("no ip to use before 100");
            if(SpiderService.clearAll()){
                PLog.i("已清空所有数据");
            }else{
                PLog.i("数据未全部清空");
            }
            return;
        }

        List<Ip> list = SpiderService.getIPByPage(1, 100);
        List<Integer> idList = new ArrayList<Integer>();
        List<String> ipList = new ArrayList<String>();
        List<Integer> portList = new ArrayList<Integer>();
        for (Ip ip : list) {
            idList.add(ip.getId());
            ipList.add(ip.getIp());
            portList.add(ip.getPort());
        }
        List<Boolean> usableList = setUsableList(ipList, portList);

        SpiderService.updateIPListState(idList, usableList);

        //前100个中没有一个可用的
        if (null == SpiderService.getUsableIpBeforeNum(100)) {
            PLog.i("no ip to use before 100");
            if(SpiderService.clearAll()){
                PLog.i("已清空所有数据");
            }else{
                PLog.i("数据未全部清空");
            }
        }
    }

    //检测ip是否可用
    public static List<Boolean> setUsableList(List<String> ipList, List<Integer> portList) {
        return ConnectUtil.netTest(ipList, portList);
    }

    //存入数据库
    public static void addToDb(List<String> ipList, List<Integer> portList, List<Boolean> usableList) {
        SpiderService.addIpList(ipList, portList, usableList);
    }

}
