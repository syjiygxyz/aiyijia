package com.ayj.aiyijia.activity.personcenter.safecenter;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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
import com.ayj.aiyijia.utils.WebUtils;
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
* 设置支付密码
* */
public class SetZfPwdActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_ma)
    EditText etMa;
    @BindView(R.id.et_ma_new)
    EditText etMaNew;
    @BindView(R.id.btn_register)
    TextView btnRegister;
    @BindView(R.id.tv_right_head)
    TextView tvRightHead;

    @Override
    public int getLayoutId() {
        return R.layout.activity_set_zf_pwd;
    }

    @Override
    public void initDatas() {
        tvTitle.setText("设置支付密码");

    }

    @Override
    public void configViews() {

    }

    @OnClick({R.id.iv_back, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_register:
                doClick();
                break;
        }
    }

    private void doClick() {
        if (TextUtils.isEmpty(etMa.getText().toString().trim())) {
            showToast("请输入支付密码");
            return;
        }
        if (etMa.getText().toString().trim().length() != 6) {
            showToast("支付密码必须为6位");
            return;
        }
        if (TextUtils.isEmpty(etMaNew.getText().toString().trim())) {
            showToast("请确认支付密码");
            return;
        }
        if (!etMaNew.getText().toString().trim().equals(etMaNew.getText().toString().trim())) {
            showToast("输入两次密码不一致");
            return;
        }
        doPost();
    }

    private void doPost() {
        if (CommonUtils.isNetworkAvailable(mContext)) {
            JSONObject obj = new JSONObject();
            try {
                obj.put("key", "");
                obj.put("msnid", AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getSnid());
                obj.put("pwd", AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getPassWord());
                obj.put("paypwd", MD5Utils.getMD5String(etMaNew.getText().toString().trim()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mProgressHub.show();
            OkHttpUtils.post().
                    url(WebUtils.getRequestUrl(WebUtils.ZF_PWD))
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
                                    UserBean userBean = AyjSwApplication.getsInstance().getUserInfo();
                                    userBean.getData().get(0).setHaspaypwd("1");
                                    AyjSwApplication.getsInstance().setUserInfo(userBean);
                                    aCache.put(ACache.USER_INFO_KEY, userBean);
                                    EventBus.getDefault().post(
                                            new FirstEvent("SetZfPwd"));
                                    showToast("设置成功");
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
