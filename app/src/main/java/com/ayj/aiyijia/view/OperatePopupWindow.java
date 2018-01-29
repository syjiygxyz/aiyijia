package com.ayj.aiyijia.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.ayj.aiyijia.R;
import com.ayj.aiyijia.adapter.DhQuanAdapter;
import com.ayj.aiyijia.adapter.MyTxlPopAdapter;
import com.ayj.aiyijia.adapter.base.listener.OnItemClickListener;
import com.ayj.aiyijia.adapter.personcenter.DsjAdapter;
import com.ayj.aiyijia.bean.UtilityItem;
import com.ayj.aiyijia.bean.cmbz.YybBean;
import com.ayj.aiyijia.utils.CommonUtils;
import com.umeng.socialize.Config;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.util.List;

/**
 * Created by zht-pc-09 on 2017/6/23.
 */
public class OperatePopupWindow extends PopupWindow {

    private Context mContext;

    private View mMenuView;

    /*工具及界面通用popupwindow*/
    public OperatePopupWindow(Context context, String title, List<UtilityItem> itemList,
                              OnItemClickListener itemClickListener) {
        mContext = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        mMenuView = inflater.inflate(R.layout.pyq_sheet_layout, null);
        RecyclerView recyclerList = (RecyclerView) mMenuView.findViewById(R.id.my_sheet_recycler);
        recyclerList.setLayoutManager(new LinearLayoutManager(context));
        MyTxlPopAdapter adapter = new MyTxlPopAdapter(R.layout.my_sheet_recycler_item, itemList);
        recyclerList.setAdapter(adapter);
        recyclerList.addOnItemTouchListener(itemClickListener);

        ((TextView) mMenuView.findViewById(R.id.my_sheet_title)).setText(title);

        // 设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.popwin_anim_style);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height =
                        mMenuView.findViewById(R.id.my_sheet_bottom_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        // 点击弹出框外面自动消失
                        OperatePopupWindow.this.setOutsideTouchable(true);
                        // 自动获取焦点,否则EditText将无法获取焦点
                        OperatePopupWindow.this.setFocusable(true);
                        dismiss();
                    }
                }
                return true;
            }
        });
        mMenuView.findViewById(R.id.my_sheet_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OperatePopupWindow.this.dismiss();
            }
        });
    }

    /**
     * 弹出分享popwindow
     *
     * @param context 上下文对象
     */
    public OperatePopupWindow(Context context,View.OnClickListener itemsOnClick) {
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.share_layout, null);
        LinearLayout weChat = (LinearLayout) mMenuView
                .findViewById(R.id.share_wechat);
        LinearLayout wxCircle = (LinearLayout) mMenuView
                .findViewById(R.id.share_wxcircle);
        //设置按钮监听
        weChat.setOnClickListener(itemsOnClick);
        wxCircle.setOnClickListener(itemsOnClick);
        // 设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.popwin_anim_style);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.lin_comm_re).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        // 点击弹出框外面自动消失
                        OperatePopupWindow.this.setOutsideTouchable(true);
                        // 自动获取焦点,否则EditText将无法获取焦点
                        OperatePopupWindow.this.setFocusable(true);
                        dismiss();
                    }
                }

                return true;
            }
        });
    }

    /**
     * 弹出分享popwindow
     *
     * @param context   上下文对象
     * @param newsTitle 分享标题,分享到朋友圈的时候才需要
     * @param content   分享到微信好友的时候需要
     * @param url       分享点击详细链接
     */
    public OperatePopupWindow(Context context, final Activity activity,
                              final String newsTitle, final String content, final String url,
                              final String imgUrl) {
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.share_layout, null);
        LinearLayout weChat = (LinearLayout) mMenuView
                .findViewById(R.id.share_wechat);
        LinearLayout wxCircle = (LinearLayout) mMenuView
                .findViewById(R.id.share_wxcircle);
        weChat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 分享到微信好友
                shareWechat(mContext, activity, content, newsTitle, url,
                        imgUrl, 1, shareListener);
                // 清除popwindow
                dismiss();
            }
        });

        wxCircle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 分享到朋友圈
                shareWechat(mContext, activity, content, newsTitle, url,
                        imgUrl, 2, shareListener);
                // 清除popwindow
                dismiss();
            }
        });
        // 设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.popwin_anim_style);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.lin_comm_re).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        // 点击弹出框外面自动消失
                        OperatePopupWindow.this.setOutsideTouchable(true);
                        // 自动获取焦点,否则EditText将无法获取焦点
                        OperatePopupWindow.this.setFocusable(true);
                        dismiss();
                    }
                }

                return true;
            }
        });
    }


    /**
     * 弹出分享popwindow
     *
     * @param context 上下文对象
     * @param content 分享到微信好友的时候需要
     * @param url     分享点击详细链接
     */
    public OperatePopupWindow(Context context, final Activity activity,
                              final String content, final String url,
                              final String imgUrl) {
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.share_layout_item, null);
        LinearLayout weChat = (LinearLayout) mMenuView
                .findViewById(R.id.share_wechat);
        LinearLayout wxCircle = (LinearLayout) mMenuView
                .findViewById(R.id.share_wxcircle);

        weChat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 分享到微信好友
                shareWechat(mContext, activity, content, url,
                        imgUrl, 1, shareListener);
                // 清除popwindow
                dismiss();
            }
        });

        wxCircle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 分享到朋友圈
                shareWechat(mContext, activity, content, url,
                        imgUrl, 2, shareListener);
                // 清除popwindow
                dismiss();
            }
        });
        // 设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.popwin_anim_style);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.lin_comm_re).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        // 点击弹出框外面自动消失
                        OperatePopupWindow.this.setOutsideTouchable(true);
                        // 自动获取焦点,否则EditText将无法获取焦点
                        OperatePopupWindow.this.setFocusable(true);
                        dismiss();
                    }
                }

                return true;
            }
        });
    }

    private UMShareListener shareListener = new UMShareListener() {

        @Override
        public void onCancel(SHARE_MEDIA arg0) {
            Toast.makeText(mContext, "分享取消！", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA arg0, Throwable t) {
            Toast.makeText(mContext, "分享失败！", Toast.LENGTH_SHORT).show();
            if(t!=null){
                Log.i("throw","throw:"+t.getMessage());
            }
        }

        @Override
        public void onStart(SHARE_MEDIA share_media) {
        }

        @Override
        public void onResult(SHARE_MEDIA arg0) {
            Toast.makeText(mContext, "分享成功！", Toast.LENGTH_SHORT).show();
        }
    };
    /**
     * 分享到微信好友、朋友圈
     *
     * @param activity
     * @param content
     * @param imgUrl
     * @param url
     * @param type          1、微信好友 2、微信朋友圈
     * @param shareListener 分享回调监听
     */
    private void shareWechat(Context context, Activity activity,
                             String content, String url, String imgUrl, int type,
                             UMShareListener shareListener) {
        ShareAction shareAction = new ShareAction(activity);
//        ProgressHUD mProgress = CommonUtils.createProgressDialog(context,
//                "正在加载", true);// 创建新的dialog
//        Config.dialog = mProgress;// 修改分享默认的加载dialog
        UMWeb  web = new UMWeb(url);
        UMImage image = new UMImage(context, imgUrl);
        image.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
        web.setThumb(image);  //缩略图
        web.setDescription(content);//描述
        if (type == 1) {
            shareAction.setPlatform(SHARE_MEDIA.WEIXIN);
        } else if (type == 2) {
            shareAction.setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE);
        }
        shareAction.setCallback(shareListener);
        shareAction.withMedia(web);
        shareAction.share();
    }

    /**
     * 分享到微信好友、朋友圈
     *
     * @param activity
     * @param content
     * @param imgUrl
     * @param url
     * @param type          1、微信好友 2、微信朋友圈
     * @param shareListener 分享回调监听
     */
    private void shareWechat(Context context, Activity activity,
                             String content, String title, String url, String imgUrl, int type,
                             UMShareListener shareListener) {
        ShareAction shareAction = new ShareAction(activity);
//        ProgressHUD mProgress = CommonUtils.createProgressDialog(context,
//                "正在加载", true);// 创建新的dialog
//        Config.dialog = mProgress;// 修改分享默认的加载dialog
        UMWeb  web = new UMWeb(url);
        web.setTitle(title);//标题
        web.setThumb(new UMImage(context, imgUrl));  //缩略图
        web.setDescription(content);//描述
        if (type == 1) {
            shareAction.setPlatform(SHARE_MEDIA.WEIXIN);
        } else if (type == 2) {
            shareAction.setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE);
        }
        shareAction.setCallback(shareListener);
        shareAction.withMedia(web);
        shareAction.share();
    }


    public OperatePopupWindow(Context context, List<YybBean.DataBean> listYyb,
                              OnItemClickListener itemClickListener) {
        mContext = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        mMenuView = inflater.inflate(R.layout.pyq_sheet_layout, null);
        RecyclerView recyclerList = (RecyclerView) mMenuView.findViewById(R.id.my_sheet_recycler);
        recyclerList.setLayoutManager(new LinearLayoutManager(context));
        DhQuanAdapter dsjAdapter = new DhQuanAdapter(R.layout.dwj_list_item, listYyb);
        recyclerList.setAdapter(dsjAdapter);
        recyclerList.addOnItemTouchListener(itemClickListener);

        ((TextView) mMenuView.findViewById(R.id.my_sheet_title)).setText("请选择使用的券");

        // 设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.popwin_anim_style);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height =
                        mMenuView.findViewById(R.id.my_sheet_bottom_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        // 点击弹出框外面自动消失
                        OperatePopupWindow.this.setOutsideTouchable(true);
                        // 自动获取焦点,否则EditText将无法获取焦点
                        OperatePopupWindow.this.setFocusable(true);
                        dismiss();
                    }
                }
                return true;
            }
        });
        mMenuView.findViewById(R.id.my_sheet_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OperatePopupWindow.this.dismiss();
            }
        });
    }
}