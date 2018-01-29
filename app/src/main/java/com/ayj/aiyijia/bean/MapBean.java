package com.ayj.aiyijia.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/22.
 */
public class MapBean {

    /**
     * err : 0
     * msg :
     * total : 2
     * data : [{"sid":"002","deptid":null,"pid":null,"tel":"18662433999","areaidshow":"/江苏省/苏州市/相城区","shoplevel":"SHOPLEVEL001","imgurl":"Upload/,Upload/2017-01-14/20170114114023972.png","shbz":null,"areaid":"32057","addr":"苏州市相城区北桥镇","isjmd":null,"imgurlshow":"http://crm.21ga.cn:9000/qmkmimageupload/Upload/,http://crm.21ga.cn:9000/qmkmimageupload/Upload/2017-01-14/20170114114023972.png","imgurlcompressshow":"http://crm.21ga.cn:9000/qmkmimageupload/CompressImages.aspx?isformalsys=1&filename=Upload/,http://crm.21ga.cn:9000/qmkmimageupload/CompressImages.aspx?isformalsys=1&filename=Upload/2017-01-14/20170114114023972.png","modifyuserid":null,"modifyuseridshow":"","modifydate":null,"deptidshow":"","pf":"5","pfcount":"1","isayjstore":"SFPD001","isayjstoreshow":"是","shopcomment":null,"shbzshow":"","area":null,"ayjstoretype":null,"socialstoretype":null,"director":"吴为群","yt":null,"dividendrate":null,"ayjstoretypeshow":"","socialstoretypeshow":"","ytshow":"","shopname":"盛北社区服务点","gpsx":"120.638726","gpsy":"31.271529","distance":"7100.9157","workbtime":"08:30","worketime":"20:00","jmdtype":null,"isjmdhot":null,"isjmdshow":"","jmdtypeshow":"","isjmdhotshow":"","shoplevelshow":"1类春苗点","introductionimgurl":null,"introductionimgurlshow":"","introductionimgurlcompressshow":"","introductionimgurltitle":"","ayjshopinvestorclass":null,"ayjshopinvestorclassshow":""},{"sid":"001","deptid":null,"pid":null,"tel":"18662433999","areaidshow":"/江苏省/苏州市/相城区","shoplevel":"SHOPLEVEL001","imgurl":"Upload/2017-02-27/20170227113133039.png,Upload/2017-02-27/20170227113108776.png,Upload/2017-02-27/20170227113025746.png","shbz":null,"areaid":"32057","addr":"苏州市相城区澄阳路2999号朗悦湾","isjmd":null,"imgurlshow":"http://crm.21ga.cn:9000/qmkmimageupload/Upload/2017-02-27/20170227113133039.png,http://crm.21ga.cn:9000/qmkmimageupload/Upload/2017-02-27/20170227113108776.png,http://crm.21ga.cn:9000/qmkmimageupload/Upload/2017-02-27/20170227113025746.png","imgurlcompressshow":"http://crm.21ga.cn:9000/qmkmimageupload/CompressImages.aspx?isformalsys=1&filename=Upload/2017-02-27/20170227113133039.png,http://crm.21ga.cn:9000/qmkmimageupload/CompressImages.aspx?isformalsys=1&filename=Upload/2017-02-27/20170227113108776.png,http://crm.21ga.cn:9000/qmkmimageupload/CompressImages.aspx?isformalsys=1&filename=Upload/2017-02-27/20170227113025746.png","modifyuserid":null,"modifyuseridshow":"","modifydate":null,"deptidshow":"","pf":"4.47","pfcount":"39","isayjstore":"SFPD001","isayjstoreshow":"是","shopcomment":null,"shbzshow":"","area":null,"ayjstoretype":null,"socialstoretype":null,"director":"吴为群","yt":null,"dividendrate":null,"ayjstoretypeshow":"","socialstoretypeshow":"","ytshow":"","shopname":"朗悦湾小区服务点","gpsx":"120.666017","gpsy":"31.4604798","distance":"7112.0549","workbtime":"08:30","worketime":"20:00","jmdtype":null,"isjmdhot":null,"isjmdshow":"","jmdtypeshow":"","isjmdhotshow":"","shoplevelshow":"1类春苗点","introductionimgurl":null,"introductionimgurlshow":"","introductionimgurlcompressshow":"","introductionimgurltitle":"","ayjshopinvestorclass":null,"ayjshopinvestorclassshow":""}]
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
         * sid : 002
         * deptid : null
         * pid : null
         * tel : 18662433999
         * areaidshow : /江苏省/苏州市/相城区
         * shoplevel : SHOPLEVEL001
         * imgurl : Upload/,Upload/2017-01-14/20170114114023972.png
         * shbz : null
         * areaid : 32057
         * addr : 苏州市相城区北桥镇
         * isjmd : null
         * imgurlshow : http://crm.21ga.cn:9000/qmkmimageupload/Upload/,http://crm.21ga.cn:9000/qmkmimageupload/Upload/2017-01-14/20170114114023972.png
         * imgurlcompressshow : http://crm.21ga.cn:9000/qmkmimageupload/CompressImages.aspx?isformalsys=1&filename=Upload/,http://crm.21ga.cn:9000/qmkmimageupload/CompressImages.aspx?isformalsys=1&filename=Upload/2017-01-14/20170114114023972.png
         * modifyuserid : null
         * modifyuseridshow :
         * modifydate : null
         * deptidshow :
         * pf : 5
         * pfcount : 1
         * isayjstore : SFPD001
         * isayjstoreshow : 是
         * shopcomment : null
         * shbzshow :
         * area : null
         * ayjstoretype : null
         * socialstoretype : null
         * director : 吴为群
         * yt : null
         * dividendrate : null
         * ayjstoretypeshow :
         * socialstoretypeshow :
         * ytshow :
         * shopname : 盛北社区服务点
         * gpsx : 120.638726
         * gpsy : 31.271529
         * distance : 7100.9157
         * workbtime : 08:30
         * worketime : 20:00
         * jmdtype : null
         * isjmdhot : null
         * isjmdshow :
         * jmdtypeshow :
         * isjmdhotshow :
         * shoplevelshow : 1类春苗点
         * introductionimgurl : null
         * introductionimgurlshow :
         * introductionimgurlcompressshow :
         * introductionimgurltitle :
         * ayjshopinvestorclass : null
         * ayjshopinvestorclassshow :
         */

