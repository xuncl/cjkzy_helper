package com.kuaimei56.cjkzy_helper;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.ImageView;

import com.kuaimei56.cjkzy_helper.data.DataFetcher;
import com.kuaimei56.cjkzy_helper.data.MyDatabaseHelper;
import com.kuaimei56.cjkzy_helper.entity.Strategy;
import com.kuaimei56.cjkzy_helper.http.RawMessageSender;
import com.kuaimei56.cjkzy_helper.utils.Const;
import com.kuaimei56.cjkzy_helper.utils.LogUtils;
import com.kuaimei56.cjkzy_helper.utils.RegexMatch;

import java.util.ArrayList;

public class FloatingWindowService extends Service{


	private boolean isAdded = false; // 是否已增加悬浮窗
	
	private static WindowManager wm; 
	
	private static WindowManager.LayoutParams params;
	
	private View floatView;

	private float startX = 0;
	
	private float startY = 0;
	
	private float x;
	
	private float y;
	
	private String copyValue;

    private ArrayList<Strategy> strategies = null;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		createFloatView();
        strategies = ClipBoardActivity.strategyList;
        LogUtils.v(Const.STRATEGY_TAG, "FloatingWindowService GET strategyList=" + strategies.size());
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
//        LogUtils.v(Const.STRATEGY_TAG, "FloatingWindowService start!");
		if (intent != null) {
//            if (strategies==null){
//                strategies = (ArrayList<Strategy>) intent.getSerializableExtra(Const.KEY_STRATEGY_LIST);
//            }
            int operation = intent.getIntExtra(Const.OPERATION, Const.OPERATION_SHOW);
			switch (operation) {
			case Const.OPERATION_SHOW:
				if (!isAdded) {
					wm.addView(floatView, params);
					isAdded = true;
				}
				break;
			case Const.OPERATION_HIDE:
				if (isAdded) {
					wm.removeView(floatView);
					isAdded = false;
				}
				break;
			}
			copyValue = intent.getStringExtra(Const.KEY_COPY);
			setupCellView(floatView);
			Log.e(this.getClass().getSimpleName(), "=====copyValue :"+copyValue);
		}
	}
	
	/**
	 * 创建悬浮窗
	 */
	private void createFloatView() {
		LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);		
		floatView = layoutInflater.inflate(R.layout.dict_popup_window, null);
		
		wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
		params = new WindowManager.LayoutParams();

		// 设置window type
		params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
		
		/*
		 * 如果设置为params.type = WindowManager.LayoutParams.TYPE_PHONE; 那么优先级会降低一些,
		 * 即拉下通知栏不可见
		 */
		params.format = PixelFormat.RGBA_8888; // 设置图片格式，效果为背景透明

		// 设置Window flag
		params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
				| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
		/*
		 * 下面的flags属性的效果形同“锁定”。 悬浮窗不可触摸，不接受任何事件,同时不影响后面的事件响应。
		 * wmParams.flags=LayoutParams.FLAG_NOT_TOUCH_MODAL |
		 * LayoutParams.FLAG_NOT_FOCUSABLE | LayoutParams.FLAG_NOT_TOUCHABLE;
		 */

		// 设置悬浮窗的长得宽
//		params.width = getResources().getDimensionPixelSize(R.dimen.float_width);
		params.width = WindowManager.LayoutParams.MATCH_PARENT;
		params.height = WindowManager.LayoutParams.WRAP_CONTENT;

		params.gravity = Gravity.LEFT | Gravity.TOP;
		params.x = 0;
		params.y = 0;
		
		// 设置悬浮窗的Touch监听
		floatView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				x = event.getRawX();
				y = event.getRawY();
				
				switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					startX = event.getX();
					startY = event.getY();
					break;
				case MotionEvent.ACTION_MOVE:					
					params.x = (int)( x - startX);  
					params.y = (int) (y - startY);  
					wm.updateViewLayout(floatView, params);
					break;
				case MotionEvent.ACTION_UP:
					startX = startY = 0;  
					break;
				}
				return true;
			}
		});
		
		wm.addView(floatView, params);
		isAdded = true;
	}

	/**
	 * 设置浮窗view内部子控件
	 * @param rootView
	 */
	private void setupCellView(View rootView) {
		ImageView closedImg = (ImageView) rootView.findViewById(R.id.float_window_closed);

        // 判断复制过来的字符串
		if (null!=copyValue){
            // 进行本地过滤策略
			String checkedResult = checkString(copyValue);
			if(null!=checkedResult){
                // 显示绿色并发送
                rootView.setBackgroundColor(Color.rgb(0, 255, 0));
                RawMessageSender.send(checkedResult);
            }else{
                rootView.setBackgroundColor(Color.rgb(255, 0, 0));
            }
        }
		closedImg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (isAdded) {
					wm.removeView(floatView);
					isAdded = false;
				}
			}
		});
		floatView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//                wm.removeView(floatView);
//                isAdded = false;
			}
		});
	}

	/**
	 * 使用本地策略检查字符串是否合法
	 * @param copyValue 字符串
	 * @return 加过标签的字符串，如果为空，说明验证未通过
	 */
	private String checkString(String copyValue) {
        if (null==strategies) {
            LogUtils.e(Const.STRATEGY_TAG, "The strategy list is not delivered to FloatingWindowsService.");
            return Const.TEXT_TAG_PRE_RAW+copyValue;
        }
		RegexMatch regexMatch = new RegexMatch(strategies,copyValue);
        if (regexMatch.checkAll()){
            // TODO: 更多的返回方式
            return copyValue;
        }
        return null;
	}


}
