package com.example.demo.common.enums;

/**
 * 药品目录生成状态枚举
 *
 * @author wujlong
 * @date 2019/8/8 14:07
 */
public enum DrugListGenaStasEnum {
    /**
     * 已生成
     */
    GENERATE("1", "已生成"),

    /**
     * 未生成
     */
    NO_GENERATE("0", "未生成");

    private String code;
    private String name;

    DrugListGenaStasEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public static String getName(String code) {
        DrugListGenaStasEnum drugListGenaStasEnum = getOrderState(code);
        return drugListGenaStasEnum != null ? drugListGenaStasEnum.name : "";
    }

    public static DrugListGenaStasEnum getOrderState(String code) {
        for (DrugListGenaStasEnum drugListGenaStasEnum : DrugListGenaStasEnum.values()) {
            if (drugListGenaStasEnum.code.equals(code)) {
                return drugListGenaStasEnum;
            }
        }
        return null;
    }
}
