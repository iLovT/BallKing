package com.jzh.ballking.utils.toast;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.view.View;

import com.jzh.ballking.R;


/**
 * Author:jzh
 * desc:ToastyUtils
 * Date:2018/06/19 16:19
 * Email:jzh970611@163.com
 */

final class ToastyUtils {
    private ToastyUtils() {
    }

    static Drawable tintIcon(@NonNull Drawable drawable, @ColorInt int tintColor) {
        drawable.setColorFilter(tintColor, PorterDuff.Mode.SRC_IN);
        return drawable;
    }

    static Drawable tint9PatchDrawableFrame(@NonNull Context context, @ColorInt int tintColor) {
        NinePatchDrawable toastDrawable = (NinePatchDrawable) getDrawable(context, R.drawable.toast_frame);
        return tintIcon(toastDrawable, tintColor);
    }

    static void setBackground(@NonNull View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= 16) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }

    }

    static Drawable getDrawable(@NonNull Context context, @DrawableRes int id) {
        return Build.VERSION.SDK_INT >= 21 ? context.getDrawable(id) : context.getResources().getDrawable(id);
    }
}

