package com.example.demo.common;


import org.springframework.util.ObjectUtils;

/**
 * Result工具类，用于返回Result对象
 *
 * @author wujllong
 * @version 1.0
 * @since 2019-07-27 15:53
 */
public final class Results {

    /**
     * 成功
     *
     * @return Result<Void>
     */
    public static Result<Void> success() {
        Result result = new Result<Void>();
        result.setCode(Result.SUCCESS);
        return result;
    }

    /**
     * 成功
     *
     * @param data 并设置data参数
     * @param <T>  data的泛型
     * @return Result<T>
     */
    public static <T> Result<T> success(T data) {
        Result result = new Result<Void>();
        result.setCode(Result.SUCCESS);
        result.setData(data);
        return result;
    }

    /**
     * 业务异常，code统一使用3
     *
     * @param serviceException 业务异常
     * @param <T>              类型
     * @return result对象
     */
    public static <T> Result<T> failure(ServiceException serviceException) {
        Result result = new Result<Void>();
        result.setCode(ObjectUtils.isEmpty(serviceException.getCode()) ? ErrorCode.SERVICE_ERROR.getCode() : serviceException.getCode());
        result.setMessage(serviceException.getMessage());
        result.setDetail(serviceException.getDetail());
        return result;
    }

    /**
     * 业务异常，code统一使用3
     *
     * @param errorMessage 错误信息
     * @param <T>          类型
     * @return result对象
     */
    public static <T> Result<T> failure(String errorMessage) {
        Result result = new Result<Void>();
        result.setCode(ErrorCode.SERVICE_ERROR.getCode());
        result.setMessage(errorMessage);
        return result;
    }

    /**
     * 业务异常，code统一使用3
     *
     * @param errorMessage 错误信息
     * @param <T>          类型
     * @return result对象
     */
    public static <T> Result<T> failure(String errorMessage, String detail) {
        Result result = new Result<Void>();
        result.setCode(ErrorCode.SERVICE_ERROR.getCode());
        result.setMessage(errorMessage);
        result.setDetail(detail);
        return result;
    }

    /**
     * 返回带异常信息的响应结果，可以自己明确的系统错误
     *
     * @param code    错误编号
     * @param message 错误信息
     * @param <T>     对应data字段的数据类型
     * @return result 对象
     */
    public static <T> Result<T> failure(int code, String message) {
        Result result = new Result();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    /**
     * 失败--针对于资审共享
     *
     * @param data 并设置data参数
     * @param <T>  data的泛型
     * @return Result<T>
     */
    public static <T> Result<T> fail(T data,String message) {
        Result result = new Result<Void>();
        result.setCode(Result.SUCCESS);
        result.setMessage(message);
        result.setData(data);
        return result;
    }
}
