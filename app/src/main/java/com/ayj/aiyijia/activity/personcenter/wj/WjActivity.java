package com.ayj.aiyijia.activity.personcenter.wj;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayj.aiyijia.R;
import com.ayj.aiyijia.activity.base.BaseActivity;
import com.ayj.aiyijia.activity.personcenter.wj.dsj.DsqActivity;
import com.ayj.aiyijia.activity.personcenter.wj.lxj.LxjMainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
* 物卷
* */
public class WjActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    public int getLayoutId() {
        return R.layout.activity_wj;
    }

    @Override
    public void initDatas() {
        tvTitle.setText("物卷");
    }

    @Override
    public void configViews() {

    }

    @OnClick({R.id.iv_back, R.id.rl_lx, R.id.rl_dwj,R.id.rl_ds})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_lx:
                startActivity(new Intent(mContext, LxjMainActivity.class));
                break;
            case R.id.rl_ds:
                DsqActivity.jumpActivity(mContext,"打赏卷");
                break;
            case R.id.rl_dwj:
                DsqActivity.jumpActivity(mContext,"兑物卷");
                break;
        }
    }
}
