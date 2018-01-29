package com.ayj.aiyijia.activity.personcenter.set;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ayj.aiyijia.AyjSwApplication;
import com.ayj.aiyijia.R;
import com.ayj.aiyijia.activity.base.BaseActivity;
import com.ayj.aiyijia.bean.MainBanner;
import com.ayj.aiyijia.bean.eventbus.FirstEvent;
import com.ayj.aiyijia.utils.ACache;
import com.ayj.aiyijia.utils.CommonUtils;
import com.ayj.aiyijia.utils.DataCleanManager;
import com.ayj.aiyijia.view.sweetalert.SweetAlertDialog;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/*
* 设置
* */
public class SettingActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_feedback)
    RelativeLayout rlFeedback;
    @BindView(R.id.rl_clean)
    RelativeLayout rlClean;
    @BindView(R.id.rl_about)
    RelativeLayout rlAbout;
    @BindView(R.id.tv_ache)
    TextView tvAche;
    @BindView(R.id.tv_quit)
    TextView tvQuit;

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initDatas() {
        tvTitle.setText("设置");
        try {
            tvAche.setText(DataCleanManager.getTotalCacheSize(mContext));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void configViews() {

    }

    @OnClick({R.id.iv_back, R.id.rl_feedback,R.id.rl_clean, R.id.rl_about,R.id.tv_quit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_feedback:
                startActivity(new Intent(mContext,InfoFeedBackActivity.class));
                break;
            case R.id.rl_clean:
                SweetAlertDialog cleanDialog = CommonUtils.getConfirmDialog(mContext,
                        "是否清除缓存?",
                        new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                DataCleanManager.clearAllCache(getApplicationContext());
                                showToast("清除成功");
                                tvAche.setText("0KB");
                                sweetAlertDialog.dismiss();
                            }
                        });
                //显示dialog
                cleanDialog.show();
                break;
            case R.id.rl_about:
                startActivity(new Intent(mContext,AboutUsActivity.class));
                break;
            case R.id.tv_quit:
                SweetAlertDialog confirmDialog = CommonUtils.getConfirmDialog(mContext,
                        "是否退出登录?",
                        new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                EventBus.getDefault().post(
                                        new FirstEvent("MyCenterClick"));
                                finish();
                                AyjSwApplication.getsInstance().setUserInfo(null);
                                aCache.remove(ACache.USER_INFO_KEY);
                                sweetAlertDialog.dismiss();
                            }
                        });
                //显示dialog
                confirmDialog.show();
                break;
        }
    }
}

