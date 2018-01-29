package com.ayj.aiyijia.bean;

import com.ayj.aiyijia.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zht-pc-09 on 2017/5/24.
 * 通用item实体类
 */
public class UtilityItem {

    private String text;//文字

    private int resId;//资源文件

    private int type;//类型点击

    private String textId;

    public UtilityItem() {
    }

    public UtilityItem(String text, String textId) {
        this.text = text;
        this.textId = textId;
    }

    public UtilityItem(String text, int resId, int type) {
        this.text = text;
        this.resId = resId;
        this.type = type;
    }
    public UtilityItem(String text, int resId) {
        this.text = text;
        this.resId = resId;
    }
    public UtilityItem(String text) {
        this.text = text;
    }

    public String getTextId() {
        return textId;
    }

    public void setTextId(String textId) {
        this.textId = textId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static List<UtilityItem> getMainGridList(){
        List<UtilityItem> lists = new ArrayList<>();
        lists.add(new UtilityItem("社区医养", R.mipmap.main_1,1));
        lists.add(new UtilityItem("调理指南", R.mipmap.main_2,2));
        lists.add(new UtilityItem("礼仓", R.mipmap.main_3,3));
        lists.add(new UtilityItem("人体穴位", R.mipmap.main_4,4));
        lists.add(new UtilityItem("购物车", R.mipmap.main_5,5));
        lists.add(new UtilityItem("个人中心", R.mipmap.main_6,6));
        return lists;
    }

    public static List<UtilityItem> dhGridList(){
        List<UtilityItem> lists = new ArrayList<>();
        lists.add(new UtilityItem("爱e币兑换", R.mipmap.cmdh_2,2));
        lists.add(new UtilityItem("金币兑换", R.mipmap.cmdh_1,1));
        lists.add(new UtilityItem("物卷兑换", R.mipmap.cmdh_3,3));

        return lists;
    }

    public static List<UtilityItem> getInsuranceList(){
        List<UtilityItem> list = new ArrayList<>();
        list.add(new UtilityItem("车辆保险", R.mipmap.insurance_car,1));
        list.add(new UtilityItem("北斗车载", R.mipmap.insurance_beidou,2));
        list.add(new UtilityItem("我的保险", R.mipmap.insurance_my,4));
        list.add(new UtilityItem("保险咨询", R.mipmap.insurance_advice,5));
        list.add(new UtilityItem("车辆信息", R.mipmap.insurance_car_information,3));
        list.add(new UtilityItem("报价反馈", R.mipmap.insurance_feedback,6));
        return list;
    }

    public static List<UtilityItem> getInsuranceCompnayList(){
        List<UtilityItem> list = new ArrayList<>();
        list.add(new UtilityItem("中国人保", R.mipmap.bx_rb,3));
        list.add(new UtilityItem("太平洋保险", R.mipmap.bx_tp,2));
        list.add(new UtilityItem("天安保险", R.mipmap.bx_ta,4));
        list.add(new UtilityItem("中联金安", R.mipmap.bx_zl,1));
        return list;
    }

    public static List<UtilityItem> getKhqMain(){
        List<UtilityItem> list = new ArrayList<>();
        list.add(new UtilityItem("保健预约", R.mipmap.jk_1,1));
        list.add(new UtilityItem("保险服务", R.mipmap.jk_2,2));
        list.add(new UtilityItem("邻家小店", R.mipmap.jk_3,3));
        list.add(new UtilityItem("折扣商铺", R.mipmap.jk_4,4));
        list.add(new UtilityItem("春苗兑换", R.mipmap.jk_5,5));
        list.add(new UtilityItem("会员加入", R.mipmap.jk_6,6));
        list.add(new UtilityItem("春苗展柜", R.mipmap.jk_7,7));
        list.add(new UtilityItem("联销", R.mipmap.jk_8,8));
        list.add(new UtilityItem("招商加盟", R.mipmap.jk_9,9));
        list.add(new UtilityItem("营养吧", R.mipmap.jk_10,10));
        return list;
    }

    public static List<UtilityItem> getShopCenterList(){
        List<UtilityItem> list = new ArrayList<>();
        list.add(new UtilityItem("订单",R.mipmap.gr_1,1));
        list.add(new UtilityItem("预约",R.mipmap.gr_2,2));
        list.add(new UtilityItem("服务卷",R.mipmap.gr_4,4));
        list.add(new UtilityItem("物卷",R.mipmap.gr_5,5));
        list.add(new UtilityItem("体验卷",R.mipmap.gr_6,6));
        list.add(new UtilityItem("礼金",R.mipmap.gr_7,7));
        list.add(new UtilityItem("礼仓",R.mipmap.center_licang,12));
        list.add(new UtilityItem("代理圈",R.mipmap.gr_8,8));
        list.add(new UtilityItem("健康记录",R.mipmap.gr_9,9));
        list.add(new UtilityItem("收货地址",R.mipmap.gr_10,10));
        list.add(new UtilityItem("安全中心",R.mipmap.gr_11,11));
        return list;
    }
}
