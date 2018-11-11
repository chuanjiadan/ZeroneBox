package com.zerone.mlog;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;

public class LogUtils {

    public static String TAG = "MLog_LogUtils";
    //    public static final String MODULE_SDK = "logsdk";
    private static boolean sLogable = true;
    private static boolean LOGV_ON;
    private static boolean LOGD_ON;
    private static boolean LOGI_ON;
    private static boolean LOGE_ON;
    private static boolean LOGW_ON;
    private static boolean LOGWTF_ON;
    private static boolean sSaveable;
    private static String sLogLock;
    private String mModuleTag;
    public static long MAX_SAVE_DAYS = 3L;//默认存储近三天的log
    public static long MAX_FILE_CAPACITY = 5L;//默认每天log文件的大小为5M
    private static Context mContext;
    private static SharedPreferences mLogSdk;
    private static final String mLogV = "logV";
    private static final String mLogD = "logD";
    private static final String mLogI = "logI";
    private static final String mLogE = "logE";
    private static final String mFiles = "files";
    private static final String mFileSize = "size";
    private static final String mSaveable = "Saveable";


    private LogUtils(String module, String className) {
        this.mModuleTag = "[" + module + "][" + className + "]:";
        TAG = "[" + className + "]:";
        if (mContext != null) {
            mLogSdk = mContext.getSharedPreferences("log_sdk", Context.MODE_PRIVATE);
            LOGV_ON = mLogSdk.getBoolean(mLogV, false);
            LOGD_ON = mLogSdk.getBoolean(mLogD, false);
            LOGI_ON = mLogSdk.getBoolean(mLogI, false);
            LOGE_ON = mLogSdk.getBoolean(mLogE, false);
            sSaveable = mLogSdk.getBoolean(mSaveable, false);
            MAX_SAVE_DAYS = mLogSdk.getLong(mFiles, MAX_SAVE_DAYS);
            MAX_FILE_CAPACITY = mLogSdk.getLong(mFileSize, MAX_FILE_CAPACITY);
        } else {
            Log.d(TAG, "LogUtils: mContext is null");
        }


    }

    public static LogUtils getInstance(String className) {
        if (TextUtils.isEmpty(className)) {
            throw new IllegalArgumentException();
        } else {
            return new LogUtils("spacebridge", className);
        }
    }

    /**
     * log初始化
     *
     * @param module 输出log的模块
     * @param tag    筛选tag，建议是类名，可快速定位
     * @return
     */
    public static LogUtils getInstance(String module, String tag) {
        if (!TextUtils.isEmpty(module) && !TextUtils.isEmpty(tag)) {
            return new LogUtils(module, tag);
        } else {
            throw new IllegalArgumentException();
        }

    }

