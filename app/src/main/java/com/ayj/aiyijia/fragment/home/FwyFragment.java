package com.ayj.aiyijia.fragment.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ayj.aiyijia.AyjSwApplication;
import com.ayj.aiyijia.R;
import com.ayj.aiyijia.activity.base.NewsActivity;
import com.ayj.aiyijia.adapter.home.FwyAdapter;
import com.ayj.aiyijia.bean.Check;
import com.ayj.aiyijia.bean.cmbz.ServicePriceBean;
import com.ayj.aiyijia.bean.home.MainFwyBean;
import com.ayj.aiyijia.fragment.base.LazyFragment;
import com.ayj.aiyijia.utils.CommonUtils;
import com.ayj.aiyijia.utils.WebUtils;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.finalteam.loadingviewfinal.OnDefaultRefreshListener;
import cn.finalteam.loadingviewfinal.PtrClassicFrameLayout;
import cn.finalteam.loadingviewfinal.PtrFrameLayout;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/7/20.
 * 服务员
 */
public class FwyFragment extends LazyFragment {

    @BindView(R.id.pcf_refresh)
    PtrClassicFrameLayout pcfRefresh;

    List<MainFwyBean.Data1Bean> shopmembers = new ArrayList<>();

    List<MainFwyBean.Data2Bean> shopmembers2 = new ArrayList<>();
    FwyAdapter fwqAdapter;

    public LayoutInflater mInflater;
    @BindView(R.id.ll_qy)
    LinearLayout llQy;
    @BindView(R.id.ll_bzd)
    LinearLayout llBzd;
    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_head_fwy;
    }

    @Override
    public void fetchData() {
        mInflater = LayoutInflater.from(getActivity());
        getFwy();
        pcfRefresh.setOnRefreshListener(new OnDefaultRefreshListener() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                shopmembers.clear();
                shopmembers2.clear();
                llBzd.removeAllViews();
                llQy.removeAllViews();
                getFwy();
            }
        });
    }

    private void getFwy() {
        if (CommonUtils.isNetworkAvailable(getActivity())) {
            mProgressHub.show();
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.CH_DETAIL))
                    .addParams("json", "{\"key\":\"\",\"shopid\":\"" + AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getRegshopid() + "\"}")
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            pcfRefresh.onRefreshComplete();
                            mProgressHub.dismiss();
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            pcfRefresh.onRefreshComplete();
                            mProgressHub.dismiss();
                            MainFwyBean shopdetail = new Gson().fromJson(response, MainFwyBean.class);
                            switch (shopdetail.getErr()) {
                                case 0:
                                    shopmembers.addAll(shopdetail.getData1());
                                    shopmembers2.addAll(shopdetail.getData2());
                                    creatView();
                                    creatVieww();
                                    break;
                                default:
                                    Toast.makeText(getActivity(), shopdetail.getMsg(),
                                            Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                    });
        } else {
            showToast(R.string.TheNetIsUnAble);
        }

    }

    private void creatView() {
        for (int i = 0; i <shopmembers.size() ; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            View view = mInflater.inflate(R.layout.item_shop_member, null);
            ImageView iv_shop_member = (ImageView) view.findViewById(R.id.iv_shop_member);
            TextView tv_member_name = (TextView) view.findViewById(R.id.tv_member_name);
            TextView tv_member_introduce = (TextView) view.findViewById(R.id.tv_member_introduce);
            if (null == shopmembers.get(i).getHeadimgurlshow()|| "".equals(shopmembers.get(i).getHeadimgurlshow())){
                iv_shop_member.setImageResource(R.mipmap.zhanweitu);
            }else {
                Picasso.with(getActivity()).load(shopmembers.get(i).getHeadimgurlshow()).placeholder(R.mipmap.zhanweitu).error(R.mipmap.jiazaishibia).into(iv_shop_member);
            }
            tv_member_name.setText(shopmembers.get(i).getZwshow()+"："+shopmembers.get(i).getName());
            tv_member_introduce.setText("个人说明:"+shopmembers.get(i).getExperience());
            view.setLayoutParams(params);
            view.setTag(shopmembers.get(i));
            llBzd.addView(view);
            view.setOnClickListener(new newsOnClickListener());//设置点击事件
        }
    }

    private void creatVieww() {
        for (int i = 0; i <shopmembers2.size() ; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            View view = mInflater.inflate(R.layout.item_shop_member, null);
            ImageView iv_shop_member = (ImageView) view.findViewById(R.id.iv_shop_member);
            TextView tv_member_name = (TextView) view.findViewById(R.id.tv_member_name);
            TextView tv_member_introduce = (TextView) view.findViewById(R.id.tv_member_introduce);
            if (null == shopmembers2.get(i).getHeadimgurlshow()|| "".equals(shopmembers2.get(i).getHeadimgurlshow())){
                iv_shop_member.setImageResource(R.mipmap.zhanweitu);
            }else {
                Picasso.with(getActivity()).load(shopmembers2.get(i).getHeadimgurlshow()).placeholder(R.mipmap.zhanweitu).error(R.mipmap.jiazaishibia).into(iv_shop_member);
            }
            tv_member_name.setText(shopmembers2.get(i).getZwshow()+"："+shopmembers2.get(i).getName());
            tv_member_introduce.setText("个人说明:"+(shopmembers2.get(i).getExperience()==null?"":shopmembers2.get(i).getExperience()));
            view.setLayoutParams(params);
            view.setTag(shopmembers2.get(i));
            llQy.addView(view);
            view.setOnClickListener(new newssOnClickListener());//设置点击事件
        }
    }

    class newsOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            MainFwyBean.Data1Bean parentFunction = (MainFwyBean.Data1Bean) v.getTag();
            NewsActivity.jumpActivity(getActivity(),parentFunction.getPosition(),"服务员详情","1","");
        }
    }

    class newssOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            MainFwyBean.Data2Bean parentFunction = (MainFwyBean.Data2Bean) v.getTag();
            NewsActivity.jumpActivity(getActivity(),parentFunction.getPosition(),"服务员详情","1","");
        }
    }
}
