package com.ayj.aiyijia.adapter.home;

import android.widget.ImageView;

import com.ayj.aiyijia.R;
import com.ayj.aiyijia.adapter.base.BaseQuickAdapter;
import com.ayj.aiyijia.adapter.base.BaseViewHolder;
import com.ayj.aiyijia.bean.khq.ShopDetail;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2017/7/20.
 */
public class FwyAdapter extends BaseQuickAdapter<ShopDetail.DataBean,BaseViewHolder> {


    public FwyAdapter(int layout, List<ShopDetail.DataBean> data) {
        super(layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopDetail.DataBean item) {
        if (null == item.getHeadimgurlshow() || "".equals(item.getHeadimgurlshow())){
            helper.setImageResource(R.id.iv_shop_member,R.mipmap.menber_loading);
        }else {
            Picasso.with(mContext).load(item.getHeadimgurlshow()).placeholder(R.mipmap.zhanweitu).error(R.mipmap.jiazaishibia).into(
                    (ImageView) helper.getView(R.id.iv_shop_member));
        }
        helper.setText(R.id.tv_member_name,item.getZwshow()+"："+item.getName());
        helper.setText(R.id.tv_member_introduce,item.getZwshow()+"："+item.getExperience());
    }
}