        private String sid;
        private Object deptid;
        private Object pid;
        private String tel;
        private String areaidshow;
        private String shoplevel;
        private String imgurl;
        private Object shbz;
        private String areaid;
        private String addr;
        private Object isjmd;
        private String imgurlshow;
        private String imgurlcompressshow;
        private Object modifyuserid;
        private String modifyuseridshow;
        private Object modifydate;
        private String deptidshow;
        private String pf;
        private String pfcount;
        private String isayjstore;
        private String isayjstoreshow;
        private Object shopcomment;
        private String shbzshow;
        private Object area;
        private Object ayjstoretype;
        private Object socialstoretype;
        private String director;
        private Object yt;
        private Object dividendrate;
        private String ayjstoretypeshow;
        private String socialstoretypeshow;
        private String ytshow;
        private String shopname;
        private String gpsx;
        private String gpsy;
        private String distance;
        private String workbtime;
        private String worketime;
        private Object jmdtype;
        private Object isjmdhot;
        private String isjmdshow;
        private String jmdtypeshow;
        private String isjmdhotshow;
        private String shoplevelshow;
        private Object introductionimgurl;
        private String introductionimgurlshow;
        private String introductionimgurlcompressshow;
        private String introductionimgurltitle;
        private Object ayjshopinvestorclass;
        private String ayjshopinvestorclassshow;

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        public Object getDeptid() {
            return deptid;
        }

        public void setDeptid(Object deptid) {
            this.deptid = deptid;
        }

        public Object getPid() {
            return pid;
        }

