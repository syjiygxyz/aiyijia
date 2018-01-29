package com.ayj.aiyijia.adapter;


import com.ayj.aiyijia.R;
import com.ayj.aiyijia.adapter.base.BaseQuickAdapter;
import com.ayj.aiyijia.adapter.base.BaseViewHolder;
import com.ayj.aiyijia.bean.UtilityItem;

import java.util.List;

/**
 * Created by zht-pc-09 on 2017/7/3.
 */
public class MyTxlPopAdapter extends BaseQuickAdapter<UtilityItem,BaseViewHolder> {

    public MyTxlPopAdapter(int layoutResId, List<UtilityItem> lists){
        super(layoutResId,lists);
    }

    @Override
    protected void convert(BaseViewHolder helper, UtilityItem item) {
        helper.setText(R.id.my_sheet_item_tv,item.getText());
    }
}
