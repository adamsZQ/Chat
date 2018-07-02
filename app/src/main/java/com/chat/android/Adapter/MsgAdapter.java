package com.chat.android.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chat.android.MainActivity;
import com.chat.android.MyApplication;
import com.chat.android.R;
import com.chat.android.db.Msg;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.chat.android.MainActivity.pref;
import static com.chat.android.R.id.right_image;
import static com.chat.android.R.id.system_avatar;



public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder>{

    private List<Msg> mMsgList;

        static  class ViewHolder extends RecyclerView.ViewHolder{

            RelativeLayout leftLayout;
            RelativeLayout rightLayout;
            TextView leftMsg;
            TextView rightMsg;
            TextView time_left;
            TextView time_right;
            CircleImageView left_image;
            CircleImageView right_image;

            public ViewHolder(View view){
                super(view);
                leftLayout = (RelativeLayout) view.findViewById(R.id.left_layout);
                rightLayout = (RelativeLayout) view.findViewById(R.id.right_layout);
                leftMsg = (TextView) view.findViewById(R.id.left_msg);
                leftMsg.setTextIsSelectable(true);
                rightMsg = (TextView) view.findViewById(R.id.right_msg);
                rightMsg.setTextIsSelectable(true);
                time_left = (TextView) view.findViewById(R.id.time_left);
                time_right = (TextView) view.findViewById(R.id.time_right);
                left_image = (CircleImageView) view.findViewById(R.id.left_image);
                right_image = (CircleImageView) view.findViewById(R.id.right_image);
//                final String per_avatar = pref.getString("per_avatar_pic",null);
//                final int per_set_avatar = pref.getInt("per_set_avatar",0);
//                if(per_avatar != null){
//                    Glide.with(MyApplication.getContext()).load(per_avatar).into(right_image);
//                }else{
//                    Glide.with(MyApplication.getContext()).load(per_set_avatar).into(right_image);
//                }
//                final int system_set_avatar = pref.getInt("system_set_avatar",0);
//                final String system_avatar = pref.getString("system_avatar_pic",null);
//                if(system_avatar != null){
//                    Glide.with(MyApplication.getContext()).load(system_avatar).into(left_image);
//                }else{
//                    Glide.with(MyApplication.getContext()).load(system_set_avatar).into(left_image);
//                }

            }
        }

    public MsgAdapter(List<Msg> msgList){
        mMsgList = msgList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_item,parent,false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Msg msg = mMsgList.get(position);
        if(msg.getType()==Msg.TYPE_RECEIVED){
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.rightLayout.setVisibility(View.GONE);
            holder.leftMsg.setText(msg.getText());
            holder.time_left.setText(msg.getTime());
            final int system_set_avatar = pref.getInt("system_set_avatar",0);
            final String system_avatar = pref.getString("system_avatar_pic",null);
            if(system_avatar != null){
                Glide.with(MyApplication.getContext()).load(system_avatar).into(holder.left_image);
            }else{
                Glide.with(MyApplication.getContext()).load(system_set_avatar).into(holder.left_image);
            }
        }else if(msg.getType()==Msg.TYPE_SEND){
            holder.rightLayout.setVisibility(View.VISIBLE);
            holder.leftLayout.setVisibility(View.GONE);
            holder.rightMsg.setText(msg.getText());
            holder.time_right.setText(msg.getTime());
            final String per_avatar = pref.getString("per_avatar_pic",null);
            final int per_set_avatar = pref.getInt("per_set_avatar",0);
            if(per_avatar != null){
                Glide.with(MyApplication.getContext()).load(per_avatar).into(holder.right_image);
            }else{
                Glide.with(MyApplication.getContext()).load(per_set_avatar).into(holder.right_image);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mMsgList.size();
    }
}
