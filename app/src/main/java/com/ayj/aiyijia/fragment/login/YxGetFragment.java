package com.ayj.aiyijia.fragment.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ayj.aiyijia.R;
import com.ayj.aiyijia.bean.Check;
import com.ayj.aiyijia.fragment.base.LazyFragment;
import com.ayj.aiyijia.utils.CommonUtils;
import com.ayj.aiyijia.utils.ValidationUtils;
import com.ayj.aiyijia.utils.WebUtils;
import com.ayj.aiyijia.view.Code;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/8/8.
 * 邮箱找回
 */
public class YxGetFragment extends LazyFragment {


    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_yzm)
    EditText etYzm;
    @BindView(R.id.iv_showCode)
    ImageView ivShowCode;
    @BindView(R.id.btn_register)
    TextView btnRegister;

    String realCode;

    @Override
    public void fetchData() {
        //将验证码用图片的形式显示出来
        ivShowCode.setImageBitmap(Code.getInstance().createBitmap());
        realCode = Code.getInstance().getCode().toLowerCase();
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_yx_get;
    }

    @OnClick({R.id.ll, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll:
                ivShowCode.setImageBitmap(Code.getInstance().createBitmap());
                realCode = Code.getInstance().getCode().toLowerCase();
                break;
            case R.id.btn_register:
                doCheck();
                break;
        }
    }

    private void postYx(){
        if (CommonUtils.isNetworkAvailable(getActivity())) {
            mProgressHub.show();
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.YX_ZH))
                    .addParams("json",
                            "{\"key\":\"\",\"email\":\"" + etPhone.getText().toString().trim() + "\"}")
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            mProgressHub.dismiss();
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            mProgressHub.dismiss();
                            Check check = new Gson().fromJson(response,
                                    Check.class);
                            Toast.makeText(getActivity(), check.getMsg(),
                                    Toast.LENGTH_SHORT).show();
                            getActivity().finish();
                        }
                    });
        } else {
            showToast(R.string.TheNetIsUnAble);
        }
    }

    private void doCheck(){
        if(TextUtils.isEmpty(etPhone.getText().toString().trim())){
            showToast("请填写你的邮箱");
            return;
        }
        if(!ValidationUtils.checkEmail(etPhone.getText().toString().trim())){
            showToast("请确认你的邮箱是否合法");
            return;
        }
        if(TextUtils.isEmpty(etYzm.getText().toString().trim())){
            showToast("请填写验证码");
            return;
        }
        if(!realCode.equals(etYzm.getText().toString().trim().toLowerCase())){
            showToast("验证码不正确");
            return;
        }
        postYx();
    }
}
