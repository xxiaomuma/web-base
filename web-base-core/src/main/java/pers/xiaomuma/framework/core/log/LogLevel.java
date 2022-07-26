package pers.xiaomuma.framework.core.log;


public enum LogLevel {

    /**
     * 不打印任何内容
     */
    NONE,

    /**
     * 日志最小化, 不打印请求消息体, 请求参数, 请求响应头
     */
    MINIMUM,

    /**
     * 简化, 消息体, 请求参数, 请求响应头最长只打印1000个字符
     */
    SIMPLE,

    /**
     * 打印所有信息
     */
    ALL,

    /**
     * 没有设置, 如果是prod环境, 按照SIMPLE打印日志, 否则按照ALL打印日志.
     */
    NOTSET;


}
