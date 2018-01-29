package com.ayj.aiyijia.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayj.aiyijia.R;
import com.ayj.aiyijia.activity.base.BaseActivity;
import com.ayj.aiyijia.activity.base.NewsActivity;
import com.ayj.aiyijia.activity.shopping.ShoppingDetailActivity;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
* 扫描界面
* */
public class SmActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    String type1;

    private CaptureFragment captureFragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_sm;
    }

    @Override
    public void initDatas() {
       tvTitle.setText("扫描二维码");
        captureFragment = new CaptureFragment();
        // 为二维码扫描界面设置定制化界面
        CodeUtils.setFragmentArgs(captureFragment, R.layout.my_camera);
        captureFragment.setAnalyzeCallback(analyzeCallback);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container, captureFragment).commit();
    }

    @Override
    public void configViews() {

    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            Log.i("11",result);
            try {
                JSONObject jsonObject = new JSONObject(result);
                if(jsonObject.opt("ordertype").equals("MEMBERORDERTYPE033")||jsonObject.opt("ordertype").equals("MEMBERORDERTYPE032")){
                    type1 = "cmrg";
                    Intent intent = new Intent();
                    intent.putExtra("type",type1);
                    intent.putExtra("ordertype",jsonObject.opt("ordertype")+"");
                    intent.putExtra("matid",jsonObject.opt("matid")+"");
                    intent.setClass(mContext, ShoppingDetailActivity.class);
                    startActivity(intent);
                    SmActivity.this.finish();
                }else{
                    showToast("商品不存在");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onAnalyzeFailed() {
            showToast("解析二维码失败");
        }
    };

}
