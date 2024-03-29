package pers.xiaomuma.framework.support.cache;

import org.springframework.context.annotation.Import;
import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(RedisConfiguration.class)
public @interface EnableRedisCache {


}
