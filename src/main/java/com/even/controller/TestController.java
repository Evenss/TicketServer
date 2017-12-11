package com.even.controller;

import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;

@Clear
public class TestController extends Controller {

    public void index(){
        renderText("test");
    }

    public void test(){
        renderText("123");
        System.out.println("test logs!");
    }
}
