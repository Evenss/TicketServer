package com.even.spider.query;

import com.even.bean.query.TicketData;
import com.google.gson.Gson;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;

public class NetworkConnector {
    private static NetworkConnector mNetworkConnector;

    private MediaType JSON, FILE, JPG;
    private Gson mGson;
    public static void main(String[] args){
        getInstance().query("http://127.0.0.1:8080");
    }


    public static NetworkConnector getInstance() {
        if (null == mNetworkConnector) {
            mNetworkConnector = new NetworkConnector();
        }
        return mNetworkConnector;
    }

    private NetworkConnector() {
        mGson = new Gson();
        JSON = MediaType.parse("application/json; charset=utf-8");
        FILE = MediaType.parse("application/octet-stream");
        JPG = MediaType.parse("image/jpg");
    }

    //异步请求
    public void query(String url, final NetCallBack callBack) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
                callBack.onNetworkError();
            }

            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                TicketData ticketResponse = mGson.fromJson(string, TicketData.class);
                if (ticketResponse.isSuccess()) {
                    callBack.onSuccess(ticketResponse);
                } else {
                    callBack.onFailed(null);
                }
            }
        });
    }

    //同步请求
    public TicketData.TicketInfo query(String url){
        return query(url,"",-1);
    }

    public TicketData.TicketInfo query(String url, String ip, int port) {
        OkHttpClient client;
        if (!StringUtils.isBlank(ip) && port > 0) {
            // 设置代理
            client = new OkHttpClient.Builder()
                    .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port)))
                    .build();
        } else {
            client = new OkHttpClient();
        }
        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        Response response;
        TicketData ticketResponse = null;
        try {
            response = call.execute();
            if (!response.isSuccessful()) {//访问不成功，ip被封，返回空内容
                System.out.println("Error：IP被封");
                ticketResponse = new TicketData();
                ticketResponse.data.ticketLists = null;
                ticketResponse.data.arrStation = "";
                ticketResponse.data.dptStation = "";
                return ticketResponse.data;
            }
            String string = response.body().string();
            System.out.println(string);
            ticketResponse = mGson.fromJson(string, TicketData.class);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error：网络异常");//访问不成功，网络差或没网，ip未被封，返回空
            return null;
        }
        return ticketResponse.data;
    }


}
