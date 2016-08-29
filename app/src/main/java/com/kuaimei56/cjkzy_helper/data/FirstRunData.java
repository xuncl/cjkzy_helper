package com.kuaimei56.cjkzy_helper.data;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.kuaimei56.cjkzy_helper.utils.Const;
import com.kuaimei56.cjkzy_helper.utils.LogUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by CLEVO on 2016/7/20.
 */
public class FirstRunData {
    SQLiteDatabase db;
    ContentValues values;

    public FirstRunData(SQLiteDatabase db){
        this.db = db;
    }

    public void insertOriginalData(){
        SimpleDateFormat sdf = new SimpleDateFormat(Const.FORMAT_PATTERN, Locale.CHINA);
        String now = sdf.format(new Date());
        insertOneKeyword("phone_number",1,1,"","@\\s*(.+)\\s*\\#\\s*(.+)\\s*\\$([\\s\\S]*[0-9]{11}[\\s\\S]*)",
                0, now, "xcl");
//        insertOneKeyword("phone_to_hypelink",1,3,"","/[^0-9+]*(?P<tel>(\\+86[0-9]{11})|([0-9]{11}))[^0-9+]*/",
//                3, now, "xcl");
    }

    public void updateOriginalData(){
        updateOneKeyword("phone_number","@\\s*(.+)\\s*\\#\\s*(.+)\\s*\\$([\\s\\S]*[0-9]{11}[\\s\\S]*)");
    }

    public void insertOneKeyword(String keyword, int valid, int mode, String result, String regex,
                                 int resultMode, String updateTime, String recorder){
        this.values = new ContentValues();

        values.put(Const.COL_KEYWORD, keyword); // 关键字
        values.put(Const.COL_VALID, valid);     //1：有效；0：无效
        values.put(Const.COL_MODE, mode);       //模式：1：应含；2：不含；3：正则
        values.put(Const.COL_RESULT, result);   //返回结果
        values.put(Const.COL_REGEX, regex);     //正则表达式
        values.put(Const.COL_RESULT_MODE, resultMode);  //返回结果模式：0:无；1：前缀；2：后缀；3：替换
        values.put(Const.COL_UPDATE_TIME, updateTime);  // 更新时间 yyyy/MM/dd HH:mm:ss
        values.put(Const.COL_RECORDER, recorder);   //更新人
        db.insert(Const.TABLE_NAME, null, values);
        values.clear();
        LogUtils.d(Const.DB_TAG, "INSERT INITIAL VALUE:" + keyword);
    }

    public void updateOneKeyword(String keyword,  String regex){
        this.values = new ContentValues();

        String whereClause = " " + Const.COL_KEYWORD + " = ? ";
        values.put(Const.COL_REGEX, regex);     //正则表达式
        String[] whereArgs =
                {
                        keyword
                };
        db.update(Const.TABLE_NAME, values, whereClause, whereArgs);
        values.clear();
        LogUtils.d(Const.DB_TAG, "UPDATE INITIAL VALUE:" + keyword);
    }
}
