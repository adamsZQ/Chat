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

import java.io.File;
import java.io.IOException;

import static com.chat.android.MainActivity.editor;

public class SettingsActivity extends AppCompatActivity {


    private Button teach_record;
    private Button chat_clean;
    private Button system_avatar;
    private Button system_about;
    private Button version_update;
    private  Uri imageUri;
    private static final int TAKE_PHOTO = 1;
    private static final int CHOOSE_PHOTO = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        teach_record = (Button) findViewById(R.id.teach_record);
        chat_clean = (Button) findViewById(R.id.chat_record);
        system_avatar = (Button) findViewById(R.id.system_avatar);
        system_about = (Button) findViewById(R.id.system_about);
        version_update = (Button) findViewById(R.id.version_update);

        Toolbar toolbar = (Toolbar) findViewById(R.id.settings_toolbar);
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

        //聊天记录清空按钮
        chat_clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog(SettingsActivity.this).builder().setTitle("提示")
                        .setMsg("您将会清空所有聊天记录，确定继续吗？")
                        .setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(SettingsActivity.this, "小田以人格担保，已不占用您任何内存~", Toast.LENGTH_LONG).show();
                            }
                        }).
                        setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }).show();
            }
        });
        //系统头像选择
        system_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ActionSheetDialog(SettingsActivity.this)
                        .builder()
                        .setCancelable(true)
                        .setCanceledOnTouchOutside(true)
                        .addSheetItem("拍照", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        takePhoto("system_avatar.jpg");
                                    }
                                })
                        .addSheetItem("从相册选择", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        if(ContextCompat.checkSelfPermission(SettingsActivity.this,
                                                Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                                            ActivityCompat.requestPermissions(SettingsActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                                        }else{
                                            openAlbum();
                                        }
                                    }
                                })
                        .addSheetItem("恢复系统默认",ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener(){
                                    @Override
                                    public void onClick(int which) {
                                        new AlertDialog(SettingsActivity.this).builder().setMsg("您确定使用系统默认头像吗？")
                                                .setPositiveButton("确定", new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        editor.putString("system_avatar_pic",null);
                                                        editor.apply();
                                                        Toast.makeText(SettingsActivity.this, "恢复成功", Toast.LENGTH_SHORT).show();
                                                    }
                                                })
                                                .setNegativeButton("取消", new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {

                                                    }
                                                }).show();

                                    }
                                }).show();

            }
        });
        //
        system_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this,System_about_Activity.class);
                startActivity(intent);
            }
        });

        version_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog(SettingsActivity.this).builder().setTitle("恭喜")
                        .setMsg("热烈祝贺，您当前已在体验最新版本哦！后续更新，敬请期待~")
                        .setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }).show();
            }
        });

        teach_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this,teach_Record_Activity.class);
                startActivity(intent);
            }
        });

    }

    //拍照功能
    public void takePhoto(String name){
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
            imageUri = FileProvider.getUriForFile(SettingsActivity.this,

                    "com.chat.android.fileprovider", photo);
        } else {
            imageUri = Uri.fromFile(photo);
        }
        //启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent,TAKE_PHOTO);

    }

    @Override
    protected  void onActivityResult(int requestCode, int resultCode ,Intent data){
        switch(requestCode){
            case TAKE_PHOTO:
                if(resultCode == RESULT_OK){
                    editor.putString("system_avatar_pic",imageUri.toString());
                    editor.apply();
                    Toast.makeText(this, "修改成功！", Toast.LENGTH_SHORT).show();
                }
                break;
            case CHOOSE_PHOTO:

                if (resultCode == RESULT_OK) {
                    // 判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        // 4.4及以上系统使用这个方法处理图片
                        handleImageOnKitKat(data);
                    } else {
                        // 4.4以下系统使用这个方法处理图片
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:break;
        }
    }
    //打开相册
    private void openAlbum(){
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults){
        switch (requestCode){
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }else {
                    Toast.makeText(this,"您拒绝了访问相册",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
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
        displayImage(imagePath); // 根据图片路径显示图片
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
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

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            editor.putString("system_avatar_pic",imagePath);
            editor.apply();
            Toast.makeText(this, "修改成功！", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "获取图片失败！", Toast.LENGTH_SHORT).show();
        }
    }

}
