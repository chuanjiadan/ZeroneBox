package com.chris.box.utiles;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import dalvik.system.DexClassLoader;

public class PluginManager {
    //插件管理类
    private static final PluginManager ourInstance = new PluginManager();
    private static final String TAG = PluginManager.class.getSimpleName();

    public static PluginManager getInstance() {
        return ourInstance;
    }

    private PluginManager() {
    }


    /**
     * 加载插件
     *
     * @param context
     */
    public void load(Context context) {
        File inFile = new File("/sdcard/base.apk");
        Log.d(TAG, "load: " + inFile.getAbsolutePath());


        try {
            Class aClass = Class.forName("android.content.pm.PackageParser");
            Object newInstance = aClass.newInstance();
            //反射得到方法
            Method declaredMethod = aClass.getDeclaredMethod("parsePackage", File.class, int.class);

            //得到解析apk的缓存
            Object packageInfo = declaredMethod.invoke(newInstance, inFile, PackageManager.GET_RECEIVERS);
            //从缓存中找到receiver
            Field receiverField = packageInfo.getClass().getDeclaredField("receivers");

            List receivers = (List) receiverField.get(packageInfo);


            //类文件加载器
            DexClassLoader dexClassLoader = new DexClassLoader(inFile.getAbsolutePath(),
                    context.getDir("plugin", Context.MODE_PRIVATE).getAbsolutePath(),
                    null, context.getClassLoader());

            Class aClass2 = Class.forName("android.content.pm.PackageParser$Component");
            Field intents = aClass2.getDeclaredField("intents");


            for (Object receiver : receivers) {
                Log.d(TAG, "load: " + receiver);
                Field className = receiver.getClass().getField("className");
                Log.d(TAG, "className: "+className);
                String name = (String) className.get(receiver);
                Class aClass1 = dexClassLoader.loadClass(name);
                BroadcastReceiver broadcastReceiver = (BroadcastReceiver) aClass1.newInstance();

                List<? extends IntentFilter> intentFilters = (List<? extends IntentFilter>) intents.get(receiver);

                for (IntentFilter intentFilter : intentFilters) {

                    context.registerReceiver(broadcastReceiver, intentFilter);

                }

            }

        } catch (Exception e) {

            e.printStackTrace();
            Log.d(TAG, "load: " + e.getMessage());
        }

    }
}
