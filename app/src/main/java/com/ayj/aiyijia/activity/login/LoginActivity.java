package com.ayj.aiyijia.activity.login;

import android.content.Intent;
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
import com.ayj.aiyijia.utils.ACache;
import com.ayj.aiyijia.utils.CommonUtils;
import com.ayj.aiyijia.utils.MD5Utils;
import com.ayj.aiyijia.utils.WebUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/8/7.
 * 登陆界面
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ed_login_1)
    EditText edLogin1;
    @BindView(R.id.ed_login_2)
    EditText edLogin2;
    @BindView(R.id.tv_login_ok)
    TextView tvLoginOk;

    @BindView(R.id.tv_zc)
    TextView tv_zc;

    @BindView(R.id.tv_wj)
    TextView tv_wj;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initDatas() {
        tvTitle.setText("登陆");
    }

    @Override
    public void configViews() {

    }


    @OnClick({R.id.iv_back, R.id.tv_login_ok,R.id.tv_zc,R.id.tv_wj})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_login_ok:
                submit();
                break;
            case R.id.tv_zc:
                startActivity(new Intent(mContext,ZcActivity.class));
                break;
            case R.id.tv_wj:
                startActivity(new Intent(mContext,GetPwdActivity.class));
                break;
        }
    }

    private void submit() {
        if (TextUtils.isEmpty(edLogin1.getText().toString().trim())){
            showToast("请输入账号");
            return;
        }
        if (TextUtils.isEmpty(edLogin2.getText().toString().trim())){
            showToast("请输入密码");
            return;
        }
        doLoginn();
    }


    private void doLoginn() {
        if (CommonUtils.isNetworkAvailable(mContext)) {
            JSONObject obj = new JSONObject();
            try {
                obj.put("key","");
                obj.put("mobile",edLogin1.getText().toString().trim());
                obj.put("pwd", MD5Utils.getMD5String(edLogin2.getText().toString().trim()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mProgressHub.show();
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.GET_ZH_MA))
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
                            switch (check.getErr()){
                                case 0:
                                    UserBean userBean = new Gson().fromJson(response, UserBean.class);
                                    userBean.getData().get(0).setPassWord(MD5Utils.getMD5String(edLogin2.getText().toString().trim()));
                                    AyjSwApplication.getsInstance().setUserInfo(userBean);
                                    aCache.put(ACache.USER_INFO_KEY,userBean);
                                    showToast("登录成功");
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
