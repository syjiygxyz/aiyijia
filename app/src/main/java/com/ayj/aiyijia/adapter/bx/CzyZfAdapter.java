package com.ayj.aiyijia.adapter.bx;

import android.view.View;


import com.ayj.aiyijia.R;
import com.ayj.aiyijia.adapter.base.BaseQuickAdapter;
import com.ayj.aiyijia.adapter.base.BaseViewHolder;
import com.ayj.aiyijia.bean.cmbz.VehicleBean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/29.
 */
public class CzyZfAdapter extends BaseQuickAdapter<VehicleBean.DataBean, BaseViewHolder> {

    private View.OnClickListener scOnClickListener;

    public CzyZfAdapter(int layoutResId, List<VehicleBean.DataBean> lists, View.OnClickListener scOnClickListener) {
        super(layoutResId, lists);
        this.scOnClickListener = scOnClickListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, VehicleBean.DataBean item) {
        helper.setText(R.id.tv_name,item.getName());
        helper.setText(R.id.tv_cp,item.getCarbrandshow()+item.getCarlines());
        if (null != scOnClickListener) {
            helper.getView(R.id.iv).setTag(helper.getPosition());
            helper.getView(R.id.iv).setOnClickListener(scOnClickListener);
        }
    }
}
