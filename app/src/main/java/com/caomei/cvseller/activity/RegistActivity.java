package com.caomei.cvseller.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.caomei.cvseller.CommonData.CommonApi;
import com.caomei.cvseller.Enum.AccessNetState;
import com.caomei.cvseller.R;
import com.caomei.cvseller.StagesAdapter;
import com.caomei.cvseller.bean.AccessNetResultBean;
import com.caomei.cvseller.bean.CommunityBean;
import com.caomei.cvseller.bean.CommunityData;
import com.caomei.cvseller.bean.StagesBean;
import com.caomei.cvseller.bean.StagesData;
import com.caomei.cvseller.bean.TypeMsgBean;
import com.caomei.cvseller.dialog.SelectCityDialog;
import com.caomei.cvseller.eventbus.ECode;
import com.caomei.cvseller.eventbus.EventMsg;
import com.caomei.cvseller.util.NetUtil;
import com.caomei.cvseller.util.ShareUtil;
import com.caomei.cvseller.util.ToastUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Timer;
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
    private Timer mTimer = new Timer();
    private ArrayList<String> mProvinces;
    private ArrayAdapter<String> adapter;
    private TextView tvProvince;
    private TextView tvServerArea;
    private TextView tvCounty;
    private ArrayList<String> mAreas;
    private ArrayList<String> mCounty;
    private StagesAdapter stageAdapter;
    private Dialog commonDialog;
    private String stageID;
    private String[] stagesData;
    private String[] communitySet;
    private StagesBean stagesBean;
    private CommunityBean communityBean;
    private int dialogType;  //0标示上级管理驿站的dialog  1 表示小区列表的dialog
    private String communityID;
    private String[] serverType;
    private int serverTypeID;
    private Dialog progressDialog;
    private boolean getStageFinish;
    private boolean getCommunityFinish;
    private boolean stageFull;
    private boolean communityFull;
    private ImageView ivBack;

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
        etRealName = (EditText) findViewById(R.id.et_nick_name);
        tvUpManage = (TextView) findViewById(R.id.tv_update_manager);
        panel_area = (RelativeLayout) findViewById(R.id.rl_panel_area);
        tvServerArea = (TextView) findViewById(R.id.tv_server_name);
        panelServerName = (RelativeLayout) findViewById(R.id.rl_panel_area);
        tvServerName = (TextView) findViewById(R.id.tv_server_name);

        tvProvince = (TextView) findViewById(R.id.tv_province);
        tvArea = (TextView) findViewById(R.id.tv_area);
        tvCounty = (TextView) findViewById(R.id.tv_county);

        etMarket = (EditText) findViewById(R.id.et_market_name);
        etMarketPostion = (EditText) findViewById(R.id.et_market_postion);
        panelServerType = (RelativeLayout) findViewById(R.id.rl_panel_server_type);
        tvServerType = (TextView) findViewById(R.id.tv_server_type);

        cbAgree = (CheckBox) findViewById(R.id.cb_agree);
        tvContract = (TextView) findViewById(R.id.tv_contract);
        btRegister = (Button) findViewById(R.id.bt_register_now);
        ivBack=(ImageView)findViewById(R.id.iv_back);
    }

    @Override
    public void setData() {
        mListener = new CommonListener();
        progressDialog = new Dialog(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_progress, null);
        progressDialog.setContentView(view);
        progressDialog.setCancelable(false);
        String tem = "门口取件_送货上门_店面自提";
        serverType = tem.split("_");
        btRegister.setOnClickListener(mListener);
        cbAgree.setOnClickListener(mListener);
        btGetCoder.setOnClickListener(mListener);
        tvContract.setOnClickListener(mListener);

        tvProvince.setOnClickListener(mListener);
        tvArea.setOnClickListener(mListener);
        tvCounty.setOnClickListener(mListener);

        panel_area.setOnClickListener(mListener);
        panelUpManager.setOnClickListener(mListener);
        panelServerName.setOnClickListener(mListener);
        panelServerType.setOnClickListener(mListener);

        ivBack.setOnClickListener(mListener);
    }

    class CommonListener implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, AdapterView.OnItemClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_back:
                    onBackPressed();
                    break;
                case R.id.bt_register_now:
                    checkMSMCoder();
