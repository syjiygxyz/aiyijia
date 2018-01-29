package com.ayj.aiyijia.bean;

import java.util.List;

/**
 * Created by zht-pc-09 on 2017/6/28.
 */
public class MdClBean {

    /**
     * err : 0
     * msg :
     * total : 1
     * data : [{"snid":"20170306091000000324","shopid":"001","num":"1.0",
     * "matid":"WL-20161206-0000682","saleunitid":"2015081010341","matidshow":"艾灸/针灸调理耗材",
     * "shopidshow":null,"saleunitidshow":"套","imgurl":null,"imgurlshow":"",
     * "imgurlcompressshow":""}]
     */

    private int err;
    private String msg;
    private String total;
    /**
     * snid : 20170306091000000324
     * shopid : 001
     * num : 1.0
     * matid : WL-20161206-0000682
     * saleunitid : 2015081010341
     * matidshow : 艾灸/针灸调理耗材
     * shopidshow : null
     * saleunitidshow : 套
     * imgurl : null
     * imgurlshow :
     * imgurlcompressshow :
     */

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
        private String snid;
        private String shopid;
        private String num;
        private String matid;
        private String saleunitid;
        private String matidshow;
        private Object shopidshow;
        private String saleunitidshow;
        private Object imgurl;
        private String imgurlshow;
        private String imgurlcompressshow;
        private String standard;

        public String getStandard() {
            return null==standard?"":standard;
        }

        public void setStandard(String standard) {
            this.standard = standard;
        }

        public String getSnid() {
            return snid;
        }

        public void setSnid(String snid) {
            this.snid = snid;
        }

        public String getShopid() {
            return shopid;
        }

        public void setShopid(String shopid) {
            this.shopid = shopid;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getMatid() {
            return matid;
        }

        public void setMatid(String matid) {
            this.matid = matid;
        }

        public String getSaleunitid() {
            return saleunitid;
        }

        public void setSaleunitid(String saleunitid) {
            this.saleunitid = saleunitid;
        }

        public String getMatidshow() {
            return matidshow;
        }

        public void setMatidshow(String matidshow) {
            this.matidshow = matidshow;
        }

        public Object getShopidshow() {
            return shopidshow;
        }

        public void setShopidshow(Object shopidshow) {
            this.shopidshow = shopidshow;
        }

        public String getSaleunitidshow() {
            return saleunitidshow;
        }

        public void setSaleunitidshow(String saleunitidshow) {
            this.saleunitidshow = saleunitidshow;
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
