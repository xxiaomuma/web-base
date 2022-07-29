package pers.xiaomuma.framework.thirdparty.pay.wx.request;


import cn.hutool.core.util.StrUtil;
import cn.hutool.http.ContentType;
import okhttp3.HttpUrl;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import pers.xiaomuma.framework.thirdparty.pay.wx.WxTransactionProperties;
import pers.xiaomuma.framework.thirdparty.pay.wx.param.WxTransactionPayParam;
import pers.xiaomuma.framework.thirdparty.pay.wx.param.WxTransactionRefundParam;
import pers.xiaomuma.framework.thirdparty.pay.wx.url.WxTransactionUrlBuilder;
import pers.xiaomuma.framework.thirdparty.utils.SignUtils;
import pers.xiaomuma.framework.thirdparty.utils.WxPayUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Objects;


public class WxTransactionRequestManager implements WxTransactionRequest {

    private PrivateKey privateKey;
    private RestTemplate restTemplate;
    private WxTransactionProperties properties;
    private static final String SCHEMA = "WECHATPAY2-SHA256-RSA2048";
    private static final String VERSION = System.getProperty("java.version");
    private static final String OS = System.getProperty("os.name") + "/" + System.getProperty("os.version");

    public WxTransactionRequestManager(PrivateKey privateKey, WxTransactionProperties properties) {
        this.properties = properties;
        this.privateKey = privateKey;
        this.restTemplate = this.createHttpClientRestTemplate();
    }

    @Override
    public ResponseEntity<String> transactionPayH5(WxTransactionPayParam param) {
        this.setPayDefaultValue(param);
        String requestAddress = WxTransactionUrlBuilder.wxTransactionPayH5Url().build();
        String requestBody = param.parse2RequestBody();
        HttpHeaders header = this.buildAuthorizationHeader(HttpMethod.POST, HttpUrl.parse(requestAddress), requestBody);
        HttpEntity<String> httpEntity = new HttpEntity<>(requestBody, header);
        return restTemplate.postForEntity(requestAddress, httpEntity, String.class);
    }

    @Override
    public ResponseEntity<String> transactionPayApp(WxTransactionPayParam param) {
        this.setPayDefaultValue(param);
        String requestAddress = WxTransactionUrlBuilder.transactionPayAppUrl().build();
        String requestBody = param.parse2RequestBody();
        HttpHeaders header = this.buildAuthorizationHeader(HttpMethod.POST, HttpUrl.parse(requestAddress), requestBody);
        HttpEntity<String> httpEntity = new HttpEntity<>(requestBody, header);
        return restTemplate.postForEntity(requestAddress, httpEntity, String.class);
    }

    @Override
    public ResponseEntity<String> transactionPayJsapi(WxTransactionPayParam param) {
        this.setPayDefaultValue(param);
        String requestAddress = WxTransactionUrlBuilder.transactionJsapiPayUrl().build();
        String requestBody = param.parse2JsapiRequestBody();
        HttpHeaders header = this.buildAuthorizationHeader(HttpMethod.POST, HttpUrl.parse(requestAddress), requestBody);
        HttpEntity<String> httpEntity = new HttpEntity<>(requestBody, header);
        return restTemplate.postForEntity(requestAddress, httpEntity, String.class);
    }

    @Override
    public ResponseEntity<String> transactionPayNative(WxTransactionPayParam param) {
        this.setPayDefaultValue(param);
        String requestAddress = WxTransactionUrlBuilder.transactionPayNativeUrl().build();
        String requestBody = param.parse2RequestBody();
        HttpHeaders header = this.buildAuthorizationHeader(HttpMethod.POST, HttpUrl.parse(requestAddress), requestBody);
        HttpEntity<String> httpEntity = new HttpEntity<>(requestBody, header);
        return restTemplate.postForEntity(requestAddress, httpEntity, String.class);
    }

    @Override
    public ResponseEntity<String> transactionRefundDomestic(WxTransactionRefundParam param) {
        String requestAddress = WxTransactionUrlBuilder.transactionRefundUrl().build();
        String requestBody = param.parse2RequestBody();
        HttpHeaders header = this.buildAuthorizationHeader(HttpMethod.POST, HttpUrl.parse(requestAddress), requestBody);
        HttpEntity<String> httpEntity = new HttpEntity<>(requestBody, header);
        return restTemplate.postForEntity(requestAddress, httpEntity, String.class);
    }

    @Override
    public ResponseEntity<String> transactionQueryTransactionId(String transactionId) {
        String requestAddress = WxTransactionUrlBuilder.transactionQueryUrl()
                .mchId(properties.getMerchantId())
                .transactionId(transactionId)
                .buildIdQuery();
        HttpUrl url = HttpUrl.parse(requestAddress);
        HttpHeaders header = this.buildAuthorizationHeader(HttpMethod.GET, url, "");
        HttpEntity<String> httpEntity = new HttpEntity<>(header);
        return restTemplate.exchange(requestAddress, HttpMethod.GET, httpEntity, String.class);
    }

