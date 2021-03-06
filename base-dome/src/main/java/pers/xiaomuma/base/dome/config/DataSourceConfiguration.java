package pers.xiaomuma.base.dome.config;

import com.google.common.collect.Lists;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.xiaomuma.base.dome.constant.DataSourceName;
import pers.xiaomuma.base.db.MultipleDataSource;


@Configuration
@MapperScan(basePackages = "pers.xiaomuma.base.dome.dao")
public class DataSourceConfiguration {

    @Bean
    public MultipleDataSource multipleDataSource() {
        return new MultipleDataSource(Lists.newArrayList(DataSourceName.DOME1, DataSourceName.DOME2));
    }
}
