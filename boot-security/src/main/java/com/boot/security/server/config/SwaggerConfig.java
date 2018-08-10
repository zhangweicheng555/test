package com.boot.security.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger文档
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket docket() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("接口文档说明")
				.apiInfo(new ApiInfoBuilder().title("接口文档")
						.contact(new Contact("大数据可视化呈现技术接口", "", "19348243@qq.com")).version("1.0").build())
				.select().paths(PathSelectors.any()).build();
	}
}