        public void setPid(Object pid) {
            this.pid = pid;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getAreaidshow() {
            return areaidshow;
        }

        public void setAreaidshow(String areaidshow) {
            this.areaidshow = areaidshow;
        }

        public String getShoplevel() {
            return shoplevel;
        }

        public void setShoplevel(String shoplevel) {
            this.shoplevel = shoplevel;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public Object getShbz() {
            return shbz;
        }

        public void setShbz(Object shbz) {
            this.shbz = shbz;
        }

        public String getAreaid() {
            return areaid;
        }

        public void setAreaid(String areaid) {
            this.areaid = areaid;
        }

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public Object getIsjmd() {
            return isjmd;
        }

        public void setIsjmd(Object isjmd) {
            this.isjmd = isjmd;
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

        public Object getModifyuserid() {
            return modifyuserid;
        }

        public void setModifyuserid(Object modifyuserid) {
            this.modifyuserid = modifyuserid;
        }

        public String getModifyuseridshow() {
            return modifyuseridshow;
        }

        public void setModifyuseridshow(String modifyuseridshow) {
            this.modifyuseridshow = modifyuseridshow;
        }

        public Object getModifydate() {
            return modifydate;
        }

        public void setModifydate(Object modifydate) {
            this.modifydate = modifydate;
        }

        public String getDeptidshow() {
            return deptidshow;
        }

        public void setDeptidshow(String deptidshow) {
            this.deptidshow = deptidshow;
        }

        public String getPf() {
            return pf;
        }

        public void setPf(String pf) {
            this.pf = pf;
        }

        public String getPfcount() {
            return pfcount;
        }

        public void setPfcount(String pfcount) {
            this.pfcount = pfcount;
        }

        public String getIsayjstore() {
            return isayjstore;
        }

        public void setIsayjstore(String isayjstore) {
            this.isayjstore = isayjstore;
        }

        public String getIsayjstoreshow() {
            return isayjstoreshow;
        }

        public void setIsayjstoreshow(String isayjstoreshow) {
            this.isayjstoreshow = isayjstoreshow;
        }

        public Object getShopcomment() {
            return shopcomment;
        }

        public void setShopcomment(Object shopcomment) {
            this.shopcomment = shopcomment;
        }

        public String getShbzshow() {
            return shbzshow;
        }

        public void setShbzshow(String shbzshow) {
            this.shbzshow = shbzshow;
        }

        public Object getArea() {
            return area;
        }

        public void setArea(Object area) {
            this.area = area;
        }

        public Object getAyjstoretype() {
            return ayjstoretype;
        }

        public void setAyjstoretype(Object ayjstoretype) {
            this.ayjstoretype = ayjstoretype;
        }

        public Object getSocialstoretype() {
            return socialstoretype;
        }

        public void setSocialstoretype(Object socialstoretype) {
            this.socialstoretype = socialstoretype;
        }

        public String getDirector() {
            return director;
        }

        public void setDirector(String director) {
            this.director = director;
        }

        public Object getYt() {
            return yt;
        }

        public void setYt(Object yt) {
            this.yt = yt;
        }

        public Object getDividendrate() {
            return dividendrate;
        }

        public void setDividendrate(Object dividendrate) {
            this.dividendrate = dividendrate;
        }

        public String getAyjstoretypeshow() {
            return ayjstoretypeshow;
        }

        public void setAyjstoretypeshow(String ayjstoretypeshow) {
            this.ayjstoretypeshow = ayjstoretypeshow;
        }

        public String getSocialstoretypeshow() {
            return socialstoretypeshow;
        }

        public void setSocialstoretypeshow(String socialstoretypeshow) {
            this.socialstoretypeshow = socialstoretypeshow;
        }

        public String getYtshow() {
            return ytshow;
        }

        public void setYtshow(String ytshow) {
            this.ytshow = ytshow;
        }

        public String getShopname() {
            return shopname;
        }

        public void setShopname(String shopname) {
            this.shopname = shopname;
        }

        public String getGpsx() {
            return gpsx;
        }

        public void setGpsx(String gpsx) {
            this.gpsx = gpsx;
        }

        public String getGpsy() {
            return gpsy;
        }

        public void setGpsy(String gpsy) {
            this.gpsy = gpsy;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getWorkbtime() {
            return workbtime;
        }

        public void setWorkbtime(String workbtime) {
            this.workbtime = workbtime;
        }

        public String getWorketime() {
            return worketime;
        }

        public void setWorketime(String worketime) {
            this.worketime = worketime;
        }

        public Object getJmdtype() {
            return jmdtype;
        }

        public void setJmdtype(Object jmdtype) {
            this.jmdtype = jmdtype;
        }

        public Object getIsjmdhot() {
            return isjmdhot;
        }

        public void setIsjmdhot(Object isjmdhot) {
            this.isjmdhot = isjmdhot;
        }

        public String getIsjmdshow() {
            return isjmdshow;
        }

        public void setIsjmdshow(String isjmdshow) {
            this.isjmdshow = isjmdshow;
        }

        public String getJmdtypeshow() {
            return jmdtypeshow;
        }

        public void setJmdtypeshow(String jmdtypeshow) {
            this.jmdtypeshow = jmdtypeshow;
        }

        public String getIsjmdhotshow() {
            return isjmdhotshow;
        }

        public void setIsjmdhotshow(String isjmdhotshow) {
            this.isjmdhotshow = isjmdhotshow;
        }

        public String getShoplevelshow() {
            return shoplevelshow;
        }

        public void setShoplevelshow(String shoplevelshow) {
            this.shoplevelshow = shoplevelshow;
        }

        public Object getIntroductionimgurl() {
            return introductionimgurl;
        }

        public void setIntroductionimgurl(Object introductionimgurl) {
            this.introductionimgurl = introductionimgurl;
        }

        public String getIntroductionimgurlshow() {
            return introductionimgurlshow;
        }

        public void setIntroductionimgurlshow(String introductionimgurlshow) {
            this.introductionimgurlshow = introductionimgurlshow;
        }

        public String getIntroductionimgurlcompressshow() {
            return introductionimgurlcompressshow;
        }

        public void setIntroductionimgurlcompressshow(String introductionimgurlcompressshow) {
            this.introductionimgurlcompressshow = introductionimgurlcompressshow;
        }

        public String getIntroductionimgurltitle() {
            return introductionimgurltitle;
        }

        public void setIntroductionimgurltitle(String introductionimgurltitle) {
            this.introductionimgurltitle = introductionimgurltitle;
        }

        public Object getAyjshopinvestorclass() {
            return ayjshopinvestorclass;
        }

        public void setAyjshopinvestorclass(Object ayjshopinvestorclass) {
            this.ayjshopinvestorclass = ayjshopinvestorclass;
        }

        public String getAyjshopinvestorclassshow() {
            return ayjshopinvestorclassshow;
        }

        public void setAyjshopinvestorclassshow(String ayjshopinvestorclassshow) {
            this.ayjshopinvestorclassshow = ayjshopinvestorclassshow;
        }
    }
}
