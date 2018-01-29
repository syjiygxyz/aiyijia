package com.ayj.aiyijia.adapter.yyb;

import android.widget.ImageView;


import com.ayj.aiyijia.R;
import com.ayj.aiyijia.adapter.base.BaseQuickAdapter;
import com.ayj.aiyijia.adapter.base.BaseViewHolder;
import com.ayj.aiyijia.bean.FootZqShop;
import com.ayj.aiyijia.utils.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2017/7/18.
 */
public class YybAdapter extends BaseQuickAdapter<FootZqShop.DataBean,BaseViewHolder> {

    public YybAdapter(int layout, List<FootZqShop.DataBean> data) {
        super(layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FootZqShop.DataBean item) {
        helper.setText(R.id.tv,item.getMatidshow());

        Picasso.with(mContext).load(item.getImgurlshow()).placeholder(R.mipmap.menber_loading).error(R.mipmap.menber_error).transform(new CircleTransform()).into(
                (ImageView) helper.getView(R.id.iv));
    }
}