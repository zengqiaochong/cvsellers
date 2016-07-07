package com.caomei.cvseller.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.caomei.cvseller.R;
import com.caomei.cvseller.activity.MainActivity;

/**
 * Created by Administrator on 2016/7/1.
 */
public class FragmentOperation extends BaseFragment {
    private Button btArrival;
    private Button btSend;
    private Button btSign;
    private CommonListner mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_oper, container, false);
        setView();
        setData();
        return mView;
    }


    private void setView() {
        btArrival = (Button) mView.findViewById(R.id.bt_scan_arrival);
        btSend = (Button) mView.findViewById(R.id.bt_send_scan);
        btSign = (Button) mView.findViewById(R.id.bt_sign_scan);
    }

    private void setData() {
        mListener=new CommonListner();
        btArrival.setOnClickListener(mListener);
        btSend.setOnClickListener(mListener);
        btSign.setOnClickListener(mListener);
    }
    class CommonListner implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_scan_arrival:


                case R.id.bt_send_scan:

                case R.id.bt_sign_scan:
//                    ((MainActivity)mContext).startNewActivity();
                    break;
            }
        }
    }
}
