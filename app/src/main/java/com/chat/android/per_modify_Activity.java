package com.chat.android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.chat.android.MainActivity.editor;
import static com.chat.android.MainActivity.pref;
import static com.chat.android.R.layout.activity_main;
import static com.chat.android.R.layout.nav_modify;

public class per_modify_Activity extends AppCompatActivity {


    private EditText input_text;
    private TextView modify_title;
    private Button delete_button;
    private String diff;
//    private SharedPreferences pref;
//    private SharedPreferences.Editor editor = pref.edit();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_modify);


        input_text = (EditText) findViewById(R.id.modify_edit);
        modify_title = (TextView) findViewById(R.id.modify_toolbar_title);
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

        Intent intent = getIntent();
        diff = intent.getStringExtra("diff");
        if("1".equals(diff)){
            modify_title.setText("用户名");
            input_text.setText(pref.getString("username",""));
            input_text.setSelection(pref.getString("username","").length());
        }else if("2".equals(diff)){
            modify_title.setText("个性签名");
            input_text.setText(pref.getString("signature",""));
            input_text.setSelection(pref.getString("signature","").length());
        }


        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input_text.setText("");
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = input_text.getText().toString();
                if(!"".equals(content)){
//                    //获取另一个xml中的控件
//                    View view = LayoutInflater.from(per_modify_Activity.this).inflate(R.layout.nav_header,null);
//                    TextView username = (TextView) view.findViewById(R.id.username);
//                    username.setText(content);
                    if("1".equals(diff)){
                        editor.putString("username",content);
                        editor.apply();
                    }else if("2".equals(diff)){
                        editor.putString("signature",content);
                        editor.apply();
                    }
                    Toast.makeText(per_modify_Activity.this, "修改成功", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(per_modify_Activity.this, "您还没有输入呢~", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    
    
    
}
