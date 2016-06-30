package com.caomei.cvseller.activity;

import android.os.Bundle;

import com.caomei.cvseller.R;
import com.caomei.cvseller.eventbus.EventMsg;

/**
 * Created by Wang Xiaojian on 2016/6/30.
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setView();
        setData();

    }

    @Override
    public void onEventMainThread(EventMsg msg) {
        super.onEventMainThread(msg);
    }

    @Override
    public void setView() {

    }

    @Override
    public void setData() {

    }
}
