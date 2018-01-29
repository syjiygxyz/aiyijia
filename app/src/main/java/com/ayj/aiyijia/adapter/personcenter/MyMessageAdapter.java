package com.ayj.aiyijia.adapter.personcenter;

import android.widget.ImageView;

import com.ayj.aiyijia.R;
import com.ayj.aiyijia.adapter.base.BaseQuickAdapter;
import com.ayj.aiyijia.adapter.base.BaseViewHolder;
import com.ayj.aiyijia.bean.AjRecordBean;
import com.ayj.aiyijia.bean.MessageItemBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2017/8/28.
 */
public class MyMessageAdapter extends BaseQuickAdapter<MessageItemBean.DataBean,BaseViewHolder> {


    public MyMessageAdapter(int layout, List<MessageItemBean.DataBean> data) {
        super(layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageItemBean.DataBean item) {
            helper.setText(R.id.tv_title,item.getAcomment());
            helper.setText(R.id.time,item.getNoticetime()+"");
            if("".equals(item.getImgurlshow())){
                helper.setImageResource(R.id.iv,R.mipmap.zhanweitu);
            }else{
                Picasso.with(mContext).load(item.getImgurlshow()).placeholder(R.mipmap.zhanweitu).error(R.mipmap.jiazaishibia).into(
                        (ImageView) helper.getView(R.id.iv));
            }

    }
}
