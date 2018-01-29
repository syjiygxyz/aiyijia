package com.ayj.aiyijia.adapter.bx;

import com.ayj.aiyijia.R;
import com.ayj.aiyijia.adapter.base.BaseQuickAdapter;
import com.ayj.aiyijia.adapter.base.BaseViewHolder;
import com.ayj.aiyijia.bean.cmbz.InsurancePriceBean;

import java.util.List;

/**
 * Created by zht-pc-04 on 2017/7/14.
 */

public class InsurancePriceListAdapter extends BaseQuickAdapter<InsurancePriceBean.DataBean.OrderDetailListBean,BaseViewHolder> {
    public InsurancePriceListAdapter(List<InsurancePriceBean.DataBean.OrderDetailListBean> data) {
        super(R.layout.item_insurance_price,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, InsurancePriceBean.DataBean.OrderDetailListBean item) {
        helper.setText(R.id.tv_protectcontent,item.getContentsnidshow());
        helper.setText(R.id.tv_protectlimit,"¥"+item.getPrice());
    }
}
