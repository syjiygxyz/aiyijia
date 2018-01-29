package com.ayj.aiyijia.fragment.home;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ayj.aiyijia.AyjSwApplication;
import com.ayj.aiyijia.R;
import com.ayj.aiyijia.activity.shopping.AddAddressActivity;
import com.ayj.aiyijia.activity.shopping.ZFActivity;
import com.ayj.aiyijia.adapter.shopping.DialogAddressAdapter;
import com.ayj.aiyijia.adapter.shopping.ShopCartAdapter;
import com.ayj.aiyijia.bean.Check;
import com.ayj.aiyijia.bean.eventbus.FirstEvent;
import com.ayj.aiyijia.bean.shopping.Address;
import com.ayj.aiyijia.bean.shopping.ShopCart;
import com.ayj.aiyijia.fragment.base.LazyFragment;
import com.ayj.aiyijia.utils.CommonUtils;
import com.ayj.aiyijia.utils.WebUtils;
import com.ayj.aiyijia.view.SwipMenuListView.SwipeMenu;
import com.ayj.aiyijia.view.SwipMenuListView.SwipeMenuCreator;
import com.ayj.aiyijia.view.SwipMenuListView.SwipeMenuItem;
import com.ayj.aiyijia.view.SwipMenuListView.SwipeMenuListView;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/8/29.
 */
public class GwcFragment extends LazyFragment{

    @BindView(R.id.lv)
    SwipeMenuListView lv;
    @BindView(R.id.tv_shopcart_yuan)
    TextView tvShopcartYuan;
    @BindView(R.id.tv_shopcart_totalprice)
    TextView tv_shopcart_totalprice;
    @BindView(R.id.btn_shopcart_buy)
    Button btnShopcartBuy;

    private String ordertype;

    ShopCartAdapter commonGwListAdapter;

    int num;

    private List<Address.DataBean> addresses = new ArrayList<>();

    private List<ShopCart.DataBean> list = new ArrayList<>();

    private Double totalMoney = 0.00;

    private DialogAddressAdapter dialogAddressAdapter;

    private Bundle bd;

    @Override
    public void fetchData() {
        EventBus.getDefault().register(this);
        if (getArguments() != null) {
            bd = getArguments();
            ordertype = bd.getString("ordertype");
        }
        //
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity());
                deleteItem.setBackground(R.color.red);
                deleteItem.setWidth(CommonUtils.dip2px(getActivity(), 80));
                deleteItem.setTitle("删除");
                deleteItem.setTitleSize(18);
                deleteItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(deleteItem);
            }
        };
        lv.setMenuCreator(creator);
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
        addresses.clear();
        list.clear();
        totalMoney = 0.00;
        getGwcList();
        getAddress();
    }

    @Override
    public void initDatas() {

    }

    /*跳转参数*/
    public static GwcFragment newInstance(String ordertype) {
        GwcFragment fragment = new GwcFragment();
        Bundle args = new Bundle();
        args.putString("ordertype", ordertype);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void configViews() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_gwc;
    }

    /*获取购物车列表*/
    private void getGwcList() {
        if (CommonUtils.isNetworkAvailable(getActivity())) {
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
                                        commonGwListAdapter = new ShopCartAdapter(getActivity(),
                                                shopCart.getData());
                                        lv.setAdapter(commonGwListAdapter);
                                    } else {
                                        commonGwListAdapter.setList(shopCart.getData());
                                        commonGwListAdapter.notifyDataSetChanged();
                                    }
                                    for (int i = 0; i < shopCart.getData().size(); i++) {
                                        totalMoney +=  Double.parseDouble(new DecimalFormat("0.00").format(
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
        final AlertDialog dialog = new AlertDialog.Builder(getActivity(),
                R.style.DialogTheme).create();
        dialog.show();

        Window window = dialog.getWindow();
        window.setContentView(R.layout.dialog_zq_address);
        window.setGravity(Gravity.BOTTOM);  //此处可以设置dialog显示的位置
        window.setWindowAnimations(R.style.mystyle);  //添加动画
        WindowManager m = getActivity().getWindowManager();
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
                getActivity(), addresses);
        lv_dialog_address.setAdapter(dialogAddressAdapter);
        lv_dialog_address.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                /*跳转到支付界面*/
                setOrder(i);
                dialog.dismiss();
            }
        });

        btn_dialog_addaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddAddressActivity.class);
                startActivity(intent);
            }
        });
    }

    /*获取地址*/
    private void getAddress() {
        if (CommonUtils.isNetworkAvailable(getActivity())) {
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

    /**
     * 生成订单
     */
    private void setOrder(int position) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("type", "gwc");
        bundle.putSerializable("list", (Serializable) list);
        bundle.putSerializable("address", addresses.get(position));
        bundle.putString("money", tv_shopcart_totalprice.getText().toString());
        bundle.putString("order_type", ordertype);
        bundle.putString("num", num + "");
        bundle.putString("matid", list.get(0).getMatid());
        intent.putExtras(bundle);
        intent.setClass(getActivity(), ZFActivity.class);
        startActivity(intent);
    }

    /*删除购物车功能*/
    private void delGw(final int wz) {
        if (CommonUtils.isNetworkAvailable(getActivity())) {
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
                                    showToast("删除成功");
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
                                    showToast("删除失败");
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
        if (event.getMsg().equals("GWCREFRESH")) {
            addresses.clear();
            list.clear();
            totalMoney = 0.00;
            getGwcList();
            getAddress();
        }
    }
}
