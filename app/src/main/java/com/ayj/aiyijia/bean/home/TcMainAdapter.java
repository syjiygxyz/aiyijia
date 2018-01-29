package com.ayj.aiyijia.bean.home;

import com.ayj.aiyijia.R;
import com.ayj.aiyijia.adapter.base.BaseQuickAdapter;
import com.ayj.aiyijia.adapter.base.BaseViewHolder;
import com.ayj.aiyijia.bean.cmbz.PackageContent;

import java.util.List;

/**
 * Created by zht-pc-04 on 2017/8/9.
 */

public class TcMainAdapter extends BaseQuickAdapter<PackageContent.DataBean,BaseViewHolder> {

    public TcMainAdapter(int layout, List<PackageContent.DataBean> data) {
        super(layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PackageContent.DataBean item) {
        helper.setText(R.id.tv_package_name, item.getTcname());
        helper.setText(R.id.tv_package_price, "¥" + Integer.parseInt(item.getNum()) * Double.parseDouble(item.getTcmoney()));
    }
}