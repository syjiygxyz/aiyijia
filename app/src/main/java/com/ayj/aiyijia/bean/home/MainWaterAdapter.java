package com.ayj.aiyijia.bean.home;

import com.ayj.aiyijia.R;
import com.ayj.aiyijia.adapter.base.BaseQuickAdapter;
import com.ayj.aiyijia.adapter.base.BaseViewHolder;
import com.ayj.aiyijia.bean.MemberCard;
import java.util.List;

/**
 * Created by zht-pc-04 on 2017/8/9.
 */

public class MainWaterAdapter extends BaseQuickAdapter<MemberCard.DataBean,BaseViewHolder>  {

    public MainWaterAdapter(int layout, List<MemberCard.DataBean> data) {
        super(layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MemberCard.DataBean item) {
        helper.setText(R.id.tv_package_name, item.getMatidshow());
        helper.setText(R.id.tv_package_price, "¥" + item.getNowprice());
    }
}
