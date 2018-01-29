package com.ayj.aiyijia.utils;


import com.ayj.aiyijia.AyjSwApplication;

/**
 * Created by zht-pc-09 on 2017/6/21.
 */
public class WebUtils {

    /**
     * 将请求的url拼接上主机地址
     *
     * @param url 请求的url
     * @return
     */
    public static String getRequestUrl(String url) {
        if (AyjSwApplication.IS_DEBUG) {
            return HOST.concat(url);
        }
        return ZS_HOST.concat(url);
    }
    /**
     * 链接的主机名称
     */
    public static final String HOST = "http://test.21ga.cn:9000/qmkm/myws/";

    public static final String HOST_TWO = AyjSwApplication.IS_DEBUG?"http://crm.21ga.cn:9000/qmkmimageupload/UploadFile.aspx":"http://crm.21ga.cn:9000/qmkmimageupload/UploadFile.aspx";

    public static  final String ZS_HOST = "http://crm.21ga.cn:9000/qmkm/myws/";

    public static  final String BD_HOST = "http://192.168.1.112:8080/qmkm/myws/";

    public static final String FX_URL_IMG = "http://crm.21ga.cn:9000/qmkmimageupload/Upload/2017-08-31/20170831184917231.png";

    public static final String LC_FX_URL = "http://crm.21ga.cn:9000/wbeH5/gift/giftshare.html?snid=";
    /*
    * 登录
    * */
    public static final String LOGN_URL = "getShopownerInfoByMobileAndPwd.action";

    /*
    * 穴位导航
    * */
    public static final String DH_SM = "http://crm.21ga.cn:9000/wbeH5/acupoint/acupointList.html";

    /*
    * 条例指南
    * */
    public static final String TL_ZM = "http://crm.21ga.cn:9000/wbeH5/Health/guideList.html";

    /*
    * 春苗所在春苗店的预约服务情况
    * */
    public static final String YY_URL = "getShopServiceBookListByUserid.action";
    /*
    * 协助签到
    * */
    public static final String QD_URL = "setServiceBookSignin.action";

    //获取门店设备的工作组一级目录
    public static final String SB_GL_URL = "getEqWorkGroupList.action";

    //获取门店设备的工作组二级目录
    public static final String SB_GL_TWO_URL = "getEqListByWorkGroupId.action";

    //修改设备工作组名称
    public static final String CHANGE_SB_URL = "updateEqWorkgroupRef.action";

    //春苗员工信息
    public static final String CM_MENBER_URL = "getEmployeeByShopid.action";

    //预约信息
    public static final String XX_YY = "getShopEmployeeWorkRecordList.action";

    //冻结账号
    public static final String DJ_YG_URL = "setFreezeShopEmployeeAccount.action";

    //修改密码
    public static final String XG_URL = "updateUserpwd.action";

    //门店管理 进货
    public static final String MDGL_URL = "getShopStockInList.action";

    //门店管理 销售
    public static final String XS_URL = "getShopMatUseList.action";

    //门店管理 存量
    public static final String CL_URL = "getShopStockList.action";

    //类似通讯录功能
    public static final String TX_URL = "getShopMemberByUseridAndPwd.action";

    //添加联系人,设置门店与会员关系，即客户圈绑定关系
    public static final String ADD_MENBER_URL = "setShopMember.action";

    //申请体验用户
    public static final String SQ_TY = "setMemberTypeApply.action";

    //送券
    public static final String SEND_QUE = "setShopFreeTicketToMember.action";

    //获取验证码
    public static final String HQ_YZ = "sendSms.action";

    //获取会员密保问题
    public static final String MB_GET ="getMemberOwnSecurityQuestionList.action";

    //会员通过密保找回密码
    public static final String MB_POST ="validateMemberSecurityAnswer.action";

    //邮箱找回
    public static final String YX_ZH = "setMemberNewPwdByEmail.action";

    //手机号注册
    public static final String ZC_URL = "setMemberRegAndCheckSms.action";

    //手机号找回
    public static final String PHONE_GET = "updateMemberPwdByMobile.action";

