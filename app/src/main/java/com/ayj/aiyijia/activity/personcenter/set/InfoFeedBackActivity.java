package com.ayj.aiyijia.activity.personcenter.set;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ayj.aiyijia.AyjSwApplication;
import com.ayj.aiyijia.R;
import com.ayj.aiyijia.activity.base.BaseActivity;
import com.ayj.aiyijia.bean.Check;
import com.ayj.aiyijia.utils.CommonUtils;
import com.ayj.aiyijia.utils.WebUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
/*
* 反馈
* */
public class InfoFeedBackActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.editContent)
    EditText editContent;
    @BindView(R.id.cb)
    CheckBox cb;
    @BindView(R.id.tv_post)
    TextView tvPost;

    private String isanonymity = "SFPD002";

    @Override
    public int getLayoutId() {
        return R.layout.activity_info_feed_back;
    }

    @Override
    public void initDatas() {
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    isanonymity = "SFPD001";
                } else {
                    isanonymity = "SFPD002";
                }
            }
        });
    }

    @Override
    public void configViews() {

    }

    @OnClick({R.id.iv_back, R.id.tv_post})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_post:
                doPost();
                break;
        }
    }

    private void doPost(){
        if(TextUtils.isEmpty(editContent.getText().toString().trim())){
            showToast("请填写你的反馈信息");
        }else{
            if(CommonUtils.isNetworkAvailable(mContext)){
                mProgressHub.show();
                OkHttpUtils.post()
                        .url(WebUtils.getRequestUrl(WebUtils.HY_FK))
                        .addParams("json","{\"key\":\"\","
                                + "\"userid\":\""+ AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getSnid()+"\","
                                + "\"isanonymity\":\""+isanonymity+"\","
                                + "\"feedbackcontent\":\""+editContent.getText().toString().toString()+"\"}")
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                mProgressHub.dismiss();
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                mProgressHub.dismiss();
                                Check check = new Gson().fromJson(response,Check.class);
                                switch (check.getErr()){
                                    case 0:
                                        showToast("提交成功");
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

}
