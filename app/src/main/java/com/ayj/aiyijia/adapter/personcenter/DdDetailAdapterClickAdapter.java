package com.ayj.aiyijia.adapter.personcenter;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ayj.aiyijia.R;
import com.ayj.aiyijia.adapter.base.BaseQuickAdapter;
import com.ayj.aiyijia.adapter.base.BaseViewHolder;
import com.ayj.aiyijia.bean.OrderDetail;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2017/8/17.
 * 2退款
 */
public class DdDetailAdapterClickAdapter extends BaseQuickAdapter<OrderDetail.DataBean.OrderDetailListBean, BaseViewHolder> {

    String ordertype;

    private View.OnClickListener oneOnClickListener;

    String type;

    public DdDetailAdapterClickAdapter(List<OrderDetail.DataBean.OrderDetailListBean> data, String ordertype,String type,View.OnClickListener oneOnClickListener) {
        super(R.layout.item_dd_detail, data);
        this.oneOnClickListener = oneOnClickListener;
        this.ordertype = ordertype;
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderDetail.DataBean.OrderDetailListBean item) {
        if("".equals(item.getMatimgurlcompressshow())){
            helper.setImageResource(R.id.iv_tuihuo,R.mipmap.zhanweitu);
        }else{
            Picasso.with(mContext).load(item.getMatimgurlcompressshow().split(
                    ",")[0]).placeholder(R.mipmap.zhanweitu).error(
                    R.mipmap.jiazaishibia).into(
                    (ImageView) helper.getView(R.id.iv_foot_zq_shopcart));
        }
        helper.setText(R.id.tv_foot_shopcart_shop, "春苗店：" + item.getShopidshow());
        helper.setText(R.id.tv_foot_shopcart_name, item.getPidshow());
        if ("MEMBERORDERTYPE002".equals(ordertype)) {
            helper.setText(R.id.tv_foot_shopcart_price, item.getXjmoney() + "爱e币");
            helper.setText(R.id.tv_foot_shopcart_num, "X" + item.getNum());
        } else {
            helper.setText(R.id.tv_foot_shopcart_price, "¥" + item.getXjmoney());
            helper.setText(R.id.tv_foot_shopcart_num, "X" + item.getNum());
        }
        if("2".equals(type)){
            if(TextUtils.isEmpty(item.getRefundstatus())) {
                helper.setVisible(R.id.iv_tuihuo,true);
                helper.setImageResource(R.id.iv_tuihuo,R.mipmap.sqthwddd);
                //退款
                if (null != oneOnClickListener) {
                    helper.getView(R.id.iv_tuihuo).setTag(item);
                    helper.getView(R.id.iv_tuihuo).setOnClickListener(oneOnClickListener);
                }
            } else {
                helper.setVisible(R.id.iv_tuihuo,false);
                helper.setVisible(R.id.tv_tuihuo,true);
                helper.setText(R.id.tv_tuihuo,item.getRefundstatusshow());
            }
        } else if("3".equals(type)){
            if("0".equals(item.getHaspj())) {
                helper.setVisible(R.id.iv_tuihuo,true);
                helper.setImageResource(R.id.iv_tuihuo,R.mipmap.pingjiaysq);
                if (null != oneOnClickListener) {
                    helper.getView(R.id.iv_tuihuo).setTag(item);
                    helper.getView(R.id.iv_tuihuo).setOnClickListener(oneOnClickListener);
                }
            } else {
                helper.setVisible(R.id.iv_tuihuo,false);
            }
        }

    }

}
