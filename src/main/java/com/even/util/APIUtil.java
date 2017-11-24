package com.even.util;

public class APIUtil {
    private static final String URL_START = "http://train.qunar.com/dict/open/s2s.do?dptStation=";
    private static final String URL_END = "&arrStation=";
    private static final String URL_DATE = "&date=";
    private static final String URL_TYPE = "&type=normal&user=neibu&source=site";
    private static final String URL_PAGE_NUM = "&start=";
    private static final String URL_PAGE_SIZE = "&num=";
    private static final String URL_SORT = "&sort=3";

    public static String getTicketUrl(String start, String end, String date, int pageNum, int pageSize) {
        return URL_START + start + URL_END + end + URL_DATE + date + URL_TYPE
                + URL_PAGE_NUM + pageNum + URL_PAGE_SIZE + pageSize + URL_SORT;
    }
}
