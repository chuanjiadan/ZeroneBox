package com.chris.box;

import android.app.Application;
import android.content.Context;
import android.os.Process;
import android.util.Log;

public class BaseApplication extends Application {


    private static final String TAG = "BaseApplication";
    public static Context mContext;

    private String name = "com.chris.box/com.chris.box.ProcessServic";


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getBaseContext();
        Log.d(TAG, "onCreate: ");


    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    @Override
    public Context getBaseContext() {
        return super.getBaseContext();
    }
}
