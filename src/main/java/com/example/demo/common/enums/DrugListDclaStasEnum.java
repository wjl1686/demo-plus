package com.example.demo.common.enums;

/**
 * @author wujlong
 * @date 2019/8/13 13:36
 */
public enum DrugListDclaStasEnum {

    NO_DCLA(0, "未报名"),
    DCLA(1, "已报名");

    public int code;
    public String name;

    DrugListDclaStasEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getName(int code) {
        DrugListDclaStasEnum item = getDrugDclaEnum(code);
        return item != null ? item.name : "";
    }

    public static DrugListDclaStasEnum getDrugDclaEnum(int code) {
        for (DrugListDclaStasEnum drugDclaEnum : DrugListDclaStasEnum.values()) {
            if (drugDclaEnum.code == code) {
                return drugDclaEnum;
            }
        }
        return null;
    }
}


