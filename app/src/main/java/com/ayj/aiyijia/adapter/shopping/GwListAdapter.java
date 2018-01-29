package com.ayj.aiyijia.adapter.shopping;

import android.widget.ImageView;

import com.ayj.aiyijia.R;
import com.ayj.aiyijia.adapter.base.BaseQuickAdapter;
import com.ayj.aiyijia.adapter.base.BaseViewHolder;
import com.ayj.aiyijia.bean.shopping.ShopCart;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by zht-pc-04 on 2017/8/9.
 */

public class GwListAdapter extends BaseQuickAdapter<ShopCart.DataBean,BaseViewHolder> {

    public GwListAdapter(List<ShopCart.DataBean> data) {
        super(R.layout.item_shopcart, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, ShopCart.DataBean item) {
        Picasso.with(mContext).load(item.getImgurlcompressshow()).placeholder(R.mipmap.zhanweitu).error(
                R.mipmap.jiazaishibia).into(
                (ImageView) helper.getView(R.id.iv_foot_zq_shopcart));
        helper.setText(R.id.tv_foot_shopcart_name, item.getMatidshow());
        helper.setText(R.id.tv_foot_shopcart_shop, "春苗店:"+item.getCarttypeshow());
        helper.setText(R.id.tv_foot_shopcart_price, "¥"+item.getPrice_now());
        helper.setText(R.id.tv_foot_shopcart_num, "X"+item.getNum());
    }
}
