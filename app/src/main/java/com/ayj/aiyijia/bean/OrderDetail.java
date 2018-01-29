package com.ayj.aiyijia.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zht-pc-09 on 2016/9/21.
 */
public class OrderDetail implements Serializable {

    public static final long serialVersionUID = -1982978541338661116L;

    /**
     * err : 0
     * msg :
     * total : 0
     * data : {"orderDiscountList":[{"createtime":null,"currentColomu":null,"currentpage":null,
     * "did":"","discountdesc":"会员零钱全额支付享受折扣","discountmoney":"-0.0","dname":"","endrow":18,
     * "msnid":"20161010140800000176","orderid":"20161226092300006435","pagermethod":null,
     * "pagesize":null,"remark":null,"snid":"209","sortChoose":"desc","startrow":0}],
     * "orderExtrafeeList":[{"createtime":null,"currentColomu":null,"currentpage":null,"did":"",
     * "dname":"","endrow":18,"extrafeetype":"EXTRAFEETYPE001","extrafeetypeshow":"运费","fee":"0",
     * "msnid":"20161010140800000176","orderid":"20161226092300006435","pagermethod":null,
     * "pagesize":null,"remark":null,"snid":"1108","sortChoose":"desc","startrow":0}],
     * "orderDetailList":[{"currentColomu":null,"currentpage":null,
     * "detailsnid":"20161226092300007568","did":"","dname":"","endrow":18,
     * "matimgurl":"2016-11-15/10165_20161115084614000705.png,
     * 2016-11-15/10165_20161115084614000784.png,2016-11-15/10165_20161115084614000864.png",
     * "matimgurlcompressshow":"http://crm.21ga.cn:9000/qmkmimageupload/CompressImages
     * .aspx?size=s&isformalsys=1&filename=2016-11-15/10165_20161115084614000705.png,http://test
     * .21ga.cn:9080/qmkmimageupload/CompressImages
     * .aspx?size=s&isformalsys=1&filename=2016-11-15/10165_20161115084614000784.png,http://test
     * .21ga.cn:9080/qmkmimageupload/CompressImages
     * .aspx?size=s&isformalsys=1&filename=2016-11-15/10165_20161115084614000864.png","num":"1",
     * "pagermethod":null,"pagesize":null,"pid":"WL-20161115-0000661","pidshow":"壮腺寳",
     * "profit":null,"serviceuserid":null,"serviceuseridshow":"","shopid":"3",
     * "shopidshow":"朗悦湾1号春苗网店","shopimgurl":null,"snid":"20161226092300006435",
     * "sortChoose":"desc","startrow":0,"xjmoney":"50.0","yjmoney":"100.0"},
     * {"currentColomu":null,"currentpage":null,"detailsnid":"20161226092300007569","did":"",
     * "dname":"","endrow":18,"matimgurl":"2016-11-15/10165_20161115084729000072.png,
     * 2016-11-15/10165_20161115084729000129.png,2016-11-15/10165_20161115084729000177.png",
     * "matimgurlcompressshow":"http://crm.21ga.cn:9000/qmkmimageupload/CompressImages
     * .aspx?size=s&isformalsys=1&filename=2016-11-15/10165_20161115084729000072.png,http://test
     * .21ga.cn:9080/qmkmimageupload/CompressImages
     * .aspx?size=s&isformalsys=1&filename=2016-11-15/10165_20161115084729000129.png,http://test
     * .21ga.cn:9080/qmkmimageupload/CompressImages
     * .aspx?size=s&isformalsys=1&filename=2016-11-15/10165_20161115084729000177.png","num":"1",
            * "pagermethod":null,"pagesize":null,"pid":"WL-20161115-0000662","pidshow":"养身足贴",
            * "profit":null,"serviceuserid":null,"serviceuseridshow":"","shopid":"3",
            * "shopidshow":"朗悦湾1号春苗网店","shopimgurl":null,"snid":"20161226092300006435",
            * "sortChoose":"desc","startrow":0,"xjmoney":"50.0","yjmoney":"100.0"}],
        * "orderPaytypeList":[{"createtime":null,"currentColomu":null,"currentpage":null,"did":"",
        * "dname":"","endrow":18,"msnid":"20161010140800000176","orderid":"20161226092300006435",
        * "pagermethod":null,"pagesize":null,"paymoney":"60.0","paytype":"PAYTYPE001",
        * "paytyperefsnid":"20161010140800000176","paytypeshow":"会员余额支付","remark":null,"snid":"516",
        * "sortChoose":"desc","startrow":0}]}
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

    public static class DataBean implements Serializable {
        /**
         * createtime : null
         * currentColomu : null
         * currentpage : null
         * did :
         * discountdesc : 会员零钱全额支付享受折扣
         * discountmoney : -0.0
         * dname :
         * endrow : 18
         * msnid : 20161010140800000176
         * orderid : 20161226092300006435
         * pagermethod : null
         * pagesize : null
         * remark : null
         * snid : 209
         * sortChoose : desc
         * startrow : 0
         */

