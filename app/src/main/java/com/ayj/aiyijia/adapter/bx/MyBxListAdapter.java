package com.ayj.aiyijia.adapter.bx;

import android.widget.ImageView;

import com.ayj.aiyijia.R;
import com.ayj.aiyijia.adapter.base.BaseQuickAdapter;
import com.ayj.aiyijia.adapter.base.BaseViewHolder;
import com.ayj.aiyijia.bean.bx.BxBdxqBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2017/7/25.
 */
public class MyBxListAdapter extends BaseQuickAdapter<BxBdxqBean.DataBean, BaseViewHolder> {

    public MyBxListAdapter(int layoutResId, List<BxBdxqBean.DataBean> lists) {
        super(layoutResId, lists);
    }

    @Override
    protected void convert(BaseViewHolder helper, BxBdxqBean.DataBean item) {
        helper.setText(R.id.tv_name,item.getIcsnidshow());
        helper.setText(R.id.tv_toubaoname,"投保人:"+item.getTbrname());
        helper.setText(R.id.tv_phonenum,"手机号:"+(null==item.getTbrmobile()?"":item.getTbrmobile().toString()));
        helper.setText(R.id.tv_bxnum,"保单号:"+item.getSnid());
        helper.setText(R.id.tv_orderstatus,item.getStatusshow());
        Picasso.with(mContext).load(item.getImgurlshow()).into((ImageView) helper.getView(R.id.iv_mybxpic));
    }
}
