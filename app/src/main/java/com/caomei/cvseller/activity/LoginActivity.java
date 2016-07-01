package com.caomei.cvseller.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.caomei.cvseller.CommonData.CommonApi;
import com.caomei.cvseller.Enum.AccessNetState;
import com.caomei.cvseller.R;
import com.caomei.cvseller.bean.AccessNetResultBean;
import com.caomei.cvseller.bean.TypeMsgBean;
import com.caomei.cvseller.eventbus.ECode;
import com.caomei.cvseller.eventbus.EventMsg;
import com.caomei.cvseller.util.NetUtil;
import com.caomei.cvseller.util.ShareUtil;
import com.caomei.cvseller.util.ToastUtil;
import com.google.gson.Gson;

import de.greenrobot.event.EventBus;

public class LoginActivity extends BaseActivity {

    private TextView tvForgetPwd;
    private TextView tvRegister;
    private CommonListner mListner;

    private Button btLogin;
    private EditText etUserName;
    private EditText etPwd;
    private String RoleId;
    private CheckBox cbSavePwd;

    private CheckBox cbLoginAuto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setView();
        setData();
    }

    @Override
    public void setView() {
        tvRegister = (TextView) findViewById(R.id.tv_register);
        tvForgetPwd = (TextView) findViewById(R.id.tv_forget_pwd);

        etUserName = (EditText) findViewById(R.id.et_user_name);
        etPwd = (EditText) findViewById(R.id.et_pwd);


        cbSavePwd=(CheckBox)findViewById(R.id.cb_remember_pass);
        cbLoginAuto=(CheckBox)findViewById(R.id.cb_remember_pass);

        btLogin = (Button) findViewById(R.id.bt_login);

    }

    @Override
    public void setData() {
        RoleId="3";
        mListner = new CommonListner();
        tvRegister.setOnClickListener(mListner);
        btLogin.setOnClickListener(mListner);
        tvForgetPwd.setOnClickListener(mListner);

        cbSavePwd.setChecked(shareUtil.getIsSaveUserPwd());
        cbLoginAuto.setChecked(shareUtil.getIsAutoLogin());

        etUserName.setText(shareUtil.getUserName());
        if(shareUtil.getIsSaveUserPwd()){
            etPwd.setText(shareUtil.getUserPwd());
        }

        if(shareUtil.getIsAutoLogin()){
            login();
        }
    }

    public class CommonListner implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.tv_register:
                    startNewActivity(SelectRoleActivity.class, R.anim.activity_slide_right_in, R.anim.activity_slide_left_out, false, null);
                    break;
                case R.id.bt_login:
//                    if (userInfoFull())
//                        login();
                    startNewActivity(MainActivity.class,R.anim.activity_slide_right_in,R.anim.activity_slide_left_out,true,null);
                    break;
                case R.id.tv_forget_pwd:
                    ToastUtil.Show(mContext,"请加群联系管理员");
                    break;
            }
        }
    }

    private boolean userInfoFull() {
        if (TextUtils.isEmpty(etUserName.getEditableText().toString()) || TextUtils.isEmpty(etPwd.getEditableText().toString()))
        {
            ToastUtil.Show(mContext, "请检查账号和用户用是否合法");
            return false;
        }
        return true;
    }

    private void login() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = CommonApi.BASE_URL + String.format(CommonApi.URL_LOGIN,etUserName.getEditableText().toString(),
                        etPwd.getEditableText().toString(),RoleId);
                Log.e("url","登录url  "+url);
                try{
                    AccessNetResultBean bean=NetUtil.getInstance(mContext).getDataFromNetByGet(url);
                    if(bean.getState()== AccessNetState.AccessNetState.Success){
                        TypeMsgBean tb=new Gson().fromJson(bean.getResult(),TypeMsgBean.class);
                        if(tb.getRESULT_TYPE()==1){
                            EventBus.getDefault().post(new EventMsg(ECode.LOGIN_IN_OK,tb.getRESULT_MSG()));
                        }else{
                            EventBus.getDefault().post(new EventMsg(ECode.LOGIN_IN_ERROR,tb.getRESULT_MSG()));
                        }
                    }else{
                        EventBus.getDefault().post(new EventMsg(ECode.LOGIN_IN_ERROR,bean.getResult()));
                    }
                }catch (Exception ex){
                    EventBus.getDefault().post(new EventMsg(ECode.LOGIN_IN_ERROR,ex.toString()));
                }
            }
        }).start();
    }

    @Override
    public void onEventMainThread(EventMsg msg)
    {
        switch (msg.getFlag()){
            case ECode.LOGIN_IN_OK:
                ToastUtil.Show(mContext,msg.getData().toString());
                saveUserInfo();
                break;
            case ECode.LOGIN_IN_ERROR:
                ToastUtil.Show(mContext,msg.getData().toString());
                break;
        }
    }

    /**
     * 保存用户信息，供下次自动登录使用
     */
    private void saveUserInfo() {
        shareUtil.setUserName(etUserName.getEditableText().toString());
        if(cbSavePwd.isChecked()){
            shareUtil.setIsSaveUserPwd(true);
            shareUtil.setUserPwd(etPwd.getEditableText().toString());
        }else{
            shareUtil.setIsSaveUserPwd(false);
            shareUtil.setUserPwd("");
        }
    }
}
