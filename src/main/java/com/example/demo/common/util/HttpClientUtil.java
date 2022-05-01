package com.example.demo.common.util;

import com.alibaba.fastjson.JSON;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Objects;

/**
 * http客户端工具类
 *
 * @author wujl
 * @date 2019/12/25 17:08
 */
@Component
@Slf4j
public class HttpClientUtil {

    @Autowired
    private OkHttpClient okHttpClient;

    private MediaType jsonMediaType = MediaType.parse("application/json; charset=utf-8");

    private static int HTTP_OK_CODE = 200;

    /**
     * get请求
     *
     * @param url
     * @return
     */

    @SneakyThrows
    public String get(String url) {
        try {
            return new String(doGet(url), "utf-8");
        } catch (Exception e) {
            log.error("httpGet调用失败。{}", url, e);
            throw e;
        }
    }

    /**
     * get请求，支持添加查询字符串
     *
     * @param url
     * @param queryString 查询字符串
     * @return
     */
    public String get(String url, Map<String, Object> queryString) {
        String fullUrl = buildUrl(url, queryString);
        return get(fullUrl);
    }

    /**
     * 获取json后直接反序列化
     *
     * @param url
     * @param clazz
     * @return
     */

    public <T> T restApiGet(String url, Class<T> clazz) {
        String resp = get(url);
        return JSON.parseObject(resp, clazz);
    }

    /**
     * get请求，支持查询字符串
     *
     * @param url
     * @param queryString
     * @param clazz
     * @param <T>
     * @return
     */

    public <T> T restApiGet(String url, Map<String, Object> queryString, Class<T> clazz) {
        String fullUrl = buildUrl(url, queryString);
        String resp = get(fullUrl);
        return JSON.parseObject(resp, clazz);
    }

    /**
     * rest接口post调用
     *
     * @param url
     * @param body
     * @return
     */
    public String restApiPost(String url, Object body) {
        try {
            return doPost(url, body);
        } catch (Exception e) {
            log.error("httpPost调用失败。{}", url, e);
            throw e;
        }
    }

    /**
     * rest接口post调用
     * 对返回值直接反序列化
     *
     * @param url
     * @param body
     * @return
     */
    public <T> T restApiPost(String url, Object body, Class<T> clazz) {
        String resp = restApiPost(url, body);
        return JSON.parseObject(resp, clazz);
    }

    /**
     * 获取文件（适合于小文件）
     *
     * @return
     */
    public byte[] getFile(String url) {
        return doGet(url);
    }

    /**
     * 获取文件（适合于大文件）
     *
     * @param url
     * @param os
     */
    @SneakyThrows
    public void getFile(String url, OutputStream os) {
        Request request = new Request.Builder().get().url(url).build();
        Response resp = okHttpClient.newCall(request).execute();
        if (resp.code() != HTTP_OK_CODE) {
            throw new RuntimeException("httpGet请求异常，响应code：" + resp.code());
        }
        try (InputStream is = resp.body().byteStream()) {
            StreamUtils.copy(is, os);
        } catch (Exception e) {
            log.error("读取文件异常。{}", url, e);
            throw e;
        }
    }

    /**
     * 根据查询字符串构造完整的url
     *
     * @param url
     * @param queryString
     * @return
     */
    public String buildUrl(String url, Map<String, Object> queryString) {
        if (null == queryString) {
            return url;
        }

        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        queryString.forEach((k, v) -> urlBuilder.addEncodedQueryParameter(k, Objects.toString(v, "")));
        return urlBuilder.build().url().toString();
    }

    @SneakyThrows
    private String doPost(String url, Object body) {
        String jsonBody = JSON.toJSONString(body);
        RequestBody requestBody = RequestBody.create(jsonBody, jsonMediaType);
        Request request = new Request.Builder().url(url).post(requestBody).build();
        Response resp = okHttpClient.newCall(request).execute();
        if (resp.code() != HTTP_OK_CODE) {
            String msg = String.format("httpPost响应code异常. [code] %s [url] %s [body] %s", String.valueOf(resp.code()), url, jsonBody);
            throw new RuntimeException(msg);
        }
        return resp.body().string();
    }

    @SneakyThrows
    private byte[] doGet(String url) {
        Request request = new Request.Builder().get().url(url).build();
        Response resp = okHttpClient.newCall(request).execute();
        if (resp.code() != HTTP_OK_CODE) {
            String msg = String.format("httpGet响应code异常. [code] %s [url] %s", String.valueOf(resp.code()), url);
            throw new RuntimeException(msg);
        }
        return resp.body().bytes();
    }

    @SneakyThrows
    public byte[] doGetforbyte(String url) {
        Request request = new Request.Builder().get().url(url).build();
        Response resp = okHttpClient.newCall(request).execute();
        if (resp.code() != HTTP_OK_CODE) {
            String msg = String.format("httpGet响应code异常. [code] %s [url] %s", String.valueOf(resp.code()), url);
            throw new RuntimeException(msg);
        }
        return resp.body().bytes();
    }
}
