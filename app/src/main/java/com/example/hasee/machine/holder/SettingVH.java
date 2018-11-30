package com.example.hasee.machine.holder;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.hasee.machine.R;

import static com.example.hasee.machine.activity.DialogueActivity.mDrawableList;

public class SettingVH extends RecyclerView.ViewHolder {
    private ImageButton mImageButton;
    private int mPosition;
    private SharedPreferences.Editor mEditor;
    private Context mContext;

    public SettingVH(Context context, View itemView, SharedPreferences.Editor editor) {
        super(itemView);
        mImageButton = itemView.findViewById(R.id.setting_item);
        mEditor = editor;
        mContext = context;

        ViewGroup.LayoutParams layoutParams = mImageButton.getLayoutParams();
        Display display = ((Activity)mContext).getWindow().getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        int mWidth = displayMetrics.widthPixels / 3;
        layoutParams.width = mWidth;
        layoutParams.height = mWidth*2;
    }

    public void bind(int position){
        mPosition = position;
        mImageButton.setImageDrawable(mDrawableList.get(position));
    }

    public void setListener() {
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.putInt("position",mPosition);
                mEditor.commit();
            }
        });
    }
}