        private List<OrderDiscountListBean> orderDiscountList;
        /**
         * createtime : null
         * currentColomu : null
         * currentpage : null
         * did :
         * dname :
         * endrow : 18
         * extrafeetype : EXTRAFEETYPE001
         * extrafeetypeshow : 运费
         * fee : 0
         * msnid : 20161010140800000176
         * orderid : 20161226092300006435
         * pagermethod : null
         * pagesize : null
         * remark : null
         * snid : 1108
         * sortChoose : desc
         * startrow : 0
         */

        private List<OrderExtrafeeListBean> orderExtrafeeList;
        /**
         * currentColomu : null
         * currentpage : null
         * detailsnid : 20161226092300007568
         * did :
         * dname :
         * endrow : 18
         * matimgurl : 2016-11-15/10165_20161115084614000705.png,
         * 2016-11-15/10165_20161115084614000784.png,2016-11-15/10165_20161115084614000864.png
         * matimgurlcompressshow : http://crm.21ga.cn:9000/qmkmimageupload/CompressImages
         * .aspx?size=s&isformalsys=1&filename=2016-11-15/10165_20161115084614000705.png,
         * http://crm.21ga.cn:9000/qmkmimageupload/CompressImages
         * .aspx?size=s&isformalsys=1&filename=2016-11-15/10165_20161115084614000784.png,
         * http://crm.21ga.cn:9000/qmkmimageupload/CompressImages
         * .aspx?size=s&isformalsys=1&filename=2016-11-15/10165_20161115084614000864.png
         * num : 1
         * pagermethod : null
         * pagesize : null
         * pid : WL-20161115-0000661
         * pidshow : 壮腺寳
         * profit : null
         * serviceuserid : null
         * serviceuseridshow :
         * shopid : 3
         * shopidshow : 朗悦湾1号春苗网店
         * shopimgurl : null
         * snid : 20161226092300006435
         * sortChoose : desc
         * startrow : 0
         * xjmoney : 50.0
         * yjmoney : 100.0
         */

        private List<OrderDetailListBean> orderDetailList;
        /**
         * createtime : null
         * currentColomu : null
         * currentpage : null
         * did :
         * dname :
         * endrow : 18
         * msnid : 20161010140800000176
         * orderid : 20161226092300006435
         * pagermethod : null
         * pagesize : null
         * paymoney : 60.0
         * paytype : PAYTYPE001
         * paytyperefsnid : 20161010140800000176
         * paytypeshow : 会员余额支付
         *
         *
         * remark : null
         * snid : 516
         * sortChoose : desc
         * startrow : 0
         */

        private List<OrderPaytypeListBean> orderPaytypeList;

        public List<OrderDiscountListBean> getOrderDiscountList() {
            return orderDiscountList;
        }

        public void setOrderDiscountList(List<OrderDiscountListBean> orderDiscountList) {
            this.orderDiscountList = orderDiscountList;
        }

        public List<OrderExtrafeeListBean> getOrderExtrafeeList() {
            return orderExtrafeeList;
        }

        public void setOrderExtrafeeList(List<OrderExtrafeeListBean> orderExtrafeeList) {
            this.orderExtrafeeList = orderExtrafeeList;
        }

        public List<OrderDetailListBean> getOrderDetailList() {
            return orderDetailList;
        }

        public void setOrderDetailList(List<OrderDetailListBean> orderDetailList) {
            this.orderDetailList = orderDetailList;
        }

        public List<OrderPaytypeListBean> getOrderPaytypeList() {
            return orderPaytypeList;
        }

        public void setOrderPaytypeList(List<OrderPaytypeListBean> orderPaytypeList) {
            this.orderPaytypeList = orderPaytypeList;
        }

        public static class OrderDiscountListBean implements Serializable {
            private Object createtime;
            private Object currentColomu;
            private Object currentpage;
            private String did;
            private String discountdesc;
            private String discountmoney;
            private String dname;
            private int endrow;
            private String msnid;
            private String orderid;
            private Object pagermethod;
            private Object pagesize;
            private Object remark;
            private String snid;
            private String sortChoose;
            private int startrow;

