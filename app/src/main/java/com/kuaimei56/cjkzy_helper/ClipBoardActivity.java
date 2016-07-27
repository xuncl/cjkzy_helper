package com.kuaimei56.cjkzy_helper;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.kuaimei56.cjkzy_helper.data.DataFetcher;
import com.kuaimei56.cjkzy_helper.data.MyDatabaseHelper;
import com.kuaimei56.cjkzy_helper.entity.Strategy;
import com.kuaimei56.cjkzy_helper.utils.Const;
import com.kuaimei56.cjkzy_helper.utils.HttpUtils;
import com.kuaimei56.cjkzy_helper.utils.LogUtils;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ClipBoardActivity extends Activity implements OnClickListener {

    private TextView mResultTextView;

    private Button mStart;

    private Button mStop;

    private Button mBind;

    private Button mUnBind;

    private Context mContext;

    private ClipBoardReceiver mBoardReceiver;

    // 全局变量 当前所有的过滤策略
    public static ArrayList<Strategy> strategyList;

    private MyDatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mContext = this;
        initUI();
        initDatabase();


    }

    private void initDatabase() {
        dbHelper = new MyDatabaseHelper(mContext, Const.DB_NAME, null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase(); //如果没有库表，则会建立
        strategyList = DataFetcher.fetchOnce(db);
        db.close();
    }

    private void initUI() {
        // 注册广播接收器，过滤器的action是自己设定的字符串
        mBoardReceiver = new ClipBoardReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Const.ACTION_CLIP);
        registerReceiver(mBoardReceiver, filter);

        mResultTextView = (TextView) findViewById(R.id.clip_text);
        mStart = (Button) findViewById(R.id.start);
        mStop = (Button) findViewById(R.id.stop);
        mBind = (Button) findViewById(R.id.bind);
        mUnBind = (Button) findViewById(R.id.unbind);

        mStart.setOnClickListener(this);
        mStop.setOnClickListener(this);
        mBind.setOnClickListener(this);
        mUnBind.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent mIntent = new Intent();
        switch (v.getId()) {
            case R.id.start:
                // 开启监听
                mIntent.setClass(ClipBoardActivity.this, ClipBoardService.class);
                mContext.startService(mIntent);
                break;
            case R.id.stop:
                // 关闭监听（并没有关闭）
                mIntent.setClass(ClipBoardActivity.this, ClipBoardService.class);
                mContext.stopService(mIntent);
                break;
            case R.id.bind:
                // 手动打开悬浮窗
                Intent show = new Intent(this, FloatingWindowService.class);
                show.putExtra(Const.OPERATION, Const.OPERATION_SHOW);
                startService(show);
//			HttpUtils.volley_Post(this);
//                getJson();
                break;
            case R.id.unbind:
                // 隐藏悬浮窗
                Intent hide = new Intent(this, FloatingWindowService.class);
                hide.putExtra(Const.OPERATION, Const.OPERATION_HIDE);
                startService(hide);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(mBoardReceiver);
    }

    class ClipBoardReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                String value = (String) bundle.get(Const.KEY_CLIP);
                Intent show = new Intent(ClipBoardActivity.this, FloatingWindowService.class);
                show.putExtra(Const.OPERATION, Const.OPERATION_SHOW);
                show.putExtra(Const.KEY_COPY, value);
                // 广播的功能：把剪贴板的数据value发送给悬浮窗服务
                ClipBoardActivity.this.startService(show);
            }
        }
    }

    // 友盟的会话统计
    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


    private String url = "http://apis.baidu.com/apistore/weatherservice/citylist";

    private void stringRequestWithPost() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //使用JSONObject给response转换编码
                    JSONObject jsonObject = new JSONObject(response);
                    mResultTextView.setText(jsonObject.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mResultTextView.setText(error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("cityname", "%E6%9C%9D%E9%98%B3");
                return map;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("apikey", "f71e5f1e08cd5a7e42a7e9aa70d22458");
                return map;
            }
        };
        MyApplication.getHttpQueue().add(stringRequest);
    }



}
