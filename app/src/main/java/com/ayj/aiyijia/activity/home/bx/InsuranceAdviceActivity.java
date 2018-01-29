package com.ayj.aiyijia.activity.home.bx;

import android.widget.ImageView;
import android.widget.TextView;


import com.ayj.aiyijia.R;
import com.ayj.aiyijia.activity.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class InsuranceAdviceActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    public int getLayoutId() {
        return R.layout.activity_insurance_advice;
    }

    @Override
    public void initDatas() {
        tvTitle.setText("保险咨询");
    }

    @Override
    public void configViews() {

    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
