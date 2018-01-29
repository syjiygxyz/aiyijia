package com.ayj.aiyijia.fragment.personcenter;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ayj.aiyijia.AyjSwApplication;
import com.ayj.aiyijia.R;
import com.ayj.aiyijia.adapter.personcenter.YyAdapter;
import com.ayj.aiyijia.bean.Check;
import com.ayj.aiyijia.bean.YyQdBean;
import com.ayj.aiyijia.fragment.base.LazyFragment;
import com.ayj.aiyijia.utils.CommonUtils;
import com.ayj.aiyijia.utils.WebUtils;
import com.ayj.aiyijia.view.DividerItemDecoration;
import com.ayj.aiyijia.view.sweetalert.SweetAlertDialog;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.finalteam.loadingviewfinal.OnDefaultRefreshListener;
import cn.finalteam.loadingviewfinal.OnLoadMoreListener;
import cn.finalteam.loadingviewfinal.PtrClassicFrameLayout;
import cn.finalteam.loadingviewfinal.PtrFrameLayout;
import cn.finalteam.loadingviewfinal.RecyclerViewFinal;
import okhttp3.Call;

/**
 * Created by zht-pc-04 on 2017/8/11.
 */

public class YyFragment extends LazyFragment {

    @BindView(R.id.recycler_view)
    RecyclerViewFinal recyclerView;
    @BindView(R.id.pcf_refresh)
    PtrClassicFrameLayout pcfRefresh;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;
    private String type;

    private int startrow = 1;
    private int endrow = 10;
    private List<YyQdBean.DataBean> yyList = new ArrayList<>();
    private YyAdapter adapter;
    private YyQdBean yyQdBean;


    public static YyFragment newInstance(String type) {
        YyFragment fragment = new YyFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void fetchData() {
        if (null != getArguments()) {
            type = getArguments().getString("type");
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        pcfRefresh.setOnRefreshListener(new OnDefaultRefreshListener() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                yyList.clear();
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
        if (CommonUtils.isNetworkAvailable(getActivity())) {
            mProgressHub.show();
            JSONObject object = new JSONObject();
            try {
                object.put("key", "");
                object.put("msnid",AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getSnid());
                object.put("pwd",AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getPassWord());
                object.put("status", type);
                object.put("startrow", startrow);
                object.put("endrow", endrow);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.GET_YY))
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
                                    yyQdBean = new Gson().fromJson(response, YyQdBean.class);
                                    yyList.addAll(yyQdBean.getData());
                                    if (yyList.size() == 0) {
                                        tvEmpty.setVisibility(View.VISIBLE);
                                    }else {
                                        tvEmpty.setVisibility(View.GONE);
                                    }
                                    if (null == adapter) {
                                        adapter = new YyAdapter(R.layout.item_yy, yyList);
                                        recyclerView.setAdapter(adapter);
                                    } else {
                                        adapter.setNewData(yyList);
                                    }
                                    adapter.setOnItemClickListener(new YyAdapter.YyResyclerViewOnItemClickListener() {
                                        @Override
                                        public void onItemClickListener(View v, int position) {
                                            switch (v.getId()) {
                                                case R.id.tv_yy_cancle:
                                                    if (getTimeDifferenceBetweenSystemTimeAndParamTime(yyList.get(position).getCreatetime()) < 5400000) {
                                                        showCancleDilog(position);
                                                    } else {
                                                        showToast("预约服务1.5小时后无法撤销");
                                                    }
                                                    break;
                                                case R.id.tv_yy_complete:
                                                    showCompleteDilog(position);
                                                    break;
                                            }
                                        }
                                    });
                                    if (yyQdBean.getData().size()<10){
                                        recyclerView.setHasLoadMore(false);
                                    }else {
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

    private void showCompleteDilog(final int position) {
        CommonUtils.getConfirmDialog(getActivity(), "确认服务完成？", new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                completeYy(position);
                sweetAlertDialog.dismiss();
            }
        }).show();
    }

    private void showCancleDilog(final int position) {
        CommonUtils.getConfirmDialog(getActivity(), "是否撤销预约服务？", new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                cancleYy(position);
                sweetAlertDialog.dismiss();
            }
        }).show();
    }

    public long getTimeDifferenceBetweenSystemTimeAndParamTime(String paramTime) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String systemTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date().getTime());// 获取系统时间
        long difference = 0;
        try {
            Date systemDate = dateFormat.parse(systemTime);
            Date paramDate = dateFormat.parse(paramTime);
            difference = systemDate.getTime() - paramDate.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return difference;
    }


    private void cancleYy(int position) {
        if (CommonUtils.isNetworkAvailable(getActivity())) {
            mProgressHub.show();
            JSONObject cancleobj = new JSONObject();
            try {
                cancleobj.put("key", "");
                cancleobj.put("msnid", AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getSnid());
                cancleobj.put("pwd", AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getPassWord());
                cancleobj.put("orderid", yyList.get(position).getOrderid());
                cancleobj.put("orderdetailid", yyList.get(position).getOrderdetailid());
                cancleobj.put("booksnid", yyList.get(position).getSnid());
                cancleobj.put("refundnum", "1");
                cancleobj.put("comment", "撤销预约");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.ORDER_STATUS_DELETE_URL))
                    .addParams("json", String.valueOf(cancleobj))
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
                                    showToast(check.getMsg());
                                    yyList.clear();
                                    startrow = 1;
                                    endrow = 10;
                                    getList();
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

    private void completeYy(int position) {
        if (CommonUtils.isNetworkAvailable(getActivity())) {
            mProgressHub.show();
            JSONObject comleteobj = new JSONObject();
            try {
                comleteobj.put("key", "");
                comleteobj.put("msnid", AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getSnid());
                comleteobj.put("pwd", AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getPassWord());
                comleteobj.put("booksnid", yyList.get(position).getSnid());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.ENSURE_SERVICE))
                    .addParams("json", String.valueOf(comleteobj))
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
                                    showToast(check.getMsg());
                                    yyList.clear();
                                    startrow = 1;
                                    endrow = 10;
                                    getList();
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

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_yy;
    }
}
