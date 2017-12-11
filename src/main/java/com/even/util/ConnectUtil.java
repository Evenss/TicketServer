package com.even.util;

import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.List;

/**
 * 爬虫网络设置工具类
 */

public class ConnectUtil {

    public static List<Boolean> netTest(List<String> ipList, List<Integer> portList) {
        long startTime = System.currentTimeMillis();   //获取开始时间
        int size = ipList.size();
        List<Boolean> usableList = new ArrayList<Boolean>();
        for (int i = 0; i < size; i++) {
            usableList.add(i, checkProxyIp(ipList.get(i), portList.get(i)));
            if (0 == i) PLog.i("第0个IP测试完成");
            if (i / 10 > 0 && i % 10 == 0) {//每10个输出一次
                PLog.i("第" + i + "个IP测试完成");
            }
        }
        long endTime = System.currentTimeMillis(); //获取结束时间
        PLog.i("共" + size + "个测试，花" + (endTime - startTime) / 1000 + "秒");
        return usableList;
    }

    private static boolean checkProxyIp(String proxyIp, int proxyPort) {
        for (int i = 0; i < 5; i++) {
            try {
                //http://1212.ip138.com/ic.asp 可以换成任何比较快的网页
                Jsoup.connect("http://1212.ip138.com/ic.asp")
                        .timeout(2 * 1000)
                        .proxy(proxyIp, proxyPort)
                        .get();
                return true;
            } catch (Exception e) {
                continue;
            }
        }
        return false;
    }
}
