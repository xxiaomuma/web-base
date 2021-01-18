package pers.xiaomuma.base.web.http.log;


import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import pers.xiaomuma.base.web.ApplicationEnv;
import java.net.URL;
import java.util.*;
import java.util.function.Predicate;

public class HttpLogUtil {

    private static final List<String> IGNORE_URL_LIST = Lists.newArrayList("/do_not_delete/*", "/fonts/**", "/css/**");
    private static final List<String> IGNORE_URL_PARAM_LIST = Lists.newArrayList("/**/encrypt", "/**/decrypt");
    private static UrlMatcher urlMatcher;
    private static UrlMatcher urlParamMatcher;
    private static Set<String> ignoreHeaders;

    static {
        urlMatcher = new UrlMatcher(new LinkedHashSet<>(IGNORE_URL_LIST), UrlMatcher.STATIC_URL_SUFFIX_LIST);
        urlParamMatcher = new UrlMatcher(new LinkedHashSet<>(IGNORE_URL_PARAM_LIST));
        ignoreHeaders = new HashSet<>();
    }

    public HttpLogUtil() {
    }

    public static synchronized void init(ApplicationEnv env) {
        LinkedHashSet<String> antUrls;
        String[] var2;
        int var3;
        int var4;
        String url;
        if (env.httpLogIgnoreUrls != null && env.httpLogIgnoreUrls.length > 0) {
            antUrls = new LinkedHashSet<>(IGNORE_URL_LIST);
            var2 = env.httpLogIgnoreUrls;
            var3 = var2.length;

            for(var4 = 0; var4 < var3; ++var4) {
                url = var2[var4];
                if (StrUtil.isNotBlank(url)) {
                    antUrls.add(url.trim());
                }
            }

            urlMatcher = new UrlMatcher(antUrls, UrlMatcher.STATIC_URL_SUFFIX_LIST);
        }

        if (env.httpLogIgnoreUrlParams != null && env.httpLogIgnoreUrlParams.length > 0) {
            antUrls = new LinkedHashSet<>(IGNORE_URL_PARAM_LIST);
            var2 = env.httpLogIgnoreUrlParams;
            var3 = var2.length;

            for(var4 = 0; var4 < var3; ++var4) {
                url = var2[var4];
                if (StrUtil.isNotBlank(url)) {
                    antUrls.add(url.trim());
                }
            }

            urlParamMatcher = new UrlMatcher(antUrls);
        }

        if (env.httpLogIgnoreHeaders != null && env.httpLogIgnoreHeaders.length > 0) {
            ignoreHeaders.addAll(Arrays.asList(env.httpLogIgnoreHeaders));
        }

    }

    public static boolean canLog(String url, LogLevel level, Logger logger) {
        if (!level.equals(LogLevel.NONE) && logger.isInfoEnabled()) {
            return url == null || !urlMatcher.ignore(url);
        } else {
            return false;
        }
    }

    public static boolean isIgnoreUrlParam(String url) {
        return StrUtil.isBlank(url) || urlParamMatcher.ignore(url);
    }

    public static boolean isIgnoreHeader(String header) {
        return StrUtil.isBlank(header) || ignoreHeaders.contains(header);
    }

    public static String logError(Throwable ex) {
        return logError(ex, null);
    }

    public static String logError(Throwable ex, Predicate<Throwable> ignore) {
        return ex != null && (ignore == null || !ignore.test(ex)) ? ex.getClass().getSimpleName() + ": " + ex.getMessage() : null;
    }

    public static String getPathFromUrl(String url) {
        try {
            return (new URL(url)).getPath();
        } catch (Exception var2) {
            return null;
        }
    }

    public static void swallowException(Logger logger, Log.RunnableThrowable runnable) {
        try {
            runnable.run();
        } catch (Exception var3) {
            logger.error("生成PerformanceLog的时候发生错误", var3);
        }

    }

    public static String trimRequestUri(String uri) {
        if (uri == null) {
            return null;
        } else {
            return uri.startsWith("//") ? uri.replaceFirst("//", "/") : uri;
        }
    }

    public static Map<String, List<String>> removeIgnoreHeader(Map<String, List<String>> map) {
        map.entrySet().removeIf((entry) -> {
            return isIgnoreHeader(entry.getKey());
        });
        return map;
    }


}
