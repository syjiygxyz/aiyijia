package com.ayj.aiyijia.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/10.
 */
public class ChangeMoneyBean {

    /**
     * err : 0
     * msg :
     * total : 12
     * data : [{"snid":"1995","msnid":"20170221164800000266","orderid":null,"createdate":"2017-08-10 13:37:49","msnidshow":"杨婉如","beforenum":"52162.00","changenum":"+1","afternum":"52163.00","hiscomment":"特殊体验会员，赠送：1个电子币","imgurl":null,"imgurlshow":"","imgurlcompressshow":""},{"snid":"1994","msnid":"20170221164800000266","orderid":null,"createdate":"2017-08-10 10:31:34","msnidshow":"杨婉如","beforenum":"52161.00","changenum":"+1","afternum":"52162.00","hiscomment":"特殊体验会员，赠送：1个电子币","imgurl":null,"imgurlshow":"","imgurlcompressshow":""},{"snid":"1993","msnid":"20170221164800000266","orderid":null,"createdate":"2017-08-10 09:27:19","msnidshow":"杨婉如","beforenum":"52160.00","changenum":"+1","afternum":"52161.00","hiscomment":"特殊体验会员，赠送：1个电子币","imgurl":null,"imgurlshow":"","imgurlcompressshow":""},{"snid":"1989","msnid":"20170221164800000266","orderid":"20170721141200010288","createdate":"2017-07-21 14:12:07","msnidshow":"杨婉如","beforenum":"53940.00","changenum":"-1780.00","afternum":"52160.00","hiscomment":"兑换，订单编号为20170721141200010288，扣除1780.0个电子币","imgurl":null,"imgurlshow":"","imgurlcompressshow":""},{"snid":"1986","msnid":"20170221164800000266","orderid":"20170719154000010120","createdate":"2017-07-19 15:40:52","msnidshow":"杨婉如","beforenum":"54440.00","changenum":"-500.00","afternum":"53940.00","hiscomment":"兑换，订单编号为20170719154000010120，扣除500.0个电子币","imgurl":null,"imgurlshow":"","imgurlcompressshow":""},{"snid":"1985","msnid":"20170221164800000266","orderid":"20170719154000010119","createdate":"2017-07-19 15:40:06","msnidshow":"杨婉如","beforenum":"54940.00","changenum":"-500.00","afternum":"54440.00","hiscomment":"兑换，订单编号为20170719154000010119，扣除500.0个电子币","imgurl":null,"imgurlshow":"","imgurlcompressshow":""},{"snid":"1984","msnid":"20170221164800000266","orderid":"20170719154000010118","createdate":"2017-07-19 15:40:03","msnidshow":"杨婉如","beforenum":"55440.00","changenum":"-500.00","afternum":"54940.00","hiscomment":"兑换，订单编号为20170719154000010118，扣除500.0个电子币","imgurl":null,"imgurlshow":"","imgurlcompressshow":""},{"snid":"1983","msnid":"20170221164800000266","orderid":"20170719153900010117","createdate":"2017-07-19 15:39:48","msnidshow":"杨婉如","beforenum":"55940.00","changenum":"-500.00","afternum":"55440.00","hiscomment":"兑换，订单编号为20170719153900010117，扣除500.0个电子币","imgurl":null,"imgurlshow":"","imgurlcompressshow":""},{"snid":"1982","msnid":"20170221164800000266","orderid":"20170719153900010113","createdate":"2017-07-19 15:39:16","msnidshow":"杨婉如","beforenum":"56440.00","changenum":"-500.00","afternum":"55940.00","hiscomment":"兑换，订单编号为20170719153900010113，扣除500.0个电子币","imgurl":null,"imgurlshow":"","imgurlcompressshow":""},{"snid":"1976","msnid":"20170221164800000266","orderid":"20170707112900009765","createdate":"2017-07-07 11:29:38","msnidshow":"杨婉如","beforenum":"58220.00","changenum":"-1780.00","afternum":"56440.00","hiscomment":"兑换【血红素系列】力血康咀嚼片孕妇装（20片/盒），扣除1780.00个电子币","imgurl":null,"imgurlshow":"","imgurlcompressshow":""},{"snid":"1975","msnid":"20170221164800000266","orderid":"20170707112900009764","createdate":"2017-07-07 11:29:24","msnidshow":"杨婉如","beforenum":"60000.00","changenum":"-1780.00","afternum":"58220.00","hiscomment":"兑换【血红素系列】力血康咀嚼片孕妇装（20片/盒），扣除1780.00个电子币","imgurl":null,"imgurlshow":"","imgurlcompressshow":""},{"snid":"1918","msnid":"20170221164800000266","orderid":"20170617092300009619","createdate":"2017-06-17 09:23:00","msnidshow":"杨婉如","beforenum":"662.00","changenum":"-500.00","afternum":"162.00","hiscomment":"兑换养生足贴，扣除500.00个电子币","imgurl":null,"imgurlshow":"","imgurlcompressshow":""}]
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
         * snid : 1995
         * msnid : 20170221164800000266
         * orderid : null
         * createdate : 2017-08-10 13:37:49
         * msnidshow : 杨婉如
         * beforenum : 52162.00
         * changenum : +1
         * afternum : 52163.00
         * hiscomment : 特殊体验会员，赠送：1个电子币
         * imgurl : null
         * imgurlshow :
         * imgurlcompressshow :
         */

        private String snid;
        private String msnid;
        private Object orderid;
        private String createdate;
        private String msnidshow;
        private String beforenum;
        private String changenum;
        private String afternum;
        private String hiscomment;
        private Object imgurl;
        private String imgurlshow;
        private String imgurlcompressshow;

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

        public Object getOrderid() {
            return orderid;
        }

        public void setOrderid(Object orderid) {
            this.orderid = orderid;
        }

        public String getCreatedate() {
            return createdate;
        }

        public void setCreatedate(String createdate) {
            this.createdate = createdate;
        }

        public String getMsnidshow() {
            return msnidshow;
        }

        public void setMsnidshow(String msnidshow) {
            this.msnidshow = msnidshow;
        }

        public String getBeforenum() {
            return beforenum;
        }

        public void setBeforenum(String beforenum) {
            this.beforenum = beforenum;
        }

        public String getChangenum() {
            return changenum;
        }

        public void setChangenum(String changenum) {
            this.changenum = changenum;
        }

        public String getAfternum() {
            return afternum;
        }

        public void setAfternum(String afternum) {
            this.afternum = afternum;
        }

        public String getHiscomment() {
            return hiscomment;
        }

        public void setHiscomment(String hiscomment) {
            this.hiscomment = hiscomment;
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
