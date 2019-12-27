package com.example.hp.billmanger;


import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpUtill {


    public static void sendOkHttpRequest(String addres,okhttp3.Callback callback)
    {
        OkHttpClient Client=new OkHttpClient();
        Request request=new Request.Builder().url(addres).build();
        Client.newCall(request).enqueue(callback);
    }

}
