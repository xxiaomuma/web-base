package pers.xiaomuma.base.db.config;

import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import pers.xiaomuma.base.db.MultipleDataSourceConfiguration;
import pers.xiaomuma.base.db.MybatisPlusConfiguration;
import pers.xiaomuma.base.db.aop.DataSourceInterceptor;

@Order(-1)
@AutoConfigureOrder(-1)
@Import({
		MultipleDataSourceConfiguration.class,
		DataSourceInterceptor.class
})
public class BaseDbConfiguration {

}
