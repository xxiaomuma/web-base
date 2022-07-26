package pers.xiaomuma.framework.base.support.database;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import pers.xiaomuma.framework.core.startup.AbstractBaseApplicationInitializer;
import pers.xiaomuma.framework.exception.StartupFailureException;
import java.util.Set;

@Component
public class StartupDataSourceCheckInitializer extends AbstractBaseApplicationInitializer implements ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(StartupDataSourceCheckInitializer.class);


    private ApplicationContext applicationContext;

    @Override
    protected void doInit() {

        String maybeErrorDataSourceKey = null;

        try {
            MultipleDataSource multipleDataSource = applicationContext.getBean(MultipleDataSource.class);
            Set<String> startupNeededDataSources = multipleDataSource.getStartupNeededDataSources();
            if(startupNeededDataSources == null || startupNeededDataSources.isEmpty()) return;
            for(String dataSourceKey : startupNeededDataSources) {
                maybeErrorDataSourceKey = dataSourceKey;
                DruidDataSource dataSource = applicationContext.getBean(dataSourceKey, DruidDataSource.class);
                dataSource.getConnection();
            }
        } catch (Exception e) {
            String msg = "启动时校验数据库连接失败, 应用启动失败, dataSourceKey: " + maybeErrorDataSourceKey;
            logger.error(msg, e);
            throw new StartupFailureException(msg);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    protected boolean isFatal() {
        return true;
    }
}
