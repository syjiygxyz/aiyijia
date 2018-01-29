package com.ayj.aiyijia.fragment.personcenter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.ayj.aiyijia.AyjSwApplication;
import com.ayj.aiyijia.R;
import com.ayj.aiyijia.activity.personcenter.dd.DdDetailsActivity;
import com.ayj.aiyijia.activity.shopping.ShoppingDetailActivity;
import com.ayj.aiyijia.adapter.personcenter.DdAdapter;
import com.ayj.aiyijia.bean.Check;
import com.ayj.aiyijia.bean.DdListInfo;
import com.ayj.aiyijia.bean.eventbus.FirstEvent;
import com.ayj.aiyijia.fragment.base.BaseFragment;
import com.ayj.aiyijia.fragment.base.LazyFragment;
import com.ayj.aiyijia.utils.ACache;
import com.ayj.aiyijia.utils.AliPay.GetOrderInfo;
import com.ayj.aiyijia.utils.AliPay.PayResult;
import com.ayj.aiyijia.utils.CommonUtils;
import com.ayj.aiyijia.utils.MD5Utils;
import com.ayj.aiyijia.utils.WebUtils;
import com.ayj.aiyijia.view.DividerItemDecoration;
import com.ayj.aiyijia.view.OperatePopupWindow;
import com.ayj.aiyijia.view.ProgressHUD;
import com.ayj.aiyijia.view.passwordinputdialog.PassWordDialog;
import com.ayj.aiyijia.view.passwordinputdialog.impl.DialogCompleteListener;
import com.ayj.aiyijia.view.sweetalert.SweetAlertDialog;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.finalteam.loadingviewfinal.HeaderAndFooterRecyclerViewAdapter;
import cn.finalteam.loadingviewfinal.OnDefaultRefreshListener;
import cn.finalteam.loadingviewfinal.OnLoadMoreListener;
import cn.finalteam.loadingviewfinal.PtrClassicFrameLayout;
import cn.finalteam.loadingviewfinal.PtrFrameLayout;
import cn.finalteam.loadingviewfinal.RecyclerViewFinal;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/8/11.
 * 订单
 * MEMBERORDERSTATUS001下单成功待支付,有取消和支付功能
 */
public class DdFragment extends LazyFragment {

    @BindView(R.id.recycler_view)
    RecyclerViewFinal recyclerView;
    @BindView(R.id.pcf_refresh)
    PtrClassicFrameLayout pcfRefresh;

    @BindView(R.id.ll)
    LinearLayout ll;
    int start = 0;

    int countSize = 10;

    DdAdapter ddAdapter;

    private Bundle bd;

    String status;

    String orderType;

    OperatePopupWindow pop;

    DdListInfo.DataBean item;

    private List<DdListInfo.DataBean> shopList = new ArrayList<>();

