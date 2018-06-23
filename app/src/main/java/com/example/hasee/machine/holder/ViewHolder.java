package com.example.hasee.machine.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hasee.machine.R;
import com.example.hasee.machine.bean.Message;

import java.util.ArrayList;

public class ViewHolder extends RecyclerView.ViewHolder {
    private TextView left_text;
    private TextView right_text;
    private LinearLayout left_layout;
    private LinearLayout right_layout;
    private ArrayList<Message> list;

    public ViewHolder(View itemView, ArrayList<Message> messageList) {
        super(itemView);

        left_text = itemView.findViewById(R.id.left_msg);
        right_text = itemView.findViewById(R.id.right_msg);
        left_layout = itemView.findViewById(R.id.left_layout);
        right_layout = itemView.findViewById(R.id.right_layout);

        list = messageList;
    }

    public TextView getLeft_text() {
        return left_text;
    }

    public void setLeft_text(TextView left_text) {
        this.left_text = left_text;
    }

    public TextView getRight_text() {
        return right_text;
    }

    public void setRight_text(TextView right_text) {
        this.right_text = right_text;
    }

    public LinearLayout getLeft_layout() {
        return left_layout;
    }

    public void setLeft_layout(LinearLayout left_layout) {
        this.left_layout = left_layout;
    }

    public LinearLayout getRight_layout() {
        return right_layout;
    }

    public void setRight_layout(LinearLayout right_layout) {
        this.right_layout = right_layout;
    }

    public ArrayList<Message> getList() {
        return list;
    }

    public void setList(ArrayList<Message> list) {
        this.list = list;
    }
}
