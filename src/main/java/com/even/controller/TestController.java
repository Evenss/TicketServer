package com.even.controller;

import com.jfinal.core.Controller;

public class TestController extends Controller {
    public void index(){
        renderText("test");
    }

    public void test(){
        String a = getPara();
        System.out.print("test data = " + a);
        renderText(a);
    }
}
