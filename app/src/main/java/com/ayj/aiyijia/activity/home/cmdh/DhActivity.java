package com.ayj.aiyijia.activity.home.cmdh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayj.aiyijia.R;
import com.ayj.aiyijia.activity.base.BaseActivity;
import com.ayj.aiyijia.activity.base.NewsActivity;
import com.ayj.aiyijia.activity.home.HomeActivity;
import com.ayj.aiyijia.activity.home.splist.SplistActivity;
import com.ayj.aiyijia.activity.personcenter.PersonCenterActivity;
import com.ayj.aiyijia.activity.shopping.ShoppingDetailActivity;
import com.ayj.aiyijia.adapter.base.BaseQuickAdapter;
import com.ayj.aiyijia.adapter.base.listener.OnItemClickListener;
import com.ayj.aiyijia.adapter.main.CommonGridAdapter;
import com.ayj.aiyijia.bean.UtilityItem;
import com.ayj.aiyijia.utils.Constant;
import com.ayj.aiyijia.utils.WebUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.loadingviewfinal.HeaderAndFooterRecyclerViewAdapter;
import cn.finalteam.loadingviewfinal.RecyclerViewFinal;

/*
* 兑换
* */
public class DhActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rlv)
    RecyclerView clv;

    CommonGridAdapter mCommonGridAdapter;

    List<UtilityItem> gridLists = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_dh;
    }

    @Override
    public void initDatas() {
        tvTitle.setText("春苗兑换");
        clv.setLayoutManager(new GridLayoutManager(mContext, 2));
        gridLists = UtilityItem.dhGridList();
        mCommonGridAdapter = new CommonGridAdapter(R.layout.dh_main_item, gridLists);
        clv.setAdapter(mCommonGridAdapter);
        /*九个栏目的点击*/
        clv.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                switch (gridLists.get(position).getType()) {
                    case 1://金币
                        SplistActivity.startActivity(mContext, "no_hot", "MEMBERORDERTYPE025", Constant.MAT_CLASS1_LJXW, "jbdh");
                        break;
                    case 2://爱e币
                        SplistActivity.startActivity(mContext, "no_hot", "MEMBERORDERTYPE002", Constant.MAT_CLASS1_LJXW, "aebdh");
                        break;
                    case 3://物卷
                        SplistActivity.startActivity(mContext, "no_hot", "MEMBERORDERTYPE032", Constant.MAT_CLASS1_LJXW, "wjdh");
                        break;
                }
            }
        });
    }

    @Override
    public void configViews() {

    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
