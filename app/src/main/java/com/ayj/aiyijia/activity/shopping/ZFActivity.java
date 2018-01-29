package com.ayj.aiyijia.activity.shopping;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.ayj.aiyijia.AyjSwApplication;
import com.ayj.aiyijia.R;
import com.ayj.aiyijia.activity.base.BaseActivity;
import com.ayj.aiyijia.activity.personcenter.LjActivity;
import com.ayj.aiyijia.adapter.shopping.GwListAdapter;
import com.ayj.aiyijia.bean.Check;
import com.ayj.aiyijia.bean.FxBean;
import com.ayj.aiyijia.bean.MoneyLeft;
import com.ayj.aiyijia.bean.eventbus.FirstEvent;
import com.ayj.aiyijia.bean.shopping.Address;
import com.ayj.aiyijia.bean.shopping.JinmaoShopDetial;
import com.ayj.aiyijia.bean.shopping.ShopCart;
import com.ayj.aiyijia.utils.AliPay.PayResult;
import com.ayj.aiyijia.utils.CommonUtils;
import com.ayj.aiyijia.utils.MD5Utils;
import com.ayj.aiyijia.utils.WebUtils;
import com.ayj.aiyijia.view.OperatePopupWindow;
import com.ayj.aiyijia.view.passwordinputdialog.PassWordDialog;
import com.ayj.aiyijia.view.passwordinputdialog.impl.DialogCompleteListener;
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
*通用支付界面
* */
public class ZFActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.cb_lq)
    CheckBox cb_lq;
    @BindView(R.id.cb_zfb)
    CheckBox cb_zfb;
    @BindView(R.id.cb_wx)
    CheckBox cb_wx;
    @BindView(R.id.btn_sure)
    Button btn_sure; //确认下单

    String num;

    private static final int SDK_PAY_FLAG = 1;

    String order_type;

    String money;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_hy_money)
    TextView tvHyMoney;
    @BindView(R.id.textView5)
    TextView textView5;
    @BindView(R.id.tv_yf)
    TextView tvYf;
    @BindView(R.id.iv_logn)
    ImageView ivLogn;
    @BindView(R.id.tv_sp_name)
    TextView tvSpName;
    @BindView(R.id.tv_foot_shopcart_shop)
    TextView tvFootShopcartShop;
    @BindView(R.id.tv_foot_shopcart_guige)
    TextView tvFootShopcartGuige;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private Address.DataBean addressBean = null;

    private String memberaddrid;

    private OperatePopupWindow pop;

    String matid;

    private JinmaoShopDetial jinmaoShopDetial;

    GwListAdapter gwListAdapter;

    private List<ShopCart.DataBean> list = new ArrayList<>();

    Intent intent;

    String addUrl;

    String jsonobj;

    String jsonobjj;//零钱支付json

    String dzMoney;

    String detailcopynum;

    String type;

    String orderId;

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
                        if (type.equals("lc")){
                            sendGift();
                        }else {
                            showToast("支付成功");
                            finish();
                        }

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

    @Subscribe
    public void onEventMainThread(FirstEvent event) {
        if("wxzfcg".equals(event.getMsg())){
            if (type.equals("lc")){
                sendGift();
            }else {
                finish();
            }
        }
    }

    private void sendGift() {
        if (CommonUtils.isNetworkAvailable(mContext)){
            mProgressHub.show();
            JSONObject object = new JSONObject();
            try {
                object.put("key","");
                object.put("msnid",AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getSnid());
                object.put("pwd",AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getPassWord());
                object.put("orderid",orderId);
            }catch (JSONException e){
                e.printStackTrace();
            }
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.HY_SL))
                    .addParams("json",String.valueOf(object))
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            mProgressHub.dismiss();
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            mProgressHub.dismiss();
                            Check check = new Gson().fromJson(response,Check.class);
                            switch (check.getErr()){
                                case 0:
                                    //FxBean fxBean = new Gson().fromJson(response,FxBean.class);
                                    pop = new OperatePopupWindow(mContext, ZFActivity.this, "领取成功后下载app即可使用","礼品赠送",WebUtils.LC_FX_URL+AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getSnid(),WebUtils.FX_URL_IMG);
                                    pop.showAtLocation(btn_sure, Gravity.BOTTOM
                                            | Gravity.CENTER_HORIZONTAL, 0, 0);
                                    pop.update();
                                    break;
                                default:
                                    showToast(check.getMsg());
                                    break;
                            }
                        }
                    });
        }else {
            showToast(R.string.TheNetIsUnAble);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_zf;
    }

    @Override
    public void initDatas() {
        mTvTitle.setText("确认订单");
        getYe();
        initView();
        initDate();
        getLqMoney();
    }

    @Override
    public void configViews() {
        EventBus.getDefault().register(this);
        cb_lq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cb_zfb.setChecked(false);
                cb_wx.setChecked(false);
                if (cb_lq.isChecked()) {
                    cb_lq.setChecked(true);
                    tvYf.setText("¥" + dzMoney);
                } else {
                    cb_lq.setChecked(false);
                    tvYf.setText("¥" + money);
                }
            }
        });
        cb_zfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvYf.setText("¥" + money);
                cb_lq.setChecked(false);
                cb_wx.setChecked(false);
                if (cb_zfb.isChecked()) {
                    cb_zfb.setChecked(true);
                } else {
                    cb_zfb.setChecked(false);
                }
            }
        });
        cb_wx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvYf.setText("¥" + money);
                cb_zfb.setChecked(false);
                cb_lq.setChecked(false);
                if (cb_wx.isChecked()) {
                    cb_wx.setChecked(true);
                } else {
                    cb_wx.setChecked(false);
                }
            }
        });
    }
    /*初始化数据*/
    private void initDate() {
        intent = getIntent();
        addressBean = (Address.DataBean) intent.getSerializableExtra("address");
        money = intent.getStringExtra("money");
        matid = intent.getStringExtra("matid");
        order_type = intent.getStringExtra("order_type");
        num = intent.getStringExtra("num");
        detailcopynum = intent.getStringExtra("detailcopynum");
        type = intent.getStringExtra("type");
        /*商品详情*/
        if (type.equals("spDetail")) { /*从商品详情界面你点击过来的*/
            ll.setVisibility(View.VISIBLE);
            tvCount.setText("X" + " " + num);
            Picasso.with(mContext).load(getIntent().getStringExtra("img_url")).placeholder(R.mipmap.zhanweitu).error(
                    R.mipmap.jiazaishibia).into(
                    ivLogn);
            tvSpName.setText(getIntent().getStringExtra("shop_matid_show"));
            tvPrice.setText("¥" + getIntent().getStringExtra("now_price"));
            addUrl = "{" + "\\\"pid\\\":\\\"" + getIntent().getStringExtra("shop_matid")
                    + "\\\",\\\"shopid\\\":\\\"" + AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getRegshopid() + "\\\",\\\"num\\\":\\\""
                    + num + "\\\"" + "}";
        } else if (type.equals("gwc")||type.equals("lc")) {//购物车跳转过来的
            recyclerView.setVisibility(View.VISIBLE);
            list = (List<ShopCart.DataBean>) intent.getSerializableExtra("list");
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
            recyclerView.setLayoutManager(layoutManager);
            /*固定高度提交性能*/
            recyclerView.setHasFixedSize(true);
            gwListAdapter = new GwListAdapter(
                    list);
            recyclerView.setAdapter(gwListAdapter);
            for (int i = 0; i < list.size(); i++) {
                if (i == 0) {
                    addUrl = "{" + "\\\"pid\\\":\\\"" + list.get(i).getMatid()
                            + "\\\",\\\"shopid\\\":\\\"" + AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getRegshopid() + "\\\",\\\"num\\\":\\\""
                            + list.get(i).getNum() + "\\\"" + "}";
                } else {
                    addUrl = addUrl + "," + "{" + "\\\"pid\\\":\\\"" + list.get(i).getMatid()
                            + "\\\",\\\"shopid\\\":\\\"" + AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getRegshopid() + "\\\",\\\"num\\\":\\\""
                            + list.get(i).getNum() + "\\\"" + "}";
                }
            }
        }
        tvName.setText("收货人：" + addressBean.getReceivename());
        tvAddress.setText(
                "收货地址：" + addressBean.getProvinceidshow() + addressBean.getCityidshow()
                        + addressBean.getAreaidshow() + addressBean.getDetailaddr());
        tvPhone.setText(addressBean.getMobile());
        tvYf.setText("¥" + money);
        memberaddrid = addressBean.getSnid();

        jsonobj = "{\"key\":\"\",\"msnid\":\""
                + AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getSnid()
                + "\",\"pwd\":\"" + AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getPassWord() + "\",\"paytype\":\"" + "PAYTYPE999" + "\",\"ordertype\":\"" + order_type
                + "\",\"memberaddrid\":\"" + memberaddrid
                + "\",\"smallmoneyuse\":\"" + ""
                + "\",\"membermoneybagusemoney\":\"" + ""
                + "\",\"servicebooksnid\":\"" + ""
                + "\",\"memberticketdetail\":\"" + ""
                + "\",\"membercarddetail\":\"" + ""
                + "\",\"detailcopynum\":\"" + detailcopynum
                + "\",\"orderdetail\":\"" + "[" + addUrl + "]" + "\"}";
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        ll = (LinearLayout) findViewById(R.id.ll);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvPhone = (TextView) findViewById(R.id.tv_phone);
        tvAddress = (TextView) findViewById(R.id.tv_address);
        tvYf = (TextView) findViewById(R.id.tv_yf);
        tvSpName = (TextView) findViewById(R.id.tv_sp_name);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tvCount = (TextView) findViewById(R.id.tv_count);
        tvHyMoney = (TextView) findViewById(R.id.tv_hy_money);
        ivLogn = (ImageView) findViewById(R.id.iv_logn);
    }

    /*获取余额*/
    private void getYe() {
        if (CommonUtils.isNetworkAvailable(mContext)) {
            mProgressHub.show();
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.ZH_ZF))
                    .addParams("json",
                            "{\"key\":\"\",\"msnid\":\"" + AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getSnid() + "\","
                                    + "\"pwd\":\"" +AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getPassWord() + "\","
                                    + "\"shopid\":\"" + AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getRegshopid() + "\",\"matid\":\"" + matid + "\"}")
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
                                    MoneyLeft moneyleft = new Gson().fromJson(response,
                                            MoneyLeft.class);
                                    final Double smallmoney_left = Double.parseDouble(
                                            moneyleft.getData().getSmallmoney());
                                    tvHyMoney.setText("¥" + smallmoney_left);
                                    break;
                                default:
                                    showToast(check.getMsg());
                                    break;
                            }
                        }

                    });
        }
    }

    /*获取零钱支付打折后的余额*/
    private void getLqMoney() {
        if (CommonUtils.isNetworkAvailable(mContext)) {
            mProgressHub.show();
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.DZ_LQ_PAY))
                    .addParams("json", "{\"key\":\"\",\"msnid\":\"" + AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getSnid()
                            + "\",\"pwd\":\"" + AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getPassWord()
                            + "\",\"ordertype\":\"" + order_type
                            + "\",\"orderdetail\":\"" + "[" + addUrl + "]" + "\"}")
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
                                    dzMoney = check.getData().toString();
                                    jsonobjj = "{\"key\":\"\",\"msnid\":\"" + AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getSnid()
                                            + "\",\"pwd\":\"" + AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getPassWord()
                                            + "\",\"paytype\":\"" + "PAYTYPE001" + "\",\"ordertype\":\"" + order_type
                                            + "\",\"memberaddrid\":\"" + memberaddrid
                                            + "\",\"smallmoneyuse\":\"" + dzMoney
                                            + "\",\"membermoneybagusemoney\":\"" + ""
                                            + "\",\"servicebooksnid\":\"" + ""
                                            + "\",\"memberticketdetail\":\"" + ""
                                            + "\",\"membercarddetail\":\"" + ""
                                            + "\",\"detailcopynum\":\"" + detailcopynum
                                            + "\",\"orderdetail\":\"" + "[" + addUrl + "]" + "\"}";
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




    private void setOrders() {
        if (CommonUtils.isNetworkAvailable(mContext)) {
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.SEND_DD))
                    .addParams("json", jsonobjj)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(String response, int id) {

                            Check check = new Gson().fromJson(response, Check.class);
                            switch (check.getErr()) {
                                case 0:
                                    orderId = check.getData().toString();
                                    if (type.equals("lc")){
                                        sendGift();
                                    }else {
                                        Toast.makeText(mContext, "购买成功",
                                                Toast.LENGTH_SHORT).show();
                                        finish();
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

    /*显示密码输入键盘*/
    private void dialogShow() {
        new PassWordDialog(this).setTitle("请输入交易密码").setCompleteListener(
                new DialogCompleteListener() {
                    @Override
                    public void dialogCompleteListener(String money, String pwd) {
                        yzPassWord(pwd);
                    }
                }).show();
    }

    /*验证密码*/
    private void yzPassWord(String pwd) {
        if (CommonUtils.isNetworkAvailable(mContext)) {
            mProgressHub.show();
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.YZ_MA))
                    .addParams("json", "{\"key\":\"\",\"msnid\":\"" +AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getSnid() + "\","
                            + "\"pwd\":\"" +  AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getPassWord()
                            + "\",\"paypwd\":\"" + MD5Utils.getMD5String(pwd)
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
                                    setOrders();
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

    @OnClick({R.id.iv_back, R.id.btn_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_sure:
                if (cb_wx.isChecked() || cb_zfb.isChecked() || cb_lq.isChecked()) {
                    /*零钱支付*/
                    if (cb_lq.isChecked()) {
                        dialogShow();
                    } else if (cb_zfb.isChecked()) {
                        /*支付宝*/
                        if (type.equals("spDetail")) {
                            postZf(mContext, jsonobj, "2", money, getIntent().getStringExtra("shop_matid"),"1");
                        } else {
                            postZf(mContext, jsonobj, "2", money, list.get(0).getMatid(),"1");
                        }
                    } else if (cb_wx.isChecked()) {
                        if (type.equals("spDetail")) {
                            postZf(mContext, jsonobj, "1", "",getIntent().getStringExtra("shop_matid"),"");
                        } else {
                            postZf(mContext, jsonobj, "1", "", list.get(0).getMatid(),"");
                        }
                    }
                } else {
                    showToast("请选择支付方式");
                }

                break;
        }
    }

    @Override
    protected void postZfb(final String payInfo) {
        super.postZfb(payInfo);
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                // 构建PayTask对象
                PayTask alipay = new PayTask((Activity) mContext);
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
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
