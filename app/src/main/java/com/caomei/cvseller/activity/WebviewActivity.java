package com.caomei.cvseller.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.caomei.cvseller.R;

/**
 * Created by Administrator on 2016/7/1.
 */
public class WebviewActivity extends BaseActivity {

    private ImageView ivBack;
    private TextView tvTitle;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        setView();
        setData();
    }

    @Override
    public void setView() {
        ivBack=(ImageView)findViewById(R.id.iv_back);
        tvTitle=(TextView)findViewById(R.id.tv_title);

        webView=(WebView)findViewById(R.id.wv_view);

    }

    @Override
    public void setData() {
        String title=getIntent().getStringExtra("title");
        String url=getIntent().getStringExtra("url");
        tvTitle.setText(title);
        webView.loadUrl(url);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.activity_slide_left_in,R.anim.activity_slide_right_out);
    }

}
