package com.zerone.mlog;

import android.content.Context;
import android.os.Build;
import android.os.Process;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;


public class CrashHandler implements UncaughtExceptionHandler {

    // CrashHandler实例
    private static CrashHandler instance;
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private Context mContext;

    public CrashHandler() {

    }

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public synchronized static CrashHandler getInstance() {
        if (instance == null) {
            instance = new CrashHandler();
        }
        return instance;
    }


    @Override
    public void uncaughtException(Thread t, Throwable ex) {
        if (ex != null) {
            String path = StorageUtils.getCrashDir();
            if (null != path) {
                File file = new File(path + StringUtils.formatDate(System.currentTimeMillis(), "yyyyMMdd"));
                FileUtils.saveFile("", file);
                BufferedWriter bw = null;

                try {
                    FileWriter writer = new FileWriter(file);
                    bw = new BufferedWriter(writer);
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    ex.printStackTrace(pw);
                    String string = sw.toString();
                    StringBuffer sb = new StringBuffer();
                    sb.append(StringUtils.formatDate(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss") + ",");
                    sb.append("sdk_version:" + Build.VERSION.RELEASE + ",");
                    bw.write(sb.toString() + "\n");
                    bw.write(string);
//                    StackTraceElement[] stackTrace = ex.getStackTrace();
//                    StringBuilder stringBuilder = new StringBuilder();
//                    for (int i = 0; i < stackTrace.length; i++) {
//                        stringBuilder.append("\t" + stackTrace[i].toString() + "\n");
//
//                    }
//                    bw.write(stringBuilder.toString());

                } catch (IOException var18) {
                } finally {
                    if (null != bw) {
                        try {
                            bw.flush();
                            bw.close();
                        } catch (IOException var17) {
                            var17.printStackTrace();
                        }
                    }

                }
            }

            Process.killProcess(Process.myPid());
        }


    }

    public void init(Context context) {
        mContext = context.getApplicationContext();
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }
}
