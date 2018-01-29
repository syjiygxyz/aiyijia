package com.ayj.aiyijia.activity.home.bx.clxx;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayj.aiyijia.AyjSwApplication;
import com.ayj.aiyijia.R;
import com.ayj.aiyijia.activity.base.BaseActivity;
import com.ayj.aiyijia.adapter.bx.VehicleInfoAdapter;
import com.ayj.aiyijia.bean.Check;
import com.ayj.aiyijia.bean.cmbz.UserPerson;
import com.ayj.aiyijia.bean.cmbz.VehicleBean;
import com.ayj.aiyijia.bean.eventbus.ClEvent;
import com.ayj.aiyijia.bean.eventbus.FirstEvent;
import com.ayj.aiyijia.utils.CommonUtils;
import com.ayj.aiyijia.utils.WebUtils;
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
import butterknife.OnClick;
import cn.finalteam.loadingviewfinal.OnDefaultRefreshListener;
import cn.finalteam.loadingviewfinal.PtrClassicFrameLayout;
import cn.finalteam.loadingviewfinal.PtrFrameLayout;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/7/24.
 * 车辆信息表0可以选择1是车险跳转的
 */
public class VehicleInfoActivity extends BaseActivity {
    List<VehicleBean.DataBean> vehicleInfoList = new ArrayList<>();
    VehicleInfoAdapter vehicleInfoAdapter;

    @BindView(R.id.vehicle_info)
    RecyclerView vehicleRlv;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.bxempty)
    ImageView bxempty;
    @BindView(R.id.pcf_refresh)
    PtrClassicFrameLayout pcfRefresh;
    @BindView(R.id.tv_right_head_left)
    TextView tv_right_head_left;
    String type;

    int count;
    @Override
    public int getLayoutId() {
        return R.layout.activity_vehicle_info;
    }

    @Override
    public void initDatas() {
        EventBus.getDefault().register(this);
        tvTitle.setText("车辆信息");
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(R.mipmap.addaaa);
        vehicleRlv.setHasFixedSize(true);
        vehicleRlv.setLayoutManager(new LinearLayoutManager(mContext));
        type = getIntent().getStringExtra("type");
        if("0".equals(type)){
            tv_right_head_left.setVisibility(View.VISIBLE);
            tv_right_head_left.setText("确定");
            count = getIntent().getIntExtra("count",0);
        }
    }

    private void getData() {
        if (CommonUtils.isNetworkAvailable(mContext)) {
            JSONObject obj = new JSONObject();
            try {
                obj.put("key", "");
                obj.put("msnid", AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getSnid());
                obj.put("pwd", AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getPassWord());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mProgressHub.show();
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.GET_VEHICLE_INFO_URL))
                    .addParams("json", String.valueOf(obj))
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            pcfRefresh.onRefreshComplete();
                            mProgressHub.dismiss();
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            pcfRefresh.onRefreshComplete();
                            mProgressHub.dismiss();
                            Check check = new Gson().fromJson(response, Check.class);
                            switch (check.getErr()) {
                                case 0:
                                    VehicleBean vehicleBean = new Gson().fromJson(response, VehicleBean.class);
                                    for (int i = 0; i <vehicleBean.getData().size() ; i++) {
                                        vehicleBean.getData().get(i).setIsnoCheck(false);
                                        vehicleInfoList.add(vehicleBean.getData().get(i));
                                    }
                                    if (vehicleInfoList.size() == 0) {
                                        bxempty.setVisibility(View.VISIBLE);
                                    } else {
                                        bxempty.setVisibility(View.GONE);
                                    }
                                    if (vehicleInfoAdapter == null) {
                                        vehicleInfoAdapter = new VehicleInfoAdapter(type,vehicleInfoList, new xgOnClickListener(), new scClickListener(),new checkOnClickListener());
                                        vehicleRlv.setAdapter(vehicleInfoAdapter);
                                    } else {
                                        vehicleInfoAdapter.setNewData(vehicleInfoList);
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

    @Override
    public void configViews() {
        getData();
        pcfRefresh.setOnRefreshListener(new OnDefaultRefreshListener() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                vehicleInfoList.clear();
                getData();
            }
        });
    }


    @OnClick({R.id.iv_back, R.id.iv_right, R.id.tv_right_head_left})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_right_head_left:
                List<VehicleBean.DataBean> selectList = new ArrayList<>();
                for (int i = 0; i <vehicleInfoList.size() ; i++) {
                    if(vehicleInfoList.get(i).isnoCheck()){
                        selectList.add(vehicleInfoList.get(i));
                    }
                }
                EventBus.getDefault().post(
                        new ClEvent(selectList,"czyZf"));
                finish();
                break;
            case R.id.iv_right:
                Intent mIntent = new Intent(mContext,CommonClFormActivity.class);
                mIntent.putExtra("type","0");
                mIntent.putExtra("info","");
                startActivity(mIntent);
                break;
        }
    }

    /**
     * 修改监听
     */
    private class xgOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v.getTag() != null) {
                VehicleBean.DataBean item = (VehicleBean.DataBean) v.getTag();
                Intent mIntent = new Intent(mContext,CommonClFormActivity.class);
                mIntent.putExtra("type","1");
                mIntent.putExtra("info",item);
                startActivity(mIntent);
            }
        }
    }

    /**
     * Checkbox监听
     */
    private class checkOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(final View v) {
            if (v.getTag() != null) {
                CheckBox cb = (CheckBox) v;
                List<VehicleBean.DataBean> selectList = new ArrayList<>();
                for (int i = 0; i <vehicleInfoList.size() ; i++) {
                    if(vehicleInfoList.get(i).isnoCheck()){
                        selectList.add(vehicleInfoList.get(i));
                    }
                }
                if(count>selectList.size()){
                    if(cb.isChecked()){
                        vehicleInfoList.get(Integer.parseInt(v.getTag()+"")).setIsnoCheck(true);
                    }else{
                        vehicleInfoList.get(Integer.parseInt(v.getTag()+"")).setIsnoCheck(false);
                    }
                    vehicleInfoAdapter.setNewData(vehicleInfoList);
                    vehicleInfoAdapter.notifyDataSetChanged();
                }else{
                    showToast("无法选择更多数量的车辆");
                    cb.setChecked(false);
                }
            }

        }
    }

    /**
     * 删除监听
     */
    private class scClickListener implements View.OnClickListener {

        @Override
        public void onClick(final View v) {
            if (v.getTag() != null) {
                SweetAlertDialog confirmDialog = CommonUtils.getConfirmDialog(mContext, "提示",
                        "是否删除?",
                        new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                del(v.getTag().toString());
                                sweetAlertDialog.dismiss();
                            }
                        });
                //显示dialog
                confirmDialog.show();
            }
        }
    }
    @Subscribe
    public void onEventMainThread(FirstEvent event) {
        if("commonClform".equals(event.getMsg())){
            vehicleInfoList.clear();
            getData();
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void del(String snid){
        if(CommonUtils.isNetworkAvailable(mContext)){
            mProgressHub.show();
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.DETALE_CL))
                    .addParams("json","{\"key\":\"\",\"msnid\":\""+AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getSnid()+"\","
                            + "\"pwd\":\""+AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getPassWord()+"\",\"snid\":\""+snid+"\"}")
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
                                    showToast("删除成功");
                                    vehicleInfoList.clear();
                                    getData();
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
