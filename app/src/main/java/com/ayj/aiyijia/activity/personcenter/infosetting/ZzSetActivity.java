package com.ayj.aiyijia.activity.personcenter.infosetting;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayj.aiyijia.R;
import com.ayj.aiyijia.activity.base.BaseActivity;
import com.ayj.aiyijia.utils.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
* 地址填写
* */
public class ZzSetActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_dq)
    TextView tvDq;
    @BindView(R.id.et_xq)
    EditText etXq;
    @BindView(R.id.tv_post)
    TextView tvPost;

    @Override
    public int getLayoutId() {
        return R.layout.activity_zz_set;
    }

    @Override
    public void initDatas() {
        tvTitle.setText("住址填写");
    }

    @Override
    public void configViews() {

    }

    @OnClick({R.id.tv_dq, R.id.tv_post,R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_dq:
                CommonUtils.getDqPickerView(mContext, tvDq);
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_post:
                doPost();
                break;
        }
    }
    private void doPost(){
        if(TextUtils.isEmpty(tvDq.getText().toString().trim())){
            showToast("请选择地区");
            return;
        }
        if(TextUtils.isEmpty(etXq.getText().toString().trim())){
            showToast("请填写详细地址");
        }
        Zlxl("homeaddr",tvDq.getText().toString().trim()+etXq.getText().toString().trim(),4);
    }
}
