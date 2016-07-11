package com.caomei.cvseller.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.caomei.cvseller.eventbus.EventMsg;
import com.caomei.cvseller.util.NetUtil;
import com.caomei.cvseller.util.ShareUtil;

import de.greenrobot.event.EventBus;

/**
 * Created by poker on 16/6/26.
 */
public abstract class BaseActivity extends AppCompatActivity {
    public Context mContext;
    public NetUtil netUtil;
    public ShareUtil shareUtil;
    public ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        shareUtil = ShareUtil.getInstance(mContext);
        netUtil = NetUtil.getInstance(mContext);
        EventBus.getDefault().register(this);
        dialog = new ProgressDialog(mContext);
    }


    public final void startNewActivity(Class<? extends Activity> target,
                                       int enterAnim, int exitAnim, boolean isFinish, Bundle mBundle) {
        Intent mIntent = new Intent(this, target);
        if (mBundle != null) {
            mIntent.putExtras(mBundle);
        }
        startActivity(mIntent);
        overridePendingTransition(enterAnim, exitAnim);
        if (isFinish) {
            finish();
        }
    }

    public abstract void setView();

    public abstract void setData();

    public void onEventMainThread(EventMsg msg) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
