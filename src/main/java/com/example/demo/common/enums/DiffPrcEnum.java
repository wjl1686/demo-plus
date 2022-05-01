package com.example.demo.common.enums;

/**
 * 差比价枚举
 *
 * @author wujlong
 * @version V1.0
 * @since 2019-08-13 14:50
 */
public enum DiffPrcEnum {
    CONTENT_DIFF(1, "price*Math.pow(1.7, Math.log(repContent/noRepContent)/Math.log(2))",
            "含量差比"),
    QUANTITY_DIFF(2, "price*Math.pow(1.9, Math.log(repQuantity/noRepQuantity)/Math.log(2))",
            "装量差比"),
    INJECTION_DIFF(3,
            "(price*Math.pow(1.7, Math.log(repContent/noRepContent)/Math.log(2)))+injection",
            "注射剂含量差比"),

    DAYUSE_DIFF(4, "price*dayuse", "日平均治疗费用差比价");

    private Integer code;
    private String calfoml;
    private String name;

    DiffPrcEnum(Integer code, String calfoml, String name) {
        this.code = code;
        this.calfoml = calfoml;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getCalfoml() {
        return calfoml;
    }

    public String getName() {
        return name;
    }

    public static String getCalfoml(Integer code) {
        DiffPrcEnum diffPrcEnum = getDiff(code);

        return diffPrcEnum == null ? null : diffPrcEnum.calfoml;

    }

    public static DiffPrcEnum getDiff(Integer code) {

        for (DiffPrcEnum diffPrcEnum : DiffPrcEnum.values()) {

            if (diffPrcEnum.code.equals(code)) {
                return diffPrcEnum;
            }

        }
        return null;
    }

}
