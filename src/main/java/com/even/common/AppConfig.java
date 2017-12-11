package com.even.common;

import com.even.controller.TestController;
import com.even.controller.TicketController;
import com.even.controller.UserController;
import com.even.interceptor.MobileInterceptor;
import com.even.model._MappingKit;
import com.even.spider.ip.IPSpider;
import com.jfinal.config.*;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.redis.RedisPlugin;
import com.jfinal.template.Engine;

public class AppConfig extends JFinalConfig{

    public static void main(String[] args) {
        JFinal.start("src/main/webapp", 80, "/",5);
    }

    @Override
    public void afterJFinalStart() {
        super.afterJFinalStart();
        IPSpider.start();
    }

    /**
     * 配置常量
     */
    public void configConstant(Constants constants) {
        PropKit.use("config.properties");
        constants.setDevMode(PropKit.getBoolean("devMode",false));
    }

    /**
     * 配置路由
     */
    public void configRoute(Routes routes) {
        routes.add("/", TestController.class);
        routes.add("/user", UserController.class);
        routes.add("/ticket", TicketController.class);

        // request拦截器
        routes.addInterceptor(new MobileInterceptor());
    }

    public void configEngine(Engine engine) {

    }

    /**
     * 配置插件
     */
    public void configPlugin(Plugins plugins) {
        // 配置 druid 数据库连接池插件
        DruidPlugin druidPlugin = new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
        plugins.add(druidPlugin);

        // 配置ActiveRecord插件
        ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
        // 所有映射在 MappingKit 中自动化搞定
        _MappingKit.mapping(arp);
        plugins.add(arp);

        // redis服务
        RedisPlugin redisPlugin = new RedisPlugin("ticket_assistant", PropKit.get("redis_ip"), PropKit.getInt("redis_port"));
        plugins.add(redisPlugin);
    }

    public void configInterceptor(Interceptors interceptors) {

    }

    public void configHandler(Handlers handlers) {

    }
}
