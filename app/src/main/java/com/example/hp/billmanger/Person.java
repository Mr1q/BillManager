package com.example.hp.billmanger;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

public class Person extends  BmobUser{
//    private String name;        //用户姓名
    private String address;     //用户地址
    private  String Birthday;   //用户生日
    private  String Sex;       //用户性别
    //private  static  String Number;  //用户ID,唯一键值
//    private double Totalin;     //用户总收入
//    private double Totalout;    //用户总支出
    private BmobFile image;//用户头像


    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
//    public void setNumber(String number){this.Number=number;}
//    public  String getNumber(){return Number;}
    public void setSex(String sex){this.Sex=sex;}
    public String getSex(){return  Sex;}
    public void setBirthday(String birthday){this.Birthday=birthday;}
    public String getBirthday(){return Birthday;}
//    public  void  setTotalout(double totalout){this.Totalout=totalout;}
//    public double getTotalout(){return Totalout;}
//    public  void  setTotalin(double totalin){this.Totalin=totalin;}
//    public double getTotalin(){return Totalin;}
    public  void  setImage(BmobFile image){this.image=image;}

    public BmobFile getImage() {
        return image;
    }

}