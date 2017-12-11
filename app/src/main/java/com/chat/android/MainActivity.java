package com.chat.android;


import android.app.ActivityManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.bumptech.glide.Glide;
import com.chat.android.Adapter.MsgAdapter;
import com.chat.android.Dialog.AlertDialog;
import com.chat.android.Theme.CardPickerDialog;
import com.chat.android.Theme.ThemeHelper;
import com.chat.android.db.Msg;
import com.chat.android.util.Address;
import com.chat.android.util.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;



public class MainActivity extends AppCompatActivity implements CardPickerDialog.ClickListener{

    private DrawerLayout drawerLayout;
    private List<Msg> msgList = new ArrayList<>();
    private Button send;
    private EditText inputText;
    private MsgAdapter adapter;
    private RecyclerView msgRecyclerView;
    private String content;
    private Button exit;
    private Button settings;
    private NavigationView navigationView;
    private ImageView back_image;
    private String[] welcome_array;
    private double currentTime,oldTime = 0;
    public static SharedPreferences.Editor editor;
    public static SharedPreferences pref ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //初始化
        initMsg();
        send = (Button) findViewById(R.id.send);
        exit = (Button) findViewById(R.id.exit);
        settings = (Button) findViewById(R.id.settings);
        inputText = (EditText)findViewById(R.id.input_text);
        msgRecyclerView = (RecyclerView)findViewById(R.id.msg_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);
        adapter = new MsgAdapter(msgList);
        msgRecyclerView.setAdapter(adapter);
        pref = getSharedPreferences("data",MODE_PRIVATE);
        editor = pref.edit();
        back_image = (ImageView)findViewById(R.id.ic_back);
        editor.putInt("per_set_avatar",R.drawable.right_image);
        editor.putInt("system_set_avatar",R.drawable.left_image);
        editor.apply();

        //标题栏
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        //滑动菜单中的监听
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                                                                                                                                                                                                                                                                                                //
//                if(preMenuItem!=null){
//                    preMenuItem.setChecked(false);
//                }
//                item.setChecked(true);
//                preMenuItem = item;

                switch (item.getItemId()){
                    case R.id.nav_person:
                        Intent intent1 = new Intent(MainActivity.this,Nav_per_Activity.class);
                        startActivity(intent1);
                        break;
                    case R.id.nav_dress:
                        Intent intent2 = new Intent(MainActivity.this,Nav_dress_Activity.class);
                        startActivity(intent2);
                        break;
                    case R.id.nav_teach:
                        Intent intent3 = new Intent(MainActivity.this,Nav_teach_Activity.class);
                        startActivity(intent3);
                        break;
                    case R.id.nav_theme:
//                        Toast.makeText(MainActivity.this, "等待小田的更新哦！", Toast.LENGTH_SHORT).show();
                        CardPickerDialog dialog = new CardPickerDialog();
                        dialog.setClickListener(MainActivity.this);
                        dialog.show(getSupportFragmentManager(), CardPickerDialog.TAG);
                        break;
                    case R.id.nav_tools:
                        Intent intent5 = new Intent(MainActivity.this,Nav_tools_Activity.class);
                        startActivityForResult(intent5,1);
                        break;
                    case R.id.nav_feedback:
                        Intent intent6 = new Intent(MainActivity.this,Nav_feedback_Activity.class);
                        startActivity(intent6);
                        break;
                    default:break;
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });

        //发送按钮的监听
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content = inputText.getText().toString();
                chat();
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog(MainActivity.this).builder().setTitle("您确定要退出吗？")
                        .setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        })
                        .setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .show();
            }
        });


        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "亲，等着更新哦～" , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,SettingsActivity.class);
                startActivity(intent);
            }
        });

        //背景图片的加载
        load_back_pic();
        //应用工具活动的处理
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ThemeUtils.getColorById(this, R.color.colorPrimaryDark));
            ActivityManager.TaskDescription description = new ActivityManager.TaskDescription(null, null, ThemeUtils.getThemeAttrColor(this, android.R.attr.colorPrimary));
            setTaskDescription(description);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //背景图片的加载
        load_back_pic();
