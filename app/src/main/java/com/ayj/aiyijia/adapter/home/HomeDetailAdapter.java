package com.ayj.aiyijia.adapter.home;

import android.widget.ImageView;

import com.ayj.aiyijia.R;
import com.ayj.aiyijia.adapter.base.BaseQuickAdapter;
import com.ayj.aiyijia.adapter.base.BaseViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2017/7/20.
 */
public class HomeDetailAdapter extends BaseQuickAdapter<String,BaseViewHolder> {


    public HomeDetailAdapter(int layout, List<String>data) {
        super(layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        Picasso.with(mContext).load(item).placeholder(R.mipmap.banner_loading).error(R.mipmap.banner_error).into(
                (ImageView) helper.getView(R.id.iv));
    }
}
