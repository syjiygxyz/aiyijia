package com.ayj.aiyijia.activity.personcenter.safecenter;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayj.aiyijia.AyjSwApplication;
import com.ayj.aiyijia.R;
import com.ayj.aiyijia.activity.base.BaseActivity;
import com.ayj.aiyijia.bean.Check;
import com.ayj.aiyijia.bean.Problem;
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
* 有密保问题
* */
public class HavePwdBhActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_my_problem1)
    TextView tvMyProblem1;
    @BindView(R.id.tv_my_answer1)
    TextView tvMyAnswer1;
    @BindView(R.id.tv_my_problem2)
    TextView tvMyProblem2;
    @BindView(R.id.tv_my_answer2)
    TextView tvMyAnswer2;

    @Override
    public int getLayoutId() {
        return R.layout.activity_have_pwd_bh;
    }

    @Override
    public void initDatas() {
        tvTitle.setText("我的密保问题");
    }

    @Override
    public void configViews() {
        getHasPwdSafe();
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

    private void getHasPwdSafe() {
        if (CommonUtils.isNetworkAvailable(mContext)) {
            mProgressHub.dismiss();
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.HY_MB))
                    .addParams("json",
                            "{\"key\":\"\",\"msnid\":\"" + AyjSwApplication.getsInstance().getUserInfo().getData().get(
                                    0).getSnid() + "\",\"pwd\":\""
                                    + AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getPwd()
                                    +"\",\"mobile\":\""+AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getMobile()+ "\"}")
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
                                    Problem problem = new Gson().fromJson(response, Problem.class);
                                    tvMyProblem1.setText(problem.getData().get(0).getQuestion());
                                    tvMyProblem2.setText(problem.getData().get(1).getQuestion());
                                    tvMyAnswer1.setText("******");
                                    tvMyAnswer2.setText("******");
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
