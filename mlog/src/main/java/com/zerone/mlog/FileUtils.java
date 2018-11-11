package com.zerone.mlog;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FileUtils {
    private static LogUtils log = LogUtils.getInstance("MLog", "FileUtils");
    private static final long MAX_CAPACITY = 1048576L;

    private static final int CHECK_LOG_FREQUENCY = 100;
    private static int index;

    public FileUtils() {
    }

    public static boolean isFileExist(String fileName) {
        if (fileName != null && fileName.length() != 0) {
            File f = new File(fileName);
            if (f.exists()) {
                return true;
            } else {
                try {
                    return f.createNewFile();
                } catch (IOException var3) {
                    log.e(var3);
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    public static boolean isDirExist(String dir) {
        if (dir != null && dir.length() != 0) {
            File mDic = new File(dir);
            if (mDic.exists()) {
                return true;
            } else {
                boolean mkdirs = mDic.mkdirs();
                return mkdirs;
            }
        } else {
            return false;
        }
    }

    public static void saveFile(String str, File file) {
        FileWriter writer = null;

        try {
            if (index / 100 >= 1) {
                deleteExcessLog();
                index = 0;
            }

            ++index;
            if (!file.exists()) {
                file.createNewFile();
                handleLog();
            }

            writer = new FileWriter(file, true);
            writer.write(formatTime(System.currentTimeMillis()) + "  " + str + "\n");
        } catch (IOException var12) {
            log.e(var12);
        } finally {
            if (null != writer) {
                try {
                    writer.flush();
                    writer.close();
                } catch (IOException var11) {
                    log.e(var11);
                }
            }

        }

    }

    private static void handleLog() {
        String logDir = StorageUtils.getLogDir();
        if (null != logDir) {
            File f = new File(logDir);
            if (f.exists() && f.isDirectory()) {
                File[] files = f.listFiles();
                if (null == files || files.length == 0) {
                    return;
                }

                File[] arr$ = files;
                int len$ = files.length;

                for (int i$ = 0; i$ < len$; ++i$) {
                    File file = arr$[i$];
                    String name = file.getName();
                    if (null != name && 8 == name.length()) {
                        long time = StringUtils.parseDate(name, "yyyyMMdd");
                        log.i("存储天数 : " + LogUtils.MAX_SAVE_DAYS);
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

    private static void deleteExcessLog() {
        String logDir = StorageUtils.getLogDir();
        if (null != logDir) {
            File f = new File(logDir);
            if (f.exists() && f.isDirectory()) {
                File[] files = f.listFiles();
                if (null == files || files.length == 0) {
                    return;
                }

                int earliestLog = 2147483647;
                long logTotalSize = 0L;
                File[] arr$ = files;
                int len$ = files.length;

                for (int i$ = 0; i$ < len$; ++i$) {
                    File file = arr$[i$];
                    String name = file.getName();
                    if (null != name && 8 == name.length()) {
                        int time;
                        try {
                            time = Integer.parseInt(name);
                        } catch (Exception var13) {
                            log.e("name parse int err, name:" + name);
                            time = 0;
                        }

                        if (0 == time) {
                            file.delete();
                        } else {
                            logTotalSize += file.length();
                            if (earliestLog > time) {
                                earliestLog = time;
                            }
                        }
                    } else {
                        file.delete();
                    }
                }

                if (logTotalSize > MAX_CAPACITY * LogUtils.MAX_FILE_CAPACITY) {
                    String fileName = logDir + earliestLog;
                    boolean result = StorageUtils.delFile(fileName);
                    log.e("fileName:" + fileName + ",result:" + result);
                }
            }

        }
    }

    private static String formatTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.CHINA);
        return format.format(new Date(time));
    }
}
