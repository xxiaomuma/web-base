package pers.xiaomuma.base.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;
import java.util.concurrent.Callable;


public class DynamicDataSourceContextHolder {

    private static final Logger LOGGER = LoggerFactory.getLogger(DynamicDataSourceContextHolder.class);

    private static Set<String> DATA_SOURCES = null;
    private static String DEFAULT_DATA_SOURCE = null;

    public static final ThreadLocal<String> CONTEXT_HOLDER = ThreadLocal.withInitial(String::new);

    public static synchronized void initDataSource(Set<String> dataSources, String defaultDataSource) {
        DynamicDataSourceContextHolder.DATA_SOURCES = dataSources;
        DynamicDataSourceContextHolder.DEFAULT_DATA_SOURCE = defaultDataSource;
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
        String dataSource = CONTEXT_HOLDER.get();
        if (dataSource == null || "".equals(dataSource)) dataSource = DEFAULT_DATA_SOURCE;
        LOGGER.debug("当前数据源数据源--> " + dataSource);
        return dataSource;
    }

    public static void runInDataSource(String dataSource, Runnable runnable) {
        try {
            CONTEXT_HOLDER.set(dataSource);
            runnable.run();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            removeDataSource(dataSource);
        }
    }

    public static <T> T callInDataSource(String dataSource, Callable<T> callable) {
        try {
            CONTEXT_HOLDER.set(dataSource);
            return callable.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            removeDataSource(dataSource);
        }
    }

    public static boolean hasDataSource(String dataSource){
        return DATA_SOURCES.contains(dataSource);
    }

}
