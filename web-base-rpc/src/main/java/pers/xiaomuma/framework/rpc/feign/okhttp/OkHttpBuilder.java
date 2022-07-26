package pers.xiaomuma.framework.rpc.feign.okhttp;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import pers.xiaomuma.framework.core.global.ApplicationConstant;

import java.util.concurrent.TimeUnit;

public class OkHttpBuilder {

    public static OkHttpClient.Builder build(ApplicationConstant applicationConstant) {
        return new OkHttpClient.Builder()
                .readTimeout(applicationConstant.okHttpReadTimeout, TimeUnit.MILLISECONDS)
                .connectTimeout(applicationConstant.okHttpConnectTimeout, TimeUnit.MILLISECONDS)
                .writeTimeout(applicationConstant.okHttpWriteTimeout, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                .connectionPool(new ConnectionPool(applicationConstant.okHttpMaxIdle, applicationConstant.okHttpAliveDuration, TimeUnit.SECONDS))
                .addInterceptor(new OkHttpLoggingInterceptor(applicationConstant));
    }

}
