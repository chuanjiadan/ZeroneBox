package com.lanhuzi.lode.dloadtest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity sniper";

    static final String ACTION = "com.lanhuzi.lode.dowlodreceive.action";
    public static List<BroadcastReceiver> load = new ArrayList<>();
    //    static final String ACTION = "com.lanhuzi.lode.dowlodreceive.DownloadReceiver";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerReceiver(receiver, new IntentFilter(ACTION));


    }

    public void load(View view) {
        if (load != null && !load.isEmpty()) {
            for (BroadcastReceiver o : load) {
                unregisterReceiver(o);
            }

            load.clear();

        }
        PluginManager.getInstance().load(this, null);
        this.sendBroadcast(new Intent("com.lanhuzi.lode.dowlodreceive.DownloadReceiver.Action"));

    }

    private File copy() {
        String name = "base.apk";
        File base_apk = this.getDir("file", Context.MODE_PRIVATE);
        Log.d(TAG, "copy: " + base_apk.getAbsolutePath());
        File file = new File(base_apk, name);
        if (file.exists()) {
            file.delete();
        }
        FileInputStream is = null;
        FileOutputStream os = null;
        File file1 = new File(Environment.getExternalStorageDirectory(), name);
        Log.d(TAG, "copy: " + file1.getAbsolutePath());

        try {
            is = new FileInputStream(file1);
            os = new FileOutputStream(file);

            int len = 0;
            byte[] bytes = new byte[1024];
            if ((len = is.read(bytes)) != -1) {
                os.write(bytes, 0, len);
            }
            if (file.exists()) {
                Log.d(TAG, "copy: 插件apk准备完成" + file.getAbsolutePath());
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "copy: 插件apk准备失败");
            Log.d(TAG, "copy: " + e.getMessage());
        } finally {
            try {
                is.close();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }


    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Log.d(TAG, "主app接收到插件的广播，插件运行成功");

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        if (load != null && !load.isEmpty()) {
            for (BroadcastReceiver broadcastReceiver : load) {
                unregisterReceiver(broadcastReceiver);
            }

        }
    }
}
