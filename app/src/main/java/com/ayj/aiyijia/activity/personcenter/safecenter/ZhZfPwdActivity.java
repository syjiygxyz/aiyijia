package com.ayj.aiyijia.activity.personcenter.safecenter;

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
import com.ayj.aiyijia.bean.eventbus.FirstEvent;
import com.ayj.aiyijia.utils.CommonUtils;
import com.ayj.aiyijia.utils.MD5Utils;
import com.ayj.aiyijia.utils.ValidationUtils;
import com.ayj.aiyijia.utils.WebUtils;
import com.ayj.aiyijia.view.CountDownButtonHelper;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/*
* 找回支付密码
* */
public class ZhZfPwdActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_yzm)
    EditText etYzm;
    @BindView(R.id.get_verificationCode)
    Button getVerificationCode;
    @BindView(R.id.et_ma)
    EditText etMa;
    @BindView(R.id.et_ma_sure)
    EditText etMaSure;
    @BindView(R.id.btn_register)
    TextView btnRegister;

    @Override
    public int getLayoutId() {
        return R.layout.activity_zh_zf_pwd;
    }

    @Override
    public void initDatas() {
        tvTitle.setText("找回支付密码");
    }

    @Override
    public void configViews() {

    }

    @OnClick({R.id.iv_back, R.id.get_verificationCode, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.get_verificationCode:
                /*获取验证码*/
                getYzNumber();
                break;
            case R.id.btn_register:
                doClick();
                break;
        }
    }

    private void doClick() {
        if (TextUtils.isEmpty(etPhone.getText().toString().trim())) {
            showToast("请输入手机号");
            return;
        }
        if (!ValidationUtils.checkTelPhone(etPhone.getText().toString().trim())) {
            showToast("请查看手机是否正确");
            return;
        }
        if (TextUtils.isEmpty(etMa.getText().toString().trim())) {
            showToast("请输入支付密码");
            return;
        }
        if (TextUtils.isEmpty(etMaSure.getText().toString().trim())) {
            showToast("请确认支付密码");
            return;
        }
        if (etMa.getText().toString().length()!=6) {
            showToast("支付密码必须为6位");
            return;
        }
        if (!etMa.getText().toString().trim().equals(etMaSure.getText().toString().trim())) {
            showToast("输入两次密码不一致");
            return;
        }
        doPost();
    }

    private void doPost() {
        if (CommonUtils.isNetworkAvailable(mContext)) {
            JSONObject obj = new JSONObject();
            try {
                obj.put("key","");
                obj.put("msnid", AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getSnid());
                obj.put("pwd",AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getPassWord());
                obj.put("mobile", etPhone.getText().toString().trim());
                obj.put("paypwd", MD5Utils.getMD5String(etMa.getText().toString().trim()));
                obj.put("vcode", etYzm.getText().toString().trim());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mProgressHub.show();
            OkHttpUtils.post().
                    url(WebUtils.getRequestUrl(WebUtils.ZH_ZF_ZH))
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
                                    showToast(check.getMsg());
                                    EventBus.getDefault().post(
                                            new FirstEvent("ZhZfPwd"));
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
}