            public Object getCreatetime() {
                return createtime;
            }

            public void setCreatetime(Object createtime) {
                this.createtime = createtime;
            }

            public Object getCurrentColomu() {
                return currentColomu;
            }

            public void setCurrentColomu(Object currentColomu) {
                this.currentColomu = currentColomu;
            }

            public Object getCurrentpage() {
                return currentpage;
            }

            public void setCurrentpage(Object currentpage) {
                this.currentpage = currentpage;
            }

            public String getDid() {
                return did;
            }

            public void setDid(String did) {
                this.did = did;
            }

            public String getDiscountdesc() {
                return discountdesc;
            }

            public void setDiscountdesc(String discountdesc) {
                this.discountdesc = discountdesc;
            }

            public String getDiscountmoney() {
                return discountmoney;
            }

            public void setDiscountmoney(String discountmoney) {
                this.discountmoney = discountmoney;
            }

            public String getDname() {
                return dname;
            }

            public void setDname(String dname) {
                this.dname = dname;
            }

            public int getEndrow() {
                return endrow;
            }

            public void setEndrow(int endrow) {
                this.endrow = endrow;
            }

            public String getMsnid() {
                return msnid;
            }

            public void setMsnid(String msnid) {
                this.msnid = msnid;
            }

            public String getOrderid() {
                return orderid;
            }

            public void setOrderid(String orderid) {
                this.orderid = orderid;
            }

            public Object getPagermethod() {
                return pagermethod;
            }

            public void setPagermethod(Object pagermethod) {
                this.pagermethod = pagermethod;
            }

            public Object getPagesize() {
                return pagesize;
            }

            public void setPagesize(Object pagesize) {
                this.pagesize = pagesize;
            }

            public Object getRemark() {
                return remark;
            }

            public void setRemark(Object remark) {
                this.remark = remark;
            }

            public String getSnid() {
                return snid;
            }

            public void setSnid(String snid) {
                this.snid = snid;
            }

            public String getSortChoose() {
                return sortChoose;
            }

            public void setSortChoose(String sortChoose) {
                this.sortChoose = sortChoose;
            }

            public int getStartrow() {
                return startrow;
            }

            public void setStartrow(int startrow) {
                this.startrow = startrow;
            }
        }

        public static class OrderExtrafeeListBean implements Serializable {
            private Object createtime;
            private Object currentColomu;
            private Object currentpage;
            private String did;
            private String dname;
            private int endrow;
            private String extrafeetype;
            private String extrafeetypeshow;
            private String fee;
            private String msnid;
            private String orderid;
            private Object pagermethod;
            private Object pagesize;
            private Object remark;
            private String snid;
            private String sortChoose;
            private int startrow;

            public Object getCreatetime() {
                return createtime;
            }

            public void setCreatetime(Object createtime) {
                this.createtime = createtime;
            }

            public Object getCurrentColomu() {
                return currentColomu;
            }

            public void setCurrentColomu(Object currentColomu) {
                this.currentColomu = currentColomu;
            }

            public Object getCurrentpage() {
                return currentpage;
            }

            public void setCurrentpage(Object currentpage) {
                this.currentpage = currentpage;
            }

            public String getDid() {
                return did;
            }

            public void setDid(String did) {
                this.did = did;
            }

            public String getDname() {
                return dname;
            }

            public void setDname(String dname) {
                this.dname = dname;
            }

            public int getEndrow() {
                return endrow;
            }

            public void setEndrow(int endrow) {
                this.endrow = endrow;
            }

            public String getExtrafeetype() {
                return extrafeetype;
            }

            public void setExtrafeetype(String extrafeetype) {
                this.extrafeetype = extrafeetype;
            }

            public String getExtrafeetypeshow() {
                return extrafeetypeshow;
            }

            public void setExtrafeetypeshow(String extrafeetypeshow) {
                this.extrafeetypeshow = extrafeetypeshow;
            }

            public String getFee() {
                return fee;
            }

            public void setFee(String fee) {
                this.fee = fee;
            }

            public String getMsnid() {
                return msnid;
            }

            public void setMsnid(String msnid) {
                this.msnid = msnid;
            }

            public String getOrderid() {
                return orderid;
            }

            public void setOrderid(String orderid) {
                this.orderid = orderid;
            }

