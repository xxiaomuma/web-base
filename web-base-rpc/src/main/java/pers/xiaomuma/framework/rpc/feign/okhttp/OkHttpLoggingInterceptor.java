package pers.xiaomuma.framework.rpc.feign.okhttp;


import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.xiaomuma.framework.core.global.ApplicationConstant;
import pers.xiaomuma.framework.core.log.HttpLogUtil;
import pers.xiaomuma.framework.core.log.LogLevel;
import pers.xiaomuma.framework.core.log.LogType;
import pers.xiaomuma.framework.core.log.request.Request4Log;
import pers.xiaomuma.framework.core.log.request.Response4Log;
import pers.xiaomuma.framework.serialize.JsonUtils;
import java.io.IOException;

public class OkHttpLoggingInterceptor implements Interceptor {

	private final static Logger logger = LoggerFactory.getLogger(OkHttpLoggingInterceptor.class);

	private LogLevel logLevel;

	public OkHttpLoggingInterceptor(ApplicationConstant applicationConstant) {
		this.logLevel = determineLogLevel(applicationConstant.logLevel, applicationConstant.isProdProfile());
	}

	public OkHttpLoggingInterceptor(LogLevel logLevel) {
		this.logLevel = logLevel;
	}

	@Override
	public Response intercept(Chain chain) throws IOException {
		Request request = chain.request();
		if (!HttpLogUtil.canLog(request.url().toString(), logLevel, logger)) {
			return chain.proceed(request);
		} else {
			long startTime = System.currentTimeMillis();
			Request4Log request4Log = OkHttpLog.createRequestLog(request, logLevel);
			logger.info(request4Log.toString());
			Exception error = null;
			Response response = null;
			try {
				response = chain.proceed(request);
				return response;
			} catch (Exception e) {
				error = e;
				throw e;
			} finally {
				long costTime = System.currentTimeMillis() - startTime;
				Response4Log logResp = OkHttpLog.createResponseLog(logLevel, request.url().toString(),
						costTime, HttpLogUtil.logError(error), response);
				logger.info(logResp.toString());
			}
		}
	}

	private LogLevel determineLogLevel(String logLevel, boolean isProductProfile) {
		LogLevel level = LogLevel.NOTSET;
		try {
			level = LogLevel.valueOf(logLevel.toUpperCase());
		} catch (Exception e) {
			logger.error("", e);
		}
		return level;
	}

	public static class OkHttpLog {

		static Response4Log createResponseLog(LogLevel logLevel, String requestURI, long elapsedTime,
											  String error, Response response) {

			Response4Log response4Log = new Response4Log(LogType.OKHTTP_RESP, logLevel);

			HttpLogUtil.swallowException(logger, () -> {

				response4Log.setTime(elapsedTime);
				response4Log.setUri(HttpLogUtil.trimRequestUri(requestURI));
				response4Log.setError(error);

				if (response != null) {
					response4Log.setStatus(response.code());

					if (!LogLevel.MINIMUM.equals(logLevel)) {
						if (response.headers() != null) {
							response4Log.setHeader(JsonUtils.object2Json(HttpLogUtil.removeIgnoreHeader(response.headers().toMultimap())));
						}
					}
				}

				response4Log.simplifyLogIfNecessary(logLevel);

			});
			return response4Log;
		}

		static Request4Log createRequestLog(Request request, LogLevel logLevel) {

			Request4Log request4Log = new Request4Log(LogType.OKHTTP_REQ, logLevel);

			HttpLogUtil.swallowException(logger, () -> {
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
					final Request copy = request.newBuilder().build();
					final Buffer buffer = new Buffer();
					copy.body().writeTo(buffer);
					return buffer.readUtf8();
				} catch (final IOException e) {
					logger.error("", e);
				}
			}
			return null;
		}
	}

}
