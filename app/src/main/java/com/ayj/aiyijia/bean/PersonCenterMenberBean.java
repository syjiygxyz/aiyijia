package com.ayj.aiyijia.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/30.
 */
public class PersonCenterMenberBean {

    /**
     * err : 0
     * msg : 获取会员个人中心的账户信息成功
     * total : 1
     * data : [{"name":"杨婉如","snid":"20170221164800000266","smallmoney":"35063.43","sfyx":"SFYX001","createuserid":null,"regshopid":null,"mobile":"18051109195","commissionmoney":"1970.00","totalemoney":"51907.00","totalgoldmoney":null,"membertype":null,"sex":"SEX001","idcard":"320722199612155120","birthday":"2012-08-08","createdate":"2017-02-21 16:48:41","memberlevel":null,"smallmoneyisopen":"SFPD001","nickname":"123","modifyuserid":null,"modifyuseridshow":"","modifydate":null,"email":"790359071@qq.com","sexshow":"男","createuseridshow":"","photoname":"memberhead/2017-03-15/20170315085523996.jpg","sfyxshow":"有效","pwd":null,"registersource":null,"registersourceshow":"","memberlevelshow":"","agencyshbz":null,"agencyshbzshow":"","shopids":null,"regshopidshow":"","haspaypwd":null,"smallmoneyisopenshow":"是","workcomp":null,"residenttype":null,"nation":null,"bloodtype":null,"educationtype":null,"professiontype":null,"maritalstatus":null,"medicalpaytype":null,"drugallergyhistype":null,"exposehistype":null,"diseasehistype":null,"surgeryhistype":null,"injuryhistype":null,"transfusionhistype":null,"geneticdiseasetype":null,"disabilitytype":null,"residenttypeshow":"","nationshow":"","bloodtypeshow":"","educationtypeshow":"","professiontypeshow":"","maritalstatusshow":"","medicalpaytypeshow":"","drugallergyhistypeshow":"","exposehistypeshow":"","diseasehistypeshow":"","surgeryhistypeshow":"","injuryhistypeshow":"","transfusionhistypeshow":"","geneticdiseasetypeshow":"","disabilitytypeshow":"","smallmoneylevel":null,"smallmoneylevelrate":null,"smallmoneylevelshow":"","homeaddr":null,"idcardphoto1":null,"idcardphoto2":null,"idcardphoto1compressshow":"","idcardphoto2compressshow":"","photonameshow":"http://crm.21ga.cn:9000/qmkmimageupload/CompressImages.aspx?isformalsys=1&filename=memberhead/2017-03-15/20170315085523996.jpg","membertypeshow":"","imgurl":null,"imgurlshow":"","imgurlcompressshow":""}]
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
         * name : 杨婉如
         * snid : 20170221164800000266
         * smallmoney : 35063.43
         * sfyx : SFYX001
         * createuserid : null
         * regshopid : null
         * mobile : 18051109195
         * commissionmoney : 1970.00
         * totalemoney : 51907.00
         * totalgoldmoney : null
         * membertype : null
         * sex : SEX001
         * idcard : 320722199612155120
         * birthday : 2012-08-08
         * createdate : 2017-02-21 16:48:41
         * memberlevel : null
         * smallmoneyisopen : SFPD001
         * nickname : 123
         * modifyuserid : null
         * modifyuseridshow :
         * modifydate : null
         * email : 790359071@qq.com
         * sexshow : 男
         * createuseridshow :
         * photoname : memberhead/2017-03-15/20170315085523996.jpg
         * sfyxshow : 有效
         * pwd : null
         * registersource : null
         * registersourceshow :
         * memberlevelshow :
         * agencyshbz : null
         * agencyshbzshow :
         * shopids : null
         * regshopidshow :
         * haspaypwd : null
         * smallmoneyisopenshow : 是
         * workcomp : null
         * residenttype : null
         * nation : null
         * bloodtype : null
         * educationtype : null
         * professiontype : null
         * maritalstatus : null
         * medicalpaytype : null
         * drugallergyhistype : null
         * exposehistype : null
         * diseasehistype : null
         * surgeryhistype : null
         * injuryhistype : null
         * transfusionhistype : null
         * geneticdiseasetype : null
         * disabilitytype : null
         * residenttypeshow :
         * nationshow :
         * bloodtypeshow :
         * educationtypeshow :
         * professiontypeshow :
         * maritalstatusshow :
         * medicalpaytypeshow :
         * drugallergyhistypeshow :
         * exposehistypeshow :
         * diseasehistypeshow :
         * surgeryhistypeshow :
         * injuryhistypeshow :
         * transfusionhistypeshow :
         * geneticdiseasetypeshow :
         * disabilitytypeshow :
         * smallmoneylevel : null
         * smallmoneylevelrate : null
         * smallmoneylevelshow :
         * homeaddr : null
         * idcardphoto1 : null
         * idcardphoto2 : null
         * idcardphoto1compressshow :
         * idcardphoto2compressshow :
         * photonameshow : http://crm.21ga.cn:9000/qmkmimageupload/CompressImages.aspx?isformalsys=1&filename=memberhead/2017-03-15/20170315085523996.jpg
         * membertypeshow :
         * imgurl : null
         * imgurlshow :
         * imgurlcompressshow :
         */
        private String name;
        private String snid;
        private String smallmoney;
        private String sfyx;
        private Object createuserid;
        private Object regshopid;
        private String mobile;
        private String commissionmoney;
        private String totalemoney;
        private Object totalgoldmoney;
        private Object membertype;
        private String sex;
        private String idcard;
        private String birthday;
        private String createdate;
        private Object memberlevel;
        private String smallmoneyisopen;
        private String nickname;
        private Object modifyuserid;
        private String modifyuseridshow;
        private Object modifydate;
        private String email;
        private String sexshow;
        private String createuseridshow;
        private String photoname;
        private String sfyxshow;
        private Object pwd;
        private Object registersource;
        private String registersourceshow;
        private String memberlevelshow;
        private Object agencyshbz;
        private String agencyshbzshow;
        private Object shopids;
        private String regshopidshow;
        private Object haspaypwd;
        private String smallmoneyisopenshow;
        private Object workcomp;
        private Object residenttype;
        private Object nation;
        private Object bloodtype;
        private Object educationtype;
        private Object professiontype;
        private Object maritalstatus;
        private Object medicalpaytype;
        private Object drugallergyhistype;
        private Object exposehistype;
        private Object diseasehistype;
        private Object surgeryhistype;
        private Object injuryhistype;
        private Object transfusionhistype;
        private Object geneticdiseasetype;
        private Object disabilitytype;
        private String residenttypeshow;
        private String nationshow;
        private String bloodtypeshow;
        private String educationtypeshow;
        private String professiontypeshow;
        private String maritalstatusshow;
        private String medicalpaytypeshow;
        private String drugallergyhistypeshow;
        private String exposehistypeshow;
        private String diseasehistypeshow;
        private String surgeryhistypeshow;
        private String injuryhistypeshow;
        private String transfusionhistypeshow;
        private String geneticdiseasetypeshow;
        private String disabilitytypeshow;
        private Object smallmoneylevel;
        private Object smallmoneylevelrate;
        private String smallmoneylevelshow;
        private Object homeaddr;
        private Object idcardphoto1;
        private Object idcardphoto2;
        private String idcardphoto1compressshow;
        private String idcardphoto2compressshow;
        private String photonameshow;
        private String membertypeshow;
        private Object imgurl;
        private String imgurlshow;
        private String imgurlcompressshow;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSnid() {
            return snid;
        }

