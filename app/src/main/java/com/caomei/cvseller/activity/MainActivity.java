package com.caomei.cvseller.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.caomei.cvseller.R;
import com.caomei.cvseller.eventbus.EventMsg;
import com.caomei.cvseller.fragment.BaseFragment;
import com.caomei.cvseller.fragment.FragmentOperation;
import com.caomei.cvseller.fragment.FragmentSpread;
import com.caomei.cvseller.fragment.FragmentTask;
import com.caomei.cvseller.fragment.FragmentUser;

import java.util.List;

import static com.caomei.cvseller.R.id.ll_panel_operation;
import static com.caomei.cvseller.R.id.ll_panel_task;
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
        mListener = new CommonListener();
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
                    if (mFragmentTask == null) {
                        mFragmentTask = new FragmentTask();
                    }
                    ft.replace(R.id.rl_fragment_container, mFragmentTask);
                    curFragment = mFragmentTask;
                    ft.show(mFragmentSpread);
                    ft.commitAllowingStateLoss();

                    resetTabView();
                    ((ImageView) panelTask.findViewById(R.id.iv_task)).setColorFilter(getResources().getColor(R.color.green));
                    ((TextView) panelTask.findViewById(R.id.tv_task)).setTextColor(getResources().getColor(R.color.green));
                    break;
                case R.id.ll_panel_operation:
                    ft = fm.beginTransaction();
                    if (mFragmentOper == null) {
                        mFragmentOper = new FragmentOperation();
                    }
                    ft.replace(R.id.rl_fragment_container, mFragmentOper);
                    curFragment = mFragmentOper;
                    ft.show(mFragmentOper);
                    ft.commitAllowingStateLoss();

                    resetTabView();

                    ((ImageView) panelOper.findViewById(R.id.iv_operation)).setColorFilter(getResources().getColor(R.color.green));
                    ((TextView) panelOper.findViewById(R.id.tv_operation)).setTextColor(getResources().getColor(R.color.green));
                    break;
                case R.id.ll_panel_spread:
                    ft = fm.beginTransaction();

                    if (mFragmentSpread == null) {
                        mFragmentSpread = new FragmentSpread();
                    }
                    ft.replace(R.id.rl_fragment_container, mFragmentSpread);
                    curFragment = mFragmentSpread;
                    ft.show(mFragmentSpread);
                    ft.commitAllowingStateLoss();
                    resetTabView();

                    ((ImageView) panelSpread.findViewById(R.id.iv_spread)).setColorFilter(getResources().getColor(R.color.green));
                    ((TextView) panelSpread.findViewById(R.id.tv_spread)).setTextColor(getResources().getColor(R.color.green));
                    break;
                case R.id.ll_panel_user:
                    ft = fm.beginTransaction();

                    if (mFragmentUser == null) {
                        mFragmentUser = new FragmentUser();
                    }
                    ft.replace(R.id.rl_fragment_container, mFragmentUser);
                    curFragment = mFragmentUser;
                    ft.show(mFragmentUser);
                    ft.commitAllowingStateLoss();
                    resetTabView();
                    ((ImageView) panelUser.findViewById(R.id.iv_user)).setColorFilter(getResources().getColor(R.color.green));
                    ((TextView) panelUser.findViewById(R.id.tv_user)).setTextColor(getResources().getColor(R.color.green));
                    break;
            }
        }
    }

    private void resetTabView() {

        ((ImageView) panelTask.findViewById(R.id.iv_task)).setColorFilter(getResources().getColor(R.color.black));
        ((TextView) panelTask.findViewById(R.id.tv_task)).setTextColor(getResources().getColor(R.color.black));

        ((ImageView) panelSpread.findViewById(R.id.iv_spread)).setColorFilter(getResources().getColor(R.color.black));
        ((TextView) panelSpread.findViewById(R.id.tv_spread)).setTextColor(getResources().getColor(R.color.black));

        ((ImageView) panelOper.findViewById(R.id.iv_operation)).setColorFilter(getResources().getColor(R.color.black));
        ((TextView) panelOper.findViewById(R.id.tv_operation)).setTextColor(getResources().getColor(R.color.black));

        ((ImageView) panelUser.findViewById(R.id.iv_user)).setColorFilter(getResources().getColor(R.color.black));
        ((TextView) panelUser.findViewById(R.id.tv_user)).setTextColor(getResources().getColor(R.color.black));

    }
}
