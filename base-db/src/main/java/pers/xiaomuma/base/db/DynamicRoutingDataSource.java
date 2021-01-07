package pers.xiaomuma.base.db;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;



public class DynamicRoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceContextHolder.getDataSource();
    }

}