    @Override
    public ResponseEntity<String> transactionQueryOutTradeNo(String outTradeNo) {
        String requestAddress = WxTransactionUrlBuilder.transactionQueryUrl()
                .mchId(properties.getMerchantId())
                .outTradeNo(outTradeNo)
                .buildOutTradeNoQuery();
        HttpUrl url = HttpUrl.parse(requestAddress);
        HttpHeaders header = this.buildAuthorizationHeader(HttpMethod.GET, url, "");
        HttpEntity<String> httpEntity = new HttpEntity<>(header);
        return restTemplate.exchange(requestAddress, HttpMethod.GET, httpEntity, String.class);
    }

    private void setPayDefaultValue(WxTransactionPayParam param) {
        param.setAppId(properties.getAppId());
        param.setMchId(properties.getMerchantId());
        if (StrUtil.isEmpty(param.getNotifyUrl())) {
            param.setNotifyUrl(properties.getNotifyUrl());
        }
    }

    private HttpHeaders buildAuthorizationHeader(HttpMethod httpMethod, HttpUrl url, String body) {
        String authorization = this.buildV3PaySignature(httpMethod, url, body);
        return this.buildHeaders(authorization);
    }

    private HttpHeaders buildHeaders(String authorization) {
        HttpHeaders headers = new HttpHeaders();
        String userAgent = String.format(
                "WeChatPay-IJPay-HttpClient/%s (%s) Java/%s",
                WxTransactionRequestManager.class.getPackage().getImplementationVersion(),
                OS,
                VERSION == null ? "Unknown" : VERSION);
        headers.add(HttpHeaders.ACCEPT, ContentType.JSON.toString());
        headers.add(HttpHeaders.AUTHORIZATION, SCHEMA +" " + authorization);
        headers.add(HttpHeaders.USER_AGENT, userAgent);
        headers.add(HttpHeaders.CONTENT_TYPE, ContentType.JSON.toString());
        if (StrUtil.isNotEmpty(properties.getCertificateSerialNo())) {
            headers.add("Wechatpay-Serial", properties.getCertificateSerialNo());
        }
        return headers;
    }

    private String buildV3PaySignature(HttpMethod httpMethod, HttpUrl url, String body) {
        String nonceStr = WxPayUtils.genNonceStr();
        long timestamp = System.currentTimeMillis() / 1000;
        String message = this.signMsgBodyV3(httpMethod.name(), url, timestamp, nonceStr, body);
        String signature = SignUtils.rsaSHA256Sign(message.getBytes(StandardCharsets.UTF_8), privateKey);
        return "mchid=\"" + properties.getMerchantId() + "\","
                + "nonce_str=\"" + nonceStr + "\","
                + "timestamp=\"" + timestamp + "\","
                + "serial_no=\"" + properties.getCertificateSerialNo() + "\","
                + "signature=\"" + signature + "\"";
    }

    private String signMsgBodyV3(String method, HttpUrl url, long timestamp, String nonceStr, String body) {
        String canonicalUrl = url.encodedPath();
        if (url.encodedQuery() != null) {
            canonicalUrl += "?" + url.encodedQuery();
        }
        return method + "\n" + canonicalUrl + "\n" + timestamp + "\n" + nonceStr + "\n" + body + "\n";
    }

    private RestTemplate createHttpClientRestTemplate() {
        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            InputStream stream = WxTransactionRequestManager.class.getClassLoader().getResourceAsStream(properties.getCertPath());
            if (Objects.isNull(stream)) {
                throw new RuntimeException(String.format("获取 %s 失败", properties.getCertPath()));
            }
            BufferedInputStream instream = new BufferedInputStream(stream);
            keyStore.load(instream, properties.getMerchantId().toCharArray());
            SSLContext sslcontext = SSLContextBuilder.create()
                    .loadKeyMaterial(keyStore, properties.getMerchantId().toCharArray())
                    .build();
            HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1"},
                    null, hostnameVerifier);
            CloseableHttpClient httpclient = HttpClients.custom()
                    .setSSLSocketFactory(sslsf)
                    .build();
            HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpclient);
            RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
            // 微信支付默认字符集为UTF-8
            restTemplate.getMessageConverters()
                    .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
            return restTemplate;
        } catch (KeyStoreException | IOException | CertificateException |
                NoSuchAlgorithmException | UnrecoverableKeyException | KeyManagementException e) {
            throw new RuntimeException("初始化restTemplate失败", e);
        }
    }



}
