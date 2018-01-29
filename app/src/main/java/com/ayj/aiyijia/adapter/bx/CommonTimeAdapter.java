package com.ayj.aiyijia.adapter.bx;

import android.graphics.Color;

import com.ayj.aiyijia.R;
import com.ayj.aiyijia.adapter.base.BaseQuickAdapter;
import com.ayj.aiyijia.adapter.base.BaseViewHolder;
import com.ayj.aiyijia.bean.bx.TimeIsCheckBean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/26.
 */
public class CommonTimeAdapter extends BaseQuickAdapter<TimeIsCheckBean, BaseViewHolder> {

    String dw;

    public CommonTimeAdapter(int layoutResId, List<TimeIsCheckBean> lists, String dw) {
        super(layoutResId, lists);
        this.dw = dw;
    }

    @Override
    protected void convert(BaseViewHolder helper, TimeIsCheckBean item) {
        helper.setText(R.id.tv1,item.getTimee()+dw);
        if(item.getCheck()){
            helper.setBackgroundRes(R.id.tv1,R.drawable.cz_background_theme);
            helper.setTextColor(R.id.tv1, Color.parseColor("#ffffff"));
        }else{
            helper.setBackgroundRes(R.id.tv1,R.drawable.cz_back_ground);
            helper.setTextColor(R.id.tv1,Color.parseColor("#2e5250"));
        }
    }
}
