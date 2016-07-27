package com.kuaimei56.cjkzy_helper;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.kuaimei56.cjkzy_helper.utils.Const;
import com.umeng.analytics.MobclickAgent;

public class MyApplication extends Application {
    public static RequestQueue queue;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        queue = Volley.newRequestQueue(getApplicationContext());

        // 集成友盟统计
        MobclickAgent.UMAnalyticsConfig config = new MobclickAgent.UMAnalyticsConfig(getApplicationContext(),
                Const.UMENG_APPKEY, Const.UMENG_CHANNELID, MobclickAgent.EScenarioType.E_UM_NORMAL);
        MobclickAgent.startWithConfigure(config);
    }

    public static RequestQueue getHttpQueue() {
        return queue;
    }

}
