package com.ayj.aiyijia.adapter.yy;

import android.view.View;

import com.ayj.aiyijia.R;
import com.ayj.aiyijia.adapter.base.BaseQuickAdapter;
import com.ayj.aiyijia.adapter.base.BaseViewHolder;
import com.ayj.aiyijia.bean.UtilityItem;
import com.ayj.aiyijia.bean.cmbz.ServicePriceBean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/18.
 */
public class YdListAdapter extends BaseQuickAdapter<ServicePriceBean.DataBean,BaseViewHolder> {

    public YdListAdapter(List<ServicePriceBean.DataBean> data) {
        super(R.layout.yy_list_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ServicePriceBean.DataBean item) {
        if("WL-20151126-0000088".equals(item.getMatid())){
            helper.setImageResource(R.id.iv_title1,R.mipmap.aijiuzuhez);
            helper.setImageResource(R.id.iv_title2,R.mipmap.yuyue_aijiu);
        } else if("WL-20151126-0000089".equals(item.getMatid())){
            helper.setImageResource(R.id.iv_title1,R.mipmap.sbtlyy);
            helper.setImageResource(R.id.iv_title2,R.mipmap.yuyue_chaosheng);
        } else if("WL-20151126-0000094".equals(item.getMatid())){
            helper.setImageResource(R.id.iv_title1,R.mipmap.fyrtli);
            helper.setImageResource(R.id.iv_title2,R.mipmap.yuyue_hanzhen);
        } else if("189".equals(item.getMatid())){
            helper.setImageResource(R.id.iv_title1,R.mipmap.yinyangbyy);
            helper.setImageResource(R.id.iv_title2,R.mipmap.yuyue_yinyangba);
        } else if("WL-20161205-0000679".equals(item.getMatid())){
            helper.setImageResource(R.id.iv_title1,R.mipmap.bxfrsyy);
            helper.setImageResource(R.id.iv_title2,R.mipmap.yuyue_bxfrs);
        }else if("93".equals(item.getMatid())) {
            helper.setImageResource(R.id.iv_title1,R.mipmap.pinputl);
            helper.setImageResource(R.id.iv_title2,R.mipmap.pptl);
        }else if("100".equals(item.getMatid())){
            helper.setImageResource(R.id.iv_title1,R.mipmap.dianzijiutl);
            helper.setImageResource(R.id.iv_title2,R.mipmap.dzjtl);
        } else if("101".equals(item.getMatid())){
            helper.setImageResource(R.id.iv_title1,R.mipmap.swdtnan);
            helper.setImageResource(R.id.iv_title2,R.mipmap.swdtnam);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }
}
