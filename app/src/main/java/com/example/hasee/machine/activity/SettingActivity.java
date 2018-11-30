package com.example.hasee.machine.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.example.hasee.machine.R;
import com.example.hasee.machine.adapter.SettingAdapter;

import static com.example.hasee.machine.activity.DialogueActivity.mDrawableList;

public class SettingActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private SettingAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeting);
        setActionBar();

        mRecyclerView = findViewById(R.id.setting_recycler);

        SharedPreferences sharedPreferences = getSharedPreferences("setting",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        mAdapter = new SettingAdapter(this,mDrawableList,editor);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("设置");
            // 显示返回按钮
            actionBar.setDisplayHomeAsUpEnabled(true);
            // 去掉logo图标
            actionBar.setDisplayShowHomeEnabled(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:   //返回键的id
                this.finish();
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
