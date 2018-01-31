package com.centrifuge.metrics.demometrics.config;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 * Provides Swagger configuration
 * 
 * @author Ram Mannam
 * @version 1.0
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket controllerApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.centrifuge.metrics.demometrics.web.controller"))
				.paths(PathSelectors.any()).build().apiInfo(metaData());
	}

	private ApiInfo metaData() {
		ApiInfo apiInfo = new ApiInfo("Spring Boot Demo Metrics Rest API",
				"Spring Boot REST API for different Rest Controllers", "1.0", "Terms of Service",
				new Contact("Ram Mannam", "Website URL", "ram.mannam@gmail.com"), "License Version 1.0", "License URL",
				new ArrayList<VendorExtension>());
		return apiInfo;
	}

}
