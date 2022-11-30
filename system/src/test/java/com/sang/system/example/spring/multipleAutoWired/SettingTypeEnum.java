package com.sang.system.example.spring.multipleAutoWired;

public enum SettingTypeEnum {

    RECTANGLE("1","Rectangle", "矩形"),

    SQUARE("2","Square", "正方形")
    ;
    public String code;
    //接口的实现类名
    public String implement;
    //备注
    public String desc;

    SettingTypeEnum(String code,String implement, String desc) {
        this.code = code;
        this.implement = implement;
        this.desc = desc;
    }
}