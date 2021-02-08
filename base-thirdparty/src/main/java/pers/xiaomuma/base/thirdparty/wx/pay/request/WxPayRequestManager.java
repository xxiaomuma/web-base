package pers.xiaomuma.base.thirdparty.wx.pay.request;


import cn.hutool.core.util.StrUtil;
import cn.hutool.http.ContentType;
import okhttp3.HttpUrl;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import pers.xiaomuma.base.thirdparty.wx.config.WxPayProperties;
import pers.xiaomuma.base.thirdparty.wx.pay.param.CompanyTransfersV2Param;
import pers.xiaomuma.base.thirdparty.wx.pay.param.JsapiPayV3Param;
import pers.xiaomuma.base.thirdparty.wx.pay.param.PayRefundV2Param;
import pers.xiaomuma.base.thirdparty.wx.pay.param.RefundApplyV3Param;
import pers.xiaomuma.base.thirdparty.wx.pay.url.OutTradeNoPayQueryUrlBuilder;
import pers.xiaomuma.base.thirdparty.wx.pay.url.WxPayUrlBuilder;
import pers.xiaomuma.base.thirdparty.wx.utils.SignUtils;
import pers.xiaomuma.base.thirdparty.wx.utils.WxPayUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Objects;

public class WxPayRequestManager implements WxPayRequest{

    private WxPayProperties properties;
    private RestTemplate restTemplate;
    private static final String OS = System.getProperty("os.name") + "/" + System.getProperty("os.version");
    private static final String VERSION = System.getProperty("java.version");
    private PrivateKey privateKey;

    public WxPayRequestManager(WxPayProperties properties) {
        this.properties = properties;
        this.restTemplate = this.createHttpClientRestTemplate();
        this.privateKey = WxPayUtils.getPrivateKey(properties.getKeyPemPath());
    }

    @Override
    public ResponseEntity<String> jsapiPayV3(JsapiPayV3Param param) {
        param.setAppId(this.properties.getAppId());
        param.setMchId(this.properties.getMerchantId());
        if (StringUtils.isEmpty(param.getNotifyUrl())) {
            param.setNotifyUrl(this.properties.getNotifyUrl());
        }
        String requestAddress = WxPayUrlBuilder.jsApiPayUrlBuilder().version(3).build();
        String requestBody = param.parse2RequestBody();
        HttpHeaders header = this.getAuthorizationHeader(HttpMethod.POST, HttpUrl.parse(requestAddress), requestBody);
        HttpEntity<String> httpEntity = new HttpEntity<>(requestBody, header);
        return this.restTemplate.postForEntity(requestAddress, httpEntity, String.class);
    }

    @Override
    public ResponseEntity<String> outTradeNoPayQueryV3(String outTradeNo) {
        String requestAddress = (new OutTradeNoPayQueryUrlBuilder()).version(3).mchId(this.properties.getMerchantId()).outTradeNo(outTradeNo).build();
        HttpUrl url = HttpUrl.parse(requestAddress);
        HttpHeaders header = this.getAuthorizationHeader(HttpMethod.GET, url, "");
        HttpEntity<String> httpEntity = new HttpEntity<>(header);
        return this.restTemplate.exchange(requestAddress, HttpMethod.GET, httpEntity, String.class);
    }

    @Override
    public ResponseEntity<String> refundApplyV3(RefundApplyV3Param param) {
        param.setSpAppId(this.properties.getAppId());
        param.setSubAppId(this.properties.getAppId());
        param.setSubMchId(this.properties.getMerchantId());
        String requestBody = param.parse2RequestBody();
        String requestAddress = WxPayUrlBuilder.wxRefundApplyV3UrlBuilder().build();
        HttpHeaders header = this.getAuthorizationHeader(HttpMethod.POST, HttpUrl.parse(requestAddress), requestBody);
        HttpEntity<String> httpEntity = new HttpEntity<>(requestBody, header);
        return this.restTemplate.postForEntity(requestAddress, httpEntity, String.class);
    }

    @Override
    public ResponseEntity<String> payRefundV2(PayRefundV2Param param) {
        param.setMchId(this.properties.getMerchantId());
        param.setAppId(this.properties.getAppId());
        param.setNonceStr(WxPayUtils.genNonceStr());
        String signStr = SignUtils.equalsSplicing(param.getSortedParamMap()) + "key=" + this.properties.getApiKey();
        param.setSign(SignUtils.md5(signStr));
        String requestBody = param.parse2RequestBody();
        String requestAddress = WxPayUrlBuilder.refundApplyV2UrlBuilder().build();
        HttpEntity<String> httpEntity = new HttpEntity<>(requestBody);
        return this.restTemplate.postForEntity(requestAddress, httpEntity, String.class);
    }