//                    register();
                    break;

                case R.id.bt_get_coder:
                    getMSMCoder();
                    break;
                case R.id.tv_contract:
                    Bundle mb = new Bundle();
                    mb.putString("title", "菜来了商家协议");
                    mb.putString("url", "http://app.zmbok.com:8080/zouma/jiameng.jsp?userName=" + etRealName.getEditableText().toString());
                    startNewActivity(WebviewActivity.class, R.anim.activity_slide_right_in, R.anim.activity_slide_left_out, false, mb);
                    break;
                case R.id.cb_agree:

                    break;
                case R.id.rl_panel_up_manager:
                    if(!getStageFinish||!stageFull){
                        ToastUtil.Show(mContext,"当前选择的市区无服务驿站");
                        return;
                    }
                    dialogType = 0;
                    ShowDialog("驿站列表", stagesData);
                    break;
                case R.id.rl_panel_area:
                    if(!getCommunityFinish||!communityFull){
                        ToastUtil.Show(mContext,"当前选择的区县无服务小区");
                        return;
                    }
                    dialogType = 1;
                    ShowDialog("小区列表", communitySet);
                    break;
                case R.id.rl_panel_server_type:
                    dialogType = 2;
                    ShowDialog("服务方式", serverType);
                    break;
                case R.id.tv_province:
                case R.id.tv_area:
                case R.id.tv_county:
                    SelectCityDialog mDialog = new SelectCityDialog(mContext);
                    mDialog.show();
                    break;
            }
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (dialogType == 0) {
                tvUpManage.setText(stagesBean.getData().get(position).getUserName());
                stageID = stagesBean.getData().get(position).getUser_id();
                commonDialog.dismiss();
            } else if (dialogType == 1) {
                tvServerArea.setText(communityBean.getData().get(position).getName());
                communityID = communityBean.getData().get(position).getId();
                commonDialog.dismiss();
            } else if (dialogType == 2) {
                tvServerType.setText(serverType[position]);
                serverTypeID = position;
                commonDialog.dismiss();
            }
        }
    }

    private void checkMSMCoder() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = CommonApi.BASE_URL + String.format(CommonApi.URL_CHECK_SMS_CODE, etPhoneNo.getEditableText().toString(),
                        etCode.getEditableText().toString(), ShareUtil.getInstance(mContext).getUserId());
                try {
                    AccessNetResultBean bean = NetUtil.getInstance(mContext).getDataFromNetByGet(url);
                    if (bean.getState() == AccessNetState.AccessNetState.Success) {
                        TypeMsgBean tb = new Gson().fromJson(bean.getResult(), TypeMsgBean.class);
                        if (tb.getRESULT_TYPE() == 1) {
                            EventBus.getDefault().post(new EventMsg(ECode.CHECK_MSM_DONE, tb.getRESULT_MSG()));
                        } else {
                            EventBus.getDefault().post(new EventMsg(ECode.CHECK_MSM_ERROR, tb.getRESULT_MSG()));
                        }
                    } else {
                        EventBus.getDefault().post(new EventMsg(ECode.CHECK_MSM_ERROR, bean.getResult()));
                    }
                } catch (Exception ex) {
                    EventBus.getDefault().post(new EventMsg(ECode.CHECK_MSM_ERROR, ex.toString()));
                }
            }
        }
        ).start();
    }

    private void ShowDialog(String title, String[] dataSet) {
        commonDialog = new Dialog(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_select, null);
        commonDialog.setContentView(view);
        commonDialog.setTitle(title);
        ListView lv = (ListView) view.findViewById(R.id.lv_data);
        lv.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, dataSet));

        lv.setOnItemClickListener(mListener);
        commonDialog.show();
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
                        tvProvince.getText(),
                        tvArea.getText(),
                        tvCounty.getText(),
                        communityID,
                        "3",
                        etMarketPostion.getEditableText().toString(),
                        "app注册",
                        serverTypeID
                );
                AccessNetResultBean bean = NetUtil.getInstance(mContext).getDataFromNetByGet(url);
                try {
                    if (bean.getState() == AccessNetState.AccessNetState.Success) {
                        Log.e("result", "注册结果： " + bean.getResult());
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
        if (etRealName.getText() == null || etRealName.getText().length() == 0) {
            ToastUtil.Show(mContext, "请输入你的真实姓名");
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

    private void getMSMCoder() {
        if (!CheckPhoneNumber()) {
            Toast.makeText(RegistActivity.this, "电话号码不合法", Toast.LENGTH_SHORT).show();
            return;
        }
        btGetCoder.setClickable(false);

        myTask = new myTimerTask(btGetCoder, 300);
        mTimer.schedule(myTask, 1000, 1000);
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
                    btn.setText(String.valueOf(length) + "s后重发");
                    if (length < 0) {
                        if (myTask != null) {
                            myTask.cancel();
                        }
                        btGetCoder.setClickable(true);
                        btn.setText("获取验证码");
                    }
                }
            });
        }
    }

    ;

    private boolean CheckPhoneNumber() {
        String str = etPhoneNo.getEditableText().toString();
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        if (str == null || str.length() < 11) {
            return false;
        }
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
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
                startNewActivity(LoginActivity.class, R.anim.activity_slide_right_in, R.anim.activity_slide_left_out, true, null);

                break;
            case ECode.REGISTER_ERROR:
                ToastUtil.Show(mContext, msg.getData().toString());
                break;
            case ECode.CHECK_MSM_ERROR:
                ToastUtil.Show(mContext, msg.getData().toString());
                break;

            case ECode.CHECK_MSM_DONE:
                register();
                break;

            case ECode.GET_STAGES_SUCCESS:
                setStagesData(msg.getData().toString());
                getStageFinish = true;
                if (getCommunityFinish) {
                    progressDialog.dismiss();
                }
                break;
            case ECode.GET_STAGES_ERROR:
                ToastUtil.Show(mContext, msg.getData().toString());
                getStageFinish = true;
                if (getCommunityFinish) {
                    progressDialog.dismiss();
                }
                break;

            case ECode.SELECT_CITY_CHANGE:
                Log.e("data", msg.getData().toString());
                String citys = msg.getData().toString();
                String[] names = citys.split("_");
                tvProvince.setText(names[0]);
                tvArea.setText(names[1]);
                tvCounty.setText(names[2]);
                requestXGData(names[1], names[2]);
//                requestUpManager(names[1]);
//                requestCommunity(names[2]);
                progressDialog.show();
                break;
            case ECode.GET_COMMUNITY_ERROR:
                getCommunityFinish = true;
                if (getStageFinish) {
                    progressDialog.dismiss();
                }
                break;
            case ECode.GET_COMMUNITY_SUCCESS:
                setCommunityData(msg.getData().toString());
                getCommunityFinish = true;
                if (getStageFinish) {
                    progressDialog.dismiss();
                }
                break;
        }
    }

    private void requestXGData(String shi, String xian) {
        final String communityUrl = CommonApi.BASE_URL + String.format(CommonApi.URL_GET_COMMUNITY, xian);
        final String stageUrl = CommonApi.BASE_URL + String.format(CommonApi.URL_GET_STAGES, shi);

        new Thread(new Runnable() {
            @Override
            public void run() {
                AccessNetResultBean stageBean = netUtil.getDataFromNetByGet(stageUrl);
                if (stageBean.getState() == AccessNetState.AccessNetState.Success) {
                    EventBus.getDefault().post(new EventMsg(ECode.GET_STAGES_SUCCESS, stageBean.getResult()));
                } else {
                    EventBus.getDefault().post(new EventMsg(ECode.GET_STAGES_ERROR, stageBean.getResult()));
                }

                AccessNetResultBean communityBean = NetUtil.getInstance(mContext).getDataFromNetByGet(communityUrl);
                if (communityBean.getState() == AccessNetState.AccessNetState.Success) {
                    EventBus.getDefault().post(new EventMsg(ECode.GET_COMMUNITY_SUCCESS, communityBean.getResult()));
                } else {
                    EventBus.getDefault().post(new EventMsg(ECode.GET_COMMUNITY_ERROR, communityBean.getResult()));
                }
            }
        }).start();
    }

    /**
     * 设置小区列表的字符串数组
     *
     * @param s
     */
    private void setCommunityData(String s) {
        communityBean = new Gson().fromJson(s, CommunityBean.class);
        if (communityBean.getData() == null || communityBean.getData().size() == 0) {
            ToastUtil.Show(mContext, tvCounty.getText().toString() + "下无服务小区");
            communityFull=false;
            return;
        }
        communityFull=true;
        String temStr = "";
        for (CommunityData data : communityBean.getData()) {
            temStr += (" " + data.getName());
        }
        communitySet = temStr.trim().split(" ");
    }

    private void setStagesData(String s) {
        Log.e("data", "驿站数据 " + s);
        stagesBean = new Gson().fromJson(s, StagesBean.class);
        if (stagesBean.getData() == null || stagesBean.getData().size() == 0) {
            ToastUtil.Show(mContext, tvArea.getText().toString() + "下无驿站分布");
            stageFull=false;
            return;
        }
        stageFull=true;
        String temStr = "";
        for (StagesData data : stagesBean.getData()) {
            temStr += (" " + data.getUserName());
        }
        stagesData = temStr.trim().split(" ");
    }

    /**
     * 根据选择的市区获取驿站列表
     *
     * @param mCity
     */
    private void requestUpManager(String mCity) {
        final String url = CommonApi.BASE_URL + String.format(CommonApi.URL_GET_STAGES, mCity);
        new Thread(new Runnable() {
            @Override
            public void run() {
                AccessNetResultBean bean = netUtil.getDataFromNetByGet(url);
                if (bean.getState() == AccessNetState.AccessNetState.Success) {
                    EventBus.getDefault().post(new EventMsg(ECode.GET_STAGES_SUCCESS, bean.getResult()));
                } else {
                    EventBus.getDefault().post(new EventMsg(ECode.GET_STAGES_ERROR, bean.getResult()));
                }
            }
        }).start();
    }

    /**
     * 根据选择的区县获取小区列表
     *
     * @param name
     */
    private void requestCommunity(String name) {
        final String url = CommonApi.BASE_URL + String.format(CommonApi.URL_GET_COMMUNITY, name);
        new Thread(new Runnable() {
            @Override
            public void run() {
                AccessNetResultBean bean = NetUtil.getInstance(mContext).getDataFromNetByGet(url);
                if (bean.getState() == AccessNetState.AccessNetState.Success) {
                    EventBus.getDefault().post(new EventMsg(ECode.GET_COMMUNITY_SUCCESS, bean.getResult()));
                } else {
                    EventBus.getDefault().post(new EventMsg(ECode.GET_COMMUNITY_ERROR, bean.getResult()));
                }
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
