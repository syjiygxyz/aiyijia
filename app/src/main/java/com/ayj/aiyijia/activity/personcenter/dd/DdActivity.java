package com.ayj.aiyijia.activity.personcenter.dd;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayj.aiyijia.R;
import com.ayj.aiyijia.activity.base.BaseActivity;
import com.ayj.aiyijia.adapter.tab.CommonPagerAdapter;
import com.ayj.aiyijia.fragment.personcenter.DdFragment;
import com.ayj.aiyijia.utils.CommonUtils;
import com.ayj.aiyijia.view.magicindicator.MagicIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/*
* 订单界面
* */
public class DdActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tp_ly)
    MagicIndicator mTpLy;
    @BindView(R.id.ly_pages)
    ViewPager mLyPages;

    private List<Fragment> listFragment = new ArrayList<>();

    List<String> titles = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_dd;
    }

    @Override
    public void initDatas() {
        mTvTitle.setText("我的订单");
        titles = Arrays.asList(getResources().getStringArray(R.array.dd_titles));
    }

    @Override
    public void configViews() {
        mLyPages.setOffscreenPageLimit(5);
        setFragments();
        CommonPagerAdapter adapter = new CommonPagerAdapter(getSupportFragmentManager(), titles,
                listFragment);
        mLyPages.setAdapter(adapter);
        CommonUtils.initJfMagicIndicator(mTpLy,titles,mLyPages,mContext);
    }

    private void setFragments() {
        listFragment.add(DdFragment.newInstance("",null));
        listFragment.add(DdFragment.newInstance("MEMBERORDERSTATUS001",null));
        listFragment.add(DdFragment.newInstance("MEMBERORDERSTATUS002",null));
        listFragment.add(DdFragment.newInstance("MEMBERORDERSTATUS008",null));
        listFragment.add(DdFragment.newInstance("MEMBERORDERSTATUS009",null));
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }


}

