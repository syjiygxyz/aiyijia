package com.ayj.aiyijia.activity.personcenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ayj.aiyijia.AyjSwApplication;
import com.ayj.aiyijia.R;
import com.ayj.aiyijia.activity.base.BaseActivity;
import com.ayj.aiyijia.activity.personcenter.common.CommonMoneyLsActivity;
import com.ayj.aiyijia.activity.personcenter.dd.DdActivity;
import com.ayj.aiyijia.activity.personcenter.dlq.DlqActivity;
import com.ayj.aiyijia.activity.personcenter.infosetting.InfoSettingActivity;
import com.ayj.aiyijia.activity.personcenter.lc.LcActivity;
import com.ayj.aiyijia.activity.personcenter.message.MyMessageActivity;
import com.ayj.aiyijia.activity.personcenter.safecenter.SafeMainActivity;
import com.ayj.aiyijia.activity.personcenter.set.SettingActivity;
import com.ayj.aiyijia.activity.personcenter.set.yy.YyActivity;
import com.ayj.aiyijia.activity.personcenter.shdz.ShdzMainActivity;
import com.ayj.aiyijia.activity.personcenter.wj.WjActivity;
import com.ayj.aiyijia.adapter.base.BaseQuickAdapter;
import com.ayj.aiyijia.adapter.base.listener.OnItemClickListener;
import com.ayj.aiyijia.adapter.main.CommonGridAdapter;
import com.ayj.aiyijia.bean.Check;
import com.ayj.aiyijia.bean.PersonCenterMenberBean;
import com.ayj.aiyijia.bean.UtilityItem;
import com.ayj.aiyijia.bean.eventbus.FirstEvent;
import com.ayj.aiyijia.bean.khq.ShopInfo;
import com.ayj.aiyijia.utils.CommonUtils;
import com.ayj.aiyijia.utils.MD5Utils;
import com.ayj.aiyijia.utils.WebUtils;
import com.ayj.aiyijia.view.RoundImageView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/*
* 个人中心
* */
public class PersonCenterActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.img_head)
    RoundImageView img_head;
    @BindView(R.id.iv_right_left)
    ImageView ivRighLeft;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.tv_xiaojinku)
    TextView tvXiaojinku;
    @BindView(R.id.tv_aiyibi)
    TextView tvAiyibi;
    @BindView(R.id.tv_jinbi)
    TextView tvJinbi;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.tv_vip)
    TextView tvVip;
    @BindView(R.id.tv_name)
    TextView tvName;
    private CommonGridAdapter commonGridAdapter;
    @BindView(R.id.tv_yj)
    TextView tv_yj;
    List<UtilityItem> list = new ArrayList<>();


    @Override
    public int getLayoutId() {
        return R.layout.activity_person_center;
    }

    @Override
    public void initDatas() {
        EventBus.getDefault().register(this);
        mTvTitle.setText("个人中心");
        ivRight.setImageResource(R.mipmap.xiaoxi);
        ivRight.setVisibility(View.VISIBLE);
        ivRighLeft.setVisibility(View.VISIBLE);
        ivRighLeft.setImageResource(R.mipmap.shezhi);
        recyclerView.setFocusable(false);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));

    }

    @Override
    protected void onResume() {
        super.onResume();
        getMenberinfo();
    }

    @Override
    public void configViews() {
        list = UtilityItem.getShopCenterList();
        commonGridAdapter = new CommonGridAdapter(R.layout.cmbz_item, list);
        recyclerView.setAdapter(commonGridAdapter);
        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (list.get(position).getType()) {
                    case 1://订单
                        startActivity(new Intent(mContext, DdActivity.class));
                        break;
                    case 2://预约
                        startActivity(new Intent(mContext, YyActivity.class));
                        break;
                    case 4://服务
                        ServiceYlActivity.jumpActivity(mContext,"4");
                        break;
                    case 5://物卷
                        startActivity(new Intent(mContext,WjActivity.class));
                        break;
                    case 6://体验卷
                        ServiceYlActivity.jumpActivity(mContext,"3");
                        break;
                    case 7://礼金
                        startActivity(new Intent(mContext,LjActivity.class));
                        break;
                    case 8://代理圈
                        startActivity(new Intent(mContext,DlqActivity.class));
                        break;
                    case 9://健康记录
                        startActivity(new Intent(mContext, JkJlActivity.class));
                        break;
                    case 10://收货地址
                        startActivity(new Intent(mContext, ShdzMainActivity.class));
                        break;
                    case 11://安全中心
                        startActivity(new Intent(mContext, SafeMainActivity.class));
                        break;
                    case 12://礼仓
                        startActivity(new Intent(mContext, LcActivity.class));
                        break;
                }
            }
        });
    }

    @OnClick({R.id.tv_xiaojinku, R.id.tv_aiyibi, R.id.tv_jinbi, R.id.iv_right, R.id.iv_back, R.id.img_head, R.id.tv_yj,R.id.iv_right_left})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_xiaojinku:
                /*钱包*/
                CommonMoneyLsActivity.startActivity(mContext,"2");
                break;
            case R.id.tv_aiyibi:
                /*爱e币*/
                CommonMoneyLsActivity.startActivity(mContext,"1");
                break;
            case R.id.tv_jinbi:
                /*金币*/
                CommonMoneyLsActivity.startActivity(mContext,"3");
                break;
            case R.id.tv_yj:
                /*佣金*/
                CommonMoneyLsActivity.startActivity(mContext,"4");
                break;
            case R.id.iv_right:
                /*消息*/
                startActivity(new Intent(mContext,MyMessageActivity.class));
                break;
            case R.id.iv_right_left:
                /*设置*/
                startActivity(new Intent(mContext, SettingActivity.class));
                break;
            case R.id.img_head:
                /*个人详情*/
                startActivity(new Intent(mContext, InfoSettingActivity.class));
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus
    }

    @Subscribe
    public void onEventMainThread(FirstEvent event) {
        if (event.getMsg().equals("MyCenterClick")) {
           finish();
        }
    }

    private void getMenberinfo() {
        if (CommonUtils.isNetworkAvailable(mContext)) {
            mProgressHub.show();
            JSONObject obj = new JSONObject();
            try {
                obj.put("key","");
                obj.put("msnid",AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getSnid());
                obj.put("pwd",AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getPassWord());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.PERSON_CENTER))
                    .addParams("json",String.valueOf(obj))
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            mProgressHub.dismiss();
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            mProgressHub.dismiss();
                            Check check = new Gson().fromJson(response, Check.class);
                            switch (check.getErr()) {
                                case 0:
                                    PersonCenterMenberBean personCenterMenberBean = new Gson().fromJson(response, PersonCenterMenberBean.class);
                                    if ("".equals(personCenterMenberBean.getData().get(0).getPhotonameshow())) {
                                        img_head.setImageResource(R.mipmap.menber_loading);
                                    } else {
                                        Picasso.with(mContext).load(personCenterMenberBean.getData().get(0).getPhotonameshow()).placeholder(R.mipmap.menber_loading).error(
                                                R.mipmap.menber_error).into(
                                                img_head);
                                    }
                                    tvName.setText(personCenterMenberBean.getData().get(0).getNickname());
                                    if("".equals(personCenterMenberBean.getData().get(0).getSmallmoneylevelshow())){
                                        tvVip.setVisibility(View.GONE);
                                    }else{
                                        tvVip.setVisibility(View.VISIBLE);
                                        tvVip.setText(personCenterMenberBean.getData().get(0).getSmallmoneylevelshow());
                                    }
                                     tvXiaojinku.setText((personCenterMenberBean.getData().get(0).getSmallmoney()+"").equals("")?"0":(personCenterMenberBean.getData().get(0).getSmallmoney()+""));
                                     tvAiyibi.setText((personCenterMenberBean.getData().get(0).getTotalemoney()+"").equals("")?"0":(personCenterMenberBean.getData().get(0).getTotalemoney()+""));
                                     if((personCenterMenberBean.getData().get(0).getTotalgoldmoney()+"").equals("")||null==personCenterMenberBean.getData().get(0).getTotalgoldmoney()){
                                         tvJinbi.setText("0");
                                     }else{
                                         tvJinbi.setText(personCenterMenberBean.getData().get(0).getTotalgoldmoney()+"");
                                     }
                                     tv_yj.setText((personCenterMenberBean.getData().get(0).getCommissionmoney()+"").equals("null")?"0":(personCenterMenberBean.getData().get(0).getCommissionmoney()+""));
                                    break;
                                default:
                                    showToast(check.getMsg());
                                    break;
                            }
                        }
                    });
        } else {
            showToast(R.string.TheNetIsUnAble);
        }
    }
}


