package com.ayj.aiyijia.activity.personcenter.infosetting;

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
import com.ayj.aiyijia.utils.CommonUtils;
import com.ayj.aiyijia.utils.ValidationUtils;
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
* 实名认证
* */
public class NameRzActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_name)
    EditText tvName;
    @BindView(R.id.tv_card)
    EditText tvCard;
    @BindView(R.id.tv_card_show)
    EditText tvCardShow;
    @BindView(R.id.btn_register)
    TextView btnRegister;

    @Override
    public int getLayoutId() {
        return R.layout.activity_name_rz;
    }

    @Override
    public void initDatas() {
        tvTitle.setText("实名认证");
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
                doPost();
                break;
        }
    }

    private void doPost(){
        if(TextUtils.isEmpty(tvName.getText().toString().trim())){
            showToast("请填写你的真实姓名");
            return;
        }
        if(TextUtils.isEmpty(tvCard.getText().toString().trim())){
            showToast("请填写你的身份证");
            return;
        }
        if(!ValidationUtils.checkCard(tvCard.getText().toString().trim())){
            showToast("请判断你的身份证是否准确");
            return;
        }
        if(!tvCard.getText().toString().trim().equals(tvCardShow.getText().toString().trim())){
            showToast("请检查两次输入的身份证号是否相同");
            return;
        }
        Zlxll();
    }

    private void Zlxll(){
        if (CommonUtils.isNetworkAvailable(mContext)) {
            JSONObject obj = new JSONObject();
            try {
                obj.put("key","");
                obj.put("userid", AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getSnid());
                obj.put("pwd",AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getPassWord());
                obj.put("name", tvName.getText().toString().trim());
                obj.put("idcard", tvCardShow.getText().toString().trim());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mProgressHub.show();
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.XIU_GAI))
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
                                    doLogin(3);
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
