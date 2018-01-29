package com.ayj.aiyijia.activity.personcenter.dd;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ayj.aiyijia.AyjSwApplication;
import com.ayj.aiyijia.R;
import com.ayj.aiyijia.activity.base.BaseActivity;
import com.ayj.aiyijia.bean.Check;
import com.ayj.aiyijia.bean.OrderDetail;
import com.ayj.aiyijia.bean.eventbus.FirstEvent;
import com.ayj.aiyijia.utils.CommonUtils;
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
* 订单评论界面
* */
public class DdCommonentActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_evaluate_time)
    TextView tvEvaluateTime;
    @BindView(R.id.rb_evaluate)
    RatingBar rbEvaluate;
    @BindView(R.id.et_evaluate)
    EditText etEvaluate;
    @BindView(R.id.cb_evaluate)
    CheckBox cbEvaluate;
    @BindView(R.id.btn_evaluate_sure)
    Button btnEvaluateSure;

    private String sfnm = "SFPD002";

    OrderDetail.DataBean.OrderDetailListBean item;

    private int pf = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_dd_commonent;
    }

    @Override
    public void initDatas() {
        tvTitle.setText("评论");
        item = (OrderDetail.DataBean.OrderDetailListBean) getIntent().getSerializableExtra("info");
        tvEvaluateTime.setText("订单账号"+item.getSnid());
    }

    @Override
    public void configViews() {
        cbEvaluate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sfnm = "SFPD001";
                } else {
                    sfnm = "SFPD002";
                }
            }
        });
        rbEvaluate.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                pf = (int)v;
            }
        });

    }

    @OnClick({R.id.iv_back, R.id.btn_evaluate_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_evaluate_sure:
                postPl();
                break;
        }
    }

    private void postPl() {
        if(TextUtils.isEmpty(etEvaluate.getText().toString().trim())){
            showToast("请添加评论");
            return;
        }
        if (CommonUtils.isNetworkAvailable(mContext)){
            mProgressHub.show();
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.SH_PJ))
                    .addParams("json", "{\"key\":\"\",\"msnid\":\""
                            + AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getSnid()
                            + "\",\"pwd\":\"" + AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getPassWord()
                            + "\",\"orderdetailsnid\":\"" + item.getDetailsnid() + "\",\"sfnm\":\""
                            + sfnm + "\",\"pf\":\"" + pf + "\",\"fk\":\"" + etEvaluate.getText().toString().trim() + "\"}")
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
                                    showToast("提交成功");
                                    EventBus.getDefault().post(
                                            new FirstEvent("Dd"));
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

