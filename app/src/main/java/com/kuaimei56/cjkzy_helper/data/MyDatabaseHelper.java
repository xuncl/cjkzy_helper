package com.kuaimei56.cjkzy_helper.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.kuaimei56.cjkzy_helper.utils.Const;

/**
 * Created by CLEVO on 2016/7/20.
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context mContext;

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Const.CREATE_KEYWORD);
        Toast.makeText(mContext, "Create Keyword success.", Toast.LENGTH_SHORT).show();
        FirstRunData fr = new FirstRunData(db);
        fr.insertOriginalData();
        Toast.makeText(mContext, "Insert original data success.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion<2){
            FirstRunData fr = new FirstRunData(db);
            fr.updateOriginalData();
            Toast.makeText(mContext, "Update original data success.", Toast.LENGTH_SHORT).show();
        }
    }
}
