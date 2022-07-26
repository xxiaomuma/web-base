package pers.xiaomuma.framework.core.global;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

public class ApplicationConstant {

    /**
     * 端口
     */
    @Value("${server.port}")
    public int port;

    /**
     * 应用名称
     */
    @Value("${spring.application.name}")
    public String applicationName;

    /**
     * 环境
     */
    @Value("${spring.profiles.active:dev}")
    public String profile;

    /**
     * 执行任务的线程数量
     */
    @Value("${app.scheduler.thread.count:0}")
    public int schedulerThreadCount;

    /**
     * 连接超时时间(默认5秒)
     */
    @Value("${app.okhttp.connect.timeout:5000}")
    public long okHttpConnectTimeout;

    /**
     * 读超时时间(默认10秒)
     */
    @Value("${app.okhttp.read.timeout:10000}")
    public long okHttpReadTimeout;

    /**
     * 写超时时间(默认10秒)
     */
    @Value("${app.okhttp.write.timeout:10000}")
    public long okHttpWriteTimeout;

    /**
     * 最大空闲连接数
     */
    @Value("${app.okhttp.max.idle:5}")
    public int okHttpMaxIdle;

    /**
     * 连接存活时间, 单位: 秒
     */
    @Value("${app.okhttp.alive.duration:300}")
    public int okHttpAliveDuration;

    /**
     * 全局sql超时时间, 默认60秒
     */
    @Value("${app.ds.timeout.global:60}")
    public int globalDataSourceTimeout;

    /**
     * NONE 不打印日志
     * MINIMUM 不打印请求消息体, 请求参数, 请求响应头
     * SIMPLE 打印精简的日志
     * ALL 打印所有日志
     */
    @Value("${app.log.logLevel:simple}")
    public String logLevel;

    /**
     * 打印日志忽略的url
     */
    @Value("${app.log.log.ignore.urls:}")
    public String[] httpLogIgnoreUrls;

    /**
     * 打印日志忽略的参数
     */
    @Value("${app.log.log.ignore.urlParams:}")
    public String[] httpLogIgnoreUrlParams;

    /**
     * 打印日志忽略的请求头
     */
    @Value("${app.log.log.ignore.headers:}")
    public String[] httpLogIgnoreHeaders;


    public boolean isDevProfile() {
        return StringUtils.isBlank(profile) || "DEV".equalsIgnoreCase(profile);
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
