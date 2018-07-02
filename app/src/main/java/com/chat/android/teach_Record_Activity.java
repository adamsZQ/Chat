package com.chat.android;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.chat.android.Adapter.TeachRecordAdapter;
import com.chat.android.db.TeachRecord;

import java.util.ArrayList;
import java.util.List;

import static com.chat.android.MainActivity.editor;
import static com.chat.android.MainActivity.pref;

public class teach_Record_Activity extends AppCompatActivity {


    private List<TeachRecord> teachRecordList = new ArrayList<>();
    private TeachRecordAdapter teachRecordAdapter;
    private TeachRecord[] teachRecords = {};
    private int Count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teach_record);
        //标题栏
        Toolbar toolbar = (Toolbar) findViewById(R.id.teachRecord_toolbar);
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
        Toast.makeText(this, "该模块正在全力开发中，敬请期待", Toast.LENGTH_SHORT).show();
        initRecords();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.teach_record_recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
        teachRecordAdapter = new TeachRecordAdapter(teachRecordList);
        recyclerView.setAdapter(teachRecordAdapter);

    }

    private void initRecords() {
//        Count = pref.getInt("teachCount",0);
//        if(Count == 0){
//            Toast.makeText(this, "您当前并未创建教学记录", Toast.LENGTH_SHORT).show();
//        }
//        for(int i=0;i<Count;i++){
//            teachRecords[i].setTitle("教学记录 "+i+1);
//            teachRecords[i].setQuestion(pref.getString("teachRecord_question"+i+1,null));
//            teachRecords[i].setAnswer(pref.getString("teachRecord_answer"+i+1,null));
//            teachRecordList.add(teachRecords[i]);
//        }
    }
}
