package pers.xiaomuma.framework.utils;

import com.google.common.base.Splitter;
import org.apache.commons.lang3.StringUtils;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StrUtils {

    public static Set<Integer> splitToIntSet(String str) {
        if (StringUtils.isEmpty(str)) {
            return new HashSet<>();
        }
        List<String> strList = Splitter.on(",").splitToList(str);
        return strList.stream().map(Integer::valueOf).collect(Collectors.toSet());
    }

}
