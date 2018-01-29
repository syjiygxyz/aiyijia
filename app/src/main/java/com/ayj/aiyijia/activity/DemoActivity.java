package com.ayj.aiyijia.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ayj.aiyijia.R;
import com.ayj.aiyijia.activity.base.BaseActivity;
import com.ayj.aiyijia.view.OperatePopupWindow;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DemoActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right_left)
    ImageView ivRightLeft;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.bt)
    Button bt;

    @BindView(R.id.ll)
    LinearLayout ll;

    private OperatePopupWindow pop;//弹出分享布局

    @Override
    public int getLayoutId() {
        return R.layout.activity_demo;
    }

    @Override
    public void initDatas() {
        pop = new OperatePopupWindow(this, this, "你渴望奶子吗", "http://www.baidu.com","http://crm.21ga.cn:9000/qmkmimageupload/Upload/2017-05-06/20170506141017378.png");
    }

    @Override
    public void configViews() {

    }


    @OnClick({R.id.iv_back, R.id.bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.bt:
                pop.showAtLocation(ll, Gravity.BOTTOM
                        | Gravity.CENTER_HORIZONTAL, 0, 0);
                pop.update();
                break;
        }
    }
}
