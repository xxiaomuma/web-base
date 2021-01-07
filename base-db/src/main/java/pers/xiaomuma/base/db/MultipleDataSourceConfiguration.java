package pers.xiaomuma.base.db;


import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;
import java.util.*;

@Order(-1)
@AutoConfigureOrder(-1)
@ConditionalOnBean(MultipleDataSource.class)
@Import(MybatisPlusConfiguration.class)
@Configuration
@EnableTransactionManagement
public class MultipleDataSourceConfiguration implements ApplicationContextAware, BeanDefinitionRegistryPostProcessor {

    private ApplicationContext applicationContext;

    @Bean("dynamicDataSource")
    public AbstractRoutingDataSource dynamicDataSource() {
        MultipleDataSource multipleDataSource = getMultipleDataSource();
        DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();
        dynamicRoutingDataSource.setLenientFallback(false);
        Map<Object, Object> dataSourceMap = new HashMap<>();
        Set<String> dataSources = multipleDataSource.getDataSources();
        String defaultDataSource = multipleDataSource.getDefaultDataSource();
        dataSources.forEach(dataSource -> {
            DruidDataSource druidDataSource = applicationContext.getBean(dataSource, DruidDataSource.class);
            if(dataSource.equalsIgnoreCase(defaultDataSource)) {
                dynamicRoutingDataSource.setDefaultTargetDataSource(druidDataSource);
            }
            dataSourceMap.put(dataSource, druidDataSource);
        });
        dynamicRoutingDataSource.setTargetDataSources(dataSourceMap);
        return dynamicRoutingDataSource;
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(AbstractRoutingDataSource dynamicDataSource) {
        return new DataSourceTransactionManager(dynamicDataSource);
    }

    @Bean
    @ConditionalOnMissingBean
    public TransactionTemplate transactionTemplate(PlatformTransactionManager platformTransactionManager) {
        return new TransactionTemplate(platformTransactionManager);
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(AbstractRoutingDataSource dynamicDataSource,
                                               MybatisConfiguration configuration) throws Exception {

        final MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        factoryBean.setDataSource(dynamicDataSource);

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        factoryBean.setMapperLocations(resolver.getResources("classpath*:/mapper/**/*.xml"));

        Set<Interceptor> interceptors = new HashSet<>();
       // interceptors.add(new MybatisSQLParameterInterceptor());
        interceptors.add(new PaginationInterceptor());

        factoryBean.setPlugins(interceptors.toArray(new Interceptor[0]));

        factoryBean.setConfiguration(configuration);
        SqlSessionFactory sqlSessionFactory = factoryBean.getObject();
        sqlSessionFactory.getConfiguration().setMapUnderscoreToCamelCase(true);
        sqlSessionFactory.getConfiguration().setJdbcTypeForNull(JdbcType.NULL);

        Integer defaultStatementTimeout = sqlSessionFactory.getConfiguration().getDefaultStatementTimeout();
        if(defaultStatementTimeout == null) {
            sqlSessionFactory.getConfiguration().setDefaultStatementTimeout(30);
        }
        return sqlSessionFactory;
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }


    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        MultipleDataSource multipleDataSource = getMultipleDataSource();
        Set<String> dataSources = multipleDataSource.getDataSources();
        String defaultDataSource = multipleDataSource.getDefaultDataSource();
        dataSources.forEach(dataSource -> {
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(MultipleDataSource.class);
            if (dataSource.equalsIgnoreCase(defaultDataSource)) {
                builder.getBeanDefinition().setPrimary(true);
            }
            registry.registerBeanDefinition(dataSource, builder.getBeanDefinition());
        });
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory factory) throws BeansException {
        //
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private MultipleDataSource getMultipleDataSource() {
        return applicationContext.getBean(MultipleDataSource.class);
    }
}
