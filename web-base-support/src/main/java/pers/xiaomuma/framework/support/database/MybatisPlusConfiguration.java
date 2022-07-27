package pers.xiaomuma.framework.support.database;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPlusConfiguration {

	@Bean
	@ConfigurationProperties(prefix = "mybatis-plus.configuration")
	public MybatisConfiguration configuration(){
		return new MybatisConfiguration();
	}

}
