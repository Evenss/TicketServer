package com.even.service;

import com.even.bean.query.TicketData;
import com.even.model.UserMonitorTicket;
import com.even.spider.query.NetCallBack;
import com.even.spider.query.NetworkConnector;
import com.even.util.APIUtil;
import com.even.util.TimeUtil;

import java.util.List;

public class TicketService {

    public static void query(String departure, String destination, long date, boolean isGD, int pageSize, int pageNum, NetCallBack callBack) {
        String dateStr = TimeUtil.getTimeFormatted(date, TimeUtil.FORMAT_YEAR_MONTH_DAY);
        String url = APIUtil.getTicketUrl(departure, destination, dateStr, pageNum, pageSize);
        NetworkConnector.getInstance().query(url, callBack);
    }

    public static TicketData.TicketInfo query(String departure, String destination, long date, boolean isGD, int pageSize, int pageNum) {
        String dateStr = TimeUtil.getTimeFormatted(date, TimeUtil.FORMAT_YEAR_MONTH_DAY);
        String url = APIUtil.getTicketUrl(departure, destination, dateStr, pageNum, pageSize);
        TicketData.TicketInfo ticketInfo = NetworkConnector.getInstance().query(url);
        return ticketInfo;
    }

    public static boolean setMonitor(int user_id,String dptStation, String arrStation,long startDate,List<String> trainNo,List<String> seats) {
        return new UserMonitorTicket()
                .setUserId(user_id)
                .setState(true)
                .setTicketCount(-1)
                .setDptStationName(dptStation)
                .setArrStationName(arrStation)
                .set("start_date",startDate)
                .setTrainNum(trainNo.toString())
                .setSeats(seats.toString())
                .setPrice((float)0).save();
    }
}
