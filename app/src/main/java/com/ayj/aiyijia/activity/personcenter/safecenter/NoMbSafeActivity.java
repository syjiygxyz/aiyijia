package com.ayj.aiyijia.activity.personcenter.safecenter;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ayj.aiyijia.AyjSwApplication;
import com.ayj.aiyijia.R;
import com.ayj.aiyijia.activity.base.BaseActivity;
import com.ayj.aiyijia.adapter.base.BaseQuickAdapter;
import com.ayj.aiyijia.adapter.base.listener.OnItemClickListener;
import com.ayj.aiyijia.bean.Check;
import com.ayj.aiyijia.bean.Problem;
import com.ayj.aiyijia.bean.UtilityItem;
import com.ayj.aiyijia.bean.eventbus.FirstEvent;
import com.ayj.aiyijia.utils.CommonUtils;
import com.ayj.aiyijia.utils.WebUtils;
import com.ayj.aiyijia.view.OperatePopupWindow;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/*
* 木有密保问题
* */
public class NoMbSafeActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_select_problem1)
    TextView tvSelectProblem1;
    @BindView(R.id.et_answer_problem1)
    EditText etAnswerProblem1;
    @BindView(R.id.tv_select_problem2)
    TextView tvSelectProblem2;
    @BindView(R.id.et_answer_problem2)
    EditText etAnswerProblem2;
    @BindView(R.id.btn_save_answer)
    Button btnSaveAnswer;

    OperatePopupWindow operatePopupWindow;

    List<UtilityItem> itemList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_no_mb_safe;
    }

    @Override
    public void initDatas() {
        tvTitle.setText("设置密保问题");
        getSafeQuestion();
    }

    @Override
    public void configViews() {

    }

    @OnClick({R.id.iv_back, R.id.tv_select_problem1, R.id.tv_select_problem2, R.id.btn_save_answer})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_select_problem1:
                operatePopupWindow = new OperatePopupWindow(mContext, "请选择", itemList,
                        new OnItemClickListener() {
                            @Override
                            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                                tvSelectProblem1.setText(itemList.get(position).getText());
                                tvSelectProblem1.setTag(itemList.get(position).getTextId());
                                operatePopupWindow.dismiss();
                            }
                        });
                operatePopupWindow.showAtLocation(btnSaveAnswer, Gravity.BOTTOM
                        | Gravity.CENTER_HORIZONTAL, 0, 0);
                operatePopupWindow.update();
                break;
            case R.id.tv_select_problem2:
                operatePopupWindow = new OperatePopupWindow(mContext, "请选择", itemList,
                        new OnItemClickListener() {
                            @Override
                            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                                tvSelectProblem2.setText(itemList.get(position).getText());
                                tvSelectProblem2.setTag(itemList.get(position).getTextId());
                                operatePopupWindow.dismiss();
                            }
                        });
                operatePopupWindow.showAtLocation(btnSaveAnswer, Gravity.BOTTOM
                        | Gravity.CENTER_HORIZONTAL, 0, 0);
                operatePopupWindow.update();
                break;
            case R.id.btn_save_answer:
                doPost();
                break;
        }
    }

    /**
     * 获取所有密保问题
     */
    private void getSafeQuestion() {
        if (CommonUtils.isNetworkAvailable(mContext)) {
            mProgressHub.dismiss();
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.MB_ALL))
                    .addParams("json", "{\"key\":\"\"}")
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
                                    Problem problem = new Gson().fromJson(response, Problem.class);
                                    for (int i = 0; i < problem.getData().size(); i++) {
                                        itemList.add(new UtilityItem(problem.getData().get(i).getQuestion(), problem.getData().get(i).getSnid()));
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

    private void doPost() {
        if(TextUtils.isEmpty(tvSelectProblem1.getText().toString().trim())){
            showToast("请选择密保问题");
            return;
        }
        if(TextUtils.isEmpty(tvSelectProblem2.getText().toString().trim())){
            showToast("请选择密保问题");
            return;
        }
        if(TextUtils.isEmpty(etAnswerProblem1.getText().toString().trim())){
            showToast("请填写密保问题1");
            return;
        }
        if(TextUtils.isEmpty(etAnswerProblem2.getText().toString().trim())){
            showToast("请填写密保问题2");
            return;
        }
        if (CommonUtils.isNetworkAvailable(mContext)) {
            mProgressHub.show();
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.BC_MB))
                    .addParams("json", "{\"key\":\"\",\"msnid\":\""
                            + AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getSnid() + "\","
                            + "\"pwd\":\"" + AyjSwApplication.getsInstance().getUserInfo().getData().get(0).getPassWord() + "\","
                            + "\"detail\":\"[{\\\"questionid\\\":\\\"" + tvSelectProblem1.getTag() + "\\\","
                            + "\\\"answer\\\":\\\"" + etAnswerProblem1.getText().toString().trim() + "\\\"},"
                            + "{\\\"questionid\\\":\\\"" + tvSelectProblem2.getTag() + "\\\","
                            + "\\\"answer\\\":\\\"" + etAnswerProblem2.getText().toString().trim() + "\\\"}]\"}")
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
                                    showToast("保存成功");
                                    EventBus.getDefault().post(
                                            new FirstEvent("SetZfPwd"));
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
