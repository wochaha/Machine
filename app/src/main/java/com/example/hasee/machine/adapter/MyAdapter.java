package com.example.hasee.machine.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hasee.machine.R;
import com.example.hasee.machine.bean.Message;
import com.example.hasee.machine.holder.ViewHolder;

import java.util.ArrayList;

/**
 * @author Hasee
 */
public class MyAdapter extends RecyclerView.Adapter<ViewHolder> {
    private ArrayList<Message> messageArrayList;

    public MyAdapter(ArrayList<Message> list){
        messageArrayList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_item,parent,false);
        return new ViewHolder(view,messageArrayList);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message msg = messageArrayList.get(position);
        if (msg.getType() == Message.TYPE_RECEIVED) {
            holder.getLeft_layout().setVisibility(View.VISIBLE);
            holder.getRight_layout().setVisibility(View.GONE);
            holder.getLeft_text().setText(msg.getContent());
        }else if (msg.getType() == Message.TYPE_SEND){
            holder.getLeft_layout().setVisibility(View.GONE);
            holder.getRight_layout().setVisibility(View.VISIBLE);
            holder.getRight_text().setText(msg.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return messageArrayList.size();
    }
}
