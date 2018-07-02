package com.chat.android;

import android.view.View;
import android.widget.Toast;

import com.chat.android.Dialog.AlertDialog;
import com.chat.android.util.HttpUtil;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.chat.android.MainActivity.editor;
import static com.chat.android.MainActivity.pref;

/**
 * Created by 26241 on 2017/7/4.
 */

public class LoadPic{

    public static void getPic(final String pref_label){
            String requestPic = "http://guolin.tech/api/bing_pic";
            HttpUtil.sendOkHttpRequest(requestPic, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
//                    new AlertDialog(MyApplication.getContext()).builder()
//                            .setMsg("该功能需要联网使用，请检查您的网络连接")
//                            .setNegativeButton("确定", new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//
//                                }
//                            }).show();
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String bingPic = response.body().string();
                    if("back_auto_pic".equals(pref_label)){
                        String back_auto_pic = pref.getString("back_auto_pic",null);
                        if(!bingPic.equals(back_auto_pic)){
                            editor.putString("back_tp_pic",null);
                            editor.putString("back_set_pic",null);
                            editor.putString("back_auto_pic",bingPic);
                            editor.apply();
                        }
                    }else if("head_auto_pic".equals(pref_label)){
                        String head_auto_pic = pref.getString("head_auto_pic",null);
                        if(!bingPic.equals(head_auto_pic)){
                            editor.putString("head_tp_pic",null);
                            editor.putString("head_auto_pic",bingPic);
                            editor.apply();
                        }
                    }

                }
            });
    }
}

