package pers.xiaomuma.framework.support.database.annotation.proxy;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import pers.xiaomuma.framework.support.database.DataSourceHolder;
import pers.xiaomuma.framework.support.database.annotation.DataSource;

@Aspect
@Order(-1)
@Configuration
public class DataSourceInterceptor {

    @Around(value = "@annotation(ds)")
    public Object proceed(ProceedingJoinPoint pjp, DataSource ds) throws Throwable {
        DataSourceHolder.putDataSource(ds.value());
        try {
            return pjp.proceed();
        } finally {
            DataSourceHolder.removeDataSource(ds.value());
        }
    }

}
