package com.ayj.aiyijia.activity.personcenter;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayj.aiyijia.AyjSwApplication;
import com.ayj.aiyijia.R;
import com.ayj.aiyijia.activity.base.BaseActivity;
import com.ayj.aiyijia.bean.Check;
import com.ayj.aiyijia.bean.FxBean;
import com.ayj.aiyijia.bean.PersonCenterMenberBean;
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

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/*
* 礼金
* */
public class LjActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_count)
    EditText tvCount;
    @BindView(R.id.tv_money)
    EditText tvMoney;
    @BindView(R.id.et_yy)
    EditText etYy;
    @BindView(R.id.tv_sq)
    TextView tv_sq;

    private OperatePopupWindow pop;//弹出分享布局

    @Override
    public int getLayoutId() {
        return R.layout.activity_lj;
    }

    @Override
    public void initDatas() {
        tvTitle.setText("礼金");
    }

    @Override
    public void configViews() {
    }

    @OnClick({R.id.iv_back, R.id.tv_sq})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_sq:
                dialogShow();
                break;
        }
    }

    private void postLj(String pwd){
        if(TextUtils.isEmpty(tvCount.getText().toString())){
            showToast("打赏数量不能为空");
            return;
        }
        if(TextUtils.isEmpty(tvMoney.getText().toString())){
            showToast("打赏金额不能为空");
            return;
        }
        if(TextUtils.isEmpty(etYy.getText().toString())){
            showToast("留言不能为空");
            return;
        }
        if (CommonUtils.isNetworkAvailable(mContext)) {
            mProgressHub.show();
            JSONObject obj = new JSONObject();
            try {
                obj.put("key","");
                obj.put("msnid",AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getSnid());
                obj.put("pwd",AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getPassWord());
                obj.put("paypwd", MD5Utils.getMD5String(pwd));
                obj.put("awardtype","MEMBERAWARDTYPE001");
                obj.put("totalnum",tvCount.getText().toString().trim());
                obj.put("singlemoney",tvMoney.getText().toString().trim());
                obj.put("formessage",etYy.getText().toString().trim());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.LJ_SON))
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
                                    showToast(check.getMsg());
                                    FxBean fxBean = new Gson().fromJson(response, FxBean.class);
                                    pop = new OperatePopupWindow(mContext, LjActivity.this, "领取成功后下载app即可使用","礼金赠送",fxBean.getData().getHtml5url(),WebUtils.FX_URL_IMG);
                                    pop.showAtLocation(tv_sq, Gravity.BOTTOM
                                            | Gravity.CENTER_HORIZONTAL, 0, 0);
                                    pop.update();
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
    private void dialogShow() {
        if(checkHavePwd()){
            if(TextUtils.isEmpty(tvCount.getText().toString())){
                showToast("打赏数量不能为空");
                return;
            }
            if(TextUtils.isEmpty(tvMoney.getText().toString())){
                showToast("打赏金额不能为空");
                return;
            }
            if(TextUtils.isEmpty(etYy.getText().toString())){
                showToast("留言不能为空");
                return;
            }
            new PassWordDialog(this).setTitle("请输入交易密码").setCompleteListener(
                    new DialogCompleteListener() {
                        @Override
                        public void dialogCompleteListener(String money, String pwd) {
                            postLj(pwd);
                        }
                    }).show();
        }
        }
}
