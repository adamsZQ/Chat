package com.chat.android.db;

/**
 * Created by 26241 on 2017/7/12.
 */

public class BackImage {
    private String name;
    private int imageId;
    public BackImage(String name,int imageId){
           this.name = name;
        this.imageId = imageId;
    }



    public int getImageId() {
        return imageId;
    }

    public String getName() {
        return name;
    }
}
