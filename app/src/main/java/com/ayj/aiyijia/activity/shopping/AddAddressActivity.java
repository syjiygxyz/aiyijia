package com.ayj.aiyijia.activity.shopping;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ayj.aiyijia.AyjSwApplication;
import com.ayj.aiyijia.R;
import com.ayj.aiyijia.activity.base.BaseActivity;
import com.ayj.aiyijia.bean.AreaInfo;
import com.ayj.aiyijia.bean.Check;
import com.ayj.aiyijia.utils.CommonUtils;
import com.ayj.aiyijia.utils.ValidationUtils;
import com.ayj.aiyijia.utils.WebUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class AddAddressActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_name)
    EditText etName;
//    @BindView(R.id.tv_dq)
//    TextView tvDq;
    @BindView(R.id.et_xq)
    EditText etXq;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_yx)
    EditText etYx;
    @BindView(R.id.tv_post)
    TextView tvPost;
    @BindView(R.id.spin_add_province)
    Spinner sp_pro;
    @BindView(R.id.spin_add_city)
    Spinner sp_city;
    @BindView(R.id.spin_add_area)
    Spinner sp_area;
    private String province_id;//当前选中的省的id
    private String city_id;//当前选中的市的id
    private String area_id;//当前选中的区的id


    @Override
    public int getLayoutId() {
        return R.layout.activity_add_address;
    }

    @Override
    public void initDatas() {
        tvTitle.setText("填写收货地址");
    }

    @Override
    public void configViews() {
        initSpPro();
    }

    @OnClick({R.id.iv_back, R.id.tv_post})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
