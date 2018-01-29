package com.ayj.aiyijia.bean;

/**
 * Created by Administrator on 2017/9/12.
 */
public class FxBean {

    /**
     * err : 0
     * msg : 会员赠送礼金成功
     * total : 1
     * data : {"html5url":"http://crm.21ga.cn:9000/wbeH5/coupon/reward/cashgift.html?snid=2017091217150000000070"}
     */

    private int err;
    private String msg;
    private String total;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * html5url : http://crm.21ga.cn:9000/wbeH5/coupon/reward/cashgift.html?snid=2017091217150000000070
         */

        private String html5url;

        public String getHtml5url() {
            return html5url;
        }

        public void setHtml5url(String html5url) {
            this.html5url = html5url;
        }
    }
}
