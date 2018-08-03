package com.jzh.ballking.utils;

import android.util.Log;

import com.jzh.ballking.BuildConfig;


/**
 * Author:jzh
 * desc: Log utils
 * Date:2018/06/19 16:11
 * Email:jzh970611@163.com
 */

public class AppLogger {

    public static void i(String tag, String s) {
        if (BuildConfig.LOG_DEBUG) {
            Log.i(tag, s);
        }
    }

    public static void e(String tag, String s) {
        if (BuildConfig.LOG_DEBUG) {
            Log.e(tag, s);
        }
    }

    /**
     * 华为荣耀7x机型无法显示log.d
     *
     * @param tag
     * @param s
     */
    public static void d(String tag, String s) {
        if (BuildConfig.LOG_DEBUG) {
            Log.d(tag, s);
        }
    }

    public static void w(String tag, String s) {
        if (BuildConfig.LOG_DEBUG) {
            Log.w(tag, s);
        }
    }
}
