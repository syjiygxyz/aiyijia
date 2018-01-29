package com.ayj.aiyijia.activity.home.cz;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ayj.aiyijia.R;
import com.ayj.aiyijia.activity.base.BaseActivity;
import com.ayj.aiyijia.fragment.cz.CzAddCzFragment;
import com.ayj.aiyijia.fragment.cz.CzAddHykFragment;

import butterknife.BindView;
import butterknife.OnClick;

/*
* 充值加入
* */
public class CzAddActivity extends BaseActivity implements ViewPager.OnPageChangeListener{


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rg_tab_menu_wyxx)
    RadioGroup rgTabMenu;
    @BindView(R.id.vp_container_wyxx)
    ViewPager vpContainer;

    private InnerPagerAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_cz_add;
    }

    @Override
    public void initDatas() {
        tvTitle.setText("会员加入");
    }

    @Override
    public void configViews() {
        /**
        * 配置radiogroup按钮监听
        */
        InnerOnCheckedChangeListener listener = new InnerOnCheckedChangeListener();
        rgTabMenu.setOnCheckedChangeListener(listener);
        /**
         * 配置页面改变监听
         */
        adapter = new InnerPagerAdapter(getSupportFragmentManager());
        vpContainer.setAdapter(adapter);
        vpContainer.setOffscreenPageLimit(2);
        vpContainer.setOnPageChangeListener(this);
    }



    @OnClick(R.id.iv_back)
    public void onClick() {
        finish();
    }

    private class InnerPagerAdapter extends FragmentPagerAdapter {

        public InnerPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment frag = null;
            Bundle bd = new Bundle();
            switch (position) {
                case 0:
                    frag = new CzAddHykFragment();
                    frag.setArguments(bd);
                    break;
                case 1:
                    frag = new CzAddCzFragment();
                    break;

            }
            return frag;
        }

        @Override
        public int getCount() {
            return 2;
        }

    }


    private class InnerOnCheckedChangeListener implements
            RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            int itemIndex = 0;
            switch (checkedId) {
                case R.id.rb_tab_menu_ZGSW:
                    itemIndex = 0;
                    break;

                case R.id.rb_tab_menu_ZXKT:
                    itemIndex = 1;
                    break;
            }
            vpContainer.setCurrentItem(itemIndex);
        }

    }

    @Override
    public void onPageSelected(int position) {
        int checkId = R.id.rb_tab_menu_ZGSW;
        switch (position) {
            case 1:
                checkId = R.id.rb_tab_menu_ZXKT;
                break;
        }
        rgTabMenu.check(checkId);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }


}
