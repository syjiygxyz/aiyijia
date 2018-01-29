package com.ayj.aiyijia.adapter.personcenter;

import com.ayj.aiyijia.R;
import com.ayj.aiyijia.adapter.base.BaseQuickAdapter;
import com.ayj.aiyijia.adapter.base.BaseViewHolder;
import com.ayj.aiyijia.bean.ChangeMoneyBean;
import com.ayj.aiyijia.bean.cmbz.UserTicket;
import com.ayj.aiyijia.utils.Constant;

import java.util.List;

/**
 * Created by Administrator on 2017/8/14.
 */
public class UseTicketAdapter extends BaseQuickAdapter<UserTicket.DataBean, BaseViewHolder> {


    public UseTicketAdapter(int layout, List<UserTicket.DataBean> data) {
        super(layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserTicket.DataBean item) {
        helper.setText(R.id.tv_ticket_name,item.getTickettypeshow());
        helper.setText(R.id.tv_ticket_time,"截至时间"+item.getCreatetime().split(" ")[0]+"至"+item.getEtime().split(" ")[0]);
        if(Constant.Wl_AJTL.equals(item.getTickettype())){
            helper.setBackgroundRes(R.id.linearLayout_ticket,R.mipmap.fuq_dzj);
        }else if(Constant.Wl_SB.equals(item.getTickettype())){
            helper.setBackgroundRes(R.id.linearLayout_ticket,R.mipmap.fwq_sb);
        }else if(Constant.Wl_FR.equals(item.getTickettype())){
            helper.setBackgroundRes(R.id.linearLayout_ticket,R.mipmap.fwq_fyr);
        }else if(Constant.Wl_TP.equals(item.getTickettype())){
            helper.setBackgroundRes(R.id.linearLayout_ticket,R.mipmap.fwq_pp);
        }else{
            helper.setBackgroundRes(R.id.linearLayout_ticket,R.mipmap.tyj_item);
        }
    }
}
