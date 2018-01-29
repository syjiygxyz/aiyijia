package com.ayj.aiyijia;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.ayj.aiyijia.bean.UserBean;
import com.ayj.aiyijia.utils.ACache;
import com.ayj.aiyijia.utils.CommonUtils;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.zhy.http.okhttp.OkHttpUtils;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;

/**
 * Created by zht-pc-09 on 2017/6/21.
 */
public class AyjSwApplication extends Application {

    public static AyjSwApplication app;//当前application上下文对象


    /**
     * 影响网络请求的链接地址与日志打印
     */
    public static final boolean IS_DEBUG = true;
    /**
     * 缓存类
     */
    private ACache aCache;

    /**
     * 用户信息实体
     */
    private UserBean userInfo;

    @Override
    public void onCreate() {
        super.onCreate();
        this.app = this;
        init();
    }

    private void init() {
        //网络请求
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);
        PlatformConfig.setWeixin("wx8635cd9b3d2185d6","21ba3ff61d2ff8b853ebb420061d3f58");
        //初始化缓存帮助类
        aCache = CommonUtils.getAcache();
        //   initUserInfo();
        UMShareAPI.get(this);
    }

    /**
     * 获取当前上下文对象
     *
     * @return
     */
    public static AyjSwApplication getContext() {
        return app;
    }

    /**
     * 初始化获取用户信息
     */
    private void initUserInfo() {
        userInfo = (UserBean) aCache.getAsObject(ACache.USER_INFO_KEY);
    }

    public UserBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserBean userInfo) {
        this.userInfo = userInfo;
    }

    public static AyjSwApplication getsInstance() {
        return app;
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public String getVersionName() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}

