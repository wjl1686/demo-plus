package com.example.demo.common.enums;

/**
 * 提示信息维护
 **/
public enum DrtTimeStasEnum {
    //0未配置 1未开启 2已结束 3在报价期间
    NO_CONFIG("0", "未配置报价时间"),
    NO_OPEN("1", "开启报价未开启"),
    END("2", "报价时间已结束"),
    NORMAL("3", "在报价时间范围内");

    DrtTimeStasEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String code, msg;


}
