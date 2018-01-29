package com.ayj.aiyijia.utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.util.VersionInfo;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ayj.aiyijia.R;
import com.ayj.aiyijia.bean.BbUpDateBean;
import com.ayj.aiyijia.bean.Check;
import com.ayj.aiyijia.view.ProgressHUD;
import com.ayj.aiyijia.view.sweetalert.SweetAlertDialog;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import okhttp3.Call;
import okhttp3.Request;

/**
 * 在线更新
 */
public class UpDateUtils {

    private Context context;
    private Boolean showHUd;
    private ProgressHUD mProgressHUD;
    private ProgressDialog pDialog;
    private Activity act;
    private VersionInfo info = null;

    /* 更新进度条 */
    private ProgressBar mProgress;

    /* 是否取消更新 */
    private boolean cancelUpdate = false;

    private Dialog mDownloadDialog;

    BbUpDateBean bbUpDateBean;

    public UpDateUtils(Context context, Boolean showHUd) {
        this.context = context;
        this.showHUd = showHUd;
    }

    public void update() {
        getLatestVersion();
    }

    // 连接服务器获取最新版本号及版本名
    private void getLatestVersion() {
        if (CommonUtils.isNetworkAvailable(context)) {
            JSONObject obj = new JSONObject();
            try {
                obj.put("key", "");
                obj.put("snid", "1");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            OkHttpUtils.post()
                    .url(WebUtils.getRequestUrl(WebUtils.BB_GX))
                    .addParams("json", String.valueOf(obj))
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
                                    bbUpDateBean = new Gson().fromJson(response, BbUpDateBean.class);
                                    if (getVersionCode(context) < Integer.parseInt(bbUpDateBean.getData().get(0).getVercode())) {
                                        if ("0".equals(bbUpDateBean.getData().get(0).getIsmust())) {// 1代表强制更新
                                            SweetAlertDialog dialog = CommonUtils.getOnlyConfirmDialog(context, "提示", "App有重大更新,请尽快更新App", "更新", new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                    downloadApk();
                                                }
                                            });
                                            dialog.setCancelable(false);
                                            dialog.setCanceledOnTouchOutside(false);
                                            dialog.show();
                                        } else {// 1代表提示更新
                                            CommonUtils.getConfirmDialog(context, "提示", "发现新版本，是否更新？\n", "更新", "取消", new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                    sweetAlertDialog.dismiss();
                                                    downloadApk();
                                                }
                                            }).show();
                                        }
                                    }
                                    break;
                                default:
                                    CommonUtils.showToast(context, check.getMsg());
                                    break;
                            }
                        }
                    });
        } else {
            CommonUtils.showToast(context, R.string.TheNetIsUnAble);
        }
    }

    /**
     * 获取软件版本号
     *
     * @param context
     * @return
     */
    private int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            // 获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = context.getPackageManager().getPackageInfo("com.ayj.aiyijia", 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }


    /**
     * 下载文件
     *
     * @author seven
     */
    private void downloadApk() {
        pDialog = new ProgressDialog(context);
        pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pDialog.setTitle("更新");
        pDialog.setProgress(100);
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();
        pDialog.setProgress(0);
        OkHttpUtils//
                .get()//
                .url(bbUpDateBean.getData().get(0).getUrl())//
                .build()//
                .execute(new FileCallBack(getCachePath().toString(), System.currentTimeMillis() + ".apk")//
                {

                    @Override
                    public void onBefore(Request request, int id) {
                        Toast.makeText(context, "开始下载更新", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void inProgress(float progress, long total, int id) {
                        pDialog.setProgress((int) (100 * progress));
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(context, "更新失败", Toast.LENGTH_SHORT).show();
                        pDialog.cancel();
                    }

                    @Override
                    public void onResponse(File file, int id) {
                        pDialog.cancel();
                        Toast.makeText(context, "下载更新成功", Toast.LENGTH_LONG)
                                .show();
                        //开始安装
                        doInstall(file);
                    }
                });
    }

    /**
     * 获取APK缓存的路径
     *
     * @return
     */
    public File getCachePath() {
        File file = context.getExternalCacheDir();
        File newFile = new File(file, "apks");
        if (!newFile.exists()) {
            newFile.mkdirs();
        }
        return newFile;
    }

    // 启动安装
    private void doInstall(File apkFile) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, "com.ayj.aiyijia.fileprovider", apkFile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());

    }
}
