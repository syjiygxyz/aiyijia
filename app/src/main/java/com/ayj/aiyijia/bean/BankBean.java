package com.ayj.aiyijia.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/10.
 */
public class BankBean {

    /**
     * err : 0
     * msg :
     * total : 1
     * data : [{"snid":"163","msnid":"20170221164800000266","bankid":"BANK005","bankidshow":"中国农业银行","createtime":"2017-03-02 17:25:20.0","bankcardtype":"BANKCARDTYPE001","bankno":"6228480505666666666","bindingstatus":"SFYX001","bankcardtypeshow":"储蓄卡","bindingstatusshow":"有效","imgurl":null,"imgurlshow":"","imgurlcompressshow":""}]
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
         * snid : 163
         * msnid : 20170221164800000266
         * bankid : BANK005
         * bankidshow : 中国农业银行
         * createtime : 2017-03-02 17:25:20.0
         * bankcardtype : BANKCARDTYPE001
         * bankno : 6228480505666666666
         * bindingstatus : SFYX001
         * bankcardtypeshow : 储蓄卡
         * bindingstatusshow : 有效
         * imgurl : null
         * imgurlshow :
         * imgurlcompressshow :
         */

        private String snid;
        private String msnid;
        private String bankid;
        private String bankidshow;
        private String createtime;
        private String bankcardtype;
        private String bankno;
        private String bindingstatus;
        private String bankcardtypeshow;
        private String bindingstatusshow;
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

        public String getBankid() {
            return bankid;
        }

        public void setBankid(String bankid) {
            this.bankid = bankid;
        }

        public String getBankidshow() {
            return bankidshow;
        }

        public void setBankidshow(String bankidshow) {
            this.bankidshow = bankidshow;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getBankcardtype() {
            return bankcardtype;
        }

        public void setBankcardtype(String bankcardtype) {
            this.bankcardtype = bankcardtype;
        }

        public String getBankno() {
            return bankno;
        }

        public void setBankno(String bankno) {
            this.bankno = bankno;
        }

        public String getBindingstatus() {
            return bindingstatus;
        }

        public void setBindingstatus(String bindingstatus) {
            this.bindingstatus = bindingstatus;
        }

        public String getBankcardtypeshow() {
            return bankcardtypeshow;
        }

        public void setBankcardtypeshow(String bankcardtypeshow) {
            this.bankcardtypeshow = bankcardtypeshow;
        }

        public String getBindingstatusshow() {
            return bindingstatusshow;
        }

        public void setBindingstatusshow(String bindingstatusshow) {
            this.bindingstatusshow = bindingstatusshow;
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
