package com.example.hasee.machine.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hasee.machine.R;
import com.example.hasee.machine.holder.SettingVH;
import java.util.List;

public class SettingAdapter extends RecyclerView.Adapter<SettingVH> {
    private List<Drawable> mDrawableList;
    private Context mContext;
    private SharedPreferences.Editor mEditor;

    public SettingAdapter(Context context, List<Drawable> drawables, SharedPreferences.Editor editor) {
        mContext = context;
        mDrawableList = drawables;
        mEditor = editor;
    }

    @NonNull
    @Override
    public SettingVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.setting_item,parent,false);
        return new SettingVH(mContext,view,mEditor);
    }

    @Override
    public void onBindViewHolder(@NonNull SettingVH holder, int position) {
        holder.bind(position);
        holder.setListener();
    }

    @Override
    public int getItemCount() {
        return mDrawableList.isEmpty() ? 0 : mDrawableList.size();
    }
}
