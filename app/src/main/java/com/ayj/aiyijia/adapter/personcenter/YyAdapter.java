package com.ayj.aiyijia.adapter.personcenter;

import android.view.View;
import android.widget.ImageView;

import com.ayj.aiyijia.R;
import com.ayj.aiyijia.adapter.base.BaseQuickAdapter;
import com.ayj.aiyijia.adapter.base.BaseViewHolder;
import com.ayj.aiyijia.bean.YyQdBean;
import com.ayj.aiyijia.utils.Constant;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by zht-pc-04 on 2017/8/11.
 */

public class YyAdapter extends BaseQuickAdapter<YyQdBean.DataBean,BaseViewHolder> {

    YyResyclerViewOnItemClickListener onItemClickListener;

    public YyAdapter(int layoutResId, List<YyQdBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, YyQdBean.DataBean item) {
        if (null == item.getImgurlcompressshow()|| "".equals(item.getImgurlcompressshow())){
            helper.setImageResource(R.id.iv_yy_item,R.mipmap.jiazaishibia);
        }else {
            Picasso.with(mContext).load(item.getImgurlcompressshow()).placeholder(R.mipmap.zhanweitu).error(R.mipmap.jiazaishibia)
                    .into((ImageView)helper.getView(R.id.iv_yy_item));
        }
        helper.setText(R.id.tv_yy_item_shop,item.getShopidshow());
        helper.setText(R.id.tv_yy_item_stat,item.getStatusshow());
        helper.setText(R.id.tv_ytime,"下单时间:"+item.getCreatetime());
        helper.setText(R.id.tv_stime,"服务时间:"+item.getServicetime());
        helper.setText(R.id.tv_name,"服务内容"+item.getPsidshow());
        if (Constant.YY_YY.equals(item.getStatus())){
            helper.setVisible(R.id.tv_yy_complete,false);
            helper.setVisible(R.id.tv_yy_cancle,true);
        }else if (Constant.YY_WC.equals(item.getStatus())){
            helper.setVisible(R.id.tv_yy_complete,true);
            helper.setVisible(R.id.tv_yy_cancle,false);
        }else {
            helper.setVisible(R.id.tv_yy_complete,false);
            helper.setVisible(R.id.tv_yy_cancle,false);
        }
        helper.getView(R.id.tv_yy_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClickListener(v,helper.getLayoutPosition());
            }
        });
        helper.getView(R.id.tv_yy_complete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClickListener(v,helper.getLayoutPosition());
            }
        });
    }
    public void setOnItemClickListener(YyResyclerViewOnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
    public interface YyResyclerViewOnItemClickListener{
        void onItemClickListener(View v ,int position);
    }
}
