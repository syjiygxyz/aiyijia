package com.ayj.aiyijia.adapter.personcenter;

import android.view.View;
import android.widget.ImageView;

import com.ayj.aiyijia.R;
import com.ayj.aiyijia.adapter.base.BaseQuickAdapter;
import com.ayj.aiyijia.adapter.base.BaseViewHolder;
import com.ayj.aiyijia.bean.OrderDetail;
import com.ayj.aiyijia.bean.shopping.ShopCart;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2017/8/17.
 */
public class DdDetailAdapter extends BaseQuickAdapter<OrderDetail.DataBean.OrderDetailListBean, BaseViewHolder> {

    String ordertype;

    public DdDetailAdapter(List<OrderDetail.DataBean.OrderDetailListBean> data, String ordertype) {
        super(R.layout.item_shopcart, data);
        this.ordertype = ordertype;
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderDetail.DataBean.OrderDetailListBean item) {
        if("".equals(item.getMatimgurlcompressshow())||null==item.getMatimgurlcompressshow()){
            helper.setImageResource(R.id.iv_foot_zq_shopcart,R.mipmap.zhanweitu);
        }else{
            Picasso.with(mContext).load(item.getMatimgurlcompressshow().split(
                    ",")[0]).placeholder(R.mipmap.zhanweitu).error(
                    R.mipmap.jiazaishibia).into(
                    (ImageView) helper.getView(R.id.iv_foot_zq_shopcart));
        }

        helper.setText(R.id.tv_foot_shopcart_shop, "春苗店：" + item.getShopidshow());
        helper.setText(R.id.tv_foot_shopcart_name,item.getPidshow());
        if("MEMBERORDERTYPE002".equals(ordertype)){
            helper.setText(R.id.tv_foot_shopcart_price,item.getXjmoney()+"爱e币");
            helper.setText(R.id.tv_foot_shopcart_num, "X" + item.getNum());
        } else {
            helper.setText(R.id.tv_foot_shopcart_price, "¥" + item.getXjmoney());
            helper.setText(R.id.tv_foot_shopcart_num, "X" + item.getNum());
        }
    }
}


