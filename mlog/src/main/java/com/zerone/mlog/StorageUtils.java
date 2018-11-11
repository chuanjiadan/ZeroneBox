package com.zerone.mlog;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;

public class StorageUtils {
    public static final String TAG = "MLog_StorageUtils";

    private static final int EXPIRE_INTERVAL = 604800000;
    private static String sExternalPath = null;
    private static String FILE_DIR_APP;
    private static String FILE_DIR_APP_LOG;
    private static String FILE_DIR_APP_CRASH;
    private static String ALL_LOG_PATH;


    public StorageUtils() {
    }

    public static void initExtDir(Context context) {
        if (context != null) {
            if (android.os.Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                ALL_LOG_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() +
                        File.separator + context.getPackageName() + File.separator;
//                sExternalPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
//                FILE_DIR_APP = context.getExternalFilesDir((String) null).getAbsolutePath() + File.separator;
//                FILE_DIR_APP_LOG = context.getExternalFilesDir("log").getAbsolutePath() + File.separator;
                FILE_DIR_APP_LOG = ALL_LOG_PATH + "log" + File.separator;
                Log.d(TAG, "initExtDir: " + FILE_DIR_APP_LOG);
                File f = new File(FILE_DIR_APP_LOG);
                if (!f.exists() || !f.isDirectory()) {
                    f.mkdirs();
                }

//                FILE_DIR_APP_CRASH = context.getExternalFilesDir("crash").getAbsolutePath() + File.separator;
                FILE_DIR_APP_CRASH = ALL_LOG_PATH + "crash" + File.separator;
                File f1 = new File(FILE_DIR_APP_CRASH);
                if (!f1.exists() || !f1.isDirectory()) {
                    f1.mkdirs();
                }

                handleExpireLog();
            }
        } else {
            Log.d(TAG, "initExtDir: context is null ");
        }

    }

    public static boolean delDir(File file, boolean saveDir) {
        boolean state = false;
        if (null != file && file.exists()) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files != null) {
                    File[] arr$ = files;
                    int len$ = files.length;

                    for (int i$ = 0; i$ < len$; ++i$) {
                        File tmp = arr$[i$];
                        state &= delDir(tmp, saveDir);
                    }
                }

                if (!saveDir) {
                    state = file.delete();
                }
            } else {
                state = !file.exists() || file.delete();
            }

            return state;
        } else {
            return true;
        }
    }

//    public static String getStorageDir() {
//        return sExternalPath;
//    }

//    public static String getAppDir() {
//        return FILE_DIR_APP;
//    }

    public static String getLogDir() {
//        Log.d(TAG, "--------------" + FILE_DIR_APP_LOG);
        return FILE_DIR_APP_LOG;
    }

    public static String getCrashDir() {
//        Log.d(TAG, "getCrashDir: FILE_DIR_APP_CRASH :"+FILE_DIR_APP_CRASH);
        return FILE_DIR_APP_CRASH;
    }

    public static String getAllLogDir() {
        return ALL_LOG_PATH;
    }

    private static void handleExpireLog() {
        String logDir = getLogDir();
        if (null != logDir) {
            File f = new File(logDir);
            if (f.exists() && f.isDirectory()) {
                File[] files = f.listFiles();
                File[] arr$ = files;
                int len$ = files.length;

                for (int i$ = 0; i$ < len$; ++i$) {
                    File file = arr$[i$];
                    String name = file.getName();
                    if (null != name && 8 == name.length()) {
                        long time = StringUtils.parseDate(name, "yyyyMMdd");
                        if (0L == time || System.currentTimeMillis() - time >= LogUtils.MAX_SAVE_DAYS * 86400000L) {
                            file.delete();
                        }
                    } else {
                        file.delete();
                    }
                }
            }

        }
    }

    public static boolean delFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }

        return flag;
    }
}