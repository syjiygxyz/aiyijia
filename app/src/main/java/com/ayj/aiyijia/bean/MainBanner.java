package com.ayj.aiyijia.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/8/7.
 */
public class MainBanner implements Serializable{


    /**
     * err : 0
     * msg :
     * total : 3
     * data : [{"matid":null,"shopid":"001","snid":"186","ordertype":"MEMBERORDERTYPE001","ordertypeshow":"三折铺订单","matidshow":"","shopidshow":"朗悦湾小区服务点","createtime":"2017-05-06 10:33:24","title":"3","redirecttype":"MAINADREDIRECTTYPE","adcomment":"3","uploaddate":null,"imgname":"Upload/2017-08-24/20170824132143450","adtype":"MAINADTYPE001","adtypeshow":"爱医家广告","imgnameshow":"http://crm.21ga.cn:9000/qmkmimageupload/CompressImages.aspx?isformalsys=1&filename=Upload/2017-08-24/20170824132143450","adtype2":null,"adtype2show":"","imgurl":null,"imgurlshow":"","imgurlcompressshow":""},{"matid":"WL-20161202-0000676","shopid":"001","snid":"185","ordertype":"MEMBERORDERTYPE019","ordertypeshow":"公司统一商城订单","matidshow":"电子艾灸仪","shopidshow":"朗悦湾小区服务点","createtime":"2017-05-06 10:33:08","title":"2","redirecttype":"MAINADREDIRECTTYPE","adcomment":"2","uploaddate":null,"imgname":"Upload/2017-05-06/20170506103308253","adtype":"MAINADTYPE001","adtypeshow":"爱医家广告","imgnameshow":"http://crm.21ga.cn:9000/qmkmimageupload/CompressImages.aspx?isformalsys=1&filename=Upload/2017-05-06/20170506103308253","adtype2":null,"adtype2show":"","imgurl":null,"imgurlshow":"","imgurlcompressshow":""},{"matid":"98","shopid":"001","snid":"184","ordertype":"MEMBERORDERTYPE005","ordertypeshow":"邻家铺子订单","matidshow":"医用冷敷贴","shopidshow":"朗悦湾小区服务点","createtime":"2017-05-06 10:32:46","title":"1","redirecttype":"MAINADREDIRECTTYPE","adcomment":"1","uploaddate":null,"imgname":"Upload/2017-05-11/20170511094003206","adtype":"MAINADTYPE001","adtypeshow":"爱医家广告","imgnameshow":"http://crm.21ga.cn:9000/qmkmimageupload/CompressImages.aspx?isformalsys=1&filename=Upload/2017-05-11/20170511094003206","adtype2":null,"adtype2show":"","imgurl":null,"imgurlshow":"","imgurlcompressshow":""}]
     */

    private int err;
    private String msg;
    private String total;
    private List<DataBean> data;

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * matid : null
         * shopid : 001
         * snid : 186
         * ordertype : MEMBERORDERTYPE001
         * ordertypeshow : 三折铺订单
         * matidshow :
         * shopidshow : 朗悦湾小区服务点
         * createtime : 2017-05-06 10:33:24
         * title : 3
         * redirecttype : MAINADREDIRECTTYPE
         * adcomment : 3
         * uploaddate : null
         * imgname : Upload/2017-08-24/20170824132143450
         * adtype : MAINADTYPE001
         * adtypeshow : 爱医家广告
         * imgnameshow : http://crm.21ga.cn:9000/qmkmimageupload/CompressImages.aspx?isformalsys=1&filename=Upload/2017-08-24/20170824132143450
         * adtype2 : null
         * adtype2show :
         * imgurl : null
         * imgurlshow :
         * imgurlcompressshow :
         */

        private Object matid;
        private String shopid;
        private String snid;
        private String ordertype;
        private String ordertypeshow;
        private String matidshow;
        private String shopidshow;
        private String createtime;
        private String title;
        private String redirecttype;
        private String adcomment;
        private Object uploaddate;
        private String imgname;
        private String adtype;
        private String adtypeshow;
        private String imgnameshow;
        private Object adtype2;
        private String adtype2show;
        private Object imgurl;
        private String imgurlshow;
        private String imgurlcompressshow;

        public Object getMatid() {
            return matid;
        }

        public void setMatid(Object matid) {
            this.matid = matid;
        }

        public String getShopid() {
            return shopid;
        }

        public void setShopid(String shopid) {
            this.shopid = shopid;
        }

        public String getSnid() {
            return snid;
        }

        public void setSnid(String snid) {
            this.snid = snid;
        }

        public String getOrdertype() {
            return ordertype;
        }

        public void setOrdertype(String ordertype) {
            this.ordertype = ordertype;
        }

        public String getOrdertypeshow() {
            return ordertypeshow;
        }

        public void setOrdertypeshow(String ordertypeshow) {
            this.ordertypeshow = ordertypeshow;
        }

        public String getMatidshow() {
            return matidshow;
        }

        public void setMatidshow(String matidshow) {
            this.matidshow = matidshow;
        }

        public String getShopidshow() {
            return shopidshow;
        }

        public void setShopidshow(String shopidshow) {
            this.shopidshow = shopidshow;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getRedirecttype() {
            return redirecttype;
        }

        public void setRedirecttype(String redirecttype) {
            this.redirecttype = redirecttype;
        }

        public String getAdcomment() {
            return adcomment;
        }

        public void setAdcomment(String adcomment) {
            this.adcomment = adcomment;
        }

        public Object getUploaddate() {
            return uploaddate;
        }

        public void setUploaddate(Object uploaddate) {
            this.uploaddate = uploaddate;
        }

        public String getImgname() {
            return imgname;
        }

        public void setImgname(String imgname) {
            this.imgname = imgname;
        }

        public String getAdtype() {
            return adtype;
        }

        public void setAdtype(String adtype) {
            this.adtype = adtype;
        }

        public String getAdtypeshow() {
            return adtypeshow;
        }

        public void setAdtypeshow(String adtypeshow) {
            this.adtypeshow = adtypeshow;
        }

        public String getImgnameshow() {
            return imgnameshow;
        }

        public void setImgnameshow(String imgnameshow) {
            this.imgnameshow = imgnameshow;
        }

        public Object getAdtype2() {
            return adtype2;
        }

        public void setAdtype2(Object adtype2) {
            this.adtype2 = adtype2;
        }

        public String getAdtype2show() {
            return adtype2show;
        }

        public void setAdtype2show(String adtype2show) {
            this.adtype2show = adtype2show;
        }

        public Object getImgurl() {
            return imgurl;
        }

        public void setImgurl(Object imgurl) {
            this.imgurl = imgurl;
        }

        public String getImgurlshow() {
            return imgurlshow;
        }

        public void setImgurlshow(String imgurlshow) {
            this.imgurlshow = imgurlshow;
        }

        public String getImgurlcompressshow() {
            return imgurlcompressshow;
        }

        public void setImgurlcompressshow(String imgurlcompressshow) {
            this.imgurlcompressshow = imgurlcompressshow;
        }
    }
}
