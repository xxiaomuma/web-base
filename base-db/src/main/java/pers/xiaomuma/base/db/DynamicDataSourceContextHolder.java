package pers.xiaomuma.base.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;


public class DynamicDataSourceContextHolder {

    private static final Logger LOGGER = LoggerFactory.getLogger(DynamicDataSourceContextHolder.class);

    private static Set<String> dataSources = null;

    public static final ThreadLocal<String> CONTEXT_HOLDER = ThreadLocal.withInitial(String::new);

    public static synchronized void initDataSource(Set<String> dataSources, String defaultDataSource) {
        DynamicDataSourceContextHolder.dataSources = dataSources;
        DynamicDataSourceContextHolder.putDataSource(defaultDataSource);
    }

    public static void putDataSource(String dataSource) {
        LOGGER.debug("使用数据源--> " + dataSource);
        CONTEXT_HOLDER.set(dataSource);
    }

    public static void removeDataSource(String dataSource) {
        LOGGER.debug("移除数据源--> " + dataSource);
        CONTEXT_HOLDER.remove();
    }

    public static String getDataSource() {
        LOGGER.debug("当前数据源数据源--> " + CONTEXT_HOLDER.get());
        return CONTEXT_HOLDER.get();
    }

    public static boolean hasDataSource(String dataSource){
        return dataSources.contains(dataSource);
    }

}
