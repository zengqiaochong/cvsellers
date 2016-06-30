package com.caomei.cvseller.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.caomei.cvseller.CommonData.CommonApi;
import com.caomei.cvseller.Enum.AccessNetState;
import com.caomei.cvseller.R;
import com.caomei.cvseller.bean.AccessNetResultBean;
import com.caomei.cvseller.bean.TypeMsgBean;
import com.caomei.cvseller.eventbus.ECode;
import com.caomei.cvseller.eventbus.EventMsg;
import com.caomei.cvseller.util.CheckIDNumber;
import com.caomei.cvseller.util.NetUtil;
import com.caomei.cvseller.util.ShareUtil;
import com.caomei.cvseller.util.ToastUtil;
import com.google.gson.Gson;

import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.greenrobot.event.EventBus;

/**
 * Created by poker on 16/6/26.
 */
public class RegistActivity extends BaseActivity {
    private Button btRegister;
    private EditText etCode;
    private EditText etPwd;
    private EditText etPwdConfirm;
    private EditText etUserName;
    private EditText etIDNo;
    private TextView tvUpManage;
    private RelativeLayout panelUpManager;
    private RelativeLayout panelServerName;
    private TextView tvServerName;
    private EditText etMarket;
    private EditText etMarketPostion;
    private TextView tvServerType;
    private CheckBox cbAgree;
    private TextView tvContract;
    private CommonListener mListener;
    private Button btGetCoder;
    private EditText etPhoneNo;
    private RelativeLayout panelServerType;
    private RelativeLayout panel_area;
    private TextView tvArea;
    
