package com.even.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.even.util.TokenUtil;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.HttpKit;
import com.jfinal.kit.Ret;
import org.apache.log4j.Logger;

public class MobileInterceptor implements Interceptor {

    public static Logger logger = Logger.getLogger(MobileInterceptor.class);

    public void intercept(Invocation inv) {
        Controller controller = inv.getController();
        if (controller.getRequest().getRequestURI().startsWith("/")) {
            JSONObject json = null;
            try {
                json = JSON.parseObject(HttpKit.readData(controller.getRequest()));
            } catch (Exception e) {
                e.printStackTrace();
                controller.renderJson(Ret.fail("error", "提交参数异常。"));
                return;
            }
            if (json != null) {
                String requestUrl = controller.getRequest().getRequestURI();
                if (requestUrl.contains("/user/login") || requestUrl.contains("/user/register") || requestUrl.contains("/ticket/query")){
                    inv.getController().setAttr("data", json);
                    inv.invoke();
                }else{
                    if (!json.containsKey("token")) {
                        controller.renderJson(Ret.fail("error", "token提交异常。"));
                        return;
                    }
                    if (!TokenUtil.checkToken(json.getString("token"))) {
                        controller.renderJson(Ret.fail("error", "授权失效,请重新登录。"));
                        return;
                    }
                    inv.getController().setAttr("data", json);
                    inv.invoke();
                }
            }
        } else {
            inv.invoke();
        }
    }
}
