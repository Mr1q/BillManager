package com.example.hp.billmanger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public class Begin_paper extends BaseActivity {
    final String url = "http://cn.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1";   //获取必应每日一图的url地址
    ImageView imageView;
    Bing_Paper bing_paper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beginning_image);
        imageView = (ImageView) findViewById(R.id.begin_image);

        HttpUtill.sendOkHttpRequest(url, new okhttp3.Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String content = response.body().string();
                        bing_paper = new Gson().fromJson(content, Bing_Paper.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Glide.with(getApplicationContext()).load("http://cn.bing.com" + bing_paper.images.get(0).partUrl).placeholder(R.mipmap.th).diskCacheStrategy(DiskCacheStrategy.NONE).into(imageView);
                            }
                        });


                    }
                }
        );
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(Begin_paper.this, Login.class);
                    startActivity(intent);
                    Begin_paper.this.finish();
                }
            }
        }).start();


    }
}
