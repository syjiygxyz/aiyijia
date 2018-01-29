package com.ayj.aiyijia.fragment.personcenter;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.Toast;

import com.ayj.aiyijia.AyjSwApplication;
import com.ayj.aiyijia.R;
import com.ayj.aiyijia.adapter.personcenter.AjAdapter;
import com.ayj.aiyijia.adapter.personcenter.CspAdapter;
import com.ayj.aiyijia.adapter.personcenter.XyyAdapter;
import com.ayj.aiyijia.bean.AjRecordBean;
import com.ayj.aiyijia.bean.Check;
import com.ayj.aiyijia.bean.MdCgBean;
import com.ayj.aiyijia.fragment.base.LazyFragment;
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
import cn.finalteam.loadingviewfinal.OnDefaultRefreshListener;
import cn.finalteam.loadingviewfinal.OnLoadMoreListener;
import cn.finalteam.loadingviewfinal.PtrClassicFrameLayout;
import cn.finalteam.loadingviewfinal.PtrFrameLayout;
import cn.finalteam.loadingviewfinal.RecyclerViewFinal;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/8/15.
 * 健康记录
 */
public class JkjlFragment extends LazyFragment {

    @BindView(R.id.recycler_view)
    RecyclerViewFinal mRecyclerView;
    @BindView(R.id.pcf_refresh)
    PtrClassicFrameLayout mPcfRefresh;

    int pagesize = 12;

    int pageno = 1;

    List<AjRecordBean.DataBean> list = new ArrayList<>();

    private Bundle bd;

    String type;

    AjAdapter ajAdapter;

    CspAdapter cspAdapter;

    XyyAdapter xyyAdapter;

    /*跳转参数*/
    public static JkjlFragment newInstance(String param1) {
        JkjlFragment fragment = new JkjlFragment();
        Bundle args = new Bundle();
        args.putString("type", param1);
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
        return R.layout.fragment_jkjl;
    }

    @Override
    public void fetchData() {
        if (getArguments() != null) {
            bd = getArguments();
            type = bd.getString("type");
        }
        mPcfRefresh.setOnRefreshListener(new OnDefaultRefreshListener() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                pageno = 1;
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
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getList();
    }

    private void getList() {
        if (CommonUtils.isNetworkAvailable(getActivity())) {
            JSONObject obj = new JSONObject();
            try {
                obj.put("key", "");
                obj.put("msnid",
                        AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getSnid());
                obj.put("pwd", AyjSwApplication.getsInstance().getUserInfo().getData().get(
                        0).getPassWord());
                obj.put("pageno", pageno);
                obj.put("pagesize", pagesize);
                obj.put("type", type);
                obj.put("userroleid", "");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mProgressHub.show();
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.JK_JL))
                    .addParams("json", String.valueOf(obj))
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
                                    pageno++;
                                    AjRecordBean ajRecordBean = new Gson().fromJson(response, AjRecordBean.class);
                                    list.addAll(ajRecordBean.getData());
                                    if (type.equals("1")) {
                                        if (null == ajAdapter) {
                                            ajAdapter = new AjAdapter(
                                                    R.layout.ajy_item, list);
                                            mRecyclerView.setAdapter(ajAdapter);
                                        } else {
                                            ajAdapter.setNewData(list);
                                        }
                                    } else if (type.equals("2") || type.equals("3")){
                                        if (null == cspAdapter) {
                                            cspAdapter = new CspAdapter(
                                                    R.layout.csp_item, list,type);
                                            mRecyclerView.setAdapter(cspAdapter);
                                        } else {
                                            cspAdapter.setNewData(list);
                                        }
                                    }else if(type.equals("4")){
                                        if (null == xyyAdapter) {
                                            xyyAdapter = new XyyAdapter(
                                                    R.layout.xyy_item, list);
                                            mRecyclerView.setAdapter(xyyAdapter);
                                        } else {
                                            xyyAdapter.setNewData(list);
                                        }
                                    }
                                        if (ajRecordBean.getData().size() < pagesize) {
                                            mRecyclerView.setHasLoadMore(false);
                                        } else {
                                            mRecyclerView.setHasLoadMore(true);
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

