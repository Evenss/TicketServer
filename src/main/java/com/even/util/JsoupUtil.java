package com.even.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupUtil {

    private static final int TIME_OUT = 5000;

    //根据url获取网页源代码
    public static String getSourceStr(String url) {
        return getSource(url).toString();
    }

    //根据url获取网页文本
    public static String getTextStr(String url) {
        return getSource(url).text();
    }

    //使用代理服务器，通过url获取网页源代码
    public static String getSourceStr(String url, String ip, int port) {
        return getSource(url, ip, port).toString();
    }

    public static Document getSource(String url) {
        Document doc;
        for (int i = 0; i < 5; i++) {
            try {
                doc = Jsoup.connect(url)
                        .data("query", "Java")
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36")
                        .timeout(TIME_OUT)
                        .ignoreContentType(true)
                        .get();
                return doc;
            } catch (Exception e) {
//                e.printStackTrace();
                continue;
            }
        }
        return null;
    }

    public static Document getSource(String url, String ip, int port) {
        Document doc;
        for (int i = 0; i < 5; i++) {
            try {
                doc = Jsoup.connect(url)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36")
                        .proxy(ip, port)
                        .timeout(TIME_OUT)
                        .ignoreContentType(true)
                        .get();
                return doc;
            } catch (Exception e) {
                //e.printStackTrace();
                continue;
            }
        }
        return null;
    }

    //获取网址内所有链接
    public static void getAllLinks(String url) {
        for (int i = 0; i < 10; i++) {
            try {
                Document doc = Jsoup.connect(url)
                        .data("query", "Java")
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36")
                        .timeout(TIME_OUT)
                        .ignoreContentType(true).get();
                Elements eles = doc.getAllElements();
                for (Element ele : eles) {
                    String link = ele.attr("abs:href");
                    if (link != null && link.length() > 0) {
                        System.out.println(link);
                    }
                }
                return;
            } catch (Exception e) {
//                e.printStackTrace();
                continue;
            }
        }
    }
}
