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
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/*
* 修改密码
* */
public class XgPwdActivity extends BaseActivity {

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
        return R.layout.activity_xg_pwd;
    }

    @Override
    public void initDatas() {
        tvTitle.setText("修改密码");
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

    private void doClick(){
         if(TextUtils.isEmpty(etMa.getText().toString().trim())){
             showToast("请输入原密码");
             return;
         }
        if(TextUtils.isEmpty(etMaNew.getText().toString().trim())){
            showToast("请输入新密码");
            return;
        }
        if(TextUtils.isEmpty(etMaSure.getText().toString().trim())){
            showToast("请确定新密码");
            return;
        }
        if(!etMaSure.getText().toString().trim().equals(etMaNew.getText().toString().trim())){
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
                obj.put("userid", AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getSnid());
                obj.put("oldpwd", MD5Utils.getMD5String(etMa.getText().toString().trim()));
                obj.put("newpwd",MD5Utils.getMD5String(etMaNew.getText().toString().trim()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mProgressHub.show();
            OkHttpUtils.post().
                    url(WebUtils.getRequestUrl(WebUtils.HY_CHANGE))
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
                                    userBean.getData().get(0).setPassWord(MD5Utils.getMD5String(etMaNew.getText().toString().trim()));
                                    AyjSwApplication.getsInstance().setUserInfo(userBean);
                                    aCache.put(ACache.USER_INFO_KEY,userBean);
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
}
