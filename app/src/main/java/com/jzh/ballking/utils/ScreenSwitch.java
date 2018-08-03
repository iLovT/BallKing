package com.jzh.ballking.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.jzh.ballking.R;


/**
 * Author:jzh
 * desc: Activity界面跳转
 * Date:2018/06/19 16:11
 * Email:jzh970611@163.com
 */

public class ScreenSwitch {

    public static void switchActivity(Context context, Class<?> descClass, Bundle bundle, int requestCode) {
        Class<?> mClass = context.getClass();
        if (mClass == descClass) {
            return;
        }
        try {
            Intent intent = new Intent();
            intent.setClass(context, descClass);
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            ((Activity) context).startActivityForResult(intent, requestCode);
            ((Activity) context).overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        } catch (Exception e) {
            AppLogger.e("TAG", e.getMessage());
        }
    }

    public static void finish(Activity context) {
        context.finish();
        context.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

}
