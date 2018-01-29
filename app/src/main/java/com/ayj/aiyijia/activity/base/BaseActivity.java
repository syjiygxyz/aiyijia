/**
 * Copyright 2016 JustWayward Team
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ayj.aiyijia.activity.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ayj.aiyijia.AyjSwApplication;
import com.ayj.aiyijia.R;
import com.ayj.aiyijia.activity.login.LoginActivity;
import com.ayj.aiyijia.activity.personcenter.safecenter.SetZfPwdActivity;
import com.ayj.aiyijia.bean.Check;
import com.ayj.aiyijia.bean.UserBean;
import com.ayj.aiyijia.bean.eventbus.FirstEvent;
import com.ayj.aiyijia.utils.ACache;
import com.ayj.aiyijia.utils.AliPay.GetOrderInfo;
import com.ayj.aiyijia.utils.CommonUtils;
import com.ayj.aiyijia.utils.MD5Utils;
import com.ayj.aiyijia.utils.WebUtils;
import com.ayj.aiyijia.view.ProgressHUD;
import com.ayj.aiyijia.view.sweetalert.SweetAlertDialog;
import com.githang.statusbar.StatusBarCompat;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.ButterKnife;
import okhttp3.Call;

public abstract class BaseActivity extends AppCompatActivity {

    protected Context mContext;

    protected Gson gson;

    protected ACache aCache;

    public ProgressHUD mProgressHub = null;

    private static final int SYSTEM_UI_FLAG_LIGHT_STATUS_BAR =  1<< 13;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, Color.parseColor("#2e5250"), true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(getLayoutId());
        mContext = this;
        aCache = CommonUtils.getAcache();
        mProgressHub = CommonUtils.createProgressDialog(mContext, "正在加载中..", true);
        ButterKnife.bind(this);
        initDatas();
        configViews();
    }

    @Override
    protected void onPause() {
        if (mProgressHub != null && mProgressHub.isShowing()) {
            mProgressHub.dismiss();
        }
        super.onPause();
    }

    public abstract int getLayoutId();

    public abstract void initDatas();
    /**
     * 对各种控件进行设置、适配、填充数据
     */
    public abstract void configViews();

    public void showToast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }
    public void showToast(int content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mProgressHub != null && mProgressHub.isShowing()) {
            mProgressHub.dismiss();
        }
    }

    /*支付订单
   * 上下文
   * 输入解析对象
   * 支付方式，1微信支付，2支付宝
   * */
    public void postZf(final Context mContext, String jsonObj, final String zfType, final String totalMoney,
                       final String matidshow, final String typeAdds){
        if (CommonUtils.isNetworkAvailable(mContext)) {
            final ProgressHUD mProgressHub = CommonUtils.createProgressDialog(mContext, "提交信息中...", false);
            if (mProgressHub != null)
                mProgressHub.show();
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.SEND_DD))
                    .addParams("json",jsonObj)
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
                                    String orderId = check.getData().toString();
                                    if("1".equals(zfType)){
                                        /*微信*/
                                        CommonUtils.paybywx(mContext,orderId);
                                    }else if("2".equals(zfType)){
                                        /*支付宝*/
                                        paybyali(totalMoney,orderId,matidshow,typeAdds);
                                    }
                                    break;
                                default:
                                    CommonUtils.showToast(mContext,check.getMsg());
                                    break;
                            }
                        }
                    });
        } else {
            CommonUtils.showToast(mContext, R.string.TheNetIsUnAble);
        }
    }
    /*支付宝支付1为会员下单2为保险下单*/
    public void paybyali(String totalMoney,String orderId,String matidshow,String type) {
        String orderInfo = "";
        String orderInfo_en = "";
        try {
            if(type.equals("1")){
                orderInfo = GetOrderInfo.getOrderInfo(orderId,
                        URLEncoder.encode(matidshow,
                                "UTF-8"), orderId, totalMoney,WebUtils.getRequestUrl(WebUtils.ZFB_TZ));
            }else if(type.equals("2")){
                orderInfo = GetOrderInfo.getOrderInfo(orderId,
                        URLEncoder.encode(matidshow,
                                "UTF-8"), orderId, totalMoney,WebUtils.getRequestUrl(WebUtils.ZFB_BX));
            }

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
        if (CommonUtils.isNetworkAvailable(mContext)) {
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
                                    postZfb(payInfo);
//                                    Runnable payRunnable = new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            // 构建PayTask对象
//                                            PayTask alipay = new PayTask(EnsureOrderActivity.this);
//                                            // 调用支付接口，获取支付结果
//                                            String result = alipay.pay(payInfo, true);
//                                            Message msg = new Message();
//                                            msg.what = SDK_PAY_FLAG;
//                                            msg.obj = result;
//                                            mHandler.sendMessage(msg);
//                                        }
//                                    };
//                                    // 必须异步调用
//                                    Thread payThread = new Thread(payRunnable);
//                                    payThread.start();
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

    /**
     * 提交支付宝订单弹出支付宝框框
     */
    protected void postZfb(String payInfo) {

    }

    /**
     * 检查是否已经登录
     */
    protected boolean checkLogin() {
        if (AyjSwApplication.getsInstance().getUserInfo() == null) {
            SweetAlertDialog confirmDialog = CommonUtils.getConfirmDialog(mContext, "您尚未登录", new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    //跳转登录
                    startActivity(new Intent(mContext, LoginActivity.class));
                    //隐藏dialog
                    sweetAlertDialog.dismiss();
                }
            });
            //显示dialog
            confirmDialog.show();
        } else {
            return true;
        }

        return false;
    }

    /**
     * 检查是否已经登录
     */
    protected boolean checkHavePwd() {
        if (!AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getHaspaypwd().equals("1")) {
            SweetAlertDialog confirmDialog = CommonUtils.getConfirmDialog(mContext, "您尚未设置支付密码,是否需要设置", new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    //跳转登录
                    startActivity(new Intent(mContext,SetZfPwdActivity.class));
                    //隐藏dialog
                    sweetAlertDialog.dismiss();
                }
            });
            //显示dialog
            confirmDialog.show();
        } else {
            return true;
        }

        return false;
    }

    public void Zlxl(final String code, final String value, final TextView view){
        if (CommonUtils.isNetworkAvailable(mContext)) {
            JSONObject obj = new JSONObject();
            try {
                obj.put("key","");
                obj.put("userid",AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getSnid());
                obj.put("pwd",AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getPassWord());
                obj.put(code, value);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mProgressHub.show();
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.XIU_GAI))
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
                                    if(code.equals("sex")){
                                        view.setText(value.equals("SEX001")?"男":"女");
                                    }else{
                                        view.setText(value);
                                    }
                                    doLogin(1);
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
    /*头像上传*/
    public void Zlxl(final String code, final String value,final int type){
        if (CommonUtils.isNetworkAvailable(mContext)) {
            JSONObject obj = new JSONObject();
            try {
                obj.put("key","");
                obj.put("userid",AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getSnid());
                obj.put("pwd",AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getPassWord());
                obj.put(code, value);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mProgressHub.show();
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.XIU_GAI))
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
                                    showToast(check.getMsg());
                                    doLogin(type);
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
    /*修改资料之后需要更新本地信息...1是直接修改本地，2是回掉需要手机号3实名认证4邮箱5住持*/
    public void doLogin(final int type) {
        if (CommonUtils.isNetworkAvailable(mContext)) {
            JSONObject obj = new JSONObject();
            try {
                obj.put("key","");
                obj.put("mobile",AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getMobile());
                obj.put("pwd", AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getPassWord());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mProgressHub.show();
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.GET_ZH_MA))
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
                            switch (check.getErr()){
                                case 0:
                                    UserBean userBean = new Gson().fromJson(response, UserBean.class);
                                    userBean.getData().get(0).setPassWord(AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getPassWord());
                                    AyjSwApplication.getsInstance().setUserInfo(userBean);
                                    aCache.put(ACache.USER_INFO_KEY,userBean);
                                    showToast("修改成功");
                                    if(type==2){
                                        EventBus.getDefault().post(
                                                new FirstEvent("SJHCLICK"));
                                        finish();
                                    }else if(3==type){
                                        EventBus.getDefault().post(
                                                new FirstEvent("SMCLICK"));
                                        finish();
                                    }else if(4==type){
                                        EventBus.getDefault().post(
                                                new FirstEvent("ZZCLICK"));
                                        finish();
                                    }
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
