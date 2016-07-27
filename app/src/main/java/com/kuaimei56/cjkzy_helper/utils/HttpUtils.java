package com.kuaimei56.cjkzy_helper.utils;


import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.kuaimei56.cjkzy_helper.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by CLEVO on 2016/7/25.
 */
public class HttpUtils {


//    HttpPost request = new HttpPost(url);
//    // 先封装一个 JSON 对象
//    JSONObject param = new JSONObject();
//    param.put("name", "rarnu");
//    param.put("password", "123456");
//    // 绑定到请求 Entry
//    StringEntity se = new StringEntity(param.toString());
//    request.setEntity(se);
//    // 发送请求
//    HttpResponse httpResponse = new DefaultHttpClient().execute(request);
//    // 得到应答的字符串，这也是一个 JSON 格式保存的数据
//    String retSrc = EntityUtils.toString(httpResponse.getEntity());
//    // 生成 JSON 对象
//    JSONObject result = new JSONObject( retSrc);
//    String token = result.get("token");


    private static final String TAG = "HTTP_UTILS";

    public static void postJson(String str) {
        String url = "/Raw/messages";
        JsonObjectRequest jsonObjectRequest;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("content", str);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        //打印前台向后台要提交的post数据
        LogUtils.e(TAG, jsonObject.toString());
        //发送post请求
        LogUtils.e(TAG, "url:" + Const.BASE_URL + url);
        try {
            jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST, Const.BASE_URL+url, jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            //打印请求后获取的json数据
                            LogUtils.e(TAG, "Response:"+response.toString());
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError arg0) {
                    LogUtils.e(TAG, "ErrorResponse:"+arg0.toString());
                }
            })
              {

                @Override
                public String getBodyContentType() {
                    return "application/json";
                }
            }
            ;
            MyApplication.getHttpQueue().add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        MyApplication.getHttpQueue().start();
    }




    public static void volley_Post(final Context context) {
        String url = "http://apis.juhe.cn/mobile/get?";
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String arg0) {
                        Toast.makeText(context, arg0,
                                Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                Toast.makeText(context, "网络请求失败",
                        Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("phone", "13666666666");
                map.put("key", "335adcc4e891ba4e4be6d7534fd54c5d");
                return map;
            }
        };
        request.setTag("abcPost");
        MyApplication.getHttpQueue().add(request);
    }

    public void volley_Get(final Context context) {
        String url = "http://apis.juhe.cn/mobile/get?phone=13666666666&key=335adcc4e891ba4e4be6d7534fd54c5d";
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String arg0) {
                        Toast.makeText(context, arg0,
                                Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                Toast.makeText(context, "网络请求失败",
                        Toast.LENGTH_LONG).show();
            }
        });
        request.setTag("abcGet");
        MyApplication.getHttpQueue().add(request);
    }
}
