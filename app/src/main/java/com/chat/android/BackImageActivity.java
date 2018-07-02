package com.chat.android;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.chat.android.Adapter.BackImageAdapter;
import com.chat.android.db.BackImage;

import java.util.ArrayList;
import java.util.List;

public class BackImageActivity extends AppCompatActivity {

    private List<BackImage> backImageList = new ArrayList<>();
    private BackImageAdapter adapter;

    private BackImage[] backImages = {
            new BackImage("默认",R.drawable.back_white),
            new BackImage("江南",R.drawable.back_jiangnan),
            new BackImage("雪林",R.drawable.back_xuelin),
            new BackImage("大海",R.drawable.back_dahai),
            new BackImage("明月",R.drawable.back_mingyue),
            new BackImage("沉醉",R.drawable.back_chenzui),
            new BackImage("浪漫",R.drawable.back_tieta),
            new BackImage("那年",R.drawable.back_nanian),
            new BackImage("毕业",R.drawable.back_biye)
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.back_set);

        //标题栏
        Toolbar toolbar = (Toolbar) findViewById(R.id.back_set_toolbar);
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

        initImages();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.back_recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new BackImageAdapter(backImageList);
        recyclerView.setAdapter(adapter);
    }
    private void initImages(){
        for(int i=0;i<backImages.length;i++){
            backImageList.add(backImages[i]);
        }
    }
}
