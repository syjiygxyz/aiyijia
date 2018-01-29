package com.ayj.aiyijia.activity.yd;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ayj.aiyijia.AyjSwApplication;
import com.ayj.aiyijia.R;
import com.ayj.aiyijia.activity.base.BaseActivity;
import com.ayj.aiyijia.adapter.yy.ServiceOrderAdapter;
import com.ayj.aiyijia.adapter.yy.YyPostHeadAdapter;
import com.ayj.aiyijia.bean.Check;
import com.ayj.aiyijia.bean.MoneyLeft;
import com.ayj.aiyijia.bean.UtilityItem;
import com.ayj.aiyijia.bean.cmbz.UserPerson;
import com.ayj.aiyijia.utils.ACache;
import com.ayj.aiyijia.utils.AliPay.PayResult;
import com.ayj.aiyijia.utils.CommonUtils;
import com.ayj.aiyijia.utils.WebUtils;
import com.ayj.aiyijia.view.MyListView;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class YyPostActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_fwd)
    TextView tv_fwd;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.rlv)
    RecyclerView rlv;
    @BindView(R.id.cb_one)
    CheckBox cbOne;
    @BindView(R.id.rl_dd)
    RelativeLayout rlDd;
    @BindView(R.id.cb_two)
    CheckBox cbTwo;
    @BindView(R.id.rl_dd_two)
    RelativeLayout rlDdTwo;
    String[] details;
    private String matid;
    private String matidshow;
    private String per_money;
    private int[] num;
    private int[] nums;
    private int discount;
    private Integer[] nums_left;
    private Integer[] nums_pay;
    private Double[] fees;

    @BindView(R.id.tv_service_sfmoney)
    TextView tv_service_sfmoney;

    @BindView(R.id.tv_service_smallmoney_left)
    TextView tv_service_smallmoney_left;

    @BindView(R.id.cb_service_order)
    CheckBox cb_service_order;
    private boolean[] checked;
    UserPerson mUserPerson;
    private String smallmoneyuse = "";
    private Double allmoney_pay = 0.00;
    private Double smallmoney_pay = 0.00;
    private Double othermoney_pay = 0.00;
    private Double smallmoney_left = 0.00;

    private static final int SDK_PAY_FLAG = 2;

    private List<UtilityItem> listHead = new ArrayList<>();

    private List<MoneyLeft.DataBean.TicketListBean> mTicketListBeans;

    private String memberticketdetail = "";

    @BindView(R.id.mlv_service_order)
    MyListView mlv_service_order;

    String[] memberticketdetails;

    private ServiceOrderAdapter mServiceOrderAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_yy_post;
    }

    @Override
    public void initDatas() {
        rlv.setLayoutManager(new GridLayoutManager(mContext, 3));
        tvTitle.setText("支付");
        tv_fwd.setText(AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getRegshopidshow());
        matid = getIntent().getStringExtra("matid");
        matidshow = getIntent().getStringExtra("matidshow");
        per_money = getIntent().getStringExtra("money");
        num = getIntent().getIntArrayExtra("nums");

    }

    @Override
    public void configViews() {

    }




    @OnClick(R.id.iv_back)
    public void onClick() {
        finish();
    }

    @OnClick({R.id.rl_dd, R.id.rl_dd_two,R.id.iv_back,R.id.cb_one,R.id.cb_two})
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
}
