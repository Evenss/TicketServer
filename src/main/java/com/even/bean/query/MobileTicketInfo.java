package com.even.bean.query;

/**
 * 向手机端发送的票详细信息
 */

public class MobileTicketInfo {
    public String dptStationName;
    public String arrStationName;
    public String trainNo;
    public long startDate;
    public String dptTime;
    public String arrTime;
    public String desc;
    public String interval;
    public String cheapestPrice;
    public TicketData.Seats seats;
}
