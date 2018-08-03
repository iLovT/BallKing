package com.jzh.ballking;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import java.util.HashSet;
import java.util.Set;


/**
 * Author:jzh
 * desc: 全局application
 * Date:2018/06/19 15:24
 * Email:jzh970611@163.com
 * company:502
 */

public class MyApp extends Application {
    private Set<Activity> allActivities;
    public static MyApp instance;

    /**
     * 解决方法数超出限制，在5.0以下分包操作
     *
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static synchronized MyApp getInstance() {
        return instance;
    }

    /**
     * 添加activity
     */
    public void addActivity(Activity act) {
        if (allActivities == null) {
            allActivities = new HashSet<>();
        }
        allActivities.add(act);
    }

    /**
     * 移除activity
     */
    public void removeActivity(Activity act) {
        if (allActivities != null) {
            allActivities.remove(act);
        }
    }

    // 退出
    public void exit() {
        for (Activity activity : allActivities) {
            if (activity != null) {
                activity.finish();
            }
        }

    }
}
