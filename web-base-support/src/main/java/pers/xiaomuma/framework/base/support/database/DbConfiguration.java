package pers.xiaomuma.framework.base.support.database;

import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import pers.xiaomuma.framework.base.support.database.annotation.proxy.DataSourceInterceptor;

@Order(-1)
@AutoConfigureOrder(-1)
@Import({
		MybatisPlusConfiguration.class,
		MultipleDataSourceConfiguration.class,
		DataSourceInterceptor.class
})
public class DbConfiguration {

}
