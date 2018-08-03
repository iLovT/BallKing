package com.jzh.ballking.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;


import com.jzh.ballking.MyApp;
import com.jzh.ballking.R;
import com.jzh.ballking.utils.ScreenSwitch;
import com.jzh.ballking.utils.toast.Toasty;

import butterknife.Unbinder;

/**
 * Author:jzh
 * desc: activity基类
 * Date:2018/06/19 16:11
 * Email:jzh970611@163.com
 */

public abstract class BaseActivity extends AppCompatActivity implements MvpView {

    private Unbinder mUnBinder;

    /**
     * @return 布局id
     */
    protected abstract int getLayoutId();


    /**
     * 初始化控件
     */
    protected abstract void initWidget();


    /**
     * 设置屏幕全屏等初始化操作
     */
    protected void beForSet() {

    }

    public void setUnBinder(Unbinder unBinder) {
        mUnBinder = unBinder;
    }

    protected void initWidget(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        beForSet();
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        }
        initWidget();
        initWidget(savedInstanceState);
        MyApp.getInstance().addActivity(this);
    }


    @Override
    public void onToast(int resId) {
        onToast(getString(resId));
    }

    @Override
    public void onToast(String message) {
        showSnackBar(message == null ? getString(R.string.some_error) : message, 1);
    }

    @Override
    public void onToastSucc(String message) {
        showSnackBar(message == null ? getString(R.string.some_error) : message, 1);
    }

    @Override
    public void onToastSucc(@StringRes int resId) {
        onToastSucc(getString(resId));
    }

    @Override
    public void onToastFail(String message) {
        showSnackBar(message == null ? getString(R.string.some_error) : message, 2);
    }

    @Override
    public void onToastFail(@StringRes int resId) {
        onToastFail(getString(resId));
    }

    @Override
    public void onToastInfo(String message) {
        showSnackBar(message == null ? getString(R.string.some_error) : message, 3);
    }

    @Override
    public void onToastInfo(@StringRes int resId) {
        onToastInfo(getString(resId));
    }

    @Override
    public void onToastWarn(String message) {
        showSnackBar(message == null ? getString(R.string.some_error) : message, 4);
    }

    @Override
    public void onToastWarn(@StringRes int resId) {
        onToastWarn(getString(resId));
    }

    @Override
    public void onToastNormal(String message) {
        showSnackBar(message == null ? getString(R.string.some_error) : message, 5);
    }

    @Override
    public void onToastNormal(@StringRes int resId) {
        onToastNormal(getString(resId));
    }

    private Toast toasty;

    /**
     * 显示吐司 如果需要定义其他类型，请自己重新定义。
     *
     * @param message
     * @param type
     */
    private void showSnackBar(String message, int type) {
        switch (type) {
            case 1:
                if (null == toasty) {
                    toasty = Toasty.success(getApplicationContext(), message, Toast.LENGTH_SHORT);
                } else {
                    toasty.cancel();
                    toasty = Toasty.success(getApplicationContext(), message, Toast.LENGTH_SHORT);
                }
                break;
            case 2:
                if (null == toasty) {
                    toasty = Toasty.error(getApplicationContext(), message, Toast.LENGTH_SHORT);
                } else {
                    toasty.cancel();
                    toasty = Toasty.error(getApplicationContext(), message, Toast.LENGTH_SHORT);
                }
                break;
            case 3:
                if (null == toasty) {
                    toasty = Toasty.info(getApplicationContext(), message, Toast.LENGTH_SHORT);
                } else {
                    toasty.cancel();
                    toasty = Toasty.info(getApplicationContext(), message, Toast.LENGTH_SHORT);
                }
                break;
            case 4:
                if (null == toasty) {
                    toasty = Toasty.warning(getApplicationContext(), message, Toast.LENGTH_SHORT);
                } else {
                    toasty.cancel();
                    toasty = Toasty.warning(getApplicationContext(), message, Toast.LENGTH_SHORT);
                }
                break;
            case 5:
                if (null == toasty) {
                    toasty = Toasty.normal(getApplicationContext(), message, Toast.LENGTH_SHORT);
                } else {
                    toasty.cancel();
                    toasty = Toasty.normal(getApplicationContext(), message, Toast.LENGTH_SHORT);
                }

                break;
        }
        if (null != toasty)
            toasty.show();
    }

    //关闭软键盘
    @Override
    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    @Override
    protected void onDestroy() {
        MyApp.getInstance().removeActivity(this);
        super.onDestroy();
        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
    }


    // 跳转
    public void goActivityForResult(Class<?> descClass, int requestCode) {
        ScreenSwitch.switchActivity(this, descClass, null, requestCode);
    }

    public void goActivityForResult(Class<?> descClass, Bundle bundle, int requestCode) {
        ScreenSwitch.switchActivity(this, descClass, bundle, requestCode);
    }

    public void goActivity(Class<?> descClass, Bundle bundle) {
        goActivityForResult(descClass, bundle, 0);
    }

    public void goActivity(Class<?> descClass) {
        goActivity(descClass, null);
    }

}
