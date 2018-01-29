package com.ayj.aiyijia.adapter.bx;


import com.ayj.aiyijia.R;
import com.ayj.aiyijia.adapter.base.BaseQuickAdapter;
import com.ayj.aiyijia.adapter.base.BaseViewHolder;
import com.ayj.aiyijia.bean.bx.BzfwBean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/26.
 */
public class CommonTwoAdapter extends BaseQuickAdapter<BzfwBean.DataBean.ContentListBean, BaseViewHolder> {

    public CommonTwoAdapter(int layoutResId, List<BzfwBean.DataBean.ContentListBean> lists) {
        super(layoutResId, lists);
    }

    @Override
    protected void convert(BaseViewHolder helper, BzfwBean.DataBean.ContentListBean item) {
        helper.setText(R.id.tv_protectcontent,item.getProtectcontent());
        helper.setText(R.id.tv_protectlimit,item.getProtectlimit()+"万年");
    }
}