package pers.xiaomuma.framework.utils;

import cn.hutool.core.util.StrUtil;

public class MackUtils {

    public static String mobile(String mobile) {
        if (StrUtil.isBlank(mobile)) {
            return mobile;
        }
        return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
    }

    public static String email(String email) {
        if (StrUtil.isBlank(email)) {
            return email;
        }
        return email.replaceAll("(\\w?)(\\w+)(\\w)(@\\w+\\.[a-z]+(\\.[a-z]+)?)", "$1****$3$4");
    }
}