    //协议
    public static final String XY_URL = "getAgreementBySnid.action";

    //身份证注册
    public static final String SFZ_URL = "setMemberRegForShopHelpReg.action";

    //会员卡
    public static final String GET_HYK = "getMemberCardList.action";

    //约单列表,物料
    public static final String YD_LIST = "getMatinfoListByPS.action";

    //获取体验券
    public static final String GET_TYQ = "getMemberTicketList.action";

    //获取营养吧
    public static final String YYB = "getMembershipCoupons.action";

    //通讯录获取密码
    public static final String GET_ZH_MA = "getMemberInfoByMobileAndPwd.action";

    public static final String GET_MENBER_DETAIL = "getMemberCardUseScopeList.action";//获取会员卡详情

    public static final String SEND_DD = "setMemberComsume.action";//会员下订单

    public static final String XSLU_URL = "getShopOrderListByUseridAndPwd.action";//销售流水

    public static final String XD_EJ_URL = "getShoppingmallClassTwo.action";//获取商城二级分类

    public static final String XD_EJ_URL_ONE = "getShoppingmallClassOne.action";//获取商城二级分类

    public static final String SP_DETAIL_URL = "getShoppingmallList.action";//获取商城分类内商品,服务券

    public static final String FWQ_URL = "getMemberTicketList.action";//获取会员券

    public static final String ZFB_URL = "getAliPaySign.action";//支付宝签名

    public static final String ZFB_TZ = "notify_url_alipay.action";//会员下单通知支付宝通知

    public static final String ZFB_BX = "notify_url_alipay_for_insuranceorder.action";//保险下单支付宝通知

    public static final String WX_XD = "setMemberPayType.action";//微信下单

    public static final String SHOP_DETAIL="getMatinfoFromShoppingmallByMatid.action";//获取商品详情

    public static final String SHOP_DETAIL_ADDRESS ="getMemberAddrList.action";//查询收货地址

    public static final String MENBER_GET_GWC ="getMemberShoppingCartList.action";//会员获取购物车

    public static final String MENBER_XDD ="setEmoneyExchangeOrder.action";//会员兑换下订单

    public static final String GET_VEHICLE_INFO_URL = "getMemberCarList.action";//获取会员车辆信息

    public static final String MENBER_XIA_GWC ="setMemberShoppingCart.action";//下购物车

    public static final String MENBER_XG_GWC ="updateShopShoppingCartNum.action";//修改购物车

    public static final String MENBER_DEL_GWC = "deleteMemberShoppingCart.action";//会员删除购物车

    public static final String GET_DQ = "getAreaListByPid.action";//地区信息

    public static final String ADD_NEW_ADDRESS = "setMemberAddr.action";//新增收货地址

    public static final String GET_INSURANCE_PRICE = "getInsuranceorderCarQuoteList.action";//保险报价反馈

    public static final String YZ_MA = "validateMemberPayPwd.action";//验证会员支付密码

    public static final String ZH_ZF = "getMemberAccountInfo.action";//获取账户支付信息

    public static final String CONFIRM_CXDD_URL = "setInsuranceOrder_car_confirm.action";//车险核保订单

    public static final String UPDATE_SENDTYPE_URL = "updateInsuranceOrdeCarBdSendtype.action";//车险比价单

    public static final String GET_SIGNEDBRAND_URL = "getInsuranceCompBrandListByBrokerid.action";//获取签约的保险公司品牌

    public static final String KL_KMZ = "getMembercardCenterListByClassidAndMatname.action";//康乐卡面值

    public static final String TC_URL = "getTccenterAndSalespromotionList.action";//获取套餐内容

    public static final String GET_FGS_URL = "getInsuranceBrokerListByInsfrom.action";//分公司

    public static final String GET_SIGNEDFGS_URL = "getInsuranceCompListByBrokeridAndInsuranceBrand.action";//获取签约的分公司

    public static final String GET_BXFA_URL = "getInsuranceSchemeListByIcsnid.action";//保险下订单

