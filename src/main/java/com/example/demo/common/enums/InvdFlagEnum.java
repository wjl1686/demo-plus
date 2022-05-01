package com.example.demo.common.enums;

/**
 * 枚举--无效标志
 *
 * @author wujlong
 * @date 2019/11/29 13:36
 */
public enum InvdFlagEnum {

    NOT("0", "否", ""),
    YES("1", "是", "");

    public String code;
    public String name;
    public String desc;

    InvdFlagEnum(String code, String name, String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;
    }

    public static String getName(String code) {
        InvdFlagEnum item = getInvdFlagEnum(code);
        return item != null ? item.name : "";
    }

    public static InvdFlagEnum getInvdFlagEnum(String code) {
        for (InvdFlagEnum invdFlagEnum : InvdFlagEnum.values()) {
            if (invdFlagEnum.code.equals(code)) {
                return invdFlagEnum;
            }
        }
        return null;
    }

    public static String getCodeByName(String name) {
        for (InvdFlagEnum invdFlagEnum : InvdFlagEnum.values()) {
            if (invdFlagEnum.name.equals(name)) {
                return invdFlagEnum.code;
            }
        }
        return "";
    }

}


