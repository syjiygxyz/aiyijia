package com.ayj.aiyijia.activity.maingwc;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayj.aiyijia.R;
import com.ayj.aiyijia.activity.base.BaseActivity;
import com.ayj.aiyijia.adapter.tab.CommonPagerAdapter;
import com.ayj.aiyijia.fragment.home.GwcFragment;
import com.ayj.aiyijia.fragment.shopping.CommonDhMoneyFragment;
import com.ayj.aiyijia.fragment.shopping.CommonSpListFragment;
import com.ayj.aiyijia.utils.CommonUtils;
import com.ayj.aiyijia.view.magicindicator.MagicIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/*
* 首页购物车
* */
public class MainGwcActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tp_ly)
    MagicIndicator mTpLy;
    @BindView(R.id.ly_pages)
    ViewPager ly_pages;

    List<String> titles = new ArrayList<>();

    private List<Fragment> listFragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main_gwc;
    }

    @Override
    public void initDatas() {
        titles = Arrays.asList(getResources().getStringArray(R.array.main_gwc));
        ly_pages.setOffscreenPageLimit(titles.size());
        setFragments();
        CommonPagerAdapter adapter = new CommonPagerAdapter(getSupportFragmentManager(), titles,
                listFragment);
        ly_pages.setAdapter(adapter);
        CommonUtils.initJfMagicIndicator(mTpLy, titles, ly_pages, mContext);
    }

    @Override
    public void configViews() {

    }

    //初始化fragment
    public void setFragments() {
        listFragment = new ArrayList<Fragment>();
        listFragment.add(GwcFragment.newInstance("MEMBERORDERTYPE001"));
        listFragment.add(GwcFragment.newInstance("MEMBERORDERTYPE005"));
        listFragment.add(GwcFragment.newInstance("MEMBERORDERTYPE033"));
        listFragment.add(GwcFragment.newInstance("MEMBERORDERTYPE019"));
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}

