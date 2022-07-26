package pers.xiaomuma.framework.core.log;

import com.google.common.collect.Lists;
import org.springframework.util.AntPathMatcher;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UrlMatcher {

    public static final Set<String> STATIC_URL_SUFFIX_LIST = Lists.newArrayList(
            "js", "gif", "jpg", "jpeg", "ico", "css", "ttd", "png")
            .stream().map(x -> "." + x).collect(Collectors.toSet());

    private AntPathMatcher pathMatcher = new AntPathMatcher();

    {
        pathMatcher.setCaseSensitive(false);
    }

    private Set<String> antUrls;

    private Set<String> suffixUrls = new LinkedHashSet<>();

    public UrlMatcher(Set<String> antUrls) {
        this.antUrls = antUrls;
    }

    public UrlMatcher(Set<String> antUrls, Set<String> suffixUrls) {
        this.antUrls = antUrls;
        this.suffixUrls = suffixUrls;
    }

    /**
     * @param url 不能为null
     */
    public boolean ignore(String url) {
        return suffixUrls.stream().anyMatch(url::endsWith) ||
                antUrls.stream().anyMatch(ignoreUrl -> pathMatcher.match(ignoreUrl, url));
    }



}
