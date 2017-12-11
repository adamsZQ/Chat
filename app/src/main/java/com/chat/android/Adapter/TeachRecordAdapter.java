package com.chat.android.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chat.android.R;
import com.chat.android.db.TeachRecord;

import java.util.List;

/**
 * Created by 26241 on 2017/7/17.
 */

public class TeachRecordAdapter extends RecyclerView.Adapter<TeachRecordAdapter.ViewHolder> {

    private Context mContext;
    private List<TeachRecord> mTeachRecordList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView teach_record_title;
        TextView question;
        TextView answer;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            teach_record_title = (TextView) view.findViewById(R.id.teach_record_title);
            question = (TextView) view.findViewById(R.id.teach_record_question);
            answer = (TextView) view.findViewById(R.id.teach_record_answer);
        }
    }

    public TeachRecordAdapter(List<TeachRecord> teachRecordList){
        mTeachRecordList = teachRecordList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.teach_record_item,parent,false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TeachRecord teachRecord = mTeachRecordList.get(position);
        holder.teach_record_title.setText(teachRecord.getTitle().toString());
        holder.question.setText(teachRecord.getQuestion());
        holder.answer.setText(teachRecord.getAnswer());

    }

    @Override
    public int getItemCount() {
        return mTeachRecordList.size();
    }


}
