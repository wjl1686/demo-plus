package com.example.demo.common.config;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.*;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

/**
 * Okhttp3 bean
 *
 * @author wujl2
 * @version V1.0
 * @since 2019-03-23 23:24
 */
@Configuration
@Slf4j
public class OkHttpClientConfig {

    /**
     * 配置bean
     *
     * @return
     */
    @Bean
    public OkHttpClient okHttpClient() {
        OkHttpClient.Builder build = new OkHttpClient.Builder();
        build.connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(40, TimeUnit.SECONDS)
                .build();
        supportHttps(build);
        return build.build();
    }

    /**
     * 支持https
     *
     * @param builder
     */
    @SneakyThrows
    private void supportHttps(OkHttpClient.Builder builder) {
        // https://stackoverflow.com/questions/25509296/trusting-all-certificates-with-okhttp
        // Create a trust manager that does not validate certificate chains
        final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
                    throws CertificateException {
            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
                    throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[]{};
            }
        }};

        // Install the all-trusting trust manager
        final SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        // Create an ssl socket factory with our all-trusting manager
        final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
        builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
        builder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
    }
}
