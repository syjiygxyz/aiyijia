package com.ayj.aiyijia.activity.personcenter.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayj.aiyijia.AyjSwApplication;
import com.ayj.aiyijia.R;
import com.ayj.aiyijia.activity.base.BaseActivity;
import com.ayj.aiyijia.activity.personcenter.money.YjToQbActivity;
import com.ayj.aiyijia.adapter.personcenter.ChangMoneyAdapter;
import com.ayj.aiyijia.bean.ChangeMoneyBean;
import com.ayj.aiyijia.bean.Check;
import com.ayj.aiyijia.bean.eventbus.FirstEvent;
import com.ayj.aiyijia.utils.CommonUtils;
import com.ayj.aiyijia.utils.Constant;
import com.ayj.aiyijia.utils.WebUtils;
import com.ayj.aiyijia.view.DividerItemDecoration;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.loadingviewfinal.OnDefaultRefreshListener;
import cn.finalteam.loadingviewfinal.OnLoadMoreListener;
import cn.finalteam.loadingviewfinal.PtrClassicFrameLayout;
import cn.finalteam.loadingviewfinal.PtrFrameLayout;
import cn.finalteam.loadingviewfinal.RecyclerViewFinal;
import okhttp3.Call;

/*
* 个人中心中间的会员钱包等截面1为爱意比2.为零钱3金币4佣金
* */
public class CommonMoneyLsActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.recycler_view)
    RecyclerViewFinal mRecyclerView;
    @BindView(R.id.pcf_refresh)
    PtrClassicFrameLayout mPcfRefresh;

    ChangeMoneyBean bean;

    List<ChangeMoneyBean.DataBean> list = new ArrayList<>();

    ChangMoneyAdapter adapter;

    int pageNo = 1;

    int pageSize = 12;

    String accountType;
    String type;
    @BindView(R.id.tv_title1)
    TextView tvTitle1;
    @BindView(R.id.tv_title2)
    TextView tvTitle2;
    @BindView(R.id.tv_right_head)
    TextView tvRightHead;

    public static void startActivity(Context cxt, String type) {
        Intent intent = new Intent(cxt, CommonMoneyLsActivity.class);
        intent.putExtra("type", type);
        cxt.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_common_money_ls;
    }

    @Override
    public void initDatas() {
        EventBus.getDefault().register(this);
        type = getIntent().getStringExtra("type");
        if (type.equals("1")) {
            tvTitle.setText("爱e币流水");
            tvTitle1.setText("订单积分摘要");
            tvTitle2.setText("爱e币量");
            accountType = Constant.MY_JF;
        } else if (type.equals("2")) {
            tvTitle.setText("会员钱包流水");
            tvTitle1.setText("订单钱包摘要");
            tvTitle2.setText("零钱");
            accountType = Constant.MY_LQ;
        } else if (type.equals("3")) {
            tvTitle.setText("会员金币流水");
            tvTitle1.setText("订单金币摘要");
            tvTitle2.setText("金币量");
            accountType = Constant.MY_JB;
        } else if (type.equals("4")) {
            tvTitle.setText("会员佣金流水");
            tvTitle1.setText("订单佣金摘要");
            tvTitle2.setText("佣金");
            accountType = Constant.MY_YJ;
        }
        tvRightHead.setText("转出");
        if(type.equals("4")){
            tvRightHead.setVisibility(View.VISIBLE);
        }else{
            tvRightHead.setVisibility(View.GONE);
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext,
                DividerItemDecoration.VERTICAL_LIST));

    }

    @Override
    public void configViews() {
        getList();
        mPcfRefresh.setOnRefreshListener(new OnDefaultRefreshListener() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                pageNo = 1;
                list.clear();
                getList();
            }
        });
        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                //发起加载更多请求
                getList();
            }
        });
    }

    private void getList() {
        if (CommonUtils.isNetworkAvailable(mContext)) {
            JSONObject obj = new JSONObject();
            try {
                obj.put("key", "");
                obj.put("msnid",
                        AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getSnid());
                obj.put("pwd", AyjSwApplication.getsInstance().getUserInfo().getData().get(
                        0).getPassWord());
                obj.put("accounttype", accountType);
                obj.put("pageno", pageNo);
                obj.put("pagesize", pageSize);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mProgressHub.show();
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.MONEY_HIS))
                    .addParams("json", String.valueOf(obj))
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            mProgressHub.dismiss();
                            mPcfRefresh.onRefreshComplete();
                            mRecyclerView.onLoadMoreComplete();
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            mProgressHub.dismiss();
                            mRecyclerView.onLoadMoreComplete();
                            mPcfRefresh.onRefreshComplete();
                            Check check = new Gson().fromJson(response, Check.class);
                            switch (check.getErr()) {
                                case 0:
                                    pageNo++;
                                    bean = new Gson().fromJson(response,
                                            ChangeMoneyBean.class);
                                    list.addAll(bean.getData());
                                    if (null == adapter) {
                                        adapter = new ChangMoneyAdapter(R.layout.item_money_change, list);
                                        mRecyclerView.setAdapter(adapter);
                                    } else {
                                        adapter.setNewData(list);
                                    }
                                    if (bean.getData().size() < pageSize) {
                                        mRecyclerView.setHasLoadMore(false);
                                    } else {
                                        mRecyclerView.setHasLoadMore(true);
                                    }
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

    @OnClick({R.id.iv_back, R.id.tv_right_head})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_right_head:
                startActivity(new Intent(mContext, YjToQbActivity.class));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus
    }

    @Subscribe
    public void onEventMainThread(FirstEvent event) {
        if (event.getMsg().equals("YJTOQB")) {
            pageNo = 1;
            list.clear();
            getList();
        }
    }
}
