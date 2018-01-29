package com.ayj.aiyijia.activity.personcenter.infosetting;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ayj.aiyijia.AyjSwApplication;
import com.ayj.aiyijia.R;
import com.ayj.aiyijia.activity.base.BaseActivity;
import com.ayj.aiyijia.adapter.base.BaseQuickAdapter;
import com.ayj.aiyijia.adapter.base.listener.OnItemClickListener;
import com.ayj.aiyijia.bean.Check;
import com.ayj.aiyijia.bean.UserBean;
import com.ayj.aiyijia.bean.UtilityItem;
import com.ayj.aiyijia.bean.eventbus.FirstEvent;
import com.ayj.aiyijia.utils.CommonUtils;
import com.ayj.aiyijia.utils.ValidationUtils;
import com.ayj.aiyijia.utils.WebUtils;
import com.ayj.aiyijia.view.OperatePopupWindow;
import com.bigkoo.pickerview.TimePickerView;
import com.google.gson.Gson;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
/*
* 编辑资料界面
* */
public class InfoSettingActivity extends BaseActivity implements TakePhoto.TakeResultListener, InvokeListener {

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.iv)
    ImageView mIv;//头像
    @BindView(R.id.tv_sexy)
    TextView mTvSexy;//性别
    @BindView(R.id.tv_date)
    TextView mTvDate;//日期
    @BindView(R.id.tv_phone)
    TextView mTvPhone;//手机号
    @BindView(R.id.tv_sm)
    TextView mTvSm;//实名认证
    @BindView(R.id.tv_address)
    TextView mTvAddress;//邮箱
    @BindView(R.id.tv_card)
    TextView mTvCard;//银行卡
    UserBean.DataBean user = null;
    @BindView(R.id.rl_tx)
    RelativeLayout rlTx;
    @BindView(R.id.rl_sex)
    RelativeLayout rlSex;
    @BindView(R.id.tv_zz)
    TextView tvZz;

    String img1;

    private TakePhoto takePhoto;
    private InvokeParam invokeParam;

    List<UtilityItem> itemList = new ArrayList<>();

    private OperatePopupWindow pop;// 弹出的popwindow

    @BindView(R.id.ll)
    LinearLayout ll;

    CropOptions options;

    @BindView(R.id.rl_phone)
    RelativeLayout rl_phone;

    Dialog dialog;

    @BindView(R.id.tv_nc)
    TextView tv_nc;




    @Override
    public int getLayoutId() {
        return R.layout.activity_info_setting;
    }

    @Override
    public void initDatas() {
        EventBus.getDefault().register(this);
        mTvTitle.setText("个人资料");
        user = AyjSwApplication.getsInstance().getUserInfo().getData().get(0);
        if ("".equals(user.getPhotonameshow())) {
            mIv.setImageResource(R.mipmap.small_loading_head);
        } else {
            Picasso.with(mContext).load(user.getPhotonameshow()).placeholder(R.mipmap.small_loading_head).error(
                    R.mipmap.small_loading_head).into(
                    mIv);
        }
        mTvSexy.setText(user.getSexshow());
        if (null == user.getBirthday()) {
            mTvDate.setText("未填写");
        } else {
            mTvDate.setText(user.getBirthday() + "");
        }
        if (null == user.getMobile()) {
            mTvPhone.setText("未填写");
        } else {
            String str = user.getMobile() + "";
            mTvPhone.setText(str.substring(0, 3) + "****" + str.substring(7, str.length()));
        }
        if (null == user.getName()) {
            mTvSm.setText("未填写");
        } else {
            mTvSm.setText("*" + user.getName().substring(1, user.getName().length()));
        }
        if (null == user.getEmail()) {
            mTvAddress.setText("未填写");
        } else {
            String str = user.getEmail() + "";
            mTvAddress.setText(str.substring(0, 2) + "****" + str.substring(6, str.length()));
        }
        if (null == user.getNickname()||"".equals(user.getNickname())) {
            tv_nc.setText("未填写");
        } else {
            tv_nc.setText(user.getNickname());
        }
        if (null == user.getHomeaddr()||"".equals(user.getHomeaddr())) {
            tvZz.setText("未填写");
        } else {
            tvZz.setText(user.getHomeaddr()+"");
        }

        /*照片剪裁*/
        options = new CropOptions.Builder()
                .setAspectX(2)
                .setAspectY(1)
                .setOutputX(300)
                .setOutputY(150)
                .create();
    }


    @Override
    public void configViews() {

    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

    //申请体验用户什么的
    private void addData(int type) {
        itemList.clear();
        if (type == 1) {
            UtilityItem item = new UtilityItem();
            item.setText("男");
            itemList.add(item);
            UtilityItem item1 = new UtilityItem();
            item1.setText("女");
            itemList.add(item1);
        } else if (type == 2) {
            UtilityItem item = new UtilityItem();
            item.setText("拍照");
            itemList.add(item);
            UtilityItem item1 = new UtilityItem();
            item1.setText("相册");
            itemList.add(item1);
        }
    }

    /**
     * 获取TakePhoto实例
     *
     * @return
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //以下代码为处理Android6.0、7.0动态权限所需
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }


    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    @Override
    public void takeCancel() {
        showToast("拍照取消");
    }

    @Override
    public void takeFail(TResult result, String msg) {
        showToast("拍照失败");
    }

    @Override
    public void takeSuccess(TResult result) {
        showImg(result.getImages());
    }

    private void showImg(ArrayList<TImage> images) {
        Bitmap bm = BitmapFactory.decodeFile(images.get(0).getOriginalPath());
        mIv.setImageBitmap(bm);
        doCheck(images);
    }

    private void showPop() {
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        final Uri imageUri = Uri.fromFile(file);
        pop = new OperatePopupWindow(mContext,
                "请选择", itemList,
                new OnItemClickListener() {
                    @Override
                    public void onSimpleItemClick(
                            BaseQuickAdapter adapter,
                            View view, int position) {
                                  /*拍照*/
                        if (0 == position) {
                            takePhoto.onPickFromCaptureWithCrop(imageUri, options);
                        } else if (1 == position) {
                            takePhoto.onPickMultiple(1);
                        }
                        pop.dismiss();
                    }
                });
        pop.showAtLocation(ll, Gravity.BOTTOM
                | Gravity.CENTER_HORIZONTAL, 0, 0);
        pop.update();
    }

    private void showSexPop() {
        pop = new OperatePopupWindow(mContext,
                "请选择", itemList,
                new OnItemClickListener() {
                    @Override
                    public void onSimpleItemClick(
                            BaseQuickAdapter adapter,
                            View view, int position) {
                        if (0 == position) {
                            Zlxl("sex", "SEX001", mTvSexy);
                        } else if (1 == position) {
                            Zlxl("sex", "SEX002", mTvSexy);
                        }
                        pop.dismiss();
                    }
                });
        pop.showAtLocation(ll, Gravity.BOTTOM
                | Gravity.CENTER_HORIZONTAL, 0, 0);
        pop.update();
    }

    private void doCheck(ArrayList<TImage> images) {
        if (CommonUtils.isNetworkAvailable(mContext)) {
            mProgressHub.show();
            OkHttpUtils.post()
                    .addFile("mFile", images.get(0).getOriginalPath(), new File(images.get(0).getOriginalPath()))
                    .url(WebUtils.HOST_TWO)
                    .addParams("isrealsys", AyjSwApplication.IS_DEBUG ? "1" : "0")
                    .addParams("folder", "memberhead")
                    .addParams("filetype", CommonUtils.getType(images.get(0).getOriginalPath()))
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
                                    img1 = check.getData().toString();
                                    Zlxl("photoname",img1,1);
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

    @OnClick({R.id.rl_tx, R.id.rl_sex, R.id.iv_back,R.id.rl_date,R.id.rl_nc,R.id.rl_phone,R.id.rl_sm,R.id.rl_yx,R.id.rl_zz,R.id.rl_yhk})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_tx:
                addData(2);
                showPop();
                break;
            case R.id.rl_yhk:
                startActivity(new Intent(mContext,YhCardActivity.class));
                break;
            case R.id.rl_zz:
                startActivity(new Intent(mContext,ZzSetActivity.class));
                break;
            case R.id.rl_sex:
                addData(1);
                showSexPop();
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_yx:
                showNcDialog("请输入邮箱","email",mTvAddress);
                break;
            case R.id.rl_nc:
                showNcDialog("请输入昵称","nickname",tv_nc);
                break;
            case R.id.rl_sm:
                startActivity(new Intent(mContext,NameRzActivity.class));
                break;
            case R.id.rl_date:
                showDayPickView("yyyy-MM-dd");
                break;
            case R.id.rl_phone:
                startActivity(new Intent(mContext,PhoneXgActivity.class));
                break;
        }
    }
    private void showDayPickView(final String dateStyle){
        Calendar startDate = Calendar.getInstance();
        startDate.set(1950, 0, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2100, 0, 23);
        Calendar nowDate = Calendar.getInstance();
        nowDate.set(nowDate.get(Calendar.YEAR),(nowDate.get(Calendar.MONTH) + 1),nowDate.get(Calendar.DAY_OF_MONTH));
        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                Zlxl("birthday", CommonUtils.getTime(date,dateStyle), mTvDate);
            }
        }).setType(new boolean[]{true, true, true, false, false, false}).setCancelText("取消").setDate("未填写".equals(mTvDate.getText().toString())?nowDate:CommonUtils.StringToCalendar(mTvDate.getText().toString(),dateStyle)).setRangDate(startDate, endDate).build();;//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.show();
    }

    private void showNcDialog(final String text, final String code, final TextView tv_show) {
        dialog = new Dialog(mContext, R.style.base_dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //定义一个dialog,并将自己定义的样式加入进去
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.nc_dialog, null);
        dialog.setContentView(dialogView); //将窗口布局加入到dialog中
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCanceledOnTouchOutside(true); //设置点击空白处不会消失
        final EditText et = (EditText) dialog.findViewById(R.id.et);
        TextView content_text = (TextView) dialog.findViewById(R.id.content_text);
        content_text.setText(text);
        Button cancel_button = (Button) dialog.findViewById(R.id.cancel_button);
        Button confirm_button = (Button) dialog.findViewById(R.id.confirm_button);
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(code.equals("nickname")){
                    if (et.getText().toString().trim().equals("")) {
                        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
                    } else {
                        Zlxl(code,et.getText().toString().trim(), tv_show);
                        dialog.dismiss();
                    }
                }else if(code.equals("email")){
                    if (et.getText().toString().trim().equals("")) {
                        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
                    } else if(!ValidationUtils.checkEmail(et.getText().toString().trim())){
                        showToast("请检查你的邮箱是否正确");
                    }else{
                        Zlxl(code,et.getText().toString().trim(), tv_show);
                        dialog.dismiss();
                    }
                }

            }
        });
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus
    }

    @Subscribe
    public void onEventMainThread(FirstEvent event) {
        /*手机号*/
        user = AyjSwApplication.getsInstance().getUserInfo().getData().get(0);
        if (event.getMsg().equals("SJHCLICK")) {
            if (null == user.getMobile()) {
                mTvPhone.setText("未填写");
            } else {
                String str = user.getMobile() + "";
                mTvPhone.setText(str.substring(0, 3) + "****" + str.substring(7, str.length()));
            }
        /*实名认证*/
        }else if(event.getMsg().equals("SMCLICK")){
            if (null == user.getName()) {
                mTvSm.setText("未填写");
            } else {
                mTvSm.setText("*" + user.getName().substring(1, user.getName().length()));
            }
        /**/
        }else if(event.getMsg().equals("ZZCLICK")){
            if (null == user.getHomeaddr()||"".equals(user.getHomeaddr())) {
                tvZz.setText("未填写");
            } else {
                tvZz.setText(user.getHomeaddr()+"");
            }
        }
    }
}

