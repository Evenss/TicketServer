package com.even.service;

import com.even.spider.query.NetCallBack;
import com.even.spider.query.NetworkConnector;
import com.even.util.APIUtil;
import com.even.util.TimeUtil;

public class TicketService {

    public static void query(String departure, String destination, long date, boolean isGD, int pageSize, int pageNum, NetCallBack callBack) {
        String dateStr = TimeUtil.getTimeFormatted(date, TimeUtil.FORMAT_YEAR_MONTH_DAY);
        String url = APIUtil.getTicketUrl(departure, destination, dateStr, pageNum, pageSize);
        NetworkConnector.getInstance().query(url, callBack);
    }
}
