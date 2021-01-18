package pers.xiaomuma.base.web.http.log;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.xiaomuma.base.common.utils.JsonUtils;

import java.io.Serializable;

public abstract class Log implements Serializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(Log.class);
    public static final int SIMPLE_MAX_SIZE = 1000;
    public static final String LOG_PREFIX = "PerfLog";
    protected LogType type;
    @JsonIgnore
    protected LogLevel logLevel;

    public Log() {
    }

    public Log(LogType type, LogLevel logLevel) {
        this.type = type;
        this.logLevel = logLevel;
    }

    public LogType getType() {
        return this.type;
    }

    public void setType(LogType type) {
        this.type = type;
    }

    public LogLevel getLogLevel() {
        return this.logLevel;
    }

    public void setLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    public String toString() {
        return String.format("%s_%s:%s", "PerfLog", this.typeToToken(this.type), JsonUtils.object2Json(this));
    }

    private String typeToToken(LogType type) {
        String token = "0";
        if (type == null) {
            LOGGER.error("PerformanceLogType is null");
        } else if (type.name().endsWith("REQ")) {
            token = "0";
        } else if (type.name().endsWith("RESP")) {
            token = "1";
        }

        return token;
    }

    public static String tryTrimToSimple(String str, LogLevel logLevel) {
        return logLevel.equals(LogLevel.SIMPLE) ? trim(str) : str;
    }

    public static String trim(String str) {
        return str != null && str.length() > 1000 ? str.substring(0, 1000) : str;
    }

    @FunctionalInterface
    public interface RunnableThrowable {
        void run() throws Exception;
    }
}
