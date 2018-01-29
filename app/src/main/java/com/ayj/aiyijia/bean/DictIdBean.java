package com.ayj.aiyijia.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/25.
 */
public class DictIdBean {

    /**
     * err : 0
     * msg :
     * total : 2
     * data : [{"orderno":"4","paramname":"销售商品","dictid":"SHOPPINGMALLCLSONE004","dicttypeid":null,"paramremark":null,"canmodify":null,"delflag":null,"parentid":null,"mark":null,"imgurl":null,"imgurlshow":"","imgurlcompressshow":""},{"orderno":"5","paramname":"体验商品","dictid":"SHOPPINGMALLCLSONE005","dicttypeid":null,"paramremark":null,"canmodify":null,"delflag":null,"parentid":null,"mark":null,"imgurl":null,"imgurlshow":"","imgurlcompressshow":""}]
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
         * orderno : 4
         * paramname : 销售商品
         * dictid : SHOPPINGMALLCLSONE004
         * dicttypeid : null
         * paramremark : null
         * canmodify : null
         * delflag : null
         * parentid : null
         * mark : null
         * imgurl : null
         * imgurlshow :
         * imgurlcompressshow :
         */

        private String orderno;
        private String paramname;
        private String dictid;
        private Object dicttypeid;
        private Object paramremark;
        private Object canmodify;
        private Object delflag;
        private Object parentid;
        private Object mark;
        private Object imgurl;
        private String imgurlshow;
        private String imgurlcompressshow;

        public String getOrderno() {
            return orderno;
        }

        public void setOrderno(String orderno) {
            this.orderno = orderno;
        }

        public String getParamname() {
            return paramname;
        }

        public void setParamname(String paramname) {
            this.paramname = paramname;
        }

        public String getDictid() {
            return dictid;
        }

        public void setDictid(String dictid) {
            this.dictid = dictid;
        }

        public Object getDicttypeid() {
            return dicttypeid;
        }

        public void setDicttypeid(Object dicttypeid) {
            this.dicttypeid = dicttypeid;
        }

        public Object getParamremark() {
            return paramremark;
        }

        public void setParamremark(Object paramremark) {
            this.paramremark = paramremark;
        }

        public Object getCanmodify() {
            return canmodify;
        }

        public void setCanmodify(Object canmodify) {
            this.canmodify = canmodify;
        }

        public Object getDelflag() {
            return delflag;
        }

        public void setDelflag(Object delflag) {
            this.delflag = delflag;
        }

        public Object getParentid() {
            return parentid;
        }

        public void setParentid(Object parentid) {
            this.parentid = parentid;
        }

        public Object getMark() {
            return mark;
        }

        public void setMark(Object mark) {
            this.mark = mark;
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
