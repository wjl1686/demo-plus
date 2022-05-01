package com.example.demo.common.enums;

/**
 * 枚举--项目附件类型
 *
 * @author wujlong
 * @date 2019/7/29 13:36
 */
public enum ItemAttTypeEnum {

    ANNO("1", "公告"),
    HELP("2", "帮助文档"),
    ITEM("3", "项目附件");

    private String code;
    private String name;

    ItemAttTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public static String getName(String code) {
        ItemAttTypeEnum item = getItemAttType(code);
        return item != null ? item.name : "";
    }

    public static ItemAttTypeEnum getItemAttType(String code) {
        for (ItemAttTypeEnum itemAttTypeEnum : ItemAttTypeEnum.values()) {
            if (itemAttTypeEnum.code.equals(code)) {
                return itemAttTypeEnum;
            }
        }
        return null;
    }
}


