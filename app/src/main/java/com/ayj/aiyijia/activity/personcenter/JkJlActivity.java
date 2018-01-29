package com.ayj.aiyijia.activity.personcenter;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayj.aiyijia.R;
import com.ayj.aiyijia.activity.base.BaseActivity;
import com.ayj.aiyijia.adapter.tab.CommonPagerAdapter;
import com.ayj.aiyijia.fragment.home.FwyFragment;
import com.ayj.aiyijia.fragment.personcenter.JkjlFragment;
import com.ayj.aiyijia.utils.CommonUtils;
import com.ayj.aiyijia.view.magicindicator.MagicIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/*
* 健康记录
* */
public class JkJlActivity extends BaseActivity {

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
        return R.layout.activity_jk_jl;
    }

    @Override
    public void initDatas() {
        mTvTitle.setText("健康记录");
        titles = Arrays.asList(getResources().getStringArray(R.array.jk_jl));
    }

    @Override
    public void configViews() {
        setFragments();
        mLyPages.setOffscreenPageLimit(4);
        CommonPagerAdapter adapter = new CommonPagerAdapter(getSupportFragmentManager(), titles,
                listFragment);
        mLyPages.setAdapter(adapter);
        CommonUtils.initJfMagicIndicator(mTpLy,titles,mLyPages,mContext);
    }

    private void setFragments() {
        listFragment.add(JkjlFragment.newInstance("1"));
        listFragment.add(JkjlFragment.newInstance("2"));
        listFragment.add(JkjlFragment.newInstance("3"));
        listFragment.add(JkjlFragment.newInstance("4"));
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
