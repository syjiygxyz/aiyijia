package com.ayj.aiyijia.activity.login;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayj.aiyijia.R;
import com.ayj.aiyijia.activity.base.BaseActivity;
import com.ayj.aiyijia.bean.Check;
import com.ayj.aiyijia.bean.XyBean;
import com.ayj.aiyijia.bean.eventbus.FirstEvent;
import com.ayj.aiyijia.utils.CommonUtils;
import com.ayj.aiyijia.utils.Constant;
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
import butterknife.OnClick;
import okhttp3.Call;

/*
* 注册
* */
public class ZcActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_right)
    TextView mTvRight;
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
    @BindView(R.id.et_jz)
    EditText mEtJz;//居住地址
    @BindView(R.id.cb_register_agreement)
    CheckBox mCbRegisterAgreement;
    @BindView(R.id.tv_register_serviceAgreement)
    TextView mTvRegisterServiceAgreement;//用户协议
    @BindView(R.id.btn_register)
    TextView mBtnRegister;//完成按钮

    XyBean mXyBean;

    Dialog dialog;



    @Override
    public int getLayoutId() {
        return R.layout.activity_zc;
    }

    @Override
    public void initDatas() {
        /*check点击事件*/
        mCbRegisterAgreement.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            mBtnRegister.setBackground(getResources().getDrawable(R.drawable.blue_button_background));
                            mBtnRegister.setEnabled(true);
                        }else{
                            mBtnRegister.setBackground(getResources().getDrawable(R.drawable.grey_no_check_background));
                            mBtnRegister.setEnabled(false);
                        }
                    }
                });
    }

    @Override
    public void configViews() {

    }

    @OnClick({R.id.iv_back, R.id.get_verificationCode,
            R.id.tv_register_serviceAgreement, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.get_verificationCode:
                /*获取验证码*/
                getYzNumber();
                break;
            case R.id.tv_register_serviceAgreement:
                /*点击协议*/
                mProgressHub.setMsg("正在加载中...");
                mProgressHub.show();
                getXz();
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
            showToast("请输入正确的手机号");
            return;
        }
        if (CommonUtils.isNetworkAvailable(mContext)) {
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
        if(TextUtils.isEmpty(mEtJz.getText().toString().trim())){
            showToast("居住地点不能为空");
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
    /*注册*/
    private void btnRegister() {
        if (CommonUtils.isNetworkAvailable(mContext)) {
            JSONObject obj = new JSONObject();
            try {
                obj.put("key", "");
                obj.put("mobile",mEtPhone.getText().toString().trim());
                obj.put("pwd", MD5Utils.getMD5String(mEtMa.getText().toString().trim()));
                obj.put("vcode",mEtYzm.getText().toString().trim());
                obj.put("homeaddr",mEtJz.getText().toString().trim());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mProgressHub.setMsg("正在注册中...");
            mProgressHub.show();
            OkHttpUtils.post().
                    url(WebUtils.getRequestUrl(WebUtils.ZC_URL))
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
    /*点击协议*/
    private void getXz(){
        if (CommonUtils.isNetworkAvailable(mContext)) {
            JSONObject obj = new JSONObject();
            try {
                obj.put("key", "");
                obj.put("snid", Constant.ZC_XY);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mProgressHub.show();
            OkHttpUtils.post().
                    url(WebUtils.getRequestUrl(WebUtils.XY_URL))
                    .addParams("json", String.valueOf(obj))
                    .build()
                    .execute(new StringCallback(){
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
                                    mXyBean = new Gson().fromJson(response, XyBean.class);
                                    showXyDialog(mXyBean);
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
    /*
    * 协议dialog
    * */
    public void showXyDialog(XyBean xyBean){
        dialog = new Dialog(mContext, R.style.base_dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.xy_item);
    /*设置标题*/
        TextView tv = (TextView) dialog.findViewById(R.id.tv_title);
        TextView tv1 = (TextView) dialog.findViewById(R.id.tv_content);
        tv.setText(xyBean.getData().get(0).getTitle());
        tv1.setText(Html.fromHtml(xyBean.getData().get(0).getAcomment()));
        TextView btn_register = (TextView) dialog.findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus
    }
}

