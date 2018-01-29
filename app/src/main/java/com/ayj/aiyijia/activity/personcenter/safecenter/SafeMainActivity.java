package com.ayj.aiyijia.activity.personcenter.safecenter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ayj.aiyijia.AyjSwApplication;
import com.ayj.aiyijia.R;
import com.ayj.aiyijia.activity.base.BaseActivity;
import com.ayj.aiyijia.bean.Check;
import com.ayj.aiyijia.bean.eventbus.FirstEvent;
import com.ayj.aiyijia.utils.CommonUtils;
import com.ayj.aiyijia.utils.WebUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/*
* 安全中心
* */
public class SafeMainActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.pwd_xg)
    RelativeLayout pwdXg;
    @BindView(R.id.zf_set)
    RelativeLayout zfSet;
    @BindView(R.id.zf_xg)
    RelativeLayout zfXg;
    @BindView(R.id.mb_set)
    RelativeLayout mbSet;
    private boolean hasPaySafe;
    @Override
    public int getLayoutId() {
        return R.layout.activity_safe_main;
    }

    @Override
    public void initDatas() {
        EventBus.getDefault().register(this);
        tvTitle.setText("安全中心");
        if(AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getHaspaypwd().equals("1")){
            zfSet.setVisibility(View.GONE);
        }else{
            zfSet.setVisibility(View.VISIBLE);
        }
        getHasPwdSafe();
    }

    @Override
    public void configViews() {

    }

    @OnClick({R.id.pwd_xg, R.id.zf_set, R.id.zf_xg, R.id.mb_set,R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            /*修改密码*/
            case R.id.pwd_xg:
                startActivity(new Intent(mContext,XgPwdActivity.class));
                break;
            /*支付密码设置*/
            case R.id.zf_set:
                startActivity(new Intent(mContext,SetZfPwdActivity.class));
                break;
            /*支付密码修改*/
            case R.id.zf_xg:
                startActivity(new Intent(mContext,XgPayPwdActivity.class));
                break;
            /*密保设置*/
            case R.id.mb_set:
                if(hasPaySafe){
                    /*有密保*/
                    startActivity(new Intent(mContext,HavePwdBhActivity.class));
                }else{
                    /*木有密保*/
                    startActivity(new Intent(mContext,NoMbSafeActivity.class));
                }
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus
    }

    @Subscribe
    public void onEventMainThread(FirstEvent event) {
        if (event.getMsg().equals("SetZfPwd")) {
            if(AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getHaspaypwd().equals("1")){
                zfSet.setVisibility(View.GONE);
            }else{
                zfSet.setVisibility(View.VISIBLE);
            }
            hasPaySafe = true;
        }
    }

    private void getHasPwdSafe() {
        if (CommonUtils.isNetworkAvailable(mContext)) {
            mProgressHub.show();
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.HY_MB))
                    .addParams("json",
                            "{\"key\":\"\",\"msnid\":\"" + AyjSwApplication.getsInstance().getUserInfo().getData().get(
                                    0).getSnid() + "\",\"pwd\":\""
                                    + AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getPassWord()
                                    +"\",\"mobile\":\""+AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getMobile()+ "\"}")
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                           mProgressHub.dismiss();
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            mProgressHub.dismiss();
                            Check check = new Gson().fromJson(response, Check.class);
                            switch (check.getErr()) {
                                case 0:
                                    if (check.getTotal() == 0) {
                                        hasPaySafe = false;
                                    } else {
                                        hasPaySafe = true;
                                    }
                                    break;
                                default:
                                    showToast(check.getMsg());
                                    break;
                            }
                        }
                    });
        } else {
            showToast(R.string.TheNetIsUnAble);
        }
    }
}
