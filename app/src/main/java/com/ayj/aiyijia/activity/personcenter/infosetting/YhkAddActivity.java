package com.ayj.aiyijia.activity.personcenter.infosetting;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ayj.aiyijia.AyjSwApplication;
import com.ayj.aiyijia.R;
import com.ayj.aiyijia.activity.base.BaseActivity;
import com.ayj.aiyijia.bean.Check;
import com.ayj.aiyijia.bean.ProvinceBean;
import com.ayj.aiyijia.bean.eventbus.FirstEvent;
import com.ayj.aiyijia.utils.CommonUtils;
import com.ayj.aiyijia.utils.ValidationUtils;
import com.ayj.aiyijia.utils.WebUtils;
import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/*
* 银行卡加入
* */
public class YhkAddActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_yh)
    TextView et_yh;
    @BindView(R.id.et_kh)
    EditText etKh;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.tv_yh)
    TextView tvYh;
    @BindView(R.id.rl_yh)
    RelativeLayout rlYh;
    @BindView(R.id.btn_register)
    TextView btnRegister;
    @BindView(R.id.cb_cx)
    CheckBox cbCx;
    @BindView(R.id.cb_xy)
    CheckBox cbXy;
    private OptionsPickerView pvOptions;

    String  mTypeString;

    private ArrayList<ProvinceBean> options1Items = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_yhk_add;
    }

    @Override
    public void initDatas() {
        options1Items.add(new ProvinceBean("BANK002", "中国建设银行"));
        options1Items.add(new ProvinceBean("BANK003", "中国银行"));
        options1Items.add(new ProvinceBean("BANK004", "交通银行"));
        options1Items.add(new ProvinceBean("BANK005", "中国农业银行"));
        options1Items.add(new ProvinceBean("BANK006", "招商银行"));
        options1Items.add(new ProvinceBean("BANK007", "中国邮政储蓄银行"));
        options1Items.add(new ProvinceBean("BANK008", "中国光大银行"));
        options1Items.add(new ProvinceBean("BANK0011", "光大银行"));
        options1Items.add(new ProvinceBean("BANK0012", "兴业银行"));
        options1Items.add(new ProvinceBean("BANK0013", "民生银行"));
        options1Items.add(new ProvinceBean("BANK0014", "上海银行"));
        options1Items.add(new ProvinceBean("BANK0015", "农村商业银行"));
        initOptionPicker();

    }

    @Override
    public void configViews() {
        cbCx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cbCx.setChecked(true);
                cbXy.setChecked(false);
                mTypeString = "BANKCARDTYPE001";
            }
        });
        cbXy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cbCx.setChecked(false);
                cbXy.setChecked(true);
                mTypeString = "BANKCARDTYPE002";
            }
        });
    }

    @OnClick({R.id.iv_back, R.id.rl_yh, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_yh:
                pvOptions.show();
                break;
            case R.id.btn_register:
                doPost();
                break;
        }
    }

    private void initOptionPicker() {//条件选择器初始化
        pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText();
                tvYh.setText(tx);
                tvYh.setTag(options1Items.get(options1).getId());
            }
        })
                .setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setTitleText("请选择银行卡种类")
                .setContentTextSize(20)//设置滚轮文字大小
                .setSelectOptions(0, 1)//默认选中项
                .setTitleBgColor(Color.WHITE)
                .setTitleColor(Color.BLACK)
                .setCancelColor(Color.BLACK)
                .setSubmitColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK)
                .isCenterLabel(true) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setLabels("", "", "")
                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                .build();
        pvOptions.setPicker(options1Items);//二级选择器
    }

    private void doPost() {
        if (TextUtils.isEmpty(etName.getText().toString().trim())) {
            showToast("持卡人姓名不能为空");
            return;
        }
        if (TextUtils.isEmpty(etKh.getText().toString().trim())) {
            showToast("持卡人卡号不能为空");
            return;
        }
        if (TextUtils.isEmpty(et_yh.getText().toString().trim())) {
            showToast("银行卡种类不能为空");
            return;
        }
        if (!ValidationUtils.checkBankCard(etKh.getText().toString().trim())) {
            showToast("请输入正确的银行卡号");
            return;
        }
        if (CommonUtils.isNetworkAvailable(mContext)) {
            mProgressHub.show();
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.ADD_YHK))
                    .addParams("json", "{\"key\":\"\",\"userid\":\"" + AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getSnid()
                            + "\",\"pwd\":\"" + AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getPassWord()
                            + "\",\"bankcardtype\":\"" + mTypeString + "\",\"bankid\":\"" + tvYh.getTag().toString() + "\",\"bankno\":\"" + etKh.getText().toString().trim() + "\"}")
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
                                    showToast("绑定成功");
                                    EventBus.getDefault().post(
                                            new FirstEvent("YHKBDCLICK"));
                                    finish();
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
