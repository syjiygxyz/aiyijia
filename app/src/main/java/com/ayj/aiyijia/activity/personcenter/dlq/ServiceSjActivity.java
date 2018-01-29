package com.ayj.aiyijia.activity.personcenter.dlq;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ayj.aiyijia.AyjSwApplication;
import com.ayj.aiyijia.R;
import com.ayj.aiyijia.activity.base.BaseActivity;
import com.ayj.aiyijia.bean.Check;
import com.ayj.aiyijia.bean.eventbus.FirstEvent;
import com.ayj.aiyijia.utils.CommonUtils;
import com.ayj.aiyijia.utils.ValidationUtils;
import com.ayj.aiyijia.utils.WebUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/*
* 添加代理
* */
public class ServiceSjActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_add_agency_shangji)
    TextView tvAddAgencyShangji;
    @BindView(R.id.et_add_agency_phone)
    EditText etAddAgencyPhone;
    @BindView(R.id.btn_add_agency_sure)
    Button btnAddAgencySure;

    @Override
    public int getLayoutId() {
        return R.layout.activity_service_sj;
    }

    @Override
    public void initDatas() {
        tvTitle.setText("添加代理");
    }

    @Override
    public void configViews() {

    }

    @OnClick({R.id.iv_back, R.id.btn_add_agency_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_add_agency_sure:
                doClick();
                break;
        }
    }
    private void doPost(){
        if(CommonUtils.isNetworkAvailable(mContext)){
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.HY_SJ))
                    .addParams("json","{\"key\":\"\",\"msnid\":\""+ AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getSnid()+"\","
                            + "\"pwd\":\""+AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getPassWord()+"\","
                            + "\"pmobile\":\""+etAddAgencyPhone.getText().toString().trim()+"\"}")
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(String response, int id) {
                            Check check = new Gson().fromJson(response,Check.class);
                            switch (check.getErr()){
                                case 0:
                                    showToast("添加成功");
                                    EventBus.getDefault().post(
                                            new FirstEvent("ServiceSj"));
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

    private void doClick(){
        if(TextUtils.isEmpty(etAddAgencyPhone.getText().toString().trim())){
            showToast("请填写手机号");
            return;
        }
        if(!ValidationUtils.checkTelPhone(etAddAgencyPhone.getText().toString().trim())){
            showToast("请填写正确手机号");
        }
        doPost();
    }
}
