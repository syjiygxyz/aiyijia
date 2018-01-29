package com.ayj.aiyijia.adapter.shopping;

import android.graphics.Paint;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayj.aiyijia.R;
import com.ayj.aiyijia.adapter.base.BaseQuickAdapter;
import com.ayj.aiyijia.adapter.base.BaseViewHolder;
import com.ayj.aiyijia.bean.FootZqShop;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by zht-pc-09 on 2017/6/10.
 * 折扣铺item布局
 */
public class ZkSpListAdapter extends BaseQuickAdapter<FootZqShop.DataBean,BaseViewHolder> {

    public ZkSpListAdapter(List<FootZqShop.DataBean> data) {
        super(R.layout.dh_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FootZqShop.DataBean item) {
        String url = item.getLogoimgurlcompressshow();
        Picasso.with(mContext).load(url).placeholder(R.mipmap.zhanweitu).error(R.mipmap.jiazaishibia).into(
                (ImageView) helper.getView(R.id.iv_foot_xd));
        helper.setText(R.id.tv_foot_xd_name,item.getMatidshow());
        helper.setText(R.id.tv_foot_xd_price,"¥"+item.getBeforeprice());
        TextView tv = helper.getView(R.id.tv_foot_xd_price);
        tv.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
        helper.setText(R.id.tv_foot_new_price,"¥"+item.getNowprice());

    }


}
