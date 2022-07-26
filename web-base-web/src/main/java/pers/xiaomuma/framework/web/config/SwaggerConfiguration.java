package pers.xiaomuma.framework.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.xiaomuma.framework.core.global.ApplicationConstant;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

	@Bean
	public Docket docket(ApplicationConstant applicationConstant) {
		if (applicationConstant.isProdProfile()) {
			return new Docket(DocumentationType.SWAGGER_2).enable(false);
		} else {
			return new Docket(DocumentationType.SWAGGER_2)
					.directModelSubstitute(LocalDate.class, java.sql.Date.class)
					.directModelSubstitute(LocalDateTime.class, java.util.Date.class)
					.select()
					.apis(input -> {
						if (input == null) {return false;}
						String packageName = input.declaringClass().getPackage().getName();
						return (
								packageName.startsWith("com.yxbl.") &&
								(packageName.contains(".web") ||
								 packageName.contains(".controller")
								)
						);
					})
					.paths(PathSelectors.any())
					.build();
		}
	}

}
