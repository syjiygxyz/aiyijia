package com.ayj.aiyijia.fragment.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.ayj.aiyijia.AyjSwApplication;
import com.ayj.aiyijia.R;
import com.ayj.aiyijia.activity.shopping.ShoppingDetailActivity;
import com.ayj.aiyijia.adapter.shopping.CommonSpListAdapter;
import com.ayj.aiyijia.adapter.shopping.DhListAdapter;
import com.ayj.aiyijia.adapter.shopping.DhMoneyAdapter;
import com.ayj.aiyijia.adapter.shopping.JbdhAdapter;
import com.ayj.aiyijia.adapter.shopping.ZkSpListAdapter;
import com.ayj.aiyijia.bean.Check;
import com.ayj.aiyijia.bean.DhMoneyBean;
import com.ayj.aiyijia.bean.FootZqShop;
import com.ayj.aiyijia.fragment.base.LazyFragment;
import com.ayj.aiyijia.utils.CommonUtils;
import com.ayj.aiyijia.utils.WebUtils;
import com.ayj.aiyijia.view.DividerItemDecoration;
import com.ayj.aiyijia.view.sweetalert.SweetAlertDialog;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.finalteam.loadingviewfinal.HeaderAndFooterRecyclerViewAdapter;
import cn.finalteam.loadingviewfinal.OnDefaultRefreshListener;
import cn.finalteam.loadingviewfinal.OnLoadMoreListener;
import cn.finalteam.loadingviewfinal.PtrClassicFrameLayout;
import cn.finalteam.loadingviewfinal.PtrFrameLayout;
import cn.finalteam.loadingviewfinal.RecyclerViewFinal;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/8/26.
 */
public class CommonDhMoneyFragment extends LazyFragment {

    DhMoneyBean footZqShop;

    CommonSpListAdapter commonSpAdapter;

    View view;

    private Bundle bd;

    String ordertype;

    DhListAdapter dhAdapter;

    ZkSpListAdapter zkSpListAdapter;

    @BindView(R.id.pcf_refresh)
    PtrClassicFrameLayout pcf_refresh;//刷新

    @BindView(R.id.recycler_view)
    RecyclerViewFinal recycler_view;//加载更多

    private List<DhMoneyBean.DataBean> shopList = new ArrayList<>();

    String type1;

    DhMoneyAdapter jbdhAdapter;


    /*跳转参数*/
    public static CommonDhMoneyFragment newInstance(String param4, String param5) {
        CommonDhMoneyFragment fragment = new CommonDhMoneyFragment();
        Bundle args = new Bundle();
        args.putString("ordertype", param4);
        args.putString("type1", param5);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.common_sp_list;
    }

    @Override
    public void fetchData() {
        initView();
        getSp();
        recycler_view.setOnItemClickListener(
                new HeaderAndFooterRecyclerViewAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(RecyclerView.ViewHolder holder, int position) {
                        /*
                        * 点击兑换
                        * */
                        dhShopping(position);
                    }
                });
    }

    private void initView() {
        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler_view.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST));
        if (getArguments() != null) {
            bd = getArguments();
            ordertype = bd.getString("ordertype");
            type1 = bd.getString("type1");
        }
        pcf_refresh.setOnRefreshListener(new OnDefaultRefreshListener() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                shopList.clear();
                getSp();
            }
        });

    }

    private void getSp() {
        if (CommonUtils.isNetworkAvailable(getActivity())) {
            mProgressHub.show();
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.GET_DH_MONEY))
                    .addParams("json","{\"key\":\"\",\"ordertype\":\"" + ordertype + "\","
                            + "\"msnid\":\"" + AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getSnid() + "\","
                            + "\"pwd\":\"" + AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getPassWord() + "\"}")
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            mProgressHub.dismiss();
                            Toast.makeText(getActivity(), "获取商品信息失败", Toast.LENGTH_SHORT).show();
                            pcf_refresh.onRefreshComplete();
                            recycler_view.onLoadMoreComplete();
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            mProgressHub.dismiss();
                            pcf_refresh.onRefreshComplete();
                            Check check = new Gson().fromJson(response, Check.class);
                            switch (check.getErr()) {
                                case 0:
                                    footZqShop = new Gson().fromJson(response, DhMoneyBean.class);
                                    if (footZqShop.getData().size() != 0) {
                                        shopList.addAll(footZqShop.getData());
                                        if (jbdhAdapter == null) {
                                            jbdhAdapter = new DhMoneyAdapter(
                                                    shopList);
                                            recycler_view.setAdapter(jbdhAdapter);
                                        } else {
                                            jbdhAdapter.setNewData(shopList);
                                        }
                                    }
                                    break;
                                default:
                                    Toast.makeText(getActivity(), "获取商品信息失败",
                                            Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                    });
        } else {
            showToast(R.string.TheNetIsUnAble);
        }
    }

    private void dhShopping(int position) {
        if (CommonUtils.isNetworkAvailable(getActivity())) {

            mProgressHub.show();
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.DH_QB))
                    .addParams("json","{\"key\":\"\","
                            + "\"msnid\":\""+ AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getSnid()+"\","
                            + "\"pwd\":\""+AyjSwApplication.getsInstance().getUserInfo().getData().get(
                            0).getPassWord()+"\","
                            + "\"transferemoney\":\""+shopList.get(position).getParamremark()+"\"}")
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
                                    CommonUtils.getSuccessDialog(getActivity(), "成功", "兑换成功", new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            sweetAlertDialog.dismiss();
                                        }
                                    }).show();
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

