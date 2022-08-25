package com.sang.common.constants;

/**
 * 正则
 */
public final class RegexConst {

    private RegexConst() {};

    // "网址URL（不带http协议）"
    public static final String URL = "/^(((ht|f)tps?):\\/\\/)?([^!@#$%^&*?.\\s-]([^!@#$%^&*?.\\s]{0,63}[^!@#$%^&*?.\\s])?\\.)+[a-z]{2,6}\\/?/";
    // "邮件"
    public static final String EMAIL = "/^(([^<>()[\\]\\.,;:\\s@\"]+(\\.[^<>()[\\]\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$/";
    // "手机号校验 （宽松 13-19 开头即可）"
    public static final String MOBILE_PHONE = "/^(?:(?:\\+|00)86)?1[3-9]\\d{9}$/";
}
