package com.ayj.aiyijia.activity.personcenter.infosetting;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayj.aiyijia.AyjSwApplication;
import com.ayj.aiyijia.R;
import com.ayj.aiyijia.activity.base.BaseActivity;
import com.ayj.aiyijia.adapter.BankBeanAdapter;
import com.ayj.aiyijia.bean.BankBean;
import com.ayj.aiyijia.bean.Check;
import com.ayj.aiyijia.bean.eventbus.FirstEvent;
import com.ayj.aiyijia.utils.CommonUtils;
import com.ayj.aiyijia.utils.WebUtils;
import com.ayj.aiyijia.view.sweetalert.SweetAlertDialog;
import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
* 银行卡列表界面
* */
public class YhCardActivity extends BaseActivity {


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
    private List<BankBean.DataBean> banks = new ArrayList<>();
    private BankBeanAdapter bankAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_yh_card;
    }

    @Override
    public void initDatas() {
        EventBus.getDefault().register(this);
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(R.mipmap.addaaa);
        tvTitle.setText("银行卡");
    }

    @Override
    public void configViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        getBank();
        pcfRefresh.setOnRefreshListener(new OnDefaultRefreshListener() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                banks.clear();
                getBank();
            }
        });
    }

    /**
     * 获取我的银行卡
     */
    private void getBank() {
        if (CommonUtils.isNetworkAvailable(mContext)) {
            mProgressHub.show();
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.CX_YHK_LE))
                    .addParams("json",
                            "{\"key\":\"\",\"userid\":\"" + AyjSwApplication.getsInstance().getUserInfo().getData().get(
                                    0).getSnid() + "\",\"pwd\":\""
                                    + AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getPassWord() + "\"}")
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
                                    BankBean bank = new Gson().fromJson(response, BankBean.class);
                                    for (int i = 0; i < bank.getData().size(); i++) {
                                        if ("SFYX001".equals(
                                                bank.getData().get(i).getBindingstatus())) {
                                            banks.add(bank.getData().get(i));
                                        }
                                    }
                                    if (null == bankAdapter) {
                                        bankAdapter = new BankBeanAdapter(R.layout.item_bank, banks, new jbClickListener());
                                        recyclerView.setAdapter(bankAdapter);
                                    } else {
                                        bankAdapter.setNewData(banks);
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
                startActivity(new Intent(mContext,YhkAddActivity.class));
                break;
        }
    }

    /**
     * 解除绑定监听
     */
    private class jbClickListener implements View.OnClickListener {

        @Override
        public void onClick(final View v) {
            if (v.getTag() != null) {
                SweetAlertDialog confirmDialog = CommonUtils.getConfirmDialog(mContext,
                        "是否解绑?",
                        new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                jbBank(v.getTag().toString());
                                sweetAlertDialog.dismiss();
                            }
                        });
                //显示dialog
                confirmDialog.show();
            }
        }
    }

    /**
     * 解绑我的银行卡
     */
    private void jbBank(String snid) {
        if (CommonUtils.isNetworkAvailable(mContext)) {
            mProgressHub.show();
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.JC_BD))
                    .addParams("json",
                            "{\"key\":\"\",\"userid\":\"" + AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getSnid() + "\",\"pwd\":\"" + AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getPassWord()
                                    + "\",\"banksnid\":\"" + snid + "\",\"bindingstatus\":\"SFYX002\"}")
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
                                    showToast(check.getMsg());
                                    banks.clear();
                                    getBank();
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
        if (event.getMsg().equals("YHKBDCLICK")) {
            banks.clear();
            getBank();
        }
    }


}
