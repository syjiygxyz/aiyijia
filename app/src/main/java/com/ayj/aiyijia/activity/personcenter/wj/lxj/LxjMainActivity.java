package com.ayj.aiyijia.activity.personcenter.wj.lxj;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayj.aiyijia.R;
import com.ayj.aiyijia.activity.base.BaseActivity;
import com.ayj.aiyijia.adapter.tab.CommonPagerAdapter;
import com.ayj.aiyijia.fragment.personcenter.LxjFragment;
import com.ayj.aiyijia.utils.CommonUtils;
import com.ayj.aiyijia.view.magicindicator.MagicIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/*
* 联销卷
* */
public class LxjMainActivity extends BaseActivity {

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
        return R.layout.activity_lxj_main;
    }

    @Override
    public void initDatas() {
    mTvTitle.setText("联销卷");
    titles = Arrays.asList(getResources().getStringArray(R.array.lxj_titles));
}

    @Override
    public void configViews() {
        setFragments();
        mLyPages.setOffscreenPageLimit(2);
        CommonPagerAdapter adapter = new CommonPagerAdapter(getSupportFragmentManager(), titles,
                listFragment);
        mLyPages.setAdapter(adapter);
        CommonUtils.initJfMagicIndicator(mTpLy,titles,mLyPages,mContext);
    }

    private void setFragments() {
        listFragment.add(LxjFragment.newInstance("0"));
        listFragment.add(LxjFragment.newInstance("1"));
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}