    private static final int SDK_PAY_FLAG = 1;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        showToast("支付成功");
                        getActivity().finish();
                    } else {
                        // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                        if (TextUtils.equals(resultStatus, "8000")) {
                            showToast("支付确认中");
                        } else {
                            // 判断resultStatus 为非"9000"则代表可能支付失败
                            // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                            showToast("支付失败");
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    public static DdFragment newInstance(String status,String orderType) {
        DdFragment fragment = new DdFragment();
        Bundle args = new Bundle();
        args.putString("classid", status);
        args.putString("ordertype",orderType);
        fragment.setArguments(args);
        return fragment;
    }

    private void getPl() {
        if (CommonUtils.isNetworkAvailable(getActivity())) {
            mProgressHub.show();
            JSONObject object = new JSONObject();
            try {
                object.put("key","");
                object.put("userid",AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getSnid());
                object.put("pwd",AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getPassWord());
                object.put("status",status);
                object.put("ordertype",orderType);
                object.put("startrow",start);
                object.put("endrow",start+countSize);
            }catch (JSONException e){
                e.printStackTrace();
            }
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.DD_XX))
                    .addParams("json",String.valueOf(object))
                    /*.addParams("json", "{\"key\":\"\",\"userid\":\"" + AyjSwApplication.getsInstance().getUserInfo().getData().get(
                            0).getSnid() + "\",\"pwd\":\""
                            + AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getPassWord() + "\",\"status\":\""
                            + status
                            + "\",\"ordertype\":\"MEMBERORDERTYPE001,MEMBERORDERTYPE002,"
                            + "MEMBERORDERTYPE003,MEMBERORDERTYPE004,MEMBERORDERTYPE005,"
                            + "MEMBERORDERTYPE007,MEMBERORDERTYPE011,MEMBERORDERTYPE013,MEMBERORDERTYPE019\","
                            + "\"startrow\":\"" + start + "\",\"endrow\":\"" + (start + countSize) + "\"}")*/
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            mProgressHub.dismiss();
                            pcfRefresh.onRefreshComplete();
                            recyclerView.onLoadMoreComplete();
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            mProgressHub.dismiss();
                            pcfRefresh.onRefreshComplete();
                            recyclerView.onLoadMoreComplete();
                            Check check = new Gson().fromJson(response, Check.class);
                            switch (check.getErr()) {
                                case 0:
                                    start = start + countSize + 1;
                                    DdListInfo ddListInfo = new Gson().fromJson(response,
                                            DdListInfo.class);
                                    if (ddListInfo.getData().size() != 0) {
                                        shopList.addAll(ddListInfo.getData());
                                    }
                                    if (null == ddAdapter) {
                                        ddAdapter = new DdAdapter(R.layout.item_order, shopList, new oneOnClickListener(), new twoClickListener());
                                        recyclerView.setAdapter(ddAdapter);
                                    } else {
                                        ddAdapter.setNewData(shopList);
                                    }
                                    if (ddListInfo.getData().size() < countSize) {
                                        recyclerView.setHasLoadMore(false);
                                    } else {
                                        recyclerView.setHasLoadMore(true);
                                    }
                                    break;
                                default:
                                    Toast.makeText(getActivity(), check.getMsg(),
                                            Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                    });
        } else {
            showToast(R.string.TheNetIsUnAble);
        }
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dd;
    }

    @Override
    public void fetchData() {
        EventBus.getDefault().register(this);
        if (getArguments() != null) {
            bd = getArguments();
            status = bd.getString("classid");
            if (null == bd.getString("ordertype")){
                orderType = "MEMBERORDERTYPE001,MEMBERORDERTYPE002,MEMBERORDERTYPE003,MEMBERORDERTYPE004,MEMBERORDERTYPE005,MEMBERORDERTYPE007,MEMBERORDERTYPE011,MEMBERORDERTYPE013,MEMBERORDERTYPE019";
            }else {
                orderType = bd.getString("ordertype");
            }
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getPl();
        pcfRefresh.setOnRefreshListener(new OnDefaultRefreshListener() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                start = 0;
                shopList.clear();
                getPl();
            }
        });
        recyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                //发起加载更多请求
                getPl();
            }
        });
        pop = new OperatePopupWindow(getActivity(), itemsOnClick);
        recyclerView.setOnItemClickListener(
                new HeaderAndFooterRecyclerViewAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(RecyclerView.ViewHolder holder, int position) {
                        Intent intent = new Intent();
                        intent.putExtra("type","1");
                        intent.putExtra("info",shopList.get(position));
                        intent.setClass(getActivity(), DdDetailsActivity.class);
                        startActivity(intent);
                    }
                });
    }

    /**
     * 第一个按钮监听
     */
    private class oneOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v.getTag() != null) {
                final DdListInfo.DataBean item = (DdListInfo.DataBean) v.getTag();
                if ("MEMBERORDERSTATUS001".equals(item.getStatus())) {
                    /*取消订单*/
                    SweetAlertDialog confirmDialog = CommonUtils.getConfirmDialog(getActivity(),
                            "是否确认取消订单?",
                            new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    quitDd(item.getSnid());
                                    sweetAlertDialog.dismiss();
                                }
                            });
                    //显示dialog
                    confirmDialog.show();
                }else if("MEMBERORDERSTATUS002".equals(item.getStatus())||"MEMBERORDERSTATUS009".equals(item.getStatus())||"MEMBERORDERSTATUS0010".equals(item.getStatus())){
                    /*评论*/
                    Intent intent = new Intent();
                    intent.putExtra("type","3");
                    intent.putExtra("info",item);
                    intent.setClass(getActivity(), DdDetailsActivity.class);
                    startActivity(intent);
                }
            }
        }
    }

    /**
     * 第二个按钮监听
     */
    private class twoClickListener implements View.OnClickListener {

        @Override
        public void onClick(final View v) {
            if (v.getTag() != null) {
                item = (DdListInfo.DataBean) v.getTag();
                if ("MEMBERORDERSTATUS001".equals(item.getStatus())) {
                    /*支付订单*/
                    pop.showAtLocation(ll, Gravity.BOTTOM
                            | Gravity.CENTER_HORIZONTAL, 0, 0);
                    pop.update();
                }else if("MEMBERORDERSTATUS008".equals(item.getStatus())){
                    /*确定收获*/
                    SweetAlertDialog confirmDialog = CommonUtils.getConfirmDialog(getActivity(),
                            "是否确认收货?",
                            new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    dialogShow(item.getSnid());
                                    sweetAlertDialog.dismiss();
                                }
                            });
                    //显示dialog
                    confirmDialog.show();
                }else if("MEMBERORDERSTATUS002".equals(item.getStatus())||"MEMBERORDERSTATUS009".equals(item.getStatus())||"MEMBERORDERSTATUS0010".equals(item.getStatus())){
                    /*退货*/
                    Intent intent = new Intent();
                    intent.putExtra("type","2");
                    intent.putExtra("info",item);
                    intent.setClass(getActivity(), DdDetailsActivity.class);
                    startActivity(intent);
                }
            }
        }
    }

    /*取消订单*/
    private void quitDd(String orderid) {

        if (CommonUtils.isNetworkAvailable(getActivity())) {
            JSONObject obj = new JSONObject();
            try {
                obj.put("key", "");
                obj.put("msnid", AyjSwApplication.getsInstance().getUserInfo().getData().get(
                        0).getSnid());
                obj.put("pwd", AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getPassWord());
                obj.put("orderid", orderid);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mProgressHub.show();
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.QX_DD))
                    .addParams("json", String.valueOf(obj))
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            mProgressHub.dismiss();
                            pcfRefresh.onRefreshComplete();
                            recyclerView.onLoadMoreComplete();
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            mProgressHub.dismiss();
                            pcfRefresh.onRefreshComplete();
                            recyclerView.onLoadMoreComplete();
                            Check check = new Gson().fromJson(response, Check.class);
                            switch (check.getErr()) {
                                case 0:
                                    showToast(check.getMsg());
                                    EventBus.getDefault().post(
                                            new FirstEvent("Dd"));
                                    break;
                                default:
                                    Toast.makeText(getActivity(), check.getMsg(),
                                            Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                    });
        } else {
            showToast(R.string.TheNetIsUnAble);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);//反注册EventBus
    }

    @Subscribe
    public void onEventMainThread(FirstEvent event) {
        if (event.getMsg().equals("Dd")) {
            start = 0;
            shopList.clear();
            getPl();
        }
    }

    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {

        public void onClick(View v) {

            switch (v.getId()) {
                /*微信*/
                case R.id.share_wechat:
                    pop.dismiss();
                    CommonUtils.paybywx(getContext(),item.getSnid());
                    break;
                /*支付宝*/
                case R.id.share_wxcircle:
                    pop.dismiss();
                    paybyali(item.getSfmoneytotal(),item.getSnid(),"爱医家");
                    break;
                default:
                    break;
            }


        }

    };

    /*支付宝支付*/
    private void paybyali(String totalMoney,String orderId,String matidshow) {
        String orderInfo = "";
        String orderInfo_en = "";
        try {
            orderInfo = GetOrderInfo.getOrderInfo(orderId,
                    URLEncoder.encode(matidshow,
                            "UTF-8"), orderId, totalMoney,WebUtils.getRequestUrl(WebUtils.ZFB_TZ));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            /**
             * 仅需对sign 做URL编码
             */
            orderInfo_en = URLEncoder.encode(orderInfo, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (CommonUtils.isNetworkAvailable(getActivity())) {
            final String finalOrderInfo = orderInfo;
            OkHttpUtils.get()
                    .url(WebUtils.getRequestUrl(WebUtils.ZFB_URL)+"?json="
                            + orderInfo_en)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                        }
                        @Override
                        public void onResponse(final String response, int id) {

                            Check check = new Gson().fromJson(response, Check.class);

                            switch (check.getErr()) {
                                case 0:
                                    final String payInfo =
                                            finalOrderInfo + "&sign=\"" + check.getMsg() + "\"&"
                                                    + "sign_type=\"RSA\"";
                                    Runnable payRunnable = new Runnable() {
                                        @Override
                                        public void run() {
                                            // 构建PayTask对象
                                            PayTask alipay = new PayTask(getActivity());
                                            // 调用支付接口，获取支付结果
                                            String result = alipay.pay(payInfo, true);
                                            Message msg = new Message();
                                            msg.what = SDK_PAY_FLAG;
                                            msg.obj = result;
                                            mHandler.sendMessage(msg);
                                        }
                                    };
                                    // 必须异步调用
                                    Thread payThread = new Thread(payRunnable);
                                    payThread.start();
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

    /*显示密码输入键盘*/
    private void dialogShow(final String snid) {
        new PassWordDialog(getActivity()).setTitle("请输入交易密码").setCompleteListener(
                new DialogCompleteListener() {
                    @Override
                    public void dialogCompleteListener(String money, String pwd) {
                        yzPassWord(pwd,snid);
                    }
                }).show();
    }

    /*验证密码*/
    private void yzPassWord(String pwd, final String snid) {
        if (CommonUtils.isNetworkAvailable(getActivity())) {
            mProgressHub.show();
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.YZ_MA))
                    .addParams("json", "{\"key\":\"\",\"msnid\":\""
                            + AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getSnid() + "\","
                            + "\"pwd\":\"" + AyjSwApplication.getsInstance().getUserInfo().getData().get(
                            0).getPassWord() + "\",\"paypwd\":\""
                            + MD5Utils.getMD5String(pwd)
                            + "\"}")
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
                                    sureOrder(snid);
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

    private void sureOrder(String orderid) {
        if (CommonUtils.isNetworkAvailable(getActivity())) {
            mProgressHub.show();
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.SURE_SH))
                    .addParams("json",
                            "{\"key\":\"\",\"msnid\":\"" + AyjSwApplication.getsInstance().getUserInfo().getData().get(
                                    0).getSnid() + "\",\"pwd\":\""
                                    + AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getPassWord()
                                    + "\",\"snid\":\"" + orderid + "\"}")
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
                                    showToast("确认成功");
                                    EventBus.getDefault().post(
                                            new FirstEvent("Dd"));
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
