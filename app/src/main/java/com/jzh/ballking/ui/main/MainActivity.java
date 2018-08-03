package com.jzh.ballking.ui.main;


import com.jzh.ballking.R;
import com.jzh.ballking.ui.base.BaseActivity;
import com.jzh.ballking.ui.game.GameActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initWidget() {
        setUnBinder(ButterKnife.bind(this));
    }


    @OnClick(R.id.begain_game)
    public void onViewClicked() {
        goActivity(GameActivity.class);
    }
}
