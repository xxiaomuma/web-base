package pers.xiaomuma.framework.base.support.database;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;
import pers.xiaomuma.framework.base.support.database.mybatis.MybatisSQLParameterInterceptor;

import java.util.*;

@Configuration
@ConditionalOnBean(MultipleDataSource.class)
@MapperScan(basePackages = {"pers.xiaomuma.**.mapper"})
@EnableTransactionManagement
public class MultipleDataSourceConfiguration implements ApplicationContextAware,BeanDefinitionRegistryPostProcessor {

    private ApplicationContext applicationContext;

    private Logger logger = LoggerFactory.getLogger(MultipleDataSourceConfiguration.class);

    @Bean(name = "dynamicDataSource")
    public AbstractRoutingDataSource dynamicDataSource(MultipleDataSource multipleDataSource,
                                                       MultipleDataSourceInitializer initializer) {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        //如果指定的dataSource key不存在, 则报错
        dynamicDataSource.setLenientFallback(false);
        Map<Object, Object> dataSourceMap = new HashMap<>();
        for (String ds : multipleDataSource.getDataSources()) {
            DruidDataSource druidDataSource = applicationContext.getBean(ds, DruidDataSource.class);
            //设置默认数据库
            if(ds.equalsIgnoreCase(multipleDataSource.getDefaultDataSource())) {
                dynamicDataSource.setDefaultTargetDataSource(druidDataSource);
            }
            dataSourceMap.put(ds, druidDataSource);
        }
        dynamicDataSource.setTargetDataSources(dataSourceMap);
        return dynamicDataSource;
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
                                               MultipleDataSourceInitializer initializer,
                                               MybatisConfiguration configuration) throws Exception {
        logger.info(initializer.PRINT_LOG);

        final MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        factoryBean.setDataSource(dynamicDataSource);

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        factoryBean.setMapperLocations(resolver.getResources("classpath*:/mapper/**/*.xml"));

        List<Interceptor> interceptors = new ArrayList<>();
        interceptors.add(new MybatisSQLParameterInterceptor());
        interceptors.add(new PaginationInterceptor());
        factoryBean.setPlugins(interceptors.toArray(new Interceptor[0]));

        factoryBean.setConfiguration(configuration);

        SqlSessionFactory sqlSessionFactory = factoryBean.getObject();
        // 驼峰命名
        sqlSessionFactory.getConfiguration().setMapUnderscoreToCamelCase(true);
        // 允许null
        sqlSessionFactory.getConfiguration().setJdbcTypeForNull(JdbcType.NULL);
        //设置默认全局超时时间
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

    @Bean
    public DataSourcePropertiesProcessor dataSourcePropertiesProcessor() {
        return new DataSourcePropertiesProcessor();
    }

    @Bean(name = MultipleDataSourceInitializer.BEAN_NAME)
    public MultipleDataSourceInitializer multipleDataSourceInitializer(DataSourcePropertiesProcessor processor, MultipleDataSource multipleDataSource) {
        for(String ds : multipleDataSource.getDataSources()) {
            DruidDataSource dataSource = applicationContext.getBean(ds, DruidDataSource.class);

            //读取配置文件
            processor.postProcessBeforeInitialization(dataSource,  ds);

            initDruidDataSource(dataSource);

        }
        return MultipleDataSourceInitializer.DEFAULT;
    }

    /**
     * 初始化druid 数据源
     * @param dataSource
     */
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
            logger.error("初始化druid数据源发生错误, ex: " + e.getMessage());
        }

    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 生成数据源对应的bean
     * @param registry
     * @throws BeansException
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        MultipleDataSource multipleDataSource = applicationContext.getBean(MultipleDataSource.class);
        if(multipleDataSource == null) {
            throw new RuntimeException("multipleDataSource cannot be null, " +
                    "配置了MultipleDataSourceConfiguration就一定要定义MultipleDataSource");
        }
        Set<String> dataSources = multipleDataSource.getDataSources();
        String defaultDataSource = multipleDataSource.getDefaultDataSource();

        for(String ds : dataSources) {
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(DruidDataSource.class);
            if(ds.equalsIgnoreCase(defaultDataSource)) {
                builder.getBeanDefinition().setPrimary(true);
            }
            registry.registerBeanDefinition(ds, builder.getBeanDefinition());
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        //
    }
}