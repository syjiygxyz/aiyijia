package com.ayj.aiyijia.activity.personcenter.dd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ayj.aiyijia.AyjSwApplication;
import com.ayj.aiyijia.R;
import com.ayj.aiyijia.activity.base.BaseActivity;
import com.ayj.aiyijia.adapter.personcenter.DdDetailAdapter;
import com.ayj.aiyijia.adapter.personcenter.DdDetailAdapterClickAdapter;
import com.ayj.aiyijia.bean.Check;
import com.ayj.aiyijia.bean.DdListInfo;
import com.ayj.aiyijia.bean.OrderDetail;
import com.ayj.aiyijia.bean.eventbus.FirstEvent;
import com.ayj.aiyijia.utils.CommonUtils;
import com.ayj.aiyijia.utils.WebUtils;
import com.ayj.aiyijia.view.DividerItemDecoration;
import com.ayj.aiyijia.view.MyLayoutManager;
import com.ayj.aiyijia.view.sweetalert.SweetAlertDialog;
import com.google.gson.Gson;
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
* 订单详情type=2 退款
* */
public class DdDetailsActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_orderinfo_state)
    TextView tv_orderinfo_state;
    @BindView(R.id.tv_orderinfo_shopid)
    TextView tv_orderinfo_shopid;
    @BindView(R.id.tv_orderinfo_time)
    TextView tv_orderinfo_time;
    @BindView(R.id.tv_orderinfo_discount)
    TextView tv_orderinfo_discount;
    @BindView(R.id.tv_orderinfo_paydetail)
    TextView tv_orderinfo_paydetail;
    @BindView(R.id.tv_orderinfo_extra)
    TextView tv_orderinfo_extra;
    @BindView(R.id.tv_orderinfo_price_qianzhui)
    TextView tv_orderinfo_price_qianzhui;
    @BindView(R.id.tv_orderinfo_price)
    TextView tv_orderinfo_price;
    @BindView(R.id.mlv_order_info)
    RecyclerView mlv_order_info;

    private String discount = "";
    private String paydetail= "";
    private String extra= "";

    DdDetailAdapter ddDetailAdapter;

    private List<OrderDetail.DataBean.OrderDetailListBean> orderDetails = new ArrayList<>();

    DdListInfo.DataBean info;

    String type;

    DdDetailAdapterClickAdapter ddDetailAdapterClickAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_dd_details;
    }

    @Override
    public void initDatas() {
        EventBus.getDefault().register(this);
        type = getIntent().getStringExtra("type");
        tvTitle.setText("订单详情");
        info = (DdListInfo.DataBean) getIntent().getSerializableExtra("info");
        tv_orderinfo_state.setText("状态：" + info.getStatusshow());
        tv_orderinfo_shopid.setText("订单编号：" + info.getSnid());
        tv_orderinfo_time.setText("下单时间：" + info.getCreatedate());
        if ("MEMBERORDERTYPE002".equals(info.getOrdertype())) {
            tv_orderinfo_price.setText(info.getSfmoneytotal()+"爱e币");
        } else {
            tv_orderinfo_price.setText("¥" + info.getSfmoneytotal());
        }
        mlv_order_info.setLayoutManager(new MyLayoutManager(mContext));
        mlv_order_info.addItemDecoration(new DividerItemDecoration(mContext,
                DividerItemDecoration.VERTICAL_LIST));
        getDetail();
    }

    @Override
    public void configViews() {

    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

    /*获取详情*/
    private void getDetail() {
        if (CommonUtils.isNetworkAvailable(mContext)) {
            JSONObject obj = new JSONObject();
            try {
                obj.put("key", "");
                obj.put("userid", AyjSwApplication.getsInstance().getUserInfo().getData().get(
                        0).getSnid());
                obj.put("pwd", AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getPassWord());
                obj.put("orderid", info.getSnid());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mProgressHub.show();
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.DD_DETAILS))
                    .addParams("json", String.valueOf(obj))
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
                                    OrderDetail orderDetail = new Gson().fromJson(response,
                                            OrderDetail.class);
                                    orderDetails.addAll(orderDetail.getData().getOrderDetailList());
                                    if(orderDetail.getData().getOrderDiscountList().size()>0) {
                                        for (int i = 0; i < orderDetail.getData().getOrderDiscountList().size();
                                             i++) {
                                            discount += orderDetail.getData().getOrderDiscountList().get(
                                                    i).getDiscountdesc() + "\t"
                                                    + orderDetail.getData().getOrderDiscountList().get(
                                                    i).getDiscountmoney() + "\n";
                                        }
                                    } else {
                                        discount = "无\n";
                                    }

                                    if(orderDetail.getData().getOrderPaytypeList().size()>0) {
                                        for (int j = 0; j < orderDetail.getData().getOrderPaytypeList().size();
                                             j++) {
                                            paydetail += orderDetail.getData().getOrderPaytypeList().get(
                                                    j).getPaytypeshow() + "\t"
                                                    + orderDetail.getData().getOrderPaytypeList().get(
                                                    j).getPaymoney() + "\n";
                                        }
                                    } else {
                                        paydetail = "无\n";
                                    }

                                    if(orderDetail.getData().getOrderExtrafeeList().size()>0) {
                                        for (int k = 0; k < orderDetail.getData().getOrderExtrafeeList().size();
                                             k++) {
                                            extra += orderDetail.getData().getOrderExtrafeeList().get(
                                                    k).getExtrafeetypeshow() + "\t"
                                                    + orderDetail.getData().getOrderExtrafeeList().get(k).getFee()
                                                    + "\n";
                                        }
                                    } else {
                                        extra = "无\n";
                                    }
                                    tv_orderinfo_discount.setText("优惠明细："+discount);
                                    tv_orderinfo_paydetail.setText("支付明细："+paydetail);
                                    tv_orderinfo_extra.setText("额外费用："+extra);
                                    /*显示*/
                                    if("1".equals(type)){
                                        if(null==ddDetailAdapter){
                                            ddDetailAdapter = new DdDetailAdapter(orderDetails,info.getOrdertype());
                                            mlv_order_info.setAdapter(ddDetailAdapter);
                                        }else{
                                            ddDetailAdapter.setNewData(orderDetails);
                                        }
                                    }else{
                                        if(null==ddDetailAdapterClickAdapter){
                                            ddDetailAdapterClickAdapter = new DdDetailAdapterClickAdapter(orderDetails,info.getOrdertype(),type,new oneOnClickListener());
                                            mlv_order_info.setAdapter(ddDetailAdapterClickAdapter);
                                        }else{
                                            ddDetailAdapterClickAdapter.setNewData(orderDetails);
                                        }

                                    }

                                    break;
                                default:
                                    Toast.makeText(mContext, check.getMsg(),
                                            Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                    });
        } else {
            showToast(R.string.TheNetIsUnAble);
        }
    }

    private class oneOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v.getTag() != null) {
                final OrderDetail.DataBean.OrderDetailListBean item = (OrderDetail.DataBean.OrderDetailListBean) v.getTag();
                if("2".equals(type)){
                    /*退款*/
                    SweetAlertDialog confirmDialog = CommonUtils.getConfirmDialog(mContext,
                            "是否确认退款?",
                            new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    cancleYy(item);
                                    sweetAlertDialog.dismiss();
                                }
                            });
                    //显示dialog
                    confirmDialog.show();
                }else if("3".equals(type)){
                    /*评价*/
                    Intent intent = new Intent();
                    intent.putExtra("info",item);
                    intent.setClass(mContext, DdCommonentActivity.class);
                    startActivity(intent);
                }
            }
        }
    }
    private void cancleYy(OrderDetail.DataBean.OrderDetailListBean item) {
        if (CommonUtils.isNetworkAvailable(mContext)) {
            mProgressHub.show();
            JSONObject cancleobj = new JSONObject();
            try {
                cancleobj.put("key", "");
                cancleobj.put("msnid", AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getSnid());
                cancleobj.put("pwd", AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getPassWord());
                cancleobj.put("orderid", item.getSnid());
                cancleobj.put("orderdetailid", item.getDetailsnid());
                cancleobj.put("refundnum", "1");
                cancleobj.put("comment", "申请退款");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.ORDER_STATUS_DELETE_URL))
                    .addParams("json", String.valueOf(cancleobj))
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
                                    showToast(check.getMsg());
                                    EventBus.getDefault().post(
                                            new FirstEvent("Dd"));
                                    orderDetails.clear();
                                    getDetail();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus
    }

    @Subscribe
    public void onEventMainThread(FirstEvent event) {
        if (event.getMsg().equals("Dd")) {
            orderDetails.clear();
            getDetail();
        }
    }
}
