package com.ayj.aiyijia.activity.personcenter.wj.dsj;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayj.aiyijia.R;
import com.ayj.aiyijia.activity.base.BaseActivity;
import com.ayj.aiyijia.adapter.tab.CommonPagerAdapter;
import com.ayj.aiyijia.fragment.personcenter.DwjFragment;
import com.ayj.aiyijia.fragment.personcenter.LxjFragment;
import com.ayj.aiyijia.utils.CommonUtils;
import com.ayj.aiyijia.view.magicindicator.MagicIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/*
* 打赏卷和兑物卷
* */
public class DsqActivity extends BaseActivity {

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

    public static void jumpActivity(Context context,String title) {
        Intent mIntent = new Intent(context, DsqActivity.class);
        mIntent.putExtra("title", title);
        context.startActivity(mIntent);
    }

    @Override
    public int getLayoutId(){
        return R.layout.activity_lxj_main;
    }

    @Override
    public void initDatas() {
        mTvTitle.setText(getIntent().getStringExtra("title"));
        titles = Arrays.asList(getResources().getStringArray(R.array.dsj_titles));
    }

    @Override
    public void configViews() {
        setFragments();
        mLyPages.setOffscreenPageLimit(3);
        CommonPagerAdapter adapter = new CommonPagerAdapter(getSupportFragmentManager(), titles,
                listFragment);
        mLyPages.setAdapter(adapter);
        CommonUtils.initJfMagicIndicator(mTpLy,titles,mLyPages,mContext);
    }

    private void setFragments() {
        if(getIntent().getStringExtra("title").equals("打赏卷")){
            listFragment.add(DwjFragment.newInstance("1","TICKETSTATUS001"));
            listFragment.add(DwjFragment.newInstance("1","TICKETSTATUS003"));
            listFragment.add(DwjFragment.newInstance("1","TICKETSTATUS004"));
        }else if(getIntent().getStringExtra("title").equals("兑物卷")){
            listFragment.add(DwjFragment.newInstance("5","TICKETSTATUS001"));
            listFragment.add(DwjFragment.newInstance("5","TICKETSTATUS003"));
            listFragment.add(DwjFragment.newInstance("5","TICKETSTATUS004"));
        }
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}

