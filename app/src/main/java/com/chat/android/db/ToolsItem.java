package com.chat.android.db;

/**
 * Created by 26241 on 2017/7/13.
 */

public class ToolsItem {
    private String name;
    private int imageId;
    public ToolsItem(String name,int imageId){
        this.imageId = imageId;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }
}
