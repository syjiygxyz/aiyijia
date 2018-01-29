package com.ayj.aiyijia.fragment.login;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.ayj.aiyijia.R;
import com.ayj.aiyijia.bean.Check;
import com.ayj.aiyijia.fragment.base.LazyFragment;
import com.ayj.aiyijia.utils.CommonUtils;
import com.ayj.aiyijia.utils.MD5Utils;
import com.ayj.aiyijia.utils.ValidationUtils;
import com.ayj.aiyijia.utils.WebUtils;
import com.ayj.aiyijia.view.CountDownButtonHelper;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/8/8.
 * 手机找回
 */
public class MoblieGetFragment extends LazyFragment {

    @BindView(R.id.et_phone)
    EditText mEtPhone;//手机号
    @BindView(R.id.et_yzm)
    EditText mEtYzm;//验证码
    @BindView(R.id.get_verificationCode)
    Button mGetVerificationCode;//发送验证码
    @BindView(R.id.et_ma)
    EditText mEtMa;//密码
    @BindView(R.id.et_ma_sure)
    EditText mEtMaSure;//确认密码
    @BindView(R.id.btn_register)
    TextView mBtnRegister;//完成按钮

    @Override
    public void fetchData() {

    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pwd_zh;
    }

    @OnClick({R.id.get_verificationCode, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.get_verificationCode:
                /*获取验证码*/
                getYzNumber();
                break;
            case R.id.btn_register:
                /*完成注册*/
                doCheck();
                break;
        }
    }

    /**
     * 按钮倒计时
     */
    private void btnCountDown() {
        CountDownButtonHelper helper = new CountDownButtonHelper(mGetVerificationCode, "",
                60, 1);

        helper.setOnFinishListener(new CountDownButtonHelper.OnFinishListener() {

            @Override
            public void finish() {

            }
        });
        helper.start();
    }
    /*获取验证码*/
    private void getYzNumber(){
        if(TextUtils.isEmpty(mEtPhone.getText().toString().trim())){
            showToast("手机号不能为空");
            return;
        }
        if(!ValidationUtils.checkTelPhone(mEtPhone.getText().toString().trim())){
            showToast("手机号非法");
            return;
        }
        if(!ValidationUtils.checkTelPhone(mEtPhone.getText().toString().trim())){
            showToast("请输入正确的验证码");
            return;
        }
        if (CommonUtils.isNetworkAvailable(getActivity())) {
            JSONObject obj = new JSONObject();
            try {
                obj.put("mobile",mEtPhone.getText().toString().trim());
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
    /*验证注册*/
    private void doCheck(){
        if(TextUtils.isEmpty(mEtPhone.getText().toString().trim())){
            showToast("手机号不能为空");
            return;
        }
        if(TextUtils.isEmpty(mEtYzm.getText().toString().trim())){
            showToast("验证码不能为空");
            return;
        }
        if(TextUtils.isEmpty(mEtMa.getText().toString().trim())){
            showToast("密码不能为空");
            return;
        }
        if(TextUtils.isEmpty(mEtMaSure.getText().toString().trim())){
            showToast("确认密码不能为空");
            return;
        }
        if(!mEtMa.getText().toString().trim().equals(mEtMaSure.getText().toString().trim())){
            showToast("两次输入的密码不一致");
            return;
        }
        if(!ValidationUtils.checkTelPhone(mEtPhone.getText().toString().trim())){
            showToast("请输入正确的手机号");
            return;
        }
        btnRegister();
    }
    /*手机找回*/
    private void btnRegister() {
        if (CommonUtils.isNetworkAvailable(getActivity())) {
            JSONObject obj = new JSONObject();
            try {
                obj.put("key", "");
                obj.put("mobile",mEtPhone.getText().toString().trim());
                obj.put("pwd", MD5Utils.getMD5String(mEtMa.getText().toString().trim()));
                obj.put("vcode",mEtYzm.getText().toString().trim());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mProgressHub.setMsg("正在找回中...");
            mProgressHub.show();
            OkHttpUtils.post().
                    url(WebUtils.getRequestUrl(WebUtils.PHONE_GET))
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
                                    getActivity().finish();
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
