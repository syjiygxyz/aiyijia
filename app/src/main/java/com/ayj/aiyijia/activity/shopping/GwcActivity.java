package com.ayj.aiyijia.activity.shopping;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.ayj.aiyijia.AyjSwApplication;
import com.ayj.aiyijia.R;
import com.ayj.aiyijia.activity.base.BaseActivity;
import com.ayj.aiyijia.adapter.shopping.DialogAddressAdapter;
import com.ayj.aiyijia.adapter.shopping.ShopCartAdapter;
import com.ayj.aiyijia.bean.Check;
import com.ayj.aiyijia.bean.shopping.Address;
import com.ayj.aiyijia.bean.shopping.ShopCart;
import com.ayj.aiyijia.utils.CommonUtils;
import com.ayj.aiyijia.utils.Constant;
import com.ayj.aiyijia.utils.WebUtils;
import com.ayj.aiyijia.view.CustomPopWindow;
import com.ayj.aiyijia.view.SwipMenuListView.SwipeMenu;
import com.ayj.aiyijia.view.SwipMenuListView.SwipeMenuCreator;
import com.ayj.aiyijia.view.SwipMenuListView.SwipeMenuItem;
import com.ayj.aiyijia.view.SwipMenuListView.SwipeMenuListView;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class GwcActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.lv)
    SwipeMenuListView lv;
    @BindView(R.id.tv_shopcart_yuan)
    TextView tvShopcartYuan;
    @BindView(R.id.tv_shopcart_totalprice)
    TextView tvShopcartTotalprice;
    @BindView(R.id.btn_shopcart_buy)
    Button btnShopcartBuy;
    @BindView(R.id.tv_right_head)
    TextView tvRightHead;
    @BindView(R.id.tv_member_name)
    TextView tvMemberName;
    @BindView(R.id.tv_member_mobile)
    TextView tvMemberMobile;
    @BindView(R.id.tv_member_address)
    TextView tvMemberAddress;
    @BindView(R.id.ll_member)
    LinearLayout llMember;
    @BindView(R.id.et_retrieve_num)
    EditText etRetrieveNum;

    private CustomPopWindow mCustomPopWindow;


    private String ordertype;

    private String type;

    ShopCartAdapter commonGwListAdapter;

    TextView tv_shopcart_totalprice;

    private Address.DataBean address;//收货地址

    int num;

    private List<Address.DataBean> addresses = new ArrayList<>();

    private List<ShopCart.DataBean> list = new ArrayList<>();

    private Double totalMoney = 0.00;

    private DialogAddressAdapter dialogAddressAdapter;


    @Override
    public int getLayoutId() {
        return R.layout.activity_gwc;
    }

    @Override
    public void initDatas() {
        ordertype = getIntent().getStringExtra("ordertype");
        if (ordertype.equals(Constant.MENBERORDERTYPE_LB)) {
            tvTitle.setText("礼包");
            type = "lc";
            llMember.setVisibility(View.VISIBLE);
            tvMemberName.setText("送礼人:" + AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getName());
            tvMemberMobile.setText(AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getMobile());
            tvMemberAddress.setText("地址:");
            etRetrieveNum.setText("1");
            etRetrieveNum.addTextChangedListener(textListener);
        } else {
            tvTitle.setText("购物车");
            type = "gwc";
        }
        if (getIntent().getStringExtra("addres").equals("main")) {
            /*右边可以选择*/
            tvRightHead.setVisibility(View.VISIBLE);
            tvRightHead.setText("统一商城");
        } else {
            /*右边不可以选择*/
            tvRightHead.setVisibility(View.GONE);
        }
        lv = (SwipeMenuListView) findViewById(R.id.lv);
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(mContext);
                deleteItem.setBackground(R.color.red);
                deleteItem.setWidth(CommonUtils.dip2px(mContext, 80));
                deleteItem.setTitle("删除");
                deleteItem.setTitleSize(18);
                deleteItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(deleteItem);
            }
        };
        lv.setMenuCreator(creator);
        btnShopcartBuy = (Button) findViewById(R.id.btn_shopcart_buy);
        tv_shopcart_totalprice = (TextView) findViewById(R.id.tv_shopcart_totalprice);
        /*兑换*/
        btnShopcartBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.size() == 0) {
                    showToast("还未有商品加入购物车");
                } else {
                    showAddressDialog();
                }
            }
        });
        lv.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        delGw(position);
                        break;

                }
                return false;
            }
        });
    }

    @Override
    public void configViews() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        addresses.clear();
        list.clear();
        totalMoney = 0.00;
        getGwcList();
        getAddress();
    }

    TextWatcher textListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!TextUtils.isEmpty(s)){
                tv_shopcart_totalprice.setText(totalMoney*Integer.valueOf(s.toString())+"");
            }
        }
    };

    /*获取购物车列表*/
    private void getGwcList() {
        if (CommonUtils.isNetworkAvailable(mContext)) {
            mProgressHub.show();
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.MENBER_GET_GWC))
                    .addParams("json",
                            "{\"key\":\"\",\"msnid\":\"" + AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getSnid()
                                    + "\",\"pwd\":\"" + AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getPassWord()
                                    + "\",\"carttype\":\"" + ordertype + "\"}")
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
                                    ShopCart shopCart = new Gson().fromJson(response,
                                            ShopCart.class);
                                    list = shopCart.getData();
                                    if (commonGwListAdapter == null) {
                                        commonGwListAdapter = new ShopCartAdapter(mContext,
                                                shopCart.getData());
                                        lv.setAdapter(commonGwListAdapter);
                                    } else {
                                        commonGwListAdapter.setList(shopCart.getData());
                                        commonGwListAdapter.notifyDataSetChanged();
                                    }
                                    for (int i = 0; i < shopCart.getData().size(); i++) {
                                        totalMoney += Double.parseDouble(new DecimalFormat("0.00").format(
                                                Double.parseDouble(shopCart.getData().get(i).getPrice_now())
                                                        * Integer.parseInt(shopCart.getData().get(i).getNum())).toString());
                                        num = num + Integer.parseInt(
                                                shopCart.getData().get(i).getNum());
                                    }
                                    tv_shopcart_totalprice.setText(new DecimalFormat("0.00").format(totalMoney));
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
     * 弹出选择收货地址dialog
     */
    private void showAddressDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(mContext,
                R.style.DialogTheme).create();
        dialog.show();

        Window window = dialog.getWindow();
        window.setContentView(R.layout.dialog_zq_address);
        window.setGravity(Gravity.BOTTOM);  //此处可以设置dialog显示的位置
        window.setWindowAnimations(R.style.mystyle);  //添加动画
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.7);
        window.setAttributes(p);

        final ImageView iv_back = (ImageView) dialog.findViewById(R.id.iv_zq_address_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        final ListView lv_dialog_address = (ListView) dialog.findViewById(R.id.lv_dialog_address);
        final Button btn_dialog_addaddress = (Button) dialog.findViewById(
                R.id.btn_dialog_addaddress);
        dialogAddressAdapter = new DialogAddressAdapter(
                mContext, addresses);
        lv_dialog_address.setAdapter(dialogAddressAdapter);
        lv_dialog_address.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                /*跳转到支付界面*/
                address = addresses.get(i);
                tvMemberAddress.setText("地址:"+
                        addresses.get(i).getProvinceidshow() + "\t" + addresses.get(
                                i).getCityidshow() + "\t" + addresses.get(i).getAreaidshow() + "\t"
                                + addresses.get(i).getDetailaddr());
                setOrder(i);
                dialog.dismiss();
            }
        });

        btn_dialog_addaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, AddAddressActivity.class);
                startActivity(intent);
            }
        });
    }

    /*获取地址*/
    private void getAddress() {
        if (CommonUtils.isNetworkAvailable(mContext)) {
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.SHOP_DETAIL_ADDRESS))
                    .addParams("json",
                            "{\"key\":\"\",\"userid\":\"" + AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getSnid()
                                    + "\",\"pwd\":\""
                                    + AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getPassWord() + "\"}")
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
                                    Address add = new Gson().fromJson(response, Address.class);
                                    for (int i = 0; i < add.getData().size(); i++) {
                                        addresses.add(add.getData().get(i));
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

    /**
     * 生成订单
     */
    private void setOrder(int position) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        bundle.putSerializable("list", (Serializable) list);
        bundle.putSerializable("address", addresses.get(position));
        bundle.putString("money", tv_shopcart_totalprice.getText().toString());
        bundle.putString("order_type", ordertype);
        bundle.putString("num", num + "");
        bundle.putString("matid", list.get(0).getMatid());
        bundle.putString("detailcopynum",etRetrieveNum.getText().toString().trim());
        intent.putExtras(bundle);
        intent.setClass(mContext, ZFActivity.class);
        startActivity(intent);
    }

    /*删除购物车功能*/
    private void delGw(final int wz) {
        if (CommonUtils.isNetworkAvailable(mContext)) {
            mProgressHub.show();
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.MENBER_DEL_GWC))
                    .addParams("json",
                            "{\"key\":\"\",\"userid\":\"" + AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getSnid()
                                    + "\",\"pwd\":\"" + AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getPassWord()
                                    + "\",\"snid\":\"" + list.get(
                                    wz).getSnid() + "\"}")
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
                                    Toast.makeText(mContext, "删除成功",
                                            Toast.LENGTH_SHORT).show();
                                    totalMoney = 0.00;
                                    list.remove(wz);
                                    for (int i = 0; i < list.size(); i++) {
                                        if (!TextUtils.isEmpty(
                                                list.get(i).getPrice_now())) {
                                            totalMoney += Double.parseDouble(
                                                    list.get(i).getPrice_now())
                                                    * Integer.parseInt(
                                                    list.get(i).getNum());
                                        }
                                    }
                                    tv_shopcart_totalprice.setText(
                                            Double.parseDouble(new DecimalFormat(
                                                    "0.00").format(totalMoney).toString())
                                                    + "");
                                    commonGwListAdapter.setList(list);
                                    commonGwListAdapter.notifyDataSetChanged();
                                    break;
                                default:
                                    Toast.makeText(mContext, "删除失败",
                                            Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                    });
        } else {
            showToast(R.string.TheNetIsUnAble);
        }
    }

    @OnClick({R.id.iv_back, R.id.tv_right_head})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_right_head:
                /*弹出对话框*/
                /**
                 * 显示PopupWindow 同时背景变暗
                 */
                View contentView = LayoutInflater.from(this).inflate(R.layout.pop_menu, null);
                //处理popWindow 显示内容
                handleLogic(contentView);
                //创建并显示popWindow
                mCustomPopWindow = new CustomPopWindow.PopupWindowBuilder(this)
                        .setView(contentView)
                        .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                        .setBgDarkAlpha(0.7f) // 控制亮度
                        .setAnimationStyle(R.style.CustomPopWindowStyle)
                        .setOnDissmissListener(new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                            }
                        })
                        .create()
                        .showAsDropDown(tvRightHead, 0, 10);
                break;
        }
    }

    /**
     * 处理弹出显示内容、点击事件等逻辑
     *
     * @param contentView
     */
    private void handleLogic(View contentView) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCustomPopWindow != null) {
                    mCustomPopWindow.dissmiss();
                }
                addresses.clear();
                list.clear();
                totalMoney = 0.00;
                getAddress();
                switch (v.getId()) {
                    case R.id.tv_ty:
                        tvRightHead.setText("统一商城");
                        ordertype = "MEMBERORDERTYPE019";
                        getGwcList();
                        break;
                    case R.id.tv_lj:
                        tvRightHead.setText("邻家小店");
                        ordertype = "MEMBERORDERTYPE005";
                        getGwcList();
                        break;
                    case R.id.tv_rg:
                        tvRightHead.setText("春苗热柜");
                        ordertype = "MEMBERORDERTYPE033";
                        getGwcList();
                        break;
                    case R.id.tv_zk:
                        tvRightHead.setText("折扣商铺");
                        ordertype = "MEMBERORDERTYPE001";
                        getGwcList();
                        break;
                }

            }
        };
        contentView.findViewById(R.id.tv_ty).setOnClickListener(listener);
        contentView.findViewById(R.id.tv_lj).setOnClickListener(listener);
        contentView.findViewById(R.id.tv_rg).setOnClickListener(listener);
        contentView.findViewById(R.id.tv_zk).setOnClickListener(listener);
    }
}
