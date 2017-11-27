package com.even.bean.query;

import java.util.List;

/**
 * 向手机端发送的票全部信息
 */

public class MobileTicket {
    public int state;
    public Data data;

    public class Data{
        public boolean lastPage;
        public String dptStation;
        public String arrStation;
        public List<MobileTicketInfo> list;
    }

    public MobileTicket(){
        state = 0;
        this.data = new Data();
    }
}
