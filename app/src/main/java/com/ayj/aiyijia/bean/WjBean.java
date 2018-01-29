package com.ayj.aiyijia.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/1.
 */
public class WjBean {

    /**
     * err : 0
     * msg :
     * total : 3
     * data : [{"status":"TICKETSTATUS001","shopid":null,"snid":"13942","msnid":"20170221172800000267","btime":"2017-08-29 00:00:00","etime":"2017-09-28 00:00:00","tickettype":"WL-20160913-0000573","ticketusescope":null,"onlinetype":null,"orderid":null,"ticketusescopeshow":null,"ticketlimitcondition":"TICKETLIMITCONDITION001","statusshow":"可用","msnidshow":null,"standard":null,"shopidshow":null,"createtime":"2017-08-29 19:05:35.0","count1":null,"getfrom":"TICKETFROM002","usetime":null,"tickettypeshow":"番茄组合","getfromshow":"春苗打赏","refundid":null,"getfromorderid":null,"ticketclass":null,"ticketclassshow":"","imgurl":"Upload/2017-02-17/20170217141539571.png","imgurlshow":"http://crm.21ga.cn:9000/qmkmimageupload/Upload/2017-02-17/20170217141539571.png","imgurlcompressshow":"http://crm.21ga.cn:9000/qmkmimageupload/CompressImages.aspx?isformalsys=1&filename=Upload/2017-02-17/20170217141539571.png"},{"status":"TICKETSTATUS001","shopid":null,"snid":"13944","msnid":"20170221172800000267","btime":"2017-08-29 00:00:00","etime":"2017-09-28 00:00:00","tickettype":"WL-20160913-0000573","ticketusescope":null,"onlinetype":null,"orderid":null,"ticketusescopeshow":null,"ticketlimitcondition":"TICKETLIMITCONDITION001","statusshow":"可用","msnidshow":null,"standard":null,"shopidshow":null,"createtime":"2017-08-29 19:05:35.0","count1":null,"getfrom":"TICKETFROM002","usetime":null,"tickettypeshow":"番茄组合","getfromshow":"春苗打赏","refundid":null,"getfromorderid":null,"ticketclass":null,"ticketclassshow":"","imgurl":"Upload/2017-02-17/20170217141539571.png","imgurlshow":"http://crm.21ga.cn:9000/qmkmimageupload/Upload/2017-02-17/20170217141539571.png","imgurlcompressshow":"http://crm.21ga.cn:9000/qmkmimageupload/CompressImages.aspx?isformalsys=1&filename=Upload/2017-02-17/20170217141539571.png"},{"status":"TICKETSTATUS001","shopid":null,"snid":"13943","msnid":"20170221172800000267","btime":"2017-08-29 00:00:00","etime":"2017-09-28 00:00:00","tickettype":"WL-20160913-0000573","ticketusescope":null,"onlinetype":null,"orderid":null,"ticketusescopeshow":null,"ticketlimitcondition":"TICKETLIMITCONDITION001","statusshow":"可用","msnidshow":null,"standard":null,"shopidshow":null,"createtime":"2017-08-29 19:05:35.0","count1":null,"getfrom":"TICKETFROM002","usetime":null,"tickettypeshow":"番茄组合","getfromshow":"春苗打赏","refundid":null,"getfromorderid":null,"ticketclass":null,"ticketclassshow":"","imgurl":"Upload/2017-02-17/20170217141539571.png","imgurlshow":"http://crm.21ga.cn:9000/qmkmimageupload/Upload/2017-02-17/20170217141539571.png","imgurlcompressshow":"http://crm.21ga.cn:9000/qmkmimageupload/CompressImages.aspx?isformalsys=1&filename=Upload/2017-02-17/20170217141539571.png"}]
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
         * status : TICKETSTATUS001
         * shopid : null
         * snid : 13942
         * msnid : 20170221172800000267
         * btime : 2017-08-29 00:00:00
         * etime : 2017-09-28 00:00:00
         * tickettype : WL-20160913-0000573
         * ticketusescope : null
         * onlinetype : null
         * orderid : null
         * ticketusescopeshow : null
         * ticketlimitcondition : TICKETLIMITCONDITION001
         * statusshow : 可用
         * msnidshow : null
         * standard : null
         * shopidshow : null
         * createtime : 2017-08-29 19:05:35.0
         * count1 : null
         * getfrom : TICKETFROM002
         * usetime : null
         * tickettypeshow : 番茄组合
         * getfromshow : 春苗打赏
         * refundid : null
         * getfromorderid : null
         * ticketclass : null
         * ticketclassshow :
         * imgurl : Upload/2017-02-17/20170217141539571.png
         * imgurlshow : http://crm.21ga.cn:9000/qmkmimageupload/Upload/2017-02-17/20170217141539571.png
         * imgurlcompressshow : http://crm.21ga.cn:9000/qmkmimageupload/CompressImages.aspx?isformalsys=1&filename=Upload/2017-02-17/20170217141539571.png
         */

