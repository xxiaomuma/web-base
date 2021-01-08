package pers.xiaomuma.base.web;

import org.springframework.context.ApplicationContext;

public class ApplicationContextHolder {

    private static ApplicationContext applicationContext;

    public static void init(ApplicationContext applicationContext) {
        ApplicationContextHolder.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

}
