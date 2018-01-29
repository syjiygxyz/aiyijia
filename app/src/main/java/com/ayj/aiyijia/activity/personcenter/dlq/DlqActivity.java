package com.ayj.aiyijia.activity.personcenter.dlq;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ayj.aiyijia.AyjSwApplication;
import com.ayj.aiyijia.R;
import com.ayj.aiyijia.activity.base.BaseActivity;
import com.ayj.aiyijia.adapter.personcenter.AgencyAdapter;
import com.ayj.aiyijia.bean.Agency;
import com.ayj.aiyijia.bean.Check;
import com.ayj.aiyijia.bean.eventbus.FirstEvent;
import com.ayj.aiyijia.utils.CommonUtils;
import com.ayj.aiyijia.utils.WebUtils;
import com.ayj.aiyijia.view.MyListView;
import com.bigkoo.pickerview.TimePickerView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/*
* 代理圈
* */
public class DlqActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right_left)
    ImageView ivRightLeft;
    @BindView(R.id.tv_date)
    TextView mTvDate;
    @BindView(R.id.rl)
    RelativeLayout rl;
    @BindView(R.id.iv_my)
    ImageView iv_my;
    @BindView(R.id.tv_my_name)
    TextView tv_my_name;
    @BindView(R.id.tv_my_price)
    TextView tv_my_price;
    @BindView(R.id.tv_shangji)
    TextView tv_shangji;
    @BindView(R.id.iv_shangji1)
    ImageView iv_shangji1;
    @BindView(R.id.tv_shangji_name1)
    TextView tv_shangji_name1;
    @BindView(R.id.iv_shangji)
    ImageView iv_shangji;
    @BindView(R.id.tv_shangji_price)
    TextView tv_shangji_price;
    @BindView(R.id.linearLayout_shangji)
    LinearLayout linearLayout_shangji;
    @BindView(R.id.tv_xiaji)
    TextView tv_xiaji;
    @BindView(R.id.lv_agency)
    MyListView lv_agency;

    @BindView(R.id.tv_total_money)
    TextView tv_total_money;

    private Double total_money = 0.0;

    private List<Agency.DataBean> agency = new ArrayList<>();
    private List<Agency.DataBean> agency1 = new ArrayList<>();

    AgencyAdapter mAgencyAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_dlq;
    }

    @Override
    public void initDatas() {
        EventBus.getDefault().register(this);
        tvTitle.setText("代理圈");
        ivRightLeft.setImageResource(R.mipmap.addaaa);
        ivRightLeft.setVisibility(View.VISIBLE);
        mTvDate.setText(CommonUtils.getTime(new Date(System.currentTimeMillis()), "yyyy-MM"));
        getDate();
    }

    @Override
    public void configViews() {

    }

    /*获取数据*/
    private void getDate() {
        total_money = 0.0;
        agency.clear();
        agency1.clear();
        mAgencyAdapter = new AgencyAdapter(mContext);
        if (CommonUtils.isNetworkAvailable(mContext)) {
            mProgressHub.show();
            JSONObject object = new JSONObject();
            try {
                object.put("key", "");
                object.put("msnid", AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getSnid());
                object.put("pwd", AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getPassWord());
                object.put("monthid", mTvDate.getText().toString().trim());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.DLQ_HQ))
                    .addParams("json", String.valueOf(object))
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
                                    Agency agencyss = new Gson().fromJson(response, Agency.class);
                                    for (int i = 0; i < agencyss.getData().size(); i++) {
                                        total_money += Double.parseDouble(
                                                agencyss.getData().get(i).getProfittotal());
                                        agency.add(agencyss.getData().get(i));
                                        if ("1".equals(agencyss.getData().get(i).getAgencylevel())) {
                                            agency1.add(agencyss.getData().get(i));
                                        }
                                    }
                                    total_money = Double.parseDouble(new java.text.DecimalFormat("0.00").format(total_money).toString());
                                    tv_total_money.setText("¥" + total_money);
                                    if (!TextUtils.isEmpty(agency1.get(0).getMsnidshow())) {
                                        tv_my_name.setText(agency1.get(0).getMsnidshow());
                                    }
                                    tv_my_price.setText(agency1.get(0).getProfittotal());
                                    if("".equals(agency1.get(0).getPmsnidimgshow())){
                                        iv_my.setImageResource(R.mipmap.small_loading_head);
                                    }else{
                                        Picasso.with(mContext).load(agency1.get(0).getPmsnidimgshow()).error(
                                                R.mipmap.small_loading_head).into(
                                                iv_my);
                                    }
                                    if (!TextUtils.isEmpty(agency1.get(0).getPmsnid())) {
                                        tv_shangji.setVisibility(View.GONE);
                                        linearLayout_shangji.setVisibility(View.VISIBLE);
                                        if (!TextUtils.isEmpty(agency1.get(0).getPmsnidshow())) {
                                            tv_shangji_name1.setText(
                                                    agency1.get(0).getPmsnidshow());
                                        }
                                        if("".equals(agency1.get(0).getPmsnidimgshow())){
                                            iv_shangji1.setImageResource(R.mipmap.small_loading_head);
                                        }else{
                                            Picasso.with(mContext).load(agency1.get(0).getPmsnidimgshow()).error(
                                                    R.mipmap.small_loading_head).into(
                                                    iv_shangji1);
                                        }
                                    } else {
                                        tv_shangji.setVisibility(View.VISIBLE);
                                        linearLayout_shangji.setVisibility(View.GONE);
                                    }

                                    mAgencyAdapter.setList(agency);
                                    lv_agency.setAdapter(mAgencyAdapter);
                                    break;
                                default:
                                    showToast(check.getMsg());
                            }
                        }
                    });
        } else {
            showToast(R.string.TheNetIsUnAble);
        }
    }

    @OnClick({R.id.iv_back, R.id.iv_right_left, R.id.rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_right_left:
                /*加上级*/
                startActivity(new Intent(mContext,ServiceSjActivity.class));
                break;
            case R.id.rl:
                showDayPickView();
                break;
        }
    }

    /*显示年月日*/
    private void showDayPickView() {
        Calendar startDate = Calendar.getInstance();
        startDate.set(2013, 0, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(endDate.get(Calendar.YEAR), endDate.get(Calendar.MONTH), endDate.get(Calendar.DAY_OF_MONTH));
        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                mTvDate.setText(CommonUtils.getTime(date, "yyyy-MM"));
                getDate();
            }
        }).setType(new boolean[]{true, true, false, false, false, false}).setCancelText("取消").setDate(CommonUtils.StringToCalendar(mTvDate.getText().toString(), "yyyy-MM")).setRangDate(startDate, endDate).build();
        ;//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus
    }

    @Subscribe
    public void onEventMainThread(FirstEvent event) {
        if (event.getMsg().equals("ServiceSj")) {
            getDate();
        }
    }
}
