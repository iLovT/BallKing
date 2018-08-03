package com.jzh.ballking.ui.base;

import android.support.annotation.StringRes;

/**
 * Author:jzh
 * desc: view 控件基本功能接口，服务于界面
 * Date:2018/06/19 16:11
 * Email:jzh970611@163.com
 */

public interface MvpView {


    void onToast(@StringRes int resId);

    void onToast(String message);

    void onToastSucc(String message);

    void onToastSucc(@StringRes int resId);

    void onToastFail(String message);

    void onToastFail(@StringRes int resId);

    void onToastInfo(String message);

    void onToastInfo(@StringRes int resId);

    void onToastWarn(String message);

    void onToastWarn(@StringRes int resId);

    void onToastNormal(String message);

    void onToastNormal(@StringRes int resId);


    /**
     * 隐藏软键盘
     */
    void hideKeyboard();

}
