package pers.xiaomuma.framework.support.database;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.xiaomuma.framework.exception.StartupFailureException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class DataSourceHolder {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceHolder.class);

    public static String defaultDataSource4Record = null;

    public static final ThreadLocal<DataSourceContainer> holder = ThreadLocal.withInitial(DataSourceContainer::new);

    private static Map<String, String> dataSourceReplaceMap = null;

    public static synchronized void initDataSourceReplaceMap(Map<String, String> map) {
        if(dataSourceReplaceMap != null) {
            throw new StartupFailureException(
                    "dataSourceReplaceMap初始化失败, 已经不为空, dataSourceReplaceMap: " + dataSourceReplaceMap.toString());
        }
        DataSourceHolder.dataSourceReplaceMap = map;
    }

    /**
     * 手动设置的数据源一定要通过finally removeDataSource进行删除
     * @param dataSource
     */
    public static void putDataSource(String dataSource) {
        logger.debug("使用manual数据源--> " + dataSource);
        holder.get().putManualDataSource(dataSource);
    }

    /**
     * 删除数据源
     * @param dataSource
     */
    public static void removeDataSource(String dataSource) {
        logger.debug("移除manual数据源--> " + dataSource);
        holder.get().removeManualDataSource(dataSource);
    }

    /**
     * 在手动设置的数据源范围内执行代码, 并且返回执行结果
     * @param dataSource 手动设置数据源
     * @param callable
     * @param <T>
     * @return
     */
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

    /**
     * 在手动设置的数据源范围内执行代码
     * @param dataSource 手动设置数据源
     * @param runnable
     * @return
     */
    public static void runInDataSource(String dataSource, Runnable runnable) {

        try {
            putDataSource(dataSource);
            runnable.run();
        } finally {
            removeDataSource(dataSource);
        }
    }



    static void putServiceDataSource(String dataSource) {
        logger.debug("使用service数据源--> " + dataSource);
        holder.get().putServiceDataSource(dataSource);
    }

    public static void removeServiceDataSource(String dataSource) {
        logger.debug("移除service数据源--> " + dataSource);
        holder.get().removeServiceDataSource(dataSource);
    }


    /**
     * 获取数据源之前先查询需不需要替换
     * @return
     */
    public static String getDataSource() {
        String dataSource = holder.get().getDataSource();
        if(dataSourceReplaceMap != null && !dataSourceReplaceMap.isEmpty() && dataSource != null) {
            String replaceDataSource = dataSourceReplaceMap.get(dataSource);
            if(StringUtils.isNotBlank(replaceDataSource)) {
                logger.info("数据源replace, before: {}, after: {}", dataSource, replaceDataSource);
                return replaceDataSource;
            }
        }

        return dataSource;
    }

    public static void clear() {
        holder.remove();
    }

    private static class DataSourceContainer {

        private static final Logger logger = LoggerFactory.getLogger(DataSourceContainer.class);

        private List<ServiceDataSource> serviceDataSourceList = new LinkedList<>();

        private List<String> manualDataSourceList = new LinkedList<>();

        void putManualDataSource(String dataSource) {
            if(!serviceDataSourceList.isEmpty()) {
                serviceDataSourceList.get(serviceDataSourceList.size() - 1).addManualDataSource(dataSource);
            } else {
                manualDataSourceList.add(dataSource);
            }
        }

        public void removeManualDataSource(String dataSource) {
            if(!serviceDataSourceList.isEmpty()) {
                serviceDataSourceList.get(serviceDataSourceList.size() - 1).removeManualDataSource(dataSource);
            } else if(!manualDataSourceList.isEmpty()){
                int lastIndex = manualDataSourceList.size() - 1;
                if (dataSource.equals(manualDataSourceList.get(lastIndex))) {
                    manualDataSourceList.remove(lastIndex);
                }
            }
        }

        void putServiceDataSource(String dataSource) {
            serviceDataSourceList.add(new ServiceDataSource(dataSource));
        }

        public void removeServiceDataSource(String datasource) {
            if (!serviceDataSourceList.isEmpty()) {
                int lastIndex = serviceDataSourceList.size() - 1;
                if (datasource.equals(serviceDataSourceList.get(lastIndex).getServiceDataSource())) {
                    serviceDataSourceList.remove(lastIndex);
                }
            }
        }


        /**
         * 调用这个方法, 得到当前的数据源
         * 数据源生效顺序:
         * 1. 通过putDataSource手动插入的数据源优先生效, 其次是通过@DataSource注解配置的数据源
         * 2. 通过putDataSource手动插入的数据源如果没有手动删除, 而且在方法有@DataSource注解的情况下, 会在方法结束的时候被自动删除
         *    如果方法没有@DataSource注解, 通过通过putDataSource手动插入的数据源会一直存在.
         * 3. 通过@DataSource注解配置的数据源有效范围是当前方法
         *
         * @return
         */
        String getDataSource() {

            if (logger.isDebugEnabled()) {
                logger.debug("请求数据库之前ThreadLocal中的数据源信息: " + this.toString());
            }

            if (manualDataSourceList.isEmpty() && serviceDataSourceList.isEmpty()) return null;

            if (!serviceDataSourceList.isEmpty()) {
                return serviceDataSourceList.get(serviceDataSourceList.size() - 1).currentDataSource();
            }

            //此时有通过@DataSource注解配置的数据源, 直接使用
            return manualDataSourceList.get(manualDataSourceList.size() - 1);
        }

        @Override
        public String toString() {
            return "DataSourceContainer{" +
                    "serviceDataSourceList=" + serviceDataSourceList +
                    ", manualDataSourceList=" + manualDataSourceList +
                    '}';
        }

    }

    private static class ServiceDataSource {

        private String serviceDataSource;

        private List<String> manualDataSourceList = new LinkedList<>();

        public ServiceDataSource(String dataSource) {
            this.serviceDataSource = dataSource;
        }

        public String currentDataSource() {
            return manualDataSourceList.isEmpty() ? serviceDataSource :
                    manualDataSourceList.get(manualDataSourceList.size() - 1);
        }

        public String getServiceDataSource() {
            return serviceDataSource;
        }

        public void addManualDataSource(String dataSource) {
            manualDataSourceList.add(dataSource);
        }

        @Override
        public String toString() {
            return "ServiceDataSource{" +
                    "serviceDataSource='" + serviceDataSource + '\'' +
                    ", manualDataSourceList=" + manualDataSourceList +
                    '}';
        }

        public void removeManualDataSource(String dataSource) {
            if (!manualDataSourceList.isEmpty()) {
                int lastIndex = manualDataSourceList.size() - 1;
                if (dataSource.equals(manualDataSourceList.get(lastIndex))) {
                    manualDataSourceList.remove(lastIndex);
                }
            }
        }
    }

}