        public void setSnid(String snid) {
            this.snid = snid;
        }

        public String getSmallmoney() {
            return smallmoney;
        }

        public void setSmallmoney(String smallmoney) {
            this.smallmoney = smallmoney;
        }

        public String getSfyx() {
            return sfyx;
        }

        public void setSfyx(String sfyx) {
            this.sfyx = sfyx;
        }

        public Object getCreateuserid() {
            return createuserid;
        }

        public void setCreateuserid(Object createuserid) {
            this.createuserid = createuserid;
        }

        public Object getRegshopid() {
            return regshopid;
        }

        public void setRegshopid(Object regshopid) {
            this.regshopid = regshopid;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getCommissionmoney() {
            return commissionmoney;
        }

        public void setCommissionmoney(String commissionmoney) {
            this.commissionmoney = commissionmoney;
        }

        public String getTotalemoney() {
            return totalemoney;
        }

        public void setTotalemoney(String totalemoney) {
            this.totalemoney = totalemoney;
        }

        public Object getTotalgoldmoney() {
            return totalgoldmoney;
        }

        public void setTotalgoldmoney(Object totalgoldmoney) {
            this.totalgoldmoney = totalgoldmoney;
        }

        public Object getMembertype() {
            return membertype;
        }

        public void setMembertype(Object membertype) {
            this.membertype = membertype;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getCreatedate() {
            return createdate;
        }

        public void setCreatedate(String createdate) {
            this.createdate = createdate;
        }

        public Object getMemberlevel() {
            return memberlevel;
        }

        public void setMemberlevel(Object memberlevel) {
            this.memberlevel = memberlevel;
        }

        public String getSmallmoneyisopen() {
            return smallmoneyisopen;
        }

        public void setSmallmoneyisopen(String smallmoneyisopen) {
            this.smallmoneyisopen = smallmoneyisopen;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getSexshow() {
            return sexshow;
        }

        public void setSexshow(String sexshow) {
            this.sexshow = sexshow;
        }

        public String getCreateuseridshow() {
            return createuseridshow;
        }

        public void setCreateuseridshow(String createuseridshow) {
            this.createuseridshow = createuseridshow;
        }

        public String getPhotoname() {
            return photoname;
        }

        public void setPhotoname(String photoname) {
            this.photoname = photoname;
        }

        public String getSfyxshow() {
            return sfyxshow;
        }

        public void setSfyxshow(String sfyxshow) {
            this.sfyxshow = sfyxshow;
        }

        public Object getPwd() {
            return pwd;
        }

        public void setPwd(Object pwd) {
            this.pwd = pwd;
        }

        public Object getRegistersource() {
            return registersource;
        }

        public void setRegistersource(Object registersource) {
            this.registersource = registersource;
        }

        public String getRegistersourceshow() {
            return registersourceshow;
        }

        public void setRegistersourceshow(String registersourceshow) {
            this.registersourceshow = registersourceshow;
        }

        public String getMemberlevelshow() {
            return memberlevelshow;
        }

        public void setMemberlevelshow(String memberlevelshow) {
            this.memberlevelshow = memberlevelshow;
        }

        public Object getAgencyshbz() {
            return agencyshbz;
        }

        public void setAgencyshbz(Object agencyshbz) {
            this.agencyshbz = agencyshbz;
        }

        public String getAgencyshbzshow() {
            return agencyshbzshow;
        }

        public void setAgencyshbzshow(String agencyshbzshow) {
            this.agencyshbzshow = agencyshbzshow;
        }

        public Object getShopids() {
            return shopids;
        }

        public void setShopids(Object shopids) {
            this.shopids = shopids;
        }

        public String getRegshopidshow() {
            return regshopidshow;
        }

        public void setRegshopidshow(String regshopidshow) {
            this.regshopidshow = regshopidshow;
        }

        public Object getHaspaypwd() {
            return haspaypwd;
        }

        public void setHaspaypwd(Object haspaypwd) {
            this.haspaypwd = haspaypwd;
        }

        public String getSmallmoneyisopenshow() {
            return smallmoneyisopenshow;
        }

        public void setSmallmoneyisopenshow(String smallmoneyisopenshow) {
            this.smallmoneyisopenshow = smallmoneyisopenshow;
        }

        public Object getWorkcomp() {
            return workcomp;
        }

        public void setWorkcomp(Object workcomp) {
            this.workcomp = workcomp;
        }

        public Object getResidenttype() {
            return residenttype;
        }

        public void setResidenttype(Object residenttype) {
            this.residenttype = residenttype;
        }

        public Object getNation() {
            return nation;
        }

        public void setNation(Object nation) {
            this.nation = nation;
        }

        public Object getBloodtype() {
            return bloodtype;
        }

        public void setBloodtype(Object bloodtype) {
            this.bloodtype = bloodtype;
        }

        public Object getEducationtype() {
            return educationtype;
        }

        public void setEducationtype(Object educationtype) {
            this.educationtype = educationtype;
        }

        public Object getProfessiontype() {
            return professiontype;
        }

        public void setProfessiontype(Object professiontype) {
            this.professiontype = professiontype;
        }

        public Object getMaritalstatus() {
            return maritalstatus;
        }

        public void setMaritalstatus(Object maritalstatus) {
            this.maritalstatus = maritalstatus;
        }

        public Object getMedicalpaytype() {
            return medicalpaytype;
        }

        public void setMedicalpaytype(Object medicalpaytype) {
            this.medicalpaytype = medicalpaytype;
        }

        public Object getDrugallergyhistype() {
            return drugallergyhistype;
        }

        public void setDrugallergyhistype(Object drugallergyhistype) {
            this.drugallergyhistype = drugallergyhistype;
        }

        public Object getExposehistype() {
            return exposehistype;
        }

        public void setExposehistype(Object exposehistype) {
            this.exposehistype = exposehistype;
        }

        public Object getDiseasehistype() {
            return diseasehistype;
        }

        public void setDiseasehistype(Object diseasehistype) {
            this.diseasehistype = diseasehistype;
        }

        public Object getSurgeryhistype() {
            return surgeryhistype;
        }

        public void setSurgeryhistype(Object surgeryhistype) {
            this.surgeryhistype = surgeryhistype;
        }

        public Object getInjuryhistype() {
            return injuryhistype;
        }

        public void setInjuryhistype(Object injuryhistype) {
            this.injuryhistype = injuryhistype;
        }

        public Object getTransfusionhistype() {
            return transfusionhistype;
        }

        public void setTransfusionhistype(Object transfusionhistype) {
            this.transfusionhistype = transfusionhistype;
        }

        public Object getGeneticdiseasetype() {
            return geneticdiseasetype;
        }

        public void setGeneticdiseasetype(Object geneticdiseasetype) {
            this.geneticdiseasetype = geneticdiseasetype;
        }

        public Object getDisabilitytype() {
            return disabilitytype;
        }

        public void setDisabilitytype(Object disabilitytype) {
            this.disabilitytype = disabilitytype;
        }

        public String getResidenttypeshow() {
            return residenttypeshow;
        }

        public void setResidenttypeshow(String residenttypeshow) {
            this.residenttypeshow = residenttypeshow;
        }

        public String getNationshow() {
            return nationshow;
        }

        public void setNationshow(String nationshow) {
            this.nationshow = nationshow;
        }

        public String getBloodtypeshow() {
            return bloodtypeshow;
        }

        public void setBloodtypeshow(String bloodtypeshow) {
            this.bloodtypeshow = bloodtypeshow;
        }

        public String getEducationtypeshow() {
            return educationtypeshow;
        }

        public void setEducationtypeshow(String educationtypeshow) {
            this.educationtypeshow = educationtypeshow;
        }

        public String getProfessiontypeshow() {
            return professiontypeshow;
        }

        public void setProfessiontypeshow(String professiontypeshow) {
            this.professiontypeshow = professiontypeshow;
        }

        public String getMaritalstatusshow() {
            return maritalstatusshow;
        }

        public void setMaritalstatusshow(String maritalstatusshow) {
            this.maritalstatusshow = maritalstatusshow;
        }

        public String getMedicalpaytypeshow() {
            return medicalpaytypeshow;
        }

        public void setMedicalpaytypeshow(String medicalpaytypeshow) {
            this.medicalpaytypeshow = medicalpaytypeshow;
        }

        public String getDrugallergyhistypeshow() {
            return drugallergyhistypeshow;
        }

        public void setDrugallergyhistypeshow(String drugallergyhistypeshow) {
            this.drugallergyhistypeshow = drugallergyhistypeshow;
        }

        public String getExposehistypeshow() {
            return exposehistypeshow;
        }

        public void setExposehistypeshow(String exposehistypeshow) {
            this.exposehistypeshow = exposehistypeshow;
        }

        public String getDiseasehistypeshow() {
            return diseasehistypeshow;
        }

        public void setDiseasehistypeshow(String diseasehistypeshow) {
            this.diseasehistypeshow = diseasehistypeshow;
        }

        public String getSurgeryhistypeshow() {
            return surgeryhistypeshow;
        }

        public void setSurgeryhistypeshow(String surgeryhistypeshow) {
            this.surgeryhistypeshow = surgeryhistypeshow;
        }

        public String getInjuryhistypeshow() {
            return injuryhistypeshow;
        }

        public void setInjuryhistypeshow(String injuryhistypeshow) {
            this.injuryhistypeshow = injuryhistypeshow;
        }

        public String getTransfusionhistypeshow() {
            return transfusionhistypeshow;
        }

        public void setTransfusionhistypeshow(String transfusionhistypeshow) {
            this.transfusionhistypeshow = transfusionhistypeshow;
        }

        public String getGeneticdiseasetypeshow() {
            return geneticdiseasetypeshow;
        }

        public void setGeneticdiseasetypeshow(String geneticdiseasetypeshow) {
            this.geneticdiseasetypeshow = geneticdiseasetypeshow;
        }

        public String getDisabilitytypeshow() {
            return disabilitytypeshow;
        }

        public void setDisabilitytypeshow(String disabilitytypeshow) {
            this.disabilitytypeshow = disabilitytypeshow;
        }

        public Object getSmallmoneylevel() {
            return smallmoneylevel;
        }

        public void setSmallmoneylevel(Object smallmoneylevel) {
            this.smallmoneylevel = smallmoneylevel;
        }

        public Object getSmallmoneylevelrate() {
            return smallmoneylevelrate;
        }

        public void setSmallmoneylevelrate(Object smallmoneylevelrate) {
            this.smallmoneylevelrate = smallmoneylevelrate;
        }

        public String getSmallmoneylevelshow() {
            return smallmoneylevelshow;
        }

        public void setSmallmoneylevelshow(String smallmoneylevelshow) {
            this.smallmoneylevelshow = smallmoneylevelshow;
        }

        public Object getHomeaddr() {
            return homeaddr;
        }

        public void setHomeaddr(Object homeaddr) {
            this.homeaddr = homeaddr;
        }

        public Object getIdcardphoto1() {
            return idcardphoto1;
        }

        public void setIdcardphoto1(Object idcardphoto1) {
            this.idcardphoto1 = idcardphoto1;
        }

        public Object getIdcardphoto2() {
            return idcardphoto2;
        }

        public void setIdcardphoto2(Object idcardphoto2) {
            this.idcardphoto2 = idcardphoto2;
        }

        public String getIdcardphoto1compressshow() {
            return idcardphoto1compressshow;
        }

        public void setIdcardphoto1compressshow(String idcardphoto1compressshow) {
            this.idcardphoto1compressshow = idcardphoto1compressshow;
        }

        public String getIdcardphoto2compressshow() {
            return idcardphoto2compressshow;
        }

        public void setIdcardphoto2compressshow(String idcardphoto2compressshow) {
            this.idcardphoto2compressshow = idcardphoto2compressshow;
        }

        public String getPhotonameshow() {
            return photonameshow;
        }

        public void setPhotonameshow(String photonameshow) {
            this.photonameshow = photonameshow;
        }

        public String getMembertypeshow() {
            return membertypeshow;
        }

        public void setMembertypeshow(String membertypeshow) {
            this.membertypeshow = membertypeshow;
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
