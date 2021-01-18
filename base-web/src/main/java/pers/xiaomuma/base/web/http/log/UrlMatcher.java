package pers.xiaomuma.base.web.http.log;


import com.google.common.collect.Lists;
import org.springframework.util.AntPathMatcher;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UrlMatcher {

    public static final Set<String> STATIC_URL_SUFFIX_LIST = Lists.newArrayList(new String[]{"js", "gif", "jpg", "jpeg", "ico", "css", "ttd", "png"})
            .stream().map((x) -> "." + x).collect(Collectors.toSet());
    private AntPathMatcher pathMatcher = new AntPathMatcher();
    private Set<String> antUrls;
    private Set<String> suffixUrls;

    public UrlMatcher(Set<String> antUrls) {
        this.pathMatcher.setCaseSensitive(false);
        this.suffixUrls = new LinkedHashSet<>();
        this.antUrls = antUrls;
    }

    public UrlMatcher(Set<String> antUrls, Set<String> suffixUrls) {
        this.pathMatcher.setCaseSensitive(false);
        this.suffixUrls = new LinkedHashSet<>();
        this.antUrls = antUrls;
        this.suffixUrls = suffixUrls;
    }

    public boolean ignore(String url) {
        Stream<String> var10000 = this.suffixUrls.stream();
        //url.getClass();
        return var10000.anyMatch(url::endsWith) || this.antUrls.stream().anyMatch((ignoreUrl) -> this.pathMatcher.match(ignoreUrl, url));
    }
}
