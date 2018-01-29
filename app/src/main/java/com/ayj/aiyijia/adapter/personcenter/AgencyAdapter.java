package com.ayj.aiyijia.adapter.personcenter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.ayj.aiyijia.R;
import com.ayj.aiyijia.bean.Agency;
import com.ayj.aiyijia.utils.Utility;
import com.ayj.aiyijia.view.MyListView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zht-pc-09 on 2016/8/2.
 */
public class AgencyAdapter extends BaseAdapter {

    private List<Agency.DataBean> agencys;
    LayoutInflater inflater;
    Context context;

    List<Agency.DataBean> agencys1;
    List<Agency.DataBean> agencys2;

    public AgencyAdapter(Context context) {
        // TODO Auto-generated constructor stub
        inflater = LayoutInflater.from(context);
        this.context = context;
        agencys1 = new ArrayList<>();
        agencys2 = new ArrayList<>();
    }

    public void setList(List<Agency.DataBean> agencys) {
        this.agencys = agencys;
        for(int i = 0;i<agencys.size();i++){
            if("2".equals(agencys.get(i).getAgencylevel())) {
                agencys1.add(agencys.get(i));
            }
            if("3".equals(agencys.get(i).getAgencylevel())) {
                agencys2.add(agencys.get(i));
            }
        }
    }

    @Override
    public int getCount() {
        return agencys1 == null ? 0 : agencys1.size();
    }

    @Override
    public Object getItem(int i) {
        return agencys1.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_agency, null);
            holder = new ViewHolder();

            holder.linearLayout_agency = (LinearLayout) convertView.findViewById(R.id.linearLayout_agency);
            holder.iv_xiaji = (ImageView) convertView.findViewById(R.id.iv_xiaji);
            holder.iv_xiaji_jiantou = (ImageView) convertView.findViewById(R.id.iv_xiaji_jiantou);
            holder.tv_xiaji_name = (TextView)convertView.findViewById(R.id.tv_xiaji_name);
            holder.tv_xiaji_price = (TextView)convertView.findViewById(R.id.tv_xiaji_price);
            holder.mlv_xiaji = (MyListView) convertView.findViewById(R.id.mlv_xiaji);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

            if (!TextUtils.isEmpty(agencys1.get(position).getMsnidshow())) {
                holder.tv_xiaji_name.setText(agencys1.get(position).getMsnidshow());
            }

            holder.tv_xiaji_price.setText(agencys1.get(position).getProfittotal());

        if("".equals(agencys1.get(position).getPmsnidimgshow())){
            holder.iv_xiaji.setImageResource(R.mipmap.small_loading_head);
        }else{
            Picasso.with(context).load(agencys1.get(position).getPmsnidimgshow()).error(
                    R.mipmap.small_loading_head).into(
                    holder.iv_xiaji);
        }
        holder.agencys3 = new ArrayList<Agency.DataBean>();
        for(int i = 0;i<agencys2.size();i++){
            if(agencys2.get(i).getPmsnid().equals(agencys1.get(position).getMsnid())){
                holder.agencys3.add(agencys2.get(i));
            }
        }
        holder.mAgencyDownAdapter = new AgencyDownAdapter(context);
        holder.mAgencyDownAdapter.setList(holder.agencys3);
        holder.mlv_xiaji.setAdapter(holder.mAgencyDownAdapter);


        holder.linearLayout_agency.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    if(holder.isCliecked){
                        holder.mlv_xiaji.setVisibility(View.GONE);
                        holder.iv_xiaji_jiantou.setImageResource(R.mipmap.xiangxiadlq);
                    } else {
                        holder.mlv_xiaji.setVisibility(View.VISIBLE);
                        holder.iv_xiaji_jiantou.setImageResource(R.mipmap.xiangshangdlq);
                    }
                    holder.isCliecked = !holder.isCliecked;
                    new Utility().setListViewHeightBasedOnChildren(holder.mlv_xiaji);
                }
            });
        return convertView;
    }
    class ViewHolder{
        LinearLayout linearLayout_agency;
        ImageView iv_xiaji,iv_xiaji_jiantou;
        TextView tv_xiaji_name,tv_xiaji_price;
        MyListView mlv_xiaji;
        List<Agency.DataBean> agencys3;
        AgencyDownAdapter mAgencyDownAdapter;
        Boolean isCliecked = true;
    }
}
