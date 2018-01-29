package com.ayj.aiyijia.adapter;

import android.view.View;

import com.ayj.aiyijia.R;
import com.ayj.aiyijia.adapter.base.BaseQuickAdapter;
import com.ayj.aiyijia.adapter.base.BaseViewHolder;
import com.ayj.aiyijia.bean.BankBean;
import com.ayj.aiyijia.bean.UtilityItem;

import java.util.List;

/**
 * Created by Administrator on 2017/8/10.
 */
public class BankBeanAdapter extends BaseQuickAdapter<BankBean.DataBean, BaseViewHolder> {

    private View.OnClickListener jbOnClickListener;

    public BankBeanAdapter(int layoutResId, List<BankBean.DataBean> lists, View.OnClickListener jbOnClickListener) {
        super(layoutResId, lists);
        this.jbOnClickListener = jbOnClickListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, BankBean.DataBean item) {
        if (null == item.getBankcardtypeshow() || "".equals(item.getBankcardtypeshow().toString())) {
            helper.setVisible(R.id.tv_bindlist_bankType, false);
        } else {
            helper.setText(R.id.tv_bindlist_bankType, item.getBankcardtypeshow());
        }
        if ("BANK001".equals(item.getBankid())) {
            helper.setImageResource(R.id.img_bindlist_bankIcon, R.mipmap.industrial);
        }
        if ("BANK003".equals(item.getBankid())) {
            helper.setImageResource(R.id.img_bindlist_bankIcon, R.mipmap.china);
        }
        if ("BANK002".equals(item.getBankid())) {
            helper.setImageResource(R.id.img_bindlist_bankIcon, R.mipmap.construction);
        }
        if ("BANKOO5".equals(item.getBankid())) {
            helper.setImageResource(R.id.img_bindlist_bankIcon, R.mipmap.agricultural);
        }
        helper.setText(R.id.tv_bindlist_bankName, item.getBankidshow());
        helper.setText(R.id.tv_bindlist_bankNum, item.getBankno().substring(item.getBankno().length() - 4, item.getBankno().length()));
        helper.setText(R.id.tv_bindlist_bankData, item.getCreatetime());
        //删除
        if (null != jbOnClickListener) {
            helper.getView(R.id.tv_bindlist_bankRemove).setTag(item.getSnid());
            helper.getView(R.id.tv_bindlist_bankRemove).setOnClickListener(jbOnClickListener);
        }
    }
}
