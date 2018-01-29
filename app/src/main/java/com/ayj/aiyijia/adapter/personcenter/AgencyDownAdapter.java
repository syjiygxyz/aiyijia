package com.ayj.aiyijia.adapter.personcenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.ayj.aiyijia.R;
import com.ayj.aiyijia.bean.Agency;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by zht-pc-09 on 2016/8/2.
 */
public class AgencyDownAdapter extends BaseAdapter {

    private List<Agency.DataBean> agencys;
    LayoutInflater inflater;
    Context context;



    public AgencyDownAdapter(Context context) {
        // TODO Auto-generated constructor stub
        inflater = LayoutInflater.from(context);

    }

    public void setList(List<Agency.DataBean> agencys) {
        this.agencys = agencys;
    }
    @Override
    public int getCount() {
        return agencys == null ? 0 : agencys.size();
    }

    @Override
    public Object getItem(int i) {
        return agencys.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_agency_down, null);
            holder = new ViewHolder();

            holder.iv_xiaji_down = (ImageView) convertView.findViewById(R.id.iv_xiaji_down);
            holder.tv_xiaji_down_name = (TextView) convertView.findViewById(R.id.tv_xiaji_down_name);
            holder.tv_xiaji_down_price = (TextView)convertView.findViewById(R.id.tv_xiaji_down_price);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

                holder.tv_xiaji_down_name.setText(agencys.get(position).getMsnidshow());
                holder.tv_xiaji_down_price.setText(agencys.get(position).getProfittotal());
        if("".equals(agencys.get(0).getPmsnidimgshow())){
            holder.iv_xiaji_down.setImageResource(R.mipmap.small_loading_head);
        }else{
            Picasso.with(context).load(agencys.get(position).getPmsnidimgshow()).error(
                    R.mipmap.small_loading_head).into(
                    holder.iv_xiaji_down);
        }
        return convertView;
    }
    class ViewHolder{

        ImageView iv_xiaji_down;
        TextView tv_xiaji_down_name,tv_xiaji_down_price;
    }
}
