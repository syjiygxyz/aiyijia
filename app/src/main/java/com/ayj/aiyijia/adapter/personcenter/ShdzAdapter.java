package com.ayj.aiyijia.adapter.personcenter;

import android.view.View;
import android.widget.CheckBox;

import com.ayj.aiyijia.R;
import com.ayj.aiyijia.adapter.base.BaseQuickAdapter;
import com.ayj.aiyijia.adapter.base.BaseViewHolder;
import com.ayj.aiyijia.bean.cmbz.VehicleBean;
import com.ayj.aiyijia.bean.shopping.Address;

import java.util.List;

/**
 * Created by Administrator on 2017/8/16.
 */
public class ShdzAdapter extends BaseQuickAdapter<Address.DataBean,BaseViewHolder> {

    private View.OnClickListener xgOnClickListener;

    private View.OnClickListener scOnClickListener;

    public ShdzAdapter(List<Address.DataBean> data, View.OnClickListener xgOnClickListener, View.OnClickListener scOnClickListener) {
        super(R.layout.item_address, data);
        this.xgOnClickListener = xgOnClickListener;
        this.scOnClickListener = scOnClickListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, Address.DataBean item) {
        helper.setText(R.id.tv_addrItem_name,item.getReceivename());
        helper.setText(R.id.tv_addrItem_postCode,item.getPostcode());
        helper.setText(R.id.tv_addrItem_contact,item.getMobile());
        helper.setText(R.id.tv_addrItem_area,item.getProvinceidshow()+"\t"+item.getCityidshow()+"\t"+item.getAreaidshow());
        helper.setText(R.id.tv_addrItem_detailAddr,item.getDetailaddr());
        if (null != scOnClickListener) {
            helper.getView(R.id.tv_addrItem_delate).setTag(item.getSnid());
            helper.getView(R.id.tv_addrItem_delate).setOnClickListener(scOnClickListener);
        }
        if (null != xgOnClickListener) {
            helper.getView(R.id.tv_addrItem_change).setTag(item);
            helper.getView(R.id.tv_addrItem_change).setOnClickListener(xgOnClickListener);
        }
    }
}
