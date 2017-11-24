package com.even.bean.query;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TicketData {
    @SerializedName("ret")
    public boolean code;
    @SerializedName("data")
    public TicketInfo data;

    public class TicketInfo{
        @SerializedName("dptStation")
        public String dptStation;
        @SerializedName("arrStation")
        public String arrStation;
        @SerializedName("s2sBeanList")
        public List<TicketList> ticketLists;
    }

    public class TicketList{
        @SerializedName("dptStationName")
        public String dptStationName;
        @SerializedName("arrStationName")
        public String arrStationName;
        @SerializedName("trainNo")
        public String trainNo;
        @SerializedName("startDate")
        public String startDate;
        @SerializedName("dptTime")
        public String dptTime;
        @SerializedName("arrTime")
        public String arrTime;
        @SerializedName("saleStatus")
        public Desc desc;
        @SerializedName("extraBeanMap")
        public ExtraTicketInfo extraTicketInfo;
        @SerializedName("seats")
        public Seats seats;
    }

    public class Desc{
        @SerializedName("desc")
        public String desc;
    }

    public class ExtraTicketInfo{
        @SerializedName("interval")
        public String interval;
    }

    public class Seats {
        @SerializedName("无座")
        public Seat zero;
        @SerializedName("硬座")
        public Seat hard_seat;
        @SerializedName("硬卧")
        public Seat hard_sleep;
        @SerializedName("软卧")
        public Seat soft;
        @SerializedName("商务座")
        public Seat business;
        @SerializedName("二等座")
        public Seat two;
        @SerializedName("一等座")
        public Seat one;
    }

    public class Seat{
        @SerializedName("price")
        public String price;
        @SerializedName("count")
        public Integer count;
    }

    public boolean isSuccess(){
        return code;
    }

}

