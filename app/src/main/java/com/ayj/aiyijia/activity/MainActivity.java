package com.ayj.aiyijia.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ayj.aiyijia.AyjSwApplication;
import com.ayj.aiyijia.R;
import com.ayj.aiyijia.activity.base.BaseActivity;
import com.ayj.aiyijia.activity.base.NewsActivity;
import com.ayj.aiyijia.activity.home.HomeActivity;
import com.ayj.aiyijia.activity.home.splist.SplistActivity;
import com.ayj.aiyijia.activity.login.LoginActivity;
import com.ayj.aiyijia.activity.map.BindShopActivity;
import com.ayj.aiyijia.activity.personcenter.PersonCenterActivity;
import com.ayj.aiyijia.activity.shopping.GwcActivity;
import com.ayj.aiyijia.activity.shopping.ShoppingDetailActivity;
import com.ayj.aiyijia.adapter.base.BaseQuickAdapter;
import com.ayj.aiyijia.adapter.base.listener.OnItemClickListener;
import com.ayj.aiyijia.adapter.main.CommonGridAdapter;
import com.ayj.aiyijia.bean.Check;
import com.ayj.aiyijia.bean.MainBanner;
import com.ayj.aiyijia.bean.UtilityItem;
import com.ayj.aiyijia.utils.CommonUtils;
import com.ayj.aiyijia.utils.UpDateUtils;
import com.ayj.aiyijia.utils.WebUtils;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerClickListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/*
* 客户端首页
* */
public class MainActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right_head)
    TextView tvRightHead;
    @BindView(R.id.banner_top)
    Banner banner_main;
    @BindView(R.id.clv)
    RecyclerView clv;
    @BindView(R.id.iv_right)
    ImageView ivRight;

    private List<String> networkImages = new ArrayList<>();

    CommonGridAdapter mCommonGridAdapter;

    List<UtilityItem> gridLists = new ArrayList<>();

    MainBanner mainBanner;

    String type1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initDatas() {
        tvTitle.setText("爱医家");
        CommonUtils.setBannerAttribute(mContext, banner_main);
        clv.setLayoutManager(new GridLayoutManager(mContext, 3));
        ivBack.setImageResource(R.mipmap.main_top_right);
        UpDateUtils upDateUtils = new UpDateUtils(mContext, false);
        upDateUtils.update();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AyjSwApplication.getsInstance().getUserInfo() == null) {
            tvRightHead.setVisibility(View.VISIBLE);
            tvRightHead.setText("登录");
            ivRight.setVisibility(View.GONE);
        } else {
            tvRightHead.setVisibility(View.GONE);
            ivRight.setVisibility(View.VISIBLE);
            ivRight.setImageResource(R.mipmap.main_head);
        }
    }

    @Override
    public void configViews() {
        getBanner();
        gridLists = UtilityItem.getMainGridList();
        mCommonGridAdapter = new CommonGridAdapter(R.layout.common_grid_item, gridLists);
        clv.setAdapter(mCommonGridAdapter);
        /*九个栏目的点击*/
        clv.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                switch (gridLists.get(position).getType()) {
                    case 1:
                        /*社区医养*/
                        if (checkLogin()) {
                            if (null == AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getRegshopid() || AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getRegshopid().equals("")) {
                                startActivity(new Intent(mContext, BindShopActivity.class));
                            } else {
                                startActivity(new Intent(mContext, HomeActivity.class));
                            }
                        }
                        break;
                    case 2:
                        /*调理指南*/
                        NewsActivity.jumpActivity(mContext, WebUtils.TL_ZM, "调理指南", "", "");
                        break;
                    case 3:
                        /*礼仓*/
                        if (checkLogin()) {
                            if (null == AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getRegshopid() || AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getRegshopid().equals("")) {
                                startActivity(new Intent(mContext, BindShopActivity.class));
                            } else {
                                SplistActivity.startActivity(mContext,"have_hot","MEMBERORDERTYPE022","","MEMBERORDERTYPE022");
                            }
                        }
                        break;
                    case 4:
                        /*人体穴位*/
                        NewsActivity.jumpActivity(mContext, WebUtils.DH_SM, "人体穴位", "", "");
                        break;
                    case 5:
                        /*购物车*/
                        if(checkLogin()) {
                            if (null == AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getRegshopid() || AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getRegshopid().equals("")) {
                                startActivity(new Intent(mContext, BindShopActivity.class));
                            } else {
                                Intent intentt = new Intent();
                                intentt.putExtra("ordertype", "MEMBERORDERTYPE019");
                                intentt.putExtra("addres", "main");
                                intentt.setClass(mContext, GwcActivity.class);
                                startActivity(intentt);
                            }
                        }
                        break;
                    case 6:
                        /*个人中心*/
                        if (checkLogin()) {
                            if (null == AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getRegshopid() || AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getRegshopid().equals("")) {
                                startActivity(new Intent(mContext, BindShopActivity.class));
                            } else {
                                startActivity(new Intent(mContext, PersonCenterActivity.class));
                            }
                        }
                        break;
                }
            }
        });
        banner_main.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
                MainBanner.DataBean info = mainBanner.getData().get(position - 1);
                /*MAINADREDIRECTTYPE MAINADREDIRECTTYPE001跳转到门店 MAINADREDIRECTTYPE002跳转到商城商品*/
                if(info.getRedirecttype().equals("MAINADREDIRECTTYPE001")){
                    if (checkLogin()) {
                        if (null == AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getRegshopid() || AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getRegshopid().equals("")) {
                            startActivity(new Intent(mContext, BindShopActivity.class));
                        } else {
                            startActivity(new Intent(mContext, HomeActivity.class));
                        }
                    }
                }else if(info.getRedirecttype().equals("MAINADREDIRECTTYPE002")){
                    if (checkLogin()) {
                        if (null == AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getRegshopid() || AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getRegshopid().equals("")) {
                            startActivity(new Intent(mContext, BindShopActivity.class));
                        } else {
                            if(info.getOrdertype().equals("MEMBERORDERTYPE033")){
                                type1 = "cmrg";
                            }else if(info.getOrdertype().equals("MEMBERORDERTYPE001")){
                                type1 = "zkp";
                            }else if(info.getOrdertype().equals("MEMBERORDERTYPE005")){
                                type1 = "ljxd";
                            }else{
                                type1 = "";
                            }
                            Intent intent = new Intent();
                            intent.putExtra("type",type1);
                            intent.putExtra("ordertype",info.getOrdertype());
                            intent.putExtra("matid",info.getMatid()+"");
                            intent.setClass(mContext, ShoppingDetailActivity.class);
                            startActivity(intent);
                        }
                    }
                }
            }
        });
    }

    private void getBanner() {
        if (CommonUtils.isNetworkAvailable(mContext)) {
            JSONObject obj = new JSONObject();
            try {
                obj.put("key", "");
                obj.put("adtype", "MAINADTYPE001");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mProgressHub.show();
            OkHttpUtils.post().
                    url(WebUtils.getRequestUrl(WebUtils.MAIN_BANNER))
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
                                    mainBanner = new Gson().fromJson(response, MainBanner.class);
                                    for (int i = 0; i < mainBanner.getData().size(); i++) {
                                        networkImages.add(mainBanner.getData().get(i).getImgnameshow());
                                    }
                                    banner_main.setImages(networkImages);
                                    //banner设置方法全部调用完毕时最后调用
                                    banner_main.start();
                                    break;
                                default:
                                    break;
                            }
                        }
                    });
        } else {
            showToast(R.string.TheNetIsUnAble);
        }
    }

    @OnClick({R.id.iv_back, R.id.tv_right_head, R.id.iv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                if (checkLogin()) {
                    cameraTask();
                }
                break;
            case R.id.tv_right_head:
                /*登陆界面*/
                startActivity(new Intent(mContext, LoginActivity.class));
                break;
            case R.id.iv_right:
                 /*个人中心*/
                if (checkLogin()) {
                    startActivity(new Intent(mContext, PersonCenterActivity.class));
                }
                break;
        }
    }

    /**
     * 请求CAMERA权限码
     */
    public static final int REQUEST_CAMERA_PERM = 101;


    /**
     * EsayPermissions接管权限处理逻辑
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @AfterPermissionGranted(REQUEST_CAMERA_PERM)
    public void cameraTask() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
            // Have permission, do the thing!
            startActivity(new Intent(mContext, SmActivity.class));
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(this, "需要请求camera权限",
                    REQUEST_CAMERA_PERM, Manifest.permission.CAMERA);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this, "当前App需要申请camera权限,需要打开设置页面么?")
                    .setTitle("权限申请")
                    .setPositiveButton("确认")
                    .setNegativeButton("取消", null /* click listener */)
                    .setRequestCode(REQUEST_CAMERA_PERM)
                    .build()
                    .show();
        }
    }


}
