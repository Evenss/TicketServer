package com.even.service;

import com.even.bean.monitor.TicketMonitor;
import com.even.bean.monitor.TicketMonitorInfo;
import com.even.bean.query.TicketData;
import com.even.model.UserMonitorTicket;
import com.even.spider.query.NetCallBack;
import com.even.spider.query.NetworkConnector;
import com.even.util.APIUtil;
import com.even.util.StringUtil;
import com.even.util.TimeUtil;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import java.util.ArrayList;
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

    public static boolean setMonitor(int userId, String dptStation, String arrStation, long startDate, List<String> trainNo, List<String> seats) {
        return new UserMonitorTicket()
                .setUserId(userId)
                .setState(true)
                .setTicketCount(-1)
                .setDptStationName(dptStation)
                .setArrStationName(arrStation)
                .set("start_date", startDate)
                .setTrainNum(trainNo.toString())
                .setSeats(seats.toString())
                .setPrice((float) 0).save();
    }

    public static TicketMonitor queryMyOrder(int userId, int pageSize, int pageNum) {
        Page<UserMonitorTicket> ticketList = UserMonitorTicket.dao.paginate(
                pageNum,
                pageSize,
                "SELECT *",
                "FROM user_monitor_ticket WHERE user_id = ?",
                userId);

        TicketMonitor ticketMonitor = new TicketMonitor();
        List<TicketMonitorInfo> list = new ArrayList<TicketMonitorInfo>();

        ticketMonitor.data.lastPage = ticketList.isLastPage();
        ticketMonitor.data.pageNumber = ticketList.getPageNumber();
        for(UserMonitorTicket ticket : ticketList.getList()){
            TicketMonitorInfo info = new TicketMonitorInfo();
            info.dptStationName = ticket.getDptStationName();
            info.arrStationName = ticket.getArrStationName();
            info.trainNo = StringUtil.listUtil(ticket.getTrainNum());
            if(ticket.getState()){
                info.state = 1;//已完成
            }else{
                info.state = 0;
            }
            info.startDate = ticket.getStartDate();
            info.seats = StringUtil.listUtil(ticket.getSeats());
            info.price = ticket.getPrice();
            list.add(info);
        }
        ticketMonitor.data.list = list;
        return ticketMonitor;
    }

}
