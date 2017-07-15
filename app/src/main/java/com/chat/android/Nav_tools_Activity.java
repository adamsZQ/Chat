package com.chat.android;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.chat.android.Adapter.ToolsAdapter;
import com.chat.android.db.ToolsItem;

import java.util.ArrayList;
import java.util.List;

public class Nav_tools_Activity extends AppCompatActivity {

    private ToolsAdapter adapter;
    private List<ToolsItem> toolsItemList = new ArrayList<>();

    private ToolsItem[] toolsItems = {
            new ToolsItem("图片",R.drawable.tool_tupian),
            new ToolsItem("影视",R.drawable.tool_yingshi),
            new ToolsItem("星座",R.drawable.tool_xingzuo),
            new ToolsItem("日期",R.drawable.tool_rili),
            new ToolsItem("邮编",R.drawable.tool_youbian),
            new ToolsItem("天气",R.drawable.tool_tianqi),
            new ToolsItem("百科",R.drawable.tool_baike),
            new ToolsItem("吉凶",R.drawable.tool_jixiong),
            new ToolsItem("股票",R.drawable.tool_gupiao),
            new ToolsItem("翻译",R.drawable.tool_fanyi),
            new ToolsItem("脑筋急转弯",R.drawable.tool_naojin)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_tools);

        //标题栏
        Toolbar toolbar = (Toolbar) findViewById(R.id.nav_tools_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initToolsItem();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.tool_recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ToolsAdapter(toolsItemList);
        recyclerView.setAdapter(adapter);
    }

     private void initToolsItem(){
        for(int i=0;i<toolsItems.length;i++){
            toolsItemList.add(toolsItems[i]);
        }
    }
}
