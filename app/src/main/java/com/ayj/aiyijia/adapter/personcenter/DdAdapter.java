package com.ayj.aiyijia.adapter.personcenter;

import android.view.View;

import com.ayj.aiyijia.R;
import com.ayj.aiyijia.adapter.base.BaseQuickAdapter;
import com.ayj.aiyijia.adapter.base.BaseViewHolder;
import com.ayj.aiyijia.bean.ChangeMoneyBean;
import com.ayj.aiyijia.bean.DdListInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/8/11.
 */
public class DdAdapter extends BaseQuickAdapter<DdListInfo.DataBean, BaseViewHolder> {

    private View.OnClickListener oneOnClickListener;

    private View.OnClickListener twoOnClickListener;


    public DdAdapter(int layout, List<DdListInfo.DataBean> data, View.OnClickListener oneOnClickListener, View.OnClickListener twoOnClickListener) {
        super(layout, data);
        this.oneOnClickListener = oneOnClickListener;
        this.twoOnClickListener = twoOnClickListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, DdListInfo.DataBean item) {
        helper.setText(R.id.tv_order_item_id, "订单号：" + item.getSnid());
        helper.setText(R.id.tv_order_item_type, "订单类型：【" + item.getOrdertypeshow() + "】");
        helper.setText(R.id.tv_order_item_time, "下单时间：  " + item.getCreatedate());
        helper.setText(R.id.tv_order_item_state, item.getStatusshow());
        if ("MEMBERORDERTYPE002".equals(item.getOrdertype())) {
            helper.setText(R.id.tv_order_item_price, item.getSfmoneytotal() + "电子币");
        } else {
            helper.setText(R.id.tv_order_item_price, item.getSfmoneytotal() + "元");
        }
        //MEMBERORDERSTATUS001下单成功待支付
        if ("MEMBERORDERSTATUS001".equals(item.getStatus())) {
            /*取消和立即支付功能*/
            helper.setVisible(R.id.tv_orderinfo_next,true);
            helper.setVisible(R.id.tv_orderinfo_evaluate,true);
            //取消
            if (null != oneOnClickListener) {
                helper.setText(R.id.tv_orderinfo_next,"取消");
                helper.getView(R.id.tv_orderinfo_next).setTag(item);
                helper.getView(R.id.tv_orderinfo_next).setOnClickListener(oneOnClickListener);
            }
            //立即支付
            if (null != twoOnClickListener) {
                helper.setText(R.id.tv_orderinfo_evaluate,"立即支付");
                helper.getView(R.id.tv_orderinfo_evaluate).setTag(item);
                helper.getView(R.id.tv_orderinfo_evaluate).setOnClickListener(twoOnClickListener);
            }
            /*确认收货*/
        }else if("MEMBERORDERSTATUS008".equals(item.getStatus())){
            helper.setVisible(R.id.tv_orderinfo_next,false);
            helper.setVisible(R.id.tv_orderinfo_evaluate,true);
            //立即支付
            if (null != twoOnClickListener) {
                helper.setText(R.id.tv_orderinfo_evaluate,"确认收货");
                helper.getView(R.id.tv_orderinfo_evaluate).setTag(item);
                helper.getView(R.id.tv_orderinfo_evaluate).setOnClickListener(twoOnClickListener);
            }
        }else if("MEMBERORDERSTATUS002".equals(item.getStatus())||"MEMBERORDERSTATUS009".equals(item.getStatus())||"MEMBERORDERSTATUS0010".equals(item.getStatus())){
            if("MEMBERORDERSTATUS009".equals(item.getStatus())||"MEMBERORDERSTATUS0010".equals(item.getStatus())){
                /*需要再判断数量是否可以评价*/
                if (item.getOrderdetailcount().equals(item.getHaspj())) {
                    helper.setVisible(R.id.tv_orderinfo_next,false);
                } else {
                    helper.setVisible(R.id.tv_orderinfo_next,true);
                    //取消
                    if (null != oneOnClickListener) {
                        helper.setText(R.id.tv_orderinfo_next,"评价");
                        helper.getView(R.id.tv_orderinfo_next).setTag(item);
                        helper.getView(R.id.tv_orderinfo_next).setOnClickListener(oneOnClickListener);
                    }
                }
            }
            helper.setVisible(R.id.tv_orderinfo_evaluate,true);
            //立即支付
            if (null != twoOnClickListener) {
                helper.setText(R.id.tv_orderinfo_evaluate,"退货");
                helper.getView(R.id.tv_orderinfo_evaluate).setTag(item);
                helper.getView(R.id.tv_orderinfo_evaluate).setOnClickListener(twoOnClickListener);
            }
        }else{
            helper.setVisible(R.id.tv_orderinfo_next,false);
            helper.setVisible(R.id.tv_orderinfo_evaluate,false);
        }

    }
}
