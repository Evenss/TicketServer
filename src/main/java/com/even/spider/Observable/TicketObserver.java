package com.even.spider.Observable;

import com.even.model.UserMonitorTicket;
import com.even.service.TicketService;
import com.even.service.UserService;
import com.even.util.EmailUtil;
import com.even.util.PushUtil;
import com.even.util.TimeUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * 观察者实现类，用户更新数据库并下发通知给用户
 */
public class TicketObserver implements Observer {

    public void update(Object o) {
        Map<String, Double> map = (Map<String, Double>) o;
        int userId = map.get("userId").intValue();
        int ticketCount = map.get("ticketCount").intValue();
        float price = map.get("price").floatValue();
        UserMonitorTicket ticket = TicketService.updateMonitorTicket(userId, ticketCount, price);
        notifyUser(ticket);
    }

    // 发送邮件等通知用户
    private void notifyUser(UserMonitorTicket ticket) {
        String title = "有票提醒";
        String content = "您预定的\n" +
                "日期为：" + TimeUtil.getTimeFormatted(ticket.getStartDate(), TimeUtil.FORMAT_YEAR_MONTH_DAY) + "\n" +
                "车次为：" + ticket.getTrainNum() + "\n" +
                "座位类型为：" + ticket.getSeats() + "\n" +
                "已经有票，赶快去买票吧！";


        //  发送邮箱通知
        String email = UserService.getUserById(ticket.getUserId()).getEmail();
        if (!StringUtils.isBlank(email)) {
            EmailUtil.getInstance().sendEmail(email, title, content);
        }

        // APP应用通知
        String phone = UserService.getUserById(ticket.getUserId()).getPhone();
        PushUtil.pushInfo(phone, title, content);// 这里可以设置点击之后跳转到哪里
    }


}
