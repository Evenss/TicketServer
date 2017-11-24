package com.even.spider.query;

/**
 * Created by Even on 2017/11/8.
 * 所有网络请求的回调函数
 */

public interface NetCallBack {
    void onNetworkError();

    void onFailed(String error);

    void onSuccess(Object object);
}