            public Object getPagermethod() {
                return pagermethod;
            }

            public void setPagermethod(Object pagermethod) {
                this.pagermethod = pagermethod;
            }

            public Object getPagesize() {
                return pagesize;
            }

            public void setPagesize(Object pagesize) {
                this.pagesize = pagesize;
            }

            public Object getRemark() {
                return remark;
            }

            public void setRemark(Object remark) {
                this.remark = remark;
            }

            public String getSnid() {
                return snid;
            }

            public void setSnid(String snid) {
                this.snid = snid;
            }

            public String getSortChoose() {
                return sortChoose;
            }

            public void setSortChoose(String sortChoose) {
                this.sortChoose = sortChoose;
            }

            public int getStartrow() {
                return startrow;
            }

            public void setStartrow(int startrow) {
                this.startrow = startrow;
            }
        }

        public static class OrderDetailListBean implements Serializable {
            private Object currentColomu;
            private Object currentpage;
            private String detailsnid;
            private String did;
            private String dname;
            private int endrow;
            private String matimgurl;
            private String matimgurlcompressshow;
            private String num;
            private Object pagermethod;
            private Object pagesize;
            private String pid;
            private String pidshow;
            private Object profit;
            private String refundstatus;
            private String refundstatusshow;
            private String sfzp;
            private String sfzpshow;
            private Object serviceuserid;
            private String serviceuseridshow;
            private String shopid;
            private String shopidshow;
            private Object shopimgurl;
            private String snid;
            private String sortChoose;
            private int startrow;
            private String subtotal;
            private String xjmoney;
            private String yjmoney;
            private String haspj;

            public Object getCurrentColomu() {
                return currentColomu;
            }

            public void setCurrentColomu(Object currentColomu) {
                this.currentColomu = currentColomu;
            }

            public Object getCurrentpage() {
                return currentpage;
            }

            public void setCurrentpage(Object currentpage) {
                this.currentpage = currentpage;
            }

            public String getDetailsnid() {
                return detailsnid;
            }

            public void setDetailsnid(String detailsnid) {
                this.detailsnid = detailsnid;
            }

            public String getDid() {
                return did;
            }

            public void setDid(String did) {
                this.did = did;
            }

            public String getDname() {
                return dname;
            }

            public void setDname(String dname) {
                this.dname = dname;
            }

            public int getEndrow() {
                return endrow;
            }

            public void setEndrow(int endrow) {
                this.endrow = endrow;
            }

            public String getMatimgurl() {
                return matimgurl;
            }

            public void setMatimgurl(String matimgurl) {
                this.matimgurl = matimgurl;
            }

            public String getMatimgurlcompressshow() {
                return matimgurlcompressshow;
            }

            public void setMatimgurlcompressshow(String matimgurlcompressshow) {
                this.matimgurlcompressshow = matimgurlcompressshow;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public Object getPagermethod() {
                return pagermethod;
            }

            public void setPagermethod(Object pagermethod) {
                this.pagermethod = pagermethod;
            }

            public Object getPagesize() {
                return pagesize;
            }

            public void setPagesize(Object pagesize) {
                this.pagesize = pagesize;
            }

            public String getPid() {
                return pid;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }

            public String getPidshow() {
                return pidshow;
            }

            public void setPidshow(String pidshow) {
                this.pidshow = pidshow;
            }

            public Object getProfit() {
                return profit;
            }

            public void setProfit(Object profit) {
                this.profit = profit;
            }

            public String getRefundstatus() {
                return refundstatus;
            }

            public void setRefundstatus(String refundstatus) {
                this.refundstatus = refundstatus;
            }

            public String getRefundstatusshow() {
                return refundstatusshow;
            }

            public void setRefundstatusshow(String refundstatusshow) {
                this.refundstatusshow = refundstatusshow;
            }

            public Object getServiceuserid() {
                return serviceuserid;
            }

            public void setServiceuserid(Object serviceuserid) {
                this.serviceuserid = serviceuserid;
            }

            public String getServiceuseridshow() {
                return serviceuseridshow;
            }

            public void setServiceuseridshow(String serviceuseridshow) {
                this.serviceuseridshow = serviceuseridshow;
            }

            public String getShopid() {
                return shopid;
            }

            public void setShopid(String shopid) {
                this.shopid = shopid;
            }

            public String getShopidshow() {
                return shopidshow;
            }

            public void setShopidshow(String shopidshow) {
                this.shopidshow = shopidshow;
            }

            public Object getShopimgurl() {
                return shopimgurl;
            }

            public void setShopimgurl(Object shopimgurl) {
                this.shopimgurl = shopimgurl;
            }

            public String getSnid() {
                return snid;
            }

            public void setSnid(String snid) {
                this.snid = snid;
            }

            public String getSortChoose() {
                return sortChoose;
            }

            public void setSortChoose(String sortChoose) {
                this.sortChoose = sortChoose;
            }

            public int getStartrow() {
                return startrow;
            }

            public void setStartrow(int startrow) {
                this.startrow = startrow;
            }

            public String getXjmoney() {
                return xjmoney;
            }

            public void setXjmoney(String xjmoney) {
                this.xjmoney = xjmoney;
            }

            public String getYjmoney() {
                return yjmoney;
            }

            public void setYjmoney(String yjmoney) {
                this.yjmoney = yjmoney;
            }

            public String getSfzp() {
                return sfzp;
            }

            public void setSfzp(String sfzp) {
                this.sfzp = sfzp;
            }

            public String getSfzpshow() {
                return sfzpshow;
            }

            public void setSfzpshow(String sfzpshow) {
                this.sfzpshow = sfzpshow;
            }

            public String getSubtotal() {
                return subtotal;
            }

            public void setSubtotal(String subtotal) {
                this.subtotal = subtotal;
            }

            public String getHaspj() {
                return haspj;
            }

            public void setHaspj(String haspj) {
                this.haspj = haspj;
            }
        }

