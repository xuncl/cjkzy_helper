package com.kuaimei56.cjkzy_helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by CLEVO on 2016/7/16.
 */
public class BootCompleteReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "boot complete", Toast.LENGTH_LONG).show();
    }
}
