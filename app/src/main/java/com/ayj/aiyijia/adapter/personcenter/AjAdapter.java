package com.ayj.aiyijia.adapter.personcenter;

import com.ayj.aiyijia.R;
import com.ayj.aiyijia.adapter.base.BaseQuickAdapter;
import com.ayj.aiyijia.adapter.base.BaseViewHolder;
import com.ayj.aiyijia.bean.AjRecordBean;
import com.ayj.aiyijia.bean.ChangeMoneyBean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/15.
 */
public class AjAdapter extends BaseQuickAdapter<AjRecordBean.DataBean,BaseViewHolder> {

    public AjAdapter(int layout, List<AjRecordBean.DataBean> data) {
        super(layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AjRecordBean.DataBean item) {
        helper.setText(R.id.tv_time,item.getCreatetime());
        helper.setText(R.id.tv_time_length,item.getWorktime());
        helper.setText(R.id.tv_ms,item.getAjmodelshow());
        helper.setText(R.id.tv_l,item.getLeft());
        helper.setText(R.id.tv_r,item.getRight());
        helper.setText(R.id.tv_1,item.getJt1());
        helper.setText(R.id.tv_2,item.getJt2());
        helper.setText(R.id.tv_3,item.getJt3());
        helper.setText(R.id.tv_4,item.getJt4());
    }
}
