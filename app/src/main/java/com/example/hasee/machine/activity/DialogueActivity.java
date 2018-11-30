package com.example.hasee.machine.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.hasee.machine.R;
import com.example.hasee.machine.adapter.MyAdapter;
import com.example.hasee.machine.bean.JsonBean;
import com.example.hasee.machine.bean.Message;
import com.example.hasee.machine.utils.MyApplication;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.hasee.machine.bean.Message.TYPE_RECEIVED;
import static com.example.hasee.machine.config.HttpConfig.API_KEY;
import static com.example.hasee.machine.config.HttpConfig.URL_KEY;

public class DialogueActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText input;
    private Button send;
    private int userid = 123456;
    private String address = URL_KEY + "?key=" + API_KEY;
    private String content;
    private ArrayList<Message> messages = new ArrayList<>();
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private LinearLayout mLayout;

    private int index;
    private SharedPreferences sharedPreferences;

    public static List<Drawable> mDrawableList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialogue);
        changeActionBar();

        initWidget();
        setBackground();
        send.setOnClickListener(this);
    }

    private void changeActionBar(){
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("小邮");
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setBackground();
    }

    private void setBackground(){
        sharedPreferences = getSharedPreferences("setting",Context.MODE_PRIVATE);
        if (sharedPreferences != null) {
            index = sharedPreferences.getInt("position",0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mLayout.setBackground(mDrawableList.get(index));
            }
        }
    }

    private void initWidget(){
        input = findViewById(R.id.input);
        send = findViewById(R.id.send);
        recyclerView = findViewById(R.id.recycler_view);
        mLayout = findViewById(R.id.back_layout);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        adapter = new MyAdapter(messages);
        recyclerView.setAdapter(adapter);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mDrawableList.add(getDrawable(R.drawable.background));
            mDrawableList.add(getDrawable(R.drawable.background1));
            mDrawableList.add(getDrawable(R.drawable.background2));
            mDrawableList.add(getDrawable(R.drawable.background3));
            mDrawableList.add(getDrawable(R.drawable.background4));
            mDrawableList.add(getDrawable(R.drawable.background5));
            mDrawableList.add(getDrawable(R.drawable.background6));
        }
    }

    @Override
    public void onClick(View v) {
        String text = input.getText().toString();
        content = text;
        switch (v.getId()) {
            case R.id.send:
                if (ContextCompat.checkSelfPermission(DialogueActivity.this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(DialogueActivity.this,new String[]{Manifest.permission.INTERNET},50);
                }else {
                    if (content != null) {
                        Message msg = new Message();
                        msg.setType(Message.TYPE_SEND);
                        msg.setContent(text);
                        messages.add(msg);
                        adapter.notifyItemInserted(messages.size()-1);
                        recyclerView.scrollToPosition(messages.size() - 1);
                        request(text, new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Toast.makeText(MyApplication.getContext(),e.toString(),Toast.LENGTH_SHORT).show();
                                Log.d("TAG",e.toString());
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                JsonBean jsonBean = parseJsonWithResponse(response.body().string());
                                Message message = new Message();
                                message.setContent(jsonBean.getText());
                                message.setType(TYPE_RECEIVED);
                                messages.add(message);
                                Log.d("info",message.getContent());
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        DialogueActivity.this.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                adapter.notifyItemInserted(messages.size()-1);
                                                recyclerView.scrollToPosition(messages.size() - 1);
                                            }
                                        });
                                    }
                                }).start();
                            }
                        });
                        input.setText("");
                    }else {
                        Toast.makeText(MyApplication.getContext(),"输入不能为空！",Toast.LENGTH_SHORT).show();
                    }
                }
                break;
                default:
                    break;
        }
    }

    private void request(String content,Callback callback) {
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder form = new FormBody.Builder();
        form.add("info",content);
        form.add("userid", String.valueOf(userid));
        Request request = new Request.Builder().url(address).post(form.build()).build();
        client.newCall(request).enqueue(callback);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 50:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    request(content,new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Toast.makeText(MyApplication.getContext(),e.toString(),Toast.LENGTH_SHORT).show();
                            Log.d("TAG",e.toString());
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            JsonBean jsonBean = parseJsonWithResponse(response.body().string());
                            Message message = new Message();
                            message.setContent(jsonBean.getText());
                            message.setType(TYPE_RECEIVED);
                            messages.add(message);
                            Log.d("info",message.getContent());
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    DialogueActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            adapter.notifyItemInserted(messages.size()-1);
                                            recyclerView.scrollToPosition(messages.size() - 1);
                                        }
                                    });
                                }
                            }).start();
                        }
                    });
                }else {
                    Toast.makeText(MyApplication.getContext(),"你拒绝了应用申请该权限！",Toast.LENGTH_SHORT).show();
                }
                break;
                default:
                    break;
        }
    }

    private JsonBean parseJsonWithResponse(String body) {
        Gson gson = new Gson();
        return gson.fromJson(body,JsonBean.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.setting:
                Intent intent = new Intent(this,SettingActivity.class);
                startActivity(intent);
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }
}