    public static final String SET_CXDD_URL = "setInsuranceOrder_car.action";//车险下订单

    public static final String POST_IMG = "qmkmimageupload/UploadFile.aspx";//上传图片

    public static final String JK_SHOP = "getShopinfoByShopid.action";//根据shopid获取shop信息

    public static final String BB_GX = "getAppverByAppsnid.action";//版本更新

    public static final String DZ_LQ_PAY = "getUseSmallMoneyDiscount.action";//零钱打折

    public static final String SP_DETAIL ="getShopinfoByShopid.action";//商品详情

    public static final String H5_DETAIL ="http://crm.21ga.cn:9000/wbeH5/serviceIntroduction/serviceIntroduction.html?";//H5界面

    public static final String CH_DETAIL ="getEmployeeByShopid2.action";//获取春苗站点详细信息

    public static final String CH_PJ ="getComsumePjByShopid.action";//获取春苗站点评论

    public static final String YKFX_URL ="getShopAccountBookTotal.action";//阴亏分析

    public static final String YKFX_XE_DETAIL ="getShopAccountBookTotalSalemoneyDetail.action";//阴亏分析总销额明细

    public static final String YKFX_TC_DETAIL ="getShopAccountBookTotalTcDetail.action";//阴亏分析总提成明细

    public static final String YKFX_KZ_DETAIL ="getShopAccountBookTotalPayoutDetail.action";//阴亏分析总开支明细

    public static final String HC_POST = "setShopMatUseInfo.action";//耗材提交

    public static final String JF_XI = "getShopEMoneyList.action";//积分信息

    public static final String KHQ_CZ = "getMemberSmallMoneyFaceCardList.action";//充值

    public static final String CL_FX = "getDictListByDicttypeid.action";//获取分类

    public static final String CL_ZL = "getCarlinesList.action";//获取车辆种类

    public static final String XZ_Cl = "setMemberCar.action";//新增会员车辆信息

    public static final String MY_BX_LIST = "getInsuranceOrderList.action";//获取会员保险订单信息

    public static final String BX_ZF = "setInsuranceOrderPayType.action";//更改保险支付方式

    public static final String CL_XX = "getAllCarlinesListGroupByBrand.action";//车辆信息

    public static final String BX_LIST ="getInsuranceCenterListByClassidAndMatnameAndBrand.action";//保险列表

    public static final String BX_POST ="getInsuranceSchemeListByIcsnid.action";//保险下订单

    public static final String BD_DETAIL ="getMatinfoFromInsurancecenterByMatid.action";//获取保险具体信息

    public static final String BX_FCX ="setInsuranceOrder.action";//获取保险具体信息非车险

    public static final String BX_MAIN_BOTTOM ="getInsuranceCenterAllListGroupByInscentertype.action";//获取保险首页底部信息

    public static final String BDY_MP4="http://crm.21ga.cn:9000/qmkmimageupload/Upload/2016-12-12/20161212163847784.mp4";//北斗仪视频

    public static final String DETALE_CL="deleteMemberCar.action";//删除会员车辆信息

    public static final String XG_XL="updateMemberCar.action";//修改会员车辆信息

    public static final String CX_URL= "getAreaListByPid.action";//车险地址

    public static final String PERSON_AGENCY_APPLY_URL="setAgencyApply.action";//提交代理申请

    public static final String SET_JOIN_APPLY="setJoininApply.action";//提交加盟申请

    public static final String GET_SHOPPING_MALL_LIST="getShoppingmallListForChunmiao.action";

    public static final String CG_XD = "setShopStockOrder.action";//采购下单

    public static final String CG_GWC = "getShopShoppingCartList.action";//采购购物车

    public static final String ADD_GWC = "setShopShoppingCart.action";//加入购物车

    public static final String DELETE_GWC ="deleteShopShoppingCart.action";//删除购物车

    public static final String JM_SH = "getJoininApplyInfoByMobile.action";//加盟审核

    public static final String YY_FWF = "getServiceFeeByShopidAndMatid.action";//服务费

