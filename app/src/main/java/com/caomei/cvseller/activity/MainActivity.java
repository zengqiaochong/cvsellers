package com.caomei.cvseller.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RelativeLayout;

import com.caomei.cvseller.R;
import com.caomei.cvseller.eventbus.EventMsg;
import com.caomei.cvseller.fragment.BaseFragment;
import com.caomei.cvseller.fragment.FragmentTask;

import static com.caomei.cvseller.R.id.rl_fragment_container;

/**
 * Created by Wang Xiaojian on 2016/6/30.
 */
public class MainActivity extends BaseFragmentActivity{

    private FragmentManager fm;
    private FragmentTransaction ft;
    private BaseFragment curFragment;
    private FragmentTask mFragmentTask;
    private RelativeLayout fragContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setView();
        setData();

    }
    @Override
    public void setData() {
        initFragments();
    }

    private void initFragments() {
        fm = getSupportFragmentManager();
        mFragmentTask = new FragmentTask();
        ft = fm.beginTransaction();
        curFragment = mFragmentTask;
        ft.add(R.id.rl_fragment_container,mFragmentTask);
        ft.show(mFragmentTask);
        ft.commit();
    }
    @Override
    public void setView() {
        fragContainer=(RelativeLayout)findViewById(R.id.rl_fragment_container);

    }

    @Override
    public void onEventMainThread(EventMsg msg) {
        super.onEventMainThread(msg);
    }

}
