package com.even.spider.monitor;

import com.even.model.Ip;
import com.even.service.SpiderService;
import com.even.util.ConnectUtil;
import com.even.util.JsoupUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class IPSpider {

    public static void main(String[] args) {
        start();
    }

    public static void start() {
        updateNetProxy(1);
    }

    public static void updateNetProxy(int pageNum) {
        List<String> ipList = new ArrayList<String>();
        List<Integer> portList = new ArrayList<Integer>();
        List<Boolean> usableList;
        Document doc;
        Ip ip = SpiderService.getUsableIpFirst();
        // 第一次爬取数据，或者数据库里面的数据都失效
        if (null == ip) {
            doc = JsoupUtil.getSource("http://www.xicidaili.com/nn/" + pageNum);
        } else {// 使用匿名IP爬取IP地址
            doc = JsoupUtil.getSource("http://www.xicidaili.com/nn/" + pageNum, ip.getIp(), ip.getPort());
        }
        Element table = doc.getElementById("ip_list");
        Elements odds = table.getElementsByClass("odd");
        //todo 这里class为空，怎么拿出来？
        Elements blank = table.getElementsByClass("");

        for (int i = 0; i < odds.size(); i++) {
            Element odd = odds.get(i);
            Elements td = odd.getElementsByTag("td");
            System.out.println("ip = " + td.get(1).text());
            System.out.println("port = " + td.get(2).text());

            ipList.add(i, td.get(1).text());
            portList.add(i, Integer.valueOf(td.get(2).text()));
        }

        //检测ip是否可用
        usableList = ConnectUtil.netTest(ipList, portList);
        //存入数据库
        SpiderService.addIpList(ipList, portList, usableList);
    }


}