        private String status;
        private Object shopid;
        private String snid;
        private String msnid;
        private String btime;
        private String etime;
        private String tickettype;
        private Object ticketusescope;
        private Object onlinetype;
        private Object orderid;
        private Object ticketusescopeshow;
        private String ticketlimitcondition;
        private String statusshow;
        private Object msnidshow;
        private Object standard;
        private Object shopidshow;
        private String createtime;
        private Object count1;
        private String getfrom;
        private Object usetime;
        private String tickettypeshow;
        private String getfromshow;
        private Object refundid;
        private Object getfromorderid;
        private Object ticketclass;
        private String ticketclassshow;
        private String imgurl;
        private String imgurlshow;
        private String imgurlcompressshow;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Object getShopid() {
            return shopid;
        }

        public void setShopid(Object shopid) {
            this.shopid = shopid;
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

        public String getBtime() {
            return btime;
        }

        public void setBtime(String btime) {
            this.btime = btime;
        }

        public String getEtime() {
            return etime;
        }

        public void setEtime(String etime) {
            this.etime = etime;
        }

        public String getTickettype() {
            return tickettype;
        }

        public void setTickettype(String tickettype) {
            this.tickettype = tickettype;
        }

        public Object getTicketusescope() {
            return ticketusescope;
        }

        public void setTicketusescope(Object ticketusescope) {
            this.ticketusescope = ticketusescope;
        }

        public Object getOnlinetype() {
            return onlinetype;
        }

        public void setOnlinetype(Object onlinetype) {
            this.onlinetype = onlinetype;
        }

        public Object getOrderid() {
            return orderid;
        }

        public void setOrderid(Object orderid) {
            this.orderid = orderid;
        }

        public Object getTicketusescopeshow() {
            return ticketusescopeshow;
        }

        public void setTicketusescopeshow(Object ticketusescopeshow) {
            this.ticketusescopeshow = ticketusescopeshow;
        }

        public String getTicketlimitcondition() {
            return ticketlimitcondition;
        }

        public void setTicketlimitcondition(String ticketlimitcondition) {
            this.ticketlimitcondition = ticketlimitcondition;
        }

        public String getStatusshow() {
            return statusshow;
        }

        public void setStatusshow(String statusshow) {
            this.statusshow = statusshow;
        }

        public Object getMsnidshow() {
            return msnidshow;
        }

        public void setMsnidshow(Object msnidshow) {
            this.msnidshow = msnidshow;
        }

        public Object getStandard() {
            return standard;
        }

        public void setStandard(Object standard) {
            this.standard = standard;
        }

        public Object getShopidshow() {
            return shopidshow;
        }

        public void setShopidshow(Object shopidshow) {
            this.shopidshow = shopidshow;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public Object getCount1() {
            return count1;
        }

        public void setCount1(Object count1) {
            this.count1 = count1;
        }

        public String getGetfrom() {
            return getfrom;
        }

        public void setGetfrom(String getfrom) {
            this.getfrom = getfrom;
        }

        public Object getUsetime() {
            return usetime;
        }

        public void setUsetime(Object usetime) {
            this.usetime = usetime;
        }

        public String getTickettypeshow() {
            return tickettypeshow;
        }

        public void setTickettypeshow(String tickettypeshow) {
            this.tickettypeshow = tickettypeshow;
        }

        public String getGetfromshow() {
            return getfromshow;
        }

        public void setGetfromshow(String getfromshow) {
            this.getfromshow = getfromshow;
        }

        public Object getRefundid() {
            return refundid;
        }

        public void setRefundid(Object refundid) {
            this.refundid = refundid;
        }

        public Object getGetfromorderid() {
            return getfromorderid;
        }

        public void setGetfromorderid(Object getfromorderid) {
            this.getfromorderid = getfromorderid;
        }

        public Object getTicketclass() {
            return ticketclass;
        }

        public void setTicketclass(Object ticketclass) {
            this.ticketclass = ticketclass;
        }

        public String getTicketclassshow() {
            return ticketclassshow;
        }

        public void setTicketclassshow(String ticketclassshow) {
            this.ticketclassshow = ticketclassshow;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
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
