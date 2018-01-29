package com.ayj.aiyijia.adapter;

import android.widget.ImageView;

import com.ayj.aiyijia.R;
import com.ayj.aiyijia.adapter.base.BaseQuickAdapter;
import com.ayj.aiyijia.adapter.base.BaseViewHolder;
import com.ayj.aiyijia.bean.WjBean;
import com.ayj.aiyijia.bean.cmbz.YybBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2017/9/22.
 */
public class DhQuanAdapter extends BaseQuickAdapter<YybBean.DataBean, BaseViewHolder> {

    public DhQuanAdapter(int layout, List<YybBean.DataBean> data) {
        super(layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, YybBean.DataBean item) {
        helper.setText(R.id.tv_name,"名称:"+item.getTickettypeshow());
        helper.setText(R.id.tv_time,"有效期:"+item.getEtime());
        helper.setText(R.id.tv_right,"立即兑换");
        helper.setVisible(R.id.tv_count,false);
        helper.setVisible(R.id.tv_gg,false);
        helper.setText(R.id.tv_bh,"编号:"+item.getSnid());
        if (null==item.getImgurlshow()||"".equals(item.getImgurlshow())) {
            helper.setImageResource(R.id.img, R.mipmap.zhanweitu);
        } else {
            Picasso.with(mContext).load(item.getImgurlshow()).placeholder(R.mipmap.zhanweitu).error(R.mipmap.jiazaishibia).into(
                    (ImageView) helper.getView(R.id.img));
        }
    }
}


