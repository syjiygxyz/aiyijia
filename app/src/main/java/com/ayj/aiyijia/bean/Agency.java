package com.ayj.aiyijia.bean;

import java.util.List;

/**
 * Created by zht-pc-09 on 2016/9/23.
 */
public class Agency {


    /**
     * err : 0
     * msg : 获取提成信息成功
     * total : 2
     * data : [{"snid":"372","msnid":"20170221164800000266","profittotal":"0.00","profit1":null,"profit2":null,"profit3":null,"monthid":null,"cwsfqr":null,"msnidshow":"杨婉如","pmsnid":"20170221172800000267","createtime":"2017-03-06 09:14:29","pmsnidshow":"123456","agencylevel":"1","msnidimg":"memberhead/2017-03-15/20170315085523996.jpg","pmsnidimg":"memberhead_test/2017-08-09/20170809145912280.jpg","cwsfqrshow":"","cwqrprofit":null,"cwqrtime":null,"imgurl":null,"imgurlshow":"","imgurlcompressshow":""},{"snid":"378","msnid":"20170306000000000269","profittotal":"0.00","profit1":null,"profit2":null,"profit3":null,"monthid":null,"cwsfqr":null,"msnidshow":null,"pmsnid":"20170221162800000263","createtime":"2017-03-06 16:43:09","pmsnidshow":"吴为群","agencylevel":"3","msnidimg":null,"pmsnidimg":"memberhead/2017-06-02/20170602200229232.jpg","cwsfqrshow":"","cwqrprofit":null,"cwqrtime":null,"imgurl":null,"imgurlshow":"","imgurlcompressshow":""}]
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
         * snid : 372
         * msnid : 20170221164800000266
         * profittotal : 0.00
         * profit1 : null
         * profit2 : null
         * profit3 : null
         * monthid : null
         * cwsfqr : null
         * msnidshow : 杨婉如
         * pmsnid : 20170221172800000267
         * createtime : 2017-03-06 09:14:29
         * pmsnidshow : 123456
         * agencylevel : 1
         * msnidimg : memberhead/2017-03-15/20170315085523996.jpg
         * pmsnidimg : memberhead_test/2017-08-09/20170809145912280.jpg
         * cwsfqrshow :
         * cwqrprofit : null
         * cwqrtime : null
         * imgurl : null
         * imgurlshow :
         * imgurlcompressshow :
         */

        private String snid;
        private String msnid;
        private String profittotal;
        private Object profit1;
        private Object profit2;
        private Object profit3;
        private Object monthid;
        private Object cwsfqr;
        private String msnidshow;
        private String pmsnid;
        private String createtime;
        private String pmsnidshow;
        private String agencylevel;
        private String msnidimg;
        private String pmsnidimg;
        private String cwsfqrshow;
        private Object cwqrprofit;
        private Object cwqrtime;
        private Object imgurl;
        private String imgurlshow;
        private String imgurlcompressshow;
        private String pmsnidimgshow;

        public String getPmsnidimgshow() {
            return pmsnidimgshow;
        }

        public void setPmsnidimgshow(String pmsnidimgshow) {
            this.pmsnidimgshow = pmsnidimgshow;
        }

        public String getSnid() {
            return snid;
        }

        public void setSnid(String snid) {
            this.snid = snid;
        }

        public String getMsnid() {
            return msnid;
        }

        public void setMsnid(String msnid) {
            this.msnid = msnid;
        }

        public String getProfittotal() {
            return profittotal;
        }

        public void setProfittotal(String profittotal) {
            this.profittotal = profittotal;
        }

        public Object getProfit1() {
            return profit1;
        }

        public void setProfit1(Object profit1) {
            this.profit1 = profit1;
        }

        public Object getProfit2() {
            return profit2;
        }

        public void setProfit2(Object profit2) {
            this.profit2 = profit2;
        }

        public Object getProfit3() {
            return profit3;
        }

        public void setProfit3(Object profit3) {
            this.profit3 = profit3;
        }

        public Object getMonthid() {
            return monthid;
        }

        public void setMonthid(Object monthid) {
            this.monthid = monthid;
        }

        public Object getCwsfqr() {
            return cwsfqr;
        }

        public void setCwsfqr(Object cwsfqr) {
            this.cwsfqr = cwsfqr;
        }

        public String getMsnidshow() {
            return msnidshow;
        }

        public void setMsnidshow(String msnidshow) {
            this.msnidshow = msnidshow;
        }

        public String getPmsnid() {
            return pmsnid;
        }

        public void setPmsnid(String pmsnid) {
            this.pmsnid = pmsnid;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getPmsnidshow() {
            return pmsnidshow;
        }

        public void setPmsnidshow(String pmsnidshow) {
            this.pmsnidshow = pmsnidshow;
        }

        public String getAgencylevel() {
            return agencylevel;
        }

        public void setAgencylevel(String agencylevel) {
            this.agencylevel = agencylevel;
        }

        public String getMsnidimg() {
            return msnidimg;
        }

        public void setMsnidimg(String msnidimg) {
            this.msnidimg = msnidimg;
        }

        public String getPmsnidimg() {
            return pmsnidimg;
        }

        public void setPmsnidimg(String pmsnidimg) {
            this.pmsnidimg = pmsnidimg;
        }

        public String getCwsfqrshow() {
            return cwsfqrshow;
        }

        public void setCwsfqrshow(String cwsfqrshow) {
            this.cwsfqrshow = cwsfqrshow;
        }

        public Object getCwqrprofit() {
            return cwqrprofit;
        }

        public void setCwqrprofit(Object cwqrprofit) {
            this.cwqrprofit = cwqrprofit;
        }

        public Object getCwqrtime() {
            return cwqrtime;
        }

        public void setCwqrtime(Object cwqrtime) {
            this.cwqrtime = cwqrtime;
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
