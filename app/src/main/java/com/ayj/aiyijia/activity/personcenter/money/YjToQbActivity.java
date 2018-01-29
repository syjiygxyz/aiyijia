package com.ayj.aiyijia.activity.personcenter.money;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ayj.aiyijia.AyjSwApplication;
import com.ayj.aiyijia.R;
import com.ayj.aiyijia.activity.base.BaseActivity;
import com.ayj.aiyijia.bean.Check;
import com.ayj.aiyijia.bean.Company;
import com.ayj.aiyijia.bean.eventbus.FirstEvent;
import com.ayj.aiyijia.utils.CommonUtils;
import com.ayj.aiyijia.utils.WebUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/*
* 佣金到钱包
* */
public class YjToQbActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et)
    EditText et;
    @BindView(R.id.tv_post)
    TextView tvPost;

    @Override
    public int getLayoutId() {
        return R.layout.activity_yj_to_qb;
    }

    @Override
    public void initDatas() {
        tvTitle.setText("转到会员钱包");
    }

    @Override
    public void configViews() {

    }

    @OnClick({R.id.iv_back, R.id.tv_post})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_post:
                doPost();
                break;
        }
    }

    private void doPost(){
        if(TextUtils.isEmpty(et.getText().toString().trim())){
            showToast("请输入你的转出金额");
        }else{
            if(CommonUtils.isNetworkAvailable(mContext)){
                mProgressHub.show();
                OkHttpUtils.post()
                        .url(WebUtils.getRequestUrl(WebUtils.YJ_TO_QB))
                        .addParams("json","{\"key\":\"\","
                                + "\"msnid\":\""+ AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getSnid()+"\","
                                + "\"pwd\":\""+AyjSwApplication.getsInstance().getUserInfo().getData().get(
                                0).getPassWord()+"\","
                                + "\"transfercommissionmoney\":\""+et.getText().toString().toString()+"\"}")
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                mProgressHub.dismiss();
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                mProgressHub.dismiss();
                                Check check = new Gson().fromJson(response,Check.class);
                                switch (check.getErr()){
                                    case 0:
                                        showToast("转出成功");
                                        EventBus.getDefault().post(
                                                new FirstEvent("YJTOQB"));
                                        finish();
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
}
