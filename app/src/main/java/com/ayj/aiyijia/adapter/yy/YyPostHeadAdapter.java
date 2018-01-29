package com.ayj.aiyijia.adapter.yy;
import com.ayj.aiyijia.R;
import com.ayj.aiyijia.adapter.base.BaseQuickAdapter;
import com.ayj.aiyijia.adapter.base.BaseViewHolder;
import com.ayj.aiyijia.bean.UtilityItem;

import java.util.List;

/**
 * Created by Administrator on 2017/8/4.
 */
public class YyPostHeadAdapter extends BaseQuickAdapter<UtilityItem,BaseViewHolder> {

    public YyPostHeadAdapter(List<UtilityItem> data) {
        super(R.layout.item_head_yy_post, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UtilityItem item) {
        helper.setText(R.id.tv,item.getText());
    }
}
