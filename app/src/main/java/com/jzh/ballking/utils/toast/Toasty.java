package com.jzh.ballking.utils.toast;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.CheckResult;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jzh.ballking.R;


/**
 * Author:jzh
 * desc:自定义全局使用的toast
 * Date:2018/06/19 16:22
 * Email:jzh970611@163.com
 * company:畅盈科技
 */
@SuppressLint({"InflateParams"})
public class Toasty {
    @ColorInt
    private static int DEFAULT_TEXT_COLOR = Color.parseColor("#FFFFFF");
    @ColorInt
    private static int ERROR_COLOR = Color.parseColor("#D50000");
    @ColorInt
    private static int INFO_COLOR = Color.parseColor("#3F51B5");
    @ColorInt
    private static int SUCCESS_COLOR = Color.parseColor("#388E3C");
    @ColorInt
    private static int WARNING_COLOR = Color.parseColor("#FFA900");
    @ColorInt
    private static int NORMAL_COLOR = Color.parseColor("#353A3E");
    private static final Typeface LOADED_TOAST_TYPEFACE = Typeface.create("sans-serif-condensed", 0);
    private static Typeface currentTypeface;
    private static int textSize;
    private static boolean tintIcon;

    private Toasty() {
    }

    @CheckResult
    public static Toast normal(@NonNull Context context, @NonNull CharSequence message) {
        return normal(context, message, 0, (Drawable) null, false);
    }

    @CheckResult
    public static Toast normal(@NonNull Context context, @NonNull CharSequence message, Drawable icon) {
        return normal(context, message, 0, icon, true);
    }

    @CheckResult
    public static Toast normal(@NonNull Context context, @NonNull CharSequence message, int duration) {
        return normal(context, message, duration, (Drawable) null, false);
    }

    @CheckResult
    public static Toast normal(@NonNull Context context, @NonNull CharSequence message, int duration, Drawable icon) {
        return normal(context, message, duration, icon, true);
    }

    @CheckResult
    public static Toast normal(@NonNull Context context, @NonNull CharSequence message, int duration, Drawable icon, boolean withIcon) {
        return custom(context, message, icon, NORMAL_COLOR, duration, withIcon, true);
    }

    @CheckResult
    public static Toast warning(@NonNull Context context, @NonNull CharSequence message) {
        return warning(context, message, 0, true);
    }

    @CheckResult
    public static Toast warning(@NonNull Context context, @NonNull CharSequence message, int duration) {
        return warning(context, message, duration, true);
    }

    @CheckResult
    public static Toast warning(@NonNull Context context, @NonNull CharSequence message, int duration, boolean withIcon) {
        return custom(context, message, ToastyUtils.getDrawable(context, R.drawable.ic_error_outline_white_48dp), WARNING_COLOR, duration, withIcon, true);
    }

    @CheckResult
    public static Toast info(@NonNull Context context, @NonNull CharSequence message) {
        return info(context, message, 0, true);
    }

    @CheckResult
    public static Toast info(@NonNull Context context, @NonNull CharSequence message, int duration) {
        return info(context, message, duration, true);
    }

    @CheckResult
    public static Toast info(@NonNull Context context, @NonNull CharSequence message, int duration, boolean withIcon) {
        return custom(context, message, ToastyUtils.getDrawable(context, R.drawable.ic_info_outline_white_48dp), INFO_COLOR, duration, withIcon, true);
    }

    @CheckResult
    public static Toast success(@NonNull Context context, @NonNull CharSequence message) {
        return success(context, message, 0, true);
    }

    @CheckResult
    public static Toast success(@NonNull Context context, @NonNull CharSequence message, int duration) {
        return success(context, message, duration, true);
    }

    @CheckResult
    public static Toast success(@NonNull Context context, @NonNull CharSequence message, int duration, boolean withIcon) {
        return custom(context, message, ToastyUtils.getDrawable(context, R.drawable.ic_check_white_48dp), SUCCESS_COLOR, duration, withIcon, true);
    }

    @CheckResult
    public static Toast error(@NonNull Context context, @NonNull CharSequence message) {
        return error(context, message, 0, true);
    }

    @CheckResult
    public static Toast error(@NonNull Context context, @NonNull CharSequence message, int duration) {
        return error(context, message, duration, true);
    }

    @CheckResult
    public static Toast error(@NonNull Context context, @NonNull CharSequence message, int duration, boolean withIcon) {
        return custom(context, message, ToastyUtils.getDrawable(context, R.drawable.ic_clear_white_48dp), ERROR_COLOR, duration, withIcon, true);
    }

    @CheckResult
    public static Toast custom(@NonNull Context context, @NonNull CharSequence message, Drawable icon, int duration, boolean withIcon) {
        return custom(context, message, icon, -1, duration, withIcon, false);
    }

    @CheckResult
    public static Toast custom(@NonNull Context context, @NonNull CharSequence message, @DrawableRes int iconRes, @ColorInt int tintColor, int duration, boolean withIcon, boolean shouldTint) {
        return custom(context, message, ToastyUtils.getDrawable(context, iconRes), tintColor, duration, withIcon, shouldTint);
    }

