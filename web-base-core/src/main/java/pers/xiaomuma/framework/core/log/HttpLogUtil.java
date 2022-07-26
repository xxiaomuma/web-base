package pers.xiaomuma.framework.core.log;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import pers.xiaomuma.framework.core.global.ApplicationConstant;
import java.net.URL;
import java.util.*;
import java.util.function.Predicate;


public class HttpLogUtil {

    private static final List<String> IGNORE_URL_LIST = Lists.newArrayList(
            "/do_not_delete/*", "/fonts/**", "/css/**"
    );

    private static final List<String> IGNORE_URL_PARAM_LIST = Lists.newArrayList(
            "/**/encrypt", "/**/decrypt"
    );

    //不打印url对应的日志
    private static UrlMatcher urlMatcher = new UrlMatcher(new LinkedHashSet<>(IGNORE_URL_LIST), UrlMatcher.STATIC_URL_SUFFIX_LIST);

    //打印日志的时候忽略url中的参数
    private static UrlMatcher urlParamMatcher = new UrlMatcher(new LinkedHashSet<>(IGNORE_URL_PARAM_LIST));

    private static Set<String> ignoreHeaders = new HashSet<>();


    public synchronized static void init(ApplicationConstant constant) {
        if(constant.httpLogIgnoreUrls != null && constant.httpLogIgnoreUrls.length > 0) {
            Set<String> antUrls = new LinkedHashSet<>(IGNORE_URL_LIST);
            for(String url : constant.httpLogIgnoreUrls) {
                if(StringUtils.isNotBlank(url)) {
                    antUrls.add(url.trim());
                }
            }
            urlMatcher = new UrlMatcher(antUrls, UrlMatcher.STATIC_URL_SUFFIX_LIST);
        }

        if(constant.httpLogIgnoreUrlParams != null && constant.httpLogIgnoreUrlParams.length > 0) {
            Set<String> antUrls = new LinkedHashSet<>(IGNORE_URL_PARAM_LIST);
            for(String url : constant.httpLogIgnoreUrlParams) {
                if(StringUtils.isNotBlank(url)) {
                    antUrls.add(url.trim());
                }
            }
            urlParamMatcher = new UrlMatcher(antUrls);
        }

        if(constant.httpLogIgnoreHeaders != null && constant.httpLogIgnoreHeaders.length > 0) {
            ignoreHeaders.addAll(Arrays.asList(constant.httpLogIgnoreHeaders));
        }
    }

    public static boolean canLog(String url, LogLevel level, Logger logger) {

        if(level.equals(LogLevel.NONE) || !logger.isInfoEnabled()) {
            return false;
        }

        return url == null || !urlMatcher.ignore(url);
    }

    public static boolean isIgnoreUrlParam(String url) {
        return StringUtils.isBlank(url) || urlParamMatcher.ignore(url);
    }


    public static boolean isIgnoreHeader(String header) {
        return StringUtils.isBlank(header) || ignoreHeaders.contains(header);
    }

    public static void main(String[] args) {

        IGNORE_URL_LIST.add("/login");
        IGNORE_URL_LIST.add("/register");
        IGNORE_URL_LIST.add("/path1/*");

        urlMatcher = new UrlMatcher(new LinkedHashSet<>(IGNORE_URL_LIST), UrlMatcher.STATIC_URL_SUFFIX_LIST);

        List<String> urls = Lists.newArrayList("/do_not_delete/check.html", "/do_not_delete", "/login",
                "/register", "/path1/123123", "/path2", "/123/encrypt", "/decrypt", "/123enrypt");

        urls.stream().filter(urlMatcher::ignore).forEach(x -> System.out.println(x));
        if(urls.stream().filter(urlMatcher::ignore).count() != 4) {
            throw new AssertionError();
        }

        String s = "http://10.10.10.90:8888/account/uploadAuditFileNew?jsonBody=%7B%22requestNo%";
        Set<String> set = new HashSet<>();
        set.add("**/uploadAuditFileNew*");
        urlMatcher = new UrlMatcher(set);
        System.out.println(urlMatcher.ignore(s));

    }

    public static String logError(Throwable ex) {
         return logError(ex, null);
    }

    public static String logError(Throwable ex, Predicate<Throwable> ignore) {

        if(ex == null || (ignore != null && ignore.test(ex)))  {
            return null;
        }
        return ex.getClass().getSimpleName() + ": " + ex.getMessage();
    }

    public static String getPathFromUrl(String url) {
        try {
            return new URL(url).getPath();
        } catch (Exception ignore) {
            return null;
        }
    }

    public static void swallowException(Logger logger, Log.RunnableThrowable runnable) {

        try {
            runnable.run();
        } catch (Exception e) {
            logger.error("生成PerformanceLog的时候发生错误", e);
        }
    }

    public static String trimRequestUri(String uri) {
        if(uri == null) {return null;};
        return uri.startsWith("//") ? uri.replaceFirst("//", "/") : uri;
    }

    public static Map<String, List<String>> removeIgnoreHeader(Map<String, List<String>> map) {
        map.entrySet().removeIf(entry -> HttpLogUtil.isIgnoreHeader(entry.getKey()));
        return map;
    }

}
