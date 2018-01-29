package com.ayj.aiyijia.activity.personcenter.safecenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
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
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/*
* 修改支付密码
* */
public class XgPayPwdActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right_head)
    TextView tvRightHead;
    @BindView(R.id.et_ma)
    EditText etMa;
    @BindView(R.id.et_ma_new)
    EditText etMaNew;
    @BindView(R.id.et_ma_sure)
    EditText etMaSure;
    @BindView(R.id.btn_register)
    TextView btnRegister;

    @Override
    public int getLayoutId() {
        return R.layout.activity_xg_pay_pwd;
    }

    @Override
    public void initDatas() {
        EventBus.getDefault().register(this);
        tvTitle.setText("修改支付密码");
        tvRightHead.setVisibility(View.VISIBLE);
        tvRightHead.setText("忘记密码");
    }

    @Override
    public void configViews() {

    }

    @OnClick({R.id.iv_back, R.id.btn_register,R.id.tv_right_head})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_register:
                doClick();
                break;
            case R.id.tv_right_head:
                startActivity(new Intent(mContext,ZhZfPwdActivity.class));
                break;
        }
    }

    private void doClick(){
        if(TextUtils.isEmpty(etMa.getText().toString().trim())){
            showToast("请输入原支付密码");
            return;
        }
        if(TextUtils.isEmpty(etMaNew.getText().toString().trim())){
            showToast("请输入新支付密码");
            return;
        }
        if (etMa.getText().toString().trim().length() != 6) {
            showToast("支付密码必须为6位");
            return;
        }
        if(TextUtils.isEmpty(etMaSure.getText().toString().trim())){
            showToast("请确定新支付密码");
            return;
        }
        if(!etMaSure.getText().toString().trim().equals(etMaNew.getText().toString().trim())){
            showToast("输入两次支付密码不一致");
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
                obj.put("paypwdold", MD5Utils.getMD5String(etMa.getText().toString().trim()));
                obj.put("paypwdnew",MD5Utils.getMD5String(etMaNew.getText().toString().trim()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mProgressHub.show();
            OkHttpUtils.post().
                    url(WebUtils.getRequestUrl(WebUtils.XG_ZF))
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
                                    showToast("修改成功");
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus
    }
    @Subscribe
    public void onEventMainThread(FirstEvent event) {
        if (event.getMsg().equals("ZhZfPwd")) {
            finish();
        }
    }
}
