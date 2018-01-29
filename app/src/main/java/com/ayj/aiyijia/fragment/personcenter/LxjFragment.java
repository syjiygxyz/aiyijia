package com.ayj.aiyijia.fragment.personcenter;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.ayj.aiyijia.AyjSwApplication;
import com.ayj.aiyijia.R;
import com.ayj.aiyijia.adapter.personcenter.AjAdapter;
import com.ayj.aiyijia.adapter.personcenter.CspAdapter;
import com.ayj.aiyijia.adapter.personcenter.DsjAdapter;
import com.ayj.aiyijia.adapter.personcenter.XyyAdapter;
import com.ayj.aiyijia.bean.AjRecordBean;
import com.ayj.aiyijia.bean.Check;
import com.ayj.aiyijia.bean.WjBean;
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
 * Created by Administrator on 2017/8/31.
 */
public class LxjFragment extends LazyFragment {

    @BindView(R.id.recycler_view)
    RecyclerViewFinal mRecyclerView;
    @BindView(R.id.pcf_refresh)
    PtrClassicFrameLayout mPcfRefresh;

    int pagesize = 12;

    int pageno = 1;

    List<WjBean.DataBean> list = new ArrayList<>();

    private Bundle bd;

    String type;

    DsjAdapter dsjAdapter;

    /*跳转参数*/
    public static LxjFragment newInstance(String param1) {
        LxjFragment fragment = new LxjFragment();
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
                obj.put("paystatus", type);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mProgressHub.show();
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.GET_LXJ))
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
                                    WjBean ticket = new Gson().fromJson(response,
                                            WjBean.class);
                                    list.addAll(ticket.getData());
                                    if (null == dsjAdapter) {
                                        dsjAdapter = new DsjAdapter(
                                                R.layout.dwj_list_item, list,type);
                                        mRecyclerView.setAdapter(dsjAdapter);
                                    } else {
                                        dsjAdapter.setNewData(list);
                                    }
                                    if (ticket.getData().size() < pagesize) {
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
