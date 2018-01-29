package com.ayj.aiyijia.activity.yd;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.ayj.aiyijia.AyjSwApplication;
import com.ayj.aiyijia.R;
import com.ayj.aiyijia.activity.base.BaseActivity;
import com.ayj.aiyijia.bean.Check;
import com.ayj.aiyijia.bean.MoneyLeft;
import com.ayj.aiyijia.bean.cmbz.UserTicket;
import com.ayj.aiyijia.bean.eventbus.FirstEvent;
import com.ayj.aiyijia.utils.AliPay.PayResult;
import com.ayj.aiyijia.utils.CommonUtils;
import com.ayj.aiyijia.utils.MD5Utils;
import com.ayj.aiyijia.utils.WebUtils;
import com.ayj.aiyijia.view.passwordinputdialog.PassWordDialog;
import com.ayj.aiyijia.view.passwordinputdialog.impl.DialogCompleteListener;
import com.bigkoo.pickerview.TimePickerView;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/*
*预约支付界面
* */
public class YdDetailsActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_fwd)
    TextView tv_fwd;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.iv1)
    ImageView iv1;
    @BindView(R.id.iv2)
    ImageView iv2;
    @BindView(R.id.cb_one)
    CheckBox cbOne;
    @BindView(R.id.rl_dd)
    RelativeLayout rlDd;
    @BindView(R.id.cb_two)
    CheckBox cbTwo;
    @BindView(R.id.tv_service_smallmoney_left)
    TextView tv_service_smallmoney_left;
    @BindView(R.id.tv_service_sfmoney)
    TextView tv_service_sfmoney;
    int num;
    String matid;
    Double totalMoney;
    @BindView(R.id.cb_service_order)
    CheckBox cb_service_order;
    List<UserTicket.DataBean> tyList = new ArrayList<>();
    List<UserTicket.DataBean> chooseList = new ArrayList<>();
    String tyIds = "WL-20151126-0000088,93,WL-20151126-0000089,WL-20151126-0000094";
    @BindView(R.id.rl_dd_two)
    RelativeLayout rlDdTwo;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.tv_fk)
    TextView tvFk;
    @BindView(R.id.cb_1)
    CheckBox cb1;
    @BindView(R.id.all_ll)
    LinearLayout allLl;
    @BindView(R.id.tv_count2)
    TextView tvCount2;
    @BindView(R.id.tv_fk2)
    TextView tvFk2;
    @BindView(R.id.cb_2)
    CheckBox cb2;
    @BindView(R.id.choose_ll)
    LinearLayout chooseLl;

    String dzMoney;

    String addUrl;

    String chooseCardUrl="";

    @BindView(R.id.cb_zfb)
    CheckBox cb_zfb;

    @BindView(R.id.cb_wx)
    CheckBox cb_wx;

    @BindView(R.id.btn_sure)
    Button btn_sure;

    @BindView(R.id.tv_three)
    TextView tv_three;

    String jsonobjj;

    private String servicetype = "SERVICEADDRTYPE001";//服务方式

    String details;

    List<String>menberDetails = new ArrayList<>();

    String chooseTime;

    private static final int SDK_PAY_FLAG = 1;

    String payType;

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
                        finish();
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

    @Override
    public int getLayoutId() {
        return R.layout.activity_yd_details;
    }

    @Override
    public void initDatas() {
        tvTitle.setText("确认订单");
        tv_fwd.setText(AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getRegshopidshow());
        num = getIntent().getIntExtra("num", 0);
        tv.setText(getIntent().getStringExtra("title") + "X" + num);
        matid = getIntent().getStringExtra("getMatid");
        getYe();
        getNumLeft();
        totalMoney = Double.parseDouble(new DecimalFormat("0.00").format(
                Double.parseDouble(getIntent().getStringExtra("standardsaleprice"))
                        * num));
        getLqMoney(num+"");
        tv_three.setText(Double.parseDouble(new DecimalFormat("0.00").format(
                Double.parseDouble(getIntent().getStringExtra("standardsaleprice"))
                        * (num-Integer.parseInt(tvFk.getText().toString())-Integer.parseInt(tvFk2.getText().toString()))))+"");
        details = "{\\\"pid\\\":\\\"" + matid
                + "\\\",\\\"shopid\\\":\\\"" + AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getRegshopid()
                + "\\\",\\\"num\\\":" + num + "}";
   //     tv_service_sfmoney.setText(Double.parseDouble(new DecimalFormat("0.00").format(totalMoney * 0.8)) + "");
    }

    @Override
    public void configViews() {
        cb_service_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cb_service_order.setChecked(true);
                cb_zfb.setChecked(false);
                cb_wx.setChecked(false);
                cb1.setChecked(false);
                cb2.setChecked(false);
                tvFk.setText("0");
                tvFk2.setText("0");
                getLqMoney((num-Integer.parseInt(tvFk.getText().toString())-Integer.parseInt(tvFk2.getText().toString()))+"");
            }
        });
        cb_zfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cb_zfb.setChecked(true);
                cb_service_order.setChecked(false);
                cb_wx.setChecked(false);
                cb1.setChecked(false);
                cb2.setChecked(false);
                tvFk.setText("0");
                tvFk2.setText("0");
                tv_three.setText(Double.parseDouble(new DecimalFormat("0.00").format(
                        Double.parseDouble(getIntent().getStringExtra("standardsaleprice"))
                                * (num-Integer.parseInt(tvFk.getText().toString())-Integer.parseInt(tvFk2.getText().toString()))))+"");
            }
        });
        cb_wx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cb_wx.setChecked(true);
                cb_service_order.setChecked(false);
                cb_zfb.setChecked(false);
                cb1.setChecked(false);
                cb2.setChecked(false);
                tvFk.setText("0");
                tvFk2.setText("0");
                tv_three.setText(Double.parseDouble(new DecimalFormat("0.00").format(
                        Double.parseDouble(getIntent().getStringExtra("standardsaleprice"))
                                * (num-Integer.parseInt(tvFk.getText().toString())-Integer.parseInt(tvFk2.getText().toString()))))+"");
            }
        });

    }

    @OnClick(R.id.iv_back)
    public void onClick() {
        finish();
    }

    @OnClick({R.id.rl_dd, R.id.rl_dd_two, R.id.iv_back, R.id.cb_one, R.id.cb_two})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_dd:
                cbOne.setChecked(true);
                break;
            case R.id.rl_dd_two:
                showToast("暂不支持上门服务");
                cbTwo.setChecked(false);
                break;
            case R.id.cb_one:
                cbOne.setChecked(true);
                break;
            case R.id.cb_two:
                showToast("暂不支持上门服务");
                cbTwo.setChecked(false);
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    /*获取余额*/
    private void getYe() {
        if (CommonUtils.isNetworkAvailable(mContext)) {
            mProgressHub.show();
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.ZH_ZF))
                    .addParams("json",
                            "{\"key\":\"\",\"msnid\":\"" + AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getSnid() + "\","
                                    + "\"pwd\":\"" + AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getPassWord() + "\","
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
                                    tv_service_smallmoney_left.setText("¥" + smallmoney_left);
                                    break;
                                default:
                                    showToast(check.getMsg());
                                    break;
                            }
                        }

                    });
        }
    }

    private void getNumLeft() {
        if (CommonUtils.isNetworkAvailable(mContext)) {
            mProgressHub.show();
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.GET_TYQ))
                    .addParams("json",
                            "{\"key\":\"\",\"userid\":\"" + AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getSnid()
                                    + "\","
                                    + "\"pwd\":\"" + AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getPassWord()
                                    + "\",\"status\":\"" + "TICKETSTATUS001"
                                    + "\",\"startrow\":\"" + 1 + "\","
                                    + "\"endrow\":\"" + 9999 + "\"}")
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
                                    UserTicket ticket = new Gson().fromJson(response,
                                            UserTicket.class);
                                    for (int i = 0; i < ticket.getData().size(); i++) {
                                        if (tyIds.contains(matid)) {
                                            if (tyIds.equals(ticket.getData().get(i).getTicketusescope())) {
                                                tyList.add(ticket.getData().get(i));
                                            }
                                        }
                                        if (matid.equals(ticket.getData().get(i).getTickettype())) {
                                            chooseList.add(ticket.getData().get(i));
                                        }
                                    }
                                    if (tyList.size() == 0) {
                                       allLl.setVisibility(View.GONE);
                                    } else {
                                       allLl.setVisibility(View.VISIBLE);
                                       tvCount.setText(tyList.size()+"");
                                       iv1.setImageResource(R.mipmap.appointment_1);
                                    }
                                    if (chooseList.size() == 0) {
                                        chooseLl.setVisibility(View.GONE);
                                    } else {
                                        chooseLl.setVisibility(View.VISIBLE);
                                        tvCount2.setText(chooseList.size()+"");
                                        if("WL-20151126-0000088".equals(matid)){
                                            iv2.setImageResource(R.mipmap.appointment_3);
                                        }else if("WL-20151126-0000089".equals(matid)){
                                            iv2.setImageResource(R.mipmap.appointment_5);
                                        }else if("WL-20151126-0000094".equals(matid)){
                                            iv2.setImageResource(R.mipmap.appointment_2);
                                        }else if("93".equals(matid)){
                                            iv2.setImageResource(R.mipmap.appointment_4);
                                        }else if("WL-20161205-0000679".equals(matid)){
                                            iv2.setImageResource(R.mipmap.appointment_1);
                                        }
                                        getLqMoney(num+"");
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

    /*获取零钱支付打折后的余额*/
    private void getLqMoney(String num) {
        addUrl = "{" + "\\\"pid\\\":\\\"" + matid
                + "\\\",\\\"shopid\\\":\\\"" + AyjSwApplication.getsInstance().getUserInfo().getData().get(
                0).getRegshopid() + "\\\",\\\"num\\\":\\\""
                + num + "\\\"" + "}";
        if (CommonUtils.isNetworkAvailable(mContext)) {
            mProgressHub.show();
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.DZ_LQ_PAY))
                    .addParams("json", "{\"key\":\"\",\"msnid\":\"" + AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getSnid()
                            + "\",\"pwd\":\"" + AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getPassWord()
                            + "\",\"ordertype\":\"" + "MEMBERORDERTYPE003"
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
                                    tv_service_sfmoney.setText(dzMoney);
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

    @OnClick({R.id.iv_back, R.id.all_ll, R.id.choose_ll,R.id.btn_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_sure:
                if(cb_wx.isChecked() || cb_zfb.isChecked() || cb_service_order.isChecked()||cb2.isChecked()||cb1.isChecked()){
                    if (cb_wx.isChecked() || cb_zfb.isChecked() || cb_service_order.isChecked()) {
                    /*零钱支付*/
                        if (cb_service_order.isChecked()) {
                            dialogShow();
                        } else if (cb_zfb.isChecked()) {
                        /*支付宝*/
                            showDayPickView("2");
                        } else if (cb_wx.isChecked()) {
                            showDayPickView("3");
                        }
                    }
                    if(cb2.isChecked()||cb1.isChecked()){
                        if((Integer.parseInt(tvFk2.getText().toString().trim())+Integer.parseInt(tvFk.getText().toString().trim()))==num){
                            dialogShow();
                        }else{
                            showToast("请选择对应数量的券");
                        }
                    }
                }else{
                    showToast("请选择支付方式");
                }
                break;
            case R.id.all_ll:
                if((tyList.size()+chooseList.size())<num){
                    cb1.setChecked(false);
                    cb2.setChecked(false);
                    showToast("券数量不足无法支付");
                }else{
                    cb_service_order.setChecked(false);
                    cb_wx.setChecked(false);
                    cb_zfb.setChecked(false);
                    if(cb1.isChecked()){
                  /*取消点击*/
                        cb1.setChecked(false);
                        tvFk.setText("0");
                        if(cb2.isChecked()){
                            if(Integer.parseInt(tvCount2.getText().toString())>num-Integer.parseInt(tvFk.getText().toString())){
                                tvFk2.setText((num-Integer.parseInt(tvFk.getText().toString()))+"");
                            }else{
                                tvFk2.setText(tvCount2.getText().toString());
                            }
                        }
                    }else{
                        cb1.setChecked(true);
                    /*券大于剩余的券数*/
                        if(Integer.parseInt(tvCount.getText().toString())>num-Integer.parseInt(tvFk2.getText().toString())){
                            tvFk.setText((num-Integer.parseInt(tvFk2.getText().toString()))+"");
                        }else{
                            tvFk.setText(tvCount.getText().toString());
                        }
                    }
                    if(num==(Integer.parseInt(tvFk.getText().toString())+Integer.parseInt(tvFk2.getText().toString()))){
                        tv_service_sfmoney.setText(0+"");
                    }else{
                        getLqMoney((num-Integer.parseInt(tvFk.getText().toString())-Integer.parseInt(tvFk2.getText().toString()))+"");
                    }
                    tv_three.setText(Double.parseDouble(new DecimalFormat("0.00").format(
                            Double.parseDouble(getIntent().getStringExtra("standardsaleprice"))
                                    * (num-Integer.parseInt(tvFk.getText().toString())-Integer.parseInt(tvFk2.getText().toString()))))+"");
                }

                break;
            case R.id.choose_ll:
                if((tyList.size()+chooseList.size())<num){
                    cb1.setChecked(false);
                    cb2.setChecked(false);
                    showToast("券数量不足无法支付");
                }else {
                    cb_service_order.setChecked(false);
                    cb_wx.setChecked(false);
                    cb_zfb.setChecked(false);
                    if (cb2.isChecked()) {
                  /*取消点击*/
                        cb2.setChecked(false);
                        tvFk2.setText("0");
                        if (cb1.isChecked()) {
                            if (Integer.parseInt(tvCount.getText().toString()) > num - Integer.parseInt(tvFk2.getText().toString())) {
                                tvFk.setText((num - Integer.parseInt(tvFk2.getText().toString())) + "");
                            } else {
                                tvFk.setText(tvCount.getText().toString());
                            }
                        }
                    } else {
                        cb2.setChecked(true);
                    /*券大于剩余的券数*/
                        if (Integer.parseInt(tvCount2.getText().toString()) > num - Integer.parseInt(tvFk.getText().toString())) {
                            tvFk2.setText((num - Integer.parseInt(tvFk.getText().toString())) + "");
                        } else {
                            tvFk2.setText(tvCount2.getText().toString());
                        }
                        if (num == (Integer.parseInt(tvFk.getText().toString()) + Integer.parseInt(tvFk2.getText().toString()))) {
                            tv_service_sfmoney.setText(0 + "");
                        } else {
                            getLqMoney((num - Integer.parseInt(tvFk.getText().toString()) - Integer.parseInt(tvFk2.getText().toString())) + "");
                        }
                    }
                    tv_three.setText(Double.parseDouble(new DecimalFormat("0.00").format(
                            Double.parseDouble(getIntent().getStringExtra("standardsaleprice"))
                                    * (num - Integer.parseInt(tvFk.getText().toString()) - Integer.parseInt(tvFk2.getText().toString())))) + "");
                }
                break;
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
                                    /*弹出日期选择框*/
                                    showDayPickView("1");
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

    private void setOrders() {
        chooseCardUrl="";
        if (CommonUtils.isNetworkAvailable(mContext)) {
            for (int i = 0; i <Integer.parseInt(tvFk.getText().toString()); i++) {
                if (chooseCardUrl.equals("")) {
                    chooseCardUrl = "{" + "\\\"snid\\\":\\\"" +tyList.get(i).getSnid()
                    +"\\\"" + "}";
                } else {
                    chooseCardUrl = chooseCardUrl + "," + "{" + "\\\"snid\\\":\\\"" + tyList.get(i).getSnid()
                            +"\\\"" + "}";
                }
            }
            for (int i = 0; i <Integer.parseInt(tvFk2.getText().toString()); i++) {
                if (chooseCardUrl.equals("")) {
                    chooseCardUrl = "{" + "\\\"snid\\\":\\\"" +chooseList.get(i).getSnid()
                            +"\\\""+ "}";
                } else {
                    chooseCardUrl = chooseCardUrl + "," + "{" + "\\\"snid\\\":\\\"" + chooseList.get(i).getSnid()
                            +"\\\"" + "}";
                }
            }
            String chooseCard = chooseCardUrl.equals("")?"":"[" + chooseCardUrl + "]";
            if(cb_service_order.isChecked()){
               payType = "PAYTYPE001";
            }else{
               payType = "PAYTYPE005";
            }
            jsonobjj = "{\"key\":\"\",\"msnid\":\"" + AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getSnid()
                    + "\",\"pwd\":\"" + AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getPassWord()
                    + "\",\"paytype\":\"" + payType + "\",\"ordertype\":\"" + "MEMBERORDERTYPE003"
                    + "\",\"memberaddrid\":\"" + ""
                    + "\",\"smallmoneyuse\":\"" + (tv_service_sfmoney.getText().toString().equals("0")?"":tv_service_sfmoney.getText().toString())
                    + "\",\"servicetime\":\"" + chooseTime
                    + "\",\"membermoneybagusemoney\":\"" + "" + "\",\"serviceaddrtype\":\"" + servicetype
                    + "\",\"serviceaddrdetail\":\"" + "" + "\",\"memberticketdetail\":\"" + chooseCard
                    + "\",\"orderdetail\":\"" + "["+details + "]"+"\"}";
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
                                    Toast.makeText(mContext, "购买成功",
                                            Toast.LENGTH_SHORT).show();
                                    EventBus.getDefault().post(
                                            new FirstEvent("YYDetail"));
                                    finish();
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

    /*显示年月日,dateStyle*/
    private void showDayPickView(final String typeee){
        Calendar endDate = Calendar.getInstance();
        endDate.set(2099, 0, 23,0,0,0);
        Calendar startDate = Calendar.getInstance();
        startDate.set(startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH), startDate.get(Calendar.DAY_OF_MONTH),startDate.get(Calendar.HOUR_OF_DAY),startDate.get(Calendar.MINUTE),startDate.get(Calendar.SECOND));
        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                if(typeee.equals("1")){
                    chooseTime = CommonUtils.getTime(date,"yyyy-MM-dd HH:mm:ss");
                    setOrders();
                }else{
                   /*第三方支付*/
                    chooseTime = CommonUtils.getTime(date,"yyyy-MM-dd HH:mm:ss");
                    jsonobjj = "{\"key\":\"\",\"msnid\":\"" + AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getSnid()
                            + "\",\"pwd\":\"" + AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getPassWord()
                            + "\",\"paytype\":\"" + "PAYTYPE999" + "\",\"ordertype\":\"" + "MEMBERORDERTYPE003"
                            + "\",\"memberaddrid\":\"" + ""
                            + "\",\"smallmoneyuse\":\"" + ""
                            + "\",\"servicetime\":\"" + chooseTime
                            + "\",\"membermoneybagusemoney\":\"" + "" + "\",\"serviceaddrtype\":\"" + servicetype
                            + "\",\"serviceaddrdetail\":\"" + "" + "\",\"memberticketdetail\":\"" + ""
                            + "\",\"orderdetail\":\"" + "["+details + "]"+"\"}";
                    if(typeee.equals("2")){
                        postZf(mContext, jsonobjj, "2", tv_three.getText().toString(),"爱医家","1");
                    }else if(typeee.equals("3")){
                        postZf(mContext, jsonobjj, "1", tv_three.getText().toString(),"爱医家","1");
                    }

                }

            }
        }).setType(new boolean[]{true, true, true, true, true, true}).setCancelText("取消").setDate(startDate).setRangDate(startDate, endDate).setContentSize(14).build();;//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.show();
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
}
