package com.even.common;

import com.even.controller.TestController;
import com.jfinal.config.*;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PropKit;
import com.jfinal.template.Engine;

public class AppConfig extends JFinalConfig{

    public static void main(String[] args) {
        JFinal.start("src/main/webapp", 8080, "/",5);
    }

    /**
     * 配置常量
     */
    public void configConstant(Constants constants) {
        PropKit.use("config.properties");
        constants.setDevMode(PropKit.getBoolean("devMode",false));
    }

    public void configRoute(Routes routes) {
        routes.add("/", TestController.class);
    }

    public void configEngine(Engine engine) {

    }

    public void configPlugin(Plugins plugins) {

    }

    public void configInterceptor(Interceptors interceptors) {

    }

    public void configHandler(Handlers handlers) {

    }
}
