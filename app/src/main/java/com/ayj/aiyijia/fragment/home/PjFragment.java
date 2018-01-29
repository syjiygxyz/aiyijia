package com.ayj.aiyijia.fragment.home;

import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.ayj.aiyijia.AyjSwApplication;
import com.ayj.aiyijia.R;
import com.ayj.aiyijia.adapter.home.FwdPlAdapter;
import com.ayj.aiyijia.bean.Check;
import com.ayj.aiyijia.bean.khq.ShopPjBean;
import com.ayj.aiyijia.fragment.base.LazyFragment;
import com.ayj.aiyijia.utils.ACache;
import com.ayj.aiyijia.utils.CommonUtils;
import com.ayj.aiyijia.utils.WebUtils;
import com.ayj.aiyijia.view.DividerItemDecoration;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.finalteam.loadingviewfinal.OnDefaultRefreshListener;
import cn.finalteam.loadingviewfinal.OnLoadMoreListener;
import cn.finalteam.loadingviewfinal.PtrClassicFrameLayout;
import cn.finalteam.loadingviewfinal.PtrFrameLayout;
import cn.finalteam.loadingviewfinal.RecyclerViewFinal;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/7/20.
 */
public class PjFragment extends LazyFragment {
    @BindView(R.id.recycler_view)
    RecyclerViewFinal recyclerView;
    @BindView(R.id.pcf_refresh)
    PtrClassicFrameLayout pcfRefresh;
    int start = 0;

    int countSize = 10;

    private List<ShopPjBean.DataBean> shopList = new ArrayList<>();


    FwdPlAdapter fwdPlAdapter;
    @Override
    public void fetchData() {
        initView();

    }


    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST));
        getPl();
        pcfRefresh.setOnRefreshListener(new OnDefaultRefreshListener() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                start = 0;
                shopList.clear();
                getPl();
            }
        });
        recyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                //发起加载更多请求
                getPl();
            }
        });
    }

    private void getPl() {
        if (CommonUtils.isNetworkAvailable(getActivity())) {
            mProgressHub.show();
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.CH_PJ))
                    .addParams("json","{\"key\":\"\",\"shopid\":\"" + AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getRegshopid() + "\",\"startrow\":\"" + start
                            + "\","
                            + "\"endrow\":\"" + (start + countSize) + "\"}")
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            mProgressHub.dismiss();
                            Toast.makeText(getActivity(), "获取服务点评价失败", Toast.LENGTH_SHORT).show();
                            pcfRefresh.onRefreshComplete();
                            recyclerView.onLoadMoreComplete();
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            mProgressHub.dismiss();
                            pcfRefresh.onRefreshComplete();
                            recyclerView.onLoadMoreComplete();
                            Check check = new Gson().fromJson(response, Check.class);
                            switch (check.getErr()) {
                                case 0:
                                    start = start + countSize + 1;
                                    ShopPjBean shopPjBean = new Gson().fromJson(response,
                                            ShopPjBean.class);
                                    if (shopPjBean.getData().size() != 0) {
                                        shopList.addAll(shopPjBean.getData());
                                    }
                                    if(null==fwdPlAdapter){
                                        fwdPlAdapter = new FwdPlAdapter(R.layout.item_pj,shopList);
                                        recyclerView.setAdapter(fwdPlAdapter);
                                    }else{
                                        fwdPlAdapter.setNewData(shopList);
                                    }
                                    if (shopPjBean.getData().size() < countSize) {
                                        recyclerView.setHasLoadMore(false);
                                    } else {
                                        recyclerView.setHasLoadMore(true);
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
}
