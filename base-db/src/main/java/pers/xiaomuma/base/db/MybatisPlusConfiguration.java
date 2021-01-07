package pers.xiaomuma.base.db;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;


public class MybatisPlusConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "mybatis-plus.configuration")
    public MybatisConfiguration configuration(){
        return new MybatisConfiguration();
    }
}
