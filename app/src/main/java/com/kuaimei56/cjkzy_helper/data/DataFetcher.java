package com.kuaimei56.cjkzy_helper.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kuaimei56.cjkzy_helper.entity.Strategy;
import com.kuaimei56.cjkzy_helper.utils.Const;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CLEVO on 2016/7/23.
 */
public class DataFetcher {

    public static ArrayList<Strategy> fetchOnce(SQLiteDatabase db) {
        String selection = Const.COL_VALID + "=?";
        String[] selectionArgs =
                {
                        String.valueOf(1)
                };

        Cursor cursor = db.query(Const.TABLE_NAME, null, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            return buildStrategyListByCursor(cursor);
        } else {
            return null;
        }
    }

    private static ArrayList<Strategy> buildStrategyListByCursor(Cursor cursor) {
        ArrayList<Strategy> strategies = new ArrayList<Strategy>();
        do {
            int id = cursor.getInt(cursor.getColumnIndex(Const.COL_ID));
            String keyword = cursor.getString(cursor.getColumnIndex(Const.COL_KEYWORD));
            int valid = cursor.getInt(cursor.getColumnIndex(Const.COL_VALID));
            int mode = cursor.getInt(cursor.getColumnIndex(Const.COL_MODE));
            String result = cursor.getString(cursor.getColumnIndex(Const.COL_RESULT));
            int resultMode = cursor.getInt(cursor.getColumnIndex(Const.COL_RESULT_MODE));
            String updateTime = cursor.getString(cursor.getColumnIndex(Const.COL_UPDATE_TIME));
            String recorder = cursor.getString(cursor.getColumnIndex(Const.COL_RECORDER));
            String regex = cursor.getString(cursor.getColumnIndex(Const.COL_REGEX));
            Strategy strategy = new Strategy(id, keyword, valid, mode, result, resultMode,
                    updateTime, recorder, regex);
            strategies.add(strategy);
        }
        while (cursor.moveToNext());
        return strategies;
    }
}
