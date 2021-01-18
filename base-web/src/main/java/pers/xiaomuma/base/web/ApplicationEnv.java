package pers.xiaomuma.base.web;


import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationEnv {

    /**端口*/
    @Value("${server.port}")
    public int port;

    /**应用名称*/
    @Value("${spring.application.name}")
    public String applicationName;

    /**环境*/
    @Value("${spring.profiles.active:dev}")
    public String profile;

    @Value("${app.okhttp.connect.timeout:5000}")
    public long okHttpConnectTimeout;
    @Value("${app.okhttp.read.timeout:10000}")
    public long okHttpReadTimeout;
    @Value("${app.okhttp.write.timeout:10000}")
    public long okHttpWriteTimeout;
    @Value("${app.okhttp.max.idle:5}")
    public int okHttpMaxIdle;
    @Value("${app.okhttp.alive.duration:300}")
    public int okHttpAliveDuration;
    @Value("${app.log.logLevel:simple}")
    public String logLevel;
    @Value("${app.log.log.ignore.urls:}")
    public String[] httpLogIgnoreUrls;
    @Value("${app.log.log.ignore.urlParams:}")
    public String[] httpLogIgnoreUrlParams;
    @Value("${app.log.log.ignore.headers:}")
    public String[] httpLogIgnoreHeaders;


    public boolean isDevProfile() {
        return StrUtil.isBlank(profile) || "DEV".equalsIgnoreCase(profile);
    }

    public boolean isTestProfile() {
        return "TEST".equalsIgnoreCase(profile);
    }

    public boolean isPrevProfile() {
        return "PREV".equalsIgnoreCase(profile);
    }

    public boolean isProdProfile() {
        return "PROD".equalsIgnoreCase(profile);
    }

}
