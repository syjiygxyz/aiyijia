package com.ayj.aiyijia.fragment.home;

import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;
import android.widget.Toast;

import com.ayj.aiyijia.AyjSwApplication;
import com.ayj.aiyijia.R;
import com.ayj.aiyijia.adapter.home.HomeDetailAdapter;
import com.ayj.aiyijia.bean.Check;
import com.ayj.aiyijia.bean.khq.ShopInfo;
import com.ayj.aiyijia.fragment.base.LazyFragment;
import com.ayj.aiyijia.utils.CommonUtils;
import com.ayj.aiyijia.utils.WebUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import cn.finalteam.loadingviewfinal.OnDefaultRefreshListener;
import cn.finalteam.loadingviewfinal.PtrClassicFrameLayout;
import cn.finalteam.loadingviewfinal.PtrFrameLayout;
import cn.finalteam.loadingviewfinal.RecyclerViewFinal;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/7/20.
 * 实景
 */
public class SjFragment extends LazyFragment {


    @BindView(R.id.recycler_view)
    RecyclerViewFinal recyclerView;
    @BindView(R.id.pcf_refresh)
    PtrClassicFrameLayout pcfRefresh;
    @BindView(R.id.tv_detail)
    TextView tv_detail;

    HomeDetailAdapter adapter;

    @Override
    public void fetchData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getShopImg();
        pcfRefresh.setOnRefreshListener(new OnDefaultRefreshListener() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getShopImg();
            }
        });
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sj;
    }

    private void getShopImg() {
        if (CommonUtils.isNetworkAvailable(getActivity())) {
            mProgressHub.show();
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.SP_DETAIL))
                    .addParams("json", "{\"key\":\"\",\"shopid\":\"" + AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getRegshopid() + "\"}")
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            mProgressHub.dismiss();
                            pcfRefresh.onRefreshComplete();
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            mProgressHub.dismiss();
                            pcfRefresh.onRefreshComplete();
                            Check check = new Gson().fromJson(response, Check.class);

                            switch (check.getErr()) {
                                case 0:
                                    ShopInfo shopInfo = new Gson().fromJson(response, ShopInfo.class);
                                    List<String> list = new ArrayList<String>();
                                    tv_detail.setText("店铺地址:"+shopInfo.getData().getAddr());
                                    if(!"".equals(shopInfo.getData().getImgurlshow())){
                                        list = Arrays.asList(shopInfo.getData().getImgurlshow().split(","));
                                    }
                                    if(null==adapter){
                                        adapter = new HomeDetailAdapter(R.layout.home_detail,list);
                                        recyclerView.setAdapter(adapter);
                                    }else{
                                        adapter.setNewData(list);
                                    }
                                    break;
                                default:
                                    Toast.makeText(getActivity(), check.getMsg(),
                                            Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                    });
        } else {
            showToast(R.string.TheNetIsUnAble);
        }

    }

}
