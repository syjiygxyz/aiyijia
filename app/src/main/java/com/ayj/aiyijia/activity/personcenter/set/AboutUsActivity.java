package com.ayj.aiyijia.activity.personcenter.set;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.ayj.aiyijia.R;
import com.ayj.aiyijia.activity.base.BaseActivity;
import com.ayj.aiyijia.bean.Check;
import com.ayj.aiyijia.bean.Company;
import com.ayj.aiyijia.utils.WebUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/*
* 关于我们
* */
public class AboutUsActivity extends BaseActivity {


    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.tv_aboutus_name)
    TextView tvAboutusName;
    @BindView(R.id.tv_aboutus_addr)
    TextView tvAboutusAddr;
    @BindView(R.id.tv_aboutus_web)
    TextView tvAboutusWeb;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    public int getLayoutId() {
        return R.layout.activity_about_us;
    }

    @Override
    public void initDatas() {
        tvTitle.setText("关于我们");
        getAboutUs();
    }

    @Override
    public void configViews() {

    }

    /**
     * 获取关于我们信息
     */
    private void getAboutUs() {
        mProgressHub.show();
        OkHttpUtils.post()
                .url(WebUtils.getRequestUrl(WebUtils.ABOUT_US))
                .addParams("json", "{\"key\":\"\",\"appname\":\"bf\"}")
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
                                Company company = new Gson().fromJson(response, Company.class);
                                tvAboutusName.setText(company.getData().get(0).getCompname());
                                tvAboutusAddr.setText(company.getData().get(0).getCompaddr());
                                tvAboutusWeb.setText(company.getData().get(0).getCompwebsite());
                                break;
                            default:
                                Toast.makeText(AboutUsActivity.this, check.getMsg(),
                                        Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}