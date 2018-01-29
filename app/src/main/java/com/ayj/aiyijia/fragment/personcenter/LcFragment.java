package com.ayj.aiyijia.fragment.personcenter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ayj.aiyijia.R;
import com.ayj.aiyijia.adapter.tab.CommonPagerAdapter;
import com.ayj.aiyijia.fragment.base.BaseFragment;
import com.ayj.aiyijia.fragment.base.LazyFragment;
import com.ayj.aiyijia.utils.CommonUtils;
import com.ayj.aiyijia.utils.Constant;
import com.ayj.aiyijia.view.magicindicator.MagicIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/9/29.
 */

public class LcFragment extends BaseFragment {
    @BindView(R.id.tp_ly)
    MagicIndicator tpLy;
    @BindView(R.id.ly_pages)
    ViewPager lyPages;

    private List<String> titles = new ArrayList<>();
    private List<Fragment> listFragment = new ArrayList<>();

    public static LcFragment newInstance(String type){
        LcFragment lcFragment = new LcFragment();
        Bundle b = new Bundle();
        b.putString("type",type);
        lcFragment.setArguments(b);
        return lcFragment;
    }
    @Override
    public void initDatas() {
        titles = Arrays.asList(getResources().getStringArray(R.array.lc_titles));
        if (null != getArguments()){
            if ("song".equals(getArguments().getString("type"))){
                    setFragments();
            }else if ("shou".equals(getArguments().getString("type"))){
                    setSlFragments();
            }
        }
    }

    private void setSlFragments() {
        listFragment.add(DdFragment.newInstance("",Constant.ORDER_SL));
        listFragment.add(DdFragment.newInstance(Constant.STA_DFH,Constant.ORDER_SL));
        listFragment.add(DdFragment.newInstance(Constant.STA_DSH,Constant.ORDER_SL));
        listFragment.add(DdFragment.newInstance(Constant.STA_YSH,Constant.ORDER_SL));
    }

    @Override
    public void configViews() {
        lyPages.setOffscreenPageLimit(4);
        CommonPagerAdapter adapter = new CommonPagerAdapter(getChildFragmentManager(), titles,
                listFragment);
        lyPages.setAdapter(adapter);
        CommonUtils.initJfMagicIndicator(tpLy,titles,lyPages,getActivity());
    }

    private void setFragments() {
        listFragment.add(LcListFragment.newInstance(""));
        listFragment.add(LcListFragment.newInstance(Constant.STA_DFH));
        listFragment.add(LcListFragment.newInstance(Constant.STA_DSH));
        listFragment.add(LcListFragment.newInstance(Constant.STA_YSH));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_lc;
    }

}
