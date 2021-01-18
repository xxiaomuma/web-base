package pers.xiaomuma.base.web.http.okhttp;


import cn.hutool.core.util.StrUtil;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.InitializingBean;
import pers.xiaomuma.base.web.ApplicationEnv;
import pers.xiaomuma.base.web.http.log.LogLevel;
import okhttp3.OkHttpClient.Builder;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class OkHttpClientBuilder implements InitializingBean {

    private Builder okHttpClientBuilder;
    private ApplicationEnv applicationEnv;

    public OkHttpClientBuilder(ApplicationEnv applicationEnv) {
        this.applicationEnv = applicationEnv;
    }

    public void afterPropertiesSet() {
        Objects.requireNonNull(this.applicationEnv, "applicationEnv不能为空");
        this.okHttpClientBuilder = httpBuilder(this.applicationEnv);
    }

    public static OkHttpClient newHttpClient() {
        return httpBuilder().build();
    }

    public static OkHttpClient newHttpClient(OkHttpClientBuilder.OkHttpBuildParam okHttpBuildParam) {
        return httpBuilder(okHttpBuildParam).build();
    }

    public OkHttpClient multiplexedHttpClient() {
        try {
            if (this.okHttpClientBuilder == null) {
                this.afterPropertiesSet();
            }

            return this.okHttpClientBuilder.build();
        } catch (Exception var2) {
            throw new RuntimeException("获取okHttpClient失败", var2);
        }
    }

    public static Builder httpBuilder(ApplicationEnv applicationEnv) {
        OkHttpClientBuilder.OkHttpBuildParam param = convertEnv2BuildParam(applicationEnv);
        return httpBuilder(param);
    }

    public static Builder httpBuilder() {
        OkHttpClientBuilder.OkHttpBuildParam param = new OkHttpClientBuilder.OkHttpBuildParam();
        return httpBuilder(param);
    }

    private static Builder httpBuilder(OkHttpClientBuilder.OkHttpBuildParam param) {
        ConnectionPool connectionPool = new ConnectionPool(param.getMaxIdle(), param.getAliveDurationSeconds(), TimeUnit.SECONDS);
        return (new Builder()).readTimeout(param.getReadTimeout(), TimeUnit.MILLISECONDS)
                .connectTimeout(param.getConnectTimeout(), TimeUnit.MILLISECONDS)
                .writeTimeout(param.getWriteTimeout(), TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                .connectionPool(connectionPool)
                .addInterceptor(new OkHttpLoggingInterceptor(param.logLevel));
    }

    public static OkHttpClientBuilder.OkHttpBuildParam convertEnv2BuildParam(ApplicationEnv applicationEnv) {
        OkHttpClientBuilder.OkHttpBuildParam param = new OkHttpClientBuilder.OkHttpBuildParam();
        param.setMaxIdle(applicationEnv.okHttpMaxIdle);
        param.setReadTimeout(applicationEnv.okHttpReadTimeout);
        param.setWriteTimeout(applicationEnv.okHttpWriteTimeout);
        param.setConnectTimeout(applicationEnv.okHttpConnectTimeout);
        param.setAliveDurationSeconds(applicationEnv.okHttpAliveDuration);
        LogLevel logLevel = LogLevel.valueOf(StrUtil.swapCase(applicationEnv.logLevel));
        param.setLogLevel(Optional.of(logLevel).orElse(LogLevel.SIMPLE));
        return param;
    }

    public static class OkHttpBuildParam {
        private long connectTimeout = 5000L;
        private long readTimeout = 5000L;
        private long writeTimeout = 5000L;
        private int maxIdle = 5000;
        private int aliveDurationSeconds = 300;
        private LogLevel logLevel;
        private boolean retryOnConnectionFailure;

        public OkHttpBuildParam() {
            this.logLevel = LogLevel.SIMPLE;
            this.retryOnConnectionFailure = false;
        }

        public long getConnectTimeout() {
            return this.connectTimeout;
        }

        public void setConnectTimeout(long okHttpConnectTimeout) {
            this.connectTimeout = okHttpConnectTimeout;
        }

        public long getReadTimeout() {
            return this.readTimeout;
        }

        public void setReadTimeout(long okHttpReadTimeout) {
            this.readTimeout = okHttpReadTimeout;
        }

        public long getWriteTimeout() {
            return this.writeTimeout;
        }

        public void setWriteTimeout(long writeTimeout) {
            this.writeTimeout = writeTimeout;
        }

        public int getMaxIdle() {
            return this.maxIdle;
        }

        public void setMaxIdle(int maxIdle) {
            this.maxIdle = maxIdle;
        }

        public int getAliveDurationSeconds() {
            return this.aliveDurationSeconds;
        }

        public void setAliveDurationSeconds(int aliveDurationSeconds) {
            this.aliveDurationSeconds = aliveDurationSeconds;
        }

        public LogLevel getLogLevel() {
            return this.logLevel;
        }

        public void setLogLevel(LogLevel logLevel) {
            this.logLevel = logLevel;
        }

        public boolean isRetryOnConnectionFailure() {
            return this.retryOnConnectionFailure;
        }

        public void setRetryOnConnectionFailure(boolean retryOnConnectionFailure) {
            this.retryOnConnectionFailure = retryOnConnectionFailure;
        }
    }
}
