package com.even.spider.query;

import com.even.bean.query.TicketData;
import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;

public class NetworkConnector {
    private static NetworkConnector mNetworkConnector;
    private OkHttpClient mClient;
    private MediaType JSON, FILE, JPG;
    private Gson mGson;

    public static NetworkConnector getInstance() {
        if (null == mNetworkConnector) {
            mNetworkConnector = new NetworkConnector();
        }
        return mNetworkConnector;
    }

    private NetworkConnector() {
        mClient = new OkHttpClient();
        mGson = new Gson();
        JSON = MediaType.parse("application/json; charset=utf-8");
        FILE = MediaType.parse("application/octet-stream");
        JPG = MediaType.parse("image/jpg");
    }

    //异步请求
    public void query(String url, final NetCallBack callBack){
        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = mClient.newCall(request);
        call.enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
                callBack.onNetworkError();
            }

            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                TicketData ticketResponse = mGson.fromJson(string, TicketData.class);
                if(ticketResponse.isSuccess()){
                    callBack.onSuccess(ticketResponse);
                }else{
                    callBack.onFailed(null);
                }
            }
        });
    }

    //同步请求
    public TicketData.TicketInfo query(String url){
        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = mClient.newCall(request);
        Response response;
        TicketData ticketResponse = null;
        try {
            response = call.execute();
            String string = response.body().string();
            System.out.println(string);
             ticketResponse = mGson.fromJson(string, TicketData.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ticketResponse.data;
    }
}
