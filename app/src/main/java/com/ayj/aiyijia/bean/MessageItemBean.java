package com.ayj.aiyijia.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/30.
 */
public class MessageItemBean {

    /**
     * err : 0
     * msg : 获取app消息通知成功
     * total : 1
     * data : [{"userid":"20170221172800000267","snid":"2","acomment":"acomment","createtime":"2017-10-03 00:00:00","appsnid":"1","msgtype":"APPNOTICEMSGTYPE001","hasnotice":"SFPD001","hasread":"SFPD001","noticetime":null,"readtime":null,"imgurl":null,"imgurlshow":"","imgurlcompressshow":""}]
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
         * userid : 20170221172800000267
         * snid : 2
         * acomment : acomment
         * createtime : 2017-10-03 00:00:00
         * appsnid : 1
         * msgtype : APPNOTICEMSGTYPE001
         * hasnotice : SFPD001
         * hasread : SFPD001
         * noticetime : null
         * readtime : null
         * imgurl : null
         * imgurlshow :
         * imgurlcompressshow :
         */

        private String userid;
        private String snid;
        private String acomment;
        private String createtime;
        private String appsnid;
        private String msgtype;
        private String hasnotice;
        private String hasread;
        private Object noticetime;
        private Object readtime;
        private Object imgurl;
        private String imgurlshow;
        private String imgurlcompressshow;

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getSnid() {
            return snid;
        }

        public void setSnid(String snid) {
            this.snid = snid;
        }

        public String getAcomment() {
            return acomment;
        }

        public void setAcomment(String acomment) {
            this.acomment = acomment;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getAppsnid() {
            return appsnid;
        }

        public void setAppsnid(String appsnid) {
            this.appsnid = appsnid;
        }

        public String getMsgtype() {
            return msgtype;
        }

        public void setMsgtype(String msgtype) {
            this.msgtype = msgtype;
        }

        public String getHasnotice() {
            return hasnotice;
        }

        public void setHasnotice(String hasnotice) {
            this.hasnotice = hasnotice;
        }

        public String getHasread() {
            return hasread;
        }

        public void setHasread(String hasread) {
            this.hasread = hasread;
        }

        public Object getNoticetime() {
            return noticetime;
        }

        public void setNoticetime(Object noticetime) {
            this.noticetime = noticetime;
        }

        public Object getReadtime() {
            return readtime;
        }

        public void setReadtime(Object readtime) {
            this.readtime = readtime;
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
