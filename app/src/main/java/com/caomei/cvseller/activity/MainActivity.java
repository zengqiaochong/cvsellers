package com.caomei.cvseller.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.caomei.cvseller.R;
import com.caomei.cvseller.eventbus.EventMsg;
import com.caomei.cvseller.fragment.BaseFragment;
import com.caomei.cvseller.fragment.FragmentOperation;
import com.caomei.cvseller.fragment.FragmentSpread;
import com.caomei.cvseller.fragment.FragmentTask;
import com.caomei.cvseller.fragment.FragmentUser;

import static com.caomei.cvseller.R.id.rl_fragment_container;

/**
 * Created by Wang Xiaojian on 2016/6/30.
 */
public class MainActivity extends BaseFragmentActivity{

    private FragmentManager fm;
    private FragmentTransaction ft;
    private BaseFragment curFragment;
    private FragmentTask mFragmentTask;
    private FragmentOperation mFragmentOper;
    private FragmentSpread mFragmentSpread;
    private FragmentUser mFragmentUser;

    private RelativeLayout fragContainer;
    private LinearLayout panelTask;
    private LinearLayout panelOper;
    private LinearLayout panelSpread;
    private LinearLayout panelUser;
    private CommonListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setView();
        setData();
    }
    @Override
    public void setView() {
        fragContainer=(RelativeLayout)findViewById(R.id.rl_fragment_container);

        panelTask=(LinearLayout)findViewById(R.id.ll_panel_task);
        panelOper=(LinearLayout)findViewById(R.id.ll_panel_operation);
        panelSpread=(LinearLayout)findViewById(R.id.ll_panel_spread);
        panelUser=(LinearLayout)findViewById(R.id.ll_panel_user);

    }

    @Override
    public void setData() {
        initFragments();
        panelOper.setOnClickListener(mListener);
        panelTask.setOnClickListener(mListener);
        panelSpread.setOnClickListener(mListener);
        panelUser.setOnClickListener(mListener);
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
    public void onEventMainThread(EventMsg msg) {
        super.onEventMainThread(msg);
    }

    class CommonListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            ft = fm.beginTransaction();
            switch (v.getId()){
                case R.id.ll_panel_task:
                    if (curFragment != null) {
                        ft.hide(curFragment);
                    }
                    if (mFragmentTask == null) {
                        mFragmentTask = new FragmentTask();
                    }
                    curFragment = mFragmentTask;
                    ft.show(mFragmentTask);
                    ft.commitAllowingStateLoss();
                    resetTabView();
                    break;
                case R.id.ll_panel_operation:
                    if (curFragment != null) {
                        ft.hide(curFragment);
                    }
                    if (mFragmentOper == null) {
                        mFragmentOper = new FragmentOperation();
                    }
                    curFragment = mFragmentOper;
                    ft.show(mFragmentOper);
                    ft.commitAllowingStateLoss();
                    resetTabView();
                    break;
                case R.id.ll_panel_spread:
                    if (curFragment != null) {
                        ft.hide(curFragment);
                    }
                    if (mFragmentSpread == null) {
                        mFragmentSpread = new FragmentSpread();
                    }
                    curFragment = mFragmentSpread;
                    ft.show(mFragmentSpread);
                    ft.commitAllowingStateLoss();
                    resetTabView();
                    break;
                case R.id.ll_panel_user:
                    if (curFragment != null) {
                        ft.hide(curFragment);
                    }
                    if (mFragmentUser == null) {
                        mFragmentUser = new FragmentUser();
                    }
                    curFragment = mFragmentUser;
                    ft.show(mFragmentUser);
                    ft.commitAllowingStateLoss();
                    resetTabView();
                    break;
            }
            ft.commit();
        }
    }

    private void resetTabView() {

    }
}
