package com.lanhuzi.lode.dowlodreceive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class DownloadReceiver extends BroadcastReceiver {
    private static final String TAG = "DownloadReceiver";
    static final String ACTION = "com.lanhuzi.lode.dowlodreceive.action";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: 下载的广播运行");
        Toast.makeText(context, "下载的广播运行", Toast.LENGTH_LONG).show();
        context.sendBroadcast(new Intent(ACTION));

    }


}
