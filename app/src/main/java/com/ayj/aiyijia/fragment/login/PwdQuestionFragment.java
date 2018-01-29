package com.ayj.aiyijia.fragment.login;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.ayj.aiyijia.R;
import com.ayj.aiyijia.bean.Check;
import com.ayj.aiyijia.bean.MbQuestion;
import com.ayj.aiyijia.fragment.base.LazyFragment;
import com.ayj.aiyijia.utils.CommonUtils;
import com.ayj.aiyijia.utils.MD5Utils;
import com.ayj.aiyijia.utils.ValidationUtils;
import com.ayj.aiyijia.utils.WebUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/8/8.
 * 密保问题找回
 */
public class PwdQuestionFragment extends LazyFragment {

    @BindView(R.id.et_retrieve_num)
    EditText etRetrieveNum;

    @BindView(R.id.tv_question1)
    TextView tvQuestion1;
    @BindView(R.id.ed_question1)
    EditText edQuestion1;

    @BindView(R.id.tv_question2)
    TextView tvQuestion2;
    @BindView(R.id.ed_question2)
    EditText edQuestion2;

    @BindView(R.id.et_retrieve_pwd)
    EditText etRetrievePwd;
    @BindView(R.id.et_retrieve_pwd2)
    EditText etRetrievePwd2;
    @BindView(R.id.btn_register)
    TextView btn_register;

    @Override
    public void fetchData() {
        etRetrieveNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (ValidationUtils.checkTelPhone(etRetrieveNum.getText().toString().trim())) {
                    getQuestion();
                }
            }
        });
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_question_get;
    }

    private void getQuestion() {
        if (CommonUtils.isNetworkAvailable(getActivity())) {
            mProgressHub.show();
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.MB_GET))
                    .addParams("json",
                            "{\"key\":\"\",\"mobile\":\"" + etRetrieveNum.getText().toString().trim() + "\"}")
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            mProgressHub.dismiss();
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            mProgressHub.dismiss();
                            Check check = new Gson().fromJson(response,
                                    Check.class);
                            switch (check.getErr()) {
                                case 0:
                                    MbQuestion mbQuestion = new Gson().fromJson(response,
                                            MbQuestion.class);
                                    tvQuestion1.setText(mbQuestion.getData().get(0).getQuestion());
                                    tvQuestion1.setTag(mbQuestion.getData().get(0).getSnid());
                                    tvQuestion2.setText(mbQuestion.getData().get(1).getQuestion());
                                    tvQuestion2.setTag(mbQuestion.getData().get(1).getSnid());
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


    private void doCheck() {
        if (TextUtils.isEmpty(etRetrieveNum.getText().toString().trim())) {
            showToast("请填写手机号");
            return;
        }
        if (!ValidationUtils.checkTelPhone(etRetrieveNum.getText().toString().trim())) {
            showToast("请检查是否正确");
            return;
        }
        if (TextUtils.isEmpty(edQuestion1.getText().toString().trim()) || TextUtils.isEmpty(edQuestion2.getText().toString().trim())) {
            showToast("请填写你的密保答案");
            return;
        }
        if (TextUtils.isEmpty(etRetrievePwd.getText().toString().trim())) {
            showToast("请填写你的密码");
            return;
        }
        if (TextUtils.isEmpty(etRetrievePwd2.getText().toString().trim())) {
            showToast("请确认你的密码");
            return;
        }
        if (!etRetrievePwd2.getText().toString().trim().equals(etRetrievePwd2.getText().toString().trim())) {
            showToast("两次输入密码不一致");
            return;
        }
        doPost();
    }

    private void doPost() {
        if (CommonUtils.isNetworkAvailable(getActivity())) {
            mProgressHub.show();
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.MB_POST))
                    .addParams("json", "{\"key\":\"\",\"mobile\":\"" + etRetrieveNum.getText().toString().trim() + "\","
                            + "\"newpwd\":\"" + MD5Utils.getMD5String(etRetrievePwd.getText().toString().trim()) + "\","
                            + "\"detail\":\"[{\\\"questionid\\\":\\\"" + tvQuestion1.getTag().toString().trim() + "\\\","
                            + "\\\"answer\\\":\\\"" + edQuestion1.getText().toString().trim() + "\\\"},"
                            + "{\\\"questionid\\\":\\\"" + tvQuestion1.getTag().toString().trim() + "\\\","
                            + "\\\"answer\\\":\\\"" + edQuestion2.getText().toString().trim() + "\\\"}]\"}")
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            mProgressHub.dismiss();
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            mProgressHub.dismiss();
                            Check check = new Gson().fromJson(response,
                                    Check.class);
                            switch (check.getErr()) {
                                case 0:
                                    getActivity().finish();
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

    @OnClick(R.id.btn_register)
    public void onViewClicked() {
        doCheck();
    }
}
