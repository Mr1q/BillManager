package com.example.hp.billmanger;

public class Income  {

    private  String name;
    private  int imageId;
    public Income(String name,int imageId)
    {
        this.name=name;
        this.imageId=imageId;
    }

    public String getName(){
        return name;
    }
    public int getImageId(){
        return imageId;
    }


}
