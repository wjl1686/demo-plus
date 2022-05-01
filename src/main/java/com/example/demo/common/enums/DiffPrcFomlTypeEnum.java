package com.example.demo.common.enums;

/**
 * 差比计算方式: 1自动生成，2手动调整
 *
 * @author wujlong
 * @version V1.0
 * @since 2019-08-13 14:50
 */
public enum DiffPrcFomlTypeEnum {
    SELF_MOTION("1", "自动生成"), HAND_MOVEMENT("2", "手动调整");

    private String code;
    private String name;

    DiffPrcFomlTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static DiffPrcFomlTypeEnum getDiff(String code) {

        for (DiffPrcFomlTypeEnum diffPrcEnum : DiffPrcFomlTypeEnum.values()) {

            if (diffPrcEnum.code.equals(code)) {
                return diffPrcEnum;
            }

        }
        return null;
    }

}
