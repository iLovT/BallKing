package com.jzh.ballking.ui.game;

import android.os.Build;
import android.view.View;
import android.view.WindowManager;


import com.jzh.ballking.R;
import com.jzh.ballking.ui.base.BaseActivity;

import butterknife.ButterKnife;


/**
 * Author:jzh
 * desc:游戏主界面
 * Date:2018/06/23 11:18
 * Email:jzh970611@163.com
 */

public class GameActivity extends BaseActivity implements View.OnSystemUiVisibilityChangeListener {

    @Override
    protected int getLayoutId() {
        return R.layout.ac_game;
    }

    @Override
    protected void initWidget() {
        setUnBinder(ButterKnife.bind(this));
        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(this);
    }

    @Override
    protected void beForSet() {
        super.beForSet();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
//        hideBottomUIMenu();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MySurfaceView.point_accout = 3;
    }

    @Override
    public void onSystemUiVisibilityChange(int visibility) {
//        if (visibility == View.VISIBLE)
//            hideBottomUIMenu();
    }

    /**
     * 隐藏虚拟按键，并且全屏
     */
    protected void hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }
}
