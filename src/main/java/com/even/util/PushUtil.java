package com.even.util;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.style.*;

public class PushUtil {
    private static String appId = "PSg0aZXd6O7m9pmR1kIfd9";
    private static String appKey = "v0mU1uSbKT8Jdce2nie0R2";
    private static String masterSecret = "lnvVt4cWb89DGueUF4kJU5";
    private static String host = "http://sdk.open.api.igexin.com/apiex.htm";

    public static void pushInfo(String phone, String title, String content) {
        pushInfo(phone, title, content, "http://www.12306.cn/mormhweb/");
    }

    public static void pushInfo(String phone, String title, String content, String clickUrl) {
        IGtPush push = new IGtPush(host, appKey, masterSecret);
        LinkTemplate template = linkTemplateDemo(title, content, clickUrl);
        SingleMessage message = new SingleMessage();
        message.setOffline(true);
        // 离线有效时间
        message.setOfflineExpireTime(3600 * 1000);//1h
        message.setData(template);

        Target target = new Target();
        target.setAppId(appId);
//        target.setClientId(CID);
        target.setAlias(phone);
        IPushResult ret;
        try {
            ret = push.pushMessageToSingle(message, target);
        } catch (RequestException e) {
            e.printStackTrace();
            ret = push.pushMessageToSingle(message, target, e.getRequestId());
        }
        if (ret != null) {
//            System.out.println(ret.getResponse().toString());
        } else {
            System.out.println("Error：服务器响应异常");
        }
    }

    private static LinkTemplate linkTemplateDemo(String title, String content, String clickUrl) {
        LinkTemplate template = new LinkTemplate();
        // 设置APPID与APPKEY
        template.setAppId(appId);
        template.setAppkey(appKey);

        Style1 style = new Style1();
        // 设置通知栏标题与内容
        style.setTitle(title);
        style.setText(content);
        //todo 配置通知栏图标 客户端修改
        style.setLogo("icon.png");
        // 配置通知栏网络图标
        style.setLogoUrl("");
        // 设置通知是否响铃，震动，或者可清除
        style.setRing(true);
        style.setVibrate(true);
        style.setClearable(true);
        template.setStyle(style);

        // 设置打开的网址地址
        template.setUrl(clickUrl);
        return template;
    }
}
