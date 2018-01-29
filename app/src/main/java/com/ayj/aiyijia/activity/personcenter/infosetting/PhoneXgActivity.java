package com.ayj.aiyijia.activity.personcenter.infosetting;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayj.aiyijia.AyjSwApplication;
import com.ayj.aiyijia.R;
import com.ayj.aiyijia.activity.base.BaseActivity;
import com.ayj.aiyijia.bean.Check;
import com.ayj.aiyijia.bean.UserBean;
import com.ayj.aiyijia.bean.eventbus.FirstEvent;
import com.ayj.aiyijia.utils.ACache;
import com.ayj.aiyijia.utils.CommonUtils;
import com.ayj.aiyijia.utils.MD5Utils;
import com.ayj.aiyijia.utils.ValidationUtils;
import com.ayj.aiyijia.utils.WebUtils;
import com.ayj.aiyijia.view.CountDownButtonHelper;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/*
* 修改手机号
* */
public class PhoneXgActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_ma)
    EditText etMa;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_yzm)
    EditText etYzm;
    @BindView(R.id.get_verificationCode)
    Button getVerificationCode;
    @BindView(R.id.btn_register)
    TextView btnRegister;

    @Override
    public int getLayoutId() {
        return R.layout.activity_phone_xg;
    }

    @Override
    public void initDatas() {
        tvTitle.setText("修改手机号");

    }

    @Override
    public void configViews() {

    }

    /**
     * 按钮倒计时
     */
    private void btnCountDown() {
        CountDownButtonHelper helper = new CountDownButtonHelper(getVerificationCode, "",
                60, 1);

        helper.setOnFinishListener(new CountDownButtonHelper.OnFinishListener() {

            @Override
            public void finish() {

            }
        });
        helper.start();
    }

    @OnClick({R.id.iv_back, R.id.get_verificationCode,R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.get_verificationCode:
                getYzNumber();
                break;
            case R.id.btn_register:
                doPost();
                break;
        }
    }

    /*获取验证码*/
    private void getYzNumber(){
        if(TextUtils.isEmpty(etPhone.getText().toString().trim())){
            showToast("手机号不能为空");
            return;
        }
        if(!ValidationUtils.checkTelPhone(etPhone.getText().toString().trim())){
            showToast("请输入正确的手机号");
            return;
        }
        if (CommonUtils.isNetworkAvailable(mContext)) {
            JSONObject obj = new JSONObject();
            try {
                obj.put("mobile",etPhone.getText().toString().trim());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mProgressHub.setMsg("请求验证码中...");
            mProgressHub.show();
            OkHttpUtils.post().
                    url(WebUtils.getRequestUrl(WebUtils.HQ_YZ))
                    .addParams("json", String.valueOf(obj))
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
                                    btnCountDown();
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

    /*获取验证码*/
    private void doPost(){
        if(TextUtils.isEmpty(etPhone.getText().toString().trim())){
            showToast("手机号不能为空");
            return;
        }
        if(!ValidationUtils.checkTelPhone(etPhone.getText().toString().trim())){
            showToast("请输入正确的手机号");
            return;
        }
        if(TextUtils.isEmpty(etMa.getText().toString().trim())){
            showToast("请输入密码");
            return;
        }
        if(TextUtils.isEmpty(etYzm.getText().toString().trim())){
            showToast("请输入验证码");
            return;
        }
        if (CommonUtils.isNetworkAvailable(mContext)) {
            JSONObject obj = new JSONObject();
            try {
                obj.put("msnid", AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getSnid());
                obj.put("pwd", MD5Utils.getMD5String(etMa.getText().toString().trim()));
                obj.put("mobilenew",etPhone.getText().toString().trim());
                obj.put("vcode",etYzm.getText().toString().trim());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mProgressHub.setMsg("正在修改中请稍后...");
            mProgressHub.show();
            OkHttpUtils.post().
                    url(WebUtils.getRequestUrl(WebUtils.XG_PHONE))
                    .addParams("json", String.valueOf(obj))
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
                                    doLogin(2);
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
