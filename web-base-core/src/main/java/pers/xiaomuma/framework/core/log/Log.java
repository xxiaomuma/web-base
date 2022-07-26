package pers.xiaomuma.framework.core.log;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.xiaomuma.framework.serialize.JsonUtils;

import java.io.Serializable;

public abstract class Log implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(Log.class);

    public static final int SIMPLE_MAX_SIZE = 1000;

    public static final String LOG_PREFIX = "PerfLog";

    /**
     * 日志类型
     */
    protected LogType type;


    @JsonIgnore
    protected LogLevel logLevel;

    public Log() {}

    public Log(LogType type, LogLevel logLevel) {
        this.type = type;
        this.logLevel = logLevel;
    }

    public LogType getType() {
        return type;
    }

    public void setType(LogType type) {
        this.type = type;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    @Override
    public String toString() {
        return String.format("%s_%s:%s", LOG_PREFIX, typeToToken(type), JsonUtils.object2Json(this));
    }

    private String typeToToken(LogType type) {
        String token = "0";
        if(type == null) {
            logger.error("PerformanceLogType is null");
        } else if(type.name().endsWith("REQ")){
            token = "0";
        } else if(type.name().endsWith("RESP")){
            token = "1";
        }
        return token;
    }

    public static String tryTrimToSimple(String str, LogLevel logLevel) {

        if(logLevel.equals(LogLevel.SIMPLE)) {
            return trim(str);
        }

        return str;

    }

    public static String trim(String str) {

        if(str != null && str.length() > SIMPLE_MAX_SIZE) {
            return str.substring(0, SIMPLE_MAX_SIZE);
        }

        return str;
    }

    @FunctionalInterface
    public interface RunnableThrowable {
        void run() throws Exception;
    }

}
