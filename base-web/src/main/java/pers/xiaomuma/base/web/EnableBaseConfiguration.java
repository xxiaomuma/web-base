package pers.xiaomuma.base.web;

import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import pers.xiaomuma.base.db.config.BaseDbConfiguration;
import pers.xiaomuma.base.web.config.BaseWebConfiguration;
import java.lang.annotation.*;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({BaseDbConfiguration.class, BaseWebConfiguration.class})
@EnableAspectJAutoProxy(
        exposeProxy = true,
        proxyTargetClass = true
)
public @interface EnableBaseConfiguration {
}
