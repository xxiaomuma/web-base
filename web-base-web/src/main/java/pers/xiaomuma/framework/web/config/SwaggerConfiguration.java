package pers.xiaomuma.framework.web.config;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.xiaomuma.framework.core.global.ApplicationConstant;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import java.time.LocalDate;
import java.time.LocalDateTime;



@Configuration
@EnableOpenApi
public class SwaggerConfiguration {

	@Bean
	public Docket docket(ApplicationConstant applicationConstant) {
		if (applicationConstant.isProdProfile()) {
			return new Docket(DocumentationType.OAS_30).enable(false);
		} else {
			return new Docket(DocumentationType.OAS_30)
					.directModelSubstitute(LocalDate.class, java.sql.Date.class)
					.directModelSubstitute(LocalDateTime.class, java.util.Date.class)
					.select()
					.apis(input -> {
						if (input == null) {return false;}
						String packageName = input.declaringClass().getPackage().getName();
						return (
								(packageName.contains(".web") ||
								 packageName.contains(".controller")
								)
						);
					})
					.paths(PathSelectors.any())
					.build();
		}
	}

	@Bean
	public ApiInfo docInfo() {
		Contact contact = new Contact("xxiaomuma","xiaomuma.com", "m18870885528@163.com");
		return new ApiInfo(
				"web-base",
				"Api文档描述",
				"1.0.0",
				"xiaomuma.com",
				contact,
				"Apache 2.0",
				"http://www.apache.org/licenses/LICENSE-2.0",
				Lists.newArrayList());
	}


}
