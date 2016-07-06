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

import java.util.List;

import static com.caomei.cvseller.R.id.rl_fragment_container;

/**
 * Created by Wang Xiaojian on 2016/6/30.
 */
public class MainActivity extends BaseFragmentActivity {

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
        fragContainer = (RelativeLayout) findViewById(R.id.rl_fragment_container);

        panelTask = (LinearLayout) findViewById(R.id.ll_panel_task);
        panelOper = (LinearLayout) findViewById(R.id.ll_panel_operation);
        panelSpread = (LinearLayout) findViewById(R.id.ll_panel_spread);
        panelUser = (LinearLayout) findViewById(R.id.ll_panel_user);

    }

    @Override
    public void setData() {
        clearFragments();
        initFragments();
        mListener=new CommonListener();
        panelOper.setOnClickListener(mListener);
        panelTask.setOnClickListener(mListener);
        panelSpread.setOnClickListener(mListener);
        panelUser.setOnClickListener(mListener);
    }
    private void clearFragments() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment f : fragments) {
                if (f != null) {
                    getSupportFragmentManager().beginTransaction().remove(f)
                            .commit();
                }
            }
        }
    }
    private void initFragments() {
        fm = getSupportFragmentManager();
        mFragmentTask = new FragmentTask();
        ft = fm.beginTransaction();
        curFragment = mFragmentTask;
        ft.add(R.id.rl_fragment_container, mFragmentTask);
        ft.show(mFragmentTask);
        ft.commit();
    }

    @Override
    public void onEventMainThread(EventMsg msg) {
        super.onEventMainThread(msg);
    }

    class CommonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            switch (v.getId()) {

                case R.id.ll_panel_task:
                    ft = fm.beginTransaction();
                    if (curFragment != null) {
                        ft.hide(curFragment);
                    }
                    if (mFragmentTask == null) {
                        mFragmentTask = new FragmentTask();
                    }
                    ft.show(mFragmentTask);
                    curFragment = mFragmentTask;
                    ft.commitAllowingStateLoss();
                    resetTabView();
                    break;
                case R.id.ll_panel_operation:
                    ft = fm.beginTransaction();
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
                    ft = fm.beginTransaction();
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
                    ft = fm.beginTransaction();
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
        }
    }

    private void resetTabView() {


    }
}
