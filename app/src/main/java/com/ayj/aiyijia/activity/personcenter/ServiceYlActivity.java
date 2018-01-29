package com.ayj.aiyijia.activity.personcenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayj.aiyijia.AyjSwApplication;
import com.ayj.aiyijia.R;
import com.ayj.aiyijia.activity.base.BaseActivity;
import com.ayj.aiyijia.adapter.personcenter.UseTicketAdapter;
import com.ayj.aiyijia.bean.Check;
import com.ayj.aiyijia.bean.cmbz.UserTicket;
import com.ayj.aiyijia.utils.CommonUtils;
import com.ayj.aiyijia.utils.WebUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

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
* 服务余量
* */
public class ServiceYlActivity extends BaseActivity {

    @BindView(R.id.recycler_view)
    RecyclerViewFinal recyclerView;
    @BindView(R.id.pcf_refresh)
    PtrClassicFrameLayout pcfRefresh;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    private int startrow = 1;
    private int endrow = 10;

    private List<UserTicket.DataBean> userTicket = new ArrayList<>();

    UseTicketAdapter ticketAdapter;

    public static void jumpActivity(Context context, String type) {
        Intent mIntent = new Intent(context, ServiceYlActivity.class);
        mIntent.putExtra("type", type);
        context.startActivity(mIntent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_service_yl;
    }

    @Override
    public void initDatas() {
        if(getIntent().getStringExtra("type").equals("3")){
            tvTitle.setText("体验卷");
        }else{
            tvTitle.setText("服务余量");
        }
    }

    @Override
    public void configViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        pcfRefresh.setOnRefreshListener(new OnDefaultRefreshListener() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                userTicket.clear();
                startrow = 1;
                endrow = 10;
                getList();
            }
        });
        recyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                startrow += 10;
                endrow += 10;
                getList();
            }
        });
        getList();

    }

    /*获取数据*/
    private void getList() {
        if (CommonUtils.isNetworkAvailable(mContext)) {
            mProgressHub.show();
            JSONObject object = new JSONObject();
            try {
                object.put("key", "");
                object.put("userid", AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getSnid());
                object.put("pwd", AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getPassWord());
                object.put("type", getIntent().getStringExtra("type"));
                object.put("startrow", startrow);
                object.put("endrow", endrow);
                object.put("status", "TICKETSTATUS001");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.GET_TYQ))
                    .addParams("json", String.valueOf(object))
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            mProgressHub.dismiss();
                            recyclerView.onLoadMoreComplete();
                            pcfRefresh.onRefreshComplete();
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            mProgressHub.dismiss();
                            recyclerView.onLoadMoreComplete();
                            pcfRefresh.onRefreshComplete();
                            Check check = new Gson().fromJson(response, Check.class);
                            switch (check.getErr()) {
                                case 0:
                                    UserTicket ticket = new Gson().fromJson(response, UserTicket.class);
                                    for (int i = 0; i < ticket.getData().size(); i++) {
                                        if ("TICKETSTATUS001".equals(
                                                ticket.getData().get(i).getStatus())) {
                                            userTicket.add(ticket.getData().get(i));
                                        }
                                    }
                                    if (null == ticketAdapter) {
                                        ticketAdapter = new UseTicketAdapter(R.layout.item_ticket, userTicket);
                                        recyclerView.setAdapter(ticketAdapter);
                                    } else {
                                        ticketAdapter.setNewData(userTicket);
                                    }
                                    if (ticket.getData().size() < 10) {
                                        recyclerView.setHasLoadMore(false);
                                    } else {
                                        recyclerView.setHasLoadMore(true);
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
