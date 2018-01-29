package com.ayj.aiyijia.activity.home.splist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ayj.aiyijia.AyjSwApplication;
import com.ayj.aiyijia.R;
import com.ayj.aiyijia.activity.base.BaseActivity;
import com.ayj.aiyijia.activity.shopping.GwcActivity;
import com.ayj.aiyijia.adapter.tab.CommonPagerAdapter;
import com.ayj.aiyijia.bean.Check;
import com.ayj.aiyijia.bean.Classification;
import com.ayj.aiyijia.bean.ScTitleBean;
import com.ayj.aiyijia.fragment.shopping.CommonDhMoneyFragment;
import com.ayj.aiyijia.fragment.shopping.CommonSpListFragment;
import com.ayj.aiyijia.utils.CommonUtils;
import com.ayj.aiyijia.utils.WebUtils;
import com.ayj.aiyijia.view.magicindicator.MagicIndicator;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;


/*
* 通用的商品列表界面
* 通用商品页面
* 邻家小铺，折扣商铺要本地加个热推，爱e币兑换需要加个兑金币
* */
public class SplistActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tp_ly)
    MagicIndicator mTpLy;
    @BindView(R.id.ly_pages)
    ViewPager ly_pages;

    List<String> titles = new ArrayList<>();
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.tv_gw_count)
    TextView tvGwCount;

    private Classification classification_xd;//获取的标题
    private List<Fragment> listFragment;

    String type;//区分是否手动添加热推

    String type1;//区分是商品列表来自哪里

    String ordertype;//获取商品用

    String matclass1;//获取商品用....是获取的

    ScTitleBean scTitleBean;


    @Override
    public int getLayoutId() {
        return R.layout.activity_splist;
    }

    @Override
    public void initDatas() {
        ordertype = getIntent().getStringExtra("ordertype");
        matclass1 = getIntent().getStringExtra("matclass1");
        type = getIntent().getStringExtra("type");
        type1 = getIntent().getStringExtra("type1");
        if (type1.equals("cmrg")) {
            mTvTitle.setText("春苗展柜");
        } else if (type1.equals("zkp")) {
            mTvTitle.setText("折扣商铺");
        } else if (type1.equals("dhzx")) {
            mTvTitle.setText("兑换中心");
        } else if (type1.equals("ljxd")) {
            mTvTitle.setText("邻家小店");
        } else if (type1.equals("jbdh")) {
            mTvTitle.setText("金币兑换");
        } else if (type1.equals("aebdh")) {
            mTvTitle.setText("爱e币兑换");
        } else if (type1.equals("wjdh")) {
            mTvTitle.setText("物卷兑换");
        } else if (type1.equals("MEMBERORDERTYPE022")) {
            mTvTitle.setText("礼仓");
            ivRight.setVisibility(View.VISIBLE);
            ivRight.setImageResource(R.mipmap.licang);
        }
        getDict();
        getShopcartNum();
    }

    @Override
    public void configViews() {
        //      setFragments();
    }

    public static void startActivity(Context cxt, String type, String ordertype, String matclass1, String type1) {
        Intent intent = new Intent(cxt, SplistActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("type1", type1);
        intent.putExtra("ordertype", ordertype);
        intent.putExtra("matclass1", matclass1);
        cxt.startActivity(intent);
    }

    /*获取二级目录的标题*/
    private void getDict() {
        if (CommonUtils.isNetworkAvailable(mContext)) {
            mProgressHub.show();
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.CL_FX))
                    .addParams("json", "{\"key\":\"\",\"dicttypeid\":\"" + ordertype + "\"}")
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
                                    scTitleBean = new Gson().fromJson(response, ScTitleBean.class);
                                    if(type1.equals("cmrg")){
                                        titles.add("促销商品");
                                    }
                                    if ("have_hot".equals(type)) {
                                        titles.add("热推");
                                    }
                                    for (int i = 0; i < scTitleBean.getData().size(); i++) {
                                        titles.add(
                                                scTitleBean.getData().get(i).getParamname());
                                    }
                                    if (type1.equals("aebdh")) {
                                        titles.add("兑钱包");
                                    }
                                    initData();
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

    private void initData() {
        ly_pages.setOffscreenPageLimit(titles.size());
        setFragments();
        CommonPagerAdapter adapter = new CommonPagerAdapter(getSupportFragmentManager(), titles,
                listFragment);
        ly_pages.setAdapter(adapter);
        CommonUtils.initJfMagicIndicator(mTpLy, titles, ly_pages, mContext);
    }


    //初始化fragment
    public void setFragments() {
        listFragment = new ArrayList<Fragment>();
        for (int i = 0; i < titles.size(); i++) {
            if ("have_hot".equals(type)) {
                if (i == 0) {
                    listFragment.add(
                            CommonSpListFragment.newInstance("", "SFPD001", "", ordertype,
                                    type1));
                } else {
                    listFragment.add(CommonSpListFragment.newInstance(""
                            , "", scTitleBean.getData().get(i - 1).getDictid(),
                            ordertype, type1));
                }
            } else if (type1.equals("aebdh") && i == titles.size() - 1) {
                /*手动增加兑换金币*/
                listFragment.add(CommonDhMoneyFragment.newInstance(
                        ordertype, type1));
            } else if (type1.equals("cmrg")) {
                if(i==0){
                    listFragment.add(CommonSpListFragment.newInstance(""
                            , "","",
                            "MEMBERORDERTYPE032", type1));
                }else{
                    listFragment.add(CommonSpListFragment.newInstance(""
                            , "", scTitleBean.getData().get(i - 1).getDictid(),
                            ordertype, type1));
                }
            } else {
                listFragment.add(CommonSpListFragment.newInstance(
                        "", "", scTitleBean.getData().get(i).getDictid(),
                        ordertype, type1));
            }
        }
    }

    @OnClick({R.id.iv_back, R.id.iv_right,R.id.tv_gw_count})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_right:
                Intent intent = new Intent();
                intent.putExtra("ordertype", type1);
                intent.putExtra("addres", "detail");
                intent.setClass(mContext, GwcActivity.class);
                startActivity(intent);
                break;
        }
    }

    /*获取购物车数量*/
    private void getShopcartNum() {
        if (CommonUtils.isNetworkAvailable(mContext)) {
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.MENBER_GET_GWC))
                    .addParams("json",
                            "{\"key\":\"\",\"msnid\":\"" + AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getSnid()
                                    + "\",\"pwd\":\"" + AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getPassWord()
                                    + "\",\"carttype\":\"" + ordertype + "\"}")
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(String response, int id) {
                            Check check = new Gson().fromJson(response, Check.class);
                            switch (check.getErr()) {
                                case 0:
                                    tvGwCount.setVisibility(View.VISIBLE);
                                    if (check.getTotal() != 0) {
                                        tvGwCount.setVisibility(View.VISIBLE);
                                        if (check.getTotal() < 99) {
                                            tvGwCount.setText(check.getTotal() + "");
                                        } else {
                                            tvGwCount.setText("99");
                                        }
                                    } else {
                                        tvGwCount.setVisibility(View.GONE);
                                    }
                                    break;
                                default:
                                    Toast.makeText(mContext, check.getMsg(),
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
