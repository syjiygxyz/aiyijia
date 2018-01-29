package com.ayj.aiyijia.adapter.cz;

import android.graphics.Color;

import com.ayj.aiyijia.R;
import com.ayj.aiyijia.adapter.base.BaseQuickAdapter;
import com.ayj.aiyijia.adapter.base.BaseViewHolder;
import com.ayj.aiyijia.bean.SmallmoneyFace;

import java.util.List;

/**
 * Created by zht-pc-09 on 2017/7/11.
 */
public class CzAdapter extends BaseQuickAdapter<SmallmoneyFace.DataBean,BaseViewHolder> {

    public CzAdapter(int layout, List<SmallmoneyFace.DataBean> data) {
        super(layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SmallmoneyFace.DataBean item) {
              helper.setText(R.id.tv1,item.getFacevalue());
              if(null==item.getGiftemoney()||"".equals(item.getGiftemoney().toString())){
                  helper.setVisible(R.id.tv_head,false);
              }else{
                  helper.setVisible(R.id.tv_head,true);
                  helper.setText(R.id.tv_head,"赠送"+item.getGiftemoney()+"爱e币");
              }
              if(item.getTrue()){
                  helper.setBackgroundRes(R.id.tv1,R.drawable.cz_background_theme);
                  helper.setTextColor(R.id.tv1, Color.parseColor("#ffffff"));
              }else{
                  helper.setBackgroundRes(R.id.tv1,R.drawable.cz_back_ground);
                  helper.setTextColor(R.id.tv1,Color.parseColor("#2e5250"));
              }
    }
}
