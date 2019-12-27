 package com.example.hp.billmanger;

import com.google.gson.annotations.SerializedName;


import java.util.List;

public class Bing_Paper {
    @SerializedName("images")
    public List<BaseBingWallpaper> images;

    public class BaseBingWallpaper {
        @SerializedName("url")
        public String partUrl;//这里的链接还需要加上微软的 http://cn.bing.com在前面

        @SerializedName("enddate")
        public String endDate;//最后更新的时间

        @SerializedName("startdate")
        public String startDate;


    }
}