        public static class OrderPaytypeListBean implements Serializable {
            private Object createtime;
            private Object currentColomu;
            private Object currentpage;
            private String did;
            private String dname;
            private int endrow;
            private String msnid;
            private String orderid;
            private Object pagermethod;
            private Object pagesize;
            private String paymoney;
            private String paytype;
            private String paytyperefsnid;
            private String paytypeshow;
            private Object remark;
            private String snid;
            private String sortChoose;
            private int startrow;

            public Object getCreatetime() {
                return createtime;
            }

            public void setCreatetime(Object createtime) {
                this.createtime = createtime;
            }

            public Object getCurrentColomu() {
                return currentColomu;
            }

            public void setCurrentColomu(Object currentColomu) {
                this.currentColomu = currentColomu;
            }

            public Object getCurrentpage() {
                return currentpage;
            }

            public void setCurrentpage(Object currentpage) {
                this.currentpage = currentpage;
            }

            public String getDid() {
                return did;
            }

            public void setDid(String did) {
                this.did = did;
            }

            public String getDname() {
                return dname;
            }

            public void setDname(String dname) {
                this.dname = dname;
            }

            public int getEndrow() {
                return endrow;
            }

            public void setEndrow(int endrow) {
                this.endrow = endrow;
            }

            public String getMsnid() {
                return msnid;
            }

            public void setMsnid(String msnid) {
                this.msnid = msnid;
            }

            public String getOrderid() {
                return orderid;
            }

            public void setOrderid(String orderid) {
                this.orderid = orderid;
            }

            public Object getPagermethod() {
                return pagermethod;
            }

            public void setPagermethod(Object pagermethod) {
                this.pagermethod = pagermethod;
            }

            public Object getPagesize() {
                return pagesize;
            }

            public void setPagesize(Object pagesize) {
                this.pagesize = pagesize;
            }

            public String getPaymoney() {
                return paymoney;
            }

            public void setPaymoney(String paymoney) {
                this.paymoney = paymoney;
            }

            public String getPaytype() {
                return paytype;
            }

            public void setPaytype(String paytype) {
                this.paytype = paytype;
            }

            public String getPaytyperefsnid() {
                return paytyperefsnid;
            }

            public void setPaytyperefsnid(String paytyperefsnid) {
                this.paytyperefsnid = paytyperefsnid;
            }

            public String getPaytypeshow() {
                return paytypeshow;
            }

            public void setPaytypeshow(String paytypeshow) {
                this.paytypeshow = paytypeshow;
            }

            public Object getRemark() {
                return remark;
            }

            public void setRemark(Object remark) {
                this.remark = remark;
            }

            public String getSnid() {
                return snid;
            }

            public void setSnid(String snid) {
                this.snid = snid;
            }

            public String getSortChoose() {
                return sortChoose;
            }

            public void setSortChoose(String sortChoose) {
                this.sortChoose = sortChoose;
            }

            public int getStartrow() {
                return startrow;
            }

            public void setStartrow(int startrow) {
                this.startrow = startrow;
            }
        }
    }
}
