package com.caomei.cvseller.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.caomei.cvseller.R;
import com.caomei.cvseller.util.ToastUtil;

/**
 * Created by poker on 16/6/26.
 */
public class SelectRoleActivity extends BaseActivity{

    private Button btSupplier;
    private Button btProvincer;
    private Button btDistrick;
    private Button btStage;
    private Button btManage;
    private CommonListner mListener;
    private ImageView ivBack;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_role);
        setView();
        setData();
    }
    @Override
    public void setView() {
        btSupplier=(Button)findViewById(R.id.bt_supplier);
        btProvincer=(Button)findViewById(R.id.bt_provincial);
        btDistrick=(Button)findViewById(R.id.bt_districk);
        btStage=(Button)findViewById(R.id.bt_stage);
        btManage=(Button)findViewById(R.id.bt_manager);
        ivBack=(ImageView)findViewById(R.id.iv_back);

    }

    @Override
    public void setData() {
        mListener=new CommonListner();
        btSupplier.setOnClickListener(mListener);
        btProvincer.setOnClickListener(mListener);
        btDistrick.setOnClickListener(mListener);
        btStage.setOnClickListener(mListener);
        btManage.setOnClickListener(mListener);
        ivBack.setOnClickListener(mListener);
    }

    class CommonListner implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.bt_supplier:
//                    break;
                case R.id.bt_provincial:
//                    break;
                case R.id.bt_districk:
//                    break;
                case R.id.bt_stage:
                    ToastUtil.Show(mContext,"暂不支持该身份用户");
                      break;
                case R.id.bt_manager:
                    startNewActivity(RegistActivity.class,R.anim.activity_slide_right_in,R.anim.activity_slide_left_out,false,null);
                    break;
                case R.id.iv_back:
                    onBackPressed();
                    break;
            }
        }
    }
}
