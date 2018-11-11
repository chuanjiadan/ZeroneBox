package com.lanhuzi.lode.dloadtest;

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
    private static PluginManager ourInstance = null;
    private static final String TAG = PluginManager.class.getSimpleName();
    private static List bc = null;

    public static PluginManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new PluginManager();
        }

        return ourInstance;
    }

    private PluginManager() {
    }


    /**
     * 加载插件
     *
     * @param context
     * @param file
     */
    public List<BroadcastReceiver> load(Context context, File file) {
        File inFile = new File("/sdcard/base.apk");
        Log.d(TAG, "load: " + inFile.getAbsolutePath());


        try {
            //首先对apk文件扫描解析
            Class aClass = Class.forName("android.content.pm.PackageParser");
            Object newInstance = aClass.newInstance();
            //反射得到PMS的apk解析方法
            Method declaredMethod = aClass.getDeclaredMethod("parsePackage", File.class, int.class);
            //获取解析apk的缓存
            Object packageInfo = declaredMethod.invoke(newInstance, inFile, PackageManager.GET_RECEIVERS);
            //从缓存中找到receiver
            Field receiverField = packageInfo.getClass().getDeclaredField("receivers");
            List receivers = (List) receiverField.get(packageInfo);


            Log.d(TAG, "load: " + inFile.getAbsolutePath());
            //类文件加载器
            DexClassLoader dexClassLoader = new DexClassLoader(inFile.getAbsolutePath(),
                    context.getDir("plugin", Context.MODE_PRIVATE).getAbsolutePath(),
                    null, context.getClassLoader());
            Log.d(TAG, "load plugin: " + context.getDir("plugin", Context.MODE_PRIVATE).getAbsolutePath());

            Class aClass2 = Class.forName("android.content.pm.PackageParser$Component");

            Log.d(TAG, "load class name: " + aClass2.getName());
            Field intents = aClass2.getDeclaredField("intents");

            Log.d(TAG, "load: intent:" + intents.getName());


            for (Object receiver : receivers) {
                Log.d(TAG, "load: " + receiver);
                Field className = receiver.getClass().getField("className");
                String name = (String) className.get(receiver);
                Class aClass1 = dexClassLoader.loadClass(name);
                BroadcastReceiver broadcastReceiver = (BroadcastReceiver) aClass1.newInstance();

                List<? extends IntentFilter> intentFilters = (List<? extends IntentFilter>) intents.get(receiver);

                for (IntentFilter intentFilter : intentFilters) {
                    Log.d(TAG, "注册广播 action= " + intentFilter.getAction(0));
                    MainActivity.load.add(broadcastReceiver);
                    context.registerReceiver(broadcastReceiver, intentFilter);


                }

            }

        } catch (Exception e) {

            e.printStackTrace();
            Log.d(TAG, "动态加载失败: " + e.getMessage());
        }

        return bc;
    }
}
