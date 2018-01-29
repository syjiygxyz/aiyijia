package com.ayj.aiyijia.bean;

import java.util.List;

/**
 * Created by zht-pc-09 on 2016/9/1.
 */
public class Problem {
    /**
     * err : 0
     * msg :
     * total : 3
     * data : [{"answer":null,"currentColomu":null,"currentpage":null,"did":"","dname":"",
     * "endrow":18,"msnid":null,"pagermethod":null,"pagesize":null,"question":"您的小学名字？",
     * "snid":"0","sortChoose":"desc","startrow":0},{"answer":null,"currentColomu":null,
     * "currentpage":null,"did":"","dname":"","endrow":18,"msnid":null,"pagermethod":null,
     * "pagesize":null,"question":"您的初中班主任？","snid":"1","sortChoose":"desc","startrow":0},
     * {"answer":null,"currentColomu":null,"currentpage":null,"did":"","dname":"","endrow":18,
     * "msnid":null,"pagermethod":null,"pagesize":null,"question":"您的初恋？","snid":"21",
     * "sortChoose":"desc","startrow":0}]
     */

    private int err;
    private String msg;
    private String total;
    /**
     * answer : null
     * currentColomu : null
     * currentpage : null
     * did :
     * dname :
     * endrow : 18
     * msnid : null
     * pagermethod : null
     * pagesize : null
     * question : 您的小学名字？
     * snid : 0
     * sortChoose : desc
     * startrow : 0
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
        private Object answer;
        private Object currentColomu;
        private Object currentpage;
        private String did;
        private String dname;
        private int endrow;
        private Object msnid;
        private Object pagermethod;
        private Object pagesize;
        private String question;
        private String snid;
        private String sortChoose;
        private int startrow;

        public Object getAnswer() {
            return answer;
        }

        public void setAnswer(Object answer) {
            this.answer = answer;
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

        public Object getMsnid() {
            return msnid;
        }

        public void setMsnid(Object msnid) {
            this.msnid = msnid;
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

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
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
