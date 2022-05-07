package com.example.demo.common;

/**
 * 异常码
 *
 * @author wujlong
 * @version 1.0
 * @since 2022-05-07 14:01
 */
public enum ErrorCode {
    // 未知的系统错误
    UNKNOWN_ERROR(160001, "未知错误"),
    VALIDATION_ERROR(160002, "参数错误"),
    SERVICE_ERROR(160003, "服务异常");

    /**
     * 响应码
     */
    private final int code;

    /**
     * 响应消息
     */
    private final String message;

    private ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
