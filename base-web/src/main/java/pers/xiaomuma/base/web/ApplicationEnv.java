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
