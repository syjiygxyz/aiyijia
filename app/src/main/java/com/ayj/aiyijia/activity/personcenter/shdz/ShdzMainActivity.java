package com.ayj.aiyijia.activity.personcenter.shdz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayj.aiyijia.AyjSwApplication;
import com.ayj.aiyijia.R;
import com.ayj.aiyijia.activity.base.BaseActivity;
import com.ayj.aiyijia.activity.home.bx.clxx.CommonClFormActivity;
import com.ayj.aiyijia.adapter.bx.VehicleInfoAdapter;
import com.ayj.aiyijia.adapter.personcenter.ShdzAdapter;
import com.ayj.aiyijia.bean.Check;
import com.ayj.aiyijia.bean.cmbz.VehicleBean;
import com.ayj.aiyijia.bean.eventbus.FirstEvent;
import com.ayj.aiyijia.bean.shopping.Address;
import com.ayj.aiyijia.utils.CommonUtils;
import com.ayj.aiyijia.utils.WebUtils;
import com.ayj.aiyijia.view.sweetalert.SweetAlertDialog;
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
import cn.finalteam.loadingviewfinal.PtrClassicFrameLayout;
import cn.finalteam.loadingviewfinal.PtrFrameLayout;
import cn.finalteam.loadingviewfinal.RecyclerViewFinal;
import okhttp3.Call;

/*
* 收货地址
* */
public class ShdzMainActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.recycler_view)
    RecyclerViewFinal recyclerView;
    @BindView(R.id.pcf_refresh)
    PtrClassicFrameLayout pcfRefresh;
    @BindView(R.id.iv_right)
    ImageView ivRight;

    ShdzAdapter shdzAdapter;


    private List<Address.DataBean> addresses = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_shdz_main;
    }

    @Override
    public void initDatas() {
        EventBus.getDefault().register(this);
        tvTitle.setText("收货地址");
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(R.mipmap.addaaa);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        getList();
    }

    @Override
    public void configViews() {
        pcfRefresh.setOnRefreshListener(new OnDefaultRefreshListener() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                addresses.clear();
                getList();
            }
        });
    }


    private void getList() {
        if (CommonUtils.isNetworkAvailable(mContext)) {
            JSONObject obj = new JSONObject();
            try {
                obj.put("key", "");
                obj.put("userid",
                        AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getSnid());
                obj.put("pwd", AyjSwApplication.getsInstance().getUserInfo().getData().get(
                        0).getPassWord());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mProgressHub.show();
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.SHOP_DETAIL_ADDRESS))
                    .addParams("json", String.valueOf(obj))
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
                                    Address add = new Gson().fromJson(response, Address.class);
                                    for (int i = 0; i < add.getData().size(); i++) {
                                        addresses.add(add.getData().get(i));
                                    }
                                    if (shdzAdapter == null) {
                                        shdzAdapter = new ShdzAdapter(addresses, new xgOnClickListener(), new scClickListener());
                                        recyclerView.setAdapter(shdzAdapter);
                                    } else {
                                        shdzAdapter.setNewData(addresses);
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

    @OnClick({R.id.iv_back, R.id.iv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_right:
                Intent mIntent = new Intent(mContext,ShdzAddAddressActivity.class);
                mIntent.putExtra("type","0");
                startActivity(mIntent);
                break;
        }
    }

    /**
     * 修改监听
     */
    private class xgOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v.getTag() != null) {
                Address.DataBean item = (Address.DataBean) v.getTag();
                Intent mIntent = new Intent(mContext,ShdzAddAddressActivity.class);
                mIntent.putExtra("type","1");
                mIntent.putExtra("info",item);
                startActivity(mIntent);
            }
        }
    }

    /**
     * 删除监听
     */
    private class scClickListener implements View.OnClickListener {

        @Override
        public void onClick(final View v) {
            if (v.getTag() != null) {
                SweetAlertDialog confirmDialog = CommonUtils.getConfirmDialog(mContext, "提示",
                        "是否删除?",
                        new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                del(v.getTag().toString());
                                sweetAlertDialog.dismiss();
                            }
                        });
                //显示dialog
                confirmDialog.show();
            }
        }
    }

    private void del(String snid){
        if(CommonUtils.isNetworkAvailable(mContext)){
            mProgressHub.show();
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.DEL_ADD))
                    .addParams("json","{\"key\":\"\",\"userid\":\""+AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getSnid()+"\","
                            + "\"pwd\":\""+AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getPassWord()+"\",\"snid\":\""+snid+"\"}")
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            mProgressHub.dismiss();
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            mProgressHub.dismiss();
                            Check check = new Gson().fromJson(response,Check.class);
                            switch (check.getErr()){
                                case 0:
                                    showToast("删除成功");
                                    addresses.clear();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus
    }

    @Subscribe
    public void onEventMainThread(FirstEvent event) {
        if (event.getMsg().equals("MYCENTERADDADDRESS")) {
            addresses.clear();
            getList();
        }
    }
}
