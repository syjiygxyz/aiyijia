package com.ayj.aiyijia.activity.home.bx.bxfk;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayj.aiyijia.AyjSwApplication;
import com.ayj.aiyijia.R;
import com.ayj.aiyijia.activity.base.BaseActivity;
import com.ayj.aiyijia.adapter.base.BaseQuickAdapter;
import com.ayj.aiyijia.adapter.base.listener.OnItemClickListener;
import com.ayj.aiyijia.adapter.bx.InsuranceFeedbackAdapter;
import com.ayj.aiyijia.bean.Check;
import com.ayj.aiyijia.bean.cmbz.InsurancePriceBean;
import com.ayj.aiyijia.bean.cmbz.UserPerson;
import com.ayj.aiyijia.utils.CommonUtils;
import com.ayj.aiyijia.utils.Constant;
import com.ayj.aiyijia.utils.WebUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.finalteam.loadingviewfinal.OnDefaultRefreshListener;
import cn.finalteam.loadingviewfinal.PtrClassicFrameLayout;
import cn.finalteam.loadingviewfinal.PtrFrameLayout;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/7/24.
 * 报价反馈
 */
public class InsuranceFeedbackActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.feedback_recycler)
    RecyclerView feedbackRecycler;
    @BindView(R.id.ptr_fresh)
    PtrClassicFrameLayout ptrFresh;

    InsuranceFeedbackAdapter adapter;

    List<InsurancePriceBean.DataBean> list = new ArrayList<>();



    @Override
    public int getLayoutId() {
        return R.layout.activity_insurance_feedback;
    }

    @Override
    public void initDatas() {
        tvTitle.setText("报价反馈");
        feedbackRecycler.setLayoutManager(new LinearLayoutManager(mContext));
    }

    @Override
    public void configViews() {
        getList();
        ptrFresh.setOnRefreshListener(new OnDefaultRefreshListener() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                list.clear();
                getList();
            }
        });
        feedbackRecycler.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (Constant.QUOTED_PRICE.equals(list.get(position).getStatus()) || Constant.WAITE_PAY.equals(list.get(position).getStatus())) {
                    Intent intent = new Intent(mContext, InsurancePriceActivity.class);
                    intent.putExtra("dataBean",(Serializable)list.get(position));
                    startActivity(intent);
                }
            }
        });
    }

    /*
    * 获取报价列表
    * */
    private void getList() {
        if (CommonUtils.isNetworkAvailable(mContext)) {
            JSONObject obj = new JSONObject();
            try {
                obj.put("key", "");
                obj.put("msnid", AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getSnid());
                obj.put("pwd", AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getPassWord());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            mProgressHub.show();
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.GET_INSURANCE_PRICE))
                    .addParams("json", String.valueOf(obj))
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            mProgressHub.dismiss();
                            ptrFresh.onRefreshComplete();
                        }

                        @Override
                        public void onResponse(String response, int id) {

                            mProgressHub.dismiss();
                            ptrFresh.onRefreshComplete();
                            Check check = new Gson().fromJson(response, Check.class);
                            switch (check.getErr()) {
                                case 0:
                                    InsurancePriceBean insurancePriceBean = new Gson().fromJson(response, InsurancePriceBean.class);
                                    list.addAll(insurancePriceBean.getData());
                                    if (null == adapter) {
                                        adapter = new InsuranceFeedbackAdapter(list);
                                        feedbackRecycler.setAdapter(adapter);
                                    } else {
                                        adapter.setNewData(list);
                                    }
                                    break;
                                default:
                                    showToast(check.getMsg());
                            }
                        }
                    });
        } else {
            showToast(R.string.TheNetIsUnAble);
        }
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
