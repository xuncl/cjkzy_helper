package com.kuaimei56.cjkzy_helper;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;



import com.kuaimei56.cjkzy_helper.utils.HttpUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

public class MainActivity extends Activity {
    private TextView textView; // 顶部textview
    private ProgressDialog pDialog;
    private TextView textView2; // 下面textview，显示获取的所有数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.text);
        textView2 = (TextView) findViewById(R.id.text2);
    }

    public void method1(View view) {
        pDialog = ProgressDialog.show(this, "请稍等", "数据加载中");
        String urlString = "http://client.azrj.cn/json/cook/cook_list.jsp?type=1&p=2&size=10"; // 一個獲取菜谱的url地址
        HttpUtils.get(urlString, new AsyncHttpResponseHandler() {


            public void onFinish() { // 完成后调用，失败，成功，都要掉
            }

            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                pDialog.dismiss();
                textView.setText("获取json数据成功，看下面");
                textView2.setText(String.valueOf(bytes));
                Log.i("hck", String.valueOf(bytes));
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(MainActivity.this, "onFailure",
                        Toast.LENGTH_LONG).show();
            }


        });
    }

    public void method2(View view) {
        String urlString = "http://client.azrj.cn/json/cook/cook_list.jsp?";
        RequestParams params = new RequestParams(); // 绑定参数
        params.put("type", "1");
        params.put("p", "2");
        params.put("size", "10");
        HttpUtils.get(urlString, params, new JsonHttpResponseHandler() {


            public void onSuccess(JSONArray arg0) { // 成功后返回一个JSONArray数据
                Log.i("hck", arg0.length() + "");
                try {
                    textView.setText("菜谱名字："
                            + arg0.getJSONObject(2).getString("name")); //返回的是JSONArray， 获取JSONArray数据里面的第2个JSONObject对象，然后获取名字为name的数据值
                } catch (Exception e) {
                    Log.e("hck", e.toString());
                }
            }

            ;

            public void onFailure(Throwable arg0) {
                Log.e("hck", " onFailure" + arg0.toString());
            }

            ;

            public void onFinish() {
                Log.i("hck", "onFinish");
            }

            ;

            public void onSuccess(JSONObject arg0) {   //返回的是JSONObject，会调用这里
                Log.i("hck", "onSuccess ");
                try {
                    textView.setText("菜谱名字："
                            + arg0.getJSONArray("list").getJSONObject(0)
                            .getString("name"));
                } catch (Exception e) {
                    Log.e("hck", e.toString());
                }
            }

            ;
        });
    }

    public void method3(View view) {
        String urlString = "http://client.azrj.cn/json/cook/cook_list.jsp?type=1&p=2&size=10";
        HttpUtils.get(urlString, new JsonHttpResponseHandler() {


            public void onSuccess(JSONObject arg0) {
                try {
                    textView.setText("菜谱名字："
                            + arg0.getJSONArray("list").getJSONObject(1)
                            .getString("name"));
                } catch (Exception e) {
                    Log.e("hck", e.toString());
                }
            }

            ;
        });
    }

    public void method4(View view) {
        String urlString = "http://client.azrj.cn/json/cook/cook_list.jsp?";
        final RequestParams params = new RequestParams();
        params.put("type", "1");
        params.put("p", "2");
        params.put("size", "10");
        HttpUtils.get(urlString, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                try {
                    JSONObject jObject = new JSONObject(String.valueOf(bytes));
                    textView.setText("菜谱名字："
                            + jObject.getJSONArray("list").getJSONObject(2)
                            .getString("name"));
                    Log.i("hck", params.getEntity(new ResponseHandlerInterface() {
                        @Override
                        public void sendResponseMessage(HttpResponse httpResponse) throws IOException {

                        }

                        @Override
                        public void sendStartMessage() {

                        }

                        @Override
                        public void sendFinishMessage() {

                        }

                        @Override
                        public void sendProgressMessage(long l, long l1) {

                        }

                        @Override
                        public void sendCancelMessage() {

                        }

                        @Override
                        public void sendSuccessMessage(int i, Header[] headers, byte[] bytes) {

                        }

                        @Override
                        public void sendFailureMessage(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                        }

                        @Override
                        public void sendRetryMessage(int i) {

                        }

                        @Override
                        public URI getRequestURI() {
                            return null;
                        }

                        @Override
                        public Header[] getRequestHeaders() {
                            return new Header[0];
                        }

                        @Override
                        public void setRequestURI(URI uri) {

                        }

                        @Override
                        public void setRequestHeaders(Header[] headers) {

                        }

                        @Override
                        public void setUseSynchronousMode(boolean b) {

                        }

                        @Override
                        public boolean getUseSynchronousMode() {
                            return false;
                        }

                        @Override
                        public void setUsePoolThread(boolean b) {

                        }

                        @Override
                        public boolean getUsePoolThread() {
                            return false;
                        }

                        @Override
                        public void onPreProcessResponse(ResponseHandlerInterface responseHandlerInterface, HttpResponse httpResponse) {

                        }

                        @Override
                        public void onPostProcessResponse(ResponseHandlerInterface responseHandlerInterface, HttpResponse httpResponse) {

                        }

                        @Override
                        public void setTag(Object o) {

                        }

                        @Override
                        public Object getTag() {
                            return null;
                        }
                    }).toString());
                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }

            public void onSuccess(String arg0) {
                try {
                    JSONObject jObject = new JSONObject(arg0);
                    textView.setText("菜谱名字："
                            + jObject.getJSONArray("list").getJSONObject(2)
                            .getString("name"));
                    Log.i("hck", params.getEntity(new ResponseHandlerInterface() {
                        @Override
                        public void sendResponseMessage(HttpResponse httpResponse) throws IOException {

                        }

                        @Override
                        public void sendStartMessage() {

                        }

                        @Override
                        public void sendFinishMessage() {

                        }

                        @Override
                        public void sendProgressMessage(long l, long l1) {

                        }

                        @Override
                        public void sendCancelMessage() {

                        }

                        @Override
                        public void sendSuccessMessage(int i, Header[] headers, byte[] bytes) {

                        }

                        @Override
                        public void sendFailureMessage(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                        }

                        @Override
                        public void sendRetryMessage(int i) {

                        }

                        @Override
                        public URI getRequestURI() {
                            return null;
                        }

                        @Override
                        public Header[] getRequestHeaders() {
                            return new Header[0];
                        }

                        @Override
                        public void setRequestURI(URI uri) {

                        }

                        @Override
                        public void setRequestHeaders(Header[] headers) {

                        }

                        @Override
                        public void setUseSynchronousMode(boolean b) {

                        }

                        @Override
                        public boolean getUseSynchronousMode() {
                            return false;
                        }

                        @Override
                        public void setUsePoolThread(boolean b) {

                        }

                        @Override
                        public boolean getUsePoolThread() {
                            return false;
                        }

                        @Override
                        public void onPreProcessResponse(ResponseHandlerInterface responseHandlerInterface, HttpResponse httpResponse) {

                        }

                        @Override
                        public void onPostProcessResponse(ResponseHandlerInterface responseHandlerInterface, HttpResponse httpResponse) {

                        }

                        @Override
                        public void setTag(Object o) {

                        }

                        @Override
                        public Object getTag() {
                            return null;
                        }
                    }).toString());
                } catch (Exception e) {
                }
            }

            ;
        });
    }

    public void method5(View view) {
        String url = "http://f.hiphotos.baidu.com/album/w%3D2048/sign=38c43ff7902397ddd6799f046dbab3b7/9c16fdfaaf51f3dee973bf7495eef01f3b2979d8.jpg";
        HttpUtils.get(url, new BinaryHttpResponseHandler() {
            @Override
            public void onSuccess(int i, org.apache.http.Header[] headers, byte[] bytes) {

            }

            @Override
            public void onFailure(int i, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {

            }

            public void onSuccess(byte[] arg0) {
                File file = Environment.getExternalStorageDirectory();
                File file2 = new File(file, "cat");
                file2.mkdir();
                file2 = new File(file2, "cat.jpg");
                try {
                    FileOutputStream oStream = new FileOutputStream(file2);
                    oStream.write(arg0);
                    oStream.flush();
                    oStream.close();
                    textView.setText("可爱的猫咪已经保存在sdcard里面");
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("hck", e.toString());
                }
            }
        });
    }
}