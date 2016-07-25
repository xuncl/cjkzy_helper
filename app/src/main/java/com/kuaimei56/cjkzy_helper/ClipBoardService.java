package com.kuaimei56.cjkzy_helper;

import android.app.Service;
import android.content.ClipData;
import android.content.ClipData.Item;
import android.content.ClipboardManager;
import android.content.ClipboardManager.OnPrimaryClipChangedListener;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.kuaimei56.cjkzy_helper.utils.Const;

/**
 * 
 * @author Administrator
 *
 */
public class ClipBoardService extends Service{

	private MyBinder binder = new MyBinder();

	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

	@Override
	public void onCreate() {
		super.onCreate();
// 获取剪贴板服务
        final ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        // 添加监听，而不是设置
        // 如果系统添加了多次监听，则每个方法都会被执行
        cm.addPrimaryClipChangedListener(new OnPrimaryClipChangedListener() {
            @Override
            public void onPrimaryClipChanged() {
                // API11后所有剪切板操作都在ClipData中
//                LogUtils.v(Const.STRATEGY_TAG, "Clip changed receive!");
                ClipData data = cm.getPrimaryClip();
                Item item = data.getItemAt(0);
                Intent xIntent = new Intent();
                xIntent.setAction(Const.ACTION_CLIP);
                xIntent.putExtra(Const.KEY_CLIP, item.getText().toString());
                sendBroadcast(xIntent);
                //TODO : 这里没有执行
                Log.e(this.getClass().getSimpleName(), "========复制文字:" + item.getText());
            }
        });
	}


	@Override
	public void onStart(Intent intent, int startId) {
	}
	
	@Override
	public void onDestroy() {
	}
	
	
	@Override
	public boolean onUnbind(Intent intent) {
		return super.onUnbind(intent);
	}
	
	public class MyBinder extends Binder{
		ClipBoardService getService(){
			return ClipBoardService.this;
		}
	}
	
}
