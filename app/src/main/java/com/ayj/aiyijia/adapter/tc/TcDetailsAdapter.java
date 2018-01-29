package com.ayj.aiyijia.adapter.tc;

import android.view.View;


import com.ayj.aiyijia.R;
import com.ayj.aiyijia.activity.base.NewsActivity;
import com.ayj.aiyijia.adapter.base.BaseQuickAdapter;
import com.ayj.aiyijia.adapter.base.BaseViewHolder;
import com.ayj.aiyijia.bean.cmbz.PackageContent;
import com.ayj.aiyijia.utils.WebUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/7/17.
 */
public class TcDetailsAdapter extends BaseQuickAdapter<PackageContent.DataBean.SalespromotiondetailListBean,BaseViewHolder> {

    public TcDetailsAdapter(int layoutResId, List<PackageContent.DataBean.SalespromotiondetailListBean> lists) {
        super(layoutResId, lists);
    }

    @Override
    protected void convert(BaseViewHolder helper, final PackageContent.DataBean.SalespromotiondetailListBean item) {
        helper.setText(R.id.tv_package_name,item.getPsidshow()+item.getNum()+"张");
        if (helper.getPosition() % 2 == 0) {
            /*偶数*/
            helper.getView(R.id.ll).setBackgroundColor(
                    mContext.getResources().getColor(R.color.white));
        } else {
            /*基数*/
            helper.getView(R.id.ll).setBackgroundColor(
                    mContext.getResources().getColor(R.color.main_dialog));
        }
        helper.setOnClickListener(R.id.tv_package_num, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*点击查看详情*/
                String h5URL="";
                String title="";
                if(item.getPsid().equals("100")){//艾灸调理物料
                    h5URL = WebUtils.H5_DETAIL+"imgtype3=COMMONIMAGETYPETH016";
                    title= "艾灸调理";
                } else if(item.getPsid().equals("WL-20151126-0000089")){//声波调理物料
                    h5URL = WebUtils.H5_DETAIL+"imgtype3=COMMONIMAGETYPETH002";
                    title= "声波调理";
                } else if(item.getPsid().equals("93")){//频谱调理
                    h5URL = WebUtils.H5_DETAIL+"imgtype3=COMMONIMAGETYPETH008";
                    title= "频谱调理";
                } else if(item.getPsid().equals("WL-20151126-0000094")){//负养热调理
                    h5URL = WebUtils.H5_DETAIL+"imgtype3=COMMONIMAGETYPETH003";
                    title= "负养热调理";
                } else if(item.getPsid().equals("189")){//营养吧调理
                    h5URL = WebUtils.H5_DETAIL+"imgtype3=COMMONIMAGETYPETH004";
                    title= "营养吧调理";
                } else if(item.getPsid().equals("WL-20161205-0000679")){//富人水
                    h5URL = WebUtils.H5_DETAIL+"imgtype3=COMMONIMAGETYPETH005";
                    title= "富人水调理";
                }else if(item.getPsid().equals("WL-20151126-0000088")){//电子酒
                    h5URL = WebUtils.H5_DETAIL+"imgtype3=COMMONIMAGETYPETH001";
                    title= "电子灸调理";
                }else if(item.getPsid().equals("101")){//生物电
                    h5URL = WebUtils.H5_DETAIL+"imgtype3=COMMONIMAGETYPETH015";
                    title= "生物电调理";
                }
                NewsActivity.jumpActivity(mContext,h5URL,title,"1","");
            }
        });
    }
}