    public static final String MAIN_BANNER = "getMainadList.action";//首页轮播图

    public static final String HY_FK = "setAppBfFeedback.action";//会员信息反馈

    public static final String ABOUT_US = "getAboutus.action";//关于我们

    public static final String XIU_GAI = "updateMemberinfo.action";//修改个人资料

    public static final String XG_PHONE = "setReBindMemberMobile.action";//重新绑定手机号

    public static final String CX_YHK_LE = "getMemberBankCard.action";//查询银行卡

    public static final String JC_BD = "updateMemberBankCardStatus.action";//解除绑定

    public static final String ADD_YHK = "setMemberBankCard.action";//添加银行卡

    public static final String MONEY_HIS  ="getMemberMoneyHisList.action";//个人中心流水

    public static final String GET_YY = "getMemberServiceBookList.action";//预约

    public static final String ENSURE_SERVICE = "setMemberConfirmFinishService.action";//完成预约

    public static final String ORDER_STATUS_DELETE_URL = "setMemberComsumeTuihuo.action";//申请退货

    public static final String DD_XX  ="getMemberComsumeList.action";//获取订单信息

    public static final String QX_DD  ="setMemberComsumeCancel.action";//取消订单

    public static final String DLQ_HQ  ="getRptAgencyProfitByLevelList.action";//获取会员代理圈

    public static final String HY_SJ  = "setAgencyPmsnid.action";//会员维护上级代理

    public static final String JK_JL  = "getEqMemberRecordHealthRecord.action";//健康记录

    public static final String HY_CHANGE  = "updateMemberpwd.action";//会员更改密码

    public static final String ZF_PWD  = "setMemberPayPwd.action";//设置支付密码

    public static final String XG_ZF  = "updateMemberPayPwd.action";//修改支付密码

    public static final String ZH_ZF_ZH  = "resetMemberPaypwd.action";//找回支付密码

    public static final String HY_MB  ="getMemberOwnSecurityQuestionList.action";//获取会员密保问题

    public static final String MB_ALL  ="getMemberSecurityQuestionList.action";//获取所有密保问题

    public static final String BC_MB  ="setMemberSecurityAnswer.action";//保存密保问题

    public static final String DEL_ADD  ="deleteMemberAddr.action";//删除收货地址

    public static final String XG_DD  ="updateMemberAddr.action";//修改收货地址

    public static final String SURE_SH  ="updateMemberComsumeShouhuo.action";//确认收货

    public static final String SH_PJ = "setComsumePj.action";//评价MEMBERORDERSTATUS 009：已收货/已完成MEMBERORDERSTATUS 010

    public static final String DD_DETAILS = "getMemberComsumeDetailList.action";//获取订单详细信息

    public static final String FW_MONEY = "getServiceFeeByShopidAndMatid.action";//获取服务费

    public static final String YJ_TO_QB = "setMemberCommissionMoneyToSmallMoney.action";//佣金转到钱包

    public static final String SHOPPINGMALL_ONE = "getShoppingmallClassOne.action";//获取classone

    public static final String NEAR_ZD = "getShopByGpsxAndGpsy.action";//服务预约附近春苗站点

    public static final String BD_MD = "setMemberRegShopid.action";//会员绑定春苗店

    public static final String GET_CXJ = "getMemberShareUnionSale";//促销卷

    public static final String GET_DH_MONEY = "getMemberEmoneyToSmallMoneyFaceList.action";//兑换零钱

    public static final String GET_MESSAGE = "getAppNoticeList.action";//获取消息通知

    public static final String GET_LXJ = "getMemberShareUnionSale.action";//获取联销卷

    public static final String PERSON_CENTER = "getMemberAccountInfo2.action";//个人中心首页

    public static final String LJ_SON = "setMemberAward.action";//礼金赠送

    public static final String DH_QB = "setMemberEMoneyToSmallMoney.action";//兑换钱包

    public static final String LC_SL = "getMemberGiftDetailList.action";//礼仓送礼列表

    public static final String HY_SL = "setMemberGift.action";//会员送礼
}
