package com.even.spider.monitor;

import com.even.model.Ip;
import com.even.model.UserMonitorTicket;
import com.even.service.SpiderService;
import com.even.util.APIUtil;
import com.even.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 任务管理服务类
 */
public class CheckTicket {
    private static ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
    private static List<Ip> ipList = new ArrayList<Ip>();

    // 开启线程查询
    public static void startThread(UserMonitorTicket ticket) {
        ipList = SpiderService.getUsableIpList();

        String dateStr = TimeUtil.getTimeFormatted(ticket.getStartDate(), TimeUtil.FORMAT_YEAR_MONTH_DAY);
        String url = APIUtil.getTicketUrl(ticket.getArrStationName(), ticket.getDptStationName(), dateStr, 1, 100);
        CheckTask task = new CheckTask(ticket.getUserId(), ticket.getTrainNum(), ticket.getSeats(), url, ipList);
        executor.execute(task);

//        executor.shutdown();// 结束任务
    }

}
