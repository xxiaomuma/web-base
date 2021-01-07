package pers.xiaomuma.base.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;


public class DynamicDataSourceContextHolder {

    private static final Logger LOGGER = LoggerFactory.getLogger(DynamicDataSourceContextHolder.class);

    private static Map<String, String> dataSourceReplaceMap = null;

    public static final ThreadLocal<DataSourceContainer> CONTEXT_HOLDER = ThreadLocal.withInitial(DataSourceContainer::new);


    public static synchronized void initDataSourceReplaceMap(Map<String, String> map) {
        if(dataSourceReplaceMap != null) {
            throw new RuntimeException("dataSourceReplaceMap初始化失败, 已经不为空, dataSourceReplaceMap: " + dataSourceReplaceMap.toString());
        }
        DynamicDataSourceContextHolder.dataSourceReplaceMap = map;
    }

    public static void putServiceDataSource(String dataSource) {
        CONTEXT_HOLDER.get().putServiceDataSource(dataSource);
        LOGGER.debug("使用service数据源--> " + dataSource);
    }

    public static void removeServiceDataSource(String dataSource) {
        CONTEXT_HOLDER.get().removeServiceDataSource(dataSource);
        LOGGER.debug("移除service数据源--> " + dataSource);
    }

    public static void putDataSource(String dataSource) {
        CONTEXT_HOLDER.get().putManualDataSource(dataSource);
        LOGGER.debug("使用manual数据源--> " + dataSource);
    }

    public static void removeDataSource(String dataSource) {
        CONTEXT_HOLDER.get().removeManualDataSource(dataSource);
        LOGGER.debug("移除manual数据源--> " + dataSource);
    }

    public static void runInDataSource(String dataSource, Runnable runnable) {
        try {
            putDataSource(dataSource);
            runnable.run();
        } finally {
            removeDataSource(dataSource);
        }
    }

    public static <T> T callInDataSource(String dataSource, Callable<T> callable) {
        try {
            putDataSource(dataSource);
            return callable.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            removeDataSource(dataSource);
        }
    }


    public static String getDataSource() {
        String dataSource = CONTEXT_HOLDER.get().getDataSource();
        if (dataSourceReplaceMap == null || dataSourceReplaceMap.isEmpty() || dataSource == null) {
            return dataSource;
        }
        String replaceDataSource = dataSourceReplaceMap.get(dataSource);
        LOGGER.info("数据源replace, before: {}, after: {}", dataSource, replaceDataSource);
        return replaceDataSource;
    }

    public static void clear() {
        CONTEXT_HOLDER.remove();
    }

    private static class DataSourceContainer {

        private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceContainer.class);
        private Set<String> manualDataSourceSet = new LinkedHashSet<>();
        private Set<ServiceDataSource> serviceDataSourceSet = new LinkedHashSet<>();

        public void putServiceDataSource(String dataSource) {
            serviceDataSourceSet.add(new ServiceDataSource(dataSource));
        }

        public void removeServiceDataSource(String datasource) {
            if (!serviceDataSourceSet.isEmpty()) {
                serviceDataSourceSet.remove(new ServiceDataSource(datasource));
            }
        }

        public void putManualDataSource(String dataSource) {
            if(!serviceDataSourceSet.isEmpty()) {
                serviceDataSourceSet.iterator().next().addManualDataSource(dataSource);
            } else {
                manualDataSourceSet.add(dataSource);
            }
        }

        public void removeManualDataSource(String dataSource) {
            if(!serviceDataSourceSet.isEmpty()) {
                serviceDataSourceSet.iterator().next().removeManualDataSource(dataSource);
            } else if(!manualDataSourceSet.isEmpty()){
                manualDataSourceSet.remove(dataSource);
            }
        }

        public String getDataSource() {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("请求数据库之前ThreadLocal中的数据源信息: " + this.toString());
            }
            if (manualDataSourceSet.isEmpty() && serviceDataSourceSet.isEmpty()) return null;

            if (!serviceDataSourceSet.isEmpty()) {
                return serviceDataSourceSet.iterator().next().currentDataSource();
            }
            return manualDataSourceSet.iterator().next();
        }

        @Override
        public String toString() {
            return "DataSourceContainer{" +
                    "manualDataSourceSet=" + manualDataSourceSet +
                    ", serviceDataSourceSet=" + serviceDataSourceSet +
                    '}';
        }
    }

    private static class ServiceDataSource {

        private String serviceDataSource;
        private Set<String> manualDataSourceSet = new LinkedHashSet<>();

        public ServiceDataSource(String dataSource) {
            this.serviceDataSource = dataSource;
        }

        public String getServiceDataSource() {
            return serviceDataSource;
        }

        public String currentDataSource() {
            if (!manualDataSourceSet.isEmpty()) {
                return manualDataSourceSet.iterator().next();
            }
            return serviceDataSource;
        }

        public void addManualDataSource(String dataSource) {
            manualDataSourceSet.add(dataSource);
        }

        public void removeManualDataSource(String dataSource) {
            if (!manualDataSourceSet.isEmpty()) {
                manualDataSourceSet.remove(dataSource);
            }
        }

        @Override
        public String toString() {
            return "ServiceDataSource{" +
                    "serviceDataSource='" + serviceDataSource + '\'' +
                    ", manualDataSourceList=" + manualDataSourceSet +
                    '}';
        }
    }
}
