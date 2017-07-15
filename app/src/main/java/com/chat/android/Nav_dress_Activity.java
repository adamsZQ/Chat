package com.chat.android;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.chat.android.Dialog.ActionSheetDialog;
import com.chat.android.Dialog.AlertDialog;
import com.suke.widget.SwitchButton;

import java.io.File;
import java.io.IOException;

import static com.chat.android.MainActivity.editor;
import static com.chat.android.MainActivity.pref;


public class Nav_dress_Activity extends AppCompatActivity {

    private  Button back_choose;
    private Button back_set;
    private Button head_choose;
    private Uri imageUri;
    private static final int TAKE_PHOTO_BACK = 1;
    private static final int TAKE_PHOTO_HEAD = 2;
    private static final int CHOOSE_PHOTO_BACK = 3;
    private static final int CHOOSE_PHOTO_HEAD = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_dress);
        
        //标题栏
        Toolbar toolbar = (Toolbar) findViewById(R.id.nav_dress_toolbar);
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

        //聊天背景开关按钮
        backAutoChange();

        //头像背景开关按钮
        headAutoChange();

        //系统自带背景图片按钮
        back_set = (Button) findViewById(R.id.back_set);
        back_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent10  = new Intent(Nav_dress_Activity.this,BackImageActivity.class);
                startActivity(intent10);
            }
        });

        //背景图片本地选择按钮
        back_choose = (Button)findViewById(R.id.back_choose);
        back_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ActionSheetDialog(Nav_dress_Activity.this)
                        .builder()
                        .setCancelable(true)
                        .setCanceledOnTouchOutside(true)
                        .addSheetItem("拍照", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        takePhoto("back_pic.jpg");
                                    }

                                })
                        .addSheetItem("从相册选择", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        //动态申请权限
                                        if(ContextCompat.checkSelfPermission(Nav_dress_Activity.this,
                                                Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                                            ActivityCompat.requestPermissions(Nav_dress_Activity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
                                        }else{
                                            openAlbum("back_pic.jpg");
                                        }
                                    }
                                }).show();
            }
        });

        //头像背景本地选择按钮
        head_choose = (Button)findViewById(R.id.head_choose);
        head_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ActionSheetDialog(Nav_dress_Activity.this)
                        .builder()
                        .setCancelable(true)
                        .setCanceledOnTouchOutside(true)
                        .addSheetItem("拍照", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        takePhoto("head_pic.jpg");
                                    }

                                })
                        .addSheetItem("从相册选择", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        //动态申请权限
                                        if(ContextCompat.checkSelfPermission(Nav_dress_Activity.this,
                                                Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                                            ActivityCompat.requestPermissions(Nav_dress_Activity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                                        }else{
                                            openAlbum("head_pic.jpg");
                                        }
                                    }
                                }).show();

            }
        });
    }

    //头像背景开关按钮
    private void headAutoChange() {
        com.suke.widget.SwitchButton switchButton1 = (com.suke.widget.SwitchButton)
                findViewById(R.id.head_auto_change_Button);

        String head_button_isChecked = pref.getString("head_button_isChecked",null);
        if("true".equals(head_button_isChecked)){
            switchButton1.setChecked(true);
        }else{
            switchButton1.setChecked(false);
        }
        switchButton1.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                //TODO do your job
                if(isChecked){

                    editor.putString("head_tp_pic",null);
                    editor.putString("head_button_isChecked","true");
                    editor.apply();

                    new AlertDialog(Nav_dress_Activity.this).builder()
                            .setMsg("您已选择头像背景图片自动切换。背景将会每天自动更新一次！")
                            .setNegativeButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            LoadPic.getPic("head_auto_pic");
                                        }
                                    }).start();
                                }
                            }).show();
                }else{

                    editor.putString("head_button_isChecked","false");
                    editor.apply();

                    new AlertDialog(Nav_dress_Activity.this).builder()
                            .setMsg("您已取消头像背景图片自动切换。可本地选择进行手动更改！")
                            .setPositiveButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            }).show();
                }
            }
        });
    }

    //聊天背景开关按钮
    private void backAutoChange() {
        com.suke.widget.SwitchButton switchButton = (com.suke.widget.SwitchButton)
                findViewById(R.id.back_auto_change_Button);

        String back_button_isChecked = pref.getString("back_button_isChecked",null);
        if("true".equals(back_button_isChecked)){
            switchButton.setChecked(true);
        }else{
            switchButton.setChecked(false);
        }

        switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if(isChecked){
                    editor.putString("back_tp_pic",null);
                    editor.putInt("back_set_pic",0);
                    editor.putString("back_button_isChecked","true");
                    editor.apply();

                    new AlertDialog(Nav_dress_Activity.this).builder()
                            .setMsg("您已选择聊天背景图片自动切换。背景将会每天自动更新一次！")
                            .setNegativeButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            LoadPic.getPic("back_auto_pic");
                                        }
                                    }).start();

                                }
                            }).show();
                }else{

                    editor.putString("back_button_isChecked","false");
                    editor.apply();

                    new AlertDialog(Nav_dress_Activity.this).builder()
                            .setMsg("您已取消聊天背景图片自动切换。可本地选择进行手动更改！")
                            .setPositiveButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            }).show();
                }
            }
        });

    }

    //拍照功能
    private void takePhoto(String name){
        File photo = new File(getExternalCacheDir(), name);
        try {
            if (photo.exists()) {
                photo.delete();
            }
            photo.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= 24) {
            imageUri = FileProvider.getUriForFile(Nav_dress_Activity.this,
                    "com.chat.android.fileprovider", photo);
        } else {
            imageUri = Uri.fromFile(photo);
        }
        //启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        if(name.equals("back_pic.jpg")){
            startActivityForResult(intent,TAKE_PHOTO_BACK);
        }else if(name.equals("head_pic.jpg")){
            startActivityForResult(intent,TAKE_PHOTO_HEAD);
        }
    }

    //打开相册
    private void openAlbum(String name){
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        if(name.equals("back_pic.jpg")){
            startActivityForResult(intent,CHOOSE_PHOTO_BACK);
        }else if(name.equals("head_pic.jpg")){
            startActivityForResult(intent,CHOOSE_PHOTO_HEAD);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults){
        switch (requestCode){
            case 0:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openAlbum("back_pic.jpg");
                }else {
                    Toast.makeText(this,"您拒绝了访问相册",Toast.LENGTH_SHORT).show();
                }
                break;
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openAlbum("head_pic.jpg");
                }else {
                    Toast.makeText(this,"您拒绝了访问相册",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected  void onActivityResult(int requestCode, int resultCode ,Intent data){
        switch(requestCode){
            case TAKE_PHOTO_BACK:
                if(resultCode == RESULT_OK){
                    editor.putString("back_tp_pic",imageUri.toString());
                    editor.apply();
                    Toast.makeText(this, "修改成功！", Toast.LENGTH_SHORT).show();
                }
                break;
            case TAKE_PHOTO_HEAD:
                if(resultCode == RESULT_OK){
                    editor.putString("head_tp_pic",imageUri.toString());
                    editor.apply();
                    Toast.makeText(this, "修改成功！", Toast.LENGTH_SHORT).show();
                }
                break;
            case CHOOSE_PHOTO_BACK:
                if (resultCode == RESULT_OK) {
                    // 判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        // 4.4及以上系统使用这个方法处理图片
                        handleImageOnKitKat(data,"back_pic.jpg");
                    } else {
                        // 4.4以下系统使用这个方法处理图片
                        handleImageBeforeKitKat(data,"back_pic.jpg");
                    }
                }
                break;
            case CHOOSE_PHOTO_HEAD:
                if (resultCode == RESULT_OK) {
                    // 判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        // 4.4及以上系统使用这个方法处理图片
                        handleImageOnKitKat(data,"head_pic.jpg");
                    } else {
                        // 4.4以下系统使用这个方法处理图片
                        handleImageBeforeKitKat(data,"head_pic.jpg");
                    }
                }
                break;
            default:break;
        }
    }

    private void handleImageBeforeKitKat(Intent data,String name) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
       displayImage(imagePath,name);
    }


    @TargetApi(19)
    private void handleImageOnKitKat(Intent data,String name) {
        String imagePath = null;
        Uri uri = data.getData();
        Log.d("TAG", "handleImageOnKitKat: uri is " + uri);
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        displayImage(imagePath,name); // 根据图片路径显示图片
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath,String name) {
        if (imagePath != null) {
            if(name.equals("back_pic.jpg")){
                editor.putString("back_tp_pic",imagePath);
                editor.apply();
            }else if(name.equals("head_pic.jpg")){
                editor.putString("head_tp_pic",imagePath);
                editor.apply();
            }
            Toast.makeText(this, "修改成功！", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "加载图片失败！", Toast.LENGTH_SHORT).show();
        }
    }
}

