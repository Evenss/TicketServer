package com.even.bean.monitor;

import java.util.List;

/**
 * 发送给移动端的用户标记车次信息
 */
public class TicketMonitor {
    public int state;
    public Data data;

    public TicketMonitor(){
        state = 0;
        this.data = new Data();
    }

    public class Data{
        public int pageNumber;
        public boolean lastPage;
        public List<TicketMonitorInfo> list;

    }
}
