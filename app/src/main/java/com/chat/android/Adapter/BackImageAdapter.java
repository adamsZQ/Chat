package com.chat.android.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chat.android.R;
import com.chat.android.db.BackImage;

import java.util.List;

import static com.chat.android.MainActivity.editor;

/**
 * Created by 26241 on 2017/7/12.
 */

public class BackImageAdapter extends RecyclerView.Adapter<BackImageAdapter.ViewHolder> {
    private Context mContext;
    private List<BackImage> mBackImageList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView backImage;
        TextView backName;

        public ViewHolder(View view){
            super(view);
            cardView = (CardView) view;
            backImage = (ImageView) view.findViewById(R.id.back_image);
            backName = (TextView) view.findViewById(R.id.back_image_name);
        }
    }


    public BackImageAdapter(List<BackImage> backImageList){
        mBackImageList = backImageList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.back_set_image,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                BackImage backImage = mBackImageList.get(position);
                editor.putString("back_tp_pic",null);
                editor.putInt("back_set_pic",backImage.getImageId());
                editor.apply();
                Toast.makeText(mContext, "修改成功", Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BackImage backImage = mBackImageList.get(position);
        holder.backName.setText(backImage.getName());
        Glide.with(mContext).load(backImage.getImageId()).into(holder.backImage);
    }

    @Override
    public int getItemCount() {
        return mBackImageList.size();
    }
}
