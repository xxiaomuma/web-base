package pers.xiaomuma.base.db;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class MultipleDataSource {

    private Set<String> dataSources;
    private String defaultDataSource;

    public MultipleDataSource(List<String> dataSources) {
        if(dataSources == null || dataSources.isEmpty()) {
            throw new RuntimeException("MultipleDataSource dataSources cannot be empty!");
        }
        this.dataSources = new HashSet<>(dataSources);
        this.defaultDataSource = dataSources.iterator().next();
        DynamicDataSourceContextHolder.initDataSource(this.dataSources, this.defaultDataSource);
    }

    public Set<String> getDataSources() {
        return dataSources;
    }

    public String getDefaultDataSource() {
        return defaultDataSource;
    }
}