    @SuppressLint({"ShowToast"})
    @CheckResult
    public static Toast custom(@NonNull Context context, @NonNull CharSequence message, Drawable icon, @ColorInt int tintColor, int duration, boolean withIcon, boolean shouldTint) {
        Toast currentToast = Toast.makeText(context, "", duration);
        currentToast.setGravity(Gravity.CENTER, 0, 0);
        View toastLayout = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.toast_layout, (ViewGroup) null);
        ImageView toastIcon = (ImageView) toastLayout.findViewById(R.id.toast_icon);
        TextView toastTextView = (TextView) toastLayout.findViewById(R.id.toast_text);
        Drawable drawableFrame;
        if (shouldTint) {
            drawableFrame = ToastyUtils.tint9PatchDrawableFrame(context, tintColor);
        } else {
            drawableFrame = ToastyUtils.getDrawable(context, R.drawable.toast_frame);
        }

        ToastyUtils.setBackground(toastLayout, drawableFrame);
        if (withIcon) {
            if (icon == null) {
                throw new IllegalArgumentException("Avoid passing \'icon\' as null if \'withIcon\' is set to true");
            }

            if (tintIcon) {
                icon = ToastyUtils.tintIcon(icon, DEFAULT_TEXT_COLOR);
            }

            ToastyUtils.setBackground(toastIcon, icon);
        } else {
            toastIcon.setVisibility(View.GONE);
        }

        toastTextView.setText(message);
        toastTextView.setTextColor(DEFAULT_TEXT_COLOR);
        toastTextView.setTypeface(currentTypeface);
        toastTextView.setTextSize(2, (float) textSize);
        currentToast.setView(toastLayout);
        return currentToast;
    }

    static {
        currentTypeface = LOADED_TOAST_TYPEFACE;
        textSize = 16;
        tintIcon = true;
    }

    public static class Config {
        @ColorInt
        private int DEFAULT_TEXT_COLOR;
        @ColorInt
        private int ERROR_COLOR;
        @ColorInt
        private int INFO_COLOR;
        @ColorInt
        private int SUCCESS_COLOR;
        @ColorInt
        private int WARNING_COLOR;
        private Typeface typeface;
        private int textSize;
        private boolean tintIcon;

        private Config() {
            this.DEFAULT_TEXT_COLOR = Toasty.DEFAULT_TEXT_COLOR;
            this.ERROR_COLOR = Toasty.ERROR_COLOR;
            this.INFO_COLOR = Toasty.INFO_COLOR;
            this.SUCCESS_COLOR = Toasty.SUCCESS_COLOR;
            this.WARNING_COLOR = Toasty.WARNING_COLOR;
            this.typeface = Toasty.currentTypeface;
            this.textSize = Toasty.textSize;
            this.tintIcon = Toasty.tintIcon;
        }

        @CheckResult
        public static Config getInstance() {
            return new Config();
        }

        public static void reset() {
            Toasty.DEFAULT_TEXT_COLOR = Color.parseColor("#FFFFFF");
            Toasty.ERROR_COLOR = Color.parseColor("#D50000");
            Toasty.INFO_COLOR = Color.parseColor("#3F51B5");
            Toasty.SUCCESS_COLOR = Color.parseColor("#388E3C");
            Toasty.WARNING_COLOR = Color.parseColor("#FFA900");
            Toasty.currentTypeface = Toasty.LOADED_TOAST_TYPEFACE;
            Toasty.textSize = 16;
            Toasty.tintIcon = true;
        }

        @CheckResult
        public Config setTextColor(@ColorInt int textColor) {
            this.DEFAULT_TEXT_COLOR = textColor;
            return this;
        }

        @CheckResult
        public Config setErrorColor(@ColorInt int errorColor) {
            this.ERROR_COLOR = errorColor;
            return this;
        }

        @CheckResult
        public Config setInfoColor(@ColorInt int infoColor) {
            this.INFO_COLOR = infoColor;
            return this;
        }

        @CheckResult
        public Config setSuccessColor(@ColorInt int successColor) {
            this.SUCCESS_COLOR = successColor;
            return this;
        }

        @CheckResult
        public Config setWarningColor(@ColorInt int warningColor) {
            this.WARNING_COLOR = warningColor;
            return this;
        }

        @CheckResult
        public Config setToastTypeface(@NonNull Typeface typeface) {
            this.typeface = typeface;
            return this;
        }

        @CheckResult
        public Config setTextSize(int sizeInSp) {
            this.textSize = sizeInSp;
            return this;
        }

        @CheckResult
        public Config tintIcon(boolean tintIcon) {
            this.tintIcon = tintIcon;
            return this;
        }

        public void apply() {
            Toasty.DEFAULT_TEXT_COLOR = this.DEFAULT_TEXT_COLOR;
            Toasty.ERROR_COLOR = this.ERROR_COLOR;
            Toasty.INFO_COLOR = this.INFO_COLOR;
            Toasty.SUCCESS_COLOR = this.SUCCESS_COLOR;
            Toasty.WARNING_COLOR = this.WARNING_COLOR;
            Toasty.currentTypeface = this.typeface;
            Toasty.textSize = this.textSize;
            Toasty.tintIcon = this.tintIcon;
        }
    }
}
