package pers.xiaomuma.framework.support.database;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.env.Environment;
import pers.xiaomuma.framework.core.global.ApplicationContextHolder;
import pers.xiaomuma.framework.exception.StartupFailureException;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class MultipleDataSource implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(MultipleDataSource.class);
    private Set<String> dataSources;
    private String defaultDataSource;
    /**
     * 在这个集合中定义的数据源, 在项目启动的时候会进行检测, 为避免启动时间过长, 检测的数据源数量越少越好.
     * 如果需要进行检测, 除了设置这个变量之外, 还需要初始化StartupDataSourceCheckInitializer这个bean
     */
    private Set<String> startupNeededDataSources;

    /**
     * list中的第一个为默认数据源
     */
    public MultipleDataSource(List<String> dataSources) {

        this(dataSources, null, null);
    }

    /**
     * list中的第一个为默认数据源
     */
    public MultipleDataSource(List<String> dataSources, Map<String, String> dataSourceReplaceMap) {
        this(dataSources, dataSourceReplaceMap, null);
    }

    /**
     * list中的第一个为默认数据源
     * @param startupNeededDataSources 在这个集合中定义的数据源, 在项目启动的时候会进行检测, 为避免启动时间过长, 检测的数据源数量越少越好.
     */
    public MultipleDataSource(List<String> dataSources, Map<String, String> dataSourceReplaceMap, List<String> startupNeededDataSources) {

        if(dataSources == null || dataSources.isEmpty()) {
            throw new StartupFailureException("MultipleDataSource dataSources cannot be empty!");
        }
        this.dataSources = new HashSet<>(dataSources);
        this.defaultDataSource = dataSources.get(0);
        this.startupNeededDataSources = startupNeededDataSources == null ? new HashSet<>() : new HashSet<>(startupNeededDataSources);

        DataSourceHolder.initDataSourceReplaceMap(dataSourceReplaceMap);
        DataSourceHolder.defaultDataSource4Record = this.defaultDataSource;
    }


    public Set<String> getDataSources() {
        return dataSources;
    }

    public String getDefaultDataSource() {
        return defaultDataSource;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            if(!isValidDataSource(defaultDataSource)) {
                throw new StartupFailureException("默认数据源配置读取失败, 没有在配置文件中进行配置? ds: " + defaultDataSource);
            }
            Set<String> filterDataSources = new HashSet<>();
            for(String ds : dataSources) {
                if(isValidDataSource(ds)) {
                    filterDataSources.add(ds);
                } else {
                    logger.warn("{}数据源初始化失败, 没有配置在配置文件中?", ds);
                }
            }
            this.dataSources = filterDataSources;
        } catch (Exception e) {
            logger.error("MultipleDataSource在过滤无效数据源的时候发生错误", e);
        }
    }

    /**
     * 检测数据源是否有效
     */
    private boolean isValidDataSource(String ds) {

        Environment environment = ApplicationContextHolder.getApplicationContext().getBean(Environment.class);
        String url = environment.getProperty(ds + ".url");
        String username = environment.getProperty(ds + ".username");
        String password = environment.getProperty(ds + ".password");

        return StringUtils.isNotBlank(url) && StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password);
    }

    public Set<String> getStartupNeededDataSources() {
        return startupNeededDataSources;
    }
}
