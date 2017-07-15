package com.chat.android.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.chat.android.Dialog.AlertDialog;
import com.chat.android.MainActivity;
import com.chat.android.MyApplication;
import com.chat.android.R;
import com.chat.android.db.ToolsItem;

import java.util.List;


/**
 * Created by 26241 on 2017/7/13.
 */

public class ToolsAdapter extends RecyclerView.Adapter<ToolsAdapter.ViewHolder> {

    private Context mContext;
    private List<ToolsItem> mToolsItemsList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView ToolImage;
        TextView ToolName;

        public ViewHolder(View view){
            super(view);
            cardView = (CardView) view;
            ToolImage  = (ImageView) view.findViewById(R.id.tool_image);
            ToolName = (TextView) view.findViewById(R.id.tool_name);
        }
    }

    public ToolsAdapter(List<ToolsItem>  ToolsItemList){
        mToolsItemsList = ToolsItemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.nav_tools_item,parent,false);
        final ToolsAdapter.ViewHolder holder = new ToolsAdapter.ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                ToolsItem toolsItem = mToolsItemsList.get(position);
                switch (toolsItem.getImageId()){
                    case R.drawable.tool_tupian:
                        new AlertDialog(mContext).builder().setTitle("图片搜索")
                                .setMsg("您可以这样问我：XXX的图片")
//                                .setPositiveButton("前去体验", new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        Intent intent = new Intent(mContext, MainActivity.class);
//                                        intent.putExtra("tools","tuPian");
//                                        mContext.startActivity(intent);
////                                        Intent intent = new Intent();
////                                        intent.putExtra("tools","tu_pian");
//
//                                    }
//                                })
                                .setNegativeButton("我知道了", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                }).show();
                        break;
                    case R.drawable.tool_baike:
                        new AlertDialog(mContext).builder().setTitle("生活百科")
                                .setMsg("输入示例：天为什么是蓝的？")
                                .setPositiveButton("确定", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                }).show();
                        break;
                    case R.drawable.tool_fanyi:
                        new AlertDialog(mContext).builder().setTitle("中英翻译")
                                .setMsg("输入示例：苹果用英语怎么说？")
                                .setPositiveButton("确定", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                }).show();
                        break;
                    case R.drawable.tool_gupiao:
                        new AlertDialog(mContext).builder().setTitle("股票查询")
                                .setMsg("您可以这样问我：腾讯的股票")
                                .setPositiveButton("确定", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                }).show();
                        break;
                    case R.drawable.tool_jixiong:
                        new AlertDialog(mContext).builder().setTitle("吉凶占卜")
                                .setMsg("输入示例：田一间这个名字好不好？")
                                .setPositiveButton("确定", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                }).show();
                        break;
                    case R.drawable.tool_naojin:
                        new AlertDialog(mContext).builder().setTitle("脑筋急转弯")
                                .setMsg("您可以这样问：说一个脑筋急转弯")
                                .setPositiveButton("确定", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                }).show();
                        break;
                    case R.drawable.tool_tianqi:
                        new AlertDialog(mContext).builder().setTitle("天气查询")
                                .setMsg("输入示例：哈尔滨的天气怎么样")
                                .setPositiveButton("确定", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                }).show();
                        break;
                    case R.drawable.tool_rili:
                        new AlertDialog(mContext).builder().setTitle("日期查询")
                                .setMsg("输入示例：今天农历多少")
                                .setPositiveButton("确定", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                }).show();
                        break;
                    case R.drawable.tool_xingzuo:
                        new AlertDialog(mContext).builder().setTitle("星座运势")
                                .setMsg("输入示例：双鱼座的运势")
                                .setPositiveButton("确定", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                }).show();
                        break;
                    case R.drawable.tool_yingshi:
                        new AlertDialog(mContext).builder().setTitle("影视搜索")
                                .setMsg("输入示例：最近的热门电影")
                                .setPositiveButton("确定", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                }).show();
                        break;
                    case R.drawable.tool_youbian:
                        new AlertDialog(mContext).builder().setTitle("邮编查询")
                                .setMsg("输入示例：哈尔滨的邮编")
                                .setPositiveButton("确定", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                }).show();
                        break;
                    default:break;
                }

            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ToolsItem toolsItem = mToolsItemsList.get(position);
        holder.ToolName.setText(toolsItem.getName());
        Glide.with(mContext).load(toolsItem.getImageId()).into(holder.ToolImage);
    }

    @Override
    public int getItemCount() {
        return mToolsItemsList.size();
    }
}
