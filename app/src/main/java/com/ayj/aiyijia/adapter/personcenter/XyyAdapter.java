package com.ayj.aiyijia.adapter.personcenter;

import com.ayj.aiyijia.R;
import com.ayj.aiyijia.adapter.base.BaseQuickAdapter;
import com.ayj.aiyijia.adapter.base.BaseViewHolder;
import com.ayj.aiyijia.bean.AjRecordBean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/15.
 */
public class XyyAdapter extends BaseQuickAdapter<AjRecordBean.DataBean,BaseViewHolder> {

    public XyyAdapter(int layout, List<AjRecordBean.DataBean> data) {
        super(layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AjRecordBean.DataBean item) {
        helper.setText(R.id.tv_time,item.getCreatetime());
        helper.setText(R.id.tv1,item.getGy());
        helper.setText(R.id.tv2,item.getDy());
        helper.setText(R.id.tv3,item.getXl());

    }
}