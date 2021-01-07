package pers.xiaomuma.base.db.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import pers.xiaomuma.base.db.DynamicDataSourceContextHolder;


@Aspect
@Order(-1)
@Configuration
public class DataSourceInterceptor {

    @Around(value = "@annotation(dataSource)")
    public Object proceed(ProceedingJoinPoint point, DataSource dataSource) throws Throwable {
        DynamicDataSourceContextHolder.putDataSource(dataSource.value());
        try {
            return point.proceed();
        } finally {
            DynamicDataSourceContextHolder.removeDataSource(dataSource.value());
        }
    }

}
