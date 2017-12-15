package com.even.controller;

import com.even.util.PLog;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import org.apache.log4j.Logger;

@Clear
public class TestController extends Controller {

    private static Logger log = Logger.getLogger(TestController.class);

    public void index(){
        renderText("test2");
    }

    public void test(){
        renderText("123");
        PLog.i("PLog test logs!");
        PLog.e("PLog test logs!");
    }
}
