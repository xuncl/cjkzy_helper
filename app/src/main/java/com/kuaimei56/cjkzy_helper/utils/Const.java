package com.kuaimei56.cjkzy_helper.utils;

/**
 * Created by CLEVO on 2016/7/20.
 */
public final class Const {


    public static final String DB_NAME = "MyKeyword.db";
    public static final String TABLE_NAME = "Keyword";

    public static final String FORMAT_PATTERN = "yyyy/MM/dd HH:mm:ss";

    public static final String COL_ID = "id";
    public static final String COL_KEYWORD = "keyword";
    public static final String COL_VALID = "valid";
    public static final String COL_MODE = "mode";
    public static final String COL_RESULT = "result";
    public static final String COL_REGEX = "regex";
    public static final String COL_RESULT_MODE = "result_mode";
    public static final String COL_UPDATE_TIME = "update_time";
    public static final String COL_RECORDER = "recorder";

    public static final String ACTION_CLIP = "com.kuaimei56.cjkzy_helper.ClipBoardReceiver";

    public static final String KEY_CLIP = "clipboardValue";
    public static final String KEY_COPY = "copyValue";
    public static final String KEY_STRATEGY_LIST = "strategyList";

    public static final String DB_TAG = "DB_TAG";
    public static final String STRATEGY_TAG = "STRATEGY_TAG";


    public static final String OPERATION = "operation";
    public static final int OPERATION_SHOW = 100;
    public static final int OPERATION_HIDE = 101;

    public static final String CREATE_KEYWORD = "create table "+ Const.TABLE_NAME+" (" +
            Const.COL_ID+" integer primary key autoincrement, " +
            Const.COL_KEYWORD+" text, " +
            Const.COL_VALID+" integer, " +
            Const.COL_MODE+" integer, " +
            Const.COL_RESULT+" text, " +
            Const.COL_REGEX+" text, " +
            Const.COL_RESULT_MODE+" integer, "+
            Const.COL_UPDATE_TIME+" text, " +
            Const.COL_RECORDER+" text)";


    public static final String UMENG_APPKEY = "578e2709e0f55a16490019e5";
    public static final String UMENG_CHANNELID = "Manual";

    public static final String TEXT_TAG_PRE_RAW = "[RAW]";
}
