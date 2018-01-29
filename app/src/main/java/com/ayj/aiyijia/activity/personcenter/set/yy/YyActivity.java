package com.ayj.aiyijia.activity.personcenter.set.yy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayj.aiyijia.R;
import com.ayj.aiyijia.activity.base.BaseActivity;
import com.ayj.aiyijia.adapter.tab.CommonPagerAdapter;
import com.ayj.aiyijia.fragment.personcenter.YyFragment;
import com.ayj.aiyijia.utils.CommonUtils;
import com.ayj.aiyijia.utils.Constant;
import com.ayj.aiyijia.view.magicindicator.MagicIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class YyActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.vp_yy)
    ViewPager vpYy;
    @BindView(R.id.indicator_yy)
    MagicIndicator indicatorYy;

    List<String> titles = new ArrayList<>();
    private List<Fragment> listFragment = new ArrayList<>();
    CommonPagerAdapter pagerAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_yy;
    }

    @Override
    public void initDatas() {
        tvTitle.setText("我的预约");
        titles.add("全部");
        titles.add("当前");
        getListFragment();
        pagerAdapter = new CommonPagerAdapter(getSupportFragmentManager(),titles,listFragment);
        vpYy.setAdapter(pagerAdapter);
        CommonUtils.initJfMagicIndicator(indicatorYy,titles,vpYy,mContext);
    }

    private void getListFragment() {
        listFragment.add(new YyFragment().newInstance(""));
        listFragment.add(new YyFragment().newInstance(Constant.YY_YY+","+Constant.YY_QD+","+Constant.YY_FW+","+Constant.YY_CX));
    }

    @Override
    public void configViews() {

    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
