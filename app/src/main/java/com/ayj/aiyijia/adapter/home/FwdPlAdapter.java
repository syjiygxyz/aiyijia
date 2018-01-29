package com.ayj.aiyijia.adapter.home;

import android.text.TextUtils;

import com.ayj.aiyijia.R;
import com.ayj.aiyijia.adapter.base.BaseQuickAdapter;
import com.ayj.aiyijia.adapter.base.BaseViewHolder;
import com.ayj.aiyijia.bean.khq.ShopPjBean;
import com.ayj.aiyijia.view.ratingBar.ProperRatingBar;

import java.util.List;

/**
 * Created by Administrator on 2017/7/20.
 */
public class FwdPlAdapter extends BaseQuickAdapter<ShopPjBean.DataBean,BaseViewHolder> {


    public FwdPlAdapter(int layout, List<ShopPjBean.DataBean> data) {
        super(layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopPjBean.DataBean item) {
        if (TextUtils.isEmpty(item.getNickname()) || "SFPD001".equals(item.getSfnm())) {
            helper.setText(R.id.name, "匿名");
        } else {
            helper.setText(R.id.name, item.getNickname());
        }
        helper.setText(R.id.createdate, item.getCreatedate());
        helper.setText(R.id.fuwu,item.getFk());
        helper.setText(R.id.pjjl, item.getPsidshow());
        ProperRatingBar prBar = helper.getView(R.id.lowerRatingBar);
        prBar.setRating(Integer.parseInt(item.getPf()));
        helper.setText(R.id.pf,item.getPf());
    }
}
