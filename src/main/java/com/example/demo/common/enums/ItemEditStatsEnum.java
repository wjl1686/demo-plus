package com.example.demo.common.enums;

/**
 * 项目内页面信息编辑状态枚举
 */
public enum ItemEditStatsEnum {


    ALL_EDIT("1", "可以编辑项目内外信息"),
    ITEM_EDIT("2", "禁止编辑项目外但可以编辑项目内信息"),
    FORBID_EDIT("3", "项目内外信息都禁止编辑");

    ItemEditStatsEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String code, msg;


}
