package pers.xiaomuma.base.db;


import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import java.util.Set;

@Configuration
public class DynamicDataSourceRegistry implements ApplicationContextAware,
        BeanDefinitionRegistryPostProcessor, EnvironmentAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(DynamicDataSourceRegistry.class);

    private Environment environment;
    private ApplicationContext applicationContext;
    private DataSourcePropertiesProcessor processor = new DataSourcePropertiesProcessor();

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        MultipleDataSource multipleDataSource = applicationContext.getBean(MultipleDataSource.class);
        Set<String> dataSources = multipleDataSource.getDataSources();
        String defaultDataSource = multipleDataSource.getDefaultDataSource();
        dataSources.forEach(dataSource -> {
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(DruidDataSource.class);
            if (dataSource.equalsIgnoreCase(defaultDataSource)) {
                builder.getBeanDefinition().setPrimary(true);
            }
            registry.registerBeanDefinition(dataSource, builder.getBeanDefinition());
        });
        multipleDataSourceInitializer(multipleDataSource);
    }



    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory factory) throws BeansException {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public void multipleDataSourceInitializer(MultipleDataSource multipleDataSource) {
        Set<String> dataSources = multipleDataSource.getDataSources();
        dataSources.forEach(dataSource -> {
            DruidDataSource druidDataSource = applicationContext.getBean(dataSource, DruidDataSource.class);

            processor.postProcessBeforeInitialization(druidDataSource, dataSource);

            initDruidDataSource(druidDataSource);
        });
    }

    private void initDruidDataSource(DruidDataSource dataSource) {

        try {

            //设置默认参数
            if(dataSource.getMaxActive() == 8 || dataSource.getMaxActive() == 5) {
                dataSource.setMaxActive(100);
            }

            if(dataSource.getInitialSize() == 0 || dataSource.getInitialSize() == 1) {
                dataSource.setInitialSize(10);
            }

            if(dataSource.getMinIdle() == 0) {
                dataSource.setMinIdle(10);
            }


            if(!dataSource.isPoolPreparedStatements()) {
                dataSource.setMaxPoolPreparedStatementPerConnectionSize(5);
            }

            //设置获取连接的最大等待时间为10s
            if(dataSource.getMaxWait() < 0 || dataSource.getMaxWait() > 5000L) {
                dataSource.setMaxWait(5000L);
            }

            if(dataSource.getValidationQuery() == null) {
                dataSource.setValidationQuery("SELECT 'x'");
            }

            if(dataSource.getValidationQueryTimeout() < 0) {
                dataSource.setValidationQueryTimeout(0);
            }

        } catch (Exception e) {
            LOGGER.error("初始化druid数据源发生错误, ex: " + e.getMessage());
        }

    }

    public class DataSourcePropertiesProcessor {

        public void postProcessBeforeInitialization(Object bean, String prefix) {
            Binder binder = Binder.get(environment);
            Bindable<?> bindable = Bindable.ofInstance(bean);
            BindResult<?> bindResult = binder.bind(prefix, bindable);
            if (!bindResult.isBound()) {
                throw new RuntimeException("Datasource properties binding error: " + prefix);
            }
        }
    }
}
