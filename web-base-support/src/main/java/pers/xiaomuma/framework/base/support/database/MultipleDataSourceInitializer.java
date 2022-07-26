package pers.xiaomuma.framework.base.support.database;

public interface MultipleDataSourceInitializer {

    MultipleDataSourceInitializer DEFAULT = new Default();

    String BEAN_NAME = "multipleDataSourceInitializer";

    String PRINT_LOG = "SqlSessionFactory is initializing now";

    class Default implements MultipleDataSourceInitializer{

    }
}
