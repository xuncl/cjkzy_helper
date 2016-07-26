package com.kuaimei56.cjkzy_helper.http;

import android.content.Context;

import com.kuaimei56.cjkzy_helper.utils.HttpUtils;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by CLEVO on 2016/7/26.
 */
public class RawMessageSender {

    public static final String URL_RAW_MESSAGE="/rawmessages";
    public static final String CHARSET="UTF-8";

    public static void send(Context context, String content){
        JsonHttpResponseHandler handler = new JsonHttpResponseHandler();
        HttpEntity entity = new StringEntity(content, CHARSET);
        HttpUtils.post(context,URL_RAW_MESSAGE,entity,"application/json",handler);
    }

    public static void receive(){
        JsonHttpResponseHandler handler = new JsonHttpResponseHandler();
        HttpUtils.get("",handler);
    }
}