    private EditText etRealName;
    private myTimerTask myTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setView();
        setData();
    }

    @Override
    public void setView() {
        etPhoneNo = (EditText) findViewById(R.id.et_phone);
        etCode = (EditText) findViewById(R.id.et_coder);
        btGetCoder = (Button) findViewById(R.id.bt_get_coder);
        panelUpManager = (RelativeLayout) findViewById(R.id.rl_panel_up_manager);
        etPwd = (EditText) findViewById(R.id.et_pwd);
        etPwdConfirm = (EditText) findViewById(R.id.et_pwd_confirm);
        etRealName =(EditText)findViewById(R.id.et_nick_name);
        tvUpManage = (TextView) findViewById(R.id.tv_update_manager);
        etIDNo = (EditText) findViewById(R.id.et_user_id_number);
        panel_area = (RelativeLayout) findViewById(R.id.rl_panel_area);
        tvArea = (TextView) findViewById(R.id.tv_server_name);
        panelServerName = (RelativeLayout) findViewById(R.id.rl_panel_area);
        tvServerName = (TextView) findViewById(R.id.tv_server_name);

        etMarket = (EditText) findViewById(R.id.et_market_name);
        etMarketPostion = (EditText) findViewById(R.id.et_market_postion);
        panelServerType = (RelativeLayout) findViewById(R.id.rl_panel_server_type);
        tvServerType = (TextView) findViewById(R.id.tv_server_type);

        cbAgree = (CheckBox) findViewById(R.id.cb_agree);
        tvContract = (TextView) findViewById(R.id.tv_contract);
        btRegister = (Button) findViewById(R.id.bt_register_now);
    }

    @Override
    public void setData() {
        mListener = new CommonListener();
        btRegister.setOnClickListener(mListener);
        cbAgree.setOnClickListener(mListener);
        btGetCoder.setOnClickListener(mListener);
        tvContract.setOnClickListener(mListener);


        panel_area.setOnClickListener(mListener);
        panelUpManager.setOnClickListener(mListener);
        panelServerName.setOnClickListener(mListener);
        panelServerType.setOnClickListener(mListener);
    }

    class CommonListener implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_register_now:
                    checkMSMCoder();
                    break;
                case R.id.bt_get_coder:
                    getMSMCoder();
                    break;
                case R.id.tv_contract:
                    break;
                case R.id.cb_agree:
                    break;


                case R.id.rl_panel_up_manager:
                    ShowDialog();
                    tvUpManage.setText("測試1");
                    break;
                case R.id.rl_panel_area:
                    ShowDialog();
                    tvArea.setText("測試1");
                    break;
                case R.id.rl_panel_server_type:
                    ShowDialog();
                    tvServerType.setText("測試1");
                    break;
            }
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        }
    }

    private void checkMSMCoder() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = CommonApi.BASE_URL + String.format(CommonApi.URL_CHECK_SMS_CODE, etPhoneNo.getEditableText().toString(),
                        etCode.getEditableText().toString(), ShareUtil.getInstance(mContext).getUserId());
                try{
                    AccessNetResultBean bean = NetUtil.getInstance(mContext).getDataFromNetByGet(url);
                    if(bean.getState()==AccessNetState.AccessNetState.Success){
                        TypeMsgBean tb=new Gson().fromJson(bean.getResult(), TypeMsgBean.class);
                        if(tb.getRESULT_TYPE()==1){
                            EventBus.getDefault().post(new EventMsg(ECode.CHECK_MSM_DONE,tb.getRESULT_MSG()));
                        }else {
                            EventBus.getDefault().post(new EventMsg(ECode.CHECK_MSM_ERROR,tb.getRESULT_MSG()));
                        }
                    }
                    else{
                        EventBus.getDefault().post(new EventMsg(ECode.CHECK_MSM_ERROR,bean.getResult()));
                    }
                }catch (Exception ex){
                    EventBus.getDefault().post(new EventMsg(ECode.CHECK_MSM_ERROR,ex.toString()));
                }

            }
        }
        ).start();
    }

    private void ShowDialog() {
        Dialog dialog = new Dialog(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_select, null);
        dialog.setContentView(view);
        dialog.setTitle("选择");
        String dataSet[] = new String[]{"測試1", "測試2", "測試3", "測試4", "測試5", "測試6"};
        ListView lv = (ListView) view.findViewById(R.id.lv_data);
        lv.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, dataSet));
        dialog.show();
    }

    private void register() {
        if (!checkUserInfoFull()) {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = CommonApi.BASE_URL + String.format(CommonApi.URL_REGISTER,
                        etPhoneNo.getEditableText().toString(),
                        etPwd.getEditableText().toString(),
                        etRealName.getEditableText().toString(),
                        etIDNo.getEditableText().toString(),
                        "湖南省",
                        ShareUtil.getInstance(mContext).getUserId(),
                        "配送范围",
                        4,
                        "",
                        "白菜",
                        etMarket.getEditableText().toString(),
                        etMarketPostion.getEditableText().toString()
                );
                AccessNetResultBean bean = NetUtil.getInstance(mContext).getDataFromNetByGet(url);
                try {
                    if (bean.getState() == AccessNetState.AccessNetState.Success) {
                        TypeMsgBean tBean = new Gson().fromJson(bean.getResult(), TypeMsgBean.class);
                        if (tBean.getRESULT_TYPE() == 1) {
                            EventBus.getDefault().post(new EventMsg(ECode.REGISTER_DONE, tBean.getRESULT_MSG()));
                        } else {

                            EventBus.getDefault().post(new EventMsg(ECode.REGISTER_ERROR, tBean.getRESULT_MSG()));
                        }
                    }
                } catch (Exception ex) {
                    EventBus.getDefault().post(new EventMsg(ECode.REGISTER_ERROR, bean.getResult()));

                }
            }
        }).start();
    }

    private boolean checkUserInfoFull() {
        if (!CheckPhoneNumber()) {
            ToastUtil.Show(mContext, "电话号码不合法");
            return false;
        }
        if (etCode.getEditableText().toString() == null || etCode.getEditableText().toString().length() < 4) {
            ToastUtil.Show(mContext, "验证码不合法");
            return false;
        }
        if (etPwd.getEditableText().toString() == null || etPwd.getEditableText().toString().length() < 6) {
            ToastUtil.Show(mContext, "密码至少6位");
            return false;
        }
        if (!etPwd.getEditableText().toString().equals(etPwdConfirm.getEditableText().toString())) {
            ToastUtil.Show(mContext, "两次输入的密码不一致");
            return false;
        }
        if (etUserName.getText() == null || etUserName.getText().length() == 0) {
            ToastUtil.Show(mContext, "请输入你的真实姓名");
            return false;
        }
        if (!idLegalIDNO()) {
            return false;
        }
        if (TextUtils.isEmpty(tvUpManage.getText())) {
            ToastUtil.Show(mContext, "请选择上级管理驿站");
            return false;
        }
        if (TextUtils.isEmpty(tvServerName.getText())) {
            ToastUtil.Show(mContext, "请选择服务小区名称");
            return false;
        }
        if (TextUtils.isEmpty(etMarket.getEditableText().toString())) {
            ToastUtil.Show(mContext, "请输入实体店名称");
            return false;
        }
        if (TextUtils.isEmpty(etMarketPostion.getEditableText().toString())) {
            ToastUtil.Show(mContext, "请输入实体店位置");
            return false;
        }
        if (TextUtils.isEmpty(tvServerType.getText())) {
            ToastUtil.Show(mContext, "请选择提供服务方式");
            return false;
        }
        return true;
    }

    private boolean idLegalIDNO() {
        String res = CheckIDNumber.IDCardValidate(etIDNo.getEditableText().toString());
        if ("".equals(res))
            return true;
        else {
            ToastUtil.Show(mContext, res);
            return false;
        }
    }

    private void getMSMCoder() {
        if (!CheckPhoneNumber()) {
            Toast.makeText(RegistActivity.this, "电话号码不合法", Toast.LENGTH_SHORT).show();
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = CommonApi.BASE_URL + String.format(CommonApi.URL_GET_SMS_CODE, etPhoneNo.getEditableText().toString(), ShareUtil.getInstance(mContext).getUserId());
                try {
                    AccessNetResultBean bean = NetUtil.getInstance(mContext).getDataFromNetByGet(url);
                    if (bean.getState() == AccessNetState.AccessNetState.Success) {
                        TypeMsgBean tBean = new Gson().fromJson(bean.getResult(), TypeMsgBean.class);
                        if (tBean.getRESULT_TYPE() == 1) {
                            EventBus.getDefault().post(new EventMsg(ECode.GET_MSM_CODER_DONE, tBean.getRESULT_MSG()));
                        } else {
                            EventBus.getDefault().post(new EventMsg(ECode.GET_MSM_CODER_ERROR, tBean.getRESULT_MSG()));
                        }
                    }
                } catch (Exception ex) {
                    EventBus.getDefault().post(new EventMsg(ECode.GET_MSM_CODER_ERROR, null));
                }
            }
        }).start();



    }
    class myTimerTask extends TimerTask {
        private int length;
        private Button btn;
        public myTimerTask(Button btn, int len) {
            this.length = len;
            this.btn = btn;
        }
        @Override
        public void run() {
            runOnUiThread(new Runnable() { // UI thread
                @Override
                public void run() {
                    length--;
                    btn.setText(String.valueOf(length)+"s后重发");
                    if (length < 0) {
                        if(myTask!=null){
                            myTask.cancel();
                        }
                        btGetCoder.setClickable(true);
                        btGetCoder.setBackgroundColor(getResources().getColor(R.color.gray));
                        btn.setText("获取验证码");
                    }
                }
            });
        }
    };
    private boolean CheckPhoneNumber() {
        String str = etPhoneNo.getEditableText().toString();
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        if (str == null || str.length() < 11) {
            return false;
        }
        p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    @Override
    public void onEventMainThread(EventMsg msg) {
        switch (msg.getFlag()) {
            case ECode.GET_MSM_CODER_DONE:

                break;
            case ECode.GET_MSM_CODER_ERROR:
                Toast.makeText(mContext, msg.getData().toString(), Toast.LENGTH_LONG).show();
                break;
            case ECode.REGISTER_DONE:
                ToastUtil.Show(mContext, msg.getData().toString());
                startNewActivity(LoginActivity.class,R.anim.activity_slide_right_in,R.anim.activity_slide_left_out,true,null);

                break;
            case ECode.REGISTER_ERROR:
                ToastUtil.Show(mContext, msg.getData().toString());
                break;

            case ECode.CHECK_MSM_DONE:
                register();
                break;
            case ECode.CHECK_MSM_ERROR:
                ToastUtil.Show(mContext,msg.getData().toString());
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
