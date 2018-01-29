package com.ayj.aiyijia.adapter.personcenter;

import android.widget.ImageView;

import com.ayj.aiyijia.R;
import com.ayj.aiyijia.adapter.base.BaseQuickAdapter;
import com.ayj.aiyijia.adapter.base.BaseViewHolder;
import com.ayj.aiyijia.bean.ChangeMoneyBean;
import com.ayj.aiyijia.bean.FootZqShop;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/10.
 */
public class ChangMoneyAdapter extends BaseQuickAdapter<ChangeMoneyBean.DataBean,BaseViewHolder> {


    public ChangMoneyAdapter(int layout, List<ChangeMoneyBean.DataBean> data) {
        super(layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChangeMoneyBean.DataBean item) {
        helper.setText(R.id.tv_title1,item.getHiscomment());
        helper.setText(R.id.tv_title2,item.getChangenum());
    }
}
