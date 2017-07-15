package com.chat.android;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Nav_feedback_Activity extends AppCompatActivity {

    private EditText feed_text;
    private TextView feed_title;
    private Button delete_button;
    private String feed_content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_modify);

        feed_text = (EditText) findViewById(R.id.modify_edit);
        feed_title = (TextView) findViewById(R.id.modify_toolbar_title);
        delete_button = (Button) findViewById(R.id.delete_button);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        
        //标题栏
        Toolbar feed_toolbar = (Toolbar) findViewById(R.id.modify_toolbar);
        setSupportActionBar(feed_toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        feed_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        feed_title.setText("意见反馈");
        feed_text.setHint("请输入您的宝贵意见");
        
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    feed_text.setText("");

            }
        });
        
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feed_content = feed_text.getText().toString();
                if(!"".equals(feed_content)){
                    Toast.makeText(Nav_feedback_Activity.this, "反馈成功,谢谢您的支持", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(Nav_feedback_Activity.this, "您还没有输入呢~", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
