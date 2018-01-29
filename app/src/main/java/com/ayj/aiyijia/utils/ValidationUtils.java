package com.ayj.aiyijia.utils;

import java.util.regex.Pattern;

public class ValidationUtils {

    /**
     * 是否输入为空
     *
     * @param str
     * @return
     */
    public static boolean checkEmpty(String str) {

        if (!"".equals(str))
            return false;

        return true;
    }

    /**
     * 验证用户名是否合法
     *
     * @return
     */
    public static boolean checkUserName(String text) {

        String reg = "[\u4e00-\u9fa5]{2,4}$";

        boolean result = Pattern.compile(reg).matcher(text).find();

        if (!result) {// 验证失败
            return false;
        }

        return true;
    }

    /**
     * 验证手机号码是否合法
     *
     * @param text
     * @return
     */
    public static boolean checkTelPhone(String text) {
        String reg = "^((13[0-9])|(14[0-9])|(15[0-9])|(18[0-9])|(17[0-9]))\\d{8}$";
        boolean result = Pattern.compile(reg).matcher(text).find();
        if (!result) {// 验证失败
            return false;
        }

        return true;
    }
    /**
     * 验证qq号码是否合法
     *
     * @param text
     * @return
     */
    public static boolean checkQQNumber(String text) {
        String reg = "[1-9][0-9]{4,}";

        boolean result = Pattern.compile(reg).matcher(text).find();

        if (!result) {// 验证失败
            return false;
        }

        return true;
    }

    /**
     * 验证邮箱是否合法
     *
     * @param text
     * @return
     */
    public static boolean checkEmail(String text) {
        String reg = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";

        boolean result = Pattern.compile(reg).matcher(text).find();

        if (!result) {// 验证失败
            return false;
        }

        return true;
    }
    /**
     * 验证身份证号码是否合法
     *
     * @param text
     * @return
     */
    public static boolean checkCard(String text) {
        String reg = "[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$";

        boolean result = Pattern.compile(reg).matcher(text).find();

        if (!result) {// 验证失败
            return false;
        }
        return true;
    }

    /**
     * 验证住址是否合法
     *
     * @param text
     * @return
     */
    public static boolean checkAddress(String text) {
        String reg = "^[\\u4E00-\\u9FA5A-Za-z\\d\\-\\_]{5,60}$";

        boolean result = Pattern.compile(reg).matcher(text).find();

        if (!result) {// 验证失败
            return false;
        }

        return true;
    }

    /**
     * 验证邮政编码是否合法
     *
     * @return
     */
    public static boolean checkYb(String text) {
        String reg = "^[1-9]\\d{5}$";

        boolean result = Pattern.compile(reg).matcher(text).find();

        if (!result) {// 验证失败
            return false;
        }

        return true;
    }

    /**
     * 验证昵称是否合法
     *
     * @param text
     * @return
     */
    public static boolean checkNickName(String text) {
        String reg = "[^?!@#$%\\^&*()]+";

        boolean result = Pattern.compile(reg).matcher(text).find();

        if (!result) {// 验证失败
            return false;
        }

        return true;
    }

    /**
     * 验证密码是否合法
     *
     * @param text
     * @return
     */
    public static boolean checkPassWord(String text) {
        String reg = "^([\u4e00-\u9fa5]+|[a-zA-Z0-9]+){6,20}$";

        boolean result = Pattern.compile(reg).matcher(text).find();

        if (!result) {// 验证失败
            return false;
        }

        return true;
    }
    /**
     * 校验银行卡卡号
     * @param cardId
     * @return
     */
    public static boolean checkBankCard(String cardId) {
        char bit = getBankCardCheckCode(cardId.substring(0, cardId.length() - 1));
        if(bit == 'N'){
            return false;
        }
        return cardId.charAt(cardId.length() - 1) == bit;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     * @param nonCheckCodeCardId
     * @return
     */
    public static char getBankCardCheckCode(String nonCheckCodeCardId){
        if(nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
            //如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for(int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if(j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char)((10 - luhmSum % 10) + '0');
    }
}
