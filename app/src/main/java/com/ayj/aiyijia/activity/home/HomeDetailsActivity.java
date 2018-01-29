package com.ayj.aiyijia.activity.home;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayj.aiyijia.R;
import com.ayj.aiyijia.activity.base.BaseActivity;
import com.ayj.aiyijia.adapter.tab.CommonPagerAdapter;
import com.ayj.aiyijia.fragment.home.FwyFragment;
import com.ayj.aiyijia.fragment.home.PjFragment;
import com.ayj.aiyijia.fragment.home.SjFragment;
import com.ayj.aiyijia.utils.CommonUtils;
import com.ayj.aiyijia.view.magicindicator.MagicIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
/**社区医养主页头部**/
public class HomeDetailsActivity extends BaseActivity {
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
        return R.layout.activity_home_details;
    }

    @Override
    public void initDatas() {
        mTvTitle.setText("服务点详情");
        titles = Arrays.asList(getResources().getStringArray(R.array.fwd_titles));
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
        listFragment.add(new FwyFragment());
        listFragment.add(new SjFragment());
        listFragment.add(new PjFragment());
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