    /**
     * 初始化设置
     *
     * @param context
     * @param days    保存天数
     * @param size    文件大小 MB
     * @param isSave  是否保存文件
     * @param logOpen 是否输出log
     */
    public static void init(Context context, long days, long size, boolean isSave, boolean logOpen) {
        Log.d(TAG, "init:log初始化设置 ");
        mContext = context;
        if (mContext != null) {
            StorageUtils.initExtDir(mContext);
            CrashHandler catchExcep = CrashHandler.getInstance();
            catchExcep.init(context);
            SharedPreferences log_sdk = mContext.getSharedPreferences("log_sdk", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = log_sdk.edit();
            edit.putLong(mFiles, days);
            edit.putLong(mFileSize, size);
            edit.putBoolean(mLogV, isSave ? isSave : logOpen);
            edit.putBoolean(mLogD, isSave ? isSave : logOpen);
            edit.putBoolean(mLogI, isSave ? isSave : logOpen);
            edit.putBoolean(mLogE, isSave ? isSave : logOpen);
            edit.putBoolean(mSaveable, isSave);
            edit.commit();
        }

    }

    public void v(String msg) {
        if (LOGV_ON) {
            Log.v(TAG, msg);
            msg = this.mModuleTag + msg;
            if (sSaveable) {
                this.log2File(msg);
            }
        }

    }

    public void v(String msg, Throwable tr) {
        if (LOGV_ON) {
            Log.v(TAG, msg, tr);
            msg = this.mModuleTag + msg;
            if (sSaveable) {
                if (null != tr) {
                    this.log2File(msg + "\n" + tr.getMessage());
                } else {
                    this.log2File(msg);
                }
            }
        }

    }

    public void d(String msg) {
        Log.d(TAG, "d:LOGD_ON " + LOGD_ON);
        if (LOGD_ON) {
            Log.d(TAG, msg);
            msg = this.mModuleTag + msg;
            Log.d(TAG, "d:sSaveable " + sSaveable);
            if (sSaveable) {
                this.log2File(msg);
            }
        }

    }

    public void d(String msg, Throwable tr) {
        if (LOGD_ON) {
            Log.d(TAG, msg, tr);
            msg = this.mModuleTag + msg;
            if (sSaveable) {
                if (null != tr) {
                    this.log2File(msg + "\n" + tr.getMessage());
                } else {
                    this.log2File(msg);
                }
            }
        }

    }

    public void i(String msg) {
        if (LOGI_ON) {
            Log.i(TAG, msg);
            msg = this.mModuleTag + msg;
            if (sSaveable) {
                this.log2File(msg);
            }
        }

    }

    public void i(String msg, Throwable tr) {
        if (LOGI_ON) {
            Log.i(TAG, msg, tr);
            msg = this.mModuleTag + msg;
            if (sSaveable) {
                if (null != tr) {
                    this.log2File(msg + "\n" + tr.getMessage());
                } else {
                    this.log2File(msg);
                }
            }
        }

    }

    private void w(String msg) {
        if (LOGW_ON) {
            Log.w(TAG, msg);
            msg = this.mModuleTag + msg;
            if (sSaveable) {
                this.log2File(msg);
            }
        }

    }

    private void w(Throwable tr) {
        if (LOGW_ON) {
            Log.w(TAG, this.mModuleTag, tr);
            if (null != tr && sSaveable) {
                this.log2File(this.mModuleTag + "\n" + tr.getMessage());
            }
        }

    }

    private void w(String msg, Throwable tr) {
        if (LOGW_ON) {
            Log.w(TAG, msg, tr);
            msg = this.mModuleTag + msg;
            if (sSaveable) {
                if (null != tr) {
                    this.log2File(msg + "\n" + tr.getMessage());
                } else {
                    this.log2File(msg);
                }
            }
        }

    }

    //暂时不提供调用
    private void wtf(String msg) {
        if (LOGWTF_ON) {
            Log.wtf(TAG, msg);
            msg = this.mModuleTag + msg;
            if (sSaveable) {
                this.log2File(msg);
            }
        }

    }

    //暂时不提供调用
    private void wtf(Throwable tr) {
        if (LOGWTF_ON) {
            Log.wtf(TAG, this.mModuleTag, tr);
            if (null != tr && sSaveable) {
                this.log2File(this.mModuleTag + "\n" + tr.getMessage());
            }
        }

    }

    //暂时不提供调用
    private void wtf(String msg, Throwable tr) {
        if (LOGWTF_ON) {
            Log.wtf(TAG, msg, tr);
            msg = this.mModuleTag + msg;
            if (sSaveable) {
                if (null != tr) {
                    this.log2File(msg + "\n" + tr.getMessage());
                } else {
                    this.log2File(msg);
                }
            }
        }

    }

    public void e(String msg) {
        if (LOGE_ON) {
            Log.e(TAG, msg);
            msg = this.mModuleTag + msg;
            if (sSaveable) {
                this.log2File(msg);
            }
        }

    }

    public void e(Throwable tr) {
        if (LOGE_ON) {
            Log.e(TAG, this.mModuleTag, tr);
            if (null != tr && sSaveable) {
                this.log2File(this.mModuleTag + "\n" + tr.getMessage());
            }
        }

    }

    public void e(String msg, Throwable tr) {
        if (LOGE_ON) {
            Log.e(TAG, msg, tr);
            msg = this.mModuleTag + msg;
            if (sSaveable) {
                if (null != tr) {
                    this.log2File(msg + "\n" + tr.getMessage());
                } else {
                    this.log2File(msg);
                }
            }
        }

    }

    public static void setTag(String tag) {
        TAG = tag;
    }

    public static boolean isLogable() {
        return sLogable;
    }

    public static void setLogable(boolean enable) {
        sLogable = enable;
    }

    /**
     * true为log存储文件开启
     *
     * @return
     */
    public static boolean isSaveable() {
        return sSaveable;
    }

    /**
     * 设置log存储开关
     *
     * @param enable false 为关闭文件保存log
     */
    protected static void setSaveable(boolean enable) {
        sSaveable = enable;
    }

    public void log2File(String log) {
        Log.d(TAG, "log2File sLogLock: " + sLogLock);
        String var2 = sLogLock;

        synchronized (sLogLock) {
            String path = StorageUtils.getLogDir();
            Log.d(TAG, "log2File path: " + path);
            if (null != path) {
                String file = StringUtils.formatDate(System.currentTimeMillis(), "yyyyMMdd");
                File f = new File(path + file);
                FileUtils.saveFile(log, f);
            }
        }
    }

    /**
     * 设置敏感log信息开关
     *
     * @param isOpen
     */
    public void setLogSwitch(boolean isOpen) {
        SharedPreferences.Editor edit = mLogSdk.edit();
//        edit.putBoolean(mLogV, isOpen);
        edit.putBoolean(mLogD, isOpen);
//        edit.putBoolean(mLogI, isOpen);
//        edit.putBoolean(mLogE, isOpen);
//        edit.putBoolean(mSaveable, isOpen);
        edit.commit();
//        sSaveable = isOpen;
//        LOGV_ON = isOpen;
        LOGD_ON = isOpen;
//        LOGI_ON = isOpen;
//        LOGE_ON = isOpen;

    }

    /**
     * 设置保存log的天数
     *
     * @param days
     */
    private void setMaxSaveFiles(long days) {
        if (days > 0) {
            MAX_SAVE_DAYS = days;
        }
    }

    /**
     * 设置每天log存储文件的最大空间 单位：MB
     *
     * @param mb
     */
    private void setFileCapacity(long mb) {
        if (mb > 0) {
            MAX_FILE_CAPACITY = mb;
        }
    }


    static {
        sLogLock = "";
    }
}
