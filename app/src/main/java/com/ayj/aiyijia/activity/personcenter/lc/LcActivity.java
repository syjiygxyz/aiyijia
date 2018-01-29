package com.ayj.aiyijia.activity.personcenter.lc;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ayj.aiyijia.R;
import com.ayj.aiyijia.activity.base.BaseActivity;
import com.ayj.aiyijia.fragment.personcenter.LcFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LcActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.rb_song)
    RadioButton rbSong;
    @BindView(R.id.rb_shou)
    RadioButton rbShou;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    @BindView(R.id.fl_content)
    FrameLayout flContent;

    private Fragment[] mFragments;
    private int mIndex;


    @Override
    public int getLayoutId() {
        return R.layout.activity_lc;
    }

    @Override
    public void initDatas() {
        initFragments();
    }

    private void initFragments() {
        mFragments = new Fragment[]{LcFragment.newInstance("song"),LcFragment.newInstance("shou")};
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fl_content,mFragments[0]).commit();
        showFragmentChecked(0);
    }

    private void showFragmentChecked(int i) {
        if (mIndex == i)
            return;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (mFragments[i].isAdded()) {
            fragmentTransaction.show(mFragments[i]);
        }else {
            fragmentTransaction.add(R.id.fl_content,mFragments[i]).show(mFragments[i]);
        }
        fragmentTransaction.hide(mFragments[mIndex]);
        fragmentTransaction.commit();
        mIndex = i;
    }

    @Override
    public void configViews() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.rb_song:
                        showFragmentChecked(0);
                        break;
                    case R.id.rb_shou:
                        showFragmentChecked(1);
                        break;
                }
            }
        });
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
