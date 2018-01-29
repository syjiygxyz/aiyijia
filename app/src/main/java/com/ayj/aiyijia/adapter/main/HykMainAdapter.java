package com.ayj.aiyijia.adapter.main;

import com.ayj.aiyijia.R;
import com.ayj.aiyijia.adapter.base.BaseQuickAdapter;
import com.ayj.aiyijia.adapter.base.BaseViewHolder;
import com.ayj.aiyijia.bean.MemberCard;

import java.util.List;

/**
 * Created by zht-pc-04 on 2017/8/9.
 */

public class HykMainAdapter extends BaseQuickAdapter<MemberCard.DataBean,BaseViewHolder> {
    public HykMainAdapter(int layout, List<MemberCard.DataBean> data) {
        super(layout, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, MemberCard.DataBean item) {
        helper.setText(R.id.tv_package_name,item.getMatidshow());
        helper.setText(R.id.tv_package_price,"¥" + item.getNowprice());
        if("康乐A卡(福利卡)".equals(item.getMatclassshow())){
            helper.setBackgroundRes(R.id.linearLayout_package,R.mipmap.klhyak);
        } else if("康乐B卡(会员卡)".equals(item.getMatclassshow())) {
            helper.setBackgroundRes(R.id.linearLayout_package, R.mipmap.klhybka);
        }
    }
}