    @Override
    public ResponseEntity<String> companyTransfersV2(CompanyTransfersV2Param param) {
        param.setMchAppId(this.properties.getAppId());
        param.setMchId(this.properties.getMerchantId());
        param.setNonceStr(WxPayUtils.genNonceStr());
        String signStr = SignUtils.equalsSplicing(param.getSortedParamMap()) + "key=" + this.properties.getApiKey();
        param.setSign(SignUtils.md5(signStr));
        String requestBody = param.parse2RequestBody();
        String requestAddress = WxPayUrlBuilder.companyTransfersUrlBuilder().build();
        HttpEntity<String> httpEntity = new HttpEntity<>(requestBody);
        return this.restTemplate.postForEntity(requestAddress, httpEntity, String.class);
    }

    private HttpHeaders getAuthorizationHeader(HttpMethod httpMethod, HttpUrl url, String body) {
        String authorization = this.buildV3PaySignature(httpMethod, url, body);
        return this.getHeaders(authorization, this.properties.getCertificateSerialNo());
    }

    private String buildV3PaySignature(HttpMethod httpMethod, HttpUrl url, String body) {
        String nonceStr = WxPayUtils.genNonceStr();
        long timestamp = System.currentTimeMillis() / 1000L;
        String message = this.signMsgBodyV3(httpMethod.name(), url, timestamp, nonceStr, body);
        String signature = SignUtils.rsaSHA256Sign(message.getBytes(StandardCharsets.UTF_8), this.privateKey);
        return "mchid=\"" + this.properties.getMerchantId() + "\",nonce_str=\"" + nonceStr + "\",timestamp=\"" + timestamp + "\",serial_no=\"" + this.properties.getCertificateSerialNo() + "\",signature=\"" + signature + "\"";
    }

    private String signMsgBodyV3(String method, HttpUrl url, long timestamp, String nonceStr, String body) {
        String canonicalUrl = url.encodedPath();
        if (url.encodedQuery() != null) {
            canonicalUrl = canonicalUrl + "?" + url.encodedQuery();
        }

        return method + "\n" + canonicalUrl + "\n" + timestamp + "\n" + nonceStr + "\n" + body + "\n";
    }

    private HttpHeaders getHeaders(String authorization, String certificateSerialNo) {
        HttpHeaders headers = new HttpHeaders();
        String userAgent = String.format("WeChatPay-IJPay-HttpClient/%s (%s) Java/%s", WxPayRequestManager.class.getPackage().getImplementationVersion(), OS, VERSION == null ? "Unknown" : VERSION);
        headers.add("Accept", ContentType.JSON.toString());
        headers.add("Authorization", "WECHATPAY2-SHA256-RSA2048 " + authorization);
        headers.add("User-Agent", userAgent);
        headers.add("Content-Type", ContentType.JSON.toString());
        if (StrUtil.isNotEmpty(certificateSerialNo)) {
            headers.add("Wechatpay-Serial", certificateSerialNo);
        }
        return headers;
    }

    private RestTemplate createHttpClientRestTemplate() {
        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            InputStream stream = WxPayRequestManager.class.getClassLoader().getResourceAsStream(this.properties.getCertPath());
            if (Objects.isNull(stream)) {
                throw new RuntimeException(String.format("获取 %s 失败", this.properties.getCertPath()));
            } else {
                BufferedInputStream instream = new BufferedInputStream(stream);
                keyStore.load(instream, this.properties.getMerchantId().toCharArray());
                SSLContext sslcontext = SSLContextBuilder.create().loadKeyMaterial(keyStore, this.properties.getMerchantId().toCharArray()).build();
                HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
                SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1"}, (String[])null, hostnameVerifier);
                CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
                HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpclient);
                RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
                restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
                return restTemplate;
            }
        } catch (IOException | CertificateException | NoSuchAlgorithmException | UnrecoverableKeyException | KeyManagementException | KeyStoreException var10) {
            throw new RuntimeException("初始化restTemplate失败", var10);
        }
    }
}
