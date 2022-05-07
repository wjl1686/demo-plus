package com.example.demo.common;


import com.example.demo.common.util.WrapperResponse;

/**
 * Result对象
 *
 * @author gonghs
 * @version 1.0
 * @since 2019-07-27 15:43
 */
public class Result<T> extends WrapperResponse<T> {

    /**
     * 用于调试的信息，可以带有非常详细的业务数据，便于排查错误。
     */
    private String detail;
    /**
     * 调用时长
     */
    private String duration;

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
//        出于安全原因，暂时不通过detail字段暴露异常细节
//        this.detail = detail;
    }

    public boolean isSuccess() {
        return SUCCESS == getCode();
    }

    public boolean isFail() {
        return !isSuccess();
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
