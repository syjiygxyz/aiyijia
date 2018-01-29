package com.ayj.aiyijia.adapter.personcenter;

import com.ayj.aiyijia.R;
import com.ayj.aiyijia.adapter.base.BaseQuickAdapter;
import com.ayj.aiyijia.adapter.base.BaseViewHolder;
import com.ayj.aiyijia.bean.AjRecordBean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/15.
 */
public class CspAdapter extends BaseQuickAdapter<AjRecordBean.DataBean,BaseViewHolder> {

    String type;

    public CspAdapter(int layout, List<AjRecordBean.DataBean> data,String type) {
        super(layout, data);
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, AjRecordBean.DataBean item) {
        if(type.equals("2")){
            helper.setText(R.id.tv_tv_1,"时长(min)");
            helper.setText(R.id.tv_tv_2,"美肤强度");
            helper.setText(R.id.tv1,item.getWorktime());
            helper.setText(R.id.tv2,item.getDw());
            helper.setText(R.id.tv_time,item.getCreatetime());
        }else if(type.equals("3")){
            helper.setText(R.id.tv_tv_1,"状态");
            helper.setText(R.id.tv_tv_2,"血糖(毫摩尔/升)");
            helper.setText(R.id.tv2,item.getNum());
            helper.setText(R.id.tv_time,item.getCreatetime());
        }

    }
}
