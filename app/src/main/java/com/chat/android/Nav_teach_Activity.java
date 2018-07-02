package com.chat.android;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static com.chat.android.MainActivity.editor;
import static com.chat.android.MainActivity.pref;

public class Nav_teach_Activity extends AppCompatActivity {

    private EditText teach_edit_1;
    private EditText teach_edit_2;
    private String content1;
    private String content2;
    private static int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_teach);
        
        teach_edit_1 = (EditText)findViewById(R.id.teach_edit_1);
        teach_edit_2 = (EditText)findViewById(R.id.teach_edit_2); 
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        count = pref.getInt("teachCount",0);

        //标题栏
        Toolbar toolbar = (Toolbar) findViewById(R.id.teach_toolbar);
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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content1 = teach_edit_1.getText().toString();
                content2 = teach_edit_2.getText().toString();
                if((!"".equals(content1)) &&(!"".equals(content2)) ){

                    count ++;
                    editor.putString("teachRecord_question"+count,content1);
                    editor.putString("teachRecord_answer"+count,content2);
                    editor.putInt("teachCount",count);
                    editor.apply();
                    Toast.makeText(Nav_teach_Activity.this, "教学成功,小田会努力的~", Toast.LENGTH_SHORT).show();

                } else if((!"".equals(content1))&&("".equals(content2))){
                    Toast.makeText(Nav_teach_Activity.this, "您还没有输入答案呢~", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Nav_teach_Activity.this, "您还没有输入问题呢~", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
