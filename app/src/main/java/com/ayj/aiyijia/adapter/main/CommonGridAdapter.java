package com.ayj.aiyijia.adapter.main;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.widget.LinearLayout;

import com.ayj.aiyijia.AyjSwApplication;
import com.ayj.aiyijia.R;
import com.ayj.aiyijia.adapter.base.BaseQuickAdapter;
import com.ayj.aiyijia.adapter.base.BaseViewHolder;
import com.ayj.aiyijia.bean.UtilityItem;
import com.ayj.aiyijia.utils.CommonUtils;

import java.util.List;

/**
 * Created by zht-pc-09 on 2017/5/24.
 * 通用的gridview适配器
 */
public class CommonGridAdapter extends BaseQuickAdapter<UtilityItem,BaseViewHolder> {

    //屏幕宽度
    private int screenWidth;

    public CommonGridAdapter(int layoutResId, List<UtilityItem> lists){
        super(layoutResId,lists);
        screenWidth = CommonUtils.getScreenWidth(AyjSwApplication.getsInstance());
    }

    @Override
    protected void convert(BaseViewHolder helper, UtilityItem item) {
            helper.setText(R.id.tv,item.getText());
            helper.setImageResource(R.id.iv,item.getResId());
//            LinearLayout ll = helper.getView(R.id.ll);
//            GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) ll.getLayoutParams();
//            params.width = screenWidth/3-1;
//            params.height = screenWidth/3-1;
//            ll.setLayoutParams(params);
    }
}
