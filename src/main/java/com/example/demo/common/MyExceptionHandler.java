package com.example.demo.common;

import com.example.demo.common.util.AjaxObject;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(value =Exception.class)
    public AjaxObject exceptionHandler(Exception e){
        return AjaxObject.error("系统出小差了，稍后再试");
    }
    @ExceptionHandler(value =BusinessException.class)
    public AjaxObject exceptionHandler(BusinessException e){
        return AjaxObject.error(e.getCode(),e.getMsg());
    }
}
