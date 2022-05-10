package com.example.demo.common.exception;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.demo.common.util.AjaxObject;
import com.example.demo.common.util.MailSenderUtil;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class MyExceptionHandler {


    @Value("${spring.profiles.active:dev}")
    private String active;
    @Value("${spring.mail.username}")
    private String form;
    @Value("${spring.application.name}")
    private String applicationName;
    @Value("${error.mail}")
    private String[] errorMailTo;

    @Autowired
    private MailSenderUtil mailSenderUtil;

    @ExceptionHandler(value = Exception.class)
    public AjaxObject exceptionHandler(HttpServletRequest request, Exception e) {
        Map<String, Object> model = Maps.newConcurrentMap();
        model.put("path", request.getRequestURI());

        Enumeration<String> headerNames = request.getHeaderNames();
        Map headers = Maps.newHashMap();
        while (headerNames.hasMoreElements()) {
            String header = headerNames.nextElement();
            headers.put(header, request.getHeader(header));
        }
        model.put("headers", JSON.toJSONString(headers));

        Map params = Maps.newHashMap();
        for (String key : request.getParameterMap().keySet()) {
            params.put(key, request.getParameter(key));
        }
        model.put("params", JSON.toJSONString(params));
        model.put("e", e);
        model.put("time", new Date(System.currentTimeMillis()));
        try {
            log.info(request.getRequestURI());
            log.info("正在发送错误通知：" + JSONArray.toJSONString(errorMailTo) + "...");
            mailSenderUtil.sendBathTemplateMail(form, errorMailTo, String.format("%s : %s:服务错误监控", active, applicationName) + "", "errorMail.ftl", model);
            log.info("邮件发送sucess");
        } catch (Exception ex) {
            log.error("邮件发送正在发送错误异常e={}", ex);
        }
        return AjaxObject.error("系统出小差了，稍后再试");
    }

    @ExceptionHandler(value = BusinessException.class)
    public AjaxObject exceptionHandler(BusinessException e) {
        return AjaxObject.error(e.getCode(), e.getMsg());
    }
}
