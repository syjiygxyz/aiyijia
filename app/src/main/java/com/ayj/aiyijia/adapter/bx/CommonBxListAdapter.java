package com.ayj.aiyijia.adapter.bx;

import android.widget.ImageView;

import com.ayj.aiyijia.R;
import com.ayj.aiyijia.adapter.base.BaseQuickAdapter;
import com.ayj.aiyijia.adapter.base.BaseViewHolder;
import com.ayj.aiyijia.bean.bx.CommonBxListBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2017/7/26.
 */
public class CommonBxListAdapter extends BaseQuickAdapter<CommonBxListBean.DataBean, BaseViewHolder> {

    public CommonBxListAdapter(int layoutResId, List<CommonBxListBean.DataBean> lists) {
        super(layoutResId, lists);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommonBxListBean.DataBean item) {
        Picasso.with(mContext).load(item.getImgurlshow()).placeholder(R.mipmap.zhanweitu).error(
                R.mipmap.jiazaishibia).into((ImageView) helper.getView(R.id.iv_pic));
        helper.setText(R.id.tv_bxtype,item.getMatidshow());
        helper.setText(R.id.tv_person,item.getFitpeople());
        helper.setText(R.id.tv_price,item.getMinpremium());
        if (null==item.getBzqxmax()){
            helper.setText(R.id.tv_time,"");
        }else {
            if (item.getBzqxstart().equals(item.getBzqxmax())) {
                helper.setText(R.id.tv_time,item.getBzqxstart());
            } else {
                helper.setText(R.id.tv_time,item.getBzqxstart() + item.getBzqxunitshow() + "-" + item.getBzqxmax() + item.getBzqxunitshow());
            }
        }
    }
}
