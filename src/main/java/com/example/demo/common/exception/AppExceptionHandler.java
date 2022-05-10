package com.example.demo.common.exception;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.demo.common.Result;
import com.example.demo.common.enums.ErrorMsg;
import com.example.demo.common.util.MailSenderUtil;
import com.example.demo.common.util.WrapperResponse;
import com.google.common.collect.Maps;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;

//@RestControllerAdvice
public class AppExceptionHandler {

    private Log log = LogFactory.getLog(AppExceptionHandler.class);

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

    /**
     * 处理异常信息，并返回客户端JSON数据
     *
     * @return
     */
    @ExceptionHandler(RestException.class)
    @ResponseBody
    public WrapperResponse restExceptionHandler(HttpServletRequest request, RestException re) {
        Object ex = re.getException();
        log.error(request.getRequestURI());
        if (ex != null) {
            log.error("业务错误：错误代码 " + re.getState() + ", 错误信息 " + re.getMsg() + ", 异常信息 " + re.getException().getMessage());
            return Result.error(re.getState(), re.getMsg(), re.getException().getMessage());
        } else {
            log.error("业务错误：错误代码 " + re.getState() + ", 错误信息 " + re.getMsg());
            return Result.error(re.getState(), re.getMsg(), null);
        }
    }

    /**
     * 处理异常信息，并返回客户端JSON数据
     *
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public WrapperResponse exceptionHandler(HttpServletRequest request, RuntimeException e) {

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
            log.info("邮件发送sucess：");
        } catch (Exception ex) {

            log.error("邮件发送正在发送错误异常e={}", ex);
        }
        return Result.error(ErrorMsg.SystemException.state, ErrorMsg.SystemException.msg, null);
    }

}