//            case R.id.tv_dq:
//             //   CommonUtils.getDqPickerView(mContext, tvDq);
//                break;
            case R.id.tv_post:
                doClick();
                break;
        }
    }

    private void doClick() {
        if (TextUtils.isEmpty(etName.getText().toString())) {
            showToast("请填写姓名");
            return;
        }
//        if (TextUtils.isEmpty(tvDq.getText().toString())) {
//            showToast("请选择地区");
//            return;
//        }
        if (TextUtils.isEmpty(etXq.getText().toString())) {
            showToast("请填写你的详细地址");
            return;
        }
        if (TextUtils.isEmpty(etPhone.getText().toString())) {
            showToast("请填写你的手机号");
            return;
        }
        if (TextUtils.isEmpty(etPhone.getText().toString())) {
            showToast("请填写你的邮编");
            return;
        }
        if (!ValidationUtils.checkTelPhone(etPhone.getText().toString())) {
            showToast("输入的手机号有误");
            return;
        }
        doPost();
    }

    private void doPost() {
        if (CommonUtils.isNetworkAvailable(AddAddressActivity.this)) {
            mProgressHub.show();
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.ADD_NEW_ADDRESS))
                    .addParams("json","{\"key\":\"\",\"userid\":\"" + AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getSnid()
                            + "\",\"pwd\":\"" + AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getPassWord()
                            + "\",\"provinceid\":\"" + province_id
                            + "\",\"cityid\":\"" + city_id
                            + "\",\"areaid\":\"" + area_id
                            +"\",\"receivename\":\"" + etName.getText().toString().trim()
                            + "\",\"detailaddr\":\"" + etXq.getText().toString().trim()
                            + "\",\"postcode\":\"" + etYx.getText().toString().trim()
                            + "\",\"mobile\":\"" + etPhone.getText().toString().trim()
                            + "\",\"tel\":\"" + "" + "\",\"isdefaultaddr\":\"" + "SFPD001" + "\"}")
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
                                    Toast.makeText(AddAddressActivity.this, "添加成功",
                                            Toast.LENGTH_SHORT).show();
                                    finish();
                                    break;
                                default:
                                    Toast.makeText(AddAddressActivity.this, "添加失败",
                                            Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                    });
        } else {
            showToast(R.string.TheNetIsUnAble);
        }
    }

    private void initSpPro() {
        OkHttpUtils.post()
                .url(WebUtils.getRequestUrl(WebUtils.getRequestUrl(WebUtils.CX_URL)))
                .addParams("json", "{\"key\":\"\",\"pid\":\"0001\"}")
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
                                final AreaInfo areainfo_pro = new Gson().fromJson(response,
                                        AreaInfo.class);
                                String[] strs = new String[areainfo_pro.getData().size()];
                                for (int i = 0; i < areainfo_pro.getData().size(); i++) {
                                    strs[i] = areainfo_pro.getData().get(i).getName();
                                }

                                ArrayAdapter adapter = new ArrayAdapter<String>(
                                        mContext,
                                        android.R.layout.simple_spinner_item, strs);
                                adapter.setDropDownViewResource(android.
                                        R.layout.simple_spinner_dropdown_item);
                                sp_pro.setAdapter(adapter);
                                sp_pro.setOnItemSelectedListener(
                                        new AdapterView.OnItemSelectedListener() {
                                            @Override
                                            public void onItemSelected(AdapterView<?> adapterView,
                                                                       View view, int i, long l) {
                                                province_id = areainfo_pro.getData().get(
                                                        i).getAid();
                                                initSpCity(areainfo_pro.getData().get(i).getAid());
//                                            initSpArea(areainfo_pro.getData().get(i).getAid());
                                            }

                                            @Override
                                            public void onNothingSelected(
                                                    AdapterView<?> adapterView) {

                                            }
                                        });
                                break;
                            default:
                                showToast(check.getMsg());
                                break;
                        }
                    }
                });

    }

    public void initSpCity(String pcode) {

        if (CommonUtils.isNetworkAvailable(mContext)) {
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.getRequestUrl(WebUtils.CX_URL)))
                    .addParams("json", "{\"key\":\"\",\"pid\":\"" + pcode + "\"}")
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
                                    final AreaInfo areainfo_city = new Gson().fromJson(response,
                                            AreaInfo.class);
                                    String[] strs = new String[areainfo_city.getData().size()];
                                    for (int i = 0; i < areainfo_city.getData().size(); i++) {
                                        strs[i] = areainfo_city.getData().get(i).getName();
                                    }
                                    ArrayAdapter adapter = new ArrayAdapter<String>(
                                            mContext,
                                            android.R.layout.simple_spinner_item, strs);
                                    adapter.setDropDownViewResource(android.
                                            R.layout.simple_spinner_dropdown_item);
                                    sp_city.setAdapter(adapter);

                                    sp_city.setOnItemSelectedListener(
                                            new AdapterView.OnItemSelectedListener() {
                                                @Override
                                                public void onItemSelected(
                                                        AdapterView<?> adapterView, View view,
                                                        int i, long l) {
                                                    city_id = areainfo_city.getData().get(
                                                            i).getAid();
                                                    initSpArea(areainfo_city.getData().get(
                                                            i).getAid());
                                                }

                                                @Override
                                                public void onNothingSelected(
                                                        AdapterView<?> adapterView) {

                                                }
                                            });
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

    public void initSpArea(String pcode) {
        if (CommonUtils.isNetworkAvailable(mContext)) {
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.getRequestUrl(WebUtils.CX_URL)))
                    .addParams("json", "{\"key\":\"\",\"pid\":\"" + pcode + "\"}")
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
                                    final AreaInfo areainfo_area = new Gson().fromJson(response,
                                            AreaInfo.class);
                                    String[] strs = new String[areainfo_area.getData().size()];
                                    for (int i = 0; i < areainfo_area.getData().size(); i++) {
                                        strs[i] = areainfo_area.getData().get(i).getName();
                                    }
                                    ArrayAdapter adapter = new ArrayAdapter<String>(
                                            mContext,
                                            android.R.layout.simple_spinner_item, strs);
                                    adapter.setDropDownViewResource(android.
                                            R.layout.simple_spinner_dropdown_item);
                                    sp_area.setAdapter(adapter);

                                    sp_area.setOnItemSelectedListener(
                                            new AdapterView.OnItemSelectedListener() {

                                                @Override
                                                public void onItemSelected(
                                                        AdapterView<?> adapterView, View view,
                                                        int i, long l) {
                                                    area_id = areainfo_area.getData().get(
                                                            i).getAid();
                                                }

                                                @Override
                                                public void onNothingSelected(
                                                        AdapterView<?> adapterView) {

                                                }
                                            });
                            }
                        }
                    });
        } else {
            showToast(R.string.TheNetIsUnAble);
        }
    }
}
