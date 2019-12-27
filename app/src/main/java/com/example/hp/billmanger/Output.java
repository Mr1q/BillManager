package com.example.hp.billmanger;

import cn.bmob.v3.BmobObject;

public class Output extends BmobObject {
    private String type;  //支出类型字段
    private  double much;//支出价格
    private String id;   //和当前用户绑定的键值
    private int Num;    //唯一键值
    private  String day; //支出月份
    private String YM;  //选择年月条件


    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public double getMuch() {
        return much;
    }
    public void setMuch(double much) {
        this.much = much;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public int getNum() {
        return Num;
    }
    public void setNum(int num) {
        this.Num=num;
    }
    public String getDay() {
        return day;
    }
    public void setDay(String day) {
        this.day=day;
    }
    public String getYM() {
        return YM;
    }
    public void setYM(String ym) {
        this.YM=ym;
    }

}
