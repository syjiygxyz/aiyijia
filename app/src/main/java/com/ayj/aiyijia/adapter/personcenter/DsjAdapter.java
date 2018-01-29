package com.ayj.aiyijia.adapter.personcenter;

import android.widget.ImageView;

import com.ayj.aiyijia.R;
import com.ayj.aiyijia.adapter.base.BaseQuickAdapter;
import com.ayj.aiyijia.adapter.base.BaseViewHolder;
import com.ayj.aiyijia.bean.WjBean;
import com.ayj.aiyijia.bean.cmbz.YybBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2017/9/1.
 */
public class DsjAdapter extends BaseQuickAdapter<WjBean.DataBean, BaseViewHolder> {

    String type1;

    public DsjAdapter(int layout, List<WjBean.DataBean> data, String type) {
        super(layout, data);
        type1 = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, WjBean.DataBean item) {
        helper.setText(R.id.tv_name,"名称:"+item.getTickettypeshow());
        helper.setText(R.id.tv_time,"有效期:"+item.getEtime());
        if(null==item.getOnlinetype()||"ONLINETYPE001".equals(item.getOnlinetype())){
            helper.setText(R.id.tv_right,"立即兑换");
        }else{
            helper.setText(R.id.tv_right,"到店领取");
        }
        helper.setVisible(R.id.tv_count,false);
        helper.setText(R.id.tv_gg,"规格:"+item.getStandard());
        helper.setText(R.id.tv_bh,"编号:"+item.getSnid());
        if (null==item.getImgurlshow()||"".equals(item.getImgurlshow())) {
            helper.setImageResource(R.id.img, R.mipmap.zhanweitu);
        } else {
            Picasso.with(mContext).load(item.getImgurlshow()).placeholder(R.mipmap.zhanweitu).error(R.mipmap.jiazaishibia).into(
                    (ImageView) helper.getView(R.id.img));
        }
        /*已经使用*/
        if("TICKETSTATUS003".equals(type1)){
            helper.setBackgroundRes(R.id.ll_right,R.mipmap.quan_right_no_use);
            helper.setText(R.id.tv_right,"已经使用");
        }else if("TICKETSTATUS004".equals(type1)){
            helper.setBackgroundRes(R.id.ll_right,R.mipmap.quan_right_no_use);
            helper.setText(R.id.tv_right,"已经过期");
        }else if("1".equals(type1)){
            helper.setBackgroundRes(R.id.ll_right,R.mipmap.quan_right_no_use);
            helper.setText(R.id.tv_right,"已经使用");
        }
    }
}

