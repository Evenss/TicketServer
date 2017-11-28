package com.even.util;

import okhttp3.internal.http.HttpMethod;
import sun.net.www.http.HttpClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConnectUtil {

    public static List<Boolean> netTest(List<String> ipList, List<Integer> portList) {
        int size = ipList.size();
        List<Boolean> usableList = new ArrayList<Boolean>();
        for (int i = 0; i < size; i++) {
            usableList.set(i, checkProxyIp(ipList.get(i), portList.get(i)));
        }
        return usableList;
    }

    //todo
    private static boolean checkProxyIp(String proxyIp, int proxyPort) {
        return false;
//        for (String proxyHost : proxyIpMap.keySet()) {
//            Integer proxyPort = proxyIpMap.get(proxyHost);
//
//            int statusCode = 0;
//            try {
//                HttpClient httpClient = new HttpClient();
//                httpClient.getHostConfiguration().setProxy(proxyHost, proxyPort);
//
//                // 连接超时时间（默认10秒 10000ms） 单位毫秒（ms）
//                int connectionTimeout = 10000;
//                // 读取数据超时时间（默认30秒 30000ms） 单位毫秒（ms）
//                int soTimeout = 30000;
//                httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeout);
//                httpClient.getHttpConnectionManager().getParams().setSoTimeout(soTimeout);
//
//                HttpMethod method = new GetMethod(reqUrl);
//
//                statusCode = httpClient.executeMethod(method);
//            } catch (Exception e) {
//                System.out.println("ip " + proxyHost + " is not aviable");
//            }
//            if (statusCode > 0) {
//                System.out.format("%s:%s-->%sn", proxyHost, proxyPort, statusCode);
//            }
//        }
    }
}
