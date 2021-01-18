package pers.xiaomuma.base.web.http.okhttp;


import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.xiaomuma.base.common.utils.JsonUtils;
import pers.xiaomuma.base.web.http.log.*;

import java.io.IOException;

public class OkHttpLoggingInterceptor implements Interceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(OkHttpLoggingInterceptor.class);
    private LogLevel logLevel;

    public OkHttpLoggingInterceptor(LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!HttpLogUtil.canLog(request.url().toString(), this.logLevel, LOGGER)) {
            return chain.proceed(request);
        } else {
            long startTime = System.currentTimeMillis();
            Request4Log request4Log = OkHttpLoggingInterceptor.OkHttpLog.createRequestLog(request, this.logLevel);
            LOGGER.info(request4Log.toString());
            Exception error = null;
            Response response = null;
            boolean var18 = false;

            Response var8;
            try {
                var18 = true;
                response = chain.proceed(request);
                var8 = response;
                var18 = false;
            } catch (Exception var19) {
                error = var19;
                throw var19;
            } finally {
                if (var18) {
                    long costTime = System.currentTimeMillis() - startTime;
                    Response4Log logResp = OkHttpLoggingInterceptor.OkHttpLog.createResponseLog(this.logLevel, request.url().toString(), costTime, HttpLogUtil.logError(error), response);
                    LOGGER.info(logResp.toString());
                }
            }

            long costTime = System.currentTimeMillis() - startTime;
            Response4Log logResp = OkHttpLoggingInterceptor.OkHttpLog.createResponseLog(this.logLevel, request.url().toString(), costTime, HttpLogUtil.logError(error), response);
            LOGGER.info(logResp.toString());
            return var8;
        }
    }

    public static class OkHttpLog {
        public OkHttpLog() {
        }

        static Response4Log createResponseLog(LogLevel logLevel, String requestURI, long elapsedTime, String error, Response response) {
            Response4Log response4Log = new Response4Log(LogType.OKHTTP_RESP, logLevel);
            HttpLogUtil.swallowException(OkHttpLoggingInterceptor.LOGGER, () -> {
                response4Log.setTime(elapsedTime);
                response4Log.setUri(HttpLogUtil.trimRequestUri(requestURI));
                response4Log.setError(error);
                if (response != null) {
                    response4Log.setStatus(response.code());
                    if (!LogLevel.MINIMUM.equals(logLevel) && response.headers() != null) {
                        response4Log.setHeader(JsonUtils.object2Json(HttpLogUtil.removeIgnoreHeader(response.headers().toMultimap())));
                    }
                }

                response4Log.simplifyLogIfNecessary(logLevel);
            });
            return response4Log;
        }

        static Request4Log createRequestLog(Request request, LogLevel logLevel) {
            Request4Log request4Log = new Request4Log(LogType.OKHTTP_REQ, logLevel);
            HttpLogUtil.swallowException(OkHttpLoggingInterceptor.LOGGER, () -> {
                request4Log.setParam("");
                request4Log.setUri(HttpLogUtil.trimRequestUri(request.url().toString()));
                request4Log.setHttpMethod(request.method());
                if (!LogLevel.MINIMUM.equals(logLevel)) {
                    String urlPath = HttpLogUtil.getPathFromUrl(request4Log.getUri());
                    if (urlPath == null || !HttpLogUtil.isIgnoreUrlParam(urlPath)) {
                        request4Log.setBody(bodyToString(request));
                    }

                    if (request.headers() != null) {
                        request4Log.setHeader(JsonUtils.object2Json(HttpLogUtil.removeIgnoreHeader(request.headers().toMultimap())));
                    }

                    request4Log.simplifyLogIfNecessary(logLevel);
                }

            });
            return request4Log;
        }

        private static String bodyToString(final Request request) {
            if (request.body() != null) {
                try {
                    Request copy = request.newBuilder().build();
                    Buffer buffer = new Buffer();
                    copy.body().writeTo(buffer);
                    return buffer.readUtf8();
                } catch (IOException var3) {
                    OkHttpLoggingInterceptor.LOGGER.error("", var3);
                }
            }

            return null;
        }
    }
}
