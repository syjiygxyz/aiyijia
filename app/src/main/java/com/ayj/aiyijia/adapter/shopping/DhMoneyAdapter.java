package com.ayj.aiyijia.adapter.shopping;

import android.widget.ImageView;

import com.ayj.aiyijia.R;
import com.ayj.aiyijia.adapter.base.BaseQuickAdapter;
import com.ayj.aiyijia.adapter.base.BaseViewHolder;
import com.ayj.aiyijia.bean.DhMoneyBean;
import com.ayj.aiyijia.bean.FootZqShop;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2017/8/26.
 */
public class DhMoneyAdapter extends BaseQuickAdapter<DhMoneyBean.DataBean,BaseViewHolder> {

    public DhMoneyAdapter(List<DhMoneyBean.DataBean> data) {
        super(R.layout.dh_money_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DhMoneyBean.DataBean item) {
        helper.setImageResource(R.id.iv_foot_xd,R.mipmap.dh_money);
        helper.setText(R.id.tv_foot_xd_name,"会员钱包");
        helper.setText(R.id.tv_foot_xd_price, item.getParamname());
        helper.setText(R.id.tv_foot_xd_count,"¥"+item.getAmountPayable());
    }
}