//        Intent intent = getIntent();
//        String tools = intent.getStringExtra("tools");
//        switch(tools){
//            case "tuPian":
//                content = "清明上河图";
//                chat();
//                break;
//            default: break;
//
//        }
    }
    
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        load_back_pic();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initMsg(){
        welcome_array = this.getResources().getStringArray(R.array.welcome_tips);
        int index =(int) (Math.random()*(welcome_array.length-1));
        String welcome_tip = welcome_array[index];
        Msg msg = new Msg(welcome_tip,Msg.TYPE_RECEIVED,getTime());
        msgList.add(msg);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                modifyData();
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.shun_kou_liu:
                content = "说个顺口溜呗～";
                chat();
                break;
            case R.id.xie_hou_yu:
                content = "来句歇后语～";
                chat();
                break;
            case R.id.rao_kou_ling:
                content = "来，说句绕口令";
                chat();
                break;
            case R.id.xiao_hua:
                content = "讲个笑话让乐乐呗";
                chat();
                break;
            case R.id.gu_shi:
                content = "讲个小故事呀";
                chat();
                break;
            default:

        }
        return true;
    }

    //监测用户数据的修改
    public void modifyData(){
        View nav_header_view = navigationView.getHeaderView(0);
        TextView username = (TextView)nav_header_view.findViewById(R.id.username);
        TextView per_sign = (TextView)nav_header_view.findViewById(R.id.per_sign);
        final CircleImageView user_portrait  = (CircleImageView) nav_header_view.findViewById(R.id.user_icon);
        final ImageView nav_back  = (ImageView)nav_header_view.findViewById(R.id.nav_back);

        user_portrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Nav_per_Activity.class);
                startActivity(intent);

            }
        });
        //头像背景的加载
        final String headTPPic = pref.getString("head_tp_pic",null);
        final String headPic = pref.getString("head_auto_pic",null);
        if(headTPPic!=null){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Glide.with(MainActivity.this).load(headTPPic).into(nav_back);
                }
            });

        }else if(headPic!=null){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Glide.with(MainActivity.this).load(headPic).into(nav_back);
                }
            });

        }
        String head_button_isChecked = pref.getString("head_button_isChecked",null);
        if("true".equals(head_button_isChecked)){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    LoadPic.getPic("head_auto_pic");
                }
            });
        }

        //用户名以及个性签名的加载
        String name = pref.getString("username",null);
        String sign = pref.getString("signature",null);

        if(name == null){
            editor.putString("username","用户名");
            username.setText("用户名");
        }else{
            username.setText(name);
        }
        if(sign == null){
            editor.putString("signature","个性签名");
            per_sign.setText("个性签名");
        }else{
            per_sign.setText(sign);
        }

        //用户头像的加载

        final int per_set_avatar = pref.getInt("per_set_avatar",0);
        final String per_avatar = pref.getString("per_avatar_pic",null);
        if(per_avatar != null){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Glide.with(MainActivity.this).load(per_avatar).into(user_portrait);
                }
            });
        }else{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Glide.with(MainActivity.this).load(per_set_avatar).into(user_portrait);
                }
            });
        }
    }

    //加载背景图片
    public void load_back_pic(){
        final int backSetPic = pref.getInt("back_set_pic",0);
        final String backPic = pref.getString("back_auto_pic",null);
        final String backTPPic = pref.getString("back_tp_pic",null);
        if(backTPPic!=null){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Glide.with(MainActivity.this).load(backTPPic).into(back_image);
                }
            });
        }else if(backSetPic != 0){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Glide.with(MainActivity.this).load(backSetPic).into(back_image);
                }
            });
        }else if(backPic != null){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Glide.with(MainActivity.this).load(backPic).into(back_image);
                }
            });
        }
        String back_button_isChecked = pref.getString("back_button_isChecked",null);
        if("true".equals(back_button_isChecked)){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    LoadPic.getPic("back_auto_pic");
                }
            }).start();

        }
    }

   //聊天主函数
    public  void chat(){
        if(!"".equals(content)){
            Msg msg = new Msg(content,Msg.TYPE_SEND,getTime());
            msgList.add(msg);
            adapter.notifyItemInserted(msgList.size() - 1);
            msgRecyclerView.scrollToPosition(msgList.size()-1);
            inputText.setText("");//清空输入框
            String address = Address.setAddress(msg.getText().toString());
            HttpUtil.sendOkHttpRequest(address, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "亲，网络有问题吧～", Toast.LENGTH_SHORT).show();
                        }
                    });

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String responseText = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(!TextUtils.isEmpty(responseText)){
                                try{
                                    JSONObject chatObject = new JSONObject(responseText);
                                    JSONArray jsonArray = chatObject.getJSONArray("response");
                                    Msg msg = new Msg(jsonArray.getJSONObject(0).getString("answer"),Msg.TYPE_RECEIVED,getTime());

                                    msgList.add(msg);
                                    adapter.notifyItemInserted(msgList.size()-1);
                                    msgRecyclerView.scrollToPosition(msgList.size()-1);
//                                    if(!"".equals(chatObject.getString("url"))){
//                                        Msg msgUrl = new Msg(chatObject.getString("url"),Msg.TYPE_RECEIVED,getTime());
//                                        msgList.add(msgUrl);
//                                        adapter.notifyItemInserted(msgList.size()-1);
//                                        msgRecyclerView.scrollToPosition(msgList.size()-1);
//                                    }
                                }catch (JSONException e){
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                }
            });
        }
    }

    //时间
    private String  getTime(){
        currentTime = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("MM月dd日 HH：mm");
        Date curDate = new Date();
        String str = format.format(curDate);
        if(currentTime-oldTime >= 3*60*1000){
            oldTime = currentTime;
            return str;
        }else {
            return "";
        }
    }

    //返回键的监听
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            new AlertDialog(MainActivity.this).builder().setTitle("您确定要退出吗？")
                    .setPositiveButton("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    })
                    .setNegativeButton("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    })
                    .show();
        }
    }

    @Override
    public void onConfirm(int currentTheme) {
        if (ThemeHelper.getTheme(MainActivity.this) != currentTheme) {
            ThemeHelper.setTheme(MainActivity.this, currentTheme);
        }
        new AlertDialog(MainActivity.this).builder().setTitle("致歉")
                .setMsg("由于作者水平有限，主题颜色更换效果需重新启动应用方可见，且并非全局更换，为此带来的不便，敬请谅解！")
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();
    }
}
