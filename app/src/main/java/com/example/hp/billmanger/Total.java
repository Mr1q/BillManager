package com.example.hp.billmanger;

public class Total {
    private  String name;
    private  int imageId;
    private String much;
    private int id;
    private  String Day;

    public Total(String name,int imageId,String much,int id,String day)
    {
        this.name=name;
        this.imageId=imageId;
        this.much=much;
        this.id=id;
        this.Day=day;
    }

    public String getName(){
        return name;
    }
    public int getImageId(){
        return imageId;
    }
    public String getMuch(){
        return much;
    }
    public  int getId(){return id;}
    public  String  getDay(){return Day;}
}
