package pers.xiaomuma.base.db;

import java.util.Map;
import java.util.Set;


public class MultipleDataSource {

    private Set<String> dataSources;
    private String defaultDataSource;

    public MultipleDataSource(Set<String> dataSources, Map<String, String> dataSourceReplaceMap) {
        if(dataSources == null || dataSources.isEmpty()) {
            throw new RuntimeException("MultipleDataSource dataSources cannot be empty!");
        }
        this.dataSources = dataSources;
        this.defaultDataSource = dataSources.iterator().next();
        DynamicDataSourceContextHolder.initDataSourceReplaceMap(dataSourceReplaceMap);
    }

    public Set<String> getDataSources() {
        return dataSources;
    }

    public String getDefaultDataSource() {
        return defaultDataSource;
    }
}